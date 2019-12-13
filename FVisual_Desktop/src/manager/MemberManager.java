package manager;

import java.util.ArrayList;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.JsonSyntaxException;

import bll.Member;
import handler.CentralHandler;

public class MemberManager {
	private static MemberManager memberManagerInstance = null;
	private Client client = ClientBuilder.newClient();
	private WebTarget webTarget = this.client.target(CentralHandler.getInstance().getRessource());
	private WebTarget webTargetMemberServiceForBase = this.webTarget.path(CentralHandler.CONST_BASE_URL);
	private WebTarget webTargetMemberServiceForOperation = this.webTarget.path(CentralHandler.CONST_OPERATION_URL);
	private WebTarget webTargetMemberService = this.webTarget.path(CentralHandler.CONST_MEMBER_URL); //.../mitglieder

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
		try {
			invocationBuilder = this.webTargetMemberService.request(MediaType.APPLICATION_JSON).header(CentralHandler.CONST_AUTHORIZATION,
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
	
	//Base Services
	public ArrayList<Member> getMembersFromBase(int baseId) {
		ArrayList<Member> collOfMembers = null;
		Invocation.Builder invocationBuilder = null;
		Response response = null;
		WebTarget webTargetGetAllMembers = this.webTargetMemberServiceForBase.path(String.valueOf(baseId) + "/" + CentralHandler.CONST_MEMBER_URL);
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
	
	public Member getMemberByIdFromBase(int baseId, int memberId) {
		Member foundedMember = null;
		Invocation.Builder invocationBuilder = null;
		Response response = null;
		WebTarget webTargetGetAll = this.webTargetMemberServiceForBase.path(String.valueOf(baseId) + "/" + CentralHandler.CONST_MEMBER_URL + "?id=" + memberId);
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
	
	public boolean updateMemberFromBase(int baseId, Member memberObj) {
		WebTarget webTargetUpdateMember = this.webTargetMemberServiceForBase.path(String.valueOf(baseId) + "/" + CentralHandler.CONST_MEMBER_URL + "/" + memberObj.getMemberId());
		Invocation.Builder invocationBuilder = webTargetUpdateMember.request(MediaType.APPLICATION_JSON).header(CentralHandler.CONST_AUTHORIZATION,
				CentralHandler.getInstance().getHeaderAuthorization());
		Response response = invocationBuilder.put(Entity.entity(memberObj, MediaType.APPLICATION_JSON));
		
		if (response.getStatus() == 200) {
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