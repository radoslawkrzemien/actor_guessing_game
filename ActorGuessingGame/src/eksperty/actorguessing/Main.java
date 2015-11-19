package eksperty.actorguessing;

import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;

public class Main {
	
	public static void main(String args[]){
		Query q = new Query("consult", new Term[] {new Atom("baza.pl")});
		System.out.println("Consult results in " + (q.hasSolution() ? " Success" : "Failure"));
	}

}
