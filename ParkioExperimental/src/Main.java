public class Main {
	
	public static void main(String[] args) throws Exception{
		DatabaseInterface.load();
		NetworkIO.runServer();
	}
	
	//Returns the 'count' # of ParkingSpots closest to the given point.
	static ParkingSpot[] getNearbySpots(double latitude, double longitude, int count){ //Get nearest spots
		return takeFirstN(Sort.sortByDistance(latitude, longitude, DatabaseInterface.get()),100);
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
