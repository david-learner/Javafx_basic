package tutorial;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class TextOverlay extends Application {
	Circle circle;
	BorderPane imageBasePane;
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void crop(Stage stage) {
		Circle circle2 = new Circle(250, 250, 100);
		circle2.setStroke(Color.RED);		//색을 투명으로 하면 Stroke이 제대로 지정되지 않음
		circle2.setStrokeWidth(30);
		//imageBasePane.getChildren().add(circle2);
		imageBasePane.setStyle("-fx-background-color: transparent;");
		imageBasePane.setClip(circle2);
		
		SnapshotParameters parameters = new SnapshotParameters();		//Snapshot찍을 때 필요한 각종 옵션들?
		parameters.setFill(Color.TRANSPARENT);							//Snapshot 배경을 투명하게
		
		WritableImage wi = imageBasePane.snapshot(parameters, null);
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Image");
		
		File file = fileChooser.showSaveDialog(stage);
		if(file != null) {
			try {
				ImageIO.write(SwingFXUtils.fromFXImage(wi, null), "png", file);
			} catch(IOException ex) {
				System.out.println(ex);
			}
		}
		
	}

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane rootPane = new BorderPane();
		Scene rootScene = new Scene(rootPane, 500, 500);
		URL url = getClass().getResource("/images/pattern01.jpg");		// path: src/images/pattern01.jpg
//		Image Source : http://www.vectortiles.com/wp-content/uploads/triangle-patterns-08.jpg
		Image image = new Image(url.toString());
		ImagePattern ip = new ImagePattern(image);
		
		Button saveBtn = new Button("Save");
		saveBtn.setOnAction(e -> {
			crop(stage);
		});
		
		
		circle = new Circle(250, 250, 100);
		circle.setFill(Color.TRANSPARENT);
		circle.setStroke(ip);
		circle.setStrokeWidth(30);
		
		Text text = new Text();
		text.setFont(new Font(20));
		
		TextField textField = new TextField();
		textField.setText("프로필 문구");
		text.textProperty().bind(textField.textProperty());
		
		imageBasePane = new BorderPane();
		imageBasePane.setCenter(text);
		imageBasePane.getChildren().add(circle);
		
		
		rootPane.setTop(saveBtn);			
		rootPane.setCenter(imageBasePane);
		rootPane.setBottom(textField);			//setBottom이랑 getChildren은 동시에 지정되지 않음
		
		//rootPane.getChildren().add(circle);
		//rootPane.getChildren().add(text);
		//rootPane.getChildren().add(textField);
		
		stage.setScene(rootScene);
		stage.show();
	}

}
