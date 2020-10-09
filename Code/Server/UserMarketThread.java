package ie.gmit;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class UserMarketThread extends Thread {
	
	Socket userSocket;
	String message;
	int socketid;
	int result;
	
	ObjectOutputStream out;
	ObjectInputStream in;
	
	// Local Variables
	String userType = "";
	String userMessage = "";
	String userName = "";
	int userId = 0;
	String email = "";
	int funds = 0;
	
	String playerName = "";
	int playerAge = 0;
	int playerId = 0;
	int playerClubId = 0;
	int playerValuation = 0;
	String playerStatus = "";
	int position = 0;
	
	
	volatile ArrayList<Club> clubs = new ArrayList<Club>();
	volatile ArrayList<PlayerAgent> agents = new ArrayList<PlayerAgent>();
	volatile ArrayList<Player> players = new ArrayList<Player>();
	
	// Instance of Classes	
	TransferMarket market = new TransferMarket();
		//here
	
	public UserMarketThread(Socket s, int i)
	{
		userSocket = s;
		socketid = i;
	}
	
	// Method to send Message to user
	void sendMessage(String msg)
	{
		try{
			out.writeObject(msg);
			out.flush();
			System.out.println("server>" + msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
	void recievedMessage(String msg)
	{
		System.out.println("user>" + msg);
	}
	
	/**
	 * isAuthenticate User
	 * verify if user exists
	 *  Synched
	 */
	public synchronized boolean isAuthenticateClub(String userName, int userId) {
		Club club = new Club();
		
		for(int i = 0; i<clubs.size();i++) {
			club = clubs.get(i);
			
			if (userName.compareToIgnoreCase(club.getName()) == 0 && userId == club.getClubId()) {
				return true;
			}
		}
		
		return false;
	}
	
	public synchronized boolean isAuthenticateAgent(String userName, int userId) {
		PlayerAgent agent = new PlayerAgent();
		
		for(int i = 0; i<agents.size();i++) {
			agent = agents.get(i);
			
			if (userName.compareToIgnoreCase(agent.getAgentName()) == 0 && userId == agent.getAgentId()) {
				return true;
			}
		}
		
		return false;
	}
	
	// Register new Club
	public synchronized String registerClub(Club club) {
		if (clubExists(club.getName(), club.getClubId())) {
			return "Club name or ID already exists";
		}
		try {
			FileWriter writer = new FileWriter("Clubs.txt", true);
			BufferedWriter bw = new BufferedWriter(writer);
			
			bw.write("\n" + club.toString());
			
			bw.close();
			writer.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.format("IOException: %s%n", e);
		}
		return "Successful";
	}
	
	// Register new Agent
	public synchronized String registerAgent(PlayerAgent agent) {
		if (agentExists(agent.getAgentName(), agent.getAgentId())) {
			return "Agent name or ID already exists";
		}
		try {
			FileWriter writer = new FileWriter("PlayerAgents.txt", true);
			BufferedWriter bw = new BufferedWriter(writer);
			
			bw.write("\n" + agent.toString());
			
			bw.close();
			writer.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.format("IOException: %s%n", e);
		}
		return "Successful";
	}
	
	// Agent add new player
	public synchronized String addPlayer(Player player) {
		if (playerExists(player.getId())) {
			return "Player already exists";
		}
		else if (player.getPosition() != 1 && player.getPosition() != 2 && player.getPosition() != 3 && player.getPosition() != 4) {
			return "Player's position is invalid, please try again";
		}
		
		try {
			FileWriter writer = new FileWriter("Players.txt", true);
			BufferedWriter bw = new BufferedWriter(writer);
			
			bw.write("\n" + player.toString());
			
			bw.close();
			writer.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.format("IOException: %s%n", e);
		}
		return "Successful";
	}
	
	// Check if club name or Id is already register
	public boolean clubExists(String clubName, int clubId) {
		Club club = new Club();
		
		for(int i = 0; i<clubs.size();i++) {
			club = clubs.get(i);
			
			if (clubName.compareToIgnoreCase(club.getName()) == 0 || clubId == club.getClubId()) {
				return true;
			}
		}
		
		return false;
	}
	
	// Check if agent name or Id is already register
	public boolean agentExists(String agentName, int agentId) {
		PlayerAgent agent = new PlayerAgent();
		
		for(int i = 0; i<agents.size();i++) {
			agent = agents.get(i);
			
			if (agentName.compareToIgnoreCase(agent.getAgentName()) == 0 || agentId == agent.getAgentId()) {
				return true;
			}
		}
		
		return false;
	}
	
	// Check if player Id already exists
	public boolean playerExists(int playerId) {
		Player player = new Player();
		
		for(int i = 0; i<players.size();i++) {
			player = players.get(i);
			
			if (playerId == player.getId()) {
				return true;
			}
		}
		
		return false;
	}
	
	// Returns array list of player from a specific agent, if null no players for that agent
	public ArrayList<Player> listPlayersOfAgent(int agentId){
		ArrayList<Player> agentPlayers = new ArrayList<Player>();
		Player player = new Player();
		
		for(int i = 0; i<players.size();i++) {
			player = players.get(i);
			
			if (agentId == player.getAgentId()) {
				agentPlayers.add(player);
			}
		}
		
		return agentPlayers;
	}
	
	// Update Valuation of a player according to his id an agent id
	public synchronized String updateValuation(int agentId, int playerId, int newValuation) {
		Player player = new Player();
		
		for(int i = 0; i<players.size();i++) {
			player = players.get(i);
			
			if (playerId == player.getId() && agentId == player.getAgentId()) {
				players.remove(i);
				
				player.setValuation(newValuation);
				
				players.add(player);
				
				updatePlayersFile();
				
				return "Valuation updated";
			}
		}
		
		return "No such player Id for agent Id: " + agentId;
	}
	
	// Update player status according to his id an agent id
	public synchronized String updateStatus(int agentId, int playerId, String newStatus) {
		Player player = new Player();
		
		for(int i = 0; i<players.size();i++) {
			player = players.get(i);
			
			if (playerId == player.getId() && agentId == player.getAgentId()) {		
				if(!newStatus.equals("selling") && !newStatus.equals("sold") && !newStatus.equals("suspended")) {
					return "Error " + newStatus + " is not a valid status";
				}
				players.remove(i);
				
				player.setStatus(newStatus);
				
				players.add(player);
				
				updatePlayersFile();
				
				return "Status updated";
			}
		}
		
		return "No such player Id for agent Id: " + agentId;
	}
	
	// Update Players file
	public synchronized void updatePlayersFile() {
		try {
			FileWriter writer = new FileWriter("Players.txt", false);
			BufferedWriter bw = new BufferedWriter(writer);
			Player player = new Player();
			
			player = players.get(0);
			bw.write(player.toString());
			
			for(int i = 1; i<players.size();i++) {
				player = players.get(i);
				bw.write("\n" + player.toString());
			}
				
			bw.close();
			writer.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.format("IOException: %s%n", e);
		}
	}
	
	// Display all players in one position
	public ArrayList<Player> displayPlayersByPos(int position) {
		ArrayList<Player> playersByPos = new ArrayList<Player>();
		Player player = new Player();
		
		for(int i = 0; i<players.size();i++) {
			player = players.get(i);
			
			if(position == player.getPosition()) {
				playersByPos.add(player);
			}
			
		}
		
		return playersByPos;
	}
	
	// Display players for sale in club
	public ArrayList<Player> displayClubPlayersSelling(int clubId) {
		ArrayList<Player> playersByPos = new ArrayList<Player>();
		Player player = new Player();
		
		for(int i = 0; i<players.size();i++) {
			player = players.get(i);
			
			if(clubId == player.getClubId() && player.getStatus().equals("selling")) {
				playersByPos.add(player);
			}
			
		}
		
		return playersByPos;
	}

	// Update player status according to his id an agent id
	public synchronized String suspendePlayerSale(int clubId, int playerId, String newStatus) {
		Player player = new Player();
		
		for(int i = 0; i<players.size();i++) {
			player = players.get(i);
			
			if (playerId == player.getId() && clubId == player.getClubId()) {		
				if(!newStatus.equals("selling") && !newStatus.equals("suspended")) {
					return "Error " + newStatus + " is not a valid status";
				}
				players.remove(i);
				
				player.setStatus(newStatus);
				
				players.add(player);
				
				updatePlayersFile();
				
				return "Status updated";
			}
		}
		
		return "No such player Id for club Id: " + clubId;
	}
	
	public void run()
	{
		
		try 
		{
		
			out = new ObjectOutputStream(userSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(userSocket.getInputStream());
			System.out.println("Connection"+ socketid+" from IP address "+userSocket.getInetAddress());
			
			sendMessage("Connection successful \n-------------------------------------------\n \nWelcome to the Transfer Market\n");
		
			// Load Clubs and Agents
			clubs = market.loadClubs();
			agents = market.loadAgents();
			players = market.loadPlayers();
			
			int loginAttemptsCounter = 0;
			boolean isAuth = false;
			String registered = "";
			String playerAdded = "";
			
			//Commence
			do {
				sendMessage("Please enter 1 to Login or 2 to Register or -1 to close the application");
				message = (String)in.readObject();
				recievedMessage(message);		
			} while (!message.equals("1")&&!message.equals("2")&&!message.equals("-1"));
						
			// Register
			if(message.equals("2")) {
				do {
					sendMessage("Please enter 1 to Register as a Club or 2 to Register as a Player Agent");
					message = (String)in.readObject();
					recievedMessage(message);
				} while (!message.equals("1")&&!message.equals("2"));
				
				if(message.equals("1")) {
					Club registerClub = new Club();
					
					do {
						sendMessage("Enter Club Name");
						userName = (String) in.readObject();
						registerClub.setName(userName);
						
						sendMessage("Enter Club ID");
						userMessage = (String) in.readObject();
						userId = Integer.parseInt(userMessage);
						registerClub.setClubId(userId);
						
						sendMessage("Enter Club Email Address");
						email = (String) in.readObject();
						registerClub.setEmail(email);
						
						sendMessage("Enter Club Funds Available for transfer");
						userMessage = (String) in.readObject();
						funds = Integer.parseInt(userMessage);
						registerClub.setFunds(funds);
						
						registered = registerClub(registerClub);
						if (registered.equals("Club name or ID already exists")) {
							sendMessage("Error Club name or ID already registered");
						}
						else if(!registered.equals("Successful")) {
							sendMessage("Error check your credentials and try Again");
						}
					
					} while (!registered.equals("Successful"));
					clubs = market.loadClubs();
				}
				
				else if(message.equals("2")) {
					PlayerAgent registerAgent = new PlayerAgent();
					
					do {
						sendMessage("Enter Agent Name");
						userName = (String) in.readObject();
						registerAgent.setAgentName(userName);
						
						sendMessage("Enter Agent ID");
						userMessage = (String) in.readObject();
						userId = Integer.parseInt(userMessage);
						registerAgent.setAgentId(userId);
						
						sendMessage("Enter Agent Email Address");
						email = (String) in.readObject();
						registerAgent.setEmail(email);
						
						registered = registerAgent(registerAgent);
						if (registered.equals("Agent name or ID already exists")) {
							sendMessage("Error Agent name or ID already registered");
						}
						else if(!registered.equals("Successful")) {
							sendMessage("Error check your credentials and try Again");
						}
					
					} while (!registered.equals("Successful"));									
					agents = market.loadAgents();
				}
				
				sendMessage("Registered Successfuly");
				// Ask user if he wants to login after register
				message = "";
			
				while (!message.equals("1")&&!message.equals("-1")) {
					sendMessage("Please enter 1 to Login or -1 to close the application");
					message = (String)in.readObject();
					recievedMessage(message);		
				}
			}// register
			
			// Login
			if(message.equals("1")) {
				do {
					sendMessage("Please enter 1 to Login as a Club or 2 to Login as a Player Agent");
					message = (String)in.readObject();
					recievedMessage(message);
				} while (!message.equals("1")&&!message.equals("2"));
				
				if (message.equals("1")) {
					// Set user type
					userType = "club";
					
					do {	
						sendMessage("Enter your Club Name");
						userName = (String) in.readObject();
						
						sendMessage("Enter your Club ID");
						userMessage = (String) in.readObject();
						userId = Integer.parseInt(userMessage);
						
						isAuth = isAuthenticateClub(userName, userId);
						
						if (!isAuth) {
							sendMessage("\nCould not login, check your credentials and try again or register if you are not a member\n"); 
							loginAttemptsCounter++;
						}
						else {
							userType = "club";
						}
					} while(!isAuth&&loginAttemptsCounter<3);
				}
				else if(message.equals("2")) {
					// set user type
					userType = "agent";
					
					do {
						sendMessage("Enter your Agent Name");
						userName = (String) in.readObject();
						
						sendMessage("Enter your Agent ID");
						userMessage = (String) in.readObject();
						userId = Integer.parseInt(userMessage);
						
						isAuth = isAuthenticateAgent(userName, userId);
						
						if (!isAuth) {
							sendMessage("\nCould not login, check your credentials and try again or register if you are not a member\n"); 
							loginAttemptsCounter++;
						}
						else {
							userType = "agent";
						}
					} while(!isAuth&&loginAttemptsCounter<3);
				}// if else if
				
			
				// 3 times incorrect close app
				if(loginAttemptsCounter >= 3) {
					sendMessage("Sorry you have exceeded the max number of login attemps, exiting application...");
				}
				else {
					sendMessage("Login Successful!");
				
					// display menu for logged in club
					if(userType.compareTo("club") == 0) {
						sendMessage("club");
						//do while
						do {
							sendMessage("Please enter 1 to search for players in a given postition\n2 to search for players for sale in your club, 3 to Suspend/Resume the sale of a player from your club \n4 to purchase a player or -1 to logout");
							message = (String)in.readObject();
							recievedMessage(message);
							
							// Display all players by position
							if(message.equals("1")) {
								ArrayList<Player> displayPlayers= new ArrayList<Player>();
								do {
									sendMessage("Please enter position to display, 1 = goalkeeper, 2 = defender, 3 = midfield and 4 = attacker");
									userMessage = (String)in.readObject();
									position = Integer.parseInt(userMessage);
								} while(position != 1 && position != 2 && position != 3 && position != 4);
								
								displayPlayers = displayPlayersByPos(position);
								
								Player player = new Player();
								
								sendMessage(Integer.toString(displayPlayers.size()));
								
								for(int i = 0; i<displayPlayers.size();i++) {
									player = displayPlayers.get(i);
									
									sendMessage(player.toString());
								}
							}
							// Display club players selling
							else if(message.equals("2")) {
								ArrayList<Player> displayPlayers= new ArrayList<Player>();
								
								displayPlayers = displayClubPlayersSelling(userId);
								
								Player player = new Player();
								
								sendMessage(Integer.toString(displayPlayers.size()));
								
								for(int i = 0; i<displayPlayers.size();i++) {
									player = displayPlayers.get(i);
									
									sendMessage(player.toString());
								}
							}
							// Update Status
							else if(message.equals("3")) {
								sendMessage("Enter Players Id");
								userMessage = (String) in.readObject();
								playerId = Integer.parseInt(userMessage);
								
								sendMessage("Enter new Status, supended or selling");
								playerStatus = (String) in.readObject();
								
								message = suspendePlayerSale(userId, playerId, playerStatus);
								
								if(message.equals("Status updated")) {
									players = market.loadPlayers();
								}
								
								sendMessage(message);
							}
							
						} while (!message.equals("-1"));
						
						
						
					}
					// display menu for logged in agent
					else if (userType.compareTo("agent") == 0) {
						sendMessage("agent");
						do {
							sendMessage("Please enter 1 to Add a Player, 2 to update a Player's Valuation, 3 to update player Status or -1 to logout");
							message = (String)in.readObject();
							recievedMessage(message);
							
							// Add Player
							if(message.equals("1")) {
								Player player = new Player();
								do {
									sendMessage("Enter Player Name");
									playerName = (String) in.readObject();
									player.setName(playerName);
									
									sendMessage("Enter Player Age");
									userMessage = (String) in.readObject();
									playerAge = Integer.parseInt(userMessage);
									player.setAge(playerAge);
									
									sendMessage("Enter Player Id");
									userMessage = (String) in.readObject();
									playerId = Integer.parseInt(userMessage);
									player.setId(playerId);
									
									sendMessage("Enter Club Id");
									userMessage = (String) in.readObject();
									playerClubId = Integer.parseInt(userMessage);
									player.setClubId(playerClubId);
									
									player.setAgentId(userId);
									
									sendMessage("Enter Player's Valuation");
									userMessage = (String) in.readObject();
									playerValuation = Integer.parseInt(userMessage);
									player.setValuation(playerValuation);
									
									player.setStatus("selling");
									
									sendMessage("Enter Player's Position 1 = Goalkeeper, 2 = Defender, 3 = Midfield and 4 = Attacker");
									userMessage = (String) in.readObject();
									position = Integer.parseInt(userMessage);
									player.setPosition(position);
									
									playerAdded = addPlayer(player);
									
									if(playerAdded.equals("Player already exists")) {
										sendMessage("Player Id already Registered please use a different Id");
									
									}
									else if(playerAdded.equals("Player's position is invalid, please try again")) {
										sendMessage(playerAdded);
									}
								} while (!playerAdded.equals("Successful"));
								
								sendMessage("Player Added Successfuly");
							}
							// Update Valuation
							else if(message.equals("2")) {
								sendMessage("Enter Players Id");
								userMessage = (String) in.readObject();
								playerId = Integer.parseInt(userMessage);
								
								sendMessage("Enter new Valuation");
								userMessage = (String) in.readObject();
								playerValuation = Integer.parseInt(userMessage);
								
								message = updateValuation(userId, playerId, playerValuation);
								
								if(message.equals("Valuation updated")) {
									players = market.loadPlayers();
								}
								
								sendMessage(message);
							}
							// Update Status
							else if(message.equals("3")) {
								sendMessage("Enter Players Id");
								userMessage = (String) in.readObject();
								playerId = Integer.parseInt(userMessage);
								
								sendMessage("Enter new Status");
								playerStatus = (String) in.readObject();
								
								message = updateStatus(userId, playerId, playerStatus);
								
								if(message.equals("Status updated")) {
									players = market.loadPlayers();
								}
								
								sendMessage(message);
							}
						} while (!message.equals("-1"));
					}
					
				}
			}// login
			
			sendMessage("User finished - Connection Closed");
		
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				out.close();
				in.close();
				userSocket.close();
			}
			
	
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
}// UserMarketThread
