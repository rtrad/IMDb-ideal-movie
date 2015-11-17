import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class ConvertSpecialChars {
	public static void main(String[] args) throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		BufferedReader read = new BufferedReader(new FileReader(new File("hex_to_char.txt")));
		String line;
		while((line = read.readLine()) != null) {
			StringTokenizer tok = new StringTokenizer(line);
			map.put(tok.nextToken(), tok.nextToken());
		}
		read.close();
		
		read = new BufferedReader(new FileReader(new File("moviedb.txt")));
		PrintWriter out = new PrintWriter(new File("fixed_output.txt"));
		while((line = read.readLine()) != null) {
			for(String s : map.keySet()) {
				if(line.indexOf(s) >= 0) {
					System.out.println(line + " " + s + " " + map.get(s));
					line = line.replaceAll(s, map.get(s));
					System.out.println(line + " " + s + " " + map.get(s));
				}
			}
			
			out.println(line);
		}
		read.close();
		out.close();
	}
}