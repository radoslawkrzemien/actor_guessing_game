package eksperty.actorguessing.engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;
import org.jpl7.Variable;

import eksperty.actorguessing.engine.entities.Actor;
import eksperty.actorguessing.engine.entities.Director;
import eksperty.actorguessing.engine.entities.Entity;
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
	private Set<Fact> knownFacts = new HashSet<>();
	private Set<Question> questionsBase = new HashSet<>();
	private Mapper mapper;
	
	public Engine (String basefile){
		this.basefile = basefile;
	}

	public Mapper initialize() {
		if(!this.consult()){
			return null;
		}
		return this.parseBasefile();
	}

	public Set<Actor> callQuery(QuestionTypes type, String argument, boolean authenticity){
		Variable v = new Variable("A");
		Query q = null;
		Set<Actor> result = new HashSet<Actor>();
		switch(type){
			case MOVIE_PLAYED_IN:
				String prologMovieName = this.mapper.getMoviePrologName(argument);
				q = new Query("gral_w", new Term[] {v, new Atom(prologMovieName)});
				Map<String,Term>[] solutions = q.allSolutions();
				for(Map<String,Term> solution : solutions){
					for(String s : solution.keySet()){
						String actorPrologName = solution.get(s).toString();
						Actor actor = new Actor(actorPrologName,this.mapper.getActorFriendlyName(actorPrologName));
						result.add(actor);
					}
				}
				break;
			case DIRECTOR_OF_MOVIE_PLAYED_IN:
				String prologDirectorName = this.mapper.getDirectorPrologName(argument);
				q = new Query("gral_w(A,F),rezyserowal(" + prologDirectorName + ",F).");
				solutions = q.allSolutions();
				for(Map<String,Term> solution : solutions){
					for(String s : solution.keySet()){
						if(s.equals("A")){
							String actorPrologName = solution.get(s).toString();
							Actor actor = new Actor(actorPrologName,this.mapper.getActorFriendlyName(actorPrologName));
							result.add(actor);
						}
					}
				}
				break;
			case SERIES_MOVIE_PLAYED_IN_FROM:
				String prologSeriesName = this.mapper.getSeriesPrologName(argument);
				q = new Query("gral_w_serii", new Term[] {v, new Atom(prologSeriesName)});
				solutions = q.allSolutions();
				for(Map<String,Term> solution : solutions){
					for(String s : solution.keySet()){
						String actorPrologName = solution.get(s).toString();
						Actor actor = new Actor(actorPrologName,this.mapper.getActorFriendlyName(actorPrologName));
						result.add(actor);
					}
				}
				break;
			case SEX:
				String prologSexName = this.mapper.getSexPrologName(argument);
				q = new Query("plec", new Term[] {v, new Atom(prologSexName)});
				solutions = q.allSolutions();
				for(Map<String,Term> solution : solutions){
					for(String s : solution.keySet()){
						String actorPrologName = solution.get(s).toString();
						Actor actor = new Actor(actorPrologName,this.mapper.getActorFriendlyName(actorPrologName));
						result.add(actor);
					}
				}
				break;
			case FEATURES:
				String prologFeatureName = this.mapper.getFeaturePrologName(argument);
				q = new Query("cechy_szczegolne", new Term[] {v, new Atom(prologFeatureName)});
				solutions = q.allSolutions();
				for(Map<String,Term> solution : solutions){
					for(String s : solution.keySet()){
						String actorPrologName = solution.get(s).toString();
						Actor actor = new Actor(actorPrologName,this.mapper.getActorFriendlyName(actorPrologName));
						result.add(actor);
					}
				}
				break;
			case PERSON_OFTEN_WORKS_WITH:
				String prologPersonName = this.mapper.getPersonPrologName(argument);
				q = new Query("czesto_pracuje_z", new Term[] {new Atom(prologPersonName), v});
				solutions = q.allSolutions();
				for(Map<String,Term> solution : solutions){
					for(String s : solution.keySet()){
						String actorPrologName = solution.get(s).toString();
						Actor actor = new Actor(actorPrologName,this.mapper.getActorFriendlyName(actorPrologName));
						result.add(actor);
					}
				}
				break;
			case ROLE_OFTEN_PLAYS:
				String prologRoleName = this.mapper.getRolePrologName(argument);
				q = new Query("czesto_gra", new Term[] {v, new Atom(prologRoleName)});
				solutions = q.allSolutions();
				for(Map<String,Term> solution : solutions){
					for(String s : solution.keySet()){
						String actorPrologName = solution.get(s).toString();
						Actor actor = new Actor(actorPrologName,this.mapper.getActorFriendlyName(actorPrologName));
						result.add(actor);
					}
				}
				break;
			default:
				System.err.println("WRONG QUESTION TYPE!");
				System.exit(-1);
				break;
		}
		if(!authenticity){
			Set<Actor> allActors = new HashSet<>();
			allActors.addAll(this.actors);
			allActors.removeAll(result);
			result = allActors;
		}
		return result;
	}
	
	public void addKnownFact(QuestionTypes type, Entity entity, boolean authenticity){
		Fact f = new Fact(type,entity,authenticity);
		this.knownFacts.add(f);
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

	private Mapper parseBasefile() {
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
		        		Parser.parseActorPlayedInMovie(line,this.actors,this.movies);
		        		break;
		        	case "gral_postac":
		        		Parser.parseActorPlayedRole(line, this.actors, this.movies, this.roles);
		        		break;
		        	case "rezyserowal":
		        		Parser.parseDirectors(line, this.directors, this.movies);
		        		break;
		        	case "film_z_serii":
		        		Parser.parseSeries(line, this.series, this.movies);
		        		break;
		        	case "plec":
		        		Parser.parseSex(line, this.actors, this.sexes);
		        		break;
		        	case "cechy_szczegolne":
		        		Parser.parseFeatures(line, this.actors, this.features);
		        		break;
		        }
		    }
		} catch (IOException e){
			System.out.println("ERROR DURING PARSING FILE!");
			System.exit(-1);
		}
		createQuestionBase();
		Mapper mapper = createMapper();
		this.mapper = mapper;
		return mapper;
	}

	private Mapper createMapper() {
		Mapper mapper = new Mapper(this.actors, this.directors, this.movies, this.features, this.sexes, this.series, this.roles);
		return mapper;
	}

	private void createQuestionBase() {
		for(Actor a : this.actors){
			this.questionsBase.add(new Question(QuestionTypes.PERSON_OFTEN_WORKS_WITH, a));
		}
		for(Director d : this.directors){
			this.questionsBase.add(new Question(QuestionTypes.DIRECTOR_OF_MOVIE_PLAYED_IN, d));
		}
		for(Movie m : this.movies){
			this.questionsBase.add(new Question(QuestionTypes.MOVIE_PLAYED_IN, m));
		}
		for(Role r : this.roles){
			this.questionsBase.add(new Question(QuestionTypes.ROLE_OFTEN_PLAYS,r));
		}
		for(Series s : this.series){
			this.questionsBase.add(new Question(QuestionTypes.SERIES_MOVIE_PLAYED_IN_FROM, s));
		}
		for(Sex s : this.sexes){
			this.questionsBase.add(new Question(QuestionTypes.SEX, s));
		}
		for(Feature f : this.features){
			this.questionsBase.add(new Question(QuestionTypes.FEATURES, f));
		}
	}

	public Question getNextQuestion() {
		Question result = null;
		Set<Actor> resultSet = getResultSet();
		if(resultSet.size() > 1){
			LinkedList<Question> questions = new LinkedList<Question>(this.questionsBase);
			Collections.shuffle(questions);
			result = questions.getFirst();
			boolean questionOk = false;
			while(!questionOk){
				Set<Actor> newResultSet = new HashSet<Actor>(resultSet);
				newResultSet.retainAll(callQuery(result.getType(), result.getEntity().getFriendlyName(), true));
				if(newResultSet.size() != resultSet.size()){
					questionOk = true;
					continue;
				}
				newResultSet.retainAll(callQuery(result.getType(), result.getEntity().getFriendlyName(), false));
				if(newResultSet.size() != resultSet.size()){
					questionOk = true;
					continue;
				}
				Collections.shuffle(questions);
				result = questions.getFirst();
			}
			this.questionsBase.remove(result);
		}
		return result;
	}
	
	private Set<Actor> getResultSet(){
		Set<Actor> resultSet = new HashSet<>();
		if(this.knownFacts.size() == 0){
			return this.actors;
		}
		for(Fact f : this.knownFacts){
			if(resultSet.isEmpty())
				resultSet.addAll(callQuery(f.getQuestionType(), f.getEntity().getFriendlyName(),f.isAuthenticity()));
			else
				resultSet.retainAll(callQuery(f.getQuestionType(), f.getEntity().getFriendlyName(),f.isAuthenticity()));
		}
		return resultSet;
	}

	public String getResult() {
		Actor result = null;
		Set<Actor> resultSet = getResultSet();
		if(resultSet.size() == 1){
			result = (Actor) resultSet.toArray()[0];
		}
		return result == null ? null : result.getFriendlyName();
	}

}
