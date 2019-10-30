package bll;

public class Member_Is_At_Operation {
	private Member member;
	private Base base;
	private Operation operation;

	public Member_Is_At_Operation(Member member, Base base, Operation operation) {
		super();
		this.member = member;
		this.base = base;
		this.operation = operation;
	}

	public Member_Is_At_Operation() {
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
}