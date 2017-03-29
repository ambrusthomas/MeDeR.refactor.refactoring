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
package org.eclipse.emf.refactor.refactoring.runtime.ui;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.refactor.refactoring.configuration.managers.ConfigurationManager;
import org.eclipse.emf.refactor.refactoring.core.Refactoring;
import org.eclipse.emf.refactor.refactoring.managers.ProjectManager;
import org.eclipse.emf.refactor.refactoring.papyrus.managers.PapyrusManager;
import org.eclipse.emf.refactor.refactoring.papyrus.managers.PapyrusSelectionManager;
import org.eclipse.emf.refactor.refactoring.runtime.config.RuntimeConfig;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.internal.treeproxy.impl.EObjectTreeElementImpl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * Class for invoking EMF model refactoring out from a selection
 * of objects in the workbench.
 * @generated NOT
 * @author Florian Mantz
 */
public class ApplicationMenu extends ContributionItem {
	
	/**
	 * Interface name of GMF editors' graphical edit parts.
	 */
	private static final String GMF_EDITPART_IDENTIFIER = 
		"org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart";
	
	/**
	 * Method of IGraphicalEditPart that returns the corresponding EObject. 
	 */
	private static Method gmfMethod = null;
	
	/**
	 * List of EObjects selected in the workbench. 
	 */
	private final List<EObject> selection;
	
	private final List<IGraphicalEditPart> editParts;
	
	/**
	 * The menu is created each time when it is displayed
	 */
	public ApplicationMenu() {
		ISelection orgSelection = 
			PlatformUI.getWorkbench().getActiveWorkbenchWindow()
									.getSelectionService().getSelection();
		System.out.println("===>>> orgSelection: " + orgSelection);
		
		if (!orgSelection.isEmpty() && ((StructuredSelection) orgSelection).getFirstElement() instanceof IGraphicalEditPart) {
			PapyrusManager.getInstance().setDiagram((Diagram) EcoreUtil.getRootContainer(((IGraphicalEditPart) ((StructuredSelection) orgSelection).getFirstElement()).getNotationView()));
		}
		
		selection = getESelection(orgSelection);
		editParts = PapyrusSelectionManager.getEditParts();
		System.out.println("===>>> selection: " + selection);
	}

	/**
	 * The menu is created each time when it is displayed
	 */
	public ApplicationMenu(String id) {
		super(id);
		ISelection orgSelection = 
			PlatformUI.getWorkbench().getActiveWorkbenchWindow()
									.getSelectionService().getSelection(); 
		selection = getESelection(orgSelection);
		editParts = PapyrusSelectionManager.getEditParts();
	}
	
	/**
	 * Sets the selected EObject list out of the origin selection.
	 * @param selection Origin selection in the workbench.
	 * @return List of selected EObjects.
	 */
	private List<EObject> getESelection(ISelection selection){
		List<EObject> r = new ArrayList<EObject>();
		for(Object o : this.getSelection(selection)){
			if(o instanceof org.eclipse.emf.ecore.EObject){
				r.add((EObject)o);
			}
		}
		return r;
	}
	
	/**
	 * Returns a list of relevant objects from the given selection.
	 * @param selection Origin selection in the workbench.
	 * @return List of relevant objects from the given selection.
	 */
	@SuppressWarnings("rawtypes")
	private Object[] getSelection(ISelection selection) {
	    if(null != selection && selection instanceof StructuredSelection){	    	
	    	StructuredSelection ss = (StructuredSelection) selection;
	    	if(null == ss.getFirstElement()){
	    		return  ss.toArray();
	    	}    	
			List<Object> list = new ArrayList<Object>();
			final Iterator i = ss.iterator();
			while(i.hasNext()){
				final Object selectedEObject = i.next();
				final Class cl = selectedEObject.getClass();
		    	//Usual EMF-Tree:
		    	boolean	added = checkEMFTree(list, selectedEObject);
			    if(!added){
			    	added = checkGmf(list, selectedEObject, cl);
			    }
			}
			return list.toArray();		    													
	    }
	    return new Object[0];
	}	
	
	/**
	 * Checks whether the selected Object is an edit part of an gmf
	 * generated editor and adds the corresponding EObject to the
	 * given list.
	 * @param list List of relevant objects from the given selection.
	 * @param selectedEObject Object to be checked.
	 * @param cl Class of the object to be checked.
	 * @return true if the object was inserted to the list, false
	 * otherwise. 
	 */
	@SuppressWarnings("rawtypes")
	private boolean checkGmf (List<Object> list, 
						final Object selectedEObject, final Class cl) {
		RuntimeConfig config = new RuntimeConfig();
		if(config.isSupportGmfDiagramEditors() && 
			RuntimeConfig.checkIsTypeOf(cl, GMF_EDITPART_IDENTIFIER, true)){
			try {
				ApplicationMenu.gmfMethod = 
						selectedEObject.getClass().getMethod
									("resolveSemanticElement", new Class[0]);
				list.add(ApplicationMenu.gmfMethod.invoke(selectedEObject));
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * Checks whether the given selectedObject is of type EObject
	 * and then adds it to the list.
	 * @param list List of relevant objects from the given selection.
	 * @param selectedEObject Object to be checked.
	 * @return true if the object was inserted to the list, false
	 * otherwise.
	 */
	private boolean checkEMFTree(List<Object> list, 
										final Object selectedEObject) {
		if(selectedEObject instanceof EObject){
			list.add(selectedEObject);
			return true;
		} else if (selectedEObject instanceof EObjectTreeElementImpl) {
			list.add(((EObjectTreeElementImpl) selectedEObject).getEObject());
			return true;
		}
		return false;
	}

	/**
	 * @see org.eclipse.jface.action.ContributionItem#fill
	 * (org.eclipse.swt.widgets.Menu, int)
	 */
	@Override
	public void fill(Menu menu, int index) {			
		ConfigurationManager.getInstance();
		IProject project = ProjectManager.getActualProject();
		LinkedList<Refactoring> refactorings = 
				ConfigurationManager.getSelectedRefactorings(project);		
		for(final Refactoring r : refactorings){
			try {
				if(r.getGui().showInMenu(this.selection)){	
					MenuItem menuItem = new MenuItem(menu, SWT.CHECK, menu.getItemCount());
					menuItem.setText(r.getName());
					menuItem.setEnabled(true);
					menuItem.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							try {
								//1. Set Selection:
								r.getController().setSelection(selection);
								r.getController().setEditParts(editParts);
								//2. Preselect Values:
								r.getController().getDataManagementObject()
														.preselect(selection);							
								//3. Show Refactoring-Gui:
								Shell shell = 
										Display.getDefault().getActiveShell();
								RefactoringWizardOpenOperation dialog = 
									new RefactoringWizardOpenOperation
															(r.getGui().show());
								dialog.run(shell, "Refactoring: " + r.getName());
								
							} catch (Exception e2) {
								MessageDialog
									.openError(null, "Error", e2.getMessage());
							}
						}
					});
				}	
			} catch (Exception e) {
				
			}
		}	
	}
}
