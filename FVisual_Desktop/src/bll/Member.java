package bll;

public class Member {
	private int memberId;
	private String firstname;
	private String lastname;
	private String username;
	private String password;
	private boolean isAdmin;
	private Base base;
	private Rank rank;

	public Member(int memberId, String firstname, String lastname, String username, String password, boolean isAdmin,
			Base base, Rank rank) {
		super();
		this.memberId = memberId;
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
		this.isAdmin = isAdmin;
		this.base = base;
		this.rank = rank;
	}

	public Member() {
		super();
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

	public Base getBase() {
		return base;
	}

	public void setBase(Base base) {
		this.base = base;
	}

	public Rank getRank() {
		return rank;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
	}

	@Override
	public String toString() {
		return username + "[" + firstname + " " + lastname + "]";
	}
}