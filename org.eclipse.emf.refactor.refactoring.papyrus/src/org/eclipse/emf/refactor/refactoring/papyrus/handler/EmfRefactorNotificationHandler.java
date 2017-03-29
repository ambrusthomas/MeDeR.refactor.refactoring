package org.eclipse.emf.refactor.refactoring.papyrus.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.impl.BoundsImpl;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Trigger;

public class EmfRefactorNotificationHandler extends AdapterImpl {

	public final static String NOTIFICATION_COMMENT_STRING = "EMF_Refactor";

	private static final String PAPYRUS_EDITOR = "org.eclipse.papyrus.infra.core.papyrusEditor";

	private static EmfRefactorNotificationHandler instance;

	private EmfRefactorNotificationHandler() {
		System.out.println("EMF Notification Handler initialized!");
//		new Thread(myThread).start();
	}

	public static EmfRefactorNotificationHandler getInstance() {
		if (instance == null) {
			instance = new EmfRefactorNotificationHandler();
		}
		return instance;
	}

	private Set<EObject> notifiers = new HashSet<>();
	private Map<EObject, BoundsImpl> boundStack = new HashMap<>();
	private boolean refactorInProgress;
//	private DiagramThread myThread = new DiagramThread();
//	private Diagram diagram;
	
	private List<Notification> unhandledNotifications = new ArrayList<>();

//private Thread waiterThing;

	public void signUp(Diagram diagram) {
		if (diagram == null)
			return;
//		this.diagram = diagram;
		View view = diagram;
		EObject element = view.getElement();
		signUp(element);
	}
	
	public void signUp(EObject element) {
		if (!notifiers.contains(element)) {
			notifiers.add(element);
			element.eAdapters().add(this);
			for (EObject item : element.eContents()) {
				signUp(item);
			}
		}
	}
	
	public void handleNotifications() {
		synchronized (unhandledNotifications) {
			final List<Notification> notifications = new ArrayList<>(unhandledNotifications);
			unhandledNotifications.clear();
		
		
			Display.getDefault().asyncExec(new Runnable() {
				
				@Override
				public void run() {
					for (Notification notification : notifications) {
						handleNotification(notification, 5);
					}
				}
				
			});
		}
	}

	@Override
	public void notifyChanged(Notification notification) {
		if (notification.getEventType() == Notification.ADD) {
			signUp((EObject) notification.getNewValue());
		} else if (notification.getEventType() == Notification.REMOVE) {
			//TODO
		}
		if (!handleRefactoringNotification(notification)) {
			if (refactorInProgress) {
				synchronized (unhandledNotifications) {
					unhandledNotifications.add(notification);
				}
				
			}
//			
				System.out.println(notification);
//				if (notification.getOldValue() != null) {
////					System.out.println(notification.getOldValue() + " " + notification.getNotifier());
//				} else {
////					System.out.println(notification.getNewValue());
//				}
//				Display.getDefault().asyncExec(new Runnable() {
//
//					@Override
//					public void run() {
//						if (waiterThing != null)
//							try {
//								waiterThing.join();
//							} catch (InterruptedException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
						
		}
	}
	private void handleNotification(Notification notification, int retry) {
		switch (notification.getEventType()) {
		case Notification.ADD:
//			System.out.println("ADD");
			if (!addElement(notification.getNewValue()) && retry > 0) {
				Display.getDefault().asyncExec(new Runnable() {

					@Override
					public void run() {
						System.out.println("ÚJRA");
						handleNotification(notification, retry-1);
					}
					
				});
			} 
//			System.out.println("-------------------------------");
			break;
		case Notification.REMOVE:
//			System.out.println("REMOVE");
			removeElement(notification.getOldValue()/*, notification.getNotifier()*/);
//			System.out.println("-------------------------------");
			break;
		case Notification.REMOVE_MANY:
//			System.out.println("REMOVE_MANY");
//			for (EObject element : ((List<EObject>) notification.getOldValue())
			
			try {
				BasicEList<EObject> o = (BasicEList<EObject>) notification.getOldValue();
				for (EObject element : o) {
					removeElement(element);
				}
			} catch (Exception e) {
//				System.out.println("Nem jött be");
			}
//			removeElement(notification.getOldValue()/*, notification.getNotifier()*/);
//			System.out.println("-------------------------------");
			break;
		case Notification.SET:
//			System.out.println("SET");
//			if (notification.getOldValue() != null)
//				removeElement(notification.getOldValue());
//			if (notification.getNewValue() != null)
//				addElement(notification.getNewValue());
//			System.out.println("-----------------------------");
			break;
		default:
//			System.out.println("NOT HANDLED" + notification.getEventType());
//			System.out.println(notification.getOldValue());
			break;
		}
	}

