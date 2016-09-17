package MVC;

import java.io.*;
import java.util.*;

/**
 * 
 * @author Nicolas Vargas
 *
 */
public class WordReader {
	
	private ArrayList<ArrayList<String>> words;
	
	public WordReader() {
		words = new ArrayList<ArrayList<String>>();	
		readFile();
	}
	
	public void readFile() {
		String file = "lvl1.txt";
		ArrayList<String> currentWords = null;
		for (int lvl = 1; lvl< 6; lvl++) {
			currentWords = new ArrayList<String>();
			switch(lvl) {
			case 1:
				break;
			case 2:
				file = "lvl2.txt";
				break;
			case 3:
				file = "lvl3.txt";
				break;
			case 4:
				file = "lvl4.txt";
				break;
			case 5:
				file = "lvl5.txt";
				break;
			}
			try {
				@SuppressWarnings("resource")
				Scanner scan = new Scanner(new File(file));
			
				while(scan.hasNext()) {
					String word = scan.next();
					currentWords.add(word);
				}
			
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}	
			this.words.add(currentWords);
		}
	}
	
	public String getRandomWord(int lvl) {
		ArrayList<String> currentWords = null;
		Random ran = new Random();
		if (lvl > 5) lvl = 5;
		int index = lvl - 1;
		currentWords = words.get(index);
		int num = currentWords.size();
		String word = currentWords.get(ran.nextInt(num));
		
		return word;
		
	}

}