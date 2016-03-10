class Sort {
	
	static ParkingSpot[] sortByDistance(double lat, double lng, ParkingSpot[] spots){
		quickSort(spots, 0, spots.length-1, lat, lng);
		return spots;
	}
	
	//NOT OPTIMIZED WILL NOT DO FOR FULL DATASET
	static int partition(ParkingSpot[] arr, int left, int right, double lat, double lng)
	{
	      int i = left, j = right;
	      ParkingSpot tmp;
	      int pivot = (left + right) / 2;
	     
	      while (i <= j) {
	            while (arr[i].distanceToPoint(lat, lng) < arr[pivot].distanceToPoint(lat, lng))
	                  i++;
	            while (arr[j].distanceToPoint(lat, lng) > arr[pivot].distanceToPoint(lat, lng))
	                  j--;
	            if (i <= j) {
	                  tmp = arr[i];
	                  arr[i] = arr[j];
	                  arr[j] = tmp;
	                  i++;
	                  j--;
	            }
	      };
	     
	      return i;
	}
	 
	static void quickSort(ParkingSpot[] arr, int left, int right, double lat, double lng) {
	      int index = partition(arr, left, right, lat, lng);
	      if (left < index - 1)
	            quickSort(arr, left, index - 1, lat, lng);
	      if (index < right)
	            quickSort(arr, index, right, lat, lng);
	}
}
