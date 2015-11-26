package eksperty.actorguessing.engine;

import eksperty.actorguessing.engine.entities.Entity;

public class Fact {
	
	private QuestionTypes questionType;
	
	private Entity entity;
	
	private boolean authenticity;
	
	public Fact(QuestionTypes questionType, Entity entity, boolean authenticity){
		this.questionType = questionType;
		this.entity = entity;
		this.authenticity = authenticity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (authenticity ? 1231 : 1237);
		result = prime * result + ((entity == null) ? 0 : entity.hashCode());
		result = prime * result
				+ ((questionType == null) ? 0 : questionType.hashCode());
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
		Fact other = (Fact) obj;
		if (authenticity != other.authenticity)
			return false;
		if (entity == null) {
			if (other.entity != null)
				return false;
		} else if (!entity.equals(other.entity))
			return false;
		if (questionType != other.questionType)
			return false;
		return true;
	}

	public QuestionTypes getQuestionType() {
		return questionType;
	}

	public Entity getEntity() {
		return entity;
	}

	public boolean isAuthenticity() {
		return authenticity;
	}
	
	

}
