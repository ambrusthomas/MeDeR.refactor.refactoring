/*
 * 
 */
package comrel.diagram.edit.helpers;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;

import comrel.CompositeRefactoring;
import comrel.Helper;
import comrel.HelperUnit;
import comrel.SingleFilterUnit;
import comrel.diagram.edit.commands.custom.CustomDestroyElementCommand;

/**
 * @generated
 */
public class SingleFilterUnitEditHelper extends ComrelBaseEditHelper {

	@Override
	protected ICommand getDestroyElementCommand(DestroyElementRequest req) {
		if (req.getElementToDestroy() instanceof HelperUnit) {
			HelperUnit context = (HelperUnit) req.getElementToDestroy();
			Helper helper = ((SingleFilterUnit) context)
					.getSingleFilterHelper();
			CompositeRefactoring cr = (CompositeRefactoring) helper
					.eContainer();
			if (cr != null) {
				if (!cr.helperInUse(helper, context)) {
					return new CustomDestroyElementCommand(req, helper);
				}
			}
		}
		return super.getDestroyElementCommand(req);
	}

}
