package assignment2;

//Assignment 2
//Ryan Frohar 101029053

import java.io.*;
import java.net.*;

public class Server {

	private DatagramSocket receiveSocket, sendSocket;
	private DatagramPacket receivePacket, sendPacket;
	public static final byte[] READ = {0, 3, 0, 1};
	public static final byte[] WRITE = {0, 4, 0, 0};

	public Server() {
		try {
			//create a datagram socket using port constructor
			receiveSocket = new DatagramSocket(69);
		} catch(SocketException s) {
			//if exception caught, print stack trace
			s.printStackTrace();
			System.exit(1);
		}
	}

	public void sendReceiveServer() {

		byte[] content, out;
		byte[] answer = new byte[4];
		int type, length;
		int j = 0, c = 0;
		String filein, modein;

		while(true) { //Repeat the following "forever"

		//Create content array of 100 bytes
		content = new byte[100];
		receivePacket = new DatagramPacket(content, content.length);

		try {
			//wait to recieve a packet request
			receiveSocket.receive(receivePacket);
		} catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		//Print out the received datagram packet
		System.out.println("Server: Packet received on port:" + receiveSocket.getLocalPort());
		System.out.println("From host: " + receivePacket.getAddress());
		System.out.println("Host port: " + receivePacket.getPort());
		System.out.println("Length: " + receivePacket.getLength());
		System.out.println("Containing: ");
		content = receivePacket.getData();

		//Print the received packet as bytes
		for (int p = 0; p < receivePacket.getLength(); p++) {
			System.out.println(p + ": " + content[p]);
		}

		//Print the received packet as string
		String contentreceived = new String(content, 0, receivePacket.getLength());
		System.out.println(contentreceived);

		//Determine whether the received packet is read, write or invalid based on first two bits

		//Check first byte, it should be 0 for read or write and if not it is invalid
		if (content[0] != 0)  {
			type = 0; //Packet received is invalid
		}
		else if (content[1] == 1) {
			type = 1; //Packet received could be a "read" since the second byte is a 1
		}
		else if (content[1] == 2) {
			type = 2; //Packet received could be a "write" since the second byte is a 2
		}
		else {
			type = 0; //Packet received is invalid since first two bits are not 0 or 1
		}

		length = receivePacket.getLength();

		//Check for 0 byte
		if (type != 0) {
			for (j = 2; j < length; j++) {
				if (content[j]==0) break;
			}

			if (j == length) type = 0; //If it reaches the end wit out 0 byte its invalid
			filein = new String(content, 2, j - 2); //Get filename
		}

		//Check for 0 byte
		if (type != 0) {
			for (c = j + 1; c < length; c++) {
				if (content[c] == 0) break;
			}

			if (c == length) type = 0;
			modein = new String(content, j, c- j- 2);
		}

		//This means there is no 0 byte at the end
		if (content[length-1] != 0) {
			type = 0;
		}

		//Create a response based on type which was found above
		if (type == 1) {
			answer = READ; //If the packet is valid read request it sends back 0301
		}
		else if (type == 2) {
			answer = WRITE;//if the packet is valid write request it sends back 0400
		}
		else {
			throw new IllegalArgumentException("Quit"); //Throw error
		}

		//Create a new Datagram packet containing answer which is an array of bytes
		sendPacket = new DatagramPacket(answer, answer.length, receivePacket.getAddress(), receivePacket.getPort());

		//Print out the response packet information
		System.out.println( "Server: Sending packet:");
		System.out.println("To host: " + sendPacket.getAddress());
		System.out.println("Destination host port: " + sendPacket.getPort());
		System.out.println("Length: " + sendPacket.getLength());
		System.out.println("Containing: ");
		out = sendPacket.getData();

		//Print out the information being sent as bytes
		for (int t = 0; t < sendPacket.getLength(); t++) {
			System.out.println("byte " + t + " " + out[t]);
		}

		try {
			sendSocket = new DatagramSocket(); //Create a DatagramSocket to use just for this response
		} catch (SocketException se) {
			se.printStackTrace();
			System.exit(1);
		}

		try {
			sendSocket.send(sendPacket); //And sends the packet via the new socket to the port it received the request from
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		System.out.println("Server: packet sent using port " + sendSocket.getLocalPort()); //Print out which port it sent the packet to
		System.out.println();
		//Need to close socket after each sendSocket was created
		sendSocket.close(); 
		}
	}

	public static void main(String[] args) {
		Server server = new Server();
		server.sendReceiveServer();
	}
}
