package controller;

import dal.DatabaseHelperLoginLogout;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginLogoutController {
	DatabaseHelperLoginLogout dbHelper;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnLogin;
    
    @FXML
    private Label lblMessage;
    
    @SuppressWarnings("unused")
	@FXML
    private void onButtonLoginClicked(MouseEvent me) throws Exception {
    	try {
        	dbHelper = DatabaseHelperLoginLogout.newInstance();
        	if(txtUsername.getText().equals("") || txtPassword.getText().equals("")) {
        		throw new Exception("Username or Password wrong");
        	}
        	
        	//dbHelper.loginUser(new User(txtUsername.getText(), txtPassword.getText()))
        	
        	if(true) {          
        		FXMLLoader fxmlLoader = new FXMLLoader();
    			fxmlLoader.setLocation(getClass().getResource("/gui/Mainframe.fxml"));
    			Scene scene = new Scene(fxmlLoader.load(), 1100, 600);
    			Stage stage = new Stage();
    			stage.setScene(scene);
    			Stage login = (Stage)((Button)me.getSource()).getScene().getWindow();
    			login.hide();
    			ControllerMainframe.setStage(stage);
    			//CentralHandler.getInstance().setMember(dbHelper.getMemberByUsername(txtUsername.getText()));
    			stage.showAndWait();
        		txtUsername.clear();
        		txtPassword.clear();
    			login.show();
        	}else {
        		txtUsername.clear();
        		txtPassword.clear();
        		throw new Exception("Username or Password wrong");
        	}
    	}catch(Exception ex) {
    		lblMessage.setText(ex.getMessage());
    	}
    }
}
