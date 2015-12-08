package eksperty.actorguessing.engine;

import java.util.HashSet;
import java.util.Set;

import eksperty.actorguessing.engine.entities.Actor;
import eksperty.actorguessing.engine.entities.Director;
import eksperty.actorguessing.engine.entities.Feature;
import eksperty.actorguessing.engine.entities.Movie;
import eksperty.actorguessing.engine.entities.Role;
import eksperty.actorguessing.engine.entities.Series;
import eksperty.actorguessing.engine.entities.Sex;

public class Mapper {
	private Set<Actor> actors;
	private Set<Director> directors;
	private Set<Movie> movies;
	private Set<Role> roles;
	private Set<Series> series;
	private Set<Sex> sexes;
	private Set<Feature> features;
	
	public Mapper(Set<Actor> actors, Set<Director> directors, Set<Movie> movies, Set<Feature> features, Set<Sex> sexes,
			Set<Series> series, Set<Role> roles) {
		this.actors = actors;
		this.directors = directors;
		this.movies = movies;
		this.roles = roles;
		this.series = series;
		this.sexes = sexes;
		this.features = features;
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

	public String getPersonPrologName(String argument) {
		String result = null;
		if((result = getActorPrologName(argument)) == null){
			result = getDirectorPrologName(argument);
		}
		return result;
	}

	public String getPersonFriendlyName(String argument) {
		String result = null;
		if((result = getActorFriendlyName(argument)) == null){
			result = getDirectorFriendlyName(argument);
		}
		return result;
	}

	public String getEntityPrologName(String argument) {
		String result = getPersonPrologName(argument);
		if(result == null){
			result = getRolePrologName(argument);
		}
		if(result == null){
			result = getFeaturePrologName(argument);
		}
		if(result == null){
			result = getMoviePrologName(argument);
		}
		if(result == null){
			result = getSeriesPrologName(argument);
		}
		if(result == null){
			result = getSexPrologName(argument);
		}
		return result;
	}

	public String getEntityFriendlyName(String argument) {
		String result = getPersonFriendlyName(argument);
		if(result == null){
			result = getRoleFriendlyName(argument);
		}
		if(result == null){
			result = getFeatureFriendlyName(argument);
		}
		if(result == null){
			result = getMovieFriendlyName(argument);
		}
		if(result == null){
			result = getSeriesFriendlyName(argument);
		}
		if(result == null){
			result = getSexFriendlyName(argument);
		}
		return result;
	}

}
