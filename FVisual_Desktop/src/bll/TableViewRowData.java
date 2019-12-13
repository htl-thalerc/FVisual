package bll;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TableViewRowData {
	private Base base;
	private OperationVehicle vehicle;

	private Member member;
	private Rank rank;

	private TextField tfFirstname;
	private TextField tfLastname;
	private Label lbUsername;
	private ComboBox<Rank> cbRank;

	public TableViewRowData(Base base, OperationVehicle vehicle) {
		super();
		this.base = base;
		this.vehicle = vehicle;
	}

	public TableViewRowData(Member member, Rank rank) {
		this.member = member;
		this.rank = rank;
	}

	public TableViewRowData(OperationVehicle operationVehicle) {
		this.vehicle = operationVehicle;
	}

	public TableViewRowData(Member member) {
		this.member = member;
	}

	public TableViewRowData(TextField tfFirstname, TextField tfLastname, Label lbUsername, ComboBox<Rank> cb) {
		this.tfFirstname = tfFirstname;
		this.tfLastname = tfLastname;
		this.lbUsername = lbUsername;
		this.cbRank = cb;
	}

	public Base getBase() {
		return base;
	}

	public void setBase(Base base) {
		this.base = base;
	}

	public OperationVehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(OperationVehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Rank getRank() {
		return rank;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
	}

	public TextField getTfFirstname() {
		return tfFirstname;
	}

	public void setTfFirstname(TextField tfFirstname) {
		this.tfFirstname = tfFirstname;
	}

	public TextField getTfLastname() {
		return tfLastname;
	}

	public void setTfLastname(TextField tfLastname) {
		this.tfLastname = tfLastname;
	}

	public Label getLbUsername() {
		return lbUsername;
	}

	public void setLbUsername(Label lbUsername) {
		this.lbUsername = lbUsername;
	}

	public ComboBox<Rank> getCbRank() {
		return cbRank;
	}

	public void setCbRank(ComboBox<Rank> cbRank) {
		this.cbRank = cbRank;
	}

	@Override
	public String toString() {
		return tfFirstname.getText() + ", " + tfLastname.getText() + ", " + lbUsername.getText() + ", "
				+ cbRank.getSelectionModel().getSelectedItem().toString();
	}
}