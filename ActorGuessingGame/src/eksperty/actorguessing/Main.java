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
		Engine engine = new Engine("baza.pl");
		if(!engine.initialize()){
			System.err.println("INITIALIZATION FAILED!");
			System.exit(-1);
		}
		System.out.println("Initialization complete");
		//final GUI gui = new GUI();
		
//		SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                //Turn off metal's use of bold fonts
//        UIManager.put("swing.boldMetal", Boolean.FALSE);
//        Demo.createAndShowGUI();
//            }
//        });
		int i = 1;
		for(Actor actor : engine.callQuery(QuestionTypes.DIRECTOR_OF_MOVIE_PLAYED_IN, "Martin Scorsese")){
			System.out.println("1");
			System.out.println("Solution " + i + ":");
			System.out.println("\t" + actor.getFriendlyName());
			i++;
		}
		i = 1;
		for(Actor actor : engine.callQuery(QuestionTypes.MOVIE_PLAYED_IN, "Taxi Driver")){
			System.out.println("2");
			System.out.println("Solution " + i + ":");
			System.out.println("\t" + actor.getFriendlyName());
			i++;
		}
		i = 1;
		for(Actor actor : engine.callQuery(QuestionTypes.ROLE_PLAYED, "Dobra Postac")){
			System.out.println("3");
			System.out.println("Solution " + i + ":");
			System.out.println("\t" + actor.getFriendlyName());
			i++;
		}
		i = 1;
		for(Actor actor : engine.callQuery(QuestionTypes.SERIES_MOVIE_PLAYED_IN_FROM, "Hannibal Series")){
			System.out.println("4");
			System.out.println("Solution " + i + ":");
			System.out.println("\t" + actor.getFriendlyName());
			i++;
		}
		i = 1;
		for(Actor actor : engine.callQuery(QuestionTypes.SEX, "Mezczyzna")){
			System.out.println("5");
			System.out.println("Solution " + i + ":");
			System.out.println("\t" + actor.getFriendlyName());
			i++;
		}
		i = 1;
		for(Actor actor : engine.callQuery(QuestionTypes.FEATURES, "Pieprzyk Na Policzku")){
			System.out.println("6");
			System.out.println("Solution " + i + ":");
			System.out.println("\t" + actor.getFriendlyName());
			i++;
		}
		i = 1;
		for(Actor actor : engine.callQuery(QuestionTypes.PERSON_OFTEN_WORKS_WITH, "Martin Scorsese")){
			System.out.println("7");
			System.out.println("Solution " + i + ":");
			System.out.println("\t" + actor.getFriendlyName());
			i++;
		}
		i = 1;
		for(Actor actor : engine.callQuery(QuestionTypes.ROLE_OFTEN_PLAYS, "Czarny Charakter")){
			System.out.println("8");
			System.out.println("Solution " + i + ":");
			System.out.println("\t" + actor.getFriendlyName());
			i++;
		}
	}

}
