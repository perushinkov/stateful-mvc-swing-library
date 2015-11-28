package perushinkov.swinglib.utils;

import perushinkov.swinglib.model.EnumValue;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.Vector;

public class TableCellEnumValueEditor extends AbstractCellEditor implements TableCellEditor {
	private static final long serialVersionUID = 1L;
	
	JComboBox<String> enumCombo;
	EnumValue<String> enumValue;
	public TableCellEnumValueEditor() {
        enumCombo = new JComboBox<String>();
        enumCombo.addActionListener((a) -> {
        	enumValue.setValue(enumCombo.getSelectedIndex());	
        });
        enumValue = null;
	}

	@Override
	public Object getCellEditorValue() {
		return enumValue;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
												 boolean isSelected, int row, int column) {
		this.enumValue = (EnumValue<String>) value;
		enumCombo.setModel(new DefaultComboBoxModel<String>(new Vector<String>(enumValue.getValues())));
		// TODO: Reuse Model for all cells.
		enumCombo.setSelectedIndex(enumValue.getIndex());
		return enumCombo;
	}

}
