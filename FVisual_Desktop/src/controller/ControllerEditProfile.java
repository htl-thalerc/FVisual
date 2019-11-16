package controller;

import java.beans.Visibility;
import java.io.IOException;

import bll.Member;
import handler.CentralHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class ControllerEditProfile {
	@FXML
	private Label lblErrorPassword;
	
    @FXML
    private Pane paneEditProfile;

    @FXML
    private Pane paneEditProfileHead;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    @FXML
    private PasswordField txtNewPassword;
    
    @FXML
    private PasswordField txtRepeatPassword;
    
    @FXML
    private CheckBox ckAdminRights;

    private Popup popup;
    private static Stage stage;
    private Group group;
    
    static void setStage(Stage stage2) {
    	stage = stage2;
    }
    
    @FXML
    public void initialize() {
    	initPopup();
    	initFields();
    }
    
    private void initPopup() {        
        popup = new Popup(); 
    	
        Button buttonContinue = new Button("Continue"); 
        Button buttonCancel = new Button("Cancel");
        Rectangle pane = new Rectangle(750, 100, Color.WHITE);
        Label label = new Label("Do you really want to change your Admin rights? You will be logged out and cant go back to be an admin."); 
        
    	pane.setStroke(Color.LIGHTGRAY);
        
        pane.setLayoutX(0);
        pane.setLayoutY(0);
        
        label.setStyle(" -fx-background-color: white;");
        label.setStyle("-fx-font-size: 15px ;");
        label.setLayoutX(10);
        label.setLayoutY(15);
        
        buttonCancel.setId("Cancel");
        buttonContinue.setId("Continue");
        
        buttonContinue.setLayoutX(10);
        buttonContinue.setLayoutY(60);
        
        buttonCancel.setLayoutX(90);
        buttonCancel.setLayoutY(60);
   
        popup.getContent().addAll(pane,label,buttonContinue,buttonCancel); 
   
        buttonContinue.setOnAction(new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
            	// change User in Database
            	CentralHandler.getInstance().setMember(null);
            	stage.close();
            } 
        });
        buttonCancel.setOnAction(new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            {   
            	popup.hide();
            	group.getChildren().remove(1);
            } 
        });
    }
    
    private void initFields() {
    	CentralHandler ch = CentralHandler.getInstance();
    	
    	Member m = ch.getMember();
    	
    	//txtFirstName.setText(m.getFirstname());
    	//txtLastName.setText(m.getLastname());
    	ckAdminRights.setSelected(true);
    }
    
    @FXML
	private void onEditProfileClicked(MouseEvent me) {
    	if(!ckAdminRights.isSelected()) {
        	Rectangle rect = new Rectangle(476, 657, Color.GRAY);
        	rect.opacityProperty().set(0.5);
        	
        	group = new Group();
        	group.getChildren().addAll(stage.getScene().getRoot(), rect);
        	
        	Scene scene = new Scene(group, 476, 657);
        	stage.setScene(scene);
        	stage.show();
        	
    		popup.show(stage);
    	}
    	else {
    		// edit firstname and Lastname in database
    		stage.close();
    	}
	}
	
    @FXML
	private void onChangePasswordClicked(MouseEvent me) {
		if(txtNewPassword.getText() != txtRepeatPassword.getText() || txtNewPassword.getText().equals("") || txtRepeatPassword.getText().equals("")) {
			lblErrorPassword.setText("Passwords are not equal or empty");
		}
		else {
			// change Password in database
		}
	}
}
