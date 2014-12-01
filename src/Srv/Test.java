package Srv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
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
		//String cmd=args[0];//"start \"\" \"D:\Oleg Kuzovkov\Andrew Kirshe.pdf\"";
		try{
			String s = c.get_commandline_results(stockArr, false);
		}catch(IOException e) {}
	}

}
