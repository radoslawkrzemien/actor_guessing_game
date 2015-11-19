package eksperty.actorguessing;

import eksperty.actorguessing.engine.Engine;

public class Main {
	
	public static void main(String args[]){
		Engine engine = new Engine("baza.pl");
		if(!engine.initialize()){
			System.err.println("INITIALIZATION FAILED!");
			System.exit(-1);
		}
		System.out.println("Initialization complete");
	}

}
