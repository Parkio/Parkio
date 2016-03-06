//https://docs.oracle.com/javase/8/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/package-summary.html

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

//To fix access restriction errors...
//Project properties -> Java Compiler -> Errors/Warnings -> Deprecated and restricted API --> Forbidden Reference --> Warning
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class NetworkIO {
	private static String resources = "";

    public static void main(String[] args) throws Exception {
    	packResources(); //Load and pack resources
    	
        HttpServer server = HttpServer.create(new InetSocketAddress(8001), 0); //Create a new HTTP server
        server.createContext("/", new MyHandler()); //Create a new context
        server.setExecutor(null); 					//Create a default executor
        server.start(); 							//Start the server
        
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
        	String response;
        	if (t.getRequestHeaders().keySet().contains("Location")){
        		String[] coords = t.getRequestHeaders().getFirst("Location").split(",");
        		response = Parser.serialize(Main.getNearbySpots(Double.parseDouble(coords[0]), Double.parseDouble(coords[1]), 5)).toString();		//Get response
        	}else{
        		response = resources;
        	}
            
            t.sendResponseHeaders(200, response.length()); 	//Send OK header
            OutputStream os = t.getResponseBody();			//Get the response
            
            os.write(response.getBytes());					//Send response
            os.close();	//Close the exchange
        }
    }
    
    static void packResources(){
    	String result = readTextFile("Resources/template.html");
    	result = result.replace("<<!!STYLE!!>>", readTextFile("Resources/style.css"));
    	result = result.replace("<<!!SCRIPT!!>>", readTextFile("Resources/script.js"));
    	resources = result;
    }
    
    static String readTextFile(String path){
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