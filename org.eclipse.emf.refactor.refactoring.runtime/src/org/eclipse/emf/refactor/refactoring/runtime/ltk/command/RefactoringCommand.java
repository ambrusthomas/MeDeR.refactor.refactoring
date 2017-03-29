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
package org.eclipse.emf.refactor.refactoring.runtime.ltk.command;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.ecore.change.util.ChangeRecorder;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.refactor.refactoring.papyrus.handler.EmfRefactorNotificationHandler;

/**
 * Class used for providing a command that executes a EMF model refactoring.
 * 
 * @generated NOT
 * @author Florian Mantz
 */
public class RefactoringCommand extends AbstractCommand {

	/**
	 * Root of the corresponding EMF model.
	 */
	private final EObject rootObject;

	/**
	 * Runnable that executes the model refactoring.
	 */
	private final Runnable refactoringToExecute;

	/**
	 * Flag that indicated whether the model refactoring execution shall be
	 * recorded.
	 */
	private final boolean enableChangeRecorder;

	/**
	 * Change description object for executing the model refactoring. If this
	 * object is not available before refactoring execution it will be get by
	 * recording the refactoring execution.
	 */
	private ChangeDescription changeDescription;

	/**
	 * Contructor creating a command using a Runnable object for model
	 * refactoring execution.
	 * 
	 * @param name
	 *            Name of the command.
	 * @param executeRefactoring
	 *            Runnable object for model refactoring execution.
	 * @param rootObject
	 *            Root of the corresponding emf model.
	 * @param enableChangeRecorder
	 *            Flag whether the model refactoring execution shall be
	 *            recorded.
	 */
	public RefactoringCommand(String name, Runnable executeRefactoring,
			EObject rootObject, boolean enableChangeRecorder) {
		super(name);
		this.refactoringToExecute = executeRefactoring;
		this.rootObject = rootObject;
		this.enableChangeRecorder = enableChangeRecorder;
		if (enableChangeRecorder && null == this.rootObject) {
			throw new IllegalArgumentException(
					"ChangeRecorder is enabled and rootObject is null!");
		}
	}

	/**
	 * Constructor creating a command using a ChangeDescription object for model
	 * refactoring execution.
	 * 
	 * @param name
	 *            Name of the command.
	 * @param description
	 *            ChangeDescription object for model refactoring execution.
	 */
	public RefactoringCommand(String name, ChangeDescription description) {
		super(name);
		this.changeDescription = description;
		this.refactoringToExecute = null;
		this.rootObject = null;
		this.enableChangeRecorder = false;
	}

	/**
	 * @see org.eclipse.emf.common.command.Command#execute()
	 */
	@Override
	public void execute() {
		Notification notification = new NotificationImpl(Notification.SET,
				null,
				EmfRefactorNotificationHandler.NOTIFICATION_COMMENT_STRING);
		EmfRefactorNotificationHandler.getInstance()
				.notifyChanged(notification);

		if (null != changeDescription) {
			changeDescription.applyAndReverse();
		} else {
			try {
				ChangeRecorder changeRecorder = null;
				if (enableChangeRecorder) {
					changeRecorder = new ChangeRecorder(rootObject);
				}
				refactoringToExecute.run();
				if (enableChangeRecorder) {
					changeDescription = changeRecorder.endRecording();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		EmfRefactorNotificationHandler.getInstance()
				.notifyChanged(notification);

	}

	/**
	 * @see org.eclipse.emf.common.command.Command#redo()
	 */
	@Override
	public void redo() {
		this.changeDescription.applyAndReverse();
	}

	/**
	 * @see org.eclipse.emf.common.command.AbstractCommand#undo()
	 */
	@Override
	public void undo() {
		this.changeDescription.applyAndReverse();
	}

	/**
	 * @see org.eclipse.emf.common.command.AbstractCommand#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return true;
	}

	/**
	 * @see org.eclipse.emf.common.command.AbstractCommand#canUndo()
	 */
	@Override
	public boolean canUndo() {
		return null != this.changeDescription;
	}

}
