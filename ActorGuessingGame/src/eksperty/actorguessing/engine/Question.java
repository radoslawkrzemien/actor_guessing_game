package eksperty.actorguessing.engine;

import eksperty.actorguessing.engine.entities.Entity;

public class Question {
	
	private QuestionTypes type;
	
	private Entity entity;

	public Question(QuestionTypes type, Entity entity) {
		super();
		this.type = type;
		this.entity = entity;
	}

	public QuestionTypes getType() {
		return type;
	}

	public Entity getEntity() {
		return entity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entity == null) ? 0 : entity.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		if (entity == null) {
			if (other.entity != null)
				return false;
		} else if (!entity.equals(other.entity))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	
	

}
