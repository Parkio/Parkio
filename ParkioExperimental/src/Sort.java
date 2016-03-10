import java.util.Arrays;
import java.util.Comparator;

class Sort {
	static ParkingSpot[] sortByDistance(double lat, double lng, ParkingSpot[] spots){
		ParkingSpot[] sortedSpots = spots;
		
		//DELETE THIS AND USE REAL SORTING
		class RadiusComparator implements Comparator<ParkingSpot>{
			private double lat, lng;
			public RadiusComparator(double lat, double lng){
				this.lat=lat;
				this.lng=lng;
			}
			
			@Override
			public int compare(ParkingSpot o1, ParkingSpot o2) {
				return (int)(o1.distanceToPoint(lat,lng) - o2.distanceToPoint(lat,lng));
			}
		}
		Arrays.sort(sortedSpots, new RadiusComparator(lat,lng));
		//END OF DELETE THIS
		
		return sortedSpots;
	}
}
