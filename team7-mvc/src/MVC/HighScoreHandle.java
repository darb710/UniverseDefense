package MVC;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This class handles the high score file and adding to it in sorted order
 * This is a good example of serialization. 
 * @author peu117
 *
 */
public class HighScoreHandle {

	HighScoreHandle() {

	}

	public HighScore deserial() {
		HighScore scores;
		HighScore back_up = new HighScore();

		// If any exception happens just return a new HighScore object. Not too
		// important if we loose it
		try {
			FileInputStream in_file = new FileInputStream("highscores");
			ObjectInputStream in_object = new ObjectInputStream(in_file);
			scores = (HighScore) in_object.readObject();
			in_object.close();
			in_file.close();
		} catch (FileNotFoundException e) {
			return back_up;
		} catch (IOException ei) {
			ei.printStackTrace();
			return back_up;
		} catch (ClassNotFoundException ce) {
			System.out.println("Class not found");
			ce.printStackTrace();
			return back_up;
		} catch (Exception eg) {
			return back_up;
		}

		return scores;
	}

	public void serial(HighScore scores) {
		try {
			FileOutputStream fileOut = new FileOutputStream("highscores");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(scores);
			out.close();
			fileOut.close();
		} catch (Exception i) {
			i.printStackTrace();
		}
	}
	
}