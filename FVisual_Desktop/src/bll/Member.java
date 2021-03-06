package bll;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class Member {
	private int memberId;
	private String firstname;
	private String lastname;
	private String username;
	private String password;
	private boolean isAdmin;
	
	private int baseId;
	private Base base;
	private int rankId;
	private Rank rank;
	private int operationId;
	private Operation operation;
	
	private boolean isUpdated = false;
	
	private TextField tfFirstname;
	private TextField tfLastname;
	private ComboBox<Rank> cbRank;

	// DB Attribut names
	public static final String CONST_DB_MEMBERID = "ID";
	public static final String CONST_DB_FIRSTNAME = "VORNAME";
	public static final String CONST_DB_LASTNAME = "NACHNAME";
	public static final String CONST_DB_USERNAME = "USERNAME";
	public static final String CONST_DB_PASSWORD = "PASSWORD";
	public static final String CONST_DB_ISADMIN = "ISADMIN";
	public static final String CONST_DB_BASEID = "ID_STUETZPUNKT";
	public static final String CONST_DB_RANKID = "ID_DIENSTGRAD";
	public static final String CONST_DB_OPERATIONID = "ID_EINSATZ";
	public static final String CONST_DB_CONTRACTION = "KUERZEL";
	public static final String CONST_DB_DESCRIPTION = "BEZEICHNUNG";

	private static Member memberObj = null;

	public static Member getInstance() {
		if (memberObj == null) {
			memberObj = new Member();
		}
		return memberObj;
	}

	public Member(int memberId, Base base, Rank rank, Operation operation, String firstname, String lastname, String username, String password, boolean isAdmin) {
		super();
		this.memberId = memberId;
		this.base = base;
		this.rank = rank;
		this.operation = operation;
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
		this.isAdmin = isAdmin;
	}
	
	public Member(TextField tfFirstname, TextField tfLastname, ComboBox<Rank> cb) {
		this.tfFirstname = tfFirstname;
		this.tfLastname = tfLastname;
		this.cbRank = cb;
	}

	public Member() {
		super();
	}
	
	public Member(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public Member(int id, String firstname, String lastname) {
		this.memberId = id;
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public int getBaseId() {
		return baseId;
	}

	public void setBaseId(int baseId) {
		this.baseId = baseId;
	}

	public Base getBase() {
		return base;
	}

	public void setBase(Base base) {
		this.base = base;
	}

	public int getRankId() {
		return rankId;
	}

	public void setRankId(int rankId) {
		this.rankId = rankId;
	}

	public Rank getRank() {
		return rank;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
	}

	public int getOperationId() {
		return operationId;
	}

	public void setOperationId(int operationId) {
		this.operationId = operationId;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
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

	public ComboBox<Rank> getCbRank() {
		return cbRank;
	}

	public void setCbRank(ComboBox<Rank> cbRank) {
		this.cbRank = cbRank;
	}

	public boolean isUpdated() {
		return isUpdated;
	}

	public void setUpdated(boolean isUpdated) {
		this.isUpdated = isUpdated;
	}

	@Override
	public String toString() {
		return username + "[" + firstname + " " + lastname + "]";
	}

	public String toFullString() {
		return memberId + ", " + firstname + ", " + lastname + ", " + username + ", " + password + ", " + isAdmin + ", "
				+ base + ", " + rank.toFullString() + ", " + isUpdated + ", " + operation.toString();
//		return memberId + ", " + firstname + ", " + lastname + ", " + username + ", " + password + ", " + isAdmin + ", "
//				+ this.base.getBaseId() + ", " + this.rank.getRankId();
	}
}