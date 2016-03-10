class Sort {

	static ParkingSpot[] sortByDistance(double lat, double lng, ParkingSpot[] spots){ //Sorts by distance to point
		//Precompute radii since Haversine formula is expensive
		for (int i=0; i<spots.length; i++){
			spots[i].precomputeDistance(lat, lng); 
		}
		quickSort(spots, 0, spots.length-1, lat, lng); //Quick sort array
		return spots;
	}

	static int partition(ParkingSpot[] arr, int left, int right, double lat, double lng){ //Standard quicksort parition
		int i = left, j = right; //Set pointers
		ParkingSpot tmp;
		int pivot = (left + right) / 2;

		while (j > i) {
			while (arr[i].getR() < arr[pivot].getR()) i++;			//Move left pointer right
			while (arr[j].getR() > arr[pivot].getR()) j--;		//Move right pointer left
			if (j > i) {
				tmp = arr[i]; arr[i] = arr[j]; arr[j] = tmp; //Swap elements
				i++; j--; //Move pointers
			}
		};

		return i;
	}

	static void quickSort(ParkingSpot[] arr, int left, int right, double lat, double lng) { //Optimized quicksort
		if (right - left < 9){ //When sublist is small, switch to insertion (9 or 10 are optimal cutoffs)
			insertionSort(arr, left, right);
		}else{
			int index = partition(arr, left, right, lat, lng); //Partition
			if (left < index - 1) quickSort(arr, left, index - 1, lat, lng); //Sort left
			if (index < right) quickSort(arr, index, right, lat, lng); //Sort right
		}
	}

	static void insertionSort(ParkingSpot[] arr, int left, int right){ //Basic insertion sort
		for (int x=left+1; x < right; x++){
			ParkingSpot temp = arr[x];
			int j = x-1;
			while (j >= 0 && temp.getR() < arr[j].getR()){
				arr[j + 1] = arr[j];
				j--;
			}
			arr[j + 1] = temp;
		}
	}
}
