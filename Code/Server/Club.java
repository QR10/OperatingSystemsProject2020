package ie.gmit;

public class Club {
	// Variables
	private String name;
	private int clubId;
	private String email;
	private int funds;
	
	// Gets and Sets 
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getClubId() {
		return clubId;
	}
	public void setClubId(int clubId) {
		this.clubId = clubId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getFunds() {
		return funds;
	}
	public void setFunds(int funds) {
		this.funds = funds;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return (getName() + " " + getClubId() + " " + getEmail() + " " + getFunds());
	}
	
	
}
