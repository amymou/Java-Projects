import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Locations {
	/**an array of all possible state abbreviations*/
	public static final String[] STATES = {"AK", "AL", "AR", "AZ",
		"CA", "CO", "CT", "DC", "DE", "FL",
		"GA", "HI", "IA", "ID",
		"IL", "IN", "KS", "KY", "LA", "MA",
		"MD", "ME", "MI", "MN",
		"MO", "MS", "MT", "NC", "ND", "NE",
		"NH", "NJ", "NM", "NV",
		"NY", "OH", "OK", "OR", "PA", "RI",
		"SC", "SD", "TN", "TX",
		"UT", "VA", "VT", "WA", "WI", "WV", "WY"};
	
	/**display the main menu for user
	 * */
	private static void displayMenu(){
		System.out.print("\nInformation about Locations in the U.S. - Please enter an option below.\n"
				+"D - Display statistics\n"
				+"L - List all locations in given state\n"
				+"S - Search for location\n"
				+"F - Find locations within given distance of a location\n"
				+"Q - Quit the program\n"
				+"Option:");
	}
	
	/**read all locations stored in the file
	 * */
	private static Location[]readLocations(String file){
		try {
			Scanner scanner=new Scanner(new File(file));
			scanner.nextLine();//skip the first line
			int recordNum=0;
			//read through the file and count the number of records
			while(scanner.hasNextLine()){
				++recordNum;
				scanner.nextLine();
			}
			scanner.close();
			
			//
			Location[]locations=new Location[recordNum];
			//begin a second read
			scanner=new Scanner(new File(file));
			scanner.useDelimiter("\t|\r|\n");//fields is seperated by '\t'
			scanner.nextLine();//skip the first line
			int k=0;
			while(scanner.hasNextLine()){//each line is a record
				String name=scanner.next().trim();//read name
				while(name.length()==0)
					if(!scanner.hasNext())
						break;
					else
						name=scanner.next().trim();
				if(!scanner.hasNext())
					break;
				//read state
				String state=scanner.next().trim();
				if(!scanner.hasNext())
					break;
				//read latitude
				String strlatitude=scanner.next().trim();
				double latitude=Double.parseDouble(strlatitude);
				if(!scanner.hasNext())
					break;
				//read longitude
				String strlongitude=scanner.next().trim();
				double longitude=Double.parseDouble(strlongitude);
				//add a new record
				locations[k++]=new Location(name, state, latitude, longitude);
			}
			scanner.close();
			return locations;
		} catch (FileNotFoundException e) {
			System.out.println("Unable to access input file: "+file);
			return null;
		}
	}
	
	/**display statistics like this:</br>
	 * &nbsp;&nbsp;Number of locations: &lt;number of locations></br>
	 * &nbsp;&nbsp;&lt;state> has the most locations with &lt;number of locations in state></br>
	 * &nbsp;&nbsp;&lt;state> has the fewest locations with &lt;number of locations in state></br>
	 * */
	public static void displayStatistics(Location[]locations){
		System.out.println("Number of locations:"+locations.length);
		int[]count=new int[STATES.length];
		for(Location l:locations){
			//try to find the index of state in the array STATE 
			int s=0,t=STATES.length;
			int m=-1;
			while(s<t){
				m=(s+t)/2;
				if(STATES[m].equals(l.getState()))
					break;
				else if(STATES[m].compareTo(l.getState())<0)
					s=m+1;
				else
					t=m;
			}
			if(s>=t){
				System.out.println("Invalid state: "+l.getState());
				break;
			}
			++count[m];//add a location for this state
		}
		int max=0,min=0;
		for(int k=1;k<count.length;++k)
			if(count[k]>count[max])
				max=k;
			else if(count[k]<count[min])
				min=k;
		System.out.println(STATES[max]+" has the most locations with "+count[max]);
		System.out.println(STATES[min]+" has the fewest locations with "+count[min]);
	}

	/**Prompt the user of a state (abbreviation) and list all locations in
	 * the state.If user input "RI", the result is like this:</br>
	 * <ul>
	 * <li>Ashaway, RI</li>
	 * <li>Barrington, RI</li>
	 * <li>Bradford, RI</li>
	 * <li>Bristol, RI</li>
	 * <li>Central Falls, RI</li>
	 * <li>...</li>
	 * </ul>
	 * @param console scanner for user to prompt
	 * @param locations all locations
	 * */
	public static void listLocationsInState(Scanner console,Location[]locations){
		System.out.print("State: ");
		String state="";
		do{
			state=console.nextLine();
		}while(state.length()==0);
		int num=0;
		for(Location l:locations)
			//find a location in the state
			if(state.equals(l.getState())){
				++num;
				System.out.println(l.getName()+","+l.getState());
			}
		if(num==0)
			System.out.println("Invalid state!");
	}

	/**
	 * Prompt the user for a string that contains the name or part of the name
	 * 	of a location (may contain space(s)) and ouputs each location name,	
	 * state,latitude, and longitude.
	 * If user input "le ro", the result is like this:</br>
	 * <ul>
	 * <li>Little Rock, AR Lat: 34.7224 Lon: -92.354076</li>
	 * <li>North Little Rock, AR Lat: 34.7884 Lon: -92.255437</li>
	 * <li>Castle Rock, CO Lat: 39.378872 Lon: -104.851305</li>
	 * <li>Le Roy, IA Lat: 40.87891 Lon: -93.592184</li>
	 * <li>Le Roy, IA Lat: 40.87891 Lon: -93.592184</li>
	 * <li>...</li>
	 * </ul>
	 * @param console scanner for user to prompt
	 * @param locations all locations
	 */
	public static void searchForLocation(Scanner console, Location[]locations) {
		System.out.print("Location (is/contains): ");
		String location="";
		do{
			location=console.nextLine();
		}while(location.length()==0);
		for(Location l:locations)
			//check whether the location contains the input
			if(l.getName().toLowerCase().contains(location.toLowerCase()))
				System.out.println(l.getName()+","+l.getState()+" Lat:"+
			l.getLatitude()+" Lon:"+l.getLongitude());
	}

	/**Prompt the user for a location name and state, as well as an integer
	 * 	distance Then output a list of locations (name/state/mileage) that 
	 * are within that distance of the given location .
	 * If user input is like this:</br>
	 * &nbsp;&nbsp;Name: Cary</br>
	 * &nbsp;&nbsp;State: NC</br>
	 * &nbsp;&nbsp;Distance: 10</br>
	 * The result is like this:</br>
	 * &nbsp;&nbsp;Locations within 10 miles from Cary, NC
	 * <ul>
	 * <li>Apex, NC: 4.75</li>
	 * <li>Holly Springs, NC: 9.09</li>
	 * <li>Morrisville, NC: 3.93</li>
	 * <li>Parkwood, NC: 9.52</li>
	 * <li>Raleigh, NC: 8.29</li>
	 * <li>...</li>
	 * </ul>
	 * @param console scanner for user to prompt
	 * @param locations all locations
	 * */
	public static void findLocations(Scanner console, Location[] locations){
		String name,state;
		int distance;
		System.out.print("Name: ");
		do{
			name=console.nextLine();
		}while(name.length()==0);
		System.out.print("State: ");
		do{
			state=console.nextLine();
		}while(state.length()==0);
		System.out.print("Distance: ");
		String dis;
		do{
			dis=console.nextLine();
		}while(dis.length()==0);
		try {
			distance=Integer.parseInt(dis);
			if(distance<1){
				System.out.println("Invalid distance!");
				return;
			}
		} catch (NumberFormatException e) {
			System.out.println("Invalid distance!");
			return;
		}
		//
		Location loc=null;//the specified location
		//find the specified location
		for(Location l:locations)
			if(state.equals(l.getState())&&name.equals(l.getName())){
				loc=l;
				break;
			}
		if(loc==null){
			System.out.println("Invalid location!");
			return;
		}
		System.out.printf("Locations within %d miles from %s, %s\n",
				distance,name,state);
		//find the matched locations within the distance
		for(Location l:locations)
			if(l==loc||distance<l.getDistance(loc))
				continue;
			else{
				System.out.printf("\t%s,%s:%.2f\n",
					l.getName(),l.getState(),l.getDistance(loc));
			}
	}

	public static void main(String[] args) {
		if(args.length==0){
			System.out.println("Please prove a file!!!");
			System.exit(0);
		}
		//read data from file
		Location[]locations=readLocations(args[0]);
		if(locations==null)
			return;
		Scanner scanner=new Scanner(System.in);
		boolean stop=false;
		while(!stop){
			displayMenu();
			String choice=scanner.nextLine();
			if(choice.length()!=1){
				System.out.println("Invalid option!");
				continue;
			}
			switch(choice.charAt(0)){
			case 'd':
			case 'D':
				displayStatistics(locations);
				break;
			case 'l':
			case 'L':
				listLocationsInState(scanner, locations);
				break;
			case 's':
			case 'S':
				searchForLocation(scanner, locations);
				break;
			case 'f':
			case 'F':
				findLocations(scanner, locations);
				break;
			case 'q':
			case 'Q':
				stop=true;
				System.out.println("Goodbye!");
				break;
			default:
				System.out.println("Invalid option!");
			}
		}
	}

}