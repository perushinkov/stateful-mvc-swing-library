package perushinkov.swinglib.utils;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * SwingFactory is used to create customized and useful
 * visual components.
 * <p/>
 * It collaborates closely with the CSS class.
 *
 * @author eglavchev
 */
public class SwingFactory {

    public static JPanel createPanel() {
        JPanel panel = new JPanel();
        CSS.panel(panel);
        return panel;
    }

    public static JList<String> createList(int cellWidth, int visibleRowCount) {
        return createList(cellWidth, 25, visibleRowCount);
    }

    public static JList<String> createList(int cellWidth, int cellHeight, int visibleRowCount) {
        JList<String> list = new JList<String>(new String[]{"List not loaded..."});
        CSS.list(list, cellWidth, cellHeight, visibleRowCount);
        return list;
    }

    public static JList<String> createListRes(int visibleRowCount) {
        JList<String> list = new JList<String>(new String[]{"List not loaded..."});
        CSS.listAutoResize(list, visibleRowCount);
        return list;
    }

    public static JTextField createTextField(String text) {
        JTextField textField = new JTextField(text);
        CSS.textField(textField);
        return textField;
    }

    public static JTextField createPasswordField(String text) {
        JPasswordField passwdField = new JPasswordField(text);
        CSS.textField(passwdField);
        return passwdField;
    }

    public static JTextField createTextField(String text, boolean enabled) {
        JTextField textField = createTextField(text);
        textField.setEnabled(enabled);
        return textField;
    }

    public static JPanel createLabeledInput(String label, JComponent textField) {
        return createXSequence(new JLabel(label), textField);
    }

    /**
     * Allows user to quickly create sequences of elements with
     * predefined padding in between.
     * Its public versions are createXSequence and createYSequence
     */
    private static JPanel createSequence(JComponent... cmps) {
        JPanel pane = createPanel();
        for (JComponent comp : cmps) {
            CSS.align(comp);
            pane.add(SwingFactory.createPadding(comp));
        }
        return pane;
    }

    public static JPanel createXSequence(JComponent... cmps) {
        JPanel pane = createSequence(cmps);
        CSS.boxByAxis(pane, CSS.X_AXIS, cmps.length);
        return pane;
    }

    public static JPanel createYSequence(JComponent... cmps) {
        JPanel pane = createSequence(cmps);
        CSS.boxByAxis(pane, CSS.Y_AXIS, cmps.length);
        return pane;
    }

    public static JButton createButton(String name) {
        JButton btn = new JButton(name);
        CSS.button(btn);
        return btn;
    }

    public static JButton createButton(String name, ActionListener actionListener) {
        JButton btn = createButton(name);
        btn.addActionListener(actionListener);
        return btn;
    }

    public static JButton createButton(String name, boolean enabled) {
        JButton btn = createButton(name);
        btn.setEnabled(enabled);
        return btn;
    }

    /**
     * Just wraps a lightweight padded panel around a component.
     * Orientation does not matter
     */
    public static JPanel createPadding(JComponent component) {
        JPanel pane = createPanel();
        pane.add(component);
        return pane;
    }

    public static <T> JComboBox<T> createCombo(T[] values) {
        JComboBox<T> combo = new JComboBox<T>(values);
        CSS.combo(combo);
        return combo;
    }

    public static JLabel createLabel(String string) {
        JLabel label = new JLabel(string);
        CSS.label(label);
        return label;
    }

    public static JComponent createTitle(String string) {
        JLabel title = new JLabel(string);
        CSS.title(title);
        return title;
    }

    public static JTextArea createTextArea(String text, int rows, int cols) {
        JTextArea textArea = new JTextArea();
        CSS.textArea(textArea, rows, cols);
        return textArea;
    }

    public static JScrollPane createScrollPaneForList(JComponent component) {
        JScrollPane scrollPane = new JScrollPane(component);
        CSS.scrollPaneForList(scrollPane);
        return scrollPane;
    }

    public static JScrollPane createScrollPaneForText(JComponent component) {
        JScrollPane scrollPane = new JScrollPane(component);
        CSS.scrollPaneForText(scrollPane);
        return scrollPane;
    }

    public static JScrollPane createScrollPaneForSize(JComponent component, int width, int height) {
        JScrollPane scrollPane = new JScrollPane(component);
        CSS.scrollPaneForSize(scrollPane, new Dimension(width, height));
        return scrollPane;
    }

    public static JScrollPane createScrollPaneForTable(JComponent component) {
        JScrollPane scrollPane = new JScrollPane(component);
        CSS.scrollPaneForTable(scrollPane);
        return scrollPane;
    }

    public static JDatePickerImpl createDatePicker() {
        JDatePickerImpl someImpl = new JDatePickerImpl(new JDatePanelImpl(new UtilDateModel()));
        CSS.datePicker(someImpl);
        return someImpl;
    }

    public static JTabbedPane createTabs(PaneTabInfo... tabs) {
        JTabbedPane pane = new JTabbedPane();
        CSS.tabbedPane(pane);
        for (int i = 0; i < tabs.length; i++) {
            PaneTabInfo tab = tabs[i];
            pane.insertTab(tab.getTitle(), null, tab.getComponent(), tab.getTip(), i);
        }
        return pane;
    }


    public static PaneTabInfo tab(String title, String tip, JComponent component) {
        return new PaneTabInfo(title, tip, component);
    }

    public static JPanel createHolder(JComponent... components) {
        JPanel panel = createPanel();
        for (JComponent cmp : components) {
            panel.add(cmp);
        }
        CSS.holder(panel);
        return panel;
    }

    public static JScrollPane createScrollPaneForPage(JPanel cmp) {
        JScrollPane pane = new JScrollPane(cmp);
        CSS.scrollPaneForPage(pane);
        return pane;
    }

    public static JScrollPane createScrollPaneMulti(JComponent cmp) {
        JScrollPane pane = new JScrollPane(cmp);
        CSS.scrollPaneMulti(pane);
        return pane;
    }

    public static JPanel createXGrid(JComponent... cmps) {
        JPanel pane = createXSequence(cmps);
        CSS.gridByAxis(pane, CSS.X_AXIS, cmps.length);
        return pane;
    }

    public static JPanel createYGrid(JComponent... cmps) {
        JPanel pane = createXSequence(cmps);
        CSS.gridByAxis(pane, CSS.Y_AXIS, cmps.length);
        return pane;
    }

    public static JPanel createGrid(JComponent[]... cmps) {
        JPanel somePanel = createPanel();

        int rows = cmps.length;
        int cols = rows == 0 ? 0 : cmps[0].length;

        for (JComponent[] row : cmps) {
            for (JComponent cell : row) {
                somePanel.add(SwingFactory.createPadding(cell));
            }
        }

        CSS.grid(somePanel, rows, cols);
        return SwingFactory.createPadding(somePanel);
    }

    public static JTableX createTableX(int width, int height) {
        JTableX table = new JTableX();
        CSS.table(table, width, height);
        return table;
    }

    public static JPanel createFrame(JComponent pane, int width, int height) {
        JPanel panel = createHolder(pane);
        CSS.frame(panel, width, height);
        return panel;
    }

}
