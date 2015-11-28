package perushinkov.swinglib.utils;

import perushinkov.swinglib.model.RootModel;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.Date;

public class TableCellDateRenderer extends JLabel implements TableCellRenderer {
	private static final long serialVersionUID = 1L;
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
												   boolean isSelected, boolean hasFocus, int row, int column) {
		value = value != null ? value : new Date();
	  this.setText(RootModel.getApplicationDateFormat().format((Date)value));


		this.setForeground(isSelected ? new Color(150, 0, 0) : new Color(0, 0, 0));

		if (hasFocus) {
			// bold
			setFont(getFont().deriveFont(getFont().getStyle() | Font.BOLD));
		} else {
			// unbold
			setFont(getFont().deriveFont(getFont().getStyle() & ~Font.BOLD));
		}
		return this;
	}

}
