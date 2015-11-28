package perushinkov.swinglib.utils;

import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import perushinkov.swinglib.model.RootModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;

public class TableCellDateEditor extends AbstractCellEditor implements TableCellEditor {
	private static final long serialVersionUID = 1L;
	
	JDatePickerImpl datePicker;
	JButton datePickerButton;
    public TableCellDateEditor() {
        datePicker = SwingFactory.createDatePicker();
        transformDatePicker();
	}
    
	private void transformDatePicker() {
		datePicker.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		datePickerButton = null;
		for (int i = 0; i < datePicker.getComponentCount(); i++) {
			Component cmp = datePicker.getComponent(i);
			if (cmp instanceof JButton) {
				datePickerButton = (JButton)cmp;
			} else if (cmp instanceof JFormattedTextField) {
				cmp.setVisible(false);
				((JFormattedTextField)cmp).getDocument().addDocumentListener(new DocumentListener() {
					@Override
					public void removeUpdate(DocumentEvent e) {update();}
					@Override
					public void insertUpdate(DocumentEvent e) {update();}
					@Override
					public void changedUpdate(DocumentEvent e) {update();}
					
					private void update() {
						updateBtnText();
					}
				});
			}
		}
		if (datePickerButton == null) {
			throw new IllegalStateException("Assumption of JDatePicker structure failed.");
		}
		CSS.buttonTight(datePickerButton);
	}

	@Override
	public Object getCellEditorValue() {
		return datePicker.getModel().getValue();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		Calendar calendar = Calendar.getInstance();
		
		value = value != null ? value : new Date();
		calendar.setTime((Date)value);
		datePicker.getModel().setDate(
				calendar.get(Calendar.YEAR)
				, calendar.get(Calendar.MONTH)
				, calendar.get(Calendar.DAY_OF_MONTH)
				);
		updateBtnText();
		
		return datePicker;
	}

	private void updateBtnText() {
		Date date = (Date)datePicker.getModel().getValue();
		if (date == null) {
			date = new Date();
		}
		String text = RootModel.getApplicationDateFormat().format(date);
		datePickerButton.setText(text);
	}

}
