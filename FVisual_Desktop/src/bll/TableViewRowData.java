package bll;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TableViewRowData {
	private Base base;
	private OperationVehicle vehicle;

	private Member member;
	private Rank rank;

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
}