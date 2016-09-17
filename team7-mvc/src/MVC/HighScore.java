package MVC;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class is the high score class, keeping all the things in order
 * @author peu117
 *
 */
public class HighScore implements Serializable {

	//DO NOT CHANGE/ EDIT/ REMOVE
	private static final long serialVersionUID = 1L;

	private ArrayList<Integer> scores;
	private ArrayList<String> names;
	
	HighScore(){
		scores = new ArrayList<Integer>();
		names = new ArrayList<String>();
	}
	
	public int add(int score, String name){
		boolean added = false;
		
		for(int pos = 0; pos < scores.size(); pos++){
			if(scores.get(pos) <= score){
				scores.add(pos, score);
				names.add(pos, name);
				return pos;
			}			
			
		}
		
		//Corner case for empty list
		if(!added){
			scores.add(score);
			names.add(name);
			return 0;
		}
		
		
		return 999999;
	}
	
	public void print_scores(){

	}
	
	public ArrayList<String> get_names(){
		return names;
	}
	
	public ArrayList<Integer> get_scores(){
		return scores;
	}
	
}