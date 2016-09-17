package MVC;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

/**
 * This is a class to handle the key pressed events from every panel and such.
 *
 */
public class GeneralDispatch implements KeyEventDispatcher {

	//The panel to update with the chars we pressed
	UniverseImagePanel working_panel;
	
	public GeneralDispatch(UniverseImagePanel gamePanel) {
		working_panel = gamePanel;
	}

	/**
	 * This handles the key event, no listener needed
	 */
	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		if (e.getID() == KeyEvent.KEY_PRESSED) {
            ;
        } else if (e.getID() == KeyEvent.KEY_RELEASED) {
            ;
        } else if (e.getID() == KeyEvent.KEY_TYPED) {
            working_panel.key_handler(e.getKeyChar());
        }
        return false;
	}

}
