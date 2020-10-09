package ie.gmit;

public class PlayerAgent {
	// Variables
	private String agentName;
	private int agentId;
	private String email;
	
	// Gets and Sets
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public int getAgentId() {
		return agentId;
	}
	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return (getAgentName() + " " + getAgentId() + " " + getEmail());
	}
}
