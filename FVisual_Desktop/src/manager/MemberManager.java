package manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonSyntaxException;

import bll.ClassTypes;
import bll.Member;
import bll.OperationVehicle;
import handler.CentralHandler;

public class MemberManager {
	private static MemberManager memberManagerInstance = null;
	private Client client = ClientBuilder.newClient();
	private WebTarget webTarget = this.client.target(CentralHandler.getInstance().getRessource());
	private WebTarget webTargetMemberServiceForBase = this.webTarget.path(CentralHandler.CONST_BASE_URL);
	private WebTarget webTargetMemberServiceForOperation = this.webTarget.path(CentralHandler.CONST_OPERATION_URL);
	private WebTarget webTargetMemberService = this.webTarget.path(CentralHandler.CONST_MEMBER_URL); //.../mitglieder
	private static final Logger LOGGER = LogManager.getLogger(MemberManager.class.getName());
	
	public static MemberManager getInstance() {
		if (memberManagerInstance == null) {
			memberManagerInstance = new MemberManager();
		}
		return memberManagerInstance;
	}
	//Basic Service
	public ArrayList<Member> getMembers() {
		ArrayList<Member> collOfMembers = null;
		Invocation.Builder invocationBuilder = null;
		Response response = null;
		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		HashMap<String, String> mainMetadata = CentralHandler.getInstance().setDatabaseFieldAttributes(ClassTypes.MEMBER, new ArrayList<String>(
					Arrays.asList("memberId", "username", "firstname", "lastname")));
		
		HashMap<ClassTypes, HashMap<String, String>> subMetadata = new HashMap<ClassTypes, HashMap<String, String>>();
		
		HashMap<String, String> subMetadataBase = CentralHandler.getInstance().setDatabaseFieldAttributes(ClassTypes.BASE, new ArrayList<String>(
				Arrays.asList("baseId", "name", "place", "street", "postCode", "houseNr")));
		HashMap<String, String> subMetadataRank = CentralHandler.getInstance().setDatabaseFieldAttributes(ClassTypes.RANK, new ArrayList<String>(
				Arrays.asList("rankId", "contraction", "description")));
		
		subMetadata.put(ClassTypes.BASE, subMetadataBase);
		subMetadata.put(ClassTypes.RANK, subMetadataRank);
		
		try {
			headers.add(CentralHandler.CONST_AUTHORIZATION, CentralHandler.getInstance().getHeaderAuthorization());
			headers.add(CentralHandler.CONST_METADATA, CentralHandler.getInstance().getHeaderMetadataString(mainMetadata, subMetadata));
			invocationBuilder = this.webTargetMemberService.request(MediaType.APPLICATION_JSON).headers(headers);
			response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
			if(response.getStatus() == 200) {
				collOfMembers = response.readEntity(new GenericType<ArrayList<Member>>() {
				});
				LOGGER.info("[MemberManager] [GET]: Members");
			}
		} catch (JsonSyntaxException ex) {
			ex.printStackTrace();
		}
		return collOfMembers;
	}
	
