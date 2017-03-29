package org.eclipse.emf.refactor.refactoring.papyrus.managers;

import org.eclipse.emf.refactor.refactoring.papyrus.handler.EmfRefactorNotificationHandler;
import org.eclipse.gmf.runtime.notation.Diagram;

public class PapyrusManager {

	private static PapyrusManager instance;
	private static EmfRefactorNotificationHandler handlerInstance;
	private Diagram diagram;
	
	private PapyrusManager() {
		initNotifier();
		setDiagram(null);
		System.out.println("PapyrusManager initialized!");
	}
	
	public static PapyrusManager getInstance() {
		if (instance == null) {
			instance = new PapyrusManager();
		}
		return instance;
	}
	
	private static void initNotifier() {
		handlerInstance = EmfRefactorNotificationHandler.getInstance();
	}

	public Diagram getDiagram() {
		return diagram;
	}

	public void setDiagram(Diagram diagram) {
		this.diagram = diagram;
		handlerInstance.signUp(diagram);
	}
}
