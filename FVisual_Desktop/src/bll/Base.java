package bll;

public class Base {
	private int baseId;
	private String name;
	private String place;
	private int plz;
	private String street;
	private String houseNr;
	
	public Base(int baseId, String name, String place, int plz, String street, String houseNr) {
		super();
		this.baseId = baseId;
		this.name = name;
		this.place = place;
		this.plz = plz;
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

	public int getPlz() {
		return plz;
	}

	public void setPlz(int plz) {
		this.plz = plz;
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
}