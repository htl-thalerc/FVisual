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
import bll.EnumCRUDOption;
import bll.Member;
import handler.CentralHandler;
import handler.ExceptionHandler;

public class MemberManager {
	private static MemberManager memberManagerInstance = null;
	private Client client = ClientBuilder.newClient();
	private WebTarget webTarget = this.client.target(CentralHandler.getInstance().getRessource());
	private WebTarget webTargetMemberServiceForBase = this.webTarget.path(CentralHandler.CONST_BASE_URL);
	private WebTarget webTargetMemberServiceForOperation = this.webTarget.path(CentralHandler.CONST_OPERATION_URL);
	private WebTarget webTargetMemberService = this.webTarget.path(CentralHandler.CONST_MEMBER_URL); // .../mitglieder
	private static final Logger LOGGER = LogManager.getLogger(MemberManager.class.getName());

	public static MemberManager getInstance() {
		if (memberManagerInstance == null) {
			memberManagerInstance = new MemberManager();
		}
		return memberManagerInstance;
	}

	// Basic Service
	public ArrayList<Member> getMembers() {
		ArrayList<Member> collOfMembers = null;
		Invocation.Builder invocationBuilder = null;
		Response response = null;
		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		HashMap<String, String> mainMetadata = CentralHandler.getInstance().setMetadataMap(ClassTypes.MEMBER, null);

		HashMap<ClassTypes, HashMap<String, String>> subMetadata = new HashMap<ClassTypes, HashMap<String, String>>();

		subMetadata.put(ClassTypes.BASE, CentralHandler.getInstance().setMetadataMap(ClassTypes.BASE, null));
		subMetadata.put(ClassTypes.RANK, CentralHandler.getInstance().setMetadataMap(ClassTypes.RANK, null));

		try {
			headers.add(CentralHandler.CONST_AUTHORIZATION, CentralHandler.getInstance().getHeaderAuthorization());
			headers.add(CentralHandler.CONST_METADATA,
					CentralHandler.getInstance().getHeaderMetadataString(mainMetadata, subMetadata));
			invocationBuilder = this.webTargetMemberService.request(MediaType.APPLICATION_JSON).headers(headers);
			response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
			if (response.getStatus() == 200) {
				collOfMembers = response.readEntity(new GenericType<ArrayList<Member>>() {
				});
				LOGGER.info("[MemberManager] [GET]: Members");
			} else {
				ExceptionHandler.getInstance().setException(response, ClassTypes.MEMBER, EnumCRUDOption.GET);
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
		HashMap<String, String> mainMetadata = CentralHandler.getInstance().setMetadataMap(ClassTypes.MEMBER, null);

		HashMap<ClassTypes, HashMap<String, String>> subMetadata = new HashMap<ClassTypes, HashMap<String, String>>();

		subMetadata.put(ClassTypes.BASE, CentralHandler.getInstance().setMetadataMap(ClassTypes.BASE, null));
		subMetadata.put(ClassTypes.RANK, CentralHandler.getInstance().setMetadataMap(ClassTypes.RANK, null));

		WebTarget webTargetGetAllBaselessMembers = this.webTargetMemberService.path("baseless");
		try {
			headers.add(CentralHandler.CONST_AUTHORIZATION, CentralHandler.getInstance().getHeaderAuthorization());
			headers.add(CentralHandler.CONST_METADATA,
					CentralHandler.getInstance().getHeaderMetadataString(mainMetadata, subMetadata));
			invocationBuilder = webTargetGetAllBaselessMembers.request(MediaType.APPLICATION_JSON).headers(headers);
			response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
			if (response.getStatus() == 200) {
				collOfMembers = response.readEntity(new GenericType<ArrayList<Member>>() {
				});
				LOGGER.info("[MemberManager] [GET]: Baseless Members");
			} else {
				ExceptionHandler.getInstance().setException(response, ClassTypes.MEMBER, EnumCRUDOption.GET);
			}
		} catch (JsonSyntaxException ex) {
			ex.printStackTrace();
		}
		return collOfMembers;
	}

	public Member getMemberById(Member memberObj) {
		Member foundedMember = null;

		Invocation.Builder invocationBuilder = null;
		Response response = null;
		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		HashMap<String, String> mainMetadata = CentralHandler.getInstance().setMetadataMap(ClassTypes.MEMBER, null);

		HashMap<ClassTypes, HashMap<String, String>> subMetadata = new HashMap<ClassTypes, HashMap<String, String>>();

		subMetadata.put(ClassTypes.BASE, CentralHandler.getInstance().setMetadataMap(ClassTypes.BASE, null));
		subMetadata.put(ClassTypes.RANK, CentralHandler.getInstance().setMetadataMap(ClassTypes.RANK, null));

		WebTarget webTargetGetMemberById = this.webTargetMemberService.path(String.valueOf(memberObj.getMemberId()));
		try {
			headers.add(CentralHandler.CONST_AUTHORIZATION, CentralHandler.getInstance().getHeaderAuthorization());
			headers.add(CentralHandler.CONST_METADATA,
					CentralHandler.getInstance().getHeaderMetadataString(mainMetadata, subMetadata));
			invocationBuilder = webTargetGetMemberById.request(MediaType.APPLICATION_JSON).headers(headers);
			response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
			if (response.getStatus() == 200) {
				ArrayList<Member> list = response.readEntity(new GenericType<ArrayList<Member>>() {
				});
				foundedMember = list.get(0);
				LOGGER.info("[MemberManager] [GET]: Member by Id");
			} else {
				ExceptionHandler.getInstance().setException(response, ClassTypes.MEMBER, EnumCRUDOption.GET);
			}
		} catch (JsonSyntaxException ex) {
			ex.printStackTrace();
		}

		return foundedMember;
	}

	// Base Services
	public ArrayList<Member> getMembersFromBase(int baseId) {
		ArrayList<Member> collOfMembers = null;
		Invocation.Builder invocationBuilder = null;
		Response response = null;
		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		HashMap<String, String> mainMetadata = CentralHandler.getInstance().setMetadataMap(ClassTypes.MEMBER, null);

		HashMap<ClassTypes, HashMap<String, String>> subMetadata = new HashMap<ClassTypes, HashMap<String, String>>();

		subMetadata.put(ClassTypes.BASE, CentralHandler.getInstance().setMetadataMap(ClassTypes.BASE, null));
		subMetadata.put(ClassTypes.RANK, CentralHandler.getInstance().setMetadataMap(ClassTypes.RANK, null));

		WebTarget webTargetGetAllMembers = this.webTargetMemberServiceForBase
				.path(String.valueOf(baseId) + "/" + CentralHandler.CONST_MEMBER_URL);
		try {
			headers.add(CentralHandler.CONST_AUTHORIZATION, CentralHandler.getInstance().getHeaderAuthorization());
			headers.add(CentralHandler.CONST_METADATA,
					CentralHandler.getInstance().getHeaderMetadataString(mainMetadata, subMetadata));
			invocationBuilder = webTargetGetAllMembers.request(MediaType.APPLICATION_JSON).headers(headers);
			response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
			if (response.getStatus() == 200) {
				collOfMembers = response.readEntity(new GenericType<ArrayList<Member>>() {
				});
				LOGGER.info("[MemberManager] [GET]: Members by BaseId");
			} else {
				ExceptionHandler.getInstance().setException(response, ClassTypes.MEMBER, EnumCRUDOption.GET);
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
		WebTarget webTargetGetAll = this.webTargetMemberServiceForBase
				.path(String.valueOf(baseId) + "/" + CentralHandler.CONST_MEMBER_URL + "?id=" + memberId);

		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		ArrayList<String> additinalAttr = new ArrayList<String>(Arrays.asList("isAdmin", "password"));
		HashMap<String, String> mainMetadata = CentralHandler.getInstance().setMetadataMap(ClassTypes.MEMBER,
				additinalAttr);

		HashMap<ClassTypes, HashMap<String, String>> subMetadata = new HashMap<ClassTypes, HashMap<String, String>>();

		subMetadata.put(ClassTypes.BASE, CentralHandler.getInstance().setMetadataMap(ClassTypes.BASE, null));
		subMetadata.put(ClassTypes.RANK, CentralHandler.getInstance().setMetadataMap(ClassTypes.RANK, null));

		try {
			headers.add(CentralHandler.CONST_AUTHORIZATION, CentralHandler.getInstance().getHeaderAuthorization());
			headers.add(CentralHandler.CONST_METADATA,
					CentralHandler.getInstance().getHeaderMetadataString(mainMetadata, subMetadata));

			invocationBuilder = webTargetGetAll.request(MediaType.APPLICATION_JSON).headers(headers);
			response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
			if (response.getStatus() == 200) {
				foundedMember = response.readEntity(new GenericType<ArrayList<Member>>() {
				});
			} else {
				ExceptionHandler.getInstance().setException(response, ClassTypes.MEMBER, EnumCRUDOption.GET);
			}
		} catch (JsonSyntaxException ex) {
			ex.printStackTrace();
		}
		return foundedMember;
	}

	public boolean addMemberToBase(int baseId, Member memberObj) {
		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<String, Object>();

		HashMap<String, String> mainMetadata = CentralHandler.getInstance().setMetadataMap(ClassTypes.MEMBER,
				new ArrayList<String>(Arrays.asList("base", "rank", "password", "isAdmin")));
		
		headers.add(CentralHandler.CONST_AUTHORIZATION, CentralHandler.getInstance().getHeaderAuthorization());
		headers.add(CentralHandler.CONST_METADATA,
				CentralHandler.getInstance().getHeaderMetadataString(mainMetadata, null));

		Invocation.Builder invocationBuilder = this.webTargetMemberService.request(MediaType.APPLICATION_JSON)
				.headers(headers);

		String jsonStr = "{\"firstname\":\"" + memberObj.getFirstname() + "\"," + "\"lastname\":\"" + memberObj.getLastname() + "\","
				+ "\"username\":\"" + memberObj.getUsername() + "\"," + "\"base\":\"" + memberObj.getBaseId() + "\","
				+ "\"rank\":\"" + memberObj.getRankId() + "\"," + "\"password\":\"" + memberObj.getPassword() + "\","
				+ "\"isAdmin\":\"" + memberObj.isAdmin() +"\"}";

		Response response = invocationBuilder.post(Entity.entity(jsonStr, MediaType.APPLICATION_JSON));

		if (response.getStatus() == 201) {
			return true;
		} else {
			ExceptionHandler.getInstance().setException(response, ClassTypes.MEMBER, EnumCRUDOption.POST);
			return false;
		}
	}

	public boolean deleteMemberFromBase(int baseId, int memberId) {
		WebTarget webTargetRemoveMember = this.webTargetMemberServiceForBase
				.path(String.valueOf(baseId) + "/" + CentralHandler.CONST_MEMBER_URL + "/" + memberId);

		Invocation.Builder invocationBuilder = webTargetRemoveMember.request(MediaType.APPLICATION_JSON)
				.header(CentralHandler.CONST_AUTHORIZATION, CentralHandler.getInstance().getHeaderAuthorization());
		Response response = invocationBuilder.delete();

		if (response.getStatus() == 204) {
			return true;
		} else {
			ExceptionHandler.getInstance().setException(response, ClassTypes.MEMBER, EnumCRUDOption.DELETE);
			return false;
		}
	}

	public boolean updateMemberFromBase(Member memberObj) {
		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<String, Object>();

		HashMap<String, String> mainMetadata = CentralHandler.getInstance().setMetadataMap(ClassTypes.MEMBER,
				new ArrayList<String>(Arrays.asList("rank", "base", "isAdmin", "password")));

		WebTarget webTargetUpdateMember = this.webTargetMemberService.path(String.valueOf(memberObj.getMemberId()));

		headers.add(CentralHandler.CONST_AUTHORIZATION, CentralHandler.getInstance().getHeaderAuthorization());
		headers.add(CentralHandler.CONST_METADATA,
				CentralHandler.getInstance().getHeaderMetadataString(mainMetadata, null));

		Invocation.Builder invocationBuilder = webTargetUpdateMember.request(MediaType.APPLICATION_JSON)
				.headers(headers);

		String jsonBodyString = "{\"base\":\"" + memberObj.getBaseId() + "\"," + "\"rank\":\"" + memberObj.getRankId()
				+ "\"," + "\"firstname\":\"" + memberObj.getFirstname() + "\"," + "\"lastname\":\""
				+ memberObj.getLastname() + "\"," + "\"username\":\"" + memberObj.getUsername() + "\","
				+ "\"password\":\"" + memberObj.getPassword() + "\"," + "\"isAdmin\":\"" + memberObj.isAdmin() + "\"}";

		Response response = invocationBuilder.put(Entity.entity(jsonBodyString, MediaType.APPLICATION_JSON));

		if (response.getStatus() == 200) {
			return true;
		} else {
			ExceptionHandler.getInstance().setException(response, ClassTypes.MEMBER, EnumCRUDOption.PUT);
			return false;
		}
	}

	// Operation Services
	public ArrayList<Member> getMembersFromOperation(int operationId) {
		ArrayList<Member> collOfMembers = null;
		Invocation.Builder invocationBuilder = null;
		Response response = null;
		WebTarget webTargetGetAllMembers = this.webTargetMemberServiceForOperation
				.path(String.valueOf(operationId) + "/" + CentralHandler.CONST_MEMBER_URL);
		try {
			invocationBuilder = webTargetGetAllMembers.request(MediaType.APPLICATION_JSON)
					.header(CentralHandler.CONST_AUTHORIZATION, CentralHandler.getInstance().getHeaderAuthorization());
			response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
			if (response.getStatus() == 200) {
				collOfMembers = response.readEntity(new GenericType<ArrayList<Member>>() {
				});
			} else {
				ExceptionHandler.getInstance().setException(response, ClassTypes.MEMBER, EnumCRUDOption.GET);
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
		WebTarget webTargetGetAll = this.webTargetMemberServiceForOperation
				.path(String.valueOf(operationId) + "/" + CentralHandler.CONST_MEMBER_URL + "?id=" + memberId);
		try {
			invocationBuilder = webTargetGetAll.request(MediaType.APPLICATION_JSON)
					.header(CentralHandler.CONST_AUTHORIZATION, CentralHandler.getInstance().getHeaderAuthorization());
			response = invocationBuilder.accept(MediaType.APPLICATION_JSON).get();
			if (response.getStatus() == 200) {
				foundedMember = response.readEntity(Member.class);
			} else {
				ExceptionHandler.getInstance().setException(response, ClassTypes.MEMBER, EnumCRUDOption.GET);
			}
		} catch (JsonSyntaxException ex) {
			ex.printStackTrace();
		}
		return foundedMember;
	}

	public boolean addMemberToOperation(int operationId, Member memberObj) {
		WebTarget webTargetAddMember = this.webTargetMemberServiceForOperation
				.path(String.valueOf(operationId) + "/" + CentralHandler.CONST_MEMBER_URL);
		Invocation.Builder invocationBuilder = webTargetAddMember.request(MediaType.APPLICATION_JSON)
				.header(CentralHandler.CONST_AUTHORIZATION, CentralHandler.getInstance().getHeaderAuthorization());
		Response response = invocationBuilder.post(Entity.entity(memberObj, MediaType.APPLICATION_JSON));

		if (response.getStatus() == 201) {
			return true;
		} else {
			ExceptionHandler.getInstance().setException(response, ClassTypes.MEMBER, EnumCRUDOption.POST);
			return false;
		}
	}

	public boolean deleteMemberFromOperation(int operationId, int memberId) {
		WebTarget webTargetRemoveMember = this.webTargetMemberServiceForOperation
				.path(String.valueOf(operationId) + "/" + CentralHandler.CONST_MEMBER_URL + "/" + memberId);
		Invocation.Builder invocationBuilder = webTargetRemoveMember.request(MediaType.APPLICATION_JSON)
				.header(CentralHandler.CONST_AUTHORIZATION, CentralHandler.getInstance().getHeaderAuthorization());
		Response response = invocationBuilder.delete();

		if (response.getStatus() == 204) {
			return true;
		} else {
			ExceptionHandler.getInstance().setException(response, ClassTypes.MEMBER, EnumCRUDOption.DELETE);
			return false;
		}
	}

	public boolean updateMemberFromOperation(int operationId, Member memberObj) {
		WebTarget webTargetUpdateMember = this.webTargetMemberServiceForOperation.path(
				String.valueOf(operationId) + "/" + CentralHandler.CONST_MEMBER_URL + "/" + memberObj.getMemberId());
		Invocation.Builder invocationBuilder = webTargetUpdateMember.request(MediaType.APPLICATION_JSON)
				.header(CentralHandler.CONST_AUTHORIZATION, CentralHandler.getInstance().getHeaderAuthorization());
		Response response = invocationBuilder.put(Entity.entity(memberObj, MediaType.APPLICATION_JSON));

		if (response.getStatus() == 200) {
			return true;
		} else {
			ExceptionHandler.getInstance().setException(response, ClassTypes.MEMBER, EnumCRUDOption.PUT);
			return false;
		}
	}
}