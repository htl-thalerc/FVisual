package bll;

public class Rank {
	private String contraction;
	private String description;
	private int rankId;
	
	public static final String CONST_DB_ID = "ID";
	public static final String CONST_DB_CONTRACTION = "KUERZEL";
	public static final String CONST_DB_DESCRIPTION = "BEZEICHNUNG";
	
	private static Rank rankObj = null;
	
	public static Rank getInstance() {
		if(rankObj == null) {
			rankObj = new Rank();
		}
		return rankObj;
	}

	public Rank(int rankId, String contraction, String description) {
		super();
		this.rankId = rankId;
		this.contraction = contraction;
		this.description = description;
	}

	public Rank() {
		super();
	}
	
	public int getRankId() {
		return rankId;
	}

	public void setRankId(int rankId) {
		this.rankId = rankId;
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
		//return rankId + " - " + contraction + " - " + description;
		return contraction + " [" + description + "]";
	}
}