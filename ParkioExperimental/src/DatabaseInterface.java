import java.util.ArrayList;

public class DatabaseInterface {
	public static ParkingSpot[] read(){
		ArrayList<ArrayList<String>> data = DatabaseReader.read(); //Read from the database
		ParkingSpot[] spots = new ParkingSpot[data.size()]; //Make an array of the correct size
		
		int i=0;
		for (ArrayList<String> row: data){ //Iterate across data rows
			//Create a parking spot from the latitude, longitude and description (should be availability times)
			spots[i] = new ParkingSpot(Double.parseDouble(row.get(0)), Double.parseDouble(row.get(1)), "Parking Spot");
			i++; //Increment index
		}
		
		return spots;
	}
}
