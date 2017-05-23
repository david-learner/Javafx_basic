package tutorial;

import java.net.URL;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class CircleImagePatternStroke extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane rootPane = new BorderPane();
		Scene rootScene = new Scene(rootPane, 300, 300);
		URL url = getClass().getResource("/images/pattern01.jpg");		// path: src/images/pattern01.jpg
//		Image Source : http://www.vectortiles.com/wp-content/uploads/triangle-patterns-08.jpg
//		System.out.println(url.getPath());
//		System.out.println(url.toString());
		Image image = new Image(url.toString());
		
		ImagePattern ip = new ImagePattern(image);
		
		Circle circle = new Circle(150, 150, 70);
		circle.setFill(Color.TRANSPARENT);
		circle.setStroke(ip);
		circle.setStrokeWidth(20);
		
		rootPane.getChildren().add(circle);
		
		stage.setScene(rootScene);
		stage.show();
	}

}
