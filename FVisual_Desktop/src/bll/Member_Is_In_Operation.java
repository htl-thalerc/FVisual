package bll;

public class Member_Is_In_Operation {
	private Member member;
	private Base base;
	private Operation operation;
	
	private int memberId;
	private int baseId;
	private int operationId;
	
	//DB Attr name
	public static final String CONST_DB_MEMBERID = "ID_MEMBER";
	public static final String CONST_DB_BASEID = "ID_STUETZPUNKT";
	public static final String CONST_DB_OPERATIONID = "ID_EINSATZ";

	public Member_Is_In_Operation(Member member, Base base, Operation operation) {
		super();
		this.member = member;
		this.base = base;
		this.operation = operation;
	}

	public Member_Is_In_Operation() {
		super();
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Base getBase() {
		return base;
	}

	public void setBase(Base base) {
		this.base = base;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	
	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public int getBaseId() {
		return baseId;
	}

	public void setBaseId(int baseId) {
		this.baseId = baseId;
	}

	public int getOperationId() {
		return operationId;
	}

	public void setOperationId(int operationId) {
		this.operationId = operationId;
	}

	@Override
	public String toString() {
		return member.toString() + ", " + operation.toString() + ", " + base.toString();
	}
}