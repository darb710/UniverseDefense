package MVC;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UniverseViewController implements ActionListener{
	private UniverseView view;
	private UniverseModel model;
	
	public UniverseViewController(UniverseView view, UniverseModel model) { 
		this.view = view;
		this.model = model;
	}
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String command = e.getActionCommand();
		if (command.equals("Exit")) { 
			System.exit(1);
		} else if (command.equals("Start Game")){
			model.switchView(2);			
			view.displayView(model.whichView());
			
			view.start();
		} else if (command.equals("New Game")){
			model.switchView(1);
			view.displayView(model.whichView());
			if (view.getPlayed()) view.stop();
		} else if (command.equals("High Score")){ 
			model.switchView(3);
			view.displayView(model.whichView());
			
			boolean played = view.getPlayed();
			if (played) view.stop();
		} else if (command.equals("Pause Game") || command.equals("Unpause Game")) {
			if(!GameEngine.paused.get()) {
                view.pauseButton.setText("Unpause Game");
				GameEngine.paused.set(true);
            } else {
            	view.pauseButton.setText("Pause Game");
            	GameEngine.paused.set(false);
                // Resume
                synchronized(UniverseView.move_thread) {
                    UniverseView.move_thread.notify();
                }
            }
		}
	}

}
