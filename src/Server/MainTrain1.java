package Server;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class MainTrain1 {
	
	public static class ClientHandler1 implements ClientHandler{
		PrintWriter out;
		Scanner in;

		@Override
		public void handleClient(InputStream inFromclient, OutputStream outToClient) {
			out=new PrintWriter(outToClient);
			in=new Scanner(inFromclient);
			String text = in.next();
			out.println(new StringBuilder(text).reverse().toString());
			out.flush();
		}

		@Override
		public void close() {
			in.close();
			out.close();
		}
		
	}

	public static void client1(int port) throws Exception{
		Socket server=new Socket("localhost", port);		
		Random r=new Random();
		String text = ""+(1000+r.nextInt(100000));
		String rev=new StringBuilder(text).reverse().toString();
		PrintWriter outToServer=new PrintWriter(server.getOutputStream());
		Scanner in=new Scanner(server.getInputStream());
		outToServer.println(text);
		outToServer.flush();
		String response=in.next();
		if(response==null || !response.equals(rev)) 
			System.out.println("problem getting the right response from your server, cannot continue the test (-100)");
		in.close();
		outToServer.println(text);
		outToServer.close();
		server.close();
	}
	
	public static boolean testServer() {
		boolean ok=true;
		Random r=new Random();
		int port=6000+r.nextInt(1000);
		MyServer s=new MyServer(port, new ClientHandler1());
		int c = Thread.activeCount();
		s.start(); // runs in the background
		if(Thread.activeCount() != c+1)
			System.out.println("new thread didn't open (-10)");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		try {
			client1(port);
			client1(port);
		}catch(Exception e) {
			System.out.println("some exception was thrown while testing your server, cannot continue the test (-100)");			
			ok=false;
		}
		s.close();
		try {Thread.sleep(2000);} catch (InterruptedException e) {}

		if (Thread.activeCount()!=c) {
			System.out.println("you have a thread open after calling close method (-100)");
			ok=false;
		}
		return ok;
	}

	public static String[] writeFile(String name) {
		Random r=new Random();
		String txt[]=new String[10];
		for(int i=0;i<txt.length;i++) 
			txt[i]=""+(10000+r.nextInt(10000));
		
		try {
			PrintWriter out=new PrintWriter(new FileWriter(name));
			for(String s : txt) {
				out.print(s+" ");
			}
			out.println();
			out.close();
		}catch(Exception e) {}
		
		return txt;
	}
	
	public static void testDM() {
		String file1[]=writeFile("file1.txt");
		String file2[]=writeFile("file2.txt");
		String file3[]=writeFile("file3.txt");
		
		DictionaryManager dm=DictionaryManager.get();
		if(dm.getSize()!=0)
			System.out.println("wrong size for the Dictionary Manager");

		if(!dm.query("file1.txt", "file2.txt",file2[4]))
			System.out.println("problem for Dicfileionary Manager query (-5)");
		if(!dm.query("file1.txt", "file2.txt",file1[9]))
			System.out.println("problem for Dicfileionary Manager query (-5)");
		if(dm.query("file1.txt", "file3.txt","2"+file3[2]))
			System.out.println("problem for Dicfileionary Manager query (-5)");
		if(dm.query("file2.txt", "file3.txt","3"+file2[5]))
			System.out.println("problem for Dicfileionary Manager query (-5)");
		if(!dm.challenge("file1.txt", "file2.txt", "file3.txt",file3[2]))
			System.out.println("problem for Dicfileionary Manager challenge (-5)");
		if(dm.challenge("file2.txt", "file3.txt", "file1.txt","3"+file2[5]))
			System.out.println("problem for Dicfileionary Manager challenge (-5)");

		if(dm.getSize()!=3)
			System.out.println("wrong size for filehe Dicfileionary Manager (-10)");
		
	}
	
	public static void runClient(int port,String query,boolean result) {
		try {
			Socket server=new Socket("localhost",port);
			PrintWriter out=new PrintWriter(server.getOutputStream());
			Scanner in=new Scanner(server.getInputStream());
			out.println(query);
			out.flush();
			String res=in.next();
			if((result && !res.equals("true")) || (!result && !res.equals("false")))
				System.out.println("problem getting the right answer from the server (-10)");
			in.close();
			out.close();
			server.close();
		} catch (IOException e) {
			System.out.println("your code ran into an IOException (-10)");
		}
	}
	
	public static void testBSCH() {
		String s1[]=writeFile("s1.txt");
		String s2[]=writeFile("s2.txt");
		
		Random r=new Random();
		int port=6000+r.nextInt(1000);
		MyServer s=new MyServer(port, new BookScrabbleHandler());
		s.start();
		runClient(port, "Q,s1.txt,s2.txt,"+s1[1], true);
		runClient(port, "Q,s1.txt,s2.txt,"+s2[4], true);
		runClient(port, "Q,s1.txt,s2.txt,2"+s1[1], false);
		runClient(port, "Q,s1.txt,s2.txt,3"+s2[4], false);
		runClient(port, "C,s1.txt,s2.txt,"+s1[9], true);
		runClient(port, "C,s1.txt,s2.txt,#"+s2[1], false);
		s.close();
	}

	public static void main(String[] args) {
		if(testServer()) {
			System.out.println("testing dictionary manager");
			testDM();
			//testBSCH();
		}
		System.out.println("done");
	}

}
