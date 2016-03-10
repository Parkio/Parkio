
class Parser {
	static String serialize(ParkingSpot[] spots){ //JSON serialize an array of ParkingSpot objects
		String result = "{ \"spots\":["; //Create object with array named "spots"
			
		for (int i=0; i<spots.length; i++){ //Iterate across array
			result += spots[i].serialize(); //Serialize individual objects
			if (i != spots.length-1){ //No comma at end of array
				result+= ",";
			}
		}
		result+="]}"; //Close array and object
		
		return result;
	}
	
	
}
