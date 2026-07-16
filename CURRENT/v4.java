import java.net.URI; //represents web addresses
import java.net.http.HttpClient; //sends http requests
import java.net.http.HttpRequest; //represents a http request
import java.net.http.HttpResponse; //stores server response
import java.util.Scanner;
import java.util.Locale;

public class v4 {
	public static void main(String[] args) throws Exception {
		Scanner input = new Scanner(System.in);
		Scanner choice = new Scanner(System.in);
		Scanner ext = new Scanner(System.in);
		
		for (;;) {
			System.out.println("\n!! WORDS IN 'AMOUNT' WILL CRASH PROGRAM !!");
			
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
			
			System.out.printf("CURRENCY: ");
			String a = choice.next();
			String currency = a.toUpperCase(Locale.ROOT);
			
			//build the search pattern
			String targetKey = "\"" + currency + "\":";

			//find where the key starts
			int keyPos = json.indexOf(targetKey);

			if (keyPos != -1) {
				//start reading right after the colon
				int start = keyPos + targetKey.length();
    
				//find the next comma OR the closing bracket, whichever comes first
				int endVal = json.indexOf(",", start);
				int endBracket = json.indexOf("}", start);
    
				//use whichever marker is closer to the start point
				int end = (endVal != -1 && endVal < endBracket) ? endVal : endBracket;
    
				//extract and clean up the result
				String result = json.substring(start, end).trim();
				System.out.println("VALUE FOUND: " + result);
				
				String Text = json.substring(start, end); //extracts only the number
				double Rate = Double.parseDouble(Text); //converts text number into a double
			
				System.out.println("\n'0' TO EXIT");
				System.out.print("AMOUNT: $");
				double con = input.nextDouble(); //takes user input for USD
			
				if (con == 00) {
					break;
				}
				else {
					System.out.print("\n");
					double fin = con * Rate; //converts USD to YEN using the rate
					System.out.printf("$%.2f USD = " + currency + "%.2f%n", con, fin);
				}
			}
			else {
				System.out.println("KEY NOT FOUND: " + currency);
			}
		}
	}
}
