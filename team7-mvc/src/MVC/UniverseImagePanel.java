package MVC;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class UniverseImagePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int SCREEN_WIDTH = 1025;
	private static int SCREEN_HEIGHT = 597;
	
	private Image img;
	private Image ship;
	private Image lives;
	private Image gameOver;
	private String score_text;
	private String level_text;
	
	private GameModel game;
	private ArrayList<EnemyModel> enemyShips;

	public UniverseImagePanel(String img) {
		this(new ImageIcon(img).getImage());
	}

	public UniverseImagePanel(Image img) {
		this.img = img;    
		Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
		game = new GameModel(this);
		enemyShips = game.getEnemyShips();
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
		setLayout(null);    
	}

	/**
	 * This rotates the player to face the incoming ships
	 * @param angle The angle, in degrees, to rotate to
	 */
	public void rotate_player(double angle){
		ship = createTransformedImage((BufferedImage) ship, angle);
	}
	/**
	 * initialize the panel
	 */
	public void init_panel() { 
		display_player();
		display_lives();
		display_score();
		display_level();
	}
	/**
	 * displays player on screen
	 */
	public void display_player() {		
		PlayerModel player = game.getPlayer();
		ship = player.getDisplay(); 
	}

	/**
	 * displays lives on screen
	 */
	public void display_lives() {
		PlayerModel player = game.getPlayer();
		PlayerLives playerLives = new PlayerLives(player);
		lives = playerLives.display_hearts();
	}

	/**
	 * displays score on screen
	 */
	public void display_score() {
		PlayerModel player = game.getPlayer();
		score_text = String.format("Score: %d", player.getScore());
	}
	
	/**
	 * displays the current level on screen
	 */
	public void display_level() {
		PlayerModel player = game.getPlayer();
		level_text = String.format("Level %d", game.getLevel(player));
	}
	
	/**
	 * displays game over on screen
	 */
	public void gameOver() {
		ImageIcon over = new ImageIcon(getClass().getResource("/Images/gameover.png"));
		BufferedImage bover;
		bover = toBufferedImage(over.getImage());
		gameOver = bover;
	}
	
	/**
	 * This resets everything for the new Game
	 */
	public void reset(){
		this.game.reset();
		display_lives();
		gameOver = null;
	}

	/**
	 * Converts a given Image into a BufferedImage
	 * @param img The Image to be converted
	 * @return The converted BufferedImage
	 */
	public static BufferedImage toBufferedImage(Image img)
	{
		if (img instanceof BufferedImage)
		{
			return (BufferedImage) img;
		}

		// Create a buffered image with transparency
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		// Return the buffered image
		return bimage;
	}


	/**
	 * This rotates the image
	 * @param image The buffered Image that you want to rotate
	 * @param angle The angle, in degrees, you wan the ship to do.
	 * @return The rotated Buffered image
	 */
	public static BufferedImage createTransformedImage(BufferedImage image, double angle) {
		angle = angle/90*Math.PI/2;

		//Do some geometry
		double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
		int w = image.getWidth(), h = image.getHeight();
		int neww = (int) Math.floor(w * cos + h * sin), newh = (int) Math.floor(h * cos + w * sin);
		//Create the new buffered image
		BufferedImage result = new BufferedImage(neww, newh, Transparency.TRANSLUCENT);
		Graphics2D g2d = result.createGraphics();
		g2d.translate((neww - w) / 2, (newh - h) / 2);
		g2d.rotate(angle, w / 2, h / 2);
		g2d.drawRenderedImage(image, null);
		g2d.dispose();      
		return result;
	}


	/**
	 * Update everything on the screen
	 */
	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null);
		g.drawImage(ship, 10, (SCREEN_HEIGHT / 2 - 20) , 60, 60, null); 
		g.drawImage(lives, 5, 5, 150, 30, null);
		g.drawImage(gameOver, 280, 120, 500, 300, null);
		
		Font score_level_font = new Font("Eras Bold ITC", Font.BOLD, 30);
		g.setFont(score_level_font);
		g.setColor(new Color(153,0,0));
		g.drawString(score_text, 800, 30);
		g.drawString(level_text, 430, 30);

		Font font = new Font("Tahoma", Font.PLAIN, 20);
		
		for(int i = 0; i < enemyShips.size(); i++){
			EnemyModel enemy = enemyShips.get(i);
			int damage = enemy.getDamage();
			String space = "";
			String enemyWord = enemy.getWord();
			int x = enemy.getX();
			int y = enemy.getY();
			
			for(int spaces = 0; spaces < damage; spaces++){
				space += "  ";
			}
			
			int size = 0;
			// Draw enemy
			if (enemy.getType().equals("easy")) size = 50;
			else size = 80;
			g.drawImage(enemy.getDisplay(), x, y, size, size, null);
			g.setColor(Color.GREEN);
			
			
			if(!enemyWord.equals("")){
				g.setFont(font);
				g.drawString(enemyWord.substring(0, damage), x-5, y + size+10);
				g.setColor(Color.ORANGE);
				g.drawString(space + enemyWord.substring(damage), x-5, y + size+10);
			}	
		}
		
		ArrayList<EnemyModel> enemyDied = game.getEnemyDied();
		
		for(int i = 0; i < enemyDied.size(); i++){
			EnemyModel enemy = enemyDied.get(i);
			int x = enemy.getX();
			int y = enemy.getY();
			
			int size = 50;
			// Draw enemy
			if (enemy.getType().equals("hard")) size = 80;
			g.drawImage(enemy.getDisplay(), x, y, size, size, null);
			g.setColor(Color.GREEN);			

		}
		
		PlayerModel player = game.getPlayer();
		ArrayList<BulletModel> bulletSet = game.getBulletSet();
		
		if (bulletSet.size() > 0) {
			for (BulletModel bullet : bulletSet) { 
				g.drawImage(bullet.getView(), bullet.getX(), bullet.getY(), 
						50, 15, null);
			}
		}
		// Draw eneryBar
		String energyTitle = "HIT SPACE TO USE SUPERPOWER";
		FontMetrics fontMetrics = g.getFontMetrics(font);
		g.setColor(Color.GREEN);
		g.setFont(font);
		int energyLength = fontMetrics.stringWidth(energyTitle);
		g.drawString(energyTitle, (SCREEN_WIDTH / 2) - (energyLength / 2), (SCREEN_HEIGHT - 20));
		int energy = player.getEnergy();
		
		g.drawRect((SCREEN_WIDTH/2) - 100, SCREEN_HEIGHT - 60, 200, 20);
		for (int i = 0; i < energy; i++) {
			g.fillOval((SCREEN_WIDTH/2) - 100 + i*20, SCREEN_HEIGHT - 59, 18, 18);
		}
		if (energy < 10) { 
			for (int i = energy; i < 10; i++) {
				g.drawOval((SCREEN_WIDTH/2) - 100 + i*20, SCREEN_HEIGHT - 59, 18, 18);
			}
		}

	}
	
	public GameModel getGameModel() { 
		return game;
	}
	/**
	 * This is called from the general dispatch. 
	 * @param key The key that was pressed, passed by Dispatcher
	 */
	public void key_handler(char key){
		game.key_handler(key);	
	}

}