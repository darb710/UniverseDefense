package MVC;

import java.awt.Image;

public class EnemyModel {
	
	private int x;
	private int y;
	private String type;
	private String word;
	private Image display;
	private int damage;

	public EnemyModel(int x, int y, String type, String word){
		this.x = x;
		this.y = y;
		this.type = type;
		this.word = word;
		this.damage = 0;
		this.setDisplay(type);
	} 
	
	public void move(int x, int y) { 
		this.x = x;
		this.y = y;
		if (type == "hard") {
			this.x -= 1;
		}
	}
	
	public int getX(){ 
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public void setType(String type){
		this.type = type;
		setDisplay(type);
	}
	
	public String getWord() { 
		return this.word;
	}
	
	public int getDamage() { 
		return this.damage;
	}
	
	public void getShot() { 
		this.damage++;
	}
	public void setDisplay(String type) { 
		EnemyView enemyDisplay = new EnemyView(type);
		this.display = enemyDisplay.getDisplay();
	}
	public Image getDisplay() { 
		return this.display;
	}
	public void resetDamage() {
		this.damage = 0;
	}
	
	public void update() { 
		
	}
	public boolean isDestroyed () { 
		if (damage == word.length() || (x < 70)) return true;
		else 
			return false;
	}
	
	public void getKey(char key) {
		if (key == this.word.charAt(damage)) { 
			damage++;
		} else { 
		}
	}

	public boolean targeted() {
		if (damage > 0) return true;
		else return false;
	}

	public int gotWord(String inputString) {
		int size = inputString.length();
		String subS = null;
		if (size <= word.length()) {
			subS = this.word.substring(0, size);
		} else return damage;
		if (subS.equals(inputString)) 
		{	
			this.damage = size;
			return size;
		}
		else return damage;
	}

	public String getType() {
		// TODO Auto-generated method stub
		return this.type;
	}

	public void getKilled() {
		damage = word.length();
		setType("explosion");
	}
	
	public float getAngle() { 
		int ship_x = 40;
		int ship_y = 308;
		float angle = (float) Math.toDegrees(Math.atan2(y - ship_y, x - ship_x));

	    if(angle < 0){
	        angle += 360;
	    }
	    return angle;
	}

}
