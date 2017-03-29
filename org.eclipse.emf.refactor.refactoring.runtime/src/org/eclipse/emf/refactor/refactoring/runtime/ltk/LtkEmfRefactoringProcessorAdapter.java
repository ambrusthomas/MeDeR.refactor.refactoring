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
package org.eclipse.emf.refactor.refactoring.runtime.ltk;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.refactor.refactoring.core.Refactoring;
import org.eclipse.emf.refactor.refactoring.managers.RefactoringManager;
import org.eclipse.emf.refactor.refactoring.runtime.ltk.change.RefactoringChange;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant;
import org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor;
import org.eclipse.ltk.core.refactoring.participants.SharableParticipants;

/**
 * Adapter class of a LTK RefactoringProcessor used for
 * EMF model refactorings.
 * @generated NOT
 * @author Florian Mantz
 */
public class LtkEmfRefactoringProcessorAdapter 
							extends RefactoringProcessor {

	/**
	 * Id of the RefactoringProcessor.
	 */
	protected final String id;
	
	/**
	 * Runnable that executes the model refactoring.
	 */
	protected final Runnable refactoringAction;
	
	/**
	 * ChangeDescription object for model refactoring execution.
	 */
	protected final ChangeDescription changeDescription;
	
	/**
	 * Given selection objects the model refactoring shall 
	 * be executed on.
	 */
	protected final List<EObject> selection;
	
	/**
	 * Root object of the EMF model.
	 */
	protected final EObject root;
	
	/**
	 * EditingDomain object of the EMF model.
	 */
	protected EditingDomain editingDomain;
	
	public EditingDomain getEditingDomain() {
		return editingDomain;
	}
	
	public void setEditingDomain(EditingDomain editingDomain) {
		this.editingDomain = editingDomain;
	}

	/**
	 * Private constructor used by other constructors.
	 * @param refactoring EMF Model Refactoring to be executed.
	 * @param selection Given selection objects the model refactoring 
	 * shall be executed on.
	 * @param refactoringAction Runnable that executes the model refactoring.
	 * @param changeDescription ChangeDescription object for model 
	 * refactoring execution.
	 */
	private LtkEmfRefactoringProcessorAdapter
		(Refactoring refactoring, List<EObject> selection, 
			 Runnable refactoringAction, ChangeDescription changeDescription){
			this.id = (null!=refactoring)?refactoring.getId():null;
			this.selection=selection;
			this.root = EcoreUtil.getRootContainer(selection.get(0));
			this.editingDomain = initEditingDomain(selection);	
			this.refactoringAction = refactoringAction;
			this.changeDescription = changeDescription;			
			if (null == this.selection || null == this.root 
								|| null == this.editingDomain){
				throw new RuntimeException
							("RefactoringProcessor could not be created!");
			}
	}
	
	/**
	 * Constructor using a Runnable that executes the model refactoring.
	 * @param refactoring EMF Model Refactoring to be executed.
	 * @param selection Given selection objects the model refactoring 
	 * shall be executed on.
	 * @param refactoringAction Runnable that executes the model refactoring.
	 */
	protected LtkEmfRefactoringProcessorAdapter(Refactoring refactoring, 
			 			List<EObject> selection, Runnable refactoringAction){
		this(refactoring,selection,refactoringAction,null);
	}	
	

	/**
	 * Constructor using a ChangeDescription object for model 
	 * refactoring execution.
	 * @param refactoring EMF Model Refactoring to be executed.
	 * @param selection Given selection objects the model refactoring 
	 * shall be executed on.
	 * @param changeDescription ChangeDescription object for model 
	 * refactoring execution.
	 */
	protected LtkEmfRefactoringProcessorAdapter(Refactoring refactoring, 
			 	List<EObject> selection, ChangeDescription changeDescription){
		this(refactoring,selection,null,changeDescription);
	}
	
	/**
	 * Static method that returns the EditingDomain of a given selection
	 * of EObjects.
	 * @param selection Given selection objects the model refactoring shall 
	 * be executed on.
	 * @return EditingDomain of a given selection of EObjects.
	 */
	private static EditingDomain initEditingDomain(List<EObject> selection) {
		if(null != selection && !selection.isEmpty()){
			EditingDomain editingDomain = 
				AdapterFactoryEditingDomain
							.getEditingDomainFor(selection.get(0));
			System.out.println("====> editingDomain 1: " + editingDomain);
			if (null == editingDomain){
				ResourceSet rset = 
						selection.get(0).eResource().getResourceSet();
				editingDomain = TransactionalEditingDomain.Factory.INSTANCE
													.getEditingDomain(rset);
				System.out.println("====> editingDomain 2: " + editingDomain);
			}
			if (null == editingDomain) {
				ResourceSet rset2 = 
						selection.get(0).eResource().getResourceSet();
				editingDomain = TransactionalEditingDomain.Factory.INSTANCE
											.createEditingDomain(rset2);
				System.out.println("====> editingDomain 3: " + editingDomain);
			}
			return editingDomain;
		}
		throw new RuntimeException("EditingDomain could not be initialized!");
	}
	
	/**
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
	 * getElements()
	 */
	@Override
	public Object[] getElements() {
		return this.selection.toArray();
	}

	/**
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
	 * getIdentifier()
	 */
	@Override
	public String getIdentifier() {
		return getClass().getName();
	}

	/**
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
	 * getProcessorName()
	 */
	@Override
	public String getProcessorName() {
		if (null == id) return "testCase";
		return RefactoringManager.getById(id).getName();
	}

	/**
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
	 * isApplicable()
	 */
	@Override
	public boolean isApplicable() throws CoreException {
		return true;
	}

	/**
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
	 * checkInitialConditions(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public RefactoringStatus checkInitialConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		return checkInitialConditions();
	}
	
	/**
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
	 * checkInitialConditions(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public RefactoringStatus checkInitialConditions() {
		RefactoringStatus result = new RefactoringStatus();
		return result;
	}

	/**
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
	 * checkFinalConditions(org.eclipse.core.runtime.IProgressMonitor, 
	 * org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext)
	 */
	@Override
	public RefactoringStatus checkFinalConditions(IProgressMonitor pm,
			CheckConditionsContext context) throws CoreException,
			OperationCanceledException {
		return checkFinalConditions();
	}
	
	/**
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
	 * checkFinalConditions(org.eclipse.core.runtime.IProgressMonitor, 
	 * org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext)
	 */
	public RefactoringStatus checkFinalConditions() {
		RefactoringStatus result = new RefactoringStatus();
		return result;
	}

	/**
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
	 * createChange(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		return createChange(true);
	}
	
	/**
	 * Creates a RefactoringChange object either with a Runnable object
	 * or a ChangeDescription object for executing a EMF model refactoring.
	 * @param enableChangeRecoder Flag whether the model refactoring
	 * execution shall be recorded.
	 * @return RefactoringChange object for executing a EMF model refactoring.
	 */
	private Change createChange(boolean enableChangeRecoder) {
		RefactoringChange refactoringChange = null;
		if (null != this.changeDescription){
			refactoringChange = 
				new RefactoringChange(this.getProcessorName(), this.root, 
					this.editingDomain, this.changeDescription, null, false);
		} else {
			refactoringChange = 
				new RefactoringChange(this.getProcessorName(), this.root, 
						this.editingDomain, null, this.refactoringAction, 
						enableChangeRecoder);			
		}
		return refactoringChange;
	}

	/**
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor#
	 * loadParticipants(org.eclipse.ltk.core.refactoring.RefactoringStatus, 
	 * org.eclipse.ltk.core.refactoring.participants.SharableParticipants)
	 */
	@Override
	public RefactoringParticipant[] loadParticipants(RefactoringStatus status,
			SharableParticipants sharedParticipants) throws CoreException {
		return new RefactoringParticipant[0];
	}
	
	/**
	 * Method to invoke a model refactoring without recording its execution.
	 * @throws OperationCanceledException 
	 * @throws CoreException
	 */
	public void runWithoutChangeRecorder() 
							throws OperationCanceledException, CoreException{
		createChange(false).perform(new NullProgressMonitor());
	}
	
	/**
	 * Checks the initial and final conditions of the refactoring.
	 * @return true if initial and final check do not return errors, false otherwise
	 */
	public boolean checkConditions(){
		return (checkInitialConditions().isOK() && checkFinalConditions().isOK());
	}

}
