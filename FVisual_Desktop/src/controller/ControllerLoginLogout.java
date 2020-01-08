package controller;

import handler.CentralHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import manager.LoginLogoutManager;

public class ControllerLoginLogout {
	@FXML
	private TextField txtUsername;
	@FXML
	private PasswordField txtPassword;
	@FXML
	private Button btnLogin;
	@FXML
	private Label lblMessage;
	private LoginLogoutManager dbHelper;

	@FXML
	public void initialize() {
		txtPassword.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				btnLogin.fire();
			}
		});

		txtUsername.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				btnLogin.fire();
			}
		});
	}

	@FXML
	void buttonLoginClicked(ActionEvent event) {
		try {
			dbHelper = LoginLogoutManager.newInstance();
			CentralHandler ch = CentralHandler.getInstance();

			if (txtUsername.getText().equals("") || txtPassword.getText().equals("")) {
				throw new Exception("Username or Password wrong");
			}

			// dbHelper.loginUser(new User(txtUsername.getText(), txtPassword.getText()))

			if (true) {
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(getClass().getResource("/gui/Mainframe.fxml"));
				Scene scene = new Scene(fxmlLoader.load(), 1100, 600);
				Stage stage = new Stage();
				stage.setScene(scene);
				Stage login = (Stage) ((Button) event.getSource()).getScene().getWindow();
				login.hide();
				// ch.setMember(dbHelper.getMemberByUsername(txtUsername.getText()));
				ControllerMainframe.setStage(stage);
				stage.showAndWait();
				txtUsername.clear();
				txtPassword.clear();
				login.show();
			} else {
				txtUsername.clear();
				txtPassword.clear();
				throw new Exception("Username or Password wrong");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			lblMessage.setText(ex.getMessage());
		}
	}
}