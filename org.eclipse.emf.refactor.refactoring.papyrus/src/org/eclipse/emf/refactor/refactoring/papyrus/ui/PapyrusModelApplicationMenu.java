package org.eclipse.emf.refactor.refactoring.papyrus.ui;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.refactor.refactoring.configuration.managers.ConfigurationManager;
import org.eclipse.emf.refactor.refactoring.core.Refactoring;
import org.eclipse.emf.refactor.refactoring.managers.ProjectManager;
import org.eclipse.emf.refactor.refactoring.papyrus.Activator;
import org.eclipse.emf.refactor.refactoring.papyrus.managers.PapyrusSelectionManager;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class PapyrusModelApplicationMenu extends ContributionItem {

	private final List<EObject> selection;
	private final List<IGraphicalEditPart> editParts;
	private final String iconDir = "icons/";
	private final String iconFile = "refactoring.png";
	private Image image;

	public PapyrusModelApplicationMenu() {
		selection = PapyrusSelectionManager.getENotationSelection();
		editParts = PapyrusSelectionManager.getEditParts();
		try {
			image = new Image(getDisplay(), getFullPath(iconDir + iconFile));
		} catch (Exception e) {
			image = null;
		}
		System.out.println("PapyrusNotationApplicationMenu::selection: " +  selection);
	}

	public PapyrusModelApplicationMenu(String id) {
		super(id);
		selection = PapyrusSelectionManager.getENotationSelection();
		editParts = PapyrusSelectionManager.getEditParts();
		try {
			image = new Image(getDisplay(), getFullPath(iconDir + iconFile));
		} catch (Exception e) {
			image = null;
		}
		System.out.println("PapyrusNotationApplicationMenu::selection: " +  selection);
	}
	
	@Override
	public void fill(Menu menu, int index) {			
		ConfigurationManager.getInstance();
		IProject project = ProjectManager.getActualProject();
		LinkedList<Refactoring> refactorings = 
				ConfigurationManager.getSelectedRefactorings(project);	
		for(final Refactoring r : refactorings) {
			if(r.getGui().showInMenu(this.selection)){	
				MenuItem menuItem = new MenuItem(menu, SWT.CHECK, menu.getItemCount());
				menuItem.setText(r.getName());
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
				if (image != null) {
					menuItem.setImage(image);
				}
			}			
		}	
	}
	
	private String getFullPath(String path){
		URL url = FileLocator.find(Activator.getDefault().getBundle(), new Path(path), Collections.EMPTY_MAP);
		URL fileUrl = null;
		try {
			fileUrl = FileLocator.toFileURL(url);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return fileUrl.getPath();
	}
	
	public static Display getDisplay() {
	      Display display = Display.getCurrent();
	      //may be null if outside the UI thread
	      if (display == null)
	         display = Display.getDefault();
	      return display;		
	   }
}