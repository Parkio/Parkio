//https://docs.oracle.com/javase/8/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/package-summary.html

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

@SuppressWarnings("restriction")
class NetworkIO {
	private static String resources = "";
	
	public static void main(String[] args) throws Exception{
    	packResources(); //Load and pack resources
    	Core.initialize();
    	
    	int port = 8081; //Set the port here
    	
        HttpServer server = HttpServer.create(new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(), port), 0); //Create a new HTTP server
        server.createContext("/", new MyHandler()); //Create a new context
        server.setExecutor(null); 					//Create a default executor
        
        System.out.println("Server online on "+InetAddress.getLocalHost().getHostAddress()+":"+port); //Print start message to console
        server.start(); 							//Start the server
        
    }

    private static class MyHandler implements HttpHandler { //Custom handler class
        @Override
        public void handle(HttpExchange t) throws IOException { //Method to handle HTTP exchanges
        	long t1 = System.nanoTime(); //Start timer
        	String response; 
        	
        	if (t.getRequestHeaders().keySet().contains("Location")){ //If the Location header is in the request, return ParkingSpots
        		String[] coords = t.getRequestHeaders().getFirst("Location").split(","); //Get the user's location from the header
        		
        		System.out.println(t.getRemoteAddress()+" requested latitude "+coords[0]+" longitude "+coords[1]); //Console log
        		
        		//			Json Serialize	  Get Nearby Parking Spots		Parse latitude and longitude doubles
        		response = Parser.serialize(Core.getNearbySpots(Double.parseDouble(coords[0]), Double.parseDouble(coords[1]), 25));		
        	}else{ //Otherwise just give the page resources
        		
        		System.out.println(t.getRemoteAddress()+" loaded page.");
        		response = resources;
        	}
        	
            t.sendResponseHeaders(200, response.length()); 	//Send OK header
            OutputStream os = t.getResponseBody();			//Get the response
            
            os.write(response.getBytes());					//Send response
            os.close();	//Close the exchange
            
            double dt = (double)(System.nanoTime() - t1)/ 1000000000.0; //End timer
            System.out.println("Request filled in "+ dt+ " seconds."); //Console log
        }
    }
    
    private static void packResources(){
    	System.out.println("Packed resources.");
    	String result = readTextFile("Resources/template.html"); //Get the template
    	result = result.replace("<<!!STYLE!!>>", readTextFile("Resources/style.css")); //Add the stylesheet
    	result = result.replace("<<!!SCRIPT!!>>", readTextFile("Resources/script.js"));//Add the script
    	resources = result;
    }
    
    private static String readTextFile(String path){ //Method for reading text files
    	String content ="";
    	try(BufferedReader br = new BufferedReader(new FileReader(path))) {
    	    StringBuilder sb = new StringBuilder();
    	    String line = "";
			try {
				line = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

    	    while (line != null) {
    	        sb.append(line);
    	        sb.append(System.lineSeparator());
    	        line = br.readLine();
    	    }
    	    
    	    content = sb.toString();
    	} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return content;	
    }
}