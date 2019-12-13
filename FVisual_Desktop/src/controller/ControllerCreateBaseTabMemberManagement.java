package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

import bll.Member;
import bll.Rank;
import bll.TableViewRowData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import manager.BaseManager;
import manager.MemberManager;
import manager.RankManager;

public class ControllerCreateBaseTabMemberManagement implements Initializable {
	@FXML
	private ListView<Member> lvAvailableMembers, lvSelectedMembers;
	@FXML
	private Button btnAddOneMember, btnRemoveOneMember, btnAddAllMembers, btnRemoveAllMembers, btnAddNewMember,
			btnSaveNewMember;
	@FXML
	private TableView<TableViewRowData> tvAddNewMember;

	private ControllerCreateBaseManagement controllerCreateBase;
	private ObservableList<Member> obsListLVAvailableMembers, obsListLVSelectedMembers;
	private ObservableList<TableViewRowData> obsListTVAddNewMember;
	private boolean isLVAvailableMembersSelected = false;
	private int nrOfTotalOrganisations = 0;

	private AtomicBoolean isValidFirstname = new AtomicBoolean(false);
	private AtomicBoolean isValidLastname = new AtomicBoolean(false);
	private AtomicBoolean isValidRank = new AtomicBoolean(false);

	private TextField tfFirstname = null;
	private TextField tfLastname = null;
	private Label lbUsername = null;

	public ControllerCreateBaseTabMemberManagement(ControllerCreateBaseManagement controllerCreateBase) {
		this.controllerCreateBase = controllerCreateBase;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.init();
	}

	private void init() {
		this.btnRemoveAllMembers.setDisable(true);
		this.btnAddOneMember.setDisable(true);
		this.btnRemoveOneMember.setDisable(true);
		this.btnSaveNewMember.setDisable(true);
		this.initAvailableMembers();
		this.initSelecetedMembers();
		this.initListViewListeners();
		this.initTableViewColumns();
	}

	private void initAvailableMembers() {
		ArrayList<Member> list = new ArrayList<Member>();

		list.add(new Member(1, "Sandro", "Assek", "asseks", "lauch", true, null, null, null));
		list.add(new Member(2, "Florian", "Graf", "graff", "lauch", true, null, null, null));
		list.add(new Member(3, "Christoph", "Thaler", "thalerc", "lauch", true, null, null, null));
		list.add(new Member(4, "Andreas", "Drabe", "drabosea", "lauch", true, null, null, null));
		list.add(new Member(5, "User", "1", "user1", "lauch", false, null, null, null));
		list.add(new Member(6, "User", "2", "user2", "lauch", false, null, null, null));
		list.add(new Member(7, "User", "3", "user3", "lauch", false, null, null, null));

		this.obsListLVAvailableMembers = FXCollections.observableArrayList(list);
		this.lvAvailableMembers.setItems(this.obsListLVAvailableMembers);

		this.nrOfTotalOrganisations = this.lvAvailableMembers.getItems().size();
	}

	private void initSelecetedMembers() {
		this.obsListLVSelectedMembers = FXCollections.observableArrayList();
		this.lvSelectedMembers.setItems(this.obsListLVSelectedMembers);
	}

	private void initListViewListeners() {
		this.lvAvailableMembers.setOnMouseClicked(event -> {
			if (this.lvAvailableMembers.getSelectionModel().getSelectedItem() != null) {
				this.btnAddOneMember.setDisable(false);
				this.btnRemoveOneMember.setDisable(true);
				this.isLVAvailableMembersSelected = true;
			}
		});

		this.lvSelectedMembers.setOnMouseClicked(event -> {
			if (this.lvSelectedMembers.getSelectionModel().getSelectedItem() != null) {
				this.btnAddOneMember.setDisable(true);
				this.btnRemoveOneMember.setDisable(false);
				this.isLVAvailableMembersSelected = false;
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void initTableViewColumns() {
		TableColumn<TableViewRowData, String> colNameBlock = new TableColumn<TableViewRowData, String>("Name");
		TableColumn<TableViewRowData, TextField> colFirstname = new TableColumn<TableViewRowData, TextField>(
				"Firstname");
		TableColumn<TableViewRowData, TextField> colLastname = new TableColumn<TableViewRowData, TextField>("Lastname");
		TableColumn<TableViewRowData, Label> colUsername = new TableColumn<TableViewRowData, Label>("Username");
		TableColumn<TableViewRowData, ComboBox<Rank>> colRank = new TableColumn<TableViewRowData, ComboBox<Rank>>(
				"Rank");

		colFirstname.setCellValueFactory(new PropertyValueFactory<TableViewRowData, TextField>("tfFirstname"));
		colLastname.setCellValueFactory(new PropertyValueFactory<TableViewRowData, TextField>("tfLastname"));
		colUsername.setCellValueFactory(new PropertyValueFactory<TableViewRowData, Label>("lbUsername"));

		colRank.setCellValueFactory(new PropertyValueFactory<TableViewRowData, ComboBox<Rank>>("cbRank"));

		colNameBlock.getColumns().addAll(colFirstname, colLastname, colUsername);
		this.tvAddNewMember.getColumns().addAll(colNameBlock, colRank);
		this.tvAddNewMember.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}

	@FXML
	private void onClickBtnAddOneMember(ActionEvent event) {
		if (this.isLVAvailableMembersSelected
				&& this.lvAvailableMembers.getSelectionModel().getSelectedItem() != null) {
			Member selectedMember = this.lvAvailableMembers.getSelectionModel().getSelectedItem();

			this.lvSelectedMembers.getSelectionModel().clearSelection();
			this.lvAvailableMembers.getSelectionModel().selectNext();

			this.btnAddOneMember.setDisable(true);

			this.obsListLVAvailableMembers.remove(selectedMember);
			this.lvAvailableMembers.refresh();

			this.obsListLVSelectedMembers.add(selectedMember);
			this.lvSelectedMembers.refresh();

			if (this.lvSelectedMembers.getItems().size() == this.nrOfTotalOrganisations) {
				this.btnAddAllMembers.setDisable(true);
				this.btnRemoveAllMembers.setDisable(false);
			}
		}
	}

	@FXML
	private void onClickBtnRemoveOneMember(ActionEvent event) {
		if (!this.isLVAvailableMembersSelected
				&& this.lvSelectedMembers.getSelectionModel().getSelectedItem() != null) {
			Member selectedMember = this.lvSelectedMembers.getSelectionModel().getSelectedItem();

			this.lvSelectedMembers.getSelectionModel().selectNext();
			this.lvAvailableMembers.getSelectionModel().clearSelection();

			this.btnRemoveOneMember.setDisable(true);

			this.obsListLVSelectedMembers.remove(selectedMember);
			this.lvSelectedMembers.refresh();

			this.obsListLVAvailableMembers.add(selectedMember);
			this.lvAvailableMembers.refresh();

			if (this.lvAvailableMembers.getItems().size() == this.nrOfTotalOrganisations) {
				this.btnAddAllMembers.setDisable(false);
				this.btnRemoveAllMembers.setDisable(true);
			}
		}
	}

	@FXML
	private void onClickBtnAddAllMembers(ActionEvent event) {
		this.obsListLVSelectedMembers.addAll(this.obsListLVAvailableMembers);
		this.lvSelectedMembers.setItems(this.obsListLVSelectedMembers);

		this.obsListLVAvailableMembers.clear();
		this.lvAvailableMembers.getItems().clear();

		this.btnAddOneMember.setDisable(true);
		this.btnRemoveOneMember.setDisable(true);

		this.btnAddAllMembers.setDisable(true);
		this.btnRemoveAllMembers.setDisable(false);
	}

	@FXML
	private void onClickBtnRemoveAllMembers(ActionEvent event) {
		this.obsListLVAvailableMembers.addAll(this.obsListLVSelectedMembers);
		this.lvAvailableMembers.setItems(this.obsListLVAvailableMembers);

		this.obsListLVSelectedMembers.clear();
		this.lvSelectedMembers.getItems().clear();

		this.btnAddOneMember.setDisable(true);
		this.btnRemoveOneMember.setDisable(true);

		this.btnAddAllMembers.setDisable(false);
		this.btnRemoveAllMembers.setDisable(true);
	}

	@FXML
	private void onClickBtnAddNewMember(ActionEvent event) {
		this.btnAddNewMember.setDisable(true);
		this.tvAddNewMember.setEditable(true);
		this.obsListTVAddNewMember = FXCollections.observableArrayList();

		this.tfFirstname = new TextField();
		this.tfFirstname.setEditable(true);
		this.tfLastname = new TextField();
		this.tfLastname.setEditable(true);
		this.lbUsername = new Label("Not available");

		ObservableList<Rank> obsListOfRanks = FXCollections.observableArrayList(RankManager.getInstance().getRanks());
		ComboBox<Rank> cbRanks = new ComboBox<Rank>();
		cbRanks.setItems(obsListOfRanks);

		this.obsListTVAddNewMember.add(new TableViewRowData(tfFirstname, tfLastname, lbUsername, cbRanks));
		this.tvAddNewMember.setItems(this.obsListTVAddNewMember);

		this.initTextFieldListeners(tfFirstname, tfLastname);
		this.initComboboxListener(cbRanks);
	}

	private void initTextFieldListeners(TextField tfFirstname, TextField tfLastname) {
		tfFirstname.textProperty().addListener((obj, oldVal, newVal) -> {
			if (newVal.length() >= 3) {
				this.isValidFirstname.set(true);
				tfFirstname.setStyle("-fx-text-box-border: green;");
				tfFirstname.setStyle("-fx-focus-color: green;");
				if (this.isValidLastname.get() && this.tfLastname.getText().substring(0, 5) != ""
						&& this.tfFirstname.getText().substring(0, 1) != "") {
					this.lbUsername.setText(this.tfLastname.getText().substring(0, 5).toLowerCase()
							+ this.tfFirstname.getText().substring(0, 1).toLowerCase());
				} else {
					this.lbUsername.setText("Not available");
				}
			} else {
				this.isValidFirstname.set(false);
				tfFirstname.setStyle("-fx-text-box-border: red;");
				tfFirstname.setStyle("-fx-focus-color: red;");
			}
			this.checkInput(this.isValidFirstname, this.isValidLastname, this.isValidRank);
		});

		tfLastname.textProperty().addListener((obj, oldVal, newVal) -> {
			if (newVal.length() >= 3) {
				this.isValidLastname.set(true);
				tfLastname.setStyle("-fx-text-box-border: green;");
				tfLastname.setStyle("-fx-focus-color: green;");
				if (this.isValidFirstname.get() && this.tfLastname.getText().substring(0, 5) != ""
						&& this.tfFirstname.getText().substring(0, 1) != "") {
					this.lbUsername.setText(this.tfLastname.getText().substring(0, 5).toLowerCase()
							+ this.tfFirstname.getText().substring(0, 1).toLowerCase());
				} else {
					this.lbUsername.setText("Not available");
				}
			} else {
				this.isValidLastname.set(false);
				tfLastname.setStyle("-fx-text-box-border: red;");
				tfLastname.setStyle("-fx-focus-color: red;");
			}
			this.checkInput(this.isValidFirstname, this.isValidLastname, this.isValidRank);
		});
	}

	private void initComboboxListener(ComboBox<Rank> cbRanks) {
		cbRanks.getSelectionModel().selectedItemProperty().addListener((options, oldVal, newVal) -> {
			if (newVal != null) {
				this.isValidRank.set(true);
				cbRanks.setStyle("-fx-text-box-border: green;");
				cbRanks.setStyle("-fx-focus-color: green;");
			} else {
				this.isValidRank.set(false);
				cbRanks.setStyle("-fx-text-box-border: red;");
				cbRanks.setStyle("-fx-focus-color: red;");
			}
			this.checkInput(this.isValidFirstname, this.isValidLastname, this.isValidRank);
		});
	}

	private void checkInput(AtomicBoolean paramIsValidFirstname, AtomicBoolean paramIsValidLastname,
			AtomicBoolean paramIsValidRank) {
		if (paramIsValidFirstname.get() && paramIsValidLastname.get() && paramIsValidRank.get()) {
			this.btnSaveNewMember.setDisable(false);
		} else {
			this.btnSaveNewMember.setDisable(true);
		}
	}

	@FXML
	private void onClickBtnSaveNewMember(ActionEvent event) {
		TableViewRowData memberData = this.tvAddNewMember.getItems().get(0);

		Member member = new Member();
		// ToDo: Post member to Database
		// Doesn't work bec. selected base is not created and therefore there is no
		// baseId
		// MemberManager.getInstance().addMemberToBase(this.controllerCreateBase.getCreatedBase().getBaseId(),
		// member);

		member.setFirstname(memberData.getMember().getFirstname());
		member.setLastname(memberData.getMember().getLastname());
		member.setUsername(memberData.getMember().getUsername());
		member.setBase(null);
		member.setRank(member.getRank());

		this.tvAddNewMember.getItems().remove(0);
		this.tvAddNewMember.refresh();
		this.obsListLVAvailableMembers.add(member);
		this.lvAvailableMembers.refresh();

		System.out.println(memberData.toString());

		this.btnAddNewMember.setDisable(false);
	}

	public List<Member> getMembersToCreate() {
		return this.lvSelectedMembers.getItems();
	}

	public void reset() {
		this.obsListLVAvailableMembers.clear();
		this.obsListLVSelectedMembers.clear();
		this.lvAvailableMembers.refresh();
		this.lvSelectedMembers.refresh();
		this.init();
	}
}