package app;

import bll.Member;
import javafx.fxml.FXMLLoader;

public class CentralHandler {
	private static CentralHandler helper;
	private Member member;

	public static CentralHandler getInstance() {
		if (helper == null) {
			helper = new CentralHandler();
		}
		return helper;
	}

	public static FXMLLoader loadFXML(final String path) {
		return new FXMLLoader(Main.class.getResource(path));
	}
	
	public Member getMember() {
		return this.member;
	}
	
	public void setMember (Member member) {
		this.member = member;
	}
}