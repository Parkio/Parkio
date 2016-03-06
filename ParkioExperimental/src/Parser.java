
public class Parser {
	static String serialize(ParkingSpot[] spots){
		String result = "{ \"spots\":[";
			
		for (int i=0; i<spots.length; i++){
			result += spots[i].serialize();
			if (i != spots.length-1){
				result+= ",";
			}
		}
		result+="]}";
		
		return result;
	}
	
	
}
