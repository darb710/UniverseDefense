package MVC;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameModel {
	
	private final int KILL_POINTS = 100;
	
	private ArrayList<EnemyModel> 	enemyShips;
	private ArrayList<BulletModel> 	bulletSet;
	private ArrayList<String> 		wordCheck;
	private ArrayList<EnemyModel> 	enemyDied;
	
	private UniverseImagePanel 		panel;
	private PlayerModel 			player;
	private WordReader 				word;
	
	private int 					level;
	private int 					speed;
	private int 					numTarget;
	private int 					nthEnemy;
	
	private boolean 				foundTarget = false;
	private boolean 				shoot = false;
	
	private String 					inputString;
	
	private Sound 					bomb1Sound = new Sound("exp1");
	private Sound					bomb2Sound = new Sound("exp2");
	private Sound					correctKeySound = new Sound ("correct_key");
	private Sound					wrongKeySound =	new Sound("wrong_key");
	private Sound					gameOverSound = new Sound("gameover");
	
	public GameModel(UniverseImagePanel panel) { 
		this.level = 1;
		this.speed = 16;
		enemyShips = new ArrayList<EnemyModel>();
		enemyDied = new ArrayList<EnemyModel>();
		player = new PlayerModel();
		word = new WordReader();
		this.panel = panel;
		foundTarget = false;
		wordCheck = new ArrayList<String>();
		bulletSet = new ArrayList<BulletModel>();
		inputString = "";
		nthEnemy = 0;
	}
	
	public int getLevel(PlayerModel player) { 
		int score = player.getScore();
		level = score / 1000 + 1;
		
		return this.level;
	}
	
	public int getSpeed() {
		speed = 20 - level;
		if (speed <=5 ) speed = 5;
		
		return speed;
	}

	
	public void reset() {
		player.reset();
		this.speed = 16;
		this.level = 1;
		enemyShips.clear();
		wordCheck.clear();
		inputString = "";
		nthEnemy = 0;
		bulletSet.clear();
		gameOverSound.stop();
		shoot = false;
	}

	public ArrayList<EnemyModel> getEnemyShips(){ 
		return enemyShips;
	}
	public int enemyCount() { 
		return enemyShips.size();
	}
	public PlayerModel getPlayer(){
		return player;
	}
	/*
	 * This spawns an enemy, to a semi random pattern.
	 */
	public boolean spawnEnemy(){
		
		Random ran = new Random();
		String enemyWord = "";
		Boolean gotWord = false;
		while (!gotWord) { 
			enemyWord = word.getRandomWord(this.level);
			if (!wordCheck.contains(enemyWord)) {
				wordCheck.add(enemyWord);
				gotWord = true;
			}
		} 
		int x = 1000, y = 0;
		String type;
		
		y = ran.nextInt(400) + 35;
		
		boolean[] check_y = new boolean[435];
		Arrays.fill(check_y, false);
		for (EnemyModel enemy : enemyShips) {
			if (enemy.getX() > 900) { 
			int lowBound = enemy.getY() - 50;
			if (enemy.getType().equals("hard")) lowBound -= 15;
			if (lowBound < 35) lowBound = 35;
			int upBound = enemy.getY() + 50;
			if (enemy.getType().equals("hard")) upBound += 15;
			if (upBound > 434) upBound = 434;
			
			for (int i = lowBound; i <= upBound; i++) { 
				check_y[i] = true;
			} }
		}
		
		if (check_y[y]) return false;

		
		if ((this.level > 3) && ( nthEnemy % (10 - level) == 0)) {
			type = "hard";
		} else type = "easy";


		EnemyModel enemy = new EnemyModel(x, y, type, enemyWord);
		nthEnemy ++;
	
		enemyShips.add(enemy);
		return true;
	}
	
	public void shoot_bullet() { 
		
			for (int i = 0; i< bulletSet.size(); i++) {
				BulletModel bullet = bulletSet.get(i);
				EnemyModel enemy = bullet.getEnemy();

				bullet.shoot();
				if (bullet.explode()) {
				    bomb2Sound.play();
					enemy.setType("explosion");
				}
				if (bullet.gotEnemy())
				{

					enemyDied.remove(enemy);
					bulletSet.remove(i);
				}
			}
		
			
	}
	
	/**
	 * Move the enemy forward
	 */
	public void progres_enemy() {
		
		int next_x = 0;
		int next_y = 0;
		for(int array_pos = 0; array_pos < this.enemyCount(); array_pos++){
			EnemyModel enemy = enemyShips.get(array_pos);
			next_x = enemy.getX() - 1;
			next_y = enemy.getY();
			enemy.move(next_x, next_y);
			next_x = enemy.getX();
		}	
	}
	
	/**
	 * Check up on the enemy, see if it's still alive or not
	 */
	public void check_enemy() {
		for(int array_pos = 0; array_pos < this.enemyCount(); array_pos++){
			EnemyModel enemy = enemyShips.get(array_pos);
			int x = enemy.getX();			
			if(x == 71){
				enemy.setType("explosion");
			} 

			if(x < 70){ //See if the enemy got you
				kill_enemy(array_pos, true);
			    bomb2Sound.play();
				panel.display_score();
				panel.display_level();
			} else if(enemy.getDamage() == enemy.getWord().length()){ //Check if you damaged the enemy to death
				player.getAngle(enemy);
				panel.display_player();
				player.getPoint(KILL_POINTS);
				if (enemy.getType() == "hard") player.getPoint(50);
				kill_enemy(array_pos, false);
				this.shoot = true;
				panel.display_score();
				panel.display_level();
				//System.out.printf("Score: %d, Level: %d\n", score.getScore(), score.getLvl());
			}

		}
	}
	
	/**
	 * This kills the enemy and removes all the resources. 
	 * @param pos The position in the enemy array to kill
	 * @param hit_player a boolean telling us if we need to take a life away
	 */
	private void kill_enemy(int pos, boolean hit_player){
		//Remove all the things
		EnemyModel enemy = enemyShips.get(pos);
				
		shoot = true;
		BulletModel bullet = new BulletModel(40 , 308, enemy);
		bulletSet.add(bullet);
		
		if ((numTarget == 1) && (enemy.getDamage() > 0)) 
		{
			inputString = "";
			numTarget = 0;
		}
		
		if(hit_player)
			player.getHit();
		
		
		panel.display_lives();
		player.killEnemy(enemy);
		enemyDied.add(enemy);
		enemyShips.remove(pos);

		if(player.isDead()) {
			UniverseView.backGroundSound.stop();
			gameOverSound.play();
			panel.gameOver();
		}
	}
	/**
	 * This is called from the general dispatch. 
	 * @param key The key that was pressed, passed by Dispatcher
	 */
	public void key_handler(char key){
		key = Character.toLowerCase(key);
	
	    synchronized (enemyShips) {
	    
	    if ((key == ' ') && (player.getEnergy() == 10)) { 
	    	killAllEnemy();
	    } else if (Character.isAlphabetic(key)){ 
	    foundTarget = false;
	    inputString += key;
	    
	    int size = inputString.length();
	    boolean destroy = false;
	    int n = 0;
	    
	    ArrayList<EnemyModel> removeEnemy = new ArrayList<EnemyModel>();
		for (EnemyModel enemy : enemyShips) {
			int gotW = enemy.gotWord(inputString);
			if (gotW == size) 
				{
					n++;
					foundTarget = true;
				}
			else if (gotW > 0) removeEnemy.add(enemy); 
			if (enemy.isDestroyed()) {
				destroy = true;
			}
		}
		if (foundTarget) {
			numTarget = n;
			correctKeySound.play();
			for (EnemyModel enemy : removeEnemy) { 
				enemy.resetDamage();
			}
			if (destroy) { 
				numTarget = 0;
				inputString = "";
				foundTarget = false;
				return;
			}
		}
		if (foundTarget == false) { 
			wrongKeySound.play();
			if (size > 1)  
				inputString = inputString.substring(0, size-1);
			else inputString = "";
			player.getPoint(-5);
			panel.display_score();
			panel.display_level();
	    }
	    }}
		
	}

	private void killAllEnemy() {
		int size = enemyShips.size();
		player.setEnemyKilled(size);
		player.getPoint( 5 * size);
		enemyShips.clear();
		player.resetEnergy();
		inputString ="";
		for (int i = 0; i < size; i++) bomb1Sound.play();
		foundTarget = false;
	}
	
	public boolean getShoot() { 
		return shoot;
	}

	public ArrayList<BulletModel> getBulletSet() {
		// TODO Auto-generated method stub
		return bulletSet;
	}

	public ArrayList<EnemyModel> getEnemyDied() {
		// TODO Auto-generated method stub
		return enemyDied;
	}
	
}
