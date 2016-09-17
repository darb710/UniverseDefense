package MVC;

import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JOptionPane;

public class GameEngine implements Runnable {
	UniverseImagePanel gamePanel;
	static AtomicBoolean paused;
	private GameModel game;
	private PlayerModel player;
	private volatile boolean newGame = false;
	
	public GameEngine(UniverseImagePanel pan){
		gamePanel = pan;
		paused = new AtomicBoolean(false);
		game = gamePanel.getGameModel();
		player = game.getPlayer();
		newGame = false;
	}
	
	//This starts the ships, etc.
	public void run(){
	
		
		while (!newGame) {
		int enemyCount = 1;
		while((!player.isDead()) && (!newGame)) {
			if(paused.get()) {
				synchronized(UniverseView.move_thread) {
					try {
						UniverseView.move_thread.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
	                }
				}
			}
			int level = game.getLevel(player);
			if (enemyCount <= 5 + (level / 3)) {
				if (this.game.spawnEnemy()) 
					enemyCount++;
			}
			try {
				Thread.sleep(game.getSpeed());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}		
			game.progres_enemy();
			game.check_enemy();
			if (game.getShoot()) 
				game.shoot_bullet();
			if(player.getEnemyKilled() > 0) {
				enemyCount = enemyCount - player.getEnemyKilled();
				player.setEnemyKilled(0);
			
			}
			gamePanel.repaint();
			if ((newGame) || player.isDead()) {
					String inputName = ""; 
					while ((inputName.length() == 0) || (inputName.length() >8)){ 
						inputName = JOptionPane.showInputDialog(gamePanel, "Enter Player Name");
						if (inputName == null) inputName = "AAA";

						if (inputName.length() > 8) {
							JOptionPane.showMessageDialog(gamePanel, "Maximum Character = 8");
						}
					}
					player.setName(inputName);
				player.add_high_score();
				if (player.highScore())
					JOptionPane.showMessageDialog(gamePanel, "NEW HIGH SCORE");
			}
		}
		}
		if (!newGame){
			gamePanel.gameOver();
		}
		else {
			gamePanel.reset();
		}
		gamePanel.repaint();
	}
	
	public GameModel getGameModel() { 
		return game;
	}
	
	public void setNewGame(boolean b) { 
		newGame = b;
	}
}
