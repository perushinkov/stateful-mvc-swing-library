package perushinkov.swinglib.utils;

import perushinkov.swinglib.model.ParamType;
import perushinkov.swinglib.model.RootModel;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Extends the JTable with editor/renderer per specific cell functionality
 * @author Emanuil
 *
 */
public class JTableX extends JTable {
	private static final long serialVersionUID = 1L;
	
	private Map<Integer, List<TableCellEditor>> editors = new TreeMap<Integer, List<TableCellEditor>>();
	private Map<Integer, List<TableCellRenderer>> renderers = new TreeMap<Integer, List<TableCellRenderer>>();
	
	public JTableX(){
		super();
		setDefaultEditor(Date.class, new TableCellDateEditor());
		setDefaultRenderer(Date.class, new TableCellDateRenderer());
	}	
	
	public void setRowEditors(int column, List<TableCellEditor> editors) {
		this.editors.put(column, editors);
	}
	
	public void setRowRenderers(int column, List<TableCellRenderer> renderers) {
		this.renderers.put(column, renderers);
	}
	
	@Override
	public TableCellEditor getCellEditor(int row, int column) {
	  TableCellEditor editor = null;
	  if (editors.containsKey(column)) {
		  List<TableCellEditor> rowOfEditors = editors.get(column);
		  if (rowOfEditors.size() > row) {
		    editor = rowOfEditors.get(row);
		  }
		}
		return editor != null ? editor : super.getCellEditor(row, column);
	}
	
	@Override
	public TableCellRenderer getCellRenderer(int row, int column) {
	  TableCellRenderer renderer = null;
    if (renderers.containsKey(column)) {
      List<TableCellRenderer> rowOfRenderers = renderers.get(column);
      if (rowOfRenderers.size() > row) {
        renderer = rowOfRenderers.get(row);
      }
    }
    return renderer != null ? renderer : super.getCellRenderer(row, column);
	}
	

	//TODO: Static Lib JTableX
	/**
	 * Maps a class to user-defined editor. If null is returned,
	 * JTable default Editor for type @param typeClass is used.
	 * @return the editor
	 */
	public static TableCellEditor mapClassToEditor(ParamType paramType) {
		if (paramType.isEnumerable()) {
			return new TableCellEnumValueEditor();
		} else {
			Class<?> typeClass = paramType.getClassType();
			if (typeClass.equals(Date.class)) {
				return new TableCellDateEditor();
			}
			return null;
		}
	}
	
	// TODO: STatic lib JTableX
	/**
	 * Maps a class to user-defined renderer. If null is returned,
	 * JTable default Renderer for type @param typeClass is used.
	 * @return the renderer
	 */
	public static TableCellRenderer mapClassToRenderer(ParamType paramType) {
		if (paramType.isEnumerable()) {
			return new TableCellEnumValueRenderer();
		} else {
			Class<?> typeClass = paramType.getClassType();
			if (typeClass.equals(Date.class)) {
				return new TableCellDateRenderer();
			}
			return null;
		}
	}
	
//	//TODO: Move to Static Lib
//	public static Object mapClassToDefault(ParamValue paramValue) {
//		if (paramType.isEnumerable()) {
//			return paramType.getEnumerable();
//		} else {
//			Class<?> typeClass = paramType.getClassType();
//			if (typeClass.equals(String.class)) {
//				return new String("");
//			}
//			if (typeClass.equals(Date.class)) {
//				return new Date();
//			}
//			if (typeClass.equals(Double.class)) {
//				return new Double(0);
//			}
//		}
//		return new String("");		
//	}
	/**
	 * Makes sure the values of the different columns are properly stringified for
	 * use in webService
	 * @param object
	 * @return
	 */
	//TODO: STatic Lib JTAbleX
	public static String getStringFor(Object object) {
		if (object instanceof Date) {
			return RootModel.getApplicationDateFormat().format((Date)object);
		}
		return object.toString(); // This covers most types
	}
	//TODO: STatic Lib JTAbleX
	public static Class<? extends Object> mapStringToType(String typeString) {
		switch (typeString) {
		case "varchar2":
			return String.class;
		case "number":
			return Double.class;
		case "date":
			return Date.class;
		}
		return String.class;
	}
}
