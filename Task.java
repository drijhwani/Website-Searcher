package developerTest;

/**
 * This is a single unit of execution.
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task implements Runnable {

	private int num;
	private String newurl;
	private String searchTerm;

	public Task(int n, String newurl, String searchTerm) {
		num = n;
		this.newurl = newurl;
		this.searchTerm = searchTerm;
	}

	public void run() {
		try {
			readContents(newurl, searchTerm);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Task " + num + " is running.");
		System.out.println("The url is: " + newurl);
	}

	public void readContents(String newurl, String searchTerm) throws IOException {

		// Make a URL to the web page
		URL url = new URL("https://www." + newurl);

		// Get the input stream through URL Connection
		URLConnection con = url.openConnection();
		InputStream is = con.getInputStream();

		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		String line = null;

		// read each line and write to file
		while ((line = br.readLine()) != null) {
			if (line.contains(searchTerm)) {
				try (Writer writer = new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream("results.txt", true), "utf-8"))) {
					writer.write(line + "\n");
				}

			}
		}
		br.close();
	}

}