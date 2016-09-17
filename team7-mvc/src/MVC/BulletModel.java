package MVC;

import java.awt.Image;

public class BulletModel {
	private int x;
	private int y;
	private int target_x;
	private int target_y;
	private float angle;
	private Image display;
	private boolean gotEnemy;
	private EnemyModel enemy;
	private boolean explode;
	
	public BulletModel(int x, int y, EnemyModel enemy) { 
		this.x = x;
		this.y = y;
		this.target_x = enemy.getX();
		int diff = 20;
		if (enemy.getType() == "hard") diff = 35;
		this.target_y = enemy.getY() + diff;
		BulletView bulletView = new BulletView(getAngle());
		this.display = bulletView.getDisplay();
		this.angle = this.getAngle();
		this.gotEnemy = false;
		this.enemy = enemy;
		this.explode = false;
	}
	
	public void shoot() { 
		int speed = 60;
		double radAngle =Math.toRadians(360 - this.angle);
		
		int yMove = (int) (Math.tan(radAngle) * speed);
		
		
		if (target_x - x > speed) { 
			x = x + speed;
			if (target_x - x < speed*2) { 
				this.explode = true;
			}
			y = y - yMove;
		}
		else {
			x = target_x;
			y = target_y;
		}

		if (x == target_x) this.gotEnemy = true;
	}
	
	private float getAngle() { 
		float angle = (float) Math.toDegrees(Math.atan2(target_y - y, target_x - x));

	    if(angle < 0){
	        angle += 360;
	    }
	    
	    return angle;
	}
	public Image getView() { 
		return this.display;
	}

	public int getX() {
		// TODO Auto-generated method stub
		return this.x;
	}
	
	public int getY() { 
		return this.y;
	}
	
	public boolean gotEnemy() { 
		return gotEnemy;
	}

	public EnemyModel getEnemy() {
		// TODO Auto-generated method stub
		return enemy;
	}

	public boolean explode() {
		// TODO Auto-generated method stub
		return this.explode;
	}
}
