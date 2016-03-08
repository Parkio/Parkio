public class Main {
	
	//Returns the 'count' # of ParkingSpots closest to the given point.
	static ParkingSpot[] getNearbySpots(double latitude, double longitude, int count){ //Get nearest spots
		ParkingSpot[] result;
		
		//PLACEHOLDER
		result = new ParkingSpot[count];
		for (int i=0; i<count; i++){
			result[i] = new ParkingSpot(latitude+Math.random()/50-Math.random()/50, longitude+Math.random()/50-Math.random()/50, "A Parking Spot");
		}
		//END PLACEHOLDER
		
		//return takeFirstN(sortByDistance(latitude, longitude, DatabaseInterface.read()),30);
		
		return result;
	}
	
	//Takes first N items from an array
	static ParkingSpot[] takeFirstN(ParkingSpot[] spots, int N){
		ParkingSpot[] newSpots = new ParkingSpot[N];
		for (int i=0; i<N; i++){
			newSpots[i] = spots[i];
		}
		return newSpots;
	}
}
