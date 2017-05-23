package tutorial;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class CreateCircleFromMousePressed extends Application {
	/* Global */
	Circle circle = new Circle(0,0,0);
	
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		/* init */
		Pane panelsPane = new Pane();
		VBox baseLayout = new VBox();
		Scene rootScene = new Scene(panelsPane, 500, 500);
		Image image = new Image("https://upload.wikimedia.org/wikipedia/en/2/24/Lenna.png");
		ImageView imageView = new ImageView(image);

		makeFilter(imageView);
		
		/* getChildren().add(node) */
		panelsPane.getChildren().add(baseLayout);
		stage.setScene(rootScene);
		stage.show();

	}

	private void makeFilter(Node node) {
		node.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
			@Override
			public void handle(final MouseEvent mouseEvent) {
				mouseEvent.consume();
			}
		});

		node.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(final MouseEvent mouseEvent) {
				circle.setStroke(Color.BLUE);
				circle.setStrokeWidth(10);
				
			}
		});
	}
}
