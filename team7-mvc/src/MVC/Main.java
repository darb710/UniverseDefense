package MVC;

import java.io.IOException;


/**
 * The Calculator class along with CalculatorController,
 * CalculatorModel and CalculatorView implements a 
 * simple-minded calculator.
 * The implementation is based on the MVC design pattern.
 * 
 */
public class Main {

	/**
	 * Create the model, view and controller objects,
	 * and launch the application.
	 * 
	 * @param args not use
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		/* create new model, view and controller */
		UniverseModel model = new UniverseModel();
		UniverseView view = new UniverseView();
		UniverseViewController controller = 
				new UniverseViewController(view, model);
		
		/* register controller as listener */
		view.registerListener(controller);

		
		
		
	} 
}


