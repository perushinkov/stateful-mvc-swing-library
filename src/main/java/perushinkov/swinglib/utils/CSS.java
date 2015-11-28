package perushinkov.swinglib.utils;

import net.sourceforge.jdatepicker.impl.JDatePickerImpl;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Just as CSS styles stylize elements with certain classes,
 * so this class serves as a CSS stylesheet. Any visual tinkering
 * and stylization should be placed here.
 *
 * @author eglavchev
 */

public class CSS {
    public static final int X_AXIS = 0;
    public static final int Y_AXIS = 1;

    /**
     * @param panel
     */
    public static void panel(JPanel panel) {
        CSS.
                align(panel);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel.setBorder(new EmptyBorder(2, 2, 2, 2));
    }

    public static void textField(JTextField txtField) {
        txtField.setMaximumSize(new Dimension(150, 30));
        txtField.setPreferredSize(new Dimension(120, 20));
        txtField.setMinimumSize(new Dimension(100, 20));
        //txtField.setBorder(new EmptyBorder(5, 10, 5, 10) );
    }

    public static void button(JButton btn) {
        //btn.setMaximumSize(new Dimension(200, 30));
    }

    public static void buttonTight(JButton button) {
        button.setPreferredSize(null);
        button.setBorder(new EmptyBorder(0, 0, 0, 0));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
    }

    public static void list(JList<String> list, int cellWidth, int cellHeight, int visibleRowCount) {
        list.setBorder(new EmptyBorder(5, 5, 5, 5));
        list.setFixedCellWidth(cellWidth);
        list.setFixedCellHeight(cellHeight);
        list.setVisibleRowCount(visibleRowCount);
    }

    public static void listAutoResize(JList<String> list, int visibleRowCount) {
        list.setBorder(new EmptyBorder(5, 5, 5, 5));
        list.setVisibleRowCount(visibleRowCount);
        list.setFixedCellHeight(25);
    }

    public static <T> void combo(JComboBox<T> combo) {
        combo.setMaximumSize(new Dimension(150, 30));
        combo.setPreferredSize(new Dimension(120, 20));
        combo.setMinimumSize(new Dimension(100, 20));
    }

    public static void title(JLabel title) {
        align(title);
        title.setFont(new Font("Helvetica", Font.BOLD, 30));
    }

    public static void textArea(JTextArea textArea, int rows, int cols) {
        textArea.setBackground(new Color(220, 220, 230));
        textArea.setBorder(new EmptyBorder(10, 10, 10, 10));

        textArea.setRows(rows);
        textArea.setColumns(cols);
    }

    public static void label(JLabel label) {
        //TODO:
    }

    public static void scrollPaneForList(JScrollPane scrollPane) {
        scrollPaneDefault(scrollPane);
    }

    public static void scrollPaneForText(JScrollPane scrollPane) {
        scrollPaneDefault(scrollPane);
    }

    public static void scrollPaneForSize(JScrollPane scrollPane, Dimension dim) {
        scrollPaneDefault(scrollPane);
        scrollPane.setPreferredSize(dim);
    }


    public static void scrollPaneForTable(JScrollPane scrollPane) {
        scrollPaneDefault(scrollPane);
    }

    public static void scrollPaneDefault(JScrollPane scrollPane) {
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
    }


    public static void scrollPaneForPage(JScrollPane cmp) {
        scrollPaneDefault(cmp);
        cmp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    public static void scrollPaneMulti(JScrollPane pane) {
        scrollPaneDefault(pane);
    }


    public static void datePicker(JDatePickerImpl someImpl) {
        someImpl.setMaximumSize(new Dimension(150, 30));
        someImpl.setPreferredSize(new Dimension(150, 30));
        someImpl.setMinimumSize(new Dimension(150, 30));
    }

    public static void tabbedPane(JTabbedPane pane) {

    }

    public static void holder(JPanel panel) {
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
    }

    public static void boxByAxis(JPanel pane, int axis, int length) {
        pane.setLayout(new BoxLayout(pane, axis));
        align(pane);
        pane.setBorder(new EmptyBorder(2, 2, 2, 2));
    }

    public static void gridByAxis(JPanel pane, int axis, int length) {
        pane.setLayout(new BoxLayout(pane, axis));
        align(pane);
        pane.setBorder(new EmptyBorder(2, 2, 2, 2));
    }

    public static void grid(JPanel pane, int rows, int cols) {
        if (rows != 0) {
            pane.setLayout(new GridLayout(rows, cols, 0, 0));
        }
        align(pane);
        pane.setBorder(new EmptyBorder(2, 2, 2, 2));
    }

    public static void align(JComponent comp) {
        comp.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        comp.setAlignmentY(JComponent.CENTER_ALIGNMENT);
    }

    public static void table(JTable table, int width, int height) {
        table.setPreferredScrollableViewportSize(
                new Dimension(width, height)
        );
    }

    public static void frame(JPanel panel, int width, int height) {
        panel.setMaximumSize(new Dimension(width, height));
        panel.setPreferredSize(new Dimension(width, height));
    }


}
