package eksperty.actorguessing.engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
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
		actorFriendlyName.trim();
		String featureFriendlyName = "";
		for(String s : featureName.split("_")){
			featureFriendlyName += Character.toUpperCase(s.charAt(0)) + s.substring(1) + " ";
		}
		featureFriendlyName.trim();
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
		actorFriendlyName.trim();
		String sexFriendlyName = "";
		for(String s : sexName.split("_")){
			sexFriendlyName += Character.toUpperCase(s.charAt(0)) + s.substring(1) + " ";
		}
		sexFriendlyName.trim();
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
		movieFriendlyName.trim();
		String seriesFriendlyName = "";
		for(String s : seriesName.split("_")){
			seriesFriendlyName += Character.toUpperCase(s.charAt(0)) + s.substring(1) + " ";
		}
		seriesFriendlyName.trim();
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
		directorFriendlyName.trim();
		String movieFriendlyName = "";
		for(String s : movieName.split("_")){
			movieFriendlyName += Character.toUpperCase(s.charAt(0)) + s.substring(1) + " ";
		}
		movieFriendlyName.trim();
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
		actorFriendlyName.trim();
		String movieFriendlyName = "";
		for(String s : movieName.split("_")){
			movieFriendlyName += Character.toUpperCase(s.charAt(0)) + s.substring(1) + " ";
		}
		movieFriendlyName.trim();
		String roleFriendlyName = "";
		for(String s : roleName.split("_")){
			roleFriendlyName += Character.toUpperCase(s.charAt(0)) + s.substring(1) + " ";
		}
		roleFriendlyName.trim();
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
		actorFriendlyName.trim();
		String movieFriendlyName = "";
		for(String s : movieName.split("_")){
			movieFriendlyName += Character.toUpperCase(s.charAt(0)) + s.substring(1) + " ";
		}
		movieFriendlyName.trim();
		Actor actor = new Actor(actorName,actorFriendlyName);
		Movie movie = new Movie(movieName,movieFriendlyName);
		this.actors.add(actor);
		this.movies.add(movie);
	}

//	public Set<Actor> getActors() {
//		return actors;
//	}
//
//	public Set<Director> getDirectors() {
//		return directors;
//	}
//
//	public Set<Movie> getMovies() {
//		return movies;
//	}
//
//	public Set<Role> getRoles() {
//		return roles;
//	}
//
//	public Set<Series> getSeries() {
//		return series;
//	}
//
//	public Set<Sex> getSexes() {
//		return sexes;
//	}
//
//	public Set<Feature> getFeatures() {
//		return features;
//	}
	
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

}
