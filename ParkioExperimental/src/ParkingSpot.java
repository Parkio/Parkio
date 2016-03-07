
public class ParkingSpot {
	private double latitude, longitude;
	private String description;
	//Need availability etc...
	
	ParkingSpot(double latitude, double longitude, String description){ //Constructor
		this.latitude = latitude;      //Initialize private variables
		this.longitude = longitude;
		this.description = description;
	}
	
	String serialize(){ //Serialize into JSON object
		 String result = "{"; 
		 result += "\"lat\":"+Double.toString(this.latitude)+",";
		 result += "\"lng\":"+Double.toString(this.longitude)+",";
		 result += "\"desc\":\""+this.description+"\"";
		 result += "}";
		 
		 return result;
	}
}
