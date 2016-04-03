import java.util.ArrayList;

class DatabaseInterface {
	private static ParkingSpot[] spots;
	
	static void load(){
		ArrayList<ArrayList<String>> data = DatabaseReader.read(); //Read from the database
		spots = new ParkingSpot[data.size()]; //Make an array of the correct size
		
		int i=0;
		for (ArrayList<String> row: data){ //Iterate across data rows
			//Create a parking spot from the latitude, longitude and description (should be availability times)
			double lat = Double.parseDouble(row.get(0));
			double lng = Double.parseDouble(row.get(1));
			double start = Double.parseDouble(row.get(2));
			double end = Double.parseDouble(row.get(3));
			double hours = Double.parseDouble(row.get(4));
			String desc = doubleToHour(start)+"-"+doubleToHour(end);

			spots[i] = new ParkingSpot(lat, lng, desc, start, end, hours);
			i++; //Increment index
		}
	}

	private static String doubleToHour(double doub){
		String desc = "";
		int d;
		if (doub % 1 == 0){
			d = (int)doub;
			if (d < 1){
				desc+=(d+12)+"am";
			}else if (d < 12){
				desc+=(d)+"am";
			}else if (d < 13){
				desc+=(d)+"pm";
			}else{
				desc+=(d-12)+"pm";
			}
		}else{
			d = (int)doub;
			if (d < 1){
				desc+=(d+12)+":30am";
			}else if (d < 12){
				desc+=(d)+":30am";
			}else if (d < 13){
				desc+=(d)+":30pm";
			}else{
				desc+=(d-12)+":30pm";
			}
		}
		return desc;

	}

	static ParkingSpot[] get(){
		return spots;
	}
}
