package Srv;
import java.net.*;
import java.io.*;
public class ServerApplication {
	 static ServerSocket socket1;
	 static Socket connection;
	 static boolean first;
	 static StringBuffer process;
	 static String TimeStamp;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
		      socket1 = new ServerSocket(Integer.parseInt(args[0]));
		      int character;

		      while (true) {
		          connection = socket1.accept();
		          BufferedInputStream is = new BufferedInputStream(connection.getInputStream());
		          InputStreamReader isr = new InputStreamReader(is);
		          process = new StringBuffer();
		          while((character = isr.read()) != 13) {
		            process.append((char)character);
		          }
		          String proc=process.toString();
		          int i = proc.indexOf(10);
		  		  String cmd = proc.substring(0,i);
		  		  String waitForRespond = proc.substring(i+1,proc.length());
		  		  System.out.println("Cmd: "+ cmd + "; " + " Wait for respond: " + waitForRespond);
		  		  boolean wait = Boolean.parseBoolean(waitForRespond);
		          /*try{
		        	  cmd = CMDReader.get_commandline_results(cmd,wait);  
		          }catch (InterruptedException e) {}
		          */
		          BufferedOutputStream os = new BufferedOutputStream(connection.getOutputStream());
		          OutputStreamWriter osw = new OutputStreamWriter(os, "US-ASCII");
		          osw.write(cmd + "Succeced" +  (char) 13);
		          osw.flush();
		       }
		      }
		      catch (IOException e) {}
		        try {
		          connection.close();
		        }
		        catch (IOException e) {}
		    }
		  }
