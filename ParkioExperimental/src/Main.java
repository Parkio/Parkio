import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
	static ParkingSpot[] getNearbySpots(double latitude, double longitude, int count){
		ParkingSpot[] result = new ParkingSpot[count];
		
		for (int i=0; i<count; i++){
			result[i] = new ParkingSpot(40.71+Math.random()/10, -74.00+Math.random()/10, "A Parking Spot");
		}
		
		return result;
	}
}