	private void removeElement(Object oldValue/*, Object container*/) {
		EObject removable = (EObject) oldValue;
		removable.eAllContents().forEachRemaining(e -> removeElement(e));

		DiagramEditPart dep = null;
		try {
			dep = getDiagramEditPart();
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (EditPart ep : getAllEditParts(dep)) {
			if (ep instanceof IGraphicalEditPart) {
				IGraphicalEditPart gep = (IGraphicalEditPart) ep;
				if (oldValue.equals(gep.getNotationView().getElement()) && gep.getNotationView().isSetElement()) {

					if (gep.getModel() instanceof Node) {
						System.out.println("NODE");
						Node node = (Node) gep.getModel();

						if (node.getLayoutConstraint() instanceof BoundsImpl)
							boundStack.put(node.getElement(), (BoundsImpl) node.getLayoutConstraint());
						
						DestroyElementRequest request = new DestroyElementRequest(gep.getEditingDomain(), gep.getNotationView(), true);
						DestroyElementCommand command11 = new DestroyElementCommand(request);
						new ICommandProxy(command11).execute();
//						System.out.println("E: " + oldValue);
//						System.out.println("NW: " + gep.getModel());
//						System.out.println(gep.getNotationView().isSetElement());
					} else if (gep.getModel() instanceof Edge) {
						System.out.println("EDGE");
						DeleteCommand cmd = new DeleteCommand(gep.getEditingDomain(), gep.getNotationView());
						ICommandProxy ic = new ICommandProxy(cmd);
						ic.execute();
//						System.out.println(gep.getNotationView().isSetElement());
						
					}
					
				}
			}
		}

	}

	private boolean addElement(Object newValue) {
		if (!(newValue instanceof EObject))
			return true;
		if (newValue instanceof Parameter || newValue instanceof Trigger) {
			return true;
		}
		EObject element = (EObject) newValue;
		
		
		
		signUp(element);
		DropObjectsRequest dor = new DropObjectsRequest();
		dor.setObjects(Arrays.asList(newValue));
		dor.setLocation(new Point(0, 0));

		DiagramEditPart dep = null;
		try {
			dep = getDiagramEditPart();
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (dep != null) {
			IGraphicalEditPart done = null;
			
			for (EditPart ep : getAllEditParts(dep)) {
				if (ep instanceof IGraphicalEditPart) {
					IGraphicalEditPart gep = (IGraphicalEditPart) ep;
					if (gep.getNotationView().getElement() == newValue && gep.getNotationView().isSetElement()) {
						done = gep;
						break;
					}
				}
				
			}
			if (done != null)
				return true;
			
			boolean success = false;
//			System.out.println(newValue);
//			System.out.println("JEJJ");
			for (EditPart ep : getAllEditParts(dep)) {
				if (ep instanceof IGraphicalEditPart) {
					IGraphicalEditPart gep = (IGraphicalEditPart) ep;
					if (gep.getNotationView().getElement() == ((EObject) newValue).eContainer()) {
						Command c = ep.getCommand(dor);
						if (c != null) {
//							System.out.println("DUPLAJEJJ!");
							c.execute();
//							System.out.println(dor.getRequiredDetail());
//							System.out.println(dor.getResult());
							success = dor.getRequiredDetail() != 0; //ep2 != null;
							break;
						}
					}
				}
				
			}
			if (success && newValue instanceof State) {
				System.out.println("ok");
				State st = (State) newValue;
				for (Transition r : st.getOutgoings()) {
					removeElement(r);
					addElement(r);
				}
				for (Transition r : st.getIncomings()) {
					removeElement(r);
					addElement(r);
				}
				for (Object e : st.getRegions().stream().flatMap(r -> r.getOwnedElements().stream()).toArray()) {
					addElement(e);
				}
			}
			if (success && boundStack.containsKey(newValue)) {
				for (EditPart ep : getAllEditParts(dep)) {
					if (ep instanceof IGraphicalEditPart) {
						IGraphicalEditPart gep = (IGraphicalEditPart) ep;
						if (gep.getNotationView().getElement() == newValue && gep.getNotationView().isSetElement()) {
							BoundsImpl current = boundStack.get(newValue);
							Rectangle r = new Rectangle(current.getX(), current.getY(), current.getWidth(), current.getHeight());
							SetBoundsCommand cmd = new SetBoundsCommand(gep.getEditingDomain(), "Set size", gep, r);
							new ICommandProxy(cmd).execute();
							boundStack.remove(newValue);
							break;
						}
					}
					
				}
			}
			return success;
		}
		return true;

	}

	public static List<EditPart> getAllEditParts(EditPart ep) {
		Map<?, ?> registry = ep.getViewer().getEditPartRegistry();
		return registry.values().stream().filter(e -> e instanceof EditPart)
				.map(e -> (EditPart) e)
				.flatMap(e -> {
					List<EditPart> all = new ArrayList<>(e.getChildren());
					all.add(e);
					return all.stream();
				})
				.map(e -> (EditPart) e)
				.collect(Collectors.toList());
	}

	private IEditorPart getActiveEditorPart() throws PartInitException {
		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		IFileEditorInput input = (IFileEditorInput) page.getActiveEditor()
				.getEditorInput();
		IMultiDiagramEditor editor = (IMultiDiagramEditor) IDE.openEditor(page,
				input, PAPYRUS_EDITOR, true);
		IEditorPart iEditorPart = editor.getActiveEditor();

		return iEditorPart;
	}

	private DiagramEditPart getDiagramEditPart() throws PartInitException {
		IEditorPart iEditorPart = getActiveEditorPart();
		DiagramEditPart diagramEditPart = ((IDiagramWorkbenchPart) iEditorPart)
				.getDiagramEditPart();

		return diagramEditPart;
	}

	/**
	 * Checks if a notification was just a signal of starting or stopping the
	 * EMF refactoring.
	 * 
	 * @param notification
	 *            Current notification
	 * @return True, if the notification was an "EMF_Refactor" comment
	 */
	private boolean handleRefactoringNotification(Notification notification) {
		if (NOTIFICATION_COMMENT_STRING.equals(notification.getNewValue())) {
			refactorInProgress = !refactorInProgress;
			return true;
		}
		return false;
	}
	
//	public void incomingRefactors() {
//		incomingRefactors(true);
//	}

//	private SomeRunnable someRunnable;
	
//	public void incomingRefactors(boolean started) {
//		if (started) {
//			if (someRunnable != null) {
//				synchronized (someRunnable.syncObj) {
//					someRunnable.syncObj.notify();
//				}
//			}
//			someRunnable = new SomeRunnable();
//			waiterThing = new Thread(someRunnable);
//			waiterThing.start();
//		} else {
//			synchronized (someRunnable.syncObj) {
//				someRunnable.syncObj.notify();
//			}
//		}
//	}
	
//	private class SomeRunnable implements Runnable {
//		
//		public Object syncObj = new Object();
//
//		@Override
//		public void run() {
//			synchronized (syncObj) {
//				try {
//					syncObj.wait();
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//		
//	}

}
