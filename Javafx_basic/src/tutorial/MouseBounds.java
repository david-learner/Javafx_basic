package tutorial;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MouseBounds extends Application {
	Stage primaryStage;
	double offsetX;
	double offsetY;
	double deltaX;
	double deltaY;
	double prevCenterX;
	double prevCenterY;
	double circleCenterX;
	double circleCenterY;
	double circleRadius = 0;
	Circle circle = new Circle();
	ImageView imageView;
	Image image;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		primaryStage.setTitle("MouseBounds");
		VBox v = new VBox();
		Group root = new Group();
		Scene scene = new Scene(v, 400, 400);
		Button button = new Button("Save");

		image = new Image(
				"https://upload.wikimedia.org/wikipedia/commons/thumb/1/14/Gatto_europeo4.jpg/1024px-Gatto_europeo4.jpg");
		imageView = new ImageView(image);

		v.getChildren().add(button);
		v.getChildren().add(root);
		root.getChildren().add(imageView);
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				crop(circle.getBoundsInParent());
			}
		});

		scene.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown()) {
					System.out.println("Scene Pressed");

					/* remove old circle */
					root.getChildren().remove(circle);
					circleRadius = 0;

					circle.setStroke(Color.VIOLET);
					circle.setFill(Color.TRANSPARENT);
					circle.setStrokeWidth(2);
					offsetX = event.getX();
					offsetY = event.getY();

					circle.setLayoutX(offsetX);
					circle.setLayoutY(offsetY);
					circle.setRadius(0);

					root.getChildren().add(circle);

					System.out.println(event.getSceneX() + ", " + event.getSceneY());
					// System.out.println(event.getX() +", "+ event.getY());
				}
			}
		});

		scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				System.out.println("Scene Dragged");

				circleRadius = (event.getX() - offsetX) / 2;
				
				circle.setCenterX((event.getX() - offsetX) / 2);
				circle.setCenterY((event.getY() - offsetY) / 2);
				circle.setRadius(circleRadius);
				
				event.consume();

				// System.out.println(event.getSceneX() +", "+
				// event.getSceneY());
			}
		});

		// circle.setOnMousePressed(new EventHandler<MouseEvent>() {
		// @Override
		// public void handle(MouseEvent event) {
		// if(event.isPrimaryButtonDown()) {
		// deltaX = event.getX();
		// deltaY = event.getY();
		// prevCenterX = circle.getCenterX();
		// prevCenterY = circle.getCenterY();
		// }
		// }
		// });

		// circle.setOnMouseDragged(new EventHandler<MouseEvent>() {
		// @Override
		// public void handle(MouseEvent event) {
		// if (event.isSecondaryButtonDown()) {
		// circle.setCenterX(prevCenterX + (event.getX() - deltaX));
		// circle.setCenterY(prevCenterY + (event.getY() - deltaY));
		// }
		// }
		// });

		/* Event Filter */
		circle.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				System.out.println("Circle Filter MOUSE_PRESSED");
				deltaX = event.getX();
				deltaY = event.getY();
				prevCenterX = circle.getCenterX();
				prevCenterY = circle.getCenterY();
			}
		});

		circle.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				System.out.println("Circle Filter MOUSE_DRAGGED");
				circleCenterX = prevCenterX + (event.getX() - deltaX);
				circleCenterY = prevCenterY + (event.getY() - deltaY);
				
				circle.setCenterX(circleCenterX);
				circle.setCenterY(circleCenterY);
				
				System.out.println("Dragged " + circle.getCenterX() + " " + circle.getCenterY() + " " + circle.getRadius());
				event.consume();

			}

		});

		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public void crop(Bounds bounds) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Image");

		File file = fileChooser.showSaveDialog(primaryStage);
		if (file == null)
			return;

		int width = (int) bounds.getWidth();
		int height = (int) bounds.getHeight();

		SnapshotParameters parameters = new SnapshotParameters();
		parameters.setFill(Color.TRANSPARENT);
		parameters.setViewport(new Rectangle2D(bounds.getMinX(), bounds.getMinY(), width, height));
		
		Circle c = new Circle(circleCenterX, circleCenterY, circleRadius);
		c.setStroke(Color.WHITE);
		c.setStrokeWidth(2);
		System.out.println(c.getCenterX() + " " + c.getCenterY() + " " + c.getRadius());
		
		WritableImage wi = new WritableImage(width, height);
		imageView.setClip(c);
		imageView.snapshot(parameters, wi);

		BufferedImage bufImageARGB = SwingFXUtils.fromFXImage(wi, null);
		BufferedImage bufImageRGB = new BufferedImage(bufImageARGB.getWidth(), bufImageARGB.getHeight(),
				BufferedImage.OPAQUE);

		Graphics2D graphics = bufImageRGB.createGraphics();
		graphics.drawImage(bufImageARGB, 0, 0, null);

		try {

			ImageIO.write(bufImageRGB, "jpg", file);

			System.out.println("Image saved to " + file.getAbsolutePath());

		} catch (IOException e) {
			e.printStackTrace();
		}

		graphics.dispose();
	}
}
