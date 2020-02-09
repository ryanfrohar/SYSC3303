package assignment2;

//Assignment 2
//Ryan Frohar 101029053

import java.io.*;
import java.net.*;

public class Host {

	private DatagramSocket socket, sendSocket, receiveSocket;
	private DatagramPacket sendPacket, receivePacket;

	public Host() {
		try {
			receiveSocket = new DatagramSocket(23); //Create a DatagramSocket to use to receive (port 23)
			socket = new DatagramSocket(); //Create a DatagramSocket to use to send and receive
		} catch(SocketException se){
			se.printStackTrace();
			System.exit(1);
		}
	}

	public void receiveAndSend() {

		byte[] info, senddata;

		for(;;) { //Repeat the following "forever"

		info = new byte[100];
		receivePacket = new DatagramPacket(info, info.length);

		try {
			receiveSocket.receive(receivePacket); //Wait to receive a request
		} catch(IOException r) {
			r.printStackTrace();
			System.exit(1);
		}

		//Print out the information it has received (print the request both as a String and as bytes)
		System.out.println("Host: Packet received:");
		System.out.println("From host: " + receivePacket.getAddress());
		System.out.println("Host port: " + receivePacket.getPort());
		System.out.println("Length: " + receivePacket.getLength());
		System.out.println("Containing: " );
		info = receivePacket.getData();

		//Print the request as bytes
		for (int b = 0; b < receivePacket.getLength(); b++){
			System.out.println(b + ": " + info[b]);
		}

		//Print the request as string
		String infoinstr = new String(info, 0, receivePacket.getLength());
		System.out.println(infoinstr);

		//Form a packet to send containing exactly what it received
		sendPacket = new DatagramPacket(info, receivePacket.getLength(), receivePacket.getAddress(), 69);


		DatagramPacket client = receivePacket;
		
		//Print out the information it is about to send
		System.out.println("Host: sending packet.");
		System.out.println("To host: " + sendPacket.getAddress());
		System.out.println("Destination host port: " + sendPacket.getPort());
		System.out.println("Length: " + sendPacket.getLength());
		System.out.println("Containing: ");
		info = sendPacket.getData();

		//Print out this information
		for (int j = 0; j < sendPacket.getLength(); j++) {
			System.out.println( j + ": " + info[j]);
		}

		try {
			socket.send(sendPacket); //Send this packet on its send/receive socket to port 69
		} catch(IOException i) {
			i.printStackTrace();
			System.exit(1);
		}

		info = new byte[100];
		receivePacket = new DatagramPacket(info, info.length);

		System.out.println("Waiting for incoming packet");
		
		try {
			socket.receive(receivePacket);	//Wait to receive a response
		} catch(IOException i) {
			i.printStackTrace();
			System.exit(1);
		}

		//Print out the information received
		System.out.println("Host: Packet received:");
		System.out.println("From host: " + receivePacket.getAddress());
		System.out.println("Host port: " + receivePacket.getPort());
		System.out.println("Length: " + receivePacket.getLength());
		System.out.println("Containing: ");
		info = receivePacket.getData();

		//Print received info as bytes
		for (int j = 0; j < receivePacket.getLength(); j++) {
			System.out.println(j + ": " + info[j]);
		}

		//Print the request as string
		infoinstr = new String(info, 0, receivePacket.getLength());
		System.out.println(infoinstr);

		//Form a packet to send back to the host sending the request
		sendPacket = new DatagramPacket(info, receivePacket.getLength(), client.getAddress(), client.getPort());

		//Print out the information it is about to send
		System.out.println("Host: sending packet.");
		System.out.println("To host: " + sendPacket.getAddress());
		System.out.println("Destination host port: " + sendPacket.getPort());
		System.out.println("Length: " + sendPacket.getLength());
		System.out.println("Containing: ");
		senddata = sendPacket.getData();

		//Print out the information being sent as bytes
		for (int j=0;j<sendPacket.getLength();j++) {
			System.out.println( j + ": " + senddata[j]);
		}

		//Print out the information being sent as string
		infoinstr = new String(senddata, 0, sendPacket.getLength());
		System.out.println(infoinstr);

		try {
			sendSocket = new DatagramSocket(); //Create a DatagramSocket to use to send this request
		} catch(SocketException s) {
			s.printStackTrace();
			System.exit(1);
		}

		try {
			sendSocket.send(sendPacket); //Sends the request
		} catch(IOException i) {
			i.printStackTrace();
			System.exit(1);
		}

		System.out.println("Host: packet sent");
		sendSocket.close();
	}
}

	public static void main(String[] args) {
		Host host = new Host();
		host.receiveAndSend();
	}
}

