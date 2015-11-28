package perushinkov.swinglib.utils;


import javax.swing.JComponent;

public class PaneTabInfo {
	private String title;
	private JComponent component;
	private String tip;
	
	public PaneTabInfo(String title, String tip, JComponent component) {
		this.title = title;
		this.component = component;
		this.tip = tip;
	}

	public String getTitle() {
		return title;
	}
	
	public JComponent getComponent() {
		return component;
	}

	public String getTip() {
		return tip;
	}	
}
