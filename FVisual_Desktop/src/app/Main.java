package app;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/gui/Login.fxml"));
			Scene scene = new Scene(root,417,353);
			scene.getStylesheets().add(getClass().getResource("/gui/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("FVisual Management Tool");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}