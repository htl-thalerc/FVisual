package loader;

import java.util.ArrayList;

import bll.Member;
import bll.Operation;
import handler.CentralHandler;
import handler.MemberHandler;
import handler.OperationHandler;
import manager.MemberManager;
import manager.OperationManager;

public class OperationLoader implements Runnable {

	@Override
	public void run() {
		ArrayList<Operation> listOfAllOperations = OperationManager.getInstance().getOperations();
		OperationHandler.getInstance().setListOfOperations(listOfAllOperations);
	}
}