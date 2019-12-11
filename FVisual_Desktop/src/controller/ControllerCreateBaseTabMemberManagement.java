package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import bll.Member;
import bll.Rank;
import bll.TableViewRowData;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ControllerCreateBaseTabMemberManagement implements Initializable {
	@FXML
	private ListView<Member> lvAvailableMembers;
	@FXML
	private ListView<Member> lvSelectedMembers;
	@FXML
	private Button btnAddOneMember;
	@FXML
	private Button btnRemoveOneMember;
	@FXML
	private Button btnAddAllMembers;
	@FXML
	private Button btnRemoveAllMembers;
	@FXML
	private Button btnAddNewMember;
	@FXML
	private TableView<TableViewRowData> tvAddNewMember;

	private ControllerCreateBaseManagement controllerCreateBase;
	private ObservableList<Member> obsListLVAvailableMembers;
	private ObservableList<Member> obsListLVSelectedMembers;
	private ObservableList<TableViewRowData> obsListTVAddNewMember;
	private boolean isLVAvailableMembersSelected = false;
	private int nrOfTotalOrganisations = 0;

	public ControllerCreateBaseTabMemberManagement(ControllerCreateBaseManagement controllerCreateBase) {
		this.controllerCreateBase = controllerCreateBase;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.init();
	}
	
	private void init() {
		this.btnAddOneMember.setDisable(true);
		this.btnRemoveOneMember.setDisable(true);
		this.initAvailableMembers();
		this.initSelecetedMembers();
		this.initListViewListeners();
		this.initTableViewColumns();
	}
	
	private void initAvailableMembers() {
		ArrayList<Member> list = new ArrayList<Member>();
		
		list.add(new Member(1, "Sandro", "Assek", "asseks", "lauch", true, null, null));
		list.add(new Member(2, "Florian", "Graf", "graff", "lauch", true, null, null));
		list.add(new Member(3, "Christoph", "Thaler", "thalerc", "lauch", true, null, null));
		list.add(new Member(4, "Andreas", "Drabe", "drabosea", "lauch", true, null, null));
		list.add(new Member(5, "User", "1", "user1", "lauch", false, null, null));
		list.add(new Member(6, "User", "2", "user2", "lauch", false, null, null));
		list.add(new Member(7, "User", "3", "user3", "lauch", false, null, null));
		
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
		TableColumn<TableViewRowData, String> colFirstname = new TableColumn<TableViewRowData, String>("Firstname");
		TableColumn<TableViewRowData, String> colLastname = new TableColumn<TableViewRowData, String>("Lastname");
		TableColumn<TableViewRowData, String> colUsername = new TableColumn<TableViewRowData, String>("Username");
		TableColumn<TableViewRowData, String> colRank = new TableColumn<TableViewRowData, String>("Rank");
		
		colFirstname.setCellValueFactory(new PropertyValueFactory<TableViewRowData, String>("firstname"));
		colLastname.setCellValueFactory(new PropertyValueFactory<TableViewRowData, String>("lastname"));
		colUsername.setCellValueFactory(new PropertyValueFactory<TableViewRowData, String>("username"));
		
		colRank.setCellValueFactory(new PropertyValueFactory<TableViewRowData, String>("rank"));
	    
		colFirstname.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMember().getFirstname()));
		colLastname.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMember().getLastname()));
	    colUsername.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMember().getUsername()));
	    
	    colRank.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRank().getDescription()));
	    
	    colNameBlock.getColumns().addAll(colFirstname, colLastname, colUsername);
	    this.tvAddNewMember.getColumns().addAll(colNameBlock, colRank);
	    this.tvAddNewMember.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}

	@FXML
	private void onClickBtnAddOneMember(ActionEvent event) {
		if (this.isLVAvailableMembersSelected && this.lvAvailableMembers.getSelectionModel().getSelectedItem() != null) {
			Member selectedMember = this.lvAvailableMembers.getSelectionModel()
					.getSelectedItem();
			
			this.lvSelectedMembers.getSelectionModel().clearSelection();
			this.lvAvailableMembers.getSelectionModel().selectNext();
			
			this.btnAddOneMember.setDisable(true);
//			if((this.lvAvailableOperationVehicles.getSelectionModel().getSelectedIndex() + 1) < this.lvAvailableOperationVehicles.getItems().size()) {
//				this.lvAvailableOperationVehicles.getSelectionModel().selectNext();
//			} else {getCreatedVehicleData
//				this.lvAvailableOperationVehicles.getSelectionModel().selectFirst();
//			}
			
			this.obsListLVAvailableMembers.remove(selectedMember);
			this.lvAvailableMembers.refresh();

			this.obsListLVSelectedMembers.add(selectedMember);
			this.lvSelectedMembers.refresh();
			
			if(this.lvSelectedMembers.getItems().size() == this.nrOfTotalOrganisations) {
				this.btnAddAllMembers.setDisable(true);
				this.btnRemoveAllMembers.setDisable(false);
			}
		}
	}

	@FXML
	private void onClickBtnRemoveOneMember(ActionEvent event) {
		if (!this.isLVAvailableMembersSelected && this.lvSelectedMembers.getSelectionModel().getSelectedItem() != null) {
			Member selectedMember = this.lvSelectedMembers.getSelectionModel()
					.getSelectedItem();
			
			this.lvSelectedMembers.getSelectionModel().selectNext();
			this.lvAvailableMembers.getSelectionModel().clearSelection();
			
			this.btnRemoveOneMember.setDisable(true);
//			if((this.lvSelectedOrganisation.getSelectionModel().getSelectedIndex() + 1) < this.lvSelectedOrganisation.getItems().size()) {
//				this.lvSelectedOrganisation.getSelectionModel().selectNext();	
//			} else {
//				this.lvSelectedOrganisation.getSelectionModel().selectFirst();
//			}
			
			this.obsListLVSelectedMembers.remove(selectedMember);
			this.lvSelectedMembers.refresh();

			this.obsListLVAvailableMembers.add(selectedMember);
			this.lvAvailableMembers.refresh();
			
			if(this.lvAvailableMembers.getItems().size() == this.nrOfTotalOrganisations) {
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
		this.obsListTVAddNewMember = FXCollections.observableArrayList();
		this.obsListTVAddNewMember.add(new TableViewRowData(new Member(), new Rank()));
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