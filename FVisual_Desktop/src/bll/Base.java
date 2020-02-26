package bll;

import javafx.scene.control.CheckBox;

public class Base implements Comparable<Base> {
	private int baseId;
	private String name;
	private String place;
	private int postCode;
	private String street;
	private String houseNr;
	
	private boolean isUpdated = false;
	
	private CheckBox selection;
	//DB Attribut names
	public static final String CONST_DB_BASEID = "ID";
	public static final String CONST_DB_NAME = "NAME";
	public static final String CONST_DB_PLACE = "ORT";
	public static final String CONST_DB_POSTCODE = "PLZ";
	public static final String CONST_DB_STREET = "STRASSE";
	public static final String CONST_DB_HOUSENR = "HAUSNR";	
	
	private static Base baseObj = null;
	
	public static Base getInstance() {
		if(baseObj == null) {
			baseObj = new Base();
		}
		return baseObj;
	}

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
		this.selection.setId("checkboxBase" + this.baseId);
		this.baseId = baseId;
		this.name = name;
		this.place = place;
		this.postCode = postCode;
		this.street = street;
		this.houseNr = houseNr;
	}
	
	public Base(CheckBox selection, Base base) {
		this.selection = selection;
		this.baseId = base.getBaseId();
		this.name = base.getName();
		this.place = base.getPlace();
		this.postCode = base.getPostCode();
		this.street = base.getStreet();
		this.houseNr = base.getHouseNr();
	}
	
	public Base() {
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
	
	public boolean isUpdated() {
		return isUpdated;
	}

	public void setUpdated(boolean isUpdated) {
		this.isUpdated = isUpdated;
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