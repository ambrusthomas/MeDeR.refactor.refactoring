package org.eclipse.emf.refactor.refactoring.papyrus.managers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.refactor.refactoring.managers.SelectionManager;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

//@SuppressWarnings("restriction")
public class PapyrusSelectionManager extends SelectionManager {
	
	public static List<IGraphicalEditPart> getGraphicalEditParts() {
		ISelection selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
		
		List<IGraphicalEditPart> graphicalEditParts = new ArrayList<>();
		if (selection instanceof StructuredSelection) {
			List<?> selectionList = ((StructuredSelection) selection).toList();
			selectionList.stream().filter(e -> e instanceof IGraphicalEditPart)
				.map(e -> {
					return (IGraphicalEditPart) e;
				}).forEach(e -> graphicalEditParts.add(e));
		}
		
		return graphicalEditParts;
	}
	
	public static List<IGraphicalEditPart> getEditParts() {
		ISelection selection = 
				PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getSelectionService().getSelection();
		
		List<IGraphicalEditPart> editParts = new ArrayList<>();
		
		if (selection instanceof StructuredSelection) {
			List strSelection = ((StructuredSelection) selection).toList();
			for (Object o : strSelection) {
				if (o instanceof IGraphicalEditPart) {
					editParts.add((IGraphicalEditPart) o);
				}
			}
		}
		
		return editParts;
		
	}
	
	public static List<EObject> getENotationSelection() {
		ISelection selection = 
				PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getSelectionService().getSelection(); 
		List<EObject> r = SelectionManager.getESelection(selection);
		for (Object o : getSelection(selection)) {
			if (o instanceof IGraphicalEditPart) {
		   	System.out.println("instanceof IGraphicalEditPart");
		    	IGraphicalEditPart gep = (IGraphicalEditPart) o;
		    	System.out.println("element: " + gep.resolveSemanticElement());
		    	System.out.println("notation view: " + gep.getNotationView());
		    	System.out.println("notation view element: " + gep.getNotationView().getElement());
		    	EObject root = EcoreUtil.getRootContainer(gep.getNotationView());
		    	System.out.println("root: " + root);
		    	r.add(gep.getNotationView().getElement());
		    	PapyrusManager.getInstance().setDiagram((Diagram) root);
		    	r.add(root);
		    } else if (EMFHelper.getEObject(o) instanceof EObject) {
		    	
		    } else {
		    	return null;
		    }
		}
		return r;
	}
	
	public static List<EObject> getESelection() {
		ISelection selection = 
				PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getSelectionService().getSelection(); 
		return getESelection(selection);
	}

	public static List<EObject> getESelection(ISelection selection) {
		if (selection == null)
			return null;
		List<EObject> r = SelectionManager.getESelection(selection);
		for (Object o : getSelection(selection)) {
//			if (o instanceof ModelElementItem) {
//				System.out.println("instanceof ModelElementItem");
//				ModelElementItem mei = (ModelElementItem) o;
//	    		System.out.println("element: " + mei.getEObject());
//	    		r.add(mei.getEObject());
//			} else {
				if (o instanceof IGraphicalEditPart) {
		    		System.out.println("instanceof IGraphicalEditPart");
		    		IGraphicalEditPart gep = (IGraphicalEditPart) o;
		    		System.out.println("element: " + gep.resolveSemanticElement());
		    		r.add(gep.resolveSemanticElement());
		    		
		    	} else {
		    		EObject eObject = EMFHelper.getEObject(o);
		    		if (eObject != null) {
		    				r.add(eObject);
		    		}
		    	}
//			}
		}
		return r;
	}

	public static void getDiagram() {
		IWorkbenchWindow[] iwws = PlatformUI.getWorkbench().getWorkbenchWindows();
		for (IWorkbenchWindow iww : iwws) {
			System.out.println("IWorkbenchWindow: " + iww);
			IWorkbenchPage[] iwps = iww.getPages();
			for (IWorkbenchPage iwp : iwps) {
				System.out.println("IWorkbenchPage: " + iwp);
			}
		}
	}
	
}
