/*
 * 
 */
package comrel.diagram.edit.parts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.handles.MoveHandle;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.BorderedBorderItemEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IBorderItemEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.BorderItemSelectionEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.figures.BorderItemLocator;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.swt.graphics.Color;

import comrel.InputPort;
import comrel.diagram.edit.parts.SingleInputPortEditPart.SingleInputPortFigure;
import comrel.diagram.edit.policies.SingleInputPort3ItemSemanticEditPolicy;
import comrel.diagram.part.ComrelVisualIDRegistry;
import comrel.diagram.providers.ComrelElementTypes;
import comrel.figures.InputPortFigure;

/**
 * @generated
 */
public class SingleInputPort3EditPart extends BorderedBorderItemEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 3005;

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
	public SingleInputPort3EditPart(View view) {
		super(view);
	}

	/**
	 * @generated
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE,
				getPrimaryDragEditPolicy());
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
				new SingleInputPort3ItemSemanticEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
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
				case SingleInputPortNameType3EditPart.VISUAL_ID:
					return new BorderItemSelectionEditPolicy() {

						protected List createSelectionHandles() {
							MoveHandle mh = new MoveHandle(
									(GraphicalEditPart) getHost());
							mh.setBorder(null);
							return Collections.singletonList(mh);
						}
					};
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
	 * @generated NOT
	 */
	protected IFigure createNodeShape() {
		SingleInputPortFigure figure = new SingleInputPortFigure();
		InputPort port = (InputPort) this.resolveSemanticElement();
		figure.setPort(port);
		return primaryShape = figure;
	}

	/**
	 * @generated
	 */
	public SingleInputPortFigure getPrimaryShape() {
		return (SingleInputPortFigure) primaryShape;
	}

	/**
	 * @generated
	 */
	protected void addBorderItem(IFigure borderItemContainer,
			IBorderItemEditPart borderItemEditPart) {
		if (borderItemEditPart instanceof SingleInputPortNameType3EditPart) {
			BorderItemLocator locator = new BorderItemLocator(getMainFigure(),
					PositionConstants.SOUTH);
			locator.setBorderItemOffset(new Dimension(-20, -20));
			borderItemContainer.add(borderItemEditPart.getFigure(), locator);
		} else {
			super.addBorderItem(borderItemContainer, borderItemEditPart);
		}
	}

	/**
	 * @generated
	 */
	protected NodeFigure createNodePlate() {
		DefaultSizeNodeFigure result = new DefaultSizeNodeFigure(20, 20);

		//FIXME: workaround for #154536
		result.getBounds().setSize(result.getPreferredSize());
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
				.getType(SingleInputPortNameType3EditPart.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	public List<IElementType> getMARelTypesOnSource() {
		ArrayList<IElementType> types = new ArrayList<IElementType>(1);
		types.add(ComrelElementTypes.SinglePortMapping_4001);
		return types;
	}

	/**
	 * @generated
	 */
	public List<IElementType> getMARelTypesOnSourceAndTarget(
			IGraphicalEditPart targetEditPart) {
		LinkedList<IElementType> types = new LinkedList<IElementType>();
		if (targetEditPart instanceof SingleInputPortEditPart) {
			types.add(ComrelElementTypes.SinglePortMapping_4001);
		}
		if (targetEditPart instanceof SingleInputPort2EditPart) {
			types.add(ComrelElementTypes.SinglePortMapping_4001);
		}
		if (targetEditPart instanceof comrel.diagram.edit.parts.SingleInputPort3EditPart) {
			types.add(ComrelElementTypes.SinglePortMapping_4001);
		}
		if (targetEditPart instanceof SingleInputPort4EditPart) {
			types.add(ComrelElementTypes.SinglePortMapping_4001);
		}
		if (targetEditPart instanceof SingleInputPort5EditPart) {
			types.add(ComrelElementTypes.SinglePortMapping_4001);
		}
		if (targetEditPart instanceof SingleInputPort6EditPart) {
			types.add(ComrelElementTypes.SinglePortMapping_4001);
		}
		if (targetEditPart instanceof SingleInputPort7EditPart) {
			types.add(ComrelElementTypes.SinglePortMapping_4001);
		}
		if (targetEditPart instanceof SingleInputPort8EditPart) {
			types.add(ComrelElementTypes.SinglePortMapping_4001);
		}
		if (targetEditPart instanceof SingleInputPort9EditPart) {
			types.add(ComrelElementTypes.SinglePortMapping_4001);
		}
		return types;
	}

	/**
	 * @generated
	 */
	public List<IElementType> getMATypesForTarget(IElementType relationshipType) {
		LinkedList<IElementType> types = new LinkedList<IElementType>();
		if (relationshipType == ComrelElementTypes.SinglePortMapping_4001) {
			types.add(ComrelElementTypes.SingleInputPort_2001);
			types.add(ComrelElementTypes.SingleInputPort_3001);
			types.add(ComrelElementTypes.SingleInputPort_3005);
			types.add(ComrelElementTypes.SingleInputPort_3009);
			types.add(ComrelElementTypes.SingleInputPort_3013);
			types.add(ComrelElementTypes.SingleInputPort_3023);
			types.add(ComrelElementTypes.SingleInputPort_3027);
			types.add(ComrelElementTypes.SingleInputPort_3031);
			types.add(ComrelElementTypes.SingleInputPort_3035);
		}
		return types;
	}

	/**
	 * @generated
	 */
	public List<IElementType> getMARelTypesOnTarget() {
		ArrayList<IElementType> types = new ArrayList<IElementType>(2);
		types.add(ComrelElementTypes.SinglePortMapping_4001);
		types.add(ComrelElementTypes.MultiSinglePortMapping_4003);
		return types;
	}

	/**
	 * @generated
	 */
	public List<IElementType> getMATypesForSource(IElementType relationshipType) {
		LinkedList<IElementType> types = new LinkedList<IElementType>();
		if (relationshipType == ComrelElementTypes.SinglePortMapping_4001) {
			types.add(ComrelElementTypes.SingleInputPort_2001);
			types.add(ComrelElementTypes.SingleInputPort_3001);
			types.add(ComrelElementTypes.SingleInputPort_3005);
			types.add(ComrelElementTypes.SingleInputPort_3009);
			types.add(ComrelElementTypes.SingleOutputPort_3011);
			types.add(ComrelElementTypes.SingleInputPort_3013);
			types.add(ComrelElementTypes.SingleOutputPort_3018);
			types.add(ComrelElementTypes.SingleInputPort_3023);
			types.add(ComrelElementTypes.SingleInputPort_3027);
			types.add(ComrelElementTypes.SingleInputPort_3031);
			types.add(ComrelElementTypes.SingleInputPort_3035);
		} else if (relationshipType == ComrelElementTypes.MultiSinglePortMapping_4003) {
			types.add(ComrelElementTypes.MultiInputPort_3002);
			types.add(ComrelElementTypes.MultiInputPort_3006);
			types.add(ComrelElementTypes.MultiInputPort_3010);
			types.add(ComrelElementTypes.MultiInputPort_3014);
			types.add(ComrelElementTypes.MultiOutputPort_3015);
			types.add(ComrelElementTypes.MultiInputPort_3017);
			types.add(ComrelElementTypes.MultiInputPort_3020);
			types.add(ComrelElementTypes.MultiOutputPort_3021);
			types.add(ComrelElementTypes.MultiInputPort_3024);
			types.add(ComrelElementTypes.MultiInputPort_3028);
			types.add(ComrelElementTypes.MultiInputPort_3032);
		}
		return types;
	}

	//	/**
	//	 * @generated
	//	 */
	//	public class SingleInputPortFigure extends RectangleFigure {
	//
	//		/**
	//		 * @generated
	//		 */
	//		public SingleInputPortFigure() {
	//			this.setPreferredSize(new Dimension(getMapMode().DPtoLP(20),
	//					getMapMode().DPtoLP(20)));
	//			this.setBorder(new MarginBorder(getMapMode().DPtoLP(5),
	//					getMapMode().DPtoLP(5), getMapMode().DPtoLP(5),
	//					getMapMode().DPtoLP(5)));
	//		}
	//
	//	}

	/**
	 * @generated NOT
	 */
	public class SingleInputPortFigure extends InputPortFigure {

		/**
		 * @generated
		 */
		public SingleInputPortFigure() {
			this.setPreferredSize(new Dimension(getMapMode().DPtoLP(20),
					getMapMode().DPtoLP(20)));
			this.setBorder(new MarginBorder(getMapMode().DPtoLP(5),
					getMapMode().DPtoLP(5), getMapMode().DPtoLP(5),
					getMapMode().DPtoLP(5)));
		}

	}

}
