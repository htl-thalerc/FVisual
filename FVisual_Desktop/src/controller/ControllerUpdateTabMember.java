package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

import bll.Member;
import bll.Rank;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import manager.RankManager;

public class ControllerUpdateTabMember implements Initializable {
	@FXML
	private ListView<Member> lvMembers;
	@FXML
	private Label lbOldFirstname, lbOldLastname, lbOldContraction, lbStatusbar;
	@FXML
	private TextField tfNewFirstname, tfNewLastname;
	@FXML
	private ComboBox<Rank> cbNewContraction;
	
	private AtomicBoolean isValidFirstname = new AtomicBoolean(false);
	private AtomicBoolean isValidLastname = new AtomicBoolean(false);
	private AtomicBoolean isValidContraction = new AtomicBoolean(false);
	
	private ControllerUpdateFullBaseDialog controllerUpdateFullBaseDialog = null;
	
	public ControllerUpdateTabMember(ControllerUpdateFullBaseDialog controllerUpdateFullBaseDialog) {
		this.controllerUpdateFullBaseDialog = controllerUpdateFullBaseDialog;
	}
	
	private Member currSelectedMember = null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.initComboboxContraction();
		this.initListViewListeners();
		this.initTextFieldListeners();
		this.initComboboxListener();
	}
	
	private void initComboboxContraction() {
		ObservableList<Rank> obsListOfContractions = FXCollections.observableArrayList(RankManager.getInstance().getRanks());
		this.cbNewContraction.setItems(obsListOfContractions);
	}
	
	private void initListViewListeners() {
		this.lvMembers.setOnMouseClicked(event -> {
			Member selectedMember = this.lvMembers.getSelectionModel().getSelectedItem();
			if(selectedMember != null) {
				this.lbOldFirstname.setText(selectedMember.getFirstname());
				this.lbOldLastname.setText(selectedMember.getLastname());
				this.lbOldContraction.setText(selectedMember.getRank().getContraction());
				this.currSelectedMember = selectedMember;
			} else {
				this.lbOldFirstname.setText("No Member selected");
				this.lbOldLastname.setText("No Member selected");
				this.lbOldContraction.setText("No Member selected");
			}
		});
	}
	
	private void initTextFieldListeners() {
		this.tfNewFirstname.textProperty().addListener((obj, oldVal, newVal) -> {
			if(newVal.length() >= 3) {
				this.isValidFirstname.set(true);
				this.tfNewFirstname.setStyle("-fx-text-box-border: green;");
				this.tfNewFirstname.setStyle("-fx-focus-color: green;");
				this.controllerUpdateFullBaseDialog.setSaveBtnDisability(false);
			} else {
				this.isValidFirstname.set(false);
				this.tfNewFirstname.setStyle("-fx-text-box-border: red;");
				this.tfNewFirstname.setStyle("-fx-focus-color: red;");
				this.controllerUpdateFullBaseDialog.setSaveBtnDisability(true);
			}
		});
		
		this.tfNewLastname.textProperty().addListener((obj, oldVal, newVal) -> {
			if(newVal.length() >= 3) {
				this.isValidLastname.set(true);
				this.tfNewLastname.setStyle("-fx-text-box-border: green;");
				this.tfNewLastname.setStyle("-fx-focus-color: green;");
				this.controllerUpdateFullBaseDialog.setSaveBtnDisability(false);
			} else {
				this.isValidLastname.set(false);
				this.tfNewLastname.setStyle("-fx-text-box-border: red;");
				this.tfNewLastname.setStyle("-fx-focus-color: red;");
				this.controllerUpdateFullBaseDialog.setSaveBtnDisability(true);
			}
		});
	}
	
	private void initComboboxListener() {
		this.cbNewContraction.getSelectionModel().selectedItemProperty().addListener((options, oldVal, newVal) -> {
			if(newVal != null) {
				this.isValidContraction.set(true);
				this.cbNewContraction.setStyle("-fx-text-box-border: green;");
				this.cbNewContraction.setStyle("-fx-focus-color: green;");
				this.controllerUpdateFullBaseDialog.setSaveBtnDisability(false);
			} else {
				this.isValidContraction.set(false);
				this.cbNewContraction.setStyle("-fx-text-box-border: red;");
				this.cbNewContraction.setStyle("-fx-focus-color: red;");
				this.controllerUpdateFullBaseDialog.setSaveBtnDisability(true);
			}
		}); 
	}
	
	public void setMemberData(Member oldMemberData) {
		ObservableList<Member> obsListOfMemberData = FXCollections.observableArrayList();
		obsListOfMemberData.add(oldMemberData);
		this.lvMembers.setItems(obsListOfMemberData);
		this.lvMembers.getSelectionModel().select(oldMemberData);
		this.lbOldFirstname.setText(oldMemberData.getFirstname());
		this.lbOldLastname.setText(oldMemberData.getLastname());
		this.lbOldContraction.setText(oldMemberData.getContraction());
	}

	public Member getNewMemberData() {
		Member member = new Member();
		
		if(this.isValidFirstname.get()) {
			member.setFirstname(this.tfNewFirstname.getText().trim());
		} else {
			member.setFirstname(this.lbOldFirstname.getText());
		}
		if(this.isValidLastname.get()) {
			member.setLastname(this.tfNewLastname.getText().trim());
		} else {
			member.setLastname(this.lbOldFirstname.getText());
		}
		if(this.isValidContraction.get()) {
			Rank selectedRank = this.cbNewContraction.getSelectionModel().getSelectedItem();
			member.setRank(selectedRank);
			member.setContraction(selectedRank.getContraction());
		} else {
			member.setRank(this.currSelectedMember.getRank());
			member.setContraction(this.currSelectedMember.getRank().getContraction());
		}
		return member;
	}
}