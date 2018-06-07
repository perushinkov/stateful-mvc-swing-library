# stateful-mvc-swing-library
<p>A swing library I use in some of my work projects.</p>

<p>Contains classes that facilitate the easy creation of state-based Swing applications.</p>

<p>I've tried to Javadoc properly all the important classes. Here are the ones you should take a look at:</p>

<ul>
  <li>
    <a href="https://github.com/perushinkov/stateful-mvc-swing-library/blob/master/src/main/java/perushinkov/swinglib/view/MVCView.java">MVCView</a>
  </li>
  <li>
    <a href="https://github.com/perushinkov/stateful-mvc-swing-library/blob/master/src/main/java/perushinkov/swinglib/utils/CSS.java">CSS</a>
  </li>
  <li>
    <a href="https://github.com/perushinkov/stateful-mvc-swing-library/blob/master/src/main/java/perushinkov/swinglib/utils/SwingFactory.java">SwingFactory</a>
  </li>
  plus this nice component:
  <li>
    <a href="https://github.com/perushinkov/stateful-mvc-swing-library/blob/master/src/main/java/perushinkov/swinglib/model/PairEditorTableModel.java">PairEditorTableModel</a>
  </li>
</ul>

<p>Here is a preview of what an example view implementation looks like:<p>

  /*
  This view serves as a switcher between 3 different subviews. It also offers a Logout, and force Logout button.
  It communicates with both its subviews and its parent view.
  */
  package example;
  
  
  /**
   * Each MVCView represents a state graph.
   * The graph consists of states and transitions between them.
   * The view must know its current state and should be able to
   * transition based on a transitionCode.
   *
   * Each MVCView can hold other MVCViews and can delegate events
   * both to its parent and to its children. For instance:
   *        On A_LOGIN event the Login view tells its parent the
   *        Master view to replace it with a Switcher view.
   *
   * The state graph of each view is placed before its class.
   *
   * Depending on how they're made, certain views can be instantiated
   * more than once and used by other views without needing knowledge
   * of them.
   *
   */
  
  /**
   * State machine:
   * <p/>
   * (start)    -------------------> SUBPAGE_A
   * SUBPAGE_A/B----(SUBPAGE_C)----> SUBPAGE_C
   * SUBPAGE_A/C----(SUBPAGE_B)----> SUBPAGE_B
   * SUBPAGE_B/C----(SUBPAGE_A)----> SUBPAGE_A
   * (SUBPAGE_A,
   * SUBPAGE_B,
   * SUBPAGE_C)  ----(LOGOUT/EXIT)---> (end)
   * <p/>
   * children: SUBPAGE_A, SUBPAGE_B, SUBPAGE_C
   */
  public class Switcher extends MVCView {
   private static final long serialVersionUID = 1 L;
  
   // STATES
   public final static int ST_SUBPAGE_A = 0;
   public final static int ST_SUBPAGE_B = 1;
   public final static int ST_SUBPAGE_C = 2;
  
   // TRANSITIONS/ACTIONS
   public final static int A_SUBPAGE_A = 0;
   public final static int A_SUBPAGE_B = 1;
   public final static int A_SUBPAGE_C = 2;
   public final static int A_LOGOUT = 3;
   public final static int A_EXIT = 4;
  
   private JComboBox
    < String > cmdsCombo;
   private JButton logoutBtn;
  
   private JButton forceLogoutBtn;
   private JPanel cmdPageHolder;
  
   private MVCView childView;
  
   private String[] switchables = new String[] {
    "Subpage A",
    "Subpage B",
    "Subpage C"
   };
  
   public Switcher(MVCView parent, RootModel model) {
    super(parent, model);
    CSS.panel(this);
  
    this.state = ST_SUBPAGE_A;
  
    // Defining components before including them in UI trees
  
    cmdsCombo = SwingFactory.createCombo(switchables);
    logoutBtn = SwingFactory.createButton("Log out");
    forceLogoutBtn = SwingFactory.createButton("Log out forcefully");
    childView = new SubpageA(this, model);
    cmdPageHolder = SwingFactory.createYSequence(childView);
  
    // Defining UI tree. (It gets more beautiful with more components, since it remains pretty and readable)
  
    this.add(
     SwingFactory.createYSequence(
      SwingFactory.createXSequence(
       cmdsCombo, logoutBtn, forceLogoutBtn
      ), cmdPageHolder
     )
    );
    registerEvents();
   }
  
   /**
    * The Controller
    *
    * Holds the definitions of the event-handlers.
    * Communicates with the MVCModel singleton,
    * and should change the current view state only
    * by calls to transition(int transitionCode)
    */
   @Override
   public void registerEvents() {
    /*
     * Upon logout-btn-click an attempt is made to stop subview.
     * If unsuccessful, an alert is shown, else logout transition is invoked
     * in parent view.
     */
    logoutBtn.addActionListener((e) -> {
     /**
      * If a view is currently blocking, it means the user cannot
      * navigate away from it. (Perhaps because a vital operation is in progress)
      */
     if (childView.isBlocking()) {
      JOptionPane.showMessageDialog(null, "Cannot logout during operation!");
     } else {
      // Now it's parent's responsibility to destroy switcher view and replace it with perhaps login screen?
      parent.transition(Master.A_LOGOUT);
      new Thread(() -> {
       try {
        rootModel.cancel();
        rootModel.shutdown();
       } catch (Exception ex) {
        // Never allow exceptions in Swing Event-thread!
        SwingUtil.alert(ex.getMessage());
       }
      }).start();
     }
    });
  
    /*
     * Similar to logoutbtn handler, but does not care if subpage view is currently doing something important.
     */
    forceLogoutBtn.addActionListener((e) -> {
     parent.transition(Master.A_LOGOUT);
     try {
      rootModel.cancel();
      rootModel.shutdown();
     } catch (Exception ex) {
      SwingUtil.alert(ex.getMessage());
     }
    });
  
    /**
     * Fetches requested view name, then fetches name of currently active view.
     * If we are already at the desired view, a suitable popup informs the user,
     * Else an attempt at navigation is made. (This is only possible if child view
     * is not in the middle of a blocking operation.)
     *
     * It's considered a good practice to modify the current view only via transition calls.
     */
    cmdsCombo.addActionListener((e) -> {
     String selected = cmdsCombo.getSelectedItem().toString();
     String current = childView.getClass().getSimpleName();
     if (selected.equals(current)) {
      JOptionPane.showMessageDialog(null, "You are already at " + current);
     } else if (childView.isBlocking()) {
      JOptionPane.showMessageDialog(null, "Cannot switch views during operation!");
     } else {
      int transitionValue = Arrays.asList(switchables).indexOf(selected);
      transition(transitionValue);
     }
    });
   }
  
   /**
    * Using its current state and the given transition,
    * the view must move to new UI state
    */
   public synchronized boolean transition(int transitionCode) {
    switch (transitionCode) {
     case A_SUBPAGE_A:
      childView = new SubpageA(this, rootModel);
      break;
     case A_SUBPAGE_B:
      childView = new SubpageB(this, rootModel);
      break;
     case A_SUBPAGE_C:
      childView = new SubpageC(this, rootModel);
      break;
     default:
      JOptionPane.showMessageDialog(null, "Illegal transition");
      return false;
    }
  
    cmdPageHolder.removeAll();
    cmdPageHolder.add(childView);
    revalidate();
    repaint();
    return true;
   }
  
   public int getState() {
    return state;
   }
  }

NOTE:
I've created a sizeable Swing application using this library, which I won't be able
to share, since it's part of a work project.

I am currently working on a smaller-scale Swing application which will use this library 
and serve as an example (<a href="https://github.com/perushinkov/vocab-tracker">link</a>). I also  intend to add a quite small "Hello world" application
to demonstrate the framework.
