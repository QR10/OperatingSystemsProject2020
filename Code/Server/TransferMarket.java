package ie.gmit;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TransferMarket {
	
	// Loads all the clubs from a file into an arrayList of clubs
	public ArrayList<Club> loadClubs() throws IOException {
		String line;
		ArrayList<Club> clubs = new ArrayList<Club>();
		
		try {
			FileReader readClubsFile = new FileReader("clubs.txt");
			BufferedReader bufferedClubsFile = new BufferedReader(readClubsFile);
			
			// while file has values, add to the array list
	        while ((line = bufferedClubsFile.readLine()) != null) { // Stop reading file when -1 is reached
	            Scanner scanner = new Scanner(line);

	            Club c = new Club();
	            
	            c.setName(scanner.next());
	            c.setClubId(scanner.nextInt());
	            c.setEmail(scanner.next());
	            c.setFunds(scanner.nextInt());
	            
	            clubs.add(c);
	            
	            // Close scanner
	            scanner.close();
	        }
	        
	        // Closing file
	        bufferedClubsFile.close();
	        readClubsFile.close();
	        
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return clubs;
	}
	
	// Loads all the clubs from a file into an arrayList of clubs
	public ArrayList<PlayerAgent> loadAgents() throws IOException {
		String line;
		ArrayList<PlayerAgent> agents = new ArrayList<PlayerAgent>();
		
		try {
			FileReader readAgentsFile = new FileReader("PlayerAgents.txt");
			BufferedReader bufferedAgentsFile = new BufferedReader(readAgentsFile);
			
			// while file has values, add to the array list
	        while ((line = bufferedAgentsFile.readLine()) != null) { // Stop reading file when -1 is reached
	            Scanner scanner = new Scanner(line);

	            PlayerAgent pAgent = new PlayerAgent();
	            
	            pAgent.setAgentName(scanner.next());
	            pAgent.setAgentId(scanner.nextInt());
	            pAgent.setEmail(scanner.next());
	            
	            agents.add(pAgent);
	            
	            // Close scanner
	            scanner.close();
	        }
	        
	        // Closing file
	        bufferedAgentsFile.close();
	        readAgentsFile.close();
	        
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return agents;
	}
	
	// Loads all the clubs from a file into an arrayList of clubs
	public ArrayList<Player> loadPlayers() throws IOException {
		String line;
		ArrayList<Player> players = new ArrayList<Player>();
		
		try {
			FileReader readPlayersFile = new FileReader("Players.txt");
			BufferedReader bufferedPlayersFile = new BufferedReader(readPlayersFile);
			
			// while file has values, add to the array list
	        while ((line = bufferedPlayersFile.readLine()) != null) { // Stop reading file when -1 is reached
	            Scanner scanner = new Scanner(line);

	            Player player = new Player();
	            
	            player.setName(scanner.next());
	            player.setAge(scanner.nextInt());
	            player.setId(scanner.nextInt());
	            player.setClubId(scanner.nextInt());
	            player.setAgentId(scanner.nextInt());
	            player.setValuation(scanner.nextInt());
	            player.setStatus(scanner.next());
	            player.setPosition(scanner.nextInt());

	            players.add(player);
	            
	            // Close scanner
	            scanner.close();
	        }
	        
	        // Closing file
	        bufferedPlayersFile.close();
	        readPlayersFile.close();
	        
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return players;
	}
}
