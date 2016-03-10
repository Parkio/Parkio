import java.util.Arrays;

class Sort {
	static ParkingSpot[] sortByDistance(double lat, double lng, ParkingSpot[] spots){
		ParkingSpot[] sortedSpots = spots;
		Arrays.sort(sortedSpots);
		return sortedSpots;
	}
}
