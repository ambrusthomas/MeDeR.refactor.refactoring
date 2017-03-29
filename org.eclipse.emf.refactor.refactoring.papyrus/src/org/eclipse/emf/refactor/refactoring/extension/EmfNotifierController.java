package org.eclipse.emf.refactor.refactoring.extension;

import org.eclipse.emf.refactor.refactoring.interfaces.IController;
import org.eclipse.emf.refactor.refactoring.papyrus.handler.EmfRefactorNotificationHandler;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.UMLFactory;

public abstract class EmfNotifierController implements IController {
	
	protected abstract void refactoringBody();
	
	protected Runnable applyRefactoring() {
		return new Runnable() {
			
			@Override
			public void run() {
				
				refactoringBody();
			}
			
		};
		
	}
}
