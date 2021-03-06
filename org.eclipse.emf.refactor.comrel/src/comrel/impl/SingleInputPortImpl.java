/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package comrel.impl;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import comrel.CompositeRefactoring;
import comrel.ComrelPackage;
import comrel.PortMapping;
import comrel.SingleInputPort;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Single Input Port</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class SingleInputPortImpl extends SinglePortImpl implements SingleInputPort {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SingleInputPortImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ComrelPackage.Literals.SINGLE_INPUT_PORT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean isMappingTarget() {
		EObject container = this.eContainer();
		if (container == null) return true;
		while(container.eContainer() != null) {
			container = container.eContainer();
		}
		CompositeRefactoring refSys;
		if(container instanceof CompositeRefactoring) {
			refSys = (CompositeRefactoring) container;
		}
		else {
			throw new UnsupportedOperationException("Container 'RefactoringSystem' doesn't exists");
		}
		EList<PortMapping> mappings = refSys.getPortMappings();
		for(PortMapping m : mappings) {
			if(m.getTarget() == this) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean isRootPort() {
		EObject container = this.eContainer();
		if (container == null) throw new UnsupportedOperationException("Container doesn't exists");
		if(container.eContainer() instanceof CompositeRefactoring) {
			return true;
		}
		else {
			return false;
		}
	}

} //SingleInputPortImpl
