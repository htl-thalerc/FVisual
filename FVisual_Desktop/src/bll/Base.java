package bll;

import javafx.scene.control.CheckBox;

public class Base implements Comparable<Base> {
	private int baseId;
	private String name;
	private String place;
	private int postCode;
	private String street;
	private String houseNr;

	private CheckBox selection;

	public Base(int baseId, String name, String place, int postCode, String street, String houseNr) {
		super();
		this.baseId = baseId;
		this.name = name;
		this.place = place;
		this.postCode = postCode;
		this.street = street;
		this.houseNr = houseNr;
	}

	public Base(CheckBox selection, int baseId, String name, String place, int postCode, String street,
			String houseNr) {
		super();
		this.selection = selection;
		this.baseId = baseId;
		this.name = name;
		this.place = place;
		this.postCode = postCode;
		this.street = street;
		this.houseNr = houseNr;
	}

	public int getBaseId() {
		return baseId;
	}

	public void setBaseId(int baseId) {
		this.baseId = baseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public int getPostCode() {
		return postCode;
	}

	public void setPostCode(int postCode) {
		this.postCode = postCode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHouseNr() {
		return houseNr;
	}

	public void setHouseNr(String houseNr) {
		this.houseNr = houseNr;
	}

	public CheckBox getSelection() {
		return selection;
	}

	@Override
	public int compareTo(Base obj) {
		return this.name.compareTo(obj.name);
	}

	@Override
	public String toString() {
		return this.baseId + ", " + this.name + ", " + this.place + ", " + this.postCode + ", " + this.street + ", "
				+ this.houseNr;
	}
}