	public ArrayList<Member> getBaselessMembers() {
		ArrayList<Member> collOfMembers = null;
		Invocation.Builder invocationBuilder = null;
		Response response = null;
		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		HashMap<String, String> mainMetadata = CentralHandler.getInstance().setDatabaseFieldAttributes(ClassTypes.MEMBER, new ArrayList<String>(
					Arrays.asList("memberId", "username", "firstname", "lastname")));
		
		HashMap<ClassTypes, HashMap<String, String>> subMetadata = new HashMap<ClassTypes, HashMap<String, String>>();
		
		HashMap<String, String> subMetadataBase = CentralHandler.getInstance().setDatabaseFieldAttributes(ClassTypes.BASE, new ArrayList<String>(
				Arrays.asList("baseId", "name", "place", "street", "postCode", "houseNr")));
		HashMap<String, String> subMetadataRank = CentralHandler.getInstance().setDatabaseFieldAttributes(ClassTypes.RANK, new ArrayList<String>(
				Arrays.asList("rankId", "contraction", "description")));
		
		subMetadata.put(ClassTypes.BASE, subMetadataBase);
		subMetadata.put(ClassTypes.RANK, subMetadataRank);
		WebTarget webTargetGetAllBaselessMembers = this.webTargetMemberService.path("baseless");
		try {
			headers.add(CentralHandler.CONST_AUTHORIZATION, CentralHandler.getInstance().getHeaderAuthorization());
			headers.add(CentralHandler.CONST_METADATA, CentralHandler.getInstance().getHeaderMetadataString(mainMetadata, subMetadata));
			invocationBuilder = webTargetGetAllBaselessMembers.request(MediaType.APPLICATION_JSON).headers(headers);
			response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
			if(response.getStatus() == 200) {
				collOfMembers = response.readEntity(new GenericType<ArrayList<Member>>() {
				});
				LOGGER.info("[MemberManager] [GET]: Baseless Members");
			}
		} catch (JsonSyntaxException ex) {
			ex.printStackTrace();
		}
		return collOfMembers;
	}
	
	//Base Services
	public ArrayList<Member> getMembersFromBase(int baseId) {
		ArrayList<Member> collOfMembers = null;
		Invocation.Builder invocationBuilder = null;
		Response response = null;
		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		HashMap<String, String> mainMetadata = CentralHandler.getInstance().setDatabaseFieldAttributes(ClassTypes.MEMBER, new ArrayList<String>(
				Arrays.asList("memberId", "username", "firstname", "lastname")));
		
		HashMap<ClassTypes, HashMap<String, String>> subMetadata = new HashMap<ClassTypes, HashMap<String, String>>();
		
		HashMap<String, String> subMetadataBase = CentralHandler.getInstance().setDatabaseFieldAttributes(ClassTypes.BASE, new ArrayList<String>(
				Arrays.asList("baseId", "name", "place", "street", "postCode", "houseNr")));
		HashMap<String, String> subMetadataRank = CentralHandler.getInstance().setDatabaseFieldAttributes(ClassTypes.RANK, new ArrayList<String>(
				Arrays.asList("rankId", "contraction", "description")));
		subMetadata.put(ClassTypes.BASE, subMetadataBase);
		subMetadata.put(ClassTypes.RANK, subMetadataRank);
		WebTarget webTargetGetAllMembers = this.webTargetMemberServiceForBase.path(String.valueOf(baseId) + "/" + CentralHandler.CONST_MEMBER_URL);
		try {
			headers.add(CentralHandler.CONST_AUTHORIZATION, CentralHandler.getInstance().getHeaderAuthorization());
			headers.add(CentralHandler.CONST_METADATA, CentralHandler.getInstance().getHeaderMetadataString(mainMetadata, subMetadata));
			invocationBuilder = webTargetGetAllMembers.request(MediaType.APPLICATION_JSON).headers(headers); 
			response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
			if(response.getStatus() == 200) {
				collOfMembers = response.readEntity(new GenericType<ArrayList<Member>>() {
				});
				LOGGER.info("[MemberManager] [GET]: Members by BaseId");
			}
		} catch (JsonSyntaxException ex) {
			ex.printStackTrace();
		}
		return collOfMembers;
	}
	
	public ArrayList<Member> getMemberByIdFromBase(int baseId, int memberId) {
		ArrayList<Member> foundedMember = null;
		Invocation.Builder invocationBuilder = null;
		Response response = null;
		WebTarget webTargetGetAll = this.webTargetMemberServiceForBase.path(String.valueOf(baseId) + "/" + CentralHandler.CONST_MEMBER_URL + "?id=" + memberId);
		
		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		HashMap<String, String> mainMetadata = CentralHandler.getInstance().setDatabaseFieldAttributes(ClassTypes.MEMBER, new ArrayList<String>(
				Arrays.asList("memberId", "username", "firstname", "lastname", "isAdmin", "password")));
		
		HashMap<ClassTypes, HashMap<String, String>> subMetadata = new HashMap<ClassTypes, HashMap<String, String>>();
		
		HashMap<String, String> subMetadataBase = CentralHandler.getInstance().setDatabaseFieldAttributes(ClassTypes.BASE, new ArrayList<String>(
				Arrays.asList("baseId", "name", "place", "street", "postCode", "houseNr")));
		HashMap<String, String> subMetadataRank = CentralHandler.getInstance().setDatabaseFieldAttributes(ClassTypes.RANK, new ArrayList<String>(
				Arrays.asList("rankId", "contraction", "description")));
		subMetadata.put(ClassTypes.BASE, subMetadataBase);
		subMetadata.put(ClassTypes.RANK, subMetadataRank);
		
		try {
			headers.add(CentralHandler.CONST_AUTHORIZATION, CentralHandler.getInstance().getHeaderAuthorization());
			headers.add(CentralHandler.CONST_METADATA, CentralHandler.getInstance().getHeaderMetadataString(mainMetadata, subMetadata));
			
			invocationBuilder = webTargetGetAll.request(MediaType.APPLICATION_JSON).headers(headers);
			response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
			if(response.getStatus() == 200) {
				foundedMember = response.readEntity(new GenericType<ArrayList<Member>>() {
				});
			}
		} catch (JsonSyntaxException ex) {
			ex.printStackTrace();
		}
		return foundedMember;
	}
	
