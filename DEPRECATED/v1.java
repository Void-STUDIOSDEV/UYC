import java.net.URI; //represents web addresses
import java.net.http.HttpClient; //sends http requests
import java.net.http.HttpRequest; //represents a http request
import java.net.http.HttpResponse; //stores server response

public class v1 {
	public static void main(String[] args) throws Exception {
		HttpClient client = HttpClient.newHttpClient(); //this handles connection to API server (JAVA already handles encryption)
		HttpRequest request = HttpRequest.newBuilder() //create a request to the API
			.uri(URI.create(
				"https://api.exchangerate.fun/latest?base=USD"
			))
			.GET() //fetches the data
			.build();
			
			
		HttpResponse<String> response = client.send( //converts the data to a string
			request, //sends the request
			HttpResponse.BodyHandlers.ofString() //tells JAVA to convert directly
		);
		
		
		String json = response.body(); //store the entire JSON response
		
		
		int start = json.indexOf("\"JPY\":") + 6; //searches for "JYP"
		int end = json.indexOf(",", start); //finds the end of the JYP
		
		
		String yenText = json.substring(start, end); //extracts only the number
		double yenRate = Double.parseDouble(yenText); //converts text number into a double
		
		
		System.out.printf("1 USD = ¥%.2f%n", yenRate);
	}
}
