package handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import app.Main;
import bll.Base;
import bll.ClassTypes;
import bll.Member;
import bll.OperationVehicle;
import bll.OtherOrganisation;
import bll.Rank;
import javafx.fxml.FXMLLoader;
import util.PropertyManager;

public class CentralHandler {
	private static CentralHandler helper;

	public static final String CONST_AUTHORIZATION = "authorization";
	public static final String CONST_METADATA = "metadata";

	public static final String CONST_BASE_URL = "stuetzpunkte";
	public static final String CONST_OPERATION_URL = "einsaetze";

	public static final String CONST_MEMBER_URL = "mitglieder";
	public static final String CONST_VEHICLE = "fahrzeuge";
	public static final String CONST_OTHER_ORGANISATION = "andere_organisation";
	public static final String CONST_RANK = "dienstgrade";

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

	public String getRessource() {
		PropertyManager pm = null;
		try {
			pm = PropertyManager.getInstance();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pm.readProperty("http") + "://" + pm.readProperty("ipAddress") + ":" + pm.readProperty("port");
	}

	public String getHeaderAuthorization() {
		return "{'username':'heinzi','flow':'management'}";
	}

	public Member getMember() {
		return this.member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public HashMap<String, String> setDatabaseFieldAttributes(ClassTypes objType, ArrayList<String> listOfAttributes) {
		HashMap<String, String> retVal = new HashMap<String, String>();
		switch (objType) {
		case BASE:
			for (int i = 0; i < listOfAttributes.size(); i++) {
				switch (listOfAttributes.get(i)) {
				case "baseId":
					retVal.put(listOfAttributes.get(i), Base.CONST_DB_BASEID);
					break;
				case "name":
					retVal.put(listOfAttributes.get(i), Base.CONST_DB_NAME);
					break;
				case "place":
					retVal.put(listOfAttributes.get(i), Base.CONST_DB_PLACE);
					break;
				case "postCode":
					retVal.put(listOfAttributes.get(i), Base.CONST_DB_POSTCODE);
					break;
				case "street":
					retVal.put(listOfAttributes.get(i), Base.CONST_DB_STREET);
					break;
				case "houseNr":
					retVal.put(listOfAttributes.get(i), Base.CONST_DB_HOUSENR);
					break;
				}
			}
		case MEMBER:
			for (int i = 0; i < listOfAttributes.size(); i++) {
				switch (listOfAttributes.get(i)) {
				case "memberId":
					retVal.put(listOfAttributes.get(i), Member.CONST_DB_MEMBERID);
					break;
				case "firstname":
					retVal.put(listOfAttributes.get(i), Member.CONST_DB_FIRSTNAME);
					break;
				case "lastname":
					retVal.put(listOfAttributes.get(i), Member.CONST_DB_LASTNAME);
					break;
				case "username":
					retVal.put(listOfAttributes.get(i), Member.CONST_DB_USERNAME);
					break;
				case "password":
					retVal.put(listOfAttributes.get(i), Member.CONST_DB_PASSWORD);
					break;
				case "isAdmin":
					retVal.put(listOfAttributes.get(i), Member.CONST_DB_ISADMIN);
					break;
				case "base":
					retVal.put(listOfAttributes.get(i), Member.CONST_DB_BASEID);
					break;
				case "rank":
					retVal.put(listOfAttributes.get(i), Member.CONST_DB_RANKID);
					break;
				}
			}
			break;
		case OPERATION:
			break;
		case OPERATION_CODE:
			break;
		case OPERATION_TYPE:
			break;
		case OPERATION_VEHICLE:
			for (int i = 0; i < listOfAttributes.size(); i++) {
				switch (listOfAttributes.get(i)) {
				case "operationVehicleId":
					retVal.put(listOfAttributes.get(i), OperationVehicle.CONST_DB_ID);
					break;
				case "description":
					retVal.put(listOfAttributes.get(i), OperationVehicle.CONST_DB_DESCRIPTION);
					break;
				case "base":
					retVal.put(listOfAttributes.get(i), OperationVehicle.CONST_DB_BASEID);
					break;
				case "baseId":
					retVal.put(listOfAttributes.get(i), OperationVehicle.CONST_DB_BASEID);
					break;
				}
			}
			break;
		case OTHER_ORG:
			for (int i = 0; i < listOfAttributes.size(); i++) {
				switch (listOfAttributes.get(i)) {
				case "otherOrganisationId":
					retVal.put(listOfAttributes.get(i), OtherOrganisation.CONST_DB_ID);
					break;
				case "name":
					retVal.put(listOfAttributes.get(i), OtherOrganisation.CONST_DB_NAME);
					break;
				}
			}
			break;
		case RANK:
			for (int i = 0; i < listOfAttributes.size(); i++) {
				switch (listOfAttributes.get(i)) {
				case "rankId":
					retVal.put(listOfAttributes.get(i), Rank.CONST_DB_ID);
					break;
				case "contraction":
					retVal.put(listOfAttributes.get(i), Rank.CONST_DB_CONTRACTION);
					break;
				case "description":
					retVal.put(listOfAttributes.get(i), Rank.CONST_DB_DESCRIPTION);
					break;
				}
			}
			break;
		}

		return retVal;
	}

	public String getHeaderMetadataString(HashMap<String, String> mainMetadataMap,
			HashMap<ClassTypes, HashMap<String, String>> subMetadata) {
		int nrOfAttributes = 0;
		String headerMetadataString = "[{";

		for (Entry<String, String> key : mainMetadataMap.entrySet()) {
			headerMetadataString += "'" + key.getKey() + "':'" + key.getValue() + "'";
			nrOfAttributes++;
			if (nrOfAttributes != mainMetadataMap.size()) {
				headerMetadataString += ", ";
			}
		}

		if (subMetadata != null) {
			for (Entry<ClassTypes, HashMap<String, String>> key : subMetadata.entrySet()) {
				switch (key.getKey()) {
				case BASE:
					String baseId = null;
					HashMap<String, String> subMetadataBase = subMetadata.get(ClassTypes.BASE);
					for (Entry<String, String> currBaseAttr : subMetadataBase.entrySet()) {
						if (currBaseAttr.getKey().equals("baseId")) {
							baseId = Member.CONST_DB_BASEID;
						}
					}
					if (baseId != null) {
						headerMetadataString += ", 'base':{'baseId':'" + baseId + "'}";
					}
					break;
				case MEMBER:
					break;
				case OPERATION:
					break;
				case OPERATION_CODE:
					break;
				case OPERATION_TYPE:
					break;
				case OPERATION_VEHICLE:
					break;
				case OTHER_ORG:
					break;
				case RANK:
					String rankId = null;
					HashMap<String, String> subMetadataRank = subMetadata.get(ClassTypes.RANK);
					for (Entry<String, String> currRankAttr : subMetadataRank.entrySet()) {
						if (currRankAttr.getKey().equals("rankId")) {
							rankId = Member.CONST_DB_RANKID;
						}
					}
					if (rankId != null) {
						headerMetadataString += ", 'rank':{'rankId':'" + rankId + "'}";
					}
					break;
				default:
					break;
				}
			}
		}
		headerMetadataString += "}]";
		headerMetadataString = headerMetadataString.replace("'", "\"");
		System.out.println(headerMetadataString);
		return headerMetadataString;
	}

	public void mergeFullMemberObject(boolean isBaselessMemberList) {
		// Add base to MemberObj
		ArrayList<Member> tempListOfMembers = null;

		if (isBaselessMemberList) {
			tempListOfMembers = MemberHandler.getInstance().getBaselessMemberList();
		} else {
			tempListOfMembers = MemberHandler.getInstance().getMemberList();
		}
		ArrayList<Base> tempListOfBases = BaseHandler.getInstance().getBaseList();
		ArrayList<Rank> tempListOfRanks = RankHandler.getInstance().getRankList();

		for (int i = 0; i < tempListOfMembers.size(); i++) {
			for (int j = 0; j < tempListOfBases.size(); j++) {
				if (tempListOfMembers.get(i).getBase().getBaseId() == tempListOfBases.get(j).getBaseId()) {
					tempListOfMembers.get(i).setBase(tempListOfBases.get(j));
				}
			}
		}
		// Add rank to MemberObj
		for (int i = 0; i < tempListOfMembers.size(); i++) {
			for (int j = 0; j < tempListOfRanks.size(); j++) {
				if (tempListOfMembers.get(i).getRank().getRankId() == tempListOfRanks.get(j).getRankId()) {
					tempListOfMembers.get(i).setRank(tempListOfRanks.get(j));
				}
			}
		}
	}

	public void mergeFullVehicleObject() {
		ArrayList<OperationVehicle> tempListOfVehciles = OperationVehicleHandler.getInstance().getVehicleList();
		ArrayList<Base> tempListOfBases = BaseHandler.getInstance().getBaseList();
		// add base to vehicleobj
		for (int i = 0; i < tempListOfVehciles.size(); i++) {
			for (int j = 0; j < tempListOfBases.size(); j++) {
				if (tempListOfVehciles.get(i).getBase().getBaseId() == tempListOfBases.get(j).getBaseId()) {
					tempListOfVehciles.get(i).setBase(tempListOfBases.get(j));
				}
			}
		}
	}
}