package MVC;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class PlayerView {
	private Image ship;
	private float angle;
	
	public PlayerView() { 
		this.angle = 0;
		ImageIcon ship_icon = new ImageIcon(getClass().getResource("/Images/Ships/player.png"));
		BufferedImage bship;
		bship = UniverseImagePanel.toBufferedImage(ship_icon.getImage());
		this.ship = UniverseImagePanel.createTransformedImage(bship, angle);
	}
	
	public Image getDisplay() { 
		return this.ship;
	}
	
	public float getAngle () { 
		return this.angle;
	}
	
	public void setAngle(float angle) { 
		ImageIcon ship_icon = new ImageIcon(getClass().getResource("/Images/Ships/player.png"));
		BufferedImage bship;
		bship = UniverseImagePanel.toBufferedImage(ship_icon.getImage());
		this.ship = UniverseImagePanel.createTransformedImage(bship, angle);
		this.angle = angle;
	}
}