	public boolean addMemberToBase(int baseId, Member memberObj) {
		WebTarget webTargetAddMember = this.webTargetMemberServiceForBase.path(String.valueOf(baseId) + "/" + CentralHandler.CONST_MEMBER_URL);
		Invocation.Builder invocationBuilder = webTargetAddMember.request(MediaType.APPLICATION_JSON).header(CentralHandler.CONST_AUTHORIZATION,
					CentralHandler.getInstance().getHeaderAuthorization());
		Response response = invocationBuilder.post(Entity.entity(memberObj, MediaType.APPLICATION_JSON));
		
		if (response.getStatus() == 201) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean deleteMemberFromBase(int baseId, int memberId) {
		WebTarget webTargetRemoveMember = this.webTargetMemberServiceForBase.path(String.valueOf(baseId) + "/" + CentralHandler.CONST_MEMBER_URL + "/" + memberId);
		
		Invocation.Builder invocationBuilder = webTargetRemoveMember.request(MediaType.APPLICATION_JSON).header(CentralHandler.CONST_AUTHORIZATION,
				CentralHandler.getInstance().getHeaderAuthorization());
		Response response = invocationBuilder.delete();

		if (response.getStatus() == 204) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean updateMemberFromBase(Member memberObj) {
		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		HashMap<String, String> mainMetadata = CentralHandler.getInstance().setDatabaseFieldAttributes(ClassTypes.MEMBER, new ArrayList<String>(
					Arrays.asList("memberId", "username", "firstname", "lastname", "rankId", "baseId", "isAdmin", "password")));
		
		WebTarget webTargetUpdateMember = this.webTargetMemberService.path(String.valueOf(memberObj.getMemberId()));
		
		headers.add(CentralHandler.CONST_AUTHORIZATION, CentralHandler.getInstance().getHeaderAuthorization());
		headers.add(CentralHandler.CONST_METADATA, CentralHandler.getInstance().getHeaderMetadataString(mainMetadata, null));
		
		Invocation.Builder invocationBuilder = webTargetUpdateMember.request(MediaType.APPLICATION_JSON).headers(headers);
		
		String jsonStr = "{\"baseId\":\"" + memberObj.getBaseId() + "\"," + 
				"\"rankId\":\"" + memberObj.getRankId() + "\"," +
				"\"firstname\":\"" + memberObj.getFirstname() + "\"," +
				"\"lastname\":\"" + memberObj.getLastname() + "\"," +
				"\"username\":\"" + memberObj.getUsername() + "\"," +
				"\"password\":\"" + memberObj.getPassword() + "\"," +
				"\"isAdmin\":\"" + memberObj.isAdmin() + "\"}";
		
		System.out.println(jsonStr);
		
		Response response = invocationBuilder.put(Entity.entity(jsonStr, MediaType.APPLICATION_JSON));
		
		if (response.getStatus() == 200) {
			ArrayList<Member> list = response.readEntity(new GenericType<ArrayList<Member>>() {
			});
			System.out.println("F: " + list.get(0).toFullString());
			return true;
		} else {
			return false;
		}
	}
	//Operation Services
	public ArrayList<Member> getMembersFromOperation(int operationId) {
		ArrayList<Member> collOfMembers = null;
		Invocation.Builder invocationBuilder = null;
		Response response = null;
		WebTarget webTargetGetAllMembers = this.webTargetMemberServiceForOperation.path(String.valueOf(operationId) + "/" + CentralHandler.CONST_MEMBER_URL);
		try {
			invocationBuilder = webTargetGetAllMembers.request(MediaType.APPLICATION_JSON).header(CentralHandler.CONST_AUTHORIZATION,
					CentralHandler.getInstance().getHeaderAuthorization()); 
			response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
			if(response.getStatus() == 200) {
				collOfMembers = response.readEntity(new GenericType<ArrayList<Member>>() {
				});
			}
		} catch (JsonSyntaxException ex) {
			ex.printStackTrace();
		}
		return collOfMembers;
	}
	
	public Member getMemberByIdFromOperation(int operationId, int memberId) {
		Member foundedMember = null;
		Invocation.Builder invocationBuilder = null;
		Response response = null;
		WebTarget webTargetGetAll = this.webTargetMemberServiceForOperation.path(String.valueOf(operationId) + "/" + CentralHandler.CONST_MEMBER_URL + "?id=" + memberId);
		try {
			invocationBuilder = webTargetGetAll.request(MediaType.APPLICATION_JSON).header(CentralHandler.CONST_AUTHORIZATION,
					CentralHandler.getInstance().getHeaderAuthorization()); 
			response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
			if(response.getStatus() == 200) {
				foundedMember = response.readEntity(Member.class);
			}
		} catch (JsonSyntaxException ex) {
			ex.printStackTrace();
		}
		return foundedMember;
	}
	
	public boolean addMemberToOperation(int operationId, Member memberObj) {
		WebTarget webTargetAddMember = this.webTargetMemberServiceForOperation.path(String.valueOf(operationId) + "/" + CentralHandler.CONST_MEMBER_URL);
		Invocation.Builder invocationBuilder = webTargetAddMember.request(MediaType.APPLICATION_JSON).header(CentralHandler.CONST_AUTHORIZATION,
					CentralHandler.getInstance().getHeaderAuthorization());
		Response response = invocationBuilder.post(Entity.entity(memberObj, MediaType.APPLICATION_JSON));
		
		if (response.getStatus() == 201) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean deleteMemberFromOperation(int operationId, int memberId) {
		WebTarget webTargetRemoveMember = this.webTargetMemberServiceForOperation.path(String.valueOf(operationId) + "/" + CentralHandler.CONST_MEMBER_URL + "/" + memberId);
		Invocation.Builder invocationBuilder = webTargetRemoveMember.request(MediaType.APPLICATION_JSON).header(CentralHandler.CONST_AUTHORIZATION,
				CentralHandler.getInstance().getHeaderAuthorization());
		Response response = invocationBuilder.delete();

		if (response.getStatus() == 204) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean updateMemberFromOperation(int operationId, Member memberObj) {
		WebTarget webTargetUpdateMember = this.webTargetMemberServiceForOperation.path(String.valueOf(operationId) + "/" + CentralHandler.CONST_MEMBER_URL + "/" + memberObj.getMemberId());
		Invocation.Builder invocationBuilder = webTargetUpdateMember.request(MediaType.APPLICATION_JSON).header(CentralHandler.CONST_AUTHORIZATION,
				CentralHandler.getInstance().getHeaderAuthorization());
		Response response = invocationBuilder.put(Entity.entity(memberObj, MediaType.APPLICATION_JSON));
		
		if (response.getStatus() == 200) {
			return true;
		} else {
			return false;
		}
	}
}