package handler;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import app.Main;
import bll.Base;
import bll.ClassTypes;
import bll.Member;
import bll.Operation;
import bll.OperationCode;
import bll.OperationType;
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
	public static final String CONST_OTHER_ORGANISATION = "andere_organisationen";
	public static final String CONST_RANK = "dienstgrade";
	public static final String CONST_OPERATION_TYPE = "einsatzarten";
	public static final String CONST_OPERATION_CODE = "einsatzcodes";

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

	public HashMap<String, String> setMetadataMap(ClassTypes classtype, ArrayList<String> additinalAttr) {
		HashMap<String, String> retVal = new HashMap<String, String>();
		ArrayList<String> listOfCurrAttr = new ArrayList<String>();

		switch (classtype) {
		case BASE:
			listOfCurrAttr.add("baseId");
			listOfCurrAttr.add("name");
			listOfCurrAttr.add("place");
			listOfCurrAttr.add("street");
			listOfCurrAttr.add("postCode");
			listOfCurrAttr.add("houseNr");
			break;
		case MEMBER:
			listOfCurrAttr.add("memberId");
			listOfCurrAttr.add("username");
			listOfCurrAttr.add("firstname");
			listOfCurrAttr.add("lastname");
			break;
		case OPERATION:
			listOfCurrAttr.add("operationId");
			listOfCurrAttr.add("address");
			listOfCurrAttr.add("postCode");
			listOfCurrAttr.add("title");
			listOfCurrAttr.add("shortDescription");
			//listOfCurrAttr.add("time");
			break;
		case OPERATION_CODE:
			listOfCurrAttr.add("operationCodeId");
			listOfCurrAttr.add("code");
			break;
		case OPERATION_TYPE:
			listOfCurrAttr.add("operationTypeId");
			listOfCurrAttr.add("description");
			break;
		case OPERATION_VEHICLE:
			listOfCurrAttr.add("operationVehicleId");
			listOfCurrAttr.add("description");
			break;
		case OTHER_ORG:
			listOfCurrAttr.add("otherOrganisationId");
			listOfCurrAttr.add("name");
			break;
		case RANK:
			listOfCurrAttr.add("rankId");
			listOfCurrAttr.add("contraction");
			listOfCurrAttr.add("description");
			break;
		default:
			break;
		}

		if (additinalAttr != null && additinalAttr.size() > 0) {
			for (int i = 0; i < additinalAttr.size(); i++) {
				listOfCurrAttr.add(additinalAttr.get(i));
			}
		}

		retVal = CentralHandler.getInstance().setDatabaseFieldAttributes(classtype, listOfCurrAttr);
		return retVal;
	}

	public HashMap<String, String> setDatabaseFieldAttributes(ClassTypes objType, List<String> currAttr) {
		HashMap<String, String> retVal = new HashMap<String, String>();
		switch (objType) {
		case BASE:
			for (int i = 0; i < currAttr.size(); i++) {
				switch (currAttr.get(i)) {
				case "baseId":
					retVal.put(currAttr.get(i), Base.CONST_DB_BASEID);
					break;
				case "name":
					retVal.put(currAttr.get(i), Base.CONST_DB_NAME);
					break;
				case "place":
					retVal.put(currAttr.get(i), Base.CONST_DB_PLACE);
					break;
				case "postCode":
					retVal.put(currAttr.get(i), Base.CONST_DB_POSTCODE);
					break;
				case "street":
					retVal.put(currAttr.get(i), Base.CONST_DB_STREET);
					break;
				case "houseNr":
					retVal.put(currAttr.get(i), Base.CONST_DB_HOUSENR);
					break;
				}
			}
		case MEMBER:
			for (int i = 0; i < currAttr.size(); i++) {
				switch (currAttr.get(i)) {
				case "memberId":
					retVal.put(currAttr.get(i), Member.CONST_DB_MEMBERID);
					break;
				case "firstname":
					retVal.put(currAttr.get(i), Member.CONST_DB_FIRSTNAME);
					break;
				case "lastname":
					retVal.put(currAttr.get(i), Member.CONST_DB_LASTNAME);
					break;
				case "username":
					retVal.put(currAttr.get(i), Member.CONST_DB_USERNAME);
					break;
				case "password":
					retVal.put(currAttr.get(i), Member.CONST_DB_PASSWORD);
					break;
				case "isAdmin":
					retVal.put(currAttr.get(i), Member.CONST_DB_ISADMIN);
					break;
				case "base":
					retVal.put(currAttr.get(i), Member.CONST_DB_BASEID);
					break;
				case "rank":
					retVal.put(currAttr.get(i), Member.CONST_DB_RANKID);
					break;
				case "description":
					retVal.put(currAttr.get(i), Member.CONST_DB_DESCRIPTION);
					break;
				case "contraction":
					retVal.put(currAttr.get(i), Member.CONST_DB_CONTRACTION);
					break;
				}
			}
			break;
		case OPERATION:
			for (int i = 0; i < currAttr.size(); i++) {
				switch (currAttr.get(i)) {
				case "operationId":
					retVal.put(currAttr.get(i), Operation.CONST_DB_OPERATIONID);
					break;
				case "operationCode":
					retVal.put(currAttr.get(i), Operation.CONST_DB_OPERATIONCODEID);
					break;
				case "operationType":
					retVal.put(currAttr.get(i), Operation.CONST_DB_OPERATIONTYPEID);
					break;
				case "postCode":
					retVal.put(currAttr.get(i), Operation.CONST_DB_POSTCODE);
					break;
				case "address":
					retVal.put(currAttr.get(i), Operation.CONST_DB_ADDRESS);
					break;
				case "title":
					retVal.put(currAttr.get(i), Operation.CONST_DB_TITLE);
					break;
				case "shortDescription":
					retVal.put(currAttr.get(i), Operation.CONST_DB_SHORTDESCRIPTION);
					break;
				case "time":
					retVal.put(currAttr.get(i), Operation.CONST_DB_TIME);
					break;
				}
			}
			break;
		case OPERATION_CODE:
			for (int i = 0; i < currAttr.size(); i++) {
				switch (currAttr.get(i)) {
					case "operationCodeId":
						retVal.put(currAttr.get(i), OperationCode.CONST_DB_OPERATIONCODEID);
						break;
					case "code":
						retVal.put(currAttr.get(i), OperationCode.CONST_DB_CODE);
						break;
				}
			}
			break;
		case OPERATION_TYPE:
			for (int i = 0; i < currAttr.size(); i++) {
				switch (currAttr.get(i)) {
					case "operationTypeId":
						retVal.put(currAttr.get(i), OperationType.CONST_DB_OPERATIONTYPEID);
						break;
					case "description":
						retVal.put(currAttr.get(i), OperationType.CONST_DB_DESCRIPTION);
						break;
				}
			}
			break;
		case OPERATION_VEHICLE:
			for (int i = 0; i < currAttr.size(); i++) {
				switch (currAttr.get(i)) {
				case "operationVehicleId":
					retVal.put(currAttr.get(i), OperationVehicle.CONST_DB_ID);
					break;
				case "description":
					retVal.put(currAttr.get(i), OperationVehicle.CONST_DB_DESCRIPTION);
					break;
				case "base":
					retVal.put(currAttr.get(i), OperationVehicle.CONST_DB_BASEID);
					break;
				case "baseId":
					retVal.put(currAttr.get(i), OperationVehicle.CONST_DB_BASEID);
					break;
				}
			}
			break;
		case OTHER_ORG:
			for (int i = 0; i < currAttr.size(); i++) {
				switch (currAttr.get(i)) {
				case "otherOrganisationId":
					retVal.put(currAttr.get(i), OtherOrganisation.CONST_DB_ID);
					break;
				case "name":
					retVal.put(currAttr.get(i), OtherOrganisation.CONST_DB_NAME);
					break;
				}
			}
			break;
		case RANK:
			for (int i = 0; i < currAttr.size(); i++) {
				switch (currAttr.get(i)) {
				case "rankId":
					retVal.put(currAttr.get(i), Rank.CONST_DB_ID);
					break;
				case "contraction":
					retVal.put(currAttr.get(i), Rank.CONST_DB_CONTRACTION);
					break;
				case "description":
					retVal.put(currAttr.get(i), Rank.CONST_DB_DESCRIPTION);
					break;
				}
			}
			break;
		default:
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
					String operationCodeId = null;
					HashMap<String, String> subMetadataOperationCode = subMetadata.get(ClassTypes.OPERATION_CODE);
					for (Entry<String, String> currBaseAttr : subMetadataOperationCode.entrySet()) {
						if (currBaseAttr.getKey().equals("operationCodeId")) {
							operationCodeId = OperationCode.CONST_DB_OPERATIONCODEID;
						}
					}
					if (operationCodeId != null) {
						headerMetadataString += ", 'operationCode':{'operationCodeId':'" + operationCodeId + "'}";
					}
					break;
				case OPERATION_TYPE:
					String operationTypeId = null;
					HashMap<String, String> subMetadataOperationType = subMetadata.get(ClassTypes.OPERATION_TYPE);
					for (Entry<String, String> currBaseAttr : subMetadataOperationType.entrySet()) {
						if (currBaseAttr.getKey().equals("operationTypeId")) {
							operationTypeId = OperationCode.CONST_DB_OPERATIONCODEID;
						}
					}
					if (operationTypeId != null) {
						headerMetadataString += ", 'operationType':{'operationTypeId':'" + operationTypeId + "'}";
					}
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
	
	public void mergeFullOperationObject() {
		ArrayList<Operation> tempListOfOperations = OperationHandler.getInstance().getOperationList();
		ArrayList<OperationCode> tempListOfOperationCodes = OperationCodeHandler.getInstance().getOperationCodeList();
		ArrayList<OperationType> tempListOfOperationTypes = OperationTypeHandler.getInstance().getOperationTypeList();
		
		//Add Code- to Operation-Object
		for(int i=0;i<tempListOfOperations.size();i++) {
			for(int j=0;j<tempListOfOperationCodes.size();j++) {
				if(tempListOfOperations.get(i).getOperationCode().getOperationCodeId() == tempListOfOperationCodes.get(j).getOperationCodeId()) {
					tempListOfOperations.get(i).setOperationCode(tempListOfOperationCodes.get(j));
				}
			}
		}
		
		//Add Type- to Operation-Object
		for(int i=0;i<tempListOfOperations.size();i++) {
			for(int j=0;j<tempListOfOperationTypes.size();j++) {
				if(tempListOfOperations.get(i).getOperationType().getOperationTypeId() == tempListOfOperationTypes.get(j).getOperationTypeId()) {
					tempListOfOperations.get(i).setOperationType(tempListOfOperationTypes.get(j));
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