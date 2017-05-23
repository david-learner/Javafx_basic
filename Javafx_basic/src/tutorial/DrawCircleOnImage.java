package tutorial;

import java.awt.RenderingHints.Key;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class DrawCircleOnImage extends Application {

	final Image image = new Image(
			"https://upload.wikimedia.org/wikipedia/commons/thumb/1/14/Gatto_europeo4.jpg/1024px-Gatto_europeo4.jpg");
	Circle circle = new Circle(200, 200, 100);
	Group imageLayer = new Group();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		final ImageView imageView = makeImageView();
		ScrollPane scrollPane = new ScrollPane();

		imageLayer.getChildren().add(imageView);
		imageLayer.getChildren().add(circle);

		VBox vBox = new VBox();
		Scene rootScene = new Scene(vBox, 0, 0);

		scrollPane.setContent(imageLayer);
		vBox.getChildren().add(scrollPane);

		stage.setScene(rootScene);
		stage.show();
	}

	private ImageView makeImageView() {
		final DragContext dragContext = new DragContext();
		ImageView imageView = new ImageView(image);
		imageView.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
			@Override
			public void handle(final MouseEvent mouseEvent) {
				mouseEvent.consume();
			}
		});
		
		imageLayer.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(final MouseEvent mouseEvent) {
				dragContext.mouseAnchorX = mouseEvent.getX();
				dragContext.mouseAnchorY = mouseEvent.getY();
				System.out.println("mouseAnchor : " + dragContext.mouseAnchorX + " " + dragContext.mouseAnchorY);
				circle.setRadius(0);
				circle.setStroke(Color.BISQUE);
				circle.setStrokeWidth(3);
				circle.setCenterX(dragContext.mouseAnchorX);
				circle.setCenterY(dragContext.mouseAnchorY);
				System.out.println("circle center : " + circle.getCenterX() + " " + circle.getCenterY());
				circle.setFill(Color.TRANSPARENT);
			}
		});
		
		imageLayer.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(final MouseEvent mouseEvent) {
				double x = mouseEvent.getX() - dragContext.mouseAnchorX;
				double y = mouseEvent.getY() - dragContext.mouseAnchorY;
				double radius = Math.sqrt(Math.abs(x) + Math.abs(y));
				dragContext.moveDistance = Math.pow(radius, 2);
				System.out.println("moveDistance : " + dragContext.moveDistance);
				circle.setRadius(dragContext.moveDistance);
			}
		});

//		imageView.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(final MouseEvent mouseEvent) {
//				dragContext.mouseAnchorX = mouseEvent.getX();
//				dragContext.mouseAnchorY = mouseEvent.getY();
//				System.out.println("mouseAnchor : " + dragContext.mouseAnchorX + " " + dragContext.mouseAnchorY);
//				circle.setRadius(0);
//				circle.setStroke(Color.BISQUE);
//				circle.setStrokeWidth(3);
//				circle.setCenterX(dragContext.mouseAnchorX);
//				circle.setCenterY(dragContext.mouseAnchorY);
//				System.out.println("circle center : " + circle.getCenterX() + " " + circle.getCenterY());
//				circle.setFill(Color.TRANSPARENT);
//			}
//		});
//
//		imageView.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(final MouseEvent mouseEvent) {
//				double x = mouseEvent.getX() - dragContext.mouseAnchorX;
//				double y = mouseEvent.getY() - dragContext.mouseAnchorY;
//				double radius = Math.sqrt(Math.abs(x) + Math.abs(y));
//				dragContext.moveDistance = Math.pow(radius, 2);
//				System.out.println("moveDistance : " + dragContext.moveDistance);
//				circle.setRadius(dragContext.moveDistance);
//			}
//		});
//
//		circle.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(final MouseEvent mouseEvent) {
//				if (mouseEvent.isPrimaryButtonDown()) {
//					dragContext.mouseAnchorX = mouseEvent.getX();
//					dragContext.mouseAnchorY = mouseEvent.getY();
//					circle.setRadius(0);
//					circle.setStroke(Color.BISQUE);
//					circle.setStrokeWidth(3);
//					circle.setCenterX(dragContext.mouseAnchorX);
//					circle.setCenterY(dragContext.mouseAnchorY);
//					circle.setFill(Color.TRANSPARENT);
//				}else if(mouseEvent.isSecondaryButtonDown()) {
//					dragContext.mouseAnchorX = mouseEvent.getX();
//					dragContext.mouseAnchorY = mouseEvent.getY();
//				}
//			}
//		});
//
//		circle.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(final MouseEvent mouseEvent) {
//				if (mouseEvent.isPrimaryButtonDown()) {
//					double x = mouseEvent.getX() - dragContext.mouseAnchorX;
//					double y = mouseEvent.getY() - dragContext.mouseAnchorY;
//					double radius = Math.sqrt(Math.abs(x) + Math.abs(y));
//					dragContext.moveDistance = Math.pow(radius, 2);
//					circle.setRadius(dragContext.moveDistance);
//				} else if (mouseEvent.isSecondaryButtonDown()) {
//					circle.setTranslateX(mouseEvent.getX() - dragContext.mouseAnchorX);
//					circle.setTranslateY(mouseEvent.getY() - dragContext.mouseAnchorY);
//				}
//
//			}
//		});
		return imageView;
	}

	private static final class DragContext {
		public double mouseAnchorX;
		public double mouseAnchorY;
		public double initialTranslateX;
		public double initialTranslateY;
		public double moveDistance;
	}
}
