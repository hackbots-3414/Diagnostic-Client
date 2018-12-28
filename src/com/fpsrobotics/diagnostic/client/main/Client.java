package com.fpsrobotics.diagnostic.client.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	private static Socket socket;
	static String message;
	static Scanner scanner = new Scanner(System.in);
	static int port = 5800;
	private static boolean isTimeoutOn = true;
	// String username = scanner.nextLine();

	public static void main(String args[]) {//TODO Delete this line
		System.out.println("Hackbots Diagnostic Client 1.0");
		parseInput(); //TODO Delete this line
	}//TODO Delete this line

	public static void parseInput() {
    	System.out.println("Input command:");
    	String input = scanner.nextLine();
    	if(input.equalsIgnoreCase("connect")) {
        	System.out.println("Connecting to robot on port "+port);
        	execute();
    	}
    	else if(input.equalsIgnoreCase("connect local")) {
    		System.out.println("Connecting to local server on port "+port);
    		executeTest();
    	}
    	else if(input.equalsIgnoreCase("exit")) {
    		System.exit(0);
    }
    	else if(input.equalsIgnoreCase("notimeout")) {
    		System.out.println("There's no way to turn this off");
    		isTimeoutOn = false;
    		parseInput();
    		}
    	else {
    	System.out.println("use 'exit', 'connect', 'connect local', 'notimeout'");
    	parseInput();
    	}
    }
	public static void execute() {
		try {
			String host = "10.34.14.16";
			InetAddress address = InetAddress.getByName(host);
			socket = new Socket(address, port);
			// Send the message to the server
			OutputStream os = socket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter bw = new BufferedWriter(osw);
			message = scanner.nextLine();

			String sendMessage = message + "\n";
			bw.write(sendMessage);
			bw.flush();
			//System.out.println("Message sent to the server  " + sendMessage);

			// Get the return message from the server
			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String message = br.readLine();
			System.out.println("ROBOT:  " + message);
			execute();//start over
			
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
			//exception.printStackTrace();
			parseInput();
		} finally {
			// Closing the socket
			try {
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		}
	public static void executeTest() {
		try {
			String host = "localhost";
			int port = 5800;
			InetAddress address = InetAddress.getByName(host);
			socket = new Socket(address, port);
			// Send the message to the server
			OutputStream os = socket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter bw = new BufferedWriter(osw);
			message = scanner.nextLine();

			String sendMessage = message + "\n";
			bw.write(sendMessage);
			bw.flush();
			//System.out.println("Message sent to the server  " + sendMessage);

			// Get the return message from the server
			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String message = br.readLine();
			System.out.println("ROBOT: " + message);
			execute();//start over
			
		} catch (Exception exception) {
			if(exception.getMessage().equals("Connection timed out: connect\r\n" + "")) { //TODO Fix this so there are no more timeouts
				System.out.println("The connection timed out, reconnecting...");
				executeTest();
			}
			else {
				System.out.println(exception.getMessage());

			}
			//exception.printStackTrace();
			parseInput();
		} finally {
			// Closing the socket
			try {
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}