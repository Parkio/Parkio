import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DatabaseReader {
	public static ArrayList<ArrayList<String>> read(){ //Need to decide on a return type
		
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>(); //Store csv data in a 2d array
		BufferedReader reader = null; //Use a buffered reader to read
		
		try {
			String line;
			reader = new BufferedReader(new FileReader("data/subset.csv")); //Open the file and instantiate the reader
			
			while ((line = reader.readLine()) != null) { //Iterate across rows
				result.add(parseCSVRow(line));	//Add the parsed row
			}
		} catch (IOException e) { //Exception when reading
			e.printStackTrace();
		} finally {
			try {
				if (reader != null){ 
					reader.close(); //Close reader when finished
				}
			} catch (IOException e) { //Exception when closing reader
				e.printStackTrace();
			}
		}
		return result;
	}
		
	private static ArrayList<String> parseCSVRow(String csvRow) {
		ArrayList<String> result = new ArrayList<String>(); //Store columns in arrayList
		
			String[] splitRow = csvRow.split("\\s*,\\s*");	//Split the row via regex (kleene whitespace comma kleene whitespace)
			for (int i = 0; i < splitRow.length; i++) {		//Iterate across columns
				result.add(splitRow[i].trim());				//Add column to row
			}
		
		return result;
	}
}
