package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class ControllerThreadProgressBarDialog implements Initializable {
	@FXML 
	private Label lbStatusbar;
	@FXML
	private ProgressBar progressBar;
	
	public ControllerThreadProgressBarDialog() {
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.progressBar.setStyle("-fx-accent: red;");
		this.lbStatusbar.setText("Initializing Components");
	}
	
	public void unbindProgressBar() {
		this.progressBar.progressProperty().unbind();
	}
	
	public void bindProgressBarOnTask(Task<Void> task) {
		this.progressBar.progressProperty().bind(task.progressProperty());
	}
	
	public void setLabelText(String value) {
		this.lbStatusbar.setText(value);
	}
}