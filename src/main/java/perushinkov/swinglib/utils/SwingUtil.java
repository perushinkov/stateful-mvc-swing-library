package perushinkov.swinglib.utils;

import javax.swing.*;

/**
 * Encompasses verbose swing functionality that is used often.
 * Currently contains just alert(String message)
 * @author eglavchev
 *
 */
public class SwingUtil {
  
	/**
	 * Alerts user with a message using invokeLater
	 * in order to not block current thread.
	 * @param message
	 */
	public static void alert(String message) {
		SwingUtilities.invokeLater(() -> {
			JOptionPane.showMessageDialog(null, message);
		});
	}
	
  public static void setBtnState(JButton btn, boolean ifVisible, boolean ifEnabled, String text) {
    btn.setVisible(ifVisible);
    btn.setEnabled(ifEnabled);
    btn.setText(text);
  }
}
