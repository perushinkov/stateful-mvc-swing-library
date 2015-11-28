package perushinkov.swinglib.model;

import perushinkov.swinglib.utils.JTableX;
import perushinkov.swinglib.utils.Pair;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a table model that facilitates the editing of name-value pairs.
 * It also provide type-sensitive editors and renderers for the values. In order
 * for that to work properly it is based on JTableX and not JTable.
 * @author eglavchev
 */
public class PairEditorTableModel implements TableModel {

  private List<String> colNames;
  private List<Class<?>> colTypes;
  private List<Object[]> data;

  boolean editable;

  private JTableX table;

  /**
   * @param table the required JTableX table
   * 
   * @param colNames the headers of the columns of the table. 
   * The first column contains checkboxes and lets the user choose which pairs to edit.
   * The second column contains the names of the name-value pairs.
   * The third column contains the values of the name-value pairs and depending on where
   * the first column is checked, it allows value-editing or just renders the parameters.
   * 
   * @param names the names for the second column
   * @param values the values for the third column. ParamValue contains type information.
   */
  public PairEditorTableModel(JTableX table, String[] colNames,
      List<String> names, List<ParamValue> values) throws InvocationTargetException, InterruptedException {
    this.table = table;
    this.editable = true;
    this.colNames = Arrays.asList(colNames);
    this.colTypes = Arrays.asList(Boolean.class, String.class, String.class);
    this.data = new ArrayList<Object[]>();
 
    // NOTE: First editors must be assigned, only then data.
    assignEditorsAndRenderersToTable(values);
    addData(names, values);
    table.setModel(this);
  }

  /**
   * Initializes an empty model with just headers.
   */
  public PairEditorTableModel(JTableX table, String[] colNames) throws InvocationTargetException, InterruptedException {
    this(table, colNames, new ArrayList<String>(), new ArrayList<ParamValue>());
  }

  /**
   * Sets if editor allows editing.
   * The PairEditor can be used in view-only mode or in its default edit-mode.
   * 
   * @param editable
   */
  public void setEditable(boolean editable) {
    this.editable = editable;
  }

  /**
   * @return if editor is in edit mode.
   */
  public boolean isEditable() {
    return editable;
  }
  
  /**
   * The PairEditorModel binds to a table upon creation and cannot bind to another one.
   * @return the table that this model applies to.
   */
  public JTableX getTable() {
    return table;
  }

  /**
   * Analyze values and assign editors and renderers to cells.
   * JTableX is required because JTable does not support different editors in one
   * column.
   * @param paramValues
   */
  private void assignEditorsAndRenderersToTable(List<ParamValue> paramValues) {

    List<TableCellEditor> editors = new ArrayList<TableCellEditor>();
    List<TableCellRenderer> renderers = new ArrayList<TableCellRenderer>();

    for (ParamValue value : paramValues) {
      editors.add(JTableX.mapClassToEditor(value.getType()));
      renderers.add(JTableX.mapClassToRenderer(value.getType()));
    }

    table.setRowEditors(2, editors);
    table.setRowRenderers(2, renderers);
  }

  public int getRowCount() {
    return data.size();
  }

  public int getColumnCount() {
    return colNames.size();
  }

  public String getColumnName(int columnIndex) {
    return colNames.get(columnIndex);
  }

  public Class<?> getColumnClass(int columnIndex) {
    return colTypes.get(columnIndex);
  }

  public boolean isCellEditable(int rowIndex, int columnIndex) {
    boolean isEditable = editable
        && (columnIndex == 0 || ((Boolean) getValueAt(rowIndex, 0))
            && columnIndex == 2);
    return isEditable;
  }

  public Object getValueAt(int rowIndex, int columnIndex) {
    return data.get(rowIndex)[columnIndex];
  }

  public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    data.get(rowIndex)[columnIndex] = aValue;
  }

  /**
   * Used only internally to ensure immutability of PairEditorModel.
   * @param names
   * @param values
   */
  private void addData(List<String> names, List<ParamValue> values) {
    for (int i = 0; i < names.size(); i++) {
      data.add(
        new Object[] { 
          false, 
          names.get(i), 
          values.get(i).getValue()
        }
      );
    }
  }

  public void addTableModelListener(TableModelListener l) {
    // Currently not supported
  }

  public void removeTableModelListener(TableModelListener l) {
    // Currently not supported
  }

  /**
   * Returns the edited pairs.
   * If nullspacers is not set a list of only the selected-for-editing pairs is returned,
   * else a list of all the pairs is returned, where the ones not selected for editing are
   * given null values.
   * 
   * The latter option is useful, because information about pair indices is  retained.
   *  
   * @param nullSpacers
   * @return
   */
  public List<Pair> getSelectedPairs(boolean nullSpacers) {
    List<Pair> list = new ArrayList<Pair>();

    for (Object[] row : data) {
      if ((Boolean) row[0]) {
        list.add(new Pair(row[1].toString(), JTableX.getStringFor(row[2])));
      } else if (nullSpacers) {
        list.add(null);
      }
    }

    return list;
  }

  /**
   * Sets which pairs are selected for editing.
   * @param selected
   */
  public void setSelected(List<Boolean> selected) {
    for (int i = 0; i < selected.size(); i++) {
      data.get(i)[0] = selected.get(i);
    }
  }
}
