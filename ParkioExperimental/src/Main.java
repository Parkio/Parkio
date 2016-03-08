public class Main {
	static ParkingSpot[] getNearbySpots(double latitude, double longitude, int count){ //Get nearest spots
		ParkingSpot[] result;
		
		//PLACEHOLDER
		result = new ParkingSpot[count];
		for (int i=0; i<count; i++){
			result[i] = new ParkingSpot(40.71+Math.random()/10, -74.00+Math.random()/10, "A Parking Spot");
		}
		//END PLACEHOLDER
		
		//return takeFirstN(sortByDistance(latitude, longitude, DatabaseInterface.read()),30);
		
		return result;
	}
	
	static ParkingSpot[] takeFirstN(ParkingSpot[] spots, int N){
		ParkingSpot[] newSpots = new ParkingSpot[N];
		for (int i=0; i<N; i++){
			newSpots[i] = spots[i];
		}
		return newSpots;
	}
}
