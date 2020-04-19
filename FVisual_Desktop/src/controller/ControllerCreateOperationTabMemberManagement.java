package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import bll.Base;
import bll.Member;
import handler.MemberHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.ListView;
import loader.BaseMemberLoader;

public class ControllerCreateOperationTabMemberManagement implements Initializable {
	@FXML
	private ListView<Member> lvAvailableMembers, lvSelectedMembers;
	@FXML
	private Button btnAddOneMember, btnRemoveOneMember, btnAddAllMembers, btnRemoveAllMembers;

	private ControllerCreateOperationManagement controllerCreateOperationManagement;
	private ObservableList<Member> obsListLVAvailableMembers, obsListLVSelectedMembers;
	private boolean isLVAvailableMembersSelected = false;
	private int nrOfTotalOrganisations = 0;

	public ControllerCreateOperationTabMemberManagement(
			ControllerCreateOperationManagement controllerCreateOperationManagement) {
		this.controllerCreateOperationManagement = controllerCreateOperationManagement;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.init();
	}

	private void init() {
		this.btnRemoveAllMembers.setDisable(true);
		this.btnAddOneMember.setDisable(true);
		this.btnRemoveOneMember.setDisable(true);
		this.initAvailableMembers();
		this.initSelecetedMembers();
		this.initListViewListeners();
	}

	private void initAvailableMembers() {
		Thread threadLoadMembersByBase = new Thread(loadMembersByBase());
		Task<Void> mainTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				threadLoadMembersByBase.start();
				threadLoadMembersByBase.join();
				return null;
			}
		};
		mainTask.setOnSucceeded(e -> {
			this.obsListLVAvailableMembers = FXCollections
					.observableArrayList(MemberHandler.getInstance().getMemberListByBaseId());
			this.lvAvailableMembers.setItems(this.obsListLVAvailableMembers);

			this.nrOfTotalOrganisations = this.lvAvailableMembers.getItems().size();
		});
		try {
			Thread threadLoading = new Thread(mainTask);
			threadLoading.start();
			threadLoading.join();
		} catch(InterruptedException ex) {
			ex.printStackTrace();
		}
	}
	
	private Task<Void> loadMembersByBase() {
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				Thread thread = new Thread(new BaseMemberLoader(new Base(1, null, null, 0, null, null)));
				thread.start();
				thread.join();
				return null;
			}
		};
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