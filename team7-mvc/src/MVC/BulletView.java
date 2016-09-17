package MVC;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class BulletView {
	private Image bullet;
	
	public BulletView(double angle) { 
		ImageIcon ship_icon = new ImageIcon(getClass().getResource("/Images/Green_laser.png"));
		BufferedImage bship;
		bship = UniverseImagePanel.toBufferedImage(ship_icon.getImage());
		this.bullet = UniverseImagePanel.createTransformedImage(bship, angle);
	}
	
	public Image getDisplay() { 
		return this.bullet;
	}

}