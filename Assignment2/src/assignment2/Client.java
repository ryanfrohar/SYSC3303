package assignment2;


//Assignment 2
//Ryan Frohar 101029053

import java.io.*;
import java.net.*;

public class Client {

	private DatagramSocket socket;
	private DatagramPacket sendPacket, receivePacket;

	public Client() {
		try {
			socket = new DatagramSocket(); //Create a DatagramPacket
		} catch (SocketException socEx) {
			socEx.printStackTrace();
			System.exit(1);
		}
	}

	public void sendReceiveDatagram() {

		byte[] message = new byte[100]; 		//Message to be sent
		byte[] filebyte = new byte[100]; 	//Filename as an array of bytes
		byte[] modebyte = new byte[100]; 	//Mode as an array of bytes
		byte[] packet = new byte[100]; 		//Content of packet as an array of bytes
		String filename, mode; 				//Filename and mode as string
		int hostPort = 23; 					//Port 23 on the intermediate host

		//Send 11 packets, 5 read, 5 write and 1 invalid request.
		//Loop to determine what each packet contains. Even = read, odd = write.

		for (int i = 0; i < 11; i++) {
			message[0] = 0; //Both read and write packets start with a 0

			if(i%2 == 0) {
				message[1] = 1; //Set second byte to 1 to be read packet
			}
			else {
				message[1] = 2; //Set second byte to 2 to be write packet
			}
			if (i == 10) {
				message[1] = 7; //Invalid request
			}

			//Add filename converted from a string to bytes
			filename = "test.txt";
			filebyte = filename.getBytes();
			System.arraycopy(filebyte, 0, message, 2, filebyte.length); //Add filebyte into 2nd element after message

			//Add a 0 byte
			message[filebyte.length + 2] = 0; //Add a 0 byte after the filename and 2 bytes

			//Add a mode converted from a string to bytes
			mode = "ocTEt"; //Set mode to ocTEt
			modebyte = mode.getBytes();
			System.arraycopy(modebyte, 0, message, filebyte.length+3, modebyte.length);

			//Add a final 0 byte
			int msgLength = filebyte.length + modebyte.length+4; //Determine how long the message packet is with the 4 bytes + the filebyte and modebyte
			message[msgLength - 1] = 0; //Add the last 0 byte at the end of the message

			try {
				sendPacket = new DatagramPacket(message, msgLength, InetAddress.getLocalHost(), hostPort); //Create packet
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}

			System.out.println("Client: Sending Packet " + (i+1));
			System.out.println("To host: " + sendPacket.getAddress());
			System.out.println("Destination host port: " + sendPacket.getPort());
			System.out.println("Length: " + sendPacket.getLength());
			System.out.println("Containing: ");
			packet = sendPacket.getData();

			//Print the send packet as bytes
			for(int k = 0; k < msgLength; k++) {
				System.out.println(k + ": " + packet[k]);
			}

			try {
				socket.send(sendPacket); //Send packet
			} catch(IOException e){
				e.printStackTrace();
				System.exit(1);
			}

			//Receives a DatagramPacket from the intermediate host and prints out the information received, including the byte array

			packet = new byte[100];
			receivePacket = new DatagramPacket(packet, packet.length);

			try {
				socket.receive(receivePacket); //Receive packet
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}

			System.out.println("Client: Packet received:");
			System.out.println("From host: " + receivePacket.getAddress());
			System.out.println("Host port: " + receivePacket.getPort());
			System.out.println("Length: " + receivePacket.getLength());
			System.out.println("Containing: ");
			packet = receivePacket.getData();

			//Print the received packet as bytes
			for (int b = 0; b < receivePacket.getLength(); b++) {
				System.out.println(b + ": " + packet[b]);
			}
			System.out.println();
		}
		socket.close();
	}

	public static void main(String[] args) {
		Client client1 = new Client();
		client1.sendReceiveDatagram();
	}
}
