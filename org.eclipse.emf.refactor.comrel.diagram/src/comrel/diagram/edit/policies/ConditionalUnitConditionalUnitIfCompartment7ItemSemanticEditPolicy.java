/*
 * 
 */
package comrel.diagram.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;

import comrel.diagram.edit.commands.ConditionCheckCreateCommand;
import comrel.diagram.providers.ComrelElementTypes;

/**
 * @generated
 */
public class ConditionalUnitConditionalUnitIfCompartment7ItemSemanticEditPolicy
		extends ComrelBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	public ConditionalUnitConditionalUnitIfCompartment7ItemSemanticEditPolicy() {
		super(ComrelElementTypes.ConditionalUnit_2006);
	}

	/**
	 * @generated
	 */
	protected Command getCreateCommand(CreateElementRequest req) {
		if (ComrelElementTypes.ConditionCheck_3076 == req.getElementType()) {
			return getGEFWrapper(new ConditionCheckCreateCommand(req));
		}
		return super.getCreateCommand(req);
	}

}
