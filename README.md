# csci361-project
a from scratch ftp client server application

*	Joshua Baker
*	10368442
*	CSCI 361
*	4/30/15
*	Mini-Project

this readme is a work in progress

The server must be started before the client on the host machine, 


Complie the server.java file with the command "javac Server.java" this can be done anywhere on the server.
Run the server with the command "java Server (port)"

do the same for the Client.java file this can also be done anywhere as you can change directory per the user commands
The client must be started with the command "java Client (host IP) (port)"

Server.java

	This is the server side of the the application, this houses the *hardcoded* user 
	names and passwords. 
	the user for this example is "jmbaker", the password is "8442". 
	there is a second user by the name "fwang" and passowrd  "csci361" 
	This code also contains all of the commands needed to navigate about the server
	


Client.java

	Client side of things, houses basic connection info. the server does most of 
	the work .This program comapres the server responses with know good reponses
	and outputs based on the response. This program must be run after the server 
	is running. 

Operaton
	
	The client can send the the server user input based on what the user would 
	like to do. The user can view the contents of the current directory similar
	to ls -l The user can change directories similar to cd The user can send 
	file and receive files to and from the server The directory on the server 
	in which to send or receive filse can be changed by the user
