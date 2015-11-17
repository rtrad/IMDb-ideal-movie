import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

public class Movies {
	public static int counter;
	public static PrintWriter out;
	
	public static void main(String[] args) throws IOException {
		counter = 1;
		out = new PrintWriter(new File("moviedb.txt"));
		for(int i = 1900; i <= 2015; i++) {
			counter = 1;
			System.out.println("Starting " + i + ":");
			run("http://www.imdb.com/search/title?at=0&sort=alpha&start=1&title_type=feature&year=" + i + "," + i);
		}
		out.close();
	}
	
	public static void run(String sURL) throws IOException {
		URL page = new URL(sURL);
		URLConnection connect = page.openConnection();
		BufferedReader read = new BufferedReader(new InputStreamReader(connect.getInputStream()));
		String inputLine;
        
        loop:for (int i = 1; i<=51; i++){
        	while ((inputLine = read.readLine()) != null && !inputLine.trim().equals("<td class=\"number\">"+counter+".</td>")) {
        		//out.println(inputLine);
        		if(inputLine.trim().equals("</table>"))
        			break loop;
            }

	        read.readLine();
	        String info = read.readLine();
	        StringBuilder link = new StringBuilder("http://imdb.com");
	        String title = "";
	        int index = info.indexOf("\"");
	        int index2 = info.indexOf("title=");
	        link.append(info.substring(index + 1, index2 - 2));
	        info = info.substring(index2);
	        index = info.indexOf("\"");
	        index2 = info.indexOf("><img");
	        title = info.substring(index + 1, index2 - 1);
	        out.println(link + "|" + title);
	        counter++;
        }
		
		out.flush();
		
		String nextURL;
		while(!(nextURL = read.readLine()).startsWith("<a href")) {
			//On to the next one
		}
		
		//The first link is to the previous page
		if(nextURL.indexOf(">Next") < 0)
			nextURL = read.readLine();
		
		//We're on the last page
		if(nextURL.indexOf(">Next") < 0)
			return;
		
		int index = nextURL.indexOf("\"");
        int index2 = nextURL.indexOf(">Next");
        System.out.println(counter);
        read.close();
        
        run("http://imdb.com" + nextURL.substring(index + 1, index2 - 1));
	}
}