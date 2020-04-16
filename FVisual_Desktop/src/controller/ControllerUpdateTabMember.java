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
	private Button btnAddNewMember, btnSaveUpdatedMemberChanges;

	private AtomicBoolean isValidFirstname = new AtomicBoolean(false);
	private AtomicBoolean isValidLastname = new AtomicBoolean(false);
	private AtomicBoolean isValidRank = new AtomicBoolean(false);

	private final String CONST_FIRSTNAME_FOR_NEW_MEMBER = "Firstname for new Member";
	private final String CONST_LASTNAME_FOR_NEW_MEMBER = "Lastname for new Member";
	private final String CONST_RANK_FOR_NEW_MEMBER = "Rank for new Member";

	private ControllerUpdateFullBaseDialog controllerUpdateFullBaseDialog = null;
	private boolean isSingleUpdate;
	private String savedMemberRank;
	
	public ControllerUpdateTabMember(ControllerUpdateFullBaseDialog controllerUpdateFullBaseDialog,
			boolean isSingleUpdate) {
		this.controllerUpdateFullBaseDialog = controllerUpdateFullBaseDialog;
		this.isSingleUpdate = isSingleUpdate;
	}

	private Member currSelectedMember = null;
	private ObservableList<Member> obsListOfMemberData = null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.controllerUpdateFullBaseDialog.setSaveBtnDisability(true);
		this.btnSaveUpdatedMemberChanges.setDisable(true);

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
		ObservableList<Rank> obsListOfContractions = FXCollections
				.observableArrayList(RankManager.getInstance().getRanks());
		this.cbNewContraction.setItems(obsListOfContractions);
	}

	private void initListViewListeners() {
		this.lvMembers.setOnMouseClicked(event -> {
			Member selectedMember = this.lvMembers.getSelectionModel().getSelectedItem();
			if (selectedMember != null) {
				this.lbOldFirstname.setText(selectedMember.getFirstname());
				this.lbOldLastname.setText(selectedMember.getLastname());
				this.lbOldRank.setText(selectedMember.getRank().getContraction());
				this.currSelectedMember = selectedMember;
			} else {
				if (this.lbOldFirstname.getText().equals("No Member selected")
						&& this.lbOldLastname.getText().equals("No Member selected")
						&& this.lbOldRank.getText().equals("No Member selected") && selectedMember == null) {
					this.btnSaveUpdatedMemberChanges.setDisable(true);
				} else {
					this.lbOldFirstname.setText("No Member selected");
					this.lbOldLastname.setText("No Member selected");
					this.lbOldRank.setText("No Member selected");
				}
			}
		});
	}

	private void initTextFieldListeners() {
		this.tfNewFirstname.textProperty().addListener((obj, oldVal, newVal) -> {
			if (newVal.length() >= 3) {
				if (this.tfNewFirstname.getText().equals(this.CONST_FIRSTNAME_FOR_NEW_MEMBER)
						&& this.tfNewLastname.getText().equals(CONST_LASTNAME_FOR_NEW_MEMBER)
						&& String.valueOf(this.cbNewContraction.getSelectionModel().getSelectedItem())
								.equals(CONST_RANK_FOR_NEW_MEMBER)) {
					this.isValidFirstname.set(false);
					this.btnSaveUpdatedMemberChanges.setDisable(true);
					this.btnAddNewMember.setDisable(true);
					this.tfNewFirstname.setStyle("-fx-text-box-border: red;");
					this.tfNewFirstname.setStyle("-fx-focus-color: red;");
					this.controllerUpdateFullBaseDialog.setSaveBtnDisability(true);
				} else {
					this.isValidFirstname.set(true);
					this.btnSaveUpdatedMemberChanges.setDisable(false);
					this.tfNewFirstname.setStyle("-fx-text-box-border: green;");
					this.tfNewFirstname.setStyle("-fx-focus-color: green;");
					this.controllerUpdateFullBaseDialog.setSaveBtnDisability(false);

					if (this.btnAddNewMember.getText().equals("Add Member")
							&& this.lbOldFirstname.getText().equals("Change Firstname")) {
						if (!this.isSingleUpdate
								&& !this.tfNewFirstname.getText().equals(this.CONST_FIRSTNAME_FOR_NEW_MEMBER)
								&& !this.tfNewLastname.getText().equals(CONST_LASTNAME_FOR_NEW_MEMBER)
								&& !String.valueOf(this.cbNewContraction.getSelectionModel().getSelectedItem())
										.equals(CONST_RANK_FOR_NEW_MEMBER)) {
							this.btnAddNewMember.setDisable(false);
							this.btnAddNewMember.setText("Save Member");
						}
						this.btnSaveUpdatedMemberChanges.setDisable(true);
					} else if (this.btnAddNewMember.getText().equals("Save Member")
							&& this.lbOldFirstname.getText().equals("Change Firstname")) {
						this.btnSaveUpdatedMemberChanges.setDisable(true);
						if (!this.isSingleUpdate
								&& !this.tfNewFirstname.getText().equals(this.CONST_FIRSTNAME_FOR_NEW_MEMBER)
								&& !this.tfNewLastname.getText().equals(CONST_LASTNAME_FOR_NEW_MEMBER)
								&& !String.valueOf(this.cbNewContraction.getSelectionModel().getSelectedItem())
										.equals(CONST_RANK_FOR_NEW_MEMBER)) {
							this.btnAddNewMember.setDisable(false);
						}
					}
				}
			} else {
				this.isValidFirstname.set(false);
				this.btnSaveUpdatedMemberChanges.setDisable(true);
				this.tfNewFirstname.setStyle("-fx-text-box-border: red;");
				this.tfNewFirstname.setStyle("-fx-focus-color: red;");
				this.controllerUpdateFullBaseDialog.setSaveBtnDisability(true);

				if (this.btnAddNewMember.getText().equals("Save Member")) {
					if (!this.isSingleUpdate) {
						this.btnAddNewMember.setDisable(true);
					}
				} else {
					if (!this.isSingleUpdate
							&& this.tfNewFirstname.getText().equals(this.CONST_FIRSTNAME_FOR_NEW_MEMBER)
							&& this.tfNewLastname.getText().equals(CONST_LASTNAME_FOR_NEW_MEMBER)
							&& String.valueOf(this.cbNewContraction.getSelectionModel().getSelectedItem())
									.equals(CONST_RANK_FOR_NEW_MEMBER)) {
						this.btnAddNewMember.setDisable(false);
					} else {
						this.btnAddNewMember.setDisable(true);
					}
				}
			}
		});

		this.tfNewLastname.textProperty().addListener((obj, oldVal, newVal) -> {
			if (newVal.length() >= 3) {
				if (this.tfNewFirstname.getText().equals(this.CONST_FIRSTNAME_FOR_NEW_MEMBER)
						&& this.tfNewLastname.getText().equals(CONST_LASTNAME_FOR_NEW_MEMBER)
						&& String.valueOf(this.cbNewContraction.getSelectionModel().getSelectedItem())
								.equals(CONST_RANK_FOR_NEW_MEMBER)) {
					this.isValidLastname.set(false);
					this.btnSaveUpdatedMemberChanges.setDisable(true);
					this.btnAddNewMember.setDisable(true);
					this.tfNewLastname.setStyle("-fx-text-box-border: red;");
					this.tfNewLastname.setStyle("-fx-focus-color: red;");
					this.controllerUpdateFullBaseDialog.setSaveBtnDisability(true);
				} else {
					this.isValidLastname.set(true);
					this.btnSaveUpdatedMemberChanges.setDisable(false);
					this.tfNewLastname.setStyle("-fx-text-box-border: green;");
					this.tfNewLastname.setStyle("-fx-focus-color: green;");
					this.controllerUpdateFullBaseDialog.setSaveBtnDisability(false);

					if (this.btnAddNewMember.getText().equals("Add Member")
							&& this.lbOldLastname.getText().equals("Change Lastname")) {
						if (!this.isSingleUpdate
								&& !this.tfNewFirstname.getText().equals(this.CONST_FIRSTNAME_FOR_NEW_MEMBER)
								&& !this.tfNewLastname.getText().equals(CONST_LASTNAME_FOR_NEW_MEMBER)
								&& !String.valueOf(this.cbNewContraction.getSelectionModel().getSelectedItem())
										.equals(CONST_RANK_FOR_NEW_MEMBER)) {
							this.btnAddNewMember.setDisable(false);
							this.btnAddNewMember.setText("Save Member");
						}
						this.btnSaveUpdatedMemberChanges.setDisable(true);
					} else if (this.btnAddNewMember.getText().equals("Save Member")
							&& this.lbOldLastname.getText().equals("Change Lastname")) {
						this.btnSaveUpdatedMemberChanges.setDisable(true);
						if (!this.isSingleUpdate
								&& !this.tfNewFirstname.getText().equals(this.CONST_FIRSTNAME_FOR_NEW_MEMBER)
								&& !this.tfNewLastname.getText().equals(CONST_LASTNAME_FOR_NEW_MEMBER)
								&& !String.valueOf(this.cbNewContraction.getSelectionModel().getSelectedItem())
										.equals(CONST_RANK_FOR_NEW_MEMBER)) {
							this.btnAddNewMember.setDisable(false);
						}
					}
				}
			} else {
				this.isValidLastname.set(false);
				this.btnSaveUpdatedMemberChanges.setDisable(true);
				this.tfNewLastname.setStyle("-fx-text-box-border: red;");
				this.tfNewLastname.setStyle("-fx-focus-color: red;");
				this.controllerUpdateFullBaseDialog.setSaveBtnDisability(true);

				if (this.btnAddNewMember.getText().equals("Save Member")) {
					if (!this.isSingleUpdate) {
						this.btnAddNewMember.setDisable(true);
					}
				} else {
					if (!this.isSingleUpdate
							&& this.tfNewFirstname.getText().equals(this.CONST_FIRSTNAME_FOR_NEW_MEMBER)
							&& this.tfNewLastname.getText().equals(CONST_LASTNAME_FOR_NEW_MEMBER)
							&& String.valueOf(this.cbNewContraction.getSelectionModel().getSelectedItem())
									.equals(CONST_RANK_FOR_NEW_MEMBER)) {
						this.btnAddNewMember.setDisable(false);
					} else {
						this.btnAddNewMember.setDisable(true);
					}
				}
			}
		});
	}

	private void initComboboxListener() {
		this.cbNewContraction.getSelectionModel().selectedItemProperty().addListener((options, oldVal, newVal) -> {
			if (newVal != null) {
				if (this.tfNewFirstname.getText().equals(this.CONST_FIRSTNAME_FOR_NEW_MEMBER)
						&& this.tfNewLastname.getText().equals(CONST_LASTNAME_FOR_NEW_MEMBER)
						&& String.valueOf(this.cbNewContraction.getSelectionModel().getSelectedItem())
								.equals(CONST_RANK_FOR_NEW_MEMBER)) {
					this.isValidRank.set(false);
					this.btnSaveUpdatedMemberChanges.setDisable(true);
					this.btnAddNewMember.setDisable(true);
					this.cbNewContraction.setStyle("-fx-text-box-border: red;");
					this.cbNewContraction.setStyle("-fx-focus-color: red;");
					this.controllerUpdateFullBaseDialog.setSaveBtnDisability(true);
				} else {
					this.isValidRank.set(true);
					this.btnSaveUpdatedMemberChanges.setDisable(false);
					this.cbNewContraction.setStyle("-fx-text-box-border: green;");
					this.cbNewContraction.setStyle("-fx-focus-color: green;");
					this.controllerUpdateFullBaseDialog.setSaveBtnDisability(false);

					if (this.btnAddNewMember.getText().equals("Add Member")
							&& this.lbOldRank.getText().equals("Change Rank")) {
						if (!this.isSingleUpdate
								&& !this.tfNewFirstname.getText().equals(this.CONST_FIRSTNAME_FOR_NEW_MEMBER)
								&& !this.tfNewLastname.getText().equals(CONST_LASTNAME_FOR_NEW_MEMBER)
								&& !String.valueOf(this.cbNewContraction.getSelectionModel().getSelectedItem())
										.equals(CONST_RANK_FOR_NEW_MEMBER)) {
							this.btnAddNewMember.setDisable(false);
							this.btnAddNewMember.setText("Save Member");
						}
						this.btnSaveUpdatedMemberChanges.setDisable(true);
					} else if (this.btnAddNewMember.getText().equals("Save Member")
							&& this.lbOldRank.getText().equals("Change Rank")) {
						this.btnSaveUpdatedMemberChanges.setDisable(true);
						if (!this.isSingleUpdate
								&& !this.tfNewFirstname.getText().equals(this.CONST_FIRSTNAME_FOR_NEW_MEMBER)
								&& !this.tfNewLastname.getText().equals(CONST_LASTNAME_FOR_NEW_MEMBER)
								&& !String.valueOf(this.cbNewContraction.getSelectionModel().getSelectedItem())
										.equals(CONST_RANK_FOR_NEW_MEMBER)) {
							this.btnAddNewMember.setDisable(false);
						}
					}
				}
			} else {
				this.isValidRank.set(false);
				this.btnSaveUpdatedMemberChanges.setDisable(true);
				this.cbNewContraction.setStyle("-fx-text-box-border: red;");
				this.cbNewContraction.setStyle("-fx-focus-color: red;");
				this.controllerUpdateFullBaseDialog.setSaveBtnDisability(true);

				if (this.btnAddNewMember.getText().equals("Save Member")) {
					if (!this.isSingleUpdate) {
						this.btnAddNewMember.setDisable(true);
					}
				} else {
					if (!this.isSingleUpdate
							&& this.tfNewFirstname.getText().equals(this.CONST_FIRSTNAME_FOR_NEW_MEMBER)
							&& this.tfNewLastname.getText().equals(CONST_LASTNAME_FOR_NEW_MEMBER)
							&& String.valueOf(this.cbNewContraction.getSelectionModel().getSelectedItem())
									.equals(CONST_RANK_FOR_NEW_MEMBER)) {
						this.btnAddNewMember.setDisable(false);
					} else {
						this.btnAddNewMember.setDisable(true);
					}
				}
			}
		});
	}

	public void setListOfMembers(ArrayList<Member> listOfMembers) {
		this.obsListOfMemberData = FXCollections.observableArrayList();
		if (listOfMembers.size() == 1) {
			this.btnSaveUpdatedMemberChanges.setDisable(true);
			Member memberToUpdate = listOfMembers.get(0);
			this.obsListOfMemberData.add(memberToUpdate);
		} else {
			this.btnSaveUpdatedMemberChanges.setDisable(false);
			ArrayList<Member> list = MemberHandler.getInstance().getMemberListByBaseId();
			if (list.size() != 0) {
				for (int i = 0; i < list.size(); i++) {
					this.obsListOfMemberData.add(list.get(i));
				}
			}
		}
		this.lvMembers.setItems(this.obsListOfMemberData);
		if (this.obsListOfMemberData.size() != 0) {
			this.lvMembers.getSelectionModel().select(this.obsListOfMemberData.get(0));
			this.lbOldFirstname.setText(this.obsListOfMemberData.get(0).getFirstname());
			this.lbOldLastname.setText(this.obsListOfMemberData.get(0).getLastname());
			this.lbOldRank.setText(this.obsListOfMemberData.get(0).getRank().getContraction());
		}
	}

	public ArrayList<Member> getNewMemberData() {
		ArrayList<Member> retVal = new ArrayList<Member>();

		if (this.lvMembers.getItems().size() == 1) {
			Member member = new Member();
			
			member.setFirstname(this.lvMembers.getItems().get(0).getFirstname());
			member.setLastname(this.lvMembers.getItems().get(0).getLastname());

			if (this.isValidRank.get()) {
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
			for (int i = 0; i < this.lvMembers.getItems().size(); i++) {
				retVal.add(this.lvMembers.getItems().get(i));
			}
		}
		return retVal;
	}

	private Rank getRankFromOldTextfield() {
		Rank retVal = null;
		ArrayList<Rank> tempListOfRanks = RankHandler.getInstance().getRankList();

		for (int i = 0; i < tempListOfRanks.size(); i++) {
			if(this.lbOldRank.getText().equals("No Member selected")) {
				if (tempListOfRanks.get(i).getContraction().equals(this.savedMemberRank)) {
					retVal = tempListOfRanks.get(i);
					break;
				}
			} else {
				if (tempListOfRanks.get(i).getContraction().equals(this.lbOldRank.getText().trim())) {
					retVal = tempListOfRanks.get(i);
					break;
				}
			}
		}
		return retVal;
	}

	@FXML
	private void onClickBtnAddNewMember(ActionEvent event) {
		if(!this.isSingleUpdate) {
			if (this.btnAddNewMember.getText().equals("Add Member")) {
				this.lvMembers.getSelectionModel().clearSelection();

				this.lvMembers.setDisable(true);

				this.btnAddNewMember.setDisable(true);
				this.btnSaveUpdatedMemberChanges.setDisable(true);

				this.lbOldFirstname.setText("Change Firstname");
				this.lbOldLastname.setText("Change Lastname");
				this.lbOldRank.setText("Change Rank");

				this.tfNewFirstname.setText(CONST_FIRSTNAME_FOR_NEW_MEMBER);
				this.tfNewLastname.setText(CONST_LASTNAME_FOR_NEW_MEMBER);
				this.cbNewContraction.setPromptText(CONST_RANK_FOR_NEW_MEMBER);

				// this.lbStatusbar.setText("Note: Fill all Textfield to save Member");
			} else if (this.btnAddNewMember.getText().equals("Save Member")) {
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
				memberToCreate.setUsername(MemberHandler.getInstance().setGeneratedUsername(new Member(-1, this.tfNewFirstname.getText().trim(), this.tfNewLastname.getText().trim())));
				if(String.valueOf(this.cbNewContraction.getValue()).equals(CONST_RANK_FOR_NEW_MEMBER)) {
					memberToCreate.setRank(RankHandler.getInstance().getRankList().get(0));
				} else {
					memberToCreate.setRank(this.cbNewContraction.getValue());
				}
				memberToCreate.setPassword(memberToCreate.getUsername().toLowerCase());
				memberToCreate.setAdmin(false);
				memberToCreate.setMemberId(-1);
				memberToCreate.setUpdated(true);

				this.obsListOfMemberData.add(memberToCreate);
				this.lvMembers.refresh();

				// this.lbStatusbar.setText("Successfully added Member '" +
				// memberToCreate.getUsername() + "'");

				this.lvMembers.getSelectionModel().clearSelection();
				this.tfNewFirstname.setText("");
				this.tfNewLastname.setText("");
				this.cbNewContraction.getSelectionModel().clearSelection();
				this.controllerUpdateFullBaseDialog.setSaveBtnDisability(false);
			}
		}
	}

	@FXML
	private void onClickBtnSaveUpdatedMemberChanges(ActionEvent event) {
		ArrayList<Member> listOfAllCurrMembers = new ArrayList<Member>();

		for (int i = 0; i < this.obsListOfMemberData.size(); i++) {
			listOfAllCurrMembers.add(this.obsListOfMemberData.get(i));
		}

		Member currSelectedMember = this.lvMembers.getSelectionModel().getSelectedItem();
		
		if (currSelectedMember != null) {
			if (this.isValidFirstname.get()) {
				currSelectedMember.setFirstname(this.tfNewFirstname.getText().trim());
			} else {
				currSelectedMember.setFirstname(this.lbOldFirstname.getText());
			}
			if (this.isValidLastname.get()) {
				currSelectedMember.setLastname(this.tfNewLastname.getText().trim());
			} else {
				currSelectedMember.setLastname(this.lbOldFirstname.getText());
			}
			if (this.isValidRank.get()) {
				Rank selectedRank = this.cbNewContraction.getSelectionModel().getSelectedItem();
				currSelectedMember.setRank(selectedRank);
				currSelectedMember.setRankId(selectedRank.getRankId());
			}
			
			String tempFirstname = this.currSelectedMember.getFirstname();
			String tempLastname = this.currSelectedMember.getLastname();
			
			if(this.tfNewFirstname.getText() != "") {
				tempFirstname = this.tfNewFirstname.getText().trim();
			}
			if(this.tfNewLastname.getText() != "") {
				tempLastname = this.tfNewLastname.getText().trim();
			} 
			currSelectedMember.setUsername(MemberHandler.getInstance().setGeneratedUsername(new Member(-1, tempFirstname, tempLastname)));
			currSelectedMember.setUpdated(true);
			
			for (int i = 0; i < listOfAllCurrMembers.size(); i++) {
				if (listOfAllCurrMembers.get(i).getMemberId() == currSelectedMember.getMemberId()) {					
					listOfAllCurrMembers.remove(listOfAllCurrMembers.get(i));
					listOfAllCurrMembers.add(currSelectedMember);
				}
			}
			this.lvMembers.refresh();
			this.lvMembers.getSelectionModel().clearSelection();
			this.tfNewFirstname.setText("");
			this.tfNewLastname.setText("");
			this.cbNewContraction.getSelectionModel().clearSelection();
			this.savedMemberRank = this.lbOldRank.getText().trim();
			this.lbOldFirstname.setText("No Member selected");
			this.lbOldLastname.setText("No Member selected");
			this.lbOldRank.setText("No Member selected");
			this.controllerUpdateFullBaseDialog.setSaveBtnDisability(false);
			this.btnSaveUpdatedMemberChanges.setDisable(true);
			this.btnAddNewMember.setDisable(false);
		}
	}
}