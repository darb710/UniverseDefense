package MVC;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class EnemyView {
	
	private Image display;
	
	public EnemyView(String type) { 
		ImageIcon shipIcon;
		BufferedImage bship;
		if (type.equals("easy"))
			shipIcon = new ImageIcon(getClass().getResource("/Images/Ships/enemy_1.png"));
		else if (type.equals("hard"))
			shipIcon =  new ImageIcon(getClass().getResource("/Images/Ships/enemy_2.png"));
		else shipIcon = new ImageIcon(getClass().getResource("/Images/Ships/exp.png"));
		bship = UniverseImagePanel.toBufferedImage(shipIcon.getImage());
		this.display = UniverseImagePanel.createTransformedImage(bship, 0);
	}
	
	public Image getDisplay() { 
		return this.display;
	}
	
	public void setDisplay(ImageIcon img_icon) { 
		ImageIcon shipIcon = img_icon;
		BufferedImage bship;
		bship = UniverseImagePanel.toBufferedImage(shipIcon.getImage());
		this.display = UniverseImagePanel.createTransformedImage(bship, 0);
	}
	
}
