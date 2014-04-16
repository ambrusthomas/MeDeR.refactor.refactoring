/*
 * 
 */
package comrel.diagram.edit.parts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.AbstractBorderedShapeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IBorderItemEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.BorderItemSelectionEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DragDropEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.figures.BorderItemLocator;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.swt.graphics.Color;

import comrel.diagram.edit.policies.ConditionalUnit4CanonicalEditPolicy;
import comrel.diagram.edit.policies.ConditionalUnit4ItemSemanticEditPolicy;
import comrel.diagram.edit.policies.OpenDiagramEditPolicy;
import comrel.diagram.part.ComrelVisualIDRegistry;

/**
 * @generated
 */
public class ConditionalUnit4EditPart extends AbstractBorderedShapeEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 3066;

	/**
	 * @generated
	 */
	protected IFigure contentPane;

	/**
	 * @generated
	 */
	protected IFigure primaryShape;

	/**
	 * @generated
	 */
	public ConditionalUnit4EditPart(View view) {
		super(view);
	}

	/**
	 * @generated
	 */
	protected void createDefaultEditPolicies() {
		installEditPolicy(EditPolicyRoles.CREATION_ROLE,
				new CreationEditPolicy());
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
				new ConditionalUnit4ItemSemanticEditPolicy());
		installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE,
				new DragDropEditPolicy());
		installEditPolicy(EditPolicyRoles.CANONICAL_ROLE,
				new ConditionalUnit4CanonicalEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
		installEditPolicy(EditPolicyRoles.OPEN_ROLE,
				new OpenDiagramEditPolicy());
		// XXX need an SCR to runtime to have another abstract superclass that would let children add reasonable editpolicies
		// removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CONNECTION_HANDLES_ROLE);
	}

	/**
	 * @generated
	 */
	protected LayoutEditPolicy createLayoutEditPolicy() {
		org.eclipse.gmf.runtime.diagram.ui.editpolicies.LayoutEditPolicy lep = new org.eclipse.gmf.runtime.diagram.ui.editpolicies.LayoutEditPolicy() {

			protected EditPolicy createChildEditPolicy(EditPart child) {
				View childView = (View) child.getModel();
				switch (ComrelVisualIDRegistry.getVisualID(childView)) {
				case SingleInputPort8EditPart.VISUAL_ID:
				case MultiInputPort9EditPart.VISUAL_ID:
					return new BorderItemSelectionEditPolicy();
				}
				EditPolicy result = child
						.getEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE);
				if (result == null) {
					result = new NonResizableEditPolicy();
				}
				return result;
			}

			protected Command getMoveChildrenCommand(Request request) {
				return null;
			}

			protected Command getCreateCommand(CreateRequest request) {
				return null;
			}
		};
		return lep;
	}

	/**
	 * @generated
	 */
	protected IFigure createNodeShape() {
		return primaryShape = new ConditionalUnitFigure();
	}

	/**
	 * @generated
	 */
	public ConditionalUnitFigure getPrimaryShape() {
		return (ConditionalUnitFigure) primaryShape;
	}

	/**
	 * @generated
	 */
	protected boolean addFixedChild(EditPart childEditPart) {
		if (childEditPart instanceof ConditionalUnitNameType4EditPart) {
			((ConditionalUnitNameType4EditPart) childEditPart)
					.setLabel(getPrimaryShape()
							.getFigureConditionalUnitLabelFigure());
			return true;
		}
		if (childEditPart instanceof ConditionalUnitConditionalUnitIfCompartment3EditPart) {
			IFigure pane = getPrimaryShape()
					.getConditionalUnitIfCompartmentFigure();
			setupContentPane(pane); // FIXME each comparment should handle his content pane in his own way 
			pane.add(((ConditionalUnitConditionalUnitIfCompartment3EditPart) childEditPart)
					.getFigure());
			return true;
		}
		if (childEditPart instanceof ConditionalUnitConditionalUnitHelperUnitsCompartment3EditPart) {
			IFigure pane = getPrimaryShape()
					.getConditionalUnitHelperUnitsCompartmentFigure();
			setupContentPane(pane); // FIXME each comparment should handle his content pane in his own way 
			pane.add(((ConditionalUnitConditionalUnitHelperUnitsCompartment3EditPart) childEditPart)
					.getFigure());
			return true;
		}
		if (childEditPart instanceof ConditionalUnitConditionalUnitThenCompartment3EditPart) {
			IFigure pane = getPrimaryShape()
					.getConditionalUnitThenCompartmentFigure();
			setupContentPane(pane); // FIXME each comparment should handle his content pane in his own way 
			pane.add(((ConditionalUnitConditionalUnitThenCompartment3EditPart) childEditPart)
					.getFigure());
			return true;
		}
		if (childEditPart instanceof ConditionalUnitConditionalUnitElseCompartment3EditPart) {
			IFigure pane = getPrimaryShape()
					.getConditionalUnitElseCompartmentFigure();
			setupContentPane(pane); // FIXME each comparment should handle his content pane in his own way 
			pane.add(((ConditionalUnitConditionalUnitElseCompartment3EditPart) childEditPart)
					.getFigure());
			return true;
		}
		if (childEditPart instanceof SingleInputPort8EditPart) {
			BorderItemLocator locator = new BorderItemLocator(getMainFigure(),
					PositionConstants.NONE);
			getBorderedFigure().getBorderItemContainer().add(
					((SingleInputPort8EditPart) childEditPart).getFigure(),
					locator);
			return true;
		}
		if (childEditPart instanceof MultiInputPort9EditPart) {
			BorderItemLocator locator = new BorderItemLocator(getMainFigure(),
					PositionConstants.NONE);
			getBorderedFigure().getBorderItemContainer().add(
					((MultiInputPort9EditPart) childEditPart).getFigure(),
					locator);
			return true;
		}
		return false;
	}

	/**
	 * @generated
	 */
	protected boolean removeFixedChild(EditPart childEditPart) {
		if (childEditPart instanceof ConditionalUnitNameType4EditPart) {
			return true;
		}
		if (childEditPart instanceof ConditionalUnitConditionalUnitIfCompartment3EditPart) {
			IFigure pane = getPrimaryShape()
					.getConditionalUnitIfCompartmentFigure();
			setupContentPane(pane); // FIXME each comparment should handle his content pane in his own way 
			pane.remove(((ConditionalUnitConditionalUnitIfCompartment3EditPart) childEditPart)
					.getFigure());
			return true;
		}
		if (childEditPart instanceof ConditionalUnitConditionalUnitHelperUnitsCompartment3EditPart) {
			IFigure pane = getPrimaryShape()
					.getConditionalUnitHelperUnitsCompartmentFigure();
			setupContentPane(pane); // FIXME each comparment should handle his content pane in his own way 
			pane.remove(((ConditionalUnitConditionalUnitHelperUnitsCompartment3EditPart) childEditPart)
					.getFigure());
			return true;
		}
		if (childEditPart instanceof ConditionalUnitConditionalUnitThenCompartment3EditPart) {
			IFigure pane = getPrimaryShape()
					.getConditionalUnitThenCompartmentFigure();
			setupContentPane(pane); // FIXME each comparment should handle his content pane in his own way 
			pane.remove(((ConditionalUnitConditionalUnitThenCompartment3EditPart) childEditPart)
					.getFigure());
			return true;
		}
		if (childEditPart instanceof ConditionalUnitConditionalUnitElseCompartment3EditPart) {
			IFigure pane = getPrimaryShape()
					.getConditionalUnitElseCompartmentFigure();
			setupContentPane(pane); // FIXME each comparment should handle his content pane in his own way 
			pane.remove(((ConditionalUnitConditionalUnitElseCompartment3EditPart) childEditPart)
					.getFigure());
			return true;
		}
		if (childEditPart instanceof SingleInputPort8EditPart) {
			getBorderedFigure().getBorderItemContainer().remove(
					((SingleInputPort8EditPart) childEditPart).getFigure());
			return true;
		}
		if (childEditPart instanceof MultiInputPort9EditPart) {
			getBorderedFigure().getBorderItemContainer().remove(
					((MultiInputPort9EditPart) childEditPart).getFigure());
			return true;
		}
		return false;
	}

	/**
	 * @generated
	 */
	protected void addChildVisual(EditPart childEditPart, int index) {
		if (addFixedChild(childEditPart)) {
			return;
		}
		super.addChildVisual(childEditPart, -1);
	}

	/**
	 * @generated
	 */
	protected void removeChildVisual(EditPart childEditPart) {
		if (removeFixedChild(childEditPart)) {
			return;
		}
		super.removeChildVisual(childEditPart);
	}

	/**
	 * @generated
	 */
	protected IFigure getContentPaneFor(IGraphicalEditPart editPart) {
		if (editPart instanceof ConditionalUnitConditionalUnitIfCompartment3EditPart) {
			return getPrimaryShape().getConditionalUnitIfCompartmentFigure();
		}
		if (editPart instanceof ConditionalUnitConditionalUnitHelperUnitsCompartment3EditPart) {
			return getPrimaryShape()
					.getConditionalUnitHelperUnitsCompartmentFigure();
		}
		if (editPart instanceof ConditionalUnitConditionalUnitThenCompartment3EditPart) {
			return getPrimaryShape().getConditionalUnitThenCompartmentFigure();
		}
		if (editPart instanceof ConditionalUnitConditionalUnitElseCompartment3EditPart) {
			return getPrimaryShape().getConditionalUnitElseCompartmentFigure();
		}
		if (editPart instanceof IBorderItemEditPart) {
			return getBorderedFigure().getBorderItemContainer();
		}
		return getContentPane();
	}

	/**
	 * @generated
	 */
	protected NodeFigure createNodePlate() {
		DefaultSizeNodeFigure result = new DefaultSizeNodeFigure(40, 40);
		return result;
	}

	/**
	 * Creates figure for this edit part.
	 * 
	 * Body of this method does not depend on settings in generation model
	 * so you may safely remove <i>generated</i> tag and modify it.
	 * 
	 * @generated
	 */
	protected NodeFigure createMainFigure() {
		NodeFigure figure = createNodePlate();
		figure.setLayoutManager(new StackLayout());
		IFigure shape = createNodeShape();
		figure.add(shape);
		contentPane = setupContentPane(shape);
		return figure;
	}

	/**
	 * Default implementation treats passed figure as content pane.
	 * Respects layout one may have set for generated figure.
	 * @param nodeShape instance of generated figure class
	 * @generated
	 */
	protected IFigure setupContentPane(IFigure nodeShape) {
		if (nodeShape.getLayoutManager() == null) {
			ConstrainedToolbarLayout layout = new ConstrainedToolbarLayout();
			layout.setSpacing(5);
			nodeShape.setLayoutManager(layout);
		}
		return nodeShape; // use nodeShape itself as contentPane
	}

	/**
	 * @generated
	 */
	public IFigure getContentPane() {
		if (contentPane != null) {
			return contentPane;
		}
		return super.getContentPane();
	}

	/**
	 * @generated
	 */
	protected void setForegroundColor(Color color) {
		if (primaryShape != null) {
			primaryShape.setForegroundColor(color);
		}
	}

	/**
	 * @generated
	 */
	protected void setBackgroundColor(Color color) {
		if (primaryShape != null) {
			primaryShape.setBackgroundColor(color);
		}
	}

	/**
	 * @generated
	 */
	protected void setLineWidth(int width) {
		if (primaryShape instanceof Shape) {
			((Shape) primaryShape).setLineWidth(width);
		}
	}

	/**
	 * @generated
	 */
	protected void setLineType(int style) {
		if (primaryShape instanceof Shape) {
			((Shape) primaryShape).setLineStyle(style);
		}
	}

	/**
	 * @generated
	 */
	public EditPart getPrimaryChildEditPart() {
		return getChildBySemanticHint(ComrelVisualIDRegistry
				.getType(ConditionalUnitNameType4EditPart.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	public class ConditionalUnitFigure extends RoundedRectangle {

		/**
		 * @generated
		 */
		private WrappingLabel fFigureConditionalUnitLabelFigure;
		/**
		 * @generated
		 */
		private RectangleFigure fConditionalUnitIfCompartmentFigure;
		/**
		 * @generated
		 */
		private RectangleFigure fConditionalUnitHelperUnitsCompartmentFigure;
		/**
		 * @generated
		 */
		private RectangleFigure fConditionalUnitThenCompartmentFigure;
		/**
		 * @generated
		 */
		private RectangleFigure fConditionalUnitElseCompartmentFigure;

		/**
		 * @generated
		 */
		public ConditionalUnitFigure() {
			this.setCornerDimensions(new Dimension(getMapMode().DPtoLP(8),
					getMapMode().DPtoLP(8)));
			this.setBorder(new MarginBorder(getMapMode().DPtoLP(5),
					getMapMode().DPtoLP(5), getMapMode().DPtoLP(5),
					getMapMode().DPtoLP(5)));
			createContents();
		}

		/**
		 * @generated
		 */
		private void createContents() {

			fFigureConditionalUnitLabelFigure = new WrappingLabel();
			fFigureConditionalUnitLabelFigure.setText("ConditionalUnit");
			fFigureConditionalUnitLabelFigure.setMaximumSize(new Dimension(
					getMapMode().DPtoLP(10000), getMapMode().DPtoLP(50)));

			this.add(fFigureConditionalUnitLabelFigure);

			fConditionalUnitIfCompartmentFigure = new RectangleFigure();
			fConditionalUnitIfCompartmentFigure.setOutline(false);

			this.add(fConditionalUnitIfCompartmentFigure);

			fConditionalUnitHelperUnitsCompartmentFigure = new RectangleFigure();
			fConditionalUnitHelperUnitsCompartmentFigure.setOutline(false);

			this.add(fConditionalUnitHelperUnitsCompartmentFigure);

			fConditionalUnitThenCompartmentFigure = new RectangleFigure();
			fConditionalUnitThenCompartmentFigure.setOutline(false);

			this.add(fConditionalUnitThenCompartmentFigure);

			fConditionalUnitElseCompartmentFigure = new RectangleFigure();
			fConditionalUnitElseCompartmentFigure.setOutline(false);

			this.add(fConditionalUnitElseCompartmentFigure);

		}

		/**
		 * @generated
		 */
		public WrappingLabel getFigureConditionalUnitLabelFigure() {
			return fFigureConditionalUnitLabelFigure;
		}

		/**
		 * @generated
		 */
		public RectangleFigure getConditionalUnitIfCompartmentFigure() {
			return fConditionalUnitIfCompartmentFigure;
		}

		/**
		 * @generated
		 */
		public RectangleFigure getConditionalUnitHelperUnitsCompartmentFigure() {
			return fConditionalUnitHelperUnitsCompartmentFigure;
		}

		/**
		 * @generated
		 */
		public RectangleFigure getConditionalUnitThenCompartmentFigure() {
			return fConditionalUnitThenCompartmentFigure;
		}

		/**
		 * @generated
		 */
		public RectangleFigure getConditionalUnitElseCompartmentFigure() {
			return fConditionalUnitElseCompartmentFigure;
		}

	}

}