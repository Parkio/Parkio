public class Main {
	
	static void initialize(){
		DatabaseInterface.load();
	}
	
	//Returns the 'count' # of ParkingSpots closest to the given point.
	static ParkingSpot[] getNearbySpots(double lat, double lng, int count){ //Get nearest spots
		ParkingSpot[] response = DatabaseInterface.get();
		response = Sort.sortByDistance(lat, lng, response);
		response = takeFirstN(response, 40);
		return response;
	}
	
	//Takes first N items from an array
	static ParkingSpot[] takeFirstN(ParkingSpot[] spots, int N){
		ParkingSpot[] newSpots = new ParkingSpot[N];
		for (int i=0; i<N && i<spots.length; i++){
			newSpots[i] = spots[i];
		}
		return newSpots;
	}
}
