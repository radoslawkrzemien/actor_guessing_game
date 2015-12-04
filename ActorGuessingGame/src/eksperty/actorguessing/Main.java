package eksperty.actorguessing;

import java.util.Map;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.jpl7.Term;

import eksperty.actorguessing.engine.Engine;
import eksperty.actorguessing.engine.QuestionTypes;
import eksperty.actorguessing.engine.entities.Actor;
import eksperty.actorguessing.gui.GUI;
import eksperty.actorguessing.gui.Demo;;

public class Main {
	
	public static void main(String args[]){
		final Engine engine = new Engine("baza.pl");
		if(!engine.initialize()){
			System.err.println("INITIALIZATION FAILED!");
			System.exit(-1);
		}
		System.out.println("Initialization complete");
		//final GUI gui = new GUI();

//		testQueries(engine);
//		testNegativeQuery(engine);
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        Demo.createAndShowGUI(engine);
            }
        });
	}

	private static void testNegativeQuery(Engine engine) {
		int i = 1;
		for(Actor actor : engine.callQuery(QuestionTypes.DIRECTOR_OF_MOVIE_PLAYED_IN, "Martin Scorsese",true)){
			System.out.println("1");
			System.out.println("Solution " + i + ":");
			System.out.println("\t" + actor.getFriendlyName());
			i++;
		}
		i = 1;
		for(Actor actor : engine.callQuery(QuestionTypes.DIRECTOR_OF_MOVIE_PLAYED_IN, "Martin Scorsese",false)){
			System.out.println("2");
			System.out.println("Solution " + i + ":");
			System.out.println("\t" + actor.getFriendlyName());
			i++;
		}
	}

	private static void testQueries(Engine engine) {
		int i = 1;
		for(Actor actor : engine.callQuery(QuestionTypes.DIRECTOR_OF_MOVIE_PLAYED_IN, "Martin Scorsese",true)){
			System.out.println("1");
			System.out.println("Solution " + i + ":");
			System.out.println("\t" + actor.getFriendlyName());
			i++;
		}
		i = 1;
		for(Actor actor : engine.callQuery(QuestionTypes.MOVIE_PLAYED_IN, "Taxi Driver",true)){
			System.out.println("2");
			System.out.println("Solution " + i + ":");
			System.out.println("\t" + actor.getFriendlyName());
			i++;
		}
//		i = 1;
//		for(Actor actor : engine.callQuery(QuestionTypes.ROLE_PLAYED, "Dobra Postac",true)){
//			System.out.println("3");
//			System.out.println("Solution " + i + ":");
//			System.out.println("\t" + actor.getFriendlyName());
//			i++;
//		}
		i = 1;
		for(Actor actor : engine.callQuery(QuestionTypes.SERIES_MOVIE_PLAYED_IN_FROM, "Hannibal Series",true)){
			System.out.println("4");
			System.out.println("Solution " + i + ":");
			System.out.println("\t" + actor.getFriendlyName());
			i++;
		}
		i = 1;
		for(Actor actor : engine.callQuery(QuestionTypes.SEX, "Mezczyzna",true)){
			System.out.println("5");
			System.out.println("Solution " + i + ":");
			System.out.println("\t" + actor.getFriendlyName());
			i++;
		}
		i = 1;
		for(Actor actor : engine.callQuery(QuestionTypes.FEATURES, "Pieprzyk Na Policzku",true)){
			System.out.println("6");
			System.out.println("Solution " + i + ":");
			System.out.println("\t" + actor.getFriendlyName());
			i++;
		}
		i = 1;
		for(Actor actor : engine.callQuery(QuestionTypes.PERSON_OFTEN_WORKS_WITH, "Martin Scorsese",true)){
			System.out.println("7");
			System.out.println("Solution " + i + ":");
			System.out.println("\t" + actor.getFriendlyName());
			i++;
		}
		i = 1;
		for(Actor actor : engine.callQuery(QuestionTypes.ROLE_OFTEN_PLAYS, "Czarny Charakter",true)){
			System.out.println("8");
			System.out.println("Solution " + i + ":");
			System.out.println("\t" + actor.getFriendlyName());
			i++;
		}
	}

}
