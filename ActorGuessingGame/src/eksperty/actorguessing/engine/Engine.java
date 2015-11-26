package eksperty.actorguessing.engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;
import org.jpl7.Variable;

import eksperty.actorguessing.engine.entities.Actor;
import eksperty.actorguessing.engine.entities.Director;
import eksperty.actorguessing.engine.entities.Feature;
import eksperty.actorguessing.engine.entities.Movie;
import eksperty.actorguessing.engine.entities.Role;
import eksperty.actorguessing.engine.entities.Series;
import eksperty.actorguessing.engine.entities.Sex;

public class Engine {
	
	private String basefile;
	private Set<Actor> actors = new HashSet<>();
	private Set<Director> directors = new HashSet<>();
	private Set<Movie> movies = new HashSet<>();
	private Set<Role> roles = new HashSet<>();
	private Set<Series> series = new HashSet<>();
	private Set<Sex> sexes = new HashSet<>();
	private Set<Feature> features = new HashSet<>();
	
	public Engine (String basefile){
		this.basefile = basefile;
	}

	public boolean initialize() {
		if(!this.consult()){
			return false;
		}
		return this.parseBasefile();
	}

	public Set<Actor> callQuery(QuestionTypes type, String argument){
		Variable v = new Variable("A");
		Query q = null;
		Set<Actor> result = new HashSet<Actor>();
		switch(type){
			case MOVIE_PLAYED_IN:
				String prologMovieName = getMoviePrologName(argument);
				q = new Query("gral_w", new Term[] {v, new Atom(prologMovieName)});
				Map<String,Term>[] solutions = q.allSolutions();
				for(Map<String,Term> solution : solutions){
					for(String s : solution.keySet()){
						String actorPrologName = solution.get(s).toString();
						Actor actor = new Actor(actorPrologName,getActorFriendlyName(actorPrologName));
						result.add(actor);
					}
				}
				break;
			case ROLE_PLAYED:
				String prologRoleName = getRolePrologName(argument);
				Variable m = new Variable("M");
				q = new Query("gral_postac", new Term[] {v, m, new Atom(prologRoleName)});
				solutions = q.allSolutions();
				for(Map<String,Term> solution : solutions){
					for(String s : solution.keySet()){
						if(s.equals("A")){
							String actorPrologName = solution.get(s).toString();
							Actor actor = new Actor(actorPrologName,getActorFriendlyName(actorPrologName));
							result.add(actor);
						}
					}
				}
				break;
			case DIRECTOR_OF_MOVIE_PLAYED_IN:
				String prologDirectorName = getDirectorPrologName(argument);
				q = new Query("gral_w(A,F),rezyserowal(" + prologDirectorName + ",F).");
				solutions = q.allSolutions();
				for(Map<String,Term> solution : solutions){
					for(String s : solution.keySet()){
						if(s.equals("A")){
							String actorPrologName = solution.get(s).toString();
							Actor actor = new Actor(actorPrologName,getActorFriendlyName(actorPrologName));
							result.add(actor);
						}
					}
				}
				break;
			case SERIES_MOVIE_PLAYED_IN_FROM:
				String prologSeriesName = getSeriesPrologName(argument);
				q = new Query("gral_w_serii", new Term[] {v, new Atom(prologSeriesName)});
				solutions = q.allSolutions();
				for(Map<String,Term> solution : solutions){
					for(String s : solution.keySet()){
						String actorPrologName = solution.get(s).toString();
						Actor actor = new Actor(actorPrologName,getActorFriendlyName(actorPrologName));
						result.add(actor);
					}
				}
				break;
			case SEX:
				String prologSexName = getSexPrologName(argument);
				q = new Query("plec", new Term[] {v, new Atom(prologSexName)});
				solutions = q.allSolutions();
				for(Map<String,Term> solution : solutions){
					for(String s : solution.keySet()){
						String actorPrologName = solution.get(s).toString();
						Actor actor = new Actor(actorPrologName,getActorFriendlyName(actorPrologName));
						result.add(actor);
					}
				}
				break;
			case FEATURES:
				String prologFeatureName = getFeaturePrologName(argument);
				q = new Query("cechy_szczegolne", new Term[] {v, new Atom(prologFeatureName)});
				solutions = q.allSolutions();
				for(Map<String,Term> solution : solutions){
					for(String s : solution.keySet()){
						String actorPrologName = solution.get(s).toString();
						Actor actor = new Actor(actorPrologName,getActorFriendlyName(actorPrologName));
						result.add(actor);
					}
				}
				break;
			case PERSON_OFTEN_WORKS_WITH:
				String prologPersonName = getPersonPrologName(argument);
				q = new Query("czesto_pracuje_z", new Term[] {new Atom(prologPersonName), v});
				solutions = q.allSolutions();
				for(Map<String,Term> solution : solutions){
					for(String s : solution.keySet()){
						String actorPrologName = solution.get(s).toString();
						Actor actor = new Actor(actorPrologName,getActorFriendlyName(actorPrologName));
						result.add(actor);
					}
				}
				break;
			case ROLE_OFTEN_PLAYS:
				prologRoleName = getRolePrologName(argument);
				q = new Query("czesto_gra", new Term[] {v, new Atom(prologRoleName)});
				solutions = q.allSolutions();
				for(Map<String,Term> solution : solutions){
					for(String s : solution.keySet()){
						String actorPrologName = solution.get(s).toString();
						Actor actor = new Actor(actorPrologName,getActorFriendlyName(actorPrologName));
						result.add(actor);
					}
				}
				break;
			default:
				System.err.println("WRONG QUESTION TYPE!");
				System.exit(-1);
				break;
		}
		return result;
	}

	private boolean consult() {
		Query q = new Query("consult", new Term[] {new Atom(basefile)});
		if(q.hasSolution()){
			System.out.println("Consult successful");
			return true;
		}
		else{
			System.err.println("CONSULT FAILED");
			return false;
		}
	}

	private boolean parseBasefile() {
		try(BufferedReader br = new BufferedReader(new FileReader(this.basefile))) {
		    for(String line; (line = br.readLine()) != null; ) {
		        if(line.startsWith("%") || line.length() == 0){
		        	continue;
		        }
		        if(!line.endsWith(".")){
		        	break;
		        }
		        String factName = line.substring(0,line.indexOf("("));
		        switch(factName){
		        	case "gral_w":
		        		parseActorPlayedInMovie(line);
		        		break;
		        	case "gral_postac":
		        		parseActorPlayedRole(line);
		        		break;
		        	case "rezyserowal":
						parseDirectors(line);
		        		break;
		        	case "film_z_serii":
						parseSeries(line);
		        		break;
		        	case "plec":
						parseSex(line);
		        		break;
		        	case "cechy_szczegolne":
						parseFeatures(line);
		        		break;
		        }
		    }
		} catch (IOException e){
			System.out.println("ERROR DURING PARSING FILE!");
			System.exit(-1);
		}
		return true;
	}
	
	public Actor askForActor(){
		Actor result = null;
		Variable actor = new Variable();
//		Query q = new Query();
		
		return result;
	}

	private void parseFeatures(String line) {
		String actorName = line.substring(line.indexOf("(") + 1, line.indexOf(","));
		String featureName = line.substring(line.indexOf(",") + 1, line.indexOf(")"));
		String actorFriendlyName = "";
		for(String s : actorName.split("_")){
			actorFriendlyName += Character.toUpperCase(s.charAt(0)) + s.substring(1) + " ";
		}
		actorFriendlyName = actorFriendlyName.trim();
		String featureFriendlyName = "";
		for(String s : featureName.split("_")){
			featureFriendlyName += Character.toUpperCase(s.charAt(0)) + s.substring(1) + " ";
		}
		featureFriendlyName = featureFriendlyName.trim();
		Actor actor = new Actor(actorName,actorFriendlyName);
		Feature feature = new Feature(featureName,featureFriendlyName);
		this.actors.add(actor);
		this.features.add(feature);
	}

	private void parseSex(String line) {
		String actorName = line.substring(line.indexOf("(") + 1, line.indexOf(","));
		String sexName = line.substring(line.indexOf(",") + 1, line.indexOf(")"));
		String actorFriendlyName = "";
		for(String s : actorName.split("_")){
			actorFriendlyName += Character.toUpperCase(s.charAt(0)) + s.substring(1) + " ";
		}
		actorFriendlyName = actorFriendlyName.trim();
		String sexFriendlyName = "";
		for(String s : sexName.split("_")){
			sexFriendlyName += Character.toUpperCase(s.charAt(0)) + s.substring(1) + " ";
		}
		sexFriendlyName = sexFriendlyName.trim();
		Actor actor = new Actor(actorName,actorFriendlyName);
		Sex sex = new Sex(sexName,sexFriendlyName);
		this.actors.add(actor);
		this.sexes.add(sex);
	}

	private void parseSeries(String line) {
		String movieName = line.substring(line.indexOf("(") + 1, line.indexOf(","));
		String seriesName = line.substring(line.indexOf(",") + 1, line.indexOf(")"));
		String movieFriendlyName = "";
		for(String s : movieName.split("_")){
			movieFriendlyName += Character.toUpperCase(s.charAt(0)) + s.substring(1) + " ";
		}
		movieFriendlyName = movieFriendlyName.trim();
		String seriesFriendlyName = "";
		for(String s : seriesName.split("_")){
			seriesFriendlyName += Character.toUpperCase(s.charAt(0)) + s.substring(1) + " ";
		}
		seriesFriendlyName = seriesFriendlyName.trim();
		Movie movie = new Movie(movieName,movieFriendlyName);
		Series series = new Series(seriesName,seriesFriendlyName);
		this.series.add(series);
		this.movies.add(movie);
	}

	private void parseDirectors(String line) {
		String directorName = line.substring(line.indexOf("(") + 1, line.indexOf(","));
		String movieName = line.substring(line.indexOf(",") + 1, line.indexOf(")"));
		String directorFriendlyName = "";
		for(String s : directorName.split("_")){
			directorFriendlyName += Character.toUpperCase(s.charAt(0)) + s.substring(1) + " ";
		}
		directorFriendlyName = directorFriendlyName.trim();
		String movieFriendlyName = "";
		for(String s : movieName.split("_")){
			movieFriendlyName += Character.toUpperCase(s.charAt(0)) + s.substring(1) + " ";
		}
		movieFriendlyName = movieFriendlyName.trim();
		Director director = new Director(directorName,directorFriendlyName);
		Movie movie = new Movie(movieName,movieFriendlyName);
		this.directors.add(director);
		this.movies.add(movie);
	}

	private void parseActorPlayedRole(String line) {
		int firstCommaIndex = line.indexOf(",");
		String actorName = line.substring(line.indexOf("(") + 1, firstCommaIndex);
		String movieName = line.substring(firstCommaIndex + 1, line.indexOf(",",firstCommaIndex + 1));
		String roleName = line.substring(line.indexOf(",",firstCommaIndex + 1) + 1, line.indexOf(")"));
		String actorFriendlyName = "";
		for(String s : actorName.split("_")){
			actorFriendlyName += Character.toUpperCase(s.charAt(0)) + s.substring(1) + " ";
		}
		actorFriendlyName = actorFriendlyName.trim();
		String movieFriendlyName = "";
		for(String s : movieName.split("_")){
			movieFriendlyName += Character.toUpperCase(s.charAt(0)) + s.substring(1) + " ";
		}
		movieFriendlyName = movieFriendlyName.trim();
		String roleFriendlyName = "";
		for(String s : roleName.split("_")){
			roleFriendlyName += Character.toUpperCase(s.charAt(0)) + s.substring(1) + " ";
		}
		roleFriendlyName = roleFriendlyName.trim();
		Actor actor = new Actor(actorName,actorFriendlyName);
		Movie movie = new Movie(movieName,movieFriendlyName);
		Role role = new Role(roleName,roleFriendlyName);
		this.actors.add(actor);
		this.movies.add(movie);
		this.roles.add(role);
	}

	private void parseActorPlayedInMovie(String line) {
		String actorName = line.substring(line.indexOf("(") + 1, line.indexOf(","));
		String movieName = line.substring(line.indexOf(",") + 1, line.indexOf(")"));
		String actorFriendlyName = "";
		for(String s : actorName.split("_")){
			actorFriendlyName += Character.toUpperCase(s.charAt(0)) + s.substring(1) + " ";
		}
		actorFriendlyName = actorFriendlyName.trim();
		String movieFriendlyName = "";
		for(String s : movieName.split("_")){
			movieFriendlyName += Character.toUpperCase(s.charAt(0)) + s.substring(1) + " ";
		}
		movieFriendlyName = movieFriendlyName.trim();
		Actor actor = new Actor(actorName,actorFriendlyName);
		Movie movie = new Movie(movieName,movieFriendlyName);
		this.actors.add(actor);
		this.movies.add(movie);
	}
	
	public String getActorPrologName(String friendlyName){
		String result = null;
		for(Actor a : this.actors){
			if(a.getFriendlyName().equals(friendlyName)){
				result = a.getPrologName();
				break;
			}
		}
		return result;
	}
	
	public String getActorFriendlyName(String prologName){
		String result = null;
		for(Actor a : this.actors){
			if(a.getPrologName().equals(prologName)){
				result = a.getFriendlyName();
				break;
			}
		}
		return result;
	}
	
	public String getDirectorPrologName(String friendlyName){
		String result = null;
		for(Director d : this.directors){
			if(d.getFriendlyName().equals(friendlyName)){
				result = d.getPrologName();
				break;
			}
		}
		return result;
	}
	
	public String getDirectorFriendlyName(String prologName){
		String result = null;
		for(Director d : this.directors){
			if(d.getPrologName().equals(prologName)){
				result = d.getFriendlyName();
				break;
			}
		}
		return result;
	}
	
	public String getFeaturePrologName(String friendlyName){
		String result = null;
		for(Feature f : this.features){
			if(f.getFriendlyName().equals(friendlyName)){
				result = f.getPrologName();
				break;
			}
		}
		return result;
	}
	
	public String getFeatureFriendlyName(String prologName){
		String result = null;
		for(Feature f : this.features){
			if(f.getPrologName().equals(prologName)){
				result = f.getFriendlyName();
				break;
			}
		}
		return result;
	}
	
	public String getMoviePrologName(String friendlyName){
		String result = null;
		for(Movie m : this.movies){
			if(m.getFriendlyName().equals(friendlyName)){
				result = m.getPrologName();
				break;
			}
		}
		return result;
	}
	
	public String getMovieFriendlyName(String prologName){
		String result = null;
		for(Movie m : this.movies){
			if(m.getPrologName().equals(prologName)){
				result = m.getFriendlyName();
				break;
			}
		}
		return result;
	}
	
	public String getRolePrologName(String friendlyName){
		String result = null;
		for(Role r : this.roles){
			if(r.getFriendlyName().equals(friendlyName)){
				result = r.getPrologName();
				break;
			}
		}
		return result;
	}
	
	public String getRoleFriendlyName(String prologName){
		String result = null;
		for(Role r : this.roles){
			if(r.getPrologName().equals(prologName)){
				result = r.getFriendlyName();
				break;
			}
		}
		return result;
	}
	
	public String getSeriesPrologName(String friendlyName){
		String result = null;
		for(Series s : this.series){
			if(s.getFriendlyName().equals(friendlyName)){
				result = s.getPrologName();
				break;
			}
		}
		return result;
	}
	
	public String getSeriesFriendlyName(String prologName){
		String result = null;
		for(Series s : this.series){
			if(s.getPrologName().equals(prologName)){
				result = s.getFriendlyName();
				break;
			}
		}
		return result;
	}
	
	public String getSexPrologName(String friendlyName){
		String result = null;
		for(Sex s : this.sexes){
			if(s.getFriendlyName().equals(friendlyName)){
				result = s.getPrologName();
				break;
			}
		}
		return result;
	}
	
	public String getSexFriendlyName(String prologName){
		String result = null;
		for(Sex s : this.sexes){
			if(s.getPrologName().equals(prologName)){
				result = s.getFriendlyName();
				break;
			}
		}
		return result;
	}

	private String getPersonPrologName(String argument) {
		String result = null;
		if((result = getActorPrologName(argument)) == null){
			result = getDirectorPrologName(argument);
		}
		return result;
	}

	private String getPersonFriendlyName(String argument) {
		String result = null;
		if((result = getActorFriendlyName(argument)) == null){
			result = getDirectorFriendlyName(argument);
		}
		return result;
	}

}
