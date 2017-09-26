package developerTest;

/**
 * This class fetches the URL response.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FetchUrl {

	public static void main(String[] args) throws IOException {
		ThreadPool pool = new ThreadPool(20);
		FetchUrl f = new FetchUrl();
		ArrayList<String> onlyUrls = f.readInput("https://s3.amazonaws.com/fieldlens-public/urls.txt");
		for (int i = 0; i < onlyUrls.size(); i++) {
			Task task = new Task(i, onlyUrls.get(i), "facebook");
			pool.execute(task);
		}

	}

	public ArrayList<String> readInput(String data) throws IOException {

		URL input = new URL(data);
		BufferedReader in = new BufferedReader(new InputStreamReader(input.openStream()));
		ArrayList<String> links = new ArrayList<String>();
		in.readLine();
		String line = null;
		while ((line = in.readLine()) != null) {
			String answer = fetchUrlName(line);
			links.add(answer);

		}
		in.close();
		return links;
	}

	public String fetchUrlName(String x) throws IOException {

		String answer = null;
		Pattern p = Pattern.compile("\"([^\"]*)\"");
		Matcher m = p.matcher(x);
		while (m.find()) {
			answer = m.group(1);
			answer = answer.substring(0, answer.length() - 1);
		}
		return answer;
	}

}
