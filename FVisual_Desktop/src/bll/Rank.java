package bll;

public class Rank {
	private int id;
	private String contraction;
	private String description;
	
	public Rank(int id, String contraction, String description) {
		super();
		this.id = id;
		this.contraction = contraction;
		this.description = description;
	}
	
	public Rank() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContraction() {
		return contraction;
	}

	public void setContraction(String contraction) {
		this.contraction = contraction;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return id + " - " + contraction + " - "+ description;
	}
}