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
<pre>
package example;

...;

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
    private static final long serialVersionUID = 1L;

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

    private JComboBox<String> cmdsCombo;
    private JButton logoutBtn;

    private JButton forceLogoutBtn;
    private JPanel cmdPageHolder;

    private MVCView childView;

    private String[] switchables = new String[]{"Subpage A", "Subpage B", "Subpage C"};

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

        this.add(
                SwingFactory.createYSequence(
                        SwingFactory.createXSequence(
                                cmdsCombo
                                , logoutBtn
                                , forceLogoutBtn
                        ), cmdPageHolder
                )
        );
        registerEvents();
    }

    @Override
    public void registerEvents() {
        logoutBtn.addActionListener((e) -> {
            if (childView.isBlocking()) {
                JOptionPane.showMessageDialog(null, "Cannot logout during operation!");
            } else {
                parent.transition(Master.A_LOGOUT);
                new Thread(() -> {
                    try {
                        rootModel.cancel();
                        rootModel.shutdown();
                    } catch (Exception ex) {
                        SwingUtil.alert(ex.getMessage());
                    }
                }).start();
            }
        });

        forceLogoutBtn.addActionListener((e) -> {
            parent.transition(Master.A_LOGOUT);
            try {
                rootModel.cancel();
                rootModel.shutdown();
            } catch (Exception ex) {
                SwingUtil.alert(ex.getMessage());
            }

        });


        cmdsCombo.addActionListener((e) -> {
            String selected = cmdsCombo.getSelectedItem().toString();
            String current = childView.getClass().getSimpleName();
            if (selected.equals(current)) {
                JOptionPane.showMessageDialog(null, "You are already at " + current);
            } else if (childView.getState() != 0) {
                JOptionPane.showMessageDialog(null, "Cannot switch views during operation!");
            } else {
                int transitionValue = Arrays.asList(switchables).indexOf(selected);
                transition(transitionValue);
            }
        });
    }

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


</pre>

NOTE:
I've created a sizeable Swing application using this library, which I won't be able
to share, since it's part of a work project.

I am currently working on a smaller-scale Swing application which will use this library 
and serve as an example (<a href="https://github.com/perushinkov/vocab-tracker">link</a>). I also  intend to add a quite small "Hello world" application
to demonstrate the framework.
