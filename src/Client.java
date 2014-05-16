import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
	
	private static Socket socket;
	private static String hostName = "localhost";
	private static int portNum = 4444;
	
	private static Scanner getInfo = new Scanner(System.in);
	
	
	public static void sendBytes(byte[] myByteArray) throws IOException {
	    sendBytes(myByteArray, 0, myByteArray.length);
	}
	
	public static void sendBytes(byte[] myByteArray, int start, int len) throws IOException {
	    if (len < 0)
	        throw new IllegalArgumentException("Negative length not allowed");
	    
	    if (start < 0 || start >= myByteArray.length)
	        throw new IndexOutOfBoundsException("Out of bounds: " + start);
	 
	    OutputStream out = socket.getOutputStream(); 
	    DataOutputStream dos = new DataOutputStream(out);

	    dos.writeInt(len);
	    if (len > 0) {
	        dos.write(myByteArray, start, len);
	    }
	}
	
	public static byte[] readBytes() throws IOException {
		 
	    InputStream in = socket.getInputStream();
	    DataInputStream dis = new DataInputStream(in);

	    int len = dis.readInt();
	    byte[] data = new byte[len];
	    if (len > 0) {
	        dis.readFully(data);
	    }
	    return data;
	}
	
	
	public static void main(String[] args){		
		
		System.out.println("Enter username: ");
		String name = getInfo.nextLine();
		String text = "";
	    
		
		try{
			socket = new Socket(hostName,portNum);
			
			byte[] sendData = new byte[name.length()];
			sendData=name.getBytes();						// to change from byte to string value String convert = new String(sendData);
			sendBytes(sendData);		
			sendData=null;
			
			while(true){
				
				text = getInfo.nextLine();
				sendData = new byte[text.length()];
				sendData=text.getBytes();	
				sendBytes(sendData);
				String read = new String(readBytes());
				System.out.println(read);
				if(text.equals("bye")) break;
				
			}
			
		
		}
		catch(Exception e){			
			System.out.println("Error: " + e.getMessage());
		} 
		finally {
			System.out.println("Connection to server closed.");
		}
		
		
	}
	

}
