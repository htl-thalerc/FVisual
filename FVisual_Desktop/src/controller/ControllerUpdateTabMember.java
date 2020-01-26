package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

import bll.Member;
import bll.OperationVehicle;
import bll.Rank;
import handler.CentralUpdateHandler;
import handler.MemberHandler;
import handler.RankHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import manager.RankManager;

public class ControllerUpdateTabMember implements Initializable {
	@FXML
	private ListView<Member> lvMembers;
	@FXML
	private Label lbOldFirstname, lbOldLastname, lbOldRank, lbStatusbar;
	@FXML
	private TextField tfNewFirstname, tfNewLastname;
	@FXML
	private ComboBox<Rank> cbNewContraction;
	@FXML
	private Button btnAddNewMember, btnSaveChanges;
	
	private AtomicBoolean isValidFirstname = new AtomicBoolean(false);
	private AtomicBoolean isValidLastname = new AtomicBoolean(false);
	private AtomicBoolean isValidRank = new AtomicBoolean(false);
	
	private ControllerUpdateFullBaseDialog controllerUpdateFullBaseDialog = null;
	
	public ControllerUpdateTabMember(ControllerUpdateFullBaseDialog controllerUpdateFullBaseDialog) {
		this.controllerUpdateFullBaseDialog = controllerUpdateFullBaseDialog;
	}
	
	private Member currSelectedMember = null;
	private ObservableList<Member> obsListOfMemberData = null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.obsListOfMemberData = FXCollections.observableArrayList();
		this.btnSaveChanges.setDisable(true);
		
		this.initListViewMembers();
		this.initComboboxRank();
		this.initListViewListeners();
		this.initTextFieldListeners();
		this.initComboboxListener();
	}
	
	private void initListViewMembers() {
		ObservableList<Member> obsListOfMembers = FXCollections.observableArrayList();
		this.lvMembers.setItems(obsListOfMembers);
	}
	
	private void initComboboxRank() {
		ObservableList<Rank> obsListOfContractions = FXCollections.observableArrayList(RankManager.getInstance().getRanks());
		this.cbNewContraction.setItems(obsListOfContractions);
	}
	
	private void initListViewListeners() {
		this.lvMembers.setOnMouseClicked(event -> {
			Member selectedMember = this.lvMembers.getSelectionModel().getSelectedItem();
			if(selectedMember != null) {
				this.lbOldFirstname.setText(selectedMember.getFirstname());
				this.lbOldLastname.setText(selectedMember.getLastname());
				this.lbOldRank.setText(selectedMember.getRank().getContraction());
				this.currSelectedMember = selectedMember;
			} else {
				this.lbOldFirstname.setText("No Member selected");
				this.lbOldLastname.setText("No Member selected");
				this.lbOldRank.setText("No Member selected");
			}
		});
	}
	
	private void initTextFieldListeners() {
		this.tfNewFirstname.textProperty().addListener((obj, oldVal, newVal) -> {
			if(newVal.length() >= 4) {
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
			if(newVal.length() >= 4) {
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
				this.isValidRank.set(true);
				this.cbNewContraction.setStyle("-fx-text-box-border: green;");
				this.cbNewContraction.setStyle("-fx-focus-color: green;");
				this.controllerUpdateFullBaseDialog.setSaveBtnDisability(false);
			} else {
				this.isValidRank.set(false);
				this.cbNewContraction.setStyle("-fx-text-box-border: red;");
				this.cbNewContraction.setStyle("-fx-focus-color: red;");
				this.controllerUpdateFullBaseDialog.setSaveBtnDisability(true);
			}
		}); 
	}
	
	public void setListOfMembers(ArrayList<Member> listOfMembers) {
		this.obsListOfMemberData.clear();
		
		if(listOfMembers.size() == 1) {
			this.btnSaveChanges.setDisable(true);
			Member memberToUpdate = listOfMembers.get(0);
			this.obsListOfMemberData.add(memberToUpdate);
		} else {
			this.btnSaveChanges.setDisable(false);
			ArrayList<Member> list = MemberHandler.getInstance().getMemberListByBaseId();
			if(list.size() != 0) {
				for(int i=0;i<list.size();i++) {
					this.obsListOfMemberData.add(list.get(0));
				}	
			}
		}
		this.lvMembers.setItems(obsListOfMemberData);
		if(this.obsListOfMemberData.size() != 0) {
			this.lvMembers.getSelectionModel().select(obsListOfMemberData.get(0));
			this.lbOldFirstname.setText(obsListOfMemberData.get(0).getFirstname());
			this.lbOldLastname.setText(obsListOfMemberData.get(0).getLastname());
			this.lbOldRank.setText(obsListOfMemberData.get(0).getRank().getContraction());
		}
	}

	public ArrayList<Member> getNewMemberData() {	
		ArrayList<Member> retVal = new ArrayList<Member>();
		
		if(this.lvMembers.getItems().size() == 1) {
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
			if(this.isValidRank.get()) {
				Rank selectedRank = this.cbNewContraction.getSelectionModel().getSelectedItem();
				member.setRank(selectedRank);
				member.setRankId(selectedRank.getRankId());
			} else {
				Rank tempRank = this.getRankFromOldTextfield();
				member.setRank(tempRank);
				member.setRankId(tempRank.getRankId());
			}
			retVal.add(member);
		} else {
			for(int i=0;i< this.lvMembers.getItems().size();i++) {
				retVal.add(this.lvMembers.getItems().get(i));
			}
		}
		return retVal;
	}
	
	private Rank getRankFromOldTextfield() {
		Rank retVal = null;
		ArrayList<Rank> tempListOfRanks = RankHandler.getInstance().getRankList();
		
		for(int i=0;i<tempListOfRanks.size();i++) {
			if(tempListOfRanks.get(i).getContraction().equals(this.lbOldRank.getText().trim())) {
				retVal = tempListOfRanks.get(i);
				break;
			}
		}
		
		return retVal;
	}
	
	@FXML
	private void onClickBtnAddNewMember(ActionEvent event) {
		if(this.btnAddNewMember.getText().equals("Add Member")) {
			this.btnAddNewMember.setText("Save Member");
			this.lvMembers.setDisable(true);
			this.btnAddNewMember.setDisable(true);
			
			this.lbOldFirstname.setText("No Member selected");
			this.lbOldLastname.setText("No Member selected");
			this.lbOldRank.setText("No Member selected");
			
			this.lbStatusbar.setText("Note: Fill all Textfield to save Member");
		} else if(this.btnAddNewMember.getText().equals("Save Member")) {
			this.btnAddNewMember.setText("Add Member");
			this.lvMembers.setDisable(false);
			this.btnAddNewMember.setDisable(false);
			
			this.lbOldFirstname.setText("No Memberdata available");
			this.lbOldLastname.setText("No Memberdata available");
			this.lbOldRank.setText("No Memberdata available");
			
			Member memberToCreate = new Member();
			
			memberToCreate.setBase(CentralUpdateHandler.getInstance().getCurrBaseToUpdate());
			memberToCreate.setBaseId(memberToCreate.getBase().getBaseId());
			memberToCreate.setFirstname(this.tfNewFirstname.getText().trim());
			memberToCreate.setLastname(this.tfNewLastname.getText().trim());
			memberToCreate.setUsername(this.tfNewLastname.getText().trim().substring(0, 4) + "" + this.tfNewFirstname.getText().trim().substring(0,1));
			memberToCreate.setRank(this.cbNewContraction.getValue());
			memberToCreate.setPassword(memberToCreate.getUsername().toLowerCase());
			memberToCreate.setAdmin(false);
			
			ObservableList<Member> listOfMembers = this.lvMembers.getItems();
			listOfMembers.add(memberToCreate);
			this.lvMembers.refresh();
			
			this.lbStatusbar.setText("Successfully added Member '" + memberToCreate.getUsername() + "'");
		}
	}
	
	@FXML private void onClickBtnSaveChanges(ActionEvent event) {
		ArrayList<Member> listOfAllCurrMembers = new ArrayList<Member>();
		
		for(int i=0;i<this.obsListOfMemberData.size();i++) {
			listOfAllCurrMembers.add(this.obsListOfMemberData.get(i));
		}
		
		Member currSelectedMember = this.lvMembers.getSelectionModel().getSelectedItem();
		
		if(currSelectedMember != null) {
			if(this.isValidFirstname.get()) {
				currSelectedMember.setFirstname(this.tfNewFirstname.getText().trim());
			} else {
				currSelectedMember.setFirstname(this.lbOldFirstname.getText());
			}
			if(this.isValidLastname.get()) {
				currSelectedMember.setLastname(this.tfNewLastname.getText().trim());
			} else {
				currSelectedMember.setLastname(this.lbOldFirstname.getText());
			}
			if(this.isValidRank.get()) {
				Rank selectedRank = this.cbNewContraction.getSelectionModel().getSelectedItem();
				currSelectedMember.setRank(selectedRank);
				currSelectedMember.setRankId(selectedRank.getRankId());
			}
			
			for(int i=0;i<listOfAllCurrMembers.size();i++) {
				if(listOfAllCurrMembers.get(i).getMemberId() == currSelectedMember.getMemberId()) {
					listOfAllCurrMembers.remove(listOfAllCurrMembers.get(i));
					listOfAllCurrMembers.add(currSelectedMember);
				}
			}
			
			this.lvMembers.refresh();
		}
	}
}