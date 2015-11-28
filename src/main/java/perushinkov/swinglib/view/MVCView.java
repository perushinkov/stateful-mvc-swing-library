package perushinkov.swinglib.view;

import perushinkov.swinglib.model.MVCModel;
import perushinkov.swinglib.model.RootModel;

import javax.swing.*;

/**
 * Each MVCView represents a state graph. 
 * The graph consists of states and transitions between them.
 * The view must know its current state and should be able to
 * transition based on a transitionCode.
 * 
 * Each MVCView can hold other MVCViews and can delegate events 
 * both to its parent and to its children. For instance:
 * 		On A_LOGIN event the Login view tells its parent the 
 * 		Master view to replace it with a Switcher view.
 * 
 * The state graph of each view is placed before its class.
 * 
 * Depending on how they're made, certain views can be instantiated 
 * more than once and used by other views without needing knowledge 
 * of them.
 * 
 * @author eglavchev
 *
 */
public abstract class MVCView extends JPanel {
	private static final long serialVersionUID = 1L;
	
	// Should never be associated with an action. Used just to revalidate Swing component tree.
	public static final int A_REVALIDATE = -1;
	
	protected int state;
	
	protected MVCView parent;
	protected RootModel rootModel;
	
	protected MVCView(MVCView parent, RootModel rootModel) {
		super(true);
		this.parent = parent;
		this.rootModel = rootModel;
	}

	/**
	 * Using its current state and the given transition,
	 * the view must move to new UI state
	 * 
	 * @param transitionCode
	 * @return if transition is possible
	 */
	public abstract boolean transition(int transitionCode);
	
	/**
	 * @return current state of view
	 */
	public int getState() {
		return state;
	}
	
	/**
	 * The Controller
	 * 
	 * Holds the definitions of the event-handlers.
	 * Communicates with the MVCModel singleton,
	 * and should change the current view state only
	 * by calls to transition(int transitionCode)
	 */
	public abstract void registerEvents();
	
	/**
	 * Override this method to use MVCView with its own model.
	 */
	public MVCModel getModel() {
	  return null;
	}
	
	/**
	 * If a view is currently blocking, it means the user cannot
	 * navigate away from it. (Perhaps because a vital operation is in progress)
	 */
	public boolean isBlocking() {
	  return false;
	}
}
