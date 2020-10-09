package ie.gmit;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Scanner;
//test
public class Client
{

	private Socket connection;
	private String message;
	private  Scanner console;
	private  String ipaddress = "51.145.24.161";
	private  int portaddress = 10000;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	public Client()
	{
		console = new Scanner(System.in);
	}

	void sendMessage(String msg)
	{
		try{
			out.writeObject(msg);
			out.flush();
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}


	public static void main(String[] args)
	{
			Client temp = new Client();
			temp.clientapp();
	}

	public void clientapp()
	{

		try
		{
			connection = new Socket(ipaddress,portaddress);
			int loginAttempts = 0;

			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			System.out.println("Client Side ready to communicate");


		    /// Client App.



			message = (String)in.readObject();
			System.out.println(message);


			do {
				message = (String)in.readObject();
				System.out.println(message);
				message = console.next();
				sendMessage(message);
				if(!message.equals("1")&&!message.equals("2")&&!message.equals("-1")) {
					System.out.println("\n******* Option not valid, please try again *******\n");
				}
			} while(!message.equals("1")&&!message.equals("2")&&!message.equals("-1"));

			// Register
			if(message.equals("2")) {
				do {
					message = (String)in.readObject();
					System.out.println(message);
					message = console.next();
					sendMessage(message);
				} while (!message.equals("1")&&!message.equals("2"));

				// Register Club
				if(message.equals("1")) {
					do {
						message = (String)in.readObject();
						System.out.println(message);
						message = console.next();
						sendMessage(message);

						message = (String)in.readObject();
						System.out.println(message);
						message = console.next();
						sendMessage(message);

						message = (String)in.readObject();
						System.out.println(message);
						message = console.next();
						sendMessage(message);

						message = (String)in.readObject();
						System.out.println(message);
						message = console.next();
						sendMessage(message);

						message = (String)in.readObject();
						System.out.println(message);

					} while(!message.equals("Registered Successfuly"));
				}

				// Register Agent
				else if(message.equals("2")) {
					do {
						message = (String)in.readObject();
						System.out.println(message);
						message = console.next();
						sendMessage(message);

						message = (String)in.readObject();
						System.out.println(message);
						message = console.next();
						sendMessage(message);

						message = (String)in.readObject();
						System.out.println(message);
						message = console.next();
						sendMessage(message);

						message = (String)in.readObject();
						System.out.println(message);
					} while(!message.equals("Registered Successfuly"));
				}

				while(!message.equals("1")&&!message.equals("-1")) {
					message = (String)in.readObject();
					System.out.println(message);
					message = console.next();
					sendMessage(message);
					if(!message.equals("1")&&!message.equals("-1")) {
						System.out.println("\n******* Option not valid, please try again *******\n");
					}
				}
			}

			// Login
			if(message.equals("1")) {
				do {
					message = (String)in.readObject();
					System.out.println(message);
					message = console.next();
					sendMessage(message);
				} while (!message.equals("1")&&!message.equals("2"));

				do {
					message = (String)in.readObject();
					System.out.println(message);
					message = console.next();
					sendMessage(message);

					message = (String)in.readObject();
					System.out.println(message);
					message = console.next();
					sendMessage(message);

					message = (String)in.readObject();
					System.out.println(message + "\n");
					loginAttempts++;

				} while(!message.equals("Login Successful!")&&loginAttempts<3);

				if(loginAttempts>=3) {
					message = (String)in.readObject();
					System.out.println(message);

					out.close();
					in.close();
					connection.close();

					return;
				}
				else {
					// Login success
					message = (String)in.readObject();

					// Club menu
					if (message.equals("club")) {
						do {
							message = (String)in.readObject();
							System.out.println(message);
							message = console.next();
							sendMessage(message);
							if(!message.equals("1")&&!message.equals("2")&&!message.equals("3")&&!message.equals("4")&&!message.equals("-1")) {
								System.out.println("\n******* Option not valid, please try again *******\n");
							}
							// Display players by position
							if(message.equals("1")) {
								do {
									message = (String)in.readObject();
									System.out.println(message);
									message = console.next();
									sendMessage(message);
								} while (!message.equals("1")&&!message.equals("2")&&!message.equals("3")&&!message.equals("4"));

								message = (String)in.readObject();
								int indexOfPlayers = Integer.parseInt(message);

								// Header
								System.out.println("\nName, Age, Id, ClubID, AgentId, Valuation, Status, Position");
								System.out.println("===========================================================");
								for (int i = 0; i < indexOfPlayers; i++) {
									message = (String)in.readObject();
									System.out.println(message);
								}
								System.out.println();
							}
							// Display players from the club
							else if(message.equals("2")) {
								message = (String)in.readObject();
								int indexOfPlayers = Integer.parseInt(message);

								// Header
								System.out.println("\nName, Age, Id, ClubID, AgentId, Valuation, Status, Position");
								System.out.println("===========================================================");
								for (int i = 0; i < indexOfPlayers; i++) {
									message = (String)in.readObject();
									System.out.println(message);
								}
								System.out.println();
							}
							// Suspend or resume player sale
							else if(message.equals("3")) {
								message = (String)in.readObject();
								System.out.println(message);
								message = console.next();
								sendMessage(message);

								message = (String)in.readObject();
								System.out.println(message);
								message = console.next();
								sendMessage(message);

								message = (String)in.readObject();
								System.out.println(message);
							}
						} while(!message.equals("-1"));
					}// club menu
					// Agent menu
					else if(message.equals("agent")) {
						do {
							message = (String)in.readObject();
							System.out.println(message);
							message = console.next();
							sendMessage(message);
							if(!message.equals("1")&&!message.equals("2")&&!message.equals("3")&&!message.equals("-1")) {
								System.out.println("\n******* Option not valid, please try again *******\n");
							}

							// Add player
							if(message.equals("1")) {
								do {
									message = (String)in.readObject();
									System.out.println(message);
									message = console.next();
									sendMessage(message);

									message = (String)in.readObject();
									System.out.println(message);
									message = console.next();
									sendMessage(message);

									message = (String)in.readObject();
									System.out.println(message);
									message = console.next();
									sendMessage(message);

									message = (String)in.readObject();
									System.out.println(message);
									message = console.next();
									sendMessage(message);

									message = (String)in.readObject();
									System.out.println(message);
									message = console.next();
									sendMessage(message);

									message = (String)in.readObject();
									System.out.println(message);
									message = console.next();
									sendMessage(message);

									message = (String)in.readObject();
									System.out.println(message);
								} while (!message.equals("Successful"));
							}// Add Player

							// Update Valuation
							else if (message.equals("2")) {
								message = (String)in.readObject();
								System.out.println(message);
								message = console.next();
								sendMessage(message);

								message = (String)in.readObject();
								System.out.println(message);
								message = console.next();
								sendMessage(message);

								message = (String)in.readObject();
								System.out.println(message);
							}
							// Update Status
							else if(message.equals("3")) {
								message = (String)in.readObject();
								System.out.println(message);
								message = console.next();
								sendMessage(message);

								message = (String)in.readObject();
								System.out.println(message);
								message = console.next();
								sendMessage(message);

								message = (String)in.readObject();
								System.out.println(message);
							}


						} while(!message.equals("-1"));

					}// Agent Menu

				}// Login
			}

			// Close application
			if (message.equals("-1")) {
				System.out.println("\n///////////////////////////////////////////////////\n///   Thank you for using Tranfer Market, bye!  ///\n///////////////////////////////////////////////////");

				out.close();
				in.close();
				connection.close();

				return;
			}

		}

		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
