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
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.refactor.refactoring.papyrus.handler.EmfRefactorNotificationHandler;
import org.eclipse.emf.refactor.refactoring.runtime.ltk.command.RefactoringCommand;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

/**
 * Class used for providing a LTK Change concerning the execution
 * of a specific EMF model refactoring.
 * @generated NOT
 * @author Thorsten Arendt
 */
public class RefactoringChange extends Change {
	
	/**
	 * Name of the RefactoringChange.
	 */
	private final String name;
	
	/**
	 * Root object of the EMF model.
	 */
	private final EObject root;
	
	/**
	 * Copy of the root element of the model (temporary model).
	 */
	private EObject rootCopy;
	
	/**
	 * EditingDomain object of the EMF model.
	 */
	private final EditingDomain editingDomain;
	
	public EditingDomain getEditingDomain() {
		return editingDomain;
	}

	/**
	 * Command that executes the EMF model refactoring.
	 */
	private final RefactoringCommand refactoringCommand;
	
	/**
	 * Default constructor.
	 * @param name Name of the RefactoringChange.
	 * @param root Root object of the EMF model.
	 * @param editingDomain EditingDomain object of the EMF model.
	 * @param changeDescription ChangeDescription object for model 
	 * refactoring execution.
	 * @param refactoringToApply Runnable object for model refactoring 
	 * execution.
	 * @param enableChangeRecorder Flag whether the model refactoring
	 * execution shall be recorded.
	 */
	public RefactoringChange(String name, EObject root, 
			EditingDomain editingDomain, ChangeDescription changeDescription,
			Runnable refactoringToApply, boolean enableChangeRecorder) {
		super();
		this.name = name;
		this.root = root;
		this.rootCopy = this.generateRootCopy();
		this.editingDomain=editingDomain;
		if(null != changeDescription){
			refactoringCommand = new RefactoringCommand(name,changeDescription);
		}else{
			refactoringCommand = new RefactoringCommand
					(name, refactoringToApply, this.root, enableChangeRecorder);			
		}
	}

	/**
	 * @see org.eclipse.ltk.core.refactoring.Change#getName()
	 */
	@Override
	public String getName() {
		return this.name;
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
	public RefactoringStatus isValid(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		return new RefactoringStatus();
	}

	/**
	 * @see org.eclipse.ltk.core.refactoring.Change#
	 * perform(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public Change perform(IProgressMonitor pm) throws CoreException {
//		EmfRefactorNotificationHandler.getInstance().incomingRefactors();
		System.out.println("START");
		
		this.editingDomain.getCommandStack().execute(refactoringCommand);
		System.out.println("END");
		EmfRefactorNotificationHandler.getInstance().handleNotifications();
		return 
			new UndoRedoChange(refactoringCommand, editingDomain, name, root);	
	}

	/**
	 * @see org.eclipse.ltk.core.refactoring.Change#getModifiedElement()
	 */
	@Override
	public Object getModifiedElement() {
		return this.root;
	}

	/**
	 * @see org.eclipse.emf.compare.ui.IModelCompareInputProvider#
	 * getModelCompareInput()
	 */
//	@Override
//	public ModelCompareInput getModelCompareInput() {
//		try {
//			//Perform Refactoring
//			PreviewCommand previewCommand = 
//							new PreviewCommand(refactoringCommand,this.root);
//			this.editingDomain.getCommandStack().execute(previewCommand);
//			//Generate DiffModel:
//			Map<String, Object> options = new HashMap<String, Object>();
//			options.put(MatchOptions.OPTION_IGNORE_XMI_ID, new Boolean(true));
//			MatchModel matchModel = null;
//			try {
//				matchModel = MatchService.doMatch(this.root,
//										previewCommand.getRootCopy(), options);
//				DiffModel diffModel = DiffService.doDiff(matchModel);
//				return new ModelCompareInput(matchModel, diffModel);	
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		throw new RuntimeException("Could not generate DiffModel");
//	}
	
	/**
	 * Generates a copy of the EMF model presented by the root
	 * element.
	 * @return Copy of the EMF model presented by the root
	 * element.
	 */
	private EObject generateRootCopy() {
		Copier copier = new Copier();
		EObject rootCopy =  copier.copy(this.root);
		copier.copyReferences();
		return rootCopy;
	}
	
	/**
	 * Gets a copy of the root element of the model (temporary model).
	 * @return Copy of the root element of the model (temporary model).
	 */
	public EObject getRootCopy() {
		return rootCopy;
	}

	public RefactoringCommand getRefactoringCommand() {
		return refactoringCommand;
	}

}
