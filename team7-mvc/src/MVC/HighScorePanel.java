package MVC;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * 
 * @author Nicolas Vargas
 *
 */
@SuppressWarnings("serial")
public class HighScorePanel extends JPanel {
	
	private Image img;
	private HighScore high_score;
	
	public HighScorePanel(String img) {
		this(new ImageIcon(img).getImage());
		pop_names();
	} 
	
	public HighScorePanel(Image img) {
		this.img = img;
		Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
		setPreferredSize(size);
		setMinimumSize(size);
	    setMaximumSize(size);
	    setSize(size);
	    setLayout(null);
	}
	
	private void pop_names(){
		HighScoreHandle tmp = new HighScoreHandle();
		high_score = tmp.deserial();		
	}	
	
	public void paintComponent(Graphics g) {
	    g.drawImage(img, 0, 0, null);    
	    
	    Font highScorefont = new Font("Eras Bold ITC", Font.BOLD, 30);
	    if(high_score == null)
	    	pop_names();
	    ArrayList<String> high_names = high_score.get_names();
	    ArrayList<Integer> high_scores = high_score.get_scores();
	    int size = high_names.size();
	    
	    g.setFont(highScorefont);
		g.setColor(new Color(153,0,0));
	    g.drawString("HIGH SCORE", 420, 50);	    
	    
	    if(size > 10)
	    	size = 10;
	    
	    for(int i = 0; i < size; i++) {
	    	g.drawString("" + (i+1) + ".", 200, 80+(50*i));
	    	g.drawString(high_names.get(i), 300, 80+(50*i));
	    	g.drawString("" + high_scores.get(i), 600, 80+(50*i));
	    }
	  }
}