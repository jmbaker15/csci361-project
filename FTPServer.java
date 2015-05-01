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
			FTPServerThread thread = new FTPServerThread(soc.accept());
		}
	}
}


