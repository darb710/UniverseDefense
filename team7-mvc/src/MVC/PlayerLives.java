package MVC;

import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

public class PlayerLives {
	
	private static Image totalLives;
	private PlayerModel player;
	
	public PlayerLives(PlayerModel player) { 
		this.player = player;
	}

	public Image display_hearts() {
		ImageIcon hearts = null;
		int livesRemaining = this.player.getLives();
		switch (livesRemaining) {
		case 0:
			hearts = new ImageIcon(getClass().getResource("/Images/Lives/0hearts.png"));
			break;
		case 1:
			hearts = new ImageIcon(getClass().getResource("/Images/Lives/1hearts.png"));
			break;
		case 2:
			hearts = new ImageIcon(getClass().getResource("/Images/Lives/2hearts.png"));
			break;
		case 3:
			hearts = new ImageIcon(getClass().getResource("/Images/Lives/3hearts.png"));
			break;
		case 4:
			hearts = new ImageIcon(getClass().getResource("/Images/Lives/4hearts.png"));
			break;
		case 5:
			hearts = new ImageIcon(getClass().getResource("/Images/Lives/5hearts.png"));
			break;
		default:
			hearts = new ImageIcon(getClass().getResource("/Images/Lives/0hearts.png"));
			break;
		}
		BufferedImage bhearts;
		bhearts = UniverseImagePanel.toBufferedImage(hearts.getImage());
		totalLives = UniverseImagePanel.createTransformedImage(bhearts, 0);
		return totalLives;
	}
	
}
