package MVC;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.io.File;
import java.io.IOException;


import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class UniverseView extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImagePanel startPanel;
	private UniverseImagePanel gamePanel;
	private Font font;
	private String imagePath = "/Images/";
	private JComponent uComponents = new JPanel(new GridLayout(0,1));
	private HighScorePanel highScorePanel;
	private JFrame universeFrame;
	
	static Thread move_thread = null;
	private GameEngine move_object;

	private JMenu gameMenu;
	private JMenu scoreMenu;
	final JMenuItem pauseButton;
	
	private JButton startButton;
	private int displayPane = 1;
	private boolean played = false;
	
	public static Sound backGroundSound = new Sound("bgmusic");
	
	public UniverseView() throws IOException { 
		super("Universe Defense - Typing Game - Team7");
		font = new Font("SanSerif", Font.BOLD, 15);
		this.universeFrame = new JFrame();
		gamePanel = new UniverseImagePanel(new
				ImageIcon(getClass().getResource(imagePath + "Backgrounds/gameBG.jpg")).getImage());
		highScorePanel = new HighScorePanel(new
				ImageIcon(getClass().getResource(imagePath + "Backgrounds/gameBG.jpg")).getImage());
		
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
	    manager.addKeyEventDispatcher(new GeneralDispatch(gamePanel));
	        
		uComponents.setBorder(new EmptyBorder(4,4,4,4));
		universeFrame.setResizable(false);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		gameMenu = new JMenu("Game");
		menuBar.add(gameMenu);
		JMenuItem newGameButton = new JMenuItem("New Game");
		gameMenu.add(newGameButton);

		pauseButton = new JMenuItem("Pause Game");
		gameMenu.add(pauseButton);

		JMenuItem exitButton = new JMenuItem("Exit");
		gameMenu.add(exitButton);
		
		scoreMenu = new JMenu("Score");
		menuBar.add(scoreMenu);
		JMenuItem highScoreButton = new JMenuItem("High Score");
		scoreMenu.add(highScoreButton);
		
		startPanel = new ImagePanel(new 
				ImageIcon(getClass().getResource(imagePath + "Backgrounds/startBG.jpg")).getImage());
		uComponents.add(startPanel);
		startPanel.setLayout(new GridBagLayout());
		
		startButton = new JButton();
		startButton.setIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream(imagePath + "/Buttons/start.gif"))));
		startButton.setOpaque(false);
		startButton.setContentAreaFilled(false);
		startButton.setBorderPainted(false);
		startButton.setFocusPainted(false);
		startButton.setActionCommand("Start Game");		
		
		startPanel.add(startButton);
		
		
		
		
		universeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		universeFrame.setJMenuBar(menuBar);
		universeFrame.setLocationByPlatform(true);
		
		universeFrame.setContentPane(uComponents);
		universeFrame.pack();
		universeFrame.setVisible(true); 
	}
	

	public void displayView(int whichPanel) {
		uComponents.setBorder(new EmptyBorder(4,4,4,4));
		if (displayPane == 1) { 
			uComponents.remove(startPanel);
		} else if (displayPane == 2) {
			uComponents.remove(gamePanel);
		} else if (displayPane == 3) {
			uComponents.remove(highScorePanel);
		}
		
		switch (whichPanel) { 
			case 1: 
				uComponents.add(startPanel);
				displayPane = 1;
				break;
			case 2: 
				uComponents.add(gamePanel);
				displayPane = 2;
				break;
			case 3:
				highScorePanel.repaint();
				uComponents.add(highScorePanel);
				displayPane = 3;
			default: break;
		} 
		
		revalidate();
		repaint();
	}	
	
	/**
	 * Register the controller as the listener to the menu items 
	 * and the buttons.
	 * @param controller The event handler for the calculator
	 */
	public void registerListener(UniverseViewController controller) {
		Component[] components;
		components = gameMenu.getMenuComponents();
		for (Component component : components) {
			if (component instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) component;
				button.addActionListener(controller);
				button.setFont(font);
			}
		}
		
		components = scoreMenu.getMenuComponents();
		for (Component component : components) {
			if (component instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) component;
				button.addActionListener(controller);
				button.setFont(font);
			}
		}
		
		startButton.addActionListener(controller);
	}


	//Start the ships moving when the start button is pressed
	public void start() {
		played = true;
		//The problem was the thread hanging, so I made another thread
		move_object = new GameEngine(gamePanel);
		move_thread = new Thread(move_object);
		backGroundSound.loop();
		gamePanel.init_panel();
		if(!move_thread.isAlive()){
			move_thread.start();
		}
		else {
			move_thread.notify();
		}
	}


	public void stop() {
		move_object.setNewGame(true);
		backGroundSound.stop();
		try {
				move_thread.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IllegalMonitorStateException ime){
			}
		played = false;
			//This is throne, I'll probably fix this with a lock or something
	}
	
	public boolean getPlayed() { 
		return played;
	}
	
	
	
}
