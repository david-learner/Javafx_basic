package tutorial;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import javafx.scene.transform.Transform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SaveHighQuality extends Application {
	Circle circle;
	BorderPane imageBasePane;
	ImageView iv = new ImageView();
	StringBuilder oldStr = 	new StringBuilder();
	private static final int LIMIT = 10;

	public static void main(String[] args) {
		int i = 0;
		int b = 10;
		launch(args);
	}

	public void crop(Stage stage) {
		Circle circle2 = new Circle(250, 250, 100);
		circle2.setStroke(Color.RED); // 색을 투명으로 하면 Stroke이 제대로 지정되지 않음
		circle2.setStrokeWidth(30);
		imageBasePane.setStyle("-fx-background-color:white;"); // 클립된 원의 배경색
		imageBasePane.setClip(circle2);

		SnapshotParameters parameters = new SnapshotParameters();
		parameters.setFill(Color.TRANSPARENT);
		parameters.setTransform(Transform.scale(5, 5));
		WritableImage wi = imageBasePane.snapshot(parameters, null);

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Image");

		File file = fileChooser.showSaveDialog(stage);
		if (file != null) {
			try {
				ImageIO.write(SwingFXUtils.fromFXImage(wi, null), "png", file);
			} catch (IOException ex) {
				System.out.println(ex);
			}
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane rootPane = new BorderPane();
		Scene rootScene = new Scene(rootPane, 500, 500);
		rootScene.getStylesheets().add("/css/font.css");
		URL url = getClass().getResource("/images/pattern01.jpg"); // path:
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

		Font.loadFont(getClass().getResourceAsStream("/css/HS봄바람체ver2.ttf"), 40);
		Label textLabel = new Label();
		textLabel.getStyleClass().add("text3"); // css에서 text3에 해당하는 것 가져오기

		TextField textField = new TextField();
		//textField.setText("CSS");
		//textLabel.textProperty().bind(textField.textProperty());
		
		textField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				System.out.println("changed");
				oldStr.delete(0, oldStr.length());		//oldStr clear
				oldStr.append(textField.getText());
				textLabel.setText(oldStr.toString());
				if (oldStr.length() == 3) {
					oldStr.append("\n");
				}else if(oldStr.length() > 6) {
					oldStr.setLength(0);
				}
			}
			
		});

		

		textField.lengthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				if (newValue.intValue() > oldValue.intValue()) {
					// Check if the new character is greater than LIMIT
					if (textField.getText().length() >= LIMIT) {

						// if it's 11th character then just setText to previous
						// one
						textField.setText(textField.getText().substring(0, LIMIT));
					}
				}
			}
		});

		imageBasePane = new BorderPane();
		imageBasePane.setCenter(textLabel);
		imageBasePane.getChildren().add(circle);

		rootPane.setTop(saveBtn);
		rootPane.setCenter(imageBasePane);
		rootPane.setBottom(textField); // setBottom이랑 getChildren은 동시에 지정되지 않음

		stage.setScene(rootScene);
		stage.show();
	}

}
