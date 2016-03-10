public class Main {
	
	static void initialize(){
		DatabaseInterface.load();
	}
	
	//Returns the 'count' # of ParkingSpots closest to the given point.
	static ParkingSpot[] getNearbySpots(double latitude, double longitude, int count){ //Get nearest spots
		return takeFirstN(DatabaseInterface.get(),100);
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
