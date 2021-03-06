//*********************************************************
//*	Joshua Baker
//*	CSCI 361
//*	4/30/15
//*	Mini-Project
//*	10368442
//*********************************************************

import java.net.*;
import java.io.*;
import java.util.*;

public class Server {
	public static void main(String[] args) throws Exception {
	if (args.length < 1) {System.out.println("Choose a port: ");
		return;
	}
	
	int port = Integer.parseInt(args[0]);
	ServerSocket soc = new ServerSocket(port);
	System.out.println("Sever is running on port " + port);
		while (true) {
			System.out.println("Connecting....");
			ServerThread thread = new ServerThread(soc.accept());
		}
	}
}

class ServerThread extends Thread { //creates thread
	Socket ClientSoc;

	DataInputStream is;
	DataOutputStream os;

	ServerThread(Socket soc) {

		try {
			ClientSoc = soc;
			is = new DataInputStream(ClientSoc.getInputStream());
			os = new DataOutputStream(ClientSoc.getOutputStream());
			System.out.println("FTP Client Connected ...");
			start();

		} catch (Exception ex) {
		}
	}

	Boolean LogOn(String username, String password) throws Exception {
		if (username.compareTo("jmbaker") == 0) { //hardcoded username
			if (password.compareTo("8442") == 0) {//hardcoded pass
				os.writeUTF("Connected");//sends auth to client to print confirmation
				return true;
			} else {
				os.writeUTF("Password incorrect");//pass incorrect
				return false;
			}

		} else if (username.compareTo("fwang") == 0) {//same as above for different user
			if (password.compareTo("csci361") == 0) {
				os.writeUTF("Connected");
				return true;
			} else {
				os.writeUTF("Password incorrect");
				return false;
			}
		}
		os.writeUTF("Username not in system");//if input username does not exist
		return false;
	}

	void SendFile() throws Exception {//send file funcion
		String filename = is.readUTF();
		File f = new File(System.getProperty("user.dir") + "\\" + filename);
		if (!f.exists()) {
			os.writeUTF("File Not Found");
			return;
		} else {
			os.writeUTF("READY");
			FileInputStream fin = new FileInputStream(f);
			int ch;
			do {
				ch = fin.read();
				os.writeUTF(String.valueOf(ch));
			} while (ch != -1);
			fin.close();
			os.writeUTF("File Receive Successful");
		}
	}

	void ReceiveFile() throws Exception {//receive file function
		String filename = is.readUTF();
		if (filename.compareTo("File not found") == 0) {
			return;
		}
		File f = new File(System.getProperty("user.dir") + "\\" + filename);
		String option;

		if (f.exists()) {
			os.writeUTF("File already exists");//checks if file is already present
			option = is.readUTF();
		} else {
			os.writeUTF("SendFile"); //sends file
			option = "Y";
		}

		if (option.compareTo("Y") == 0) {
			FileOutputStream fout = new FileOutputStream(f);
			int ch;
			String temp;
			do {
				temp = is.readUTF();
				ch = Integer.parseInt(temp);
				if (ch != -1) {
					fout.write(ch);
				}
			} while (ch != -1);
			fout.close();
			os.writeUTF("File Send Successful");//transmission was sent by the client
		} else {
			return;
		}

	}

	public void run() {
		String homeDirectory = System.getProperty("user.dir");
		boolean active = true;
		boolean logon = false;
		double count=0; //exit variable
		while (active) {

			try {
				System.out.println("Waiting for Command ...");
				count=count+1;
					if (count >50)  //fixes bug in system when the client exits it 								//would just sit in an infiite loop so 								//now after 50 runs it exits with the 								//below command.
					 System.exit(0);
				
				String Command = is.readUTF();

				

				if (Command.compareTo("LOGON") == 0) {
					System.out.println("\tLOGON Command Received ...");
					String username = is.readUTF();
					String password = is.readUTF();
					logon = LogOn(username, password);
					

				} else if ((Command.compareTo("GET") == 0) && logon) {   //sends file to client
					System.out.println("\tGET Command Received ...");
					SendFile();
					continue;
				} else if ((Command.compareTo("SEND") == 0) && logon) { //receives file from client
					System.out.println("\tSEND Command Received ...");
					ReceiveFile();
					continue;
				} else if ((Command.compareTo("DIR") == 0) && logon) { //show current directory
					System.out.println("\tDIR Command Received ...");
					os.writeUTF("\nCurrent directory:\n\t"
							+ System.getProperty("user.dir"));
					String files = "";
					File folder = new File(System.getProperty("user.dir"));
					File[] listOfFiles = folder.listFiles();

					for (int i = 0; i < listOfFiles.length; i++) {

						if (listOfFiles[i].isFile()) {
							files = files + "\n\t\t" + listOfFiles[i].getName();
						}
					}
					os.writeUTF(files);
					continue;
				} else if ((Command.compareTo("CD") == 0) && logon) {  // changes directory
					System.out.println("\tCD Command Received ...");
					System.setProperty("user.dir", is.readUTF());
					os.writeUTF("Directory change Successful");
					continue;
				} else if ((Command.compareTo("DISCONNECT") == 0) && logon) {  //exits per user command
					System.out.println("\tDisconnect Command Received ...");
					System.setProperty("user.dir", homeDirectory);
					active = false;
				}
			} catch (Exception ex) {
			}
		}
	}
}
