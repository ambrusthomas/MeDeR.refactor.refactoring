/*******************************************************************************
 * Copyright (c) Philipps University of Marburg. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Philipps University of Marburg - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.refactor.refactoring.runtime.ltk.change;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.refactor.refactoring.papyrus.handler.EmfRefactorNotificationHandler;
import org.eclipse.emf.refactor.refactoring.runtime.ltk.command.RefactoringCommand;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

/**
 * Class used for providing a LTK Change concerning redo
 * and undo functionality.
 * @generated NOT
 * @author Florian Mantz
 */
public class UndoRedoChange extends Change {
	
	/**
	 * Command from which the Change has been invoked.
	 */
	final RefactoringCommand command;
	
	/**
	 * EditingDomain object of the EMF model.
	 */
	final EditingDomain editingDomain;
	
	/**
	 * Name of the UndoRedoChange.
	 */
	final String name;
	
	/**
	 * Root object of the EMF model.
	 */
	final EObject root;
	
	/**
	 * Default constructor. 
	 * @param command Command from which the Change has been invoked.
	 * @param editingDomain EditingDomain object of the EMF model.
	 * @param name Name of the UndoRedoChange.
	 * @param root Root object of the EMF model.
	 */
	public UndoRedoChange(RefactoringCommand command,
			EditingDomain editingDomain, String name, EObject root) {
		super();
		this.command = command;
		this.editingDomain = editingDomain;
		this.name = name;
		this.root = root;
	}

	/**
	 * @see org.eclipse.ltk.core.refactoring.Change#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * @see org.eclipse.ltk.core.refactoring.Change#
	 * initializeValidationData(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void initializeValidationData(IProgressMonitor pm) {
		// do nothing
	}

	/**
	 * @see org.eclipse.ltk.core.refactoring.Change#
	 * isValid(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public RefactoringStatus isValid(IProgressMonitor pm) 
					throws CoreException, OperationCanceledException {
		return new RefactoringStatus();
	}

	/**
	 * @see org.eclipse.ltk.core.refactoring.Change#
	 * perform(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public Change perform(IProgressMonitor pm) throws CoreException {
		System.out.println("START");
		
		editingDomain.getCommandStack().execute(command);

		System.out.println("END");
		EmfRefactorNotificationHandler.getInstance().handleNotifications();
		return new UndoRedoChange(command, editingDomain, name, root);
	}

	/**
	 * @see org.eclipse.ltk.core.refactoring.Change#getModifiedElement()
	 */
	@Override
	public Object getModifiedElement() {
		return root;
	}

}
