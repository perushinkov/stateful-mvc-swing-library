package perushinkov.swinglib.utils;

import perushinkov.swinglib.model.EnumValue;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

//TODO: Make singleton
public class TableCellEnumValueRenderer implements TableCellRenderer {
	@SuppressWarnings("unchecked")
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
												   boolean isSelected, boolean hasFocus, int row, int column) {
		return SwingFactory.createLabel(((EnumValue<String>) value).getValue());
	}

}
