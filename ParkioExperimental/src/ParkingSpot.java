
class ParkingSpot{
	private double latitude, longitude;
	private String description, days;
	private double r;	//Precomputed radius
	private int start, end;
	
	ParkingSpot(double latitude, double longitude, String description){ //Constructor
		this.latitude = latitude;      //Initialize private variables
		this.longitude = longitude;
		this.description = description;
		this.start = -1;
		this.end = -1;
		this.days = "All Week";
	}
	
	String serialize(){ //Serialize into JSON object
		 String result = "{"; 
		 result += "\"lat\":"+Double.toString(this.latitude)+",";
		 result += "\"lng\":"+Double.toString(this.longitude)+",";
		 result += "\"desc\":\""+this.description+"\"" + ",";
		 result += "\"start\":" + Integer.toString(this.start) +",";
		 result += "\"end\":" + Integer.toString(this.end) +",";
		 result += "\"days\":\"" + this.days +"\"";
		 result += "}";
		 
		 return result;
	}
	
	public String toString(){
		return this.serialize();
	}
	
	void precomputeDistance(double lat, double lng){
		r = this.distanceToPoint(lat, lng);
	}
	
	double getR(){ //Returns the precomputed R
		return r;
	}
	
	private double distanceToPoint(double lat, double lng){
		//Uses the Haversine formula
		int R = 6371000; // metres
		double omega1 = Math.toRadians(lat);
		double omega2 = Math.toRadians(this.latitude);
		double deltaOmega = Math.toRadians(this.latitude-lat);
		double deltaLambda = Math.toRadians(this.longitude-lng);

		double a = Math.sin(deltaOmega/2) * Math.sin(deltaOmega/2) + 
				Math.cos(omega1) * Math.cos(omega2)*
				Math.sin(deltaLambda/2) * Math.sin(deltaLambda/2);
		
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

		double d = R * c;
		return d;
	}
}