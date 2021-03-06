package eksperty.actorguessing.engine.entities;

public class Entity {
	
	protected String prologName;
	protected String friendlyName;
	
	public Entity(String prologName, String friendlyName){
		this.prologName = prologName;
		this.friendlyName = friendlyName;
	}
	
	public String getPrologName() {
		return prologName;
	}

	public String getFriendlyName() {
		return friendlyName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((friendlyName == null) ? 0 : friendlyName.hashCode());
		result = prime * result
				+ ((prologName == null) ? 0 : prologName.hashCode());
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
		Entity other = (Entity) obj;
		if (friendlyName == null) {
			if (other.friendlyName != null)
				return false;
		} else if (!friendlyName.equals(other.friendlyName))
			return false;
		if (prologName == null) {
			if (other.prologName != null)
				return false;
		} else if (!prologName.equals(other.prologName))
			return false;
		return true;
	}

}
