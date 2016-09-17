package MVC;

import java.awt.Image;
import java.util.ArrayList;

public class PlayerModel {
	private int lives; 
	private int score; 
	private int energy;
	private boolean dead;
	private Image display;
	private int enemyKilled;
	private int x = 40;
	private int y = 308;
	private PlayerView playerView;
	private ArrayList<BulletModel> bulletSet;
	
	HighScore high_score;
	HighScoreHandle high_score_handle;
	private String name;
	private boolean highScore;
	
	public PlayerModel() { 
		this.lives = 5;
		this.score = 0;
		this.energy = 0;
		this.dead = false;
		this.enemyKilled = 0;
		playerView = new PlayerView();
		this.display = playerView.getDisplay();
		this.name = "AAA";
		bulletSet = new ArrayList<BulletModel>();
		highScore = false;
		high_score_handle = new HighScoreHandle();
		high_score = high_score_handle.deserial();
	}
	
	public Image getDisplay() { 
		this.display = playerView.getDisplay();
		return display;
	}
	public int getLives() { 
		return this.lives;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public void getHit(){ 
		this.lives--;
		this.score -= 25;
		this.energy = 0;
	}
	
	public void getPoint(int point) {
		this.score += point;
		this.energy += point;
	}
	
	/**
	 * This adds the high score and name to the file
	 */
	public void add_high_score(){
		if(high_score.add(score, this.name) == 0){
			highScore  = true;
		}
		
		high_score_handle.serial(high_score);
		
	}
	
	public boolean isDead() { 
		if (lives == 0) { 
			dead = true;
		}
		else dead = false;
		return dead;
	}
	
	public void setDead() { 
		dead = true;
	}
	
	public int getEnemyKilled() { 
		return enemyKilled;
	}
	public void killEnemy(EnemyModel enemy) { 
		enemyKilled = 1;
		
	}
	
	public void setEnemyKilled(int size) { 
		enemyKilled = size;
	}

	public void setLives(int i) {
		this.lives = i;		
	}
	
	public void reset() { 
		this.highScore = false;
		this.score = 0;
		this.lives = 5;
		this.energy = 0;
		this.name = "AAA";
		this.bulletSet.clear();
		high_score_handle = new HighScoreHandle();
		high_score = high_score_handle.deserial();
	}

	public int getEnergy() {
		int returnEnergy = 0;
		returnEnergy = energy / 100;

		if (returnEnergy > 10) returnEnergy = 10;
		return returnEnergy;
	}

	public void setName(String inputName) {
		this.name = inputName;
	}

	public void resetEnergy() {
		energy = 0;		
	}
	
	public float getAngle(EnemyModel enemy) { 
		int target_x = enemy.getX();
		int target_y = enemy.getY();
		int diff = 20;
		if (enemy.getType() == "hard") diff = 50;
		float angle = (float) Math.toDegrees(Math.atan2(target_y + diff - y, target_x - x));
		
	    if(angle < 0){
	        angle += 360;
	    }
	    playerView.setAngle(angle);
	    return angle;
	}
	
	public ArrayList<BulletModel> getBulletSet() { 
		return bulletSet;
	}
	
	public boolean highScore() { 
		return this.highScore;
	}
}
