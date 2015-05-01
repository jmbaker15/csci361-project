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

class Client {
	public static void main(String... args) throws Exception 
	{
		if (args.length < 1) {
	         System.out.println("Connect to port? : ");
	         return;
	    }
		final String host = args[0]; 
		final int port = Integer.parseInt(args[1]); 
		Socket soc = new Socket(host, port);
		FTPClientThread thread = new FTPClientThread(soc);
		thread.Menu();

	}
}


