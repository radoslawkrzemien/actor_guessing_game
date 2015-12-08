package eksperty.actorguessing.engine;

import java.util.Set;

import eksperty.actorguessing.engine.entities.Actor;
import eksperty.actorguessing.engine.entities.Director;
import eksperty.actorguessing.engine.entities.Feature;
import eksperty.actorguessing.engine.entities.Movie;
import eksperty.actorguessing.engine.entities.Role;
import eksperty.actorguessing.engine.entities.Series;
import eksperty.actorguessing.engine.entities.Sex;

public class Parser {

	public static void parseFeatures(String line, Set<Actor> actors, Set<Feature> features) {
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
		actors.add(actor);
		features.add(feature);
	}

	public static void parseSex(String line, Set<Actor> actors, Set<Sex> sexes) {
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
		actors.add(actor);
		sexes.add(sex);
	}

	public static void parseSeries(String line, Set<Series> seriesSet, Set<Movie> movies) {
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
		seriesSet.add(series);
		movies.add(movie);
	}

	public static void parseDirectors(String line, Set<Director> directors, Set<Movie> movies) {
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
		directors.add(director);
		movies.add(movie);
	}

	public static void parseActorPlayedRole(String line, Set<Actor> actors, Set<Movie> movies, Set<Role> roles) {
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
		actors.add(actor);
		movies.add(movie);
		roles.add(role);
	}

	public static void parseActorPlayedInMovie(String line, Set<Actor> actors, Set<Movie> movies) {
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
		actors.add(actor);
		movies.add(movie);
	}

}
