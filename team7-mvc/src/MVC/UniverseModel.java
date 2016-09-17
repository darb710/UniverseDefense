package MVC;

public class UniverseModel {
	private int view;
	
	public UniverseModel() { 
		view = 1;
	}
	
	public int whichView() { 
		return view;
	}
	
	public void switchView(int whichPanel) { 
		this.view = whichPanel;
	}
}
