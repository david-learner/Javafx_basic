package tutorial;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ClipImageViewAsCircle extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		final Image image = new Image(
				"https://upload.wikimedia.org/wikipedia/commons/thumb/1/14/Gatto_europeo4.jpg/1024px-Gatto_europeo4.jpg");
		final ImageView imageView = new ImageView(image);
		ScrollPane scrollPane = new ScrollPane();
		Circle circle = new Circle(200, 200, 200);
		VBox vBox = new VBox();
		Scene rootScene = new Scene(vBox, 0, 0);

		scrollPane.setContent(imageView);
		vBox.getChildren().add(scrollPane);

		imageView.setClip(circle);

		stage.setScene(rootScene);
		stage.show();

		WritableImage wi = imageView.snapshot(new SnapshotParameters(), null);

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Image");

		File file = fileChooser.showSaveDialog(stage);
		if (file == null)
			return;

		try {
			ImageIO.write(SwingFXUtils.fromFXImage(wi, null), "png", file);
		} catch (IOException e) {
			// TODO: handle exception here
		}
	}
}
