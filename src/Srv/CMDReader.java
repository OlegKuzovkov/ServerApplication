package Srv;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CMDReader {
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		CMDReader c = new CMDReader();
		List<String> list = new ArrayList<String>();
		Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(args[0]);
		while (m.find())
		    list.add(m.group(1)); // Add .replace("\"", "") to remove surrounding quotes.


		System.out.println(list);
		String[] stockArr = new String[list.size()];
		stockArr = list.toArray(stockArr);
		String[] arr={"cmd","/c","start","\"\"","D:\\Oleg Kuzovkov\\Andrew Kirshe.pdf"};
		//String cmd=args[0];//"start \"\" \"D:\Oleg Kuzovkov\Andrew Kirshe.pdf\"";
		try{
			String s = c.get_commandline_results(arr, true);
			System.out.println(s);
		}catch(IOException e) {}
	}

    public static String get_commandline_results(String[] cmd, boolean waitForRespond)
        throws IOException,InterruptedException{
        //Do not remove the authorizedCommand method.  Be sure to 
        //catch bad things coming through cmd like "rm -rf / myfile" 
        //which erases your box no questions asked.  Be prepared 
        //to live in the house of pain if you have not whitelisted 
        //and blacklisted badness that comes in through cmd.
        String result = "";
        Runtime rt = Runtime.getRuntime();
        ProcessBuilder pb = new ProcessBuilder(cmd);
        Process p = pb.start();
        //final Process p = Runtime.getRuntime().exec(cmd); //String.format("cmd /c %s",)
        if (waitForRespond){
        	final ProcessResultReader stderr = new ProcessResultReader(p.getErrorStream(), "STDERR");
        	final ProcessResultReader stdout = new ProcessResultReader(p.getInputStream(), "STDOUT");
        	stderr.start();
        	stdout.start();
        	final int exitValue = p.waitFor();
        	if (exitValue == 0){
        		result = stdout.toString();
        	}
        	else{
        		result = stderr.toString();
        	}
        }
        return result;
    }
}

class ProcessResultReader extends Thread{
    final InputStream is;
    final String type;
    final StringBuilder sb;

    ProcessResultReader(final InputStream is, String type){
        this.is = is;
        this.type = type;
        this.sb = new StringBuilder();
    }

    public void run()
    {
        try{
            final InputStreamReader isr = new InputStreamReader(is);
            final BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null)
            {
                this.sb.append(line).append("\n");
            }
        }
        catch (final IOException ioe)
        {
            System.err.println(ioe.getMessage());
            throw new RuntimeException(ioe);
        }
    }
    @Override
    public String toString()
    {
        return this.sb.toString();
    }
}
