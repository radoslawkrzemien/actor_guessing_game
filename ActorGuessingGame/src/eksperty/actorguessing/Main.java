package eksperty.actorguessing;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import eksperty.actorguessing.engine.Engine;
import eksperty.actorguessing.gui.GUI;

public class Main {
	
	public static void main(String args[]){
		Engine engine = new Engine("baza.pl");
		if(!engine.initialize()){
			System.err.println("INITIALIZATION FAILED!");
			System.exit(-1);
		}
		System.out.println("Initialization complete");
		//final GUI gui = new GUI();
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        GUI.createAndShowGUI();
            }
        });
	}

}
