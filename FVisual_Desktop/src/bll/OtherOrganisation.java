package bll;

public class OtherOrganisation {
	private int otherOrganisationId;
	private String name;

	public OtherOrganisation(int otherOrganisationId, String name) {
		super();
		this.otherOrganisationId = otherOrganisationId;
		this.name = name;
	}

	public OtherOrganisation() {
		super();
	}

	public int getOtherOrganisationId() {
		return otherOrganisationId;
	}

	public void setOtherOrganisationId(int otherOrganisationId) {
		this.otherOrganisationId = otherOrganisationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}