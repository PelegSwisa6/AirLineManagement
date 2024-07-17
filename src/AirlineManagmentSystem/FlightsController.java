package AirlineManagmentSystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;


public class FlightsController {

	private static DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd::HH:mm:ss");

	public static void AddNewFlight(Database database, Scanner s) throws SQLException {
		int planeID = AirplanesController.selectAirplanesID(database, s);
		Airplane plane = AirplanesController.getAirplaneById(database, planeID);

		System.out.println("Enter origin airport id (int): \n(-1 to show all airports");
		int originID = s.nextInt();
		if(originID == -1) {
			AirportsController.printAllAirports(database);
			System.out.println("Enter origin airport id (int): ");
			originID = s.nextInt();
		}
		Airport originAirport = AirportsController.getAirportById(database, originID);

		System.out.println("Enter destination airport id (int): \n(-1 to show all airports");
		int destinationID = s.nextInt();
		if (destinationID == -1) {
			AirportsController.printAllAirports(database);
			System.out.println("Enter destination airport id (int): ");
			destinationID = s.nextInt();
		}
		Airport destinationAirport = AirportsController.getAirportById(database, destinationID);

		System.out.println("Enter departure time (yyyy-MM-dd::HH:mm:ss): ");
		String dTime = s.next();
		LocalDateTime departureTime = LocalDateTime.parse(dTime, format);

		System.out.println("Enter arrival time (yyyy-MM-dd::HH:mm:ss): ");
		String aTime = s.next();
		LocalDateTime arrivalTime = LocalDateTime.parse(aTime, format);

		Flight flight = new Flight();
		flight.setAirplane(plane);
		flight.setOrigin(originAirport);
		flight.setDestination(destinationAirport);
		flight.setDepartureTime(departureTime);
		flight.setArrivalTime(arrivalTime);

		ArrayList<Flight> flights = getAllFlights(database);
		int id = 0;
		if (flights.size()!=0) id = flights.size();

		flight.setId(id);

		StringBuilder fields = new StringBuilder("id, airplane, origin, destination, departureTime, arrivalTime, isDelayed, bookedEconomy, bookedBusiness, stuff, passengers");
		StringBuilder values = new StringBuilder("'" + flight.getId() + "', '"
				+ flight.getAirplane().getId() + "', '"
				+ flight.getOrigin().getId() + "', '"
				+ flight.getDestination().getId() + "', '"
				+ flight.getDepartureTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd::HH:mm:ss")) + "', '"
				+ flight.getArrivalTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd::HH:mm:ss")) + "', "
				+ "'false', 0, 0, '<%%/>', '<%%/>'");

		String insert = "INSERT INTO flights (" + fields.toString() + ") VALUES (" + values.toString() + ");";
		database.getStatement().execute(insert);

		System.out.println("Flight added successfully");

	}

	public static ArrayList<Flight> getAllFlights(Database database) throws SQLException{
		ArrayList<Flight> flights = new ArrayList<>();
		String select = "SELECT * FROM flights;";
		ResultSet rs = database.getStatement().executeQuery(select);

		ArrayList<Integer> IDs= new ArrayList<Integer>();
		ArrayList<Integer> planeIDs= new ArrayList<Integer>();
		ArrayList<Integer> originIDs= new ArrayList<Integer>();
		ArrayList<Integer> destIDs= new ArrayList<Integer>();
		ArrayList<String> depTimes= new ArrayList<String>();
		ArrayList<String> arrTimes= new ArrayList<String>();
		ArrayList<String> dels= new ArrayList<String>();
		ArrayList<Integer> bookedEconomySeats= new ArrayList<Integer>();
		ArrayList<Integer> bookedBusinessSeats= new ArrayList<Integer>();
		ArrayList<String> sts= new ArrayList<String>();
		ArrayList<String> pass= new ArrayList<String>();

		while(rs.next()) {

			IDs.add(rs.getInt("id"));
			planeIDs.add(rs.getInt("airplane"));
			originIDs.add(rs.getInt("origin"));
			destIDs.add(rs.getInt("destination"));
			depTimes.add(rs.getString("departureTime"));
			arrTimes.add(rs.getString("arrivalTime"));
			dels.add(rs.getString("isDelayed"));
			bookedEconomySeats.add(rs.getInt("bookedEconomy"));
			bookedBusinessSeats.add(rs.getInt("bookedBusiness"));
			sts.add(rs.getString("stuff"));
			pass.add(rs.getString("passengers"));
		}
		for (int i = 0; i < IDs.size(); i++) {
			Flight flight = new Flight();
			flight.setId(IDs.get(i));

			int planeID = planeIDs.get(i);
			int originID = originIDs.get(i);
			int destID  = destIDs.get(i);
			String depTime = depTimes.get(i);
			String arrTime = arrTimes.get(i);
			String del = dels.get(i);
			flight.setBookedEconomy(bookedEconomySeats.get(i));
			flight.setBookedBusiness(bookedBusinessSeats.get(i));
			String st = sts.get(i);
			String pas = pass.get(i);

			boolean delayed = Boolean.parseBoolean(del);
			Airplane plane = AirplanesController.getAirplaneById(database, planeID);
			flight.setAirplane(plane);


			flight.setOrigin(AirportsController.getAirportById(database, originID));


			flight.setDestination(AirportsController.getAirportById(database, destID));


			LocalDateTime departure = LocalDateTime.parse(depTime, format);
			flight.setDepartureTime(departure);


			LocalDateTime arrival = LocalDateTime.parse(arrTime, format);
			flight.setArrivalTime(arrival);


			if (delayed) flight.delay();


			String [] stuffID = st.split("<%%/>");
			Employee[] stuff = new Employee[10];
			EmployeeController employeeController = new EmployeeController();
			for (int j = 0; j < stuffID.length; j++) {
				int id = Integer.parseInt(stuffID[j]);
				stuff[j] = employeeController.getById(database, id);
			}
			flight.setStuff(stuff);


			String[] passengersID = pas.split("<%%/>");
			int totalCapacity = plane.getEconomyCapacity() + plane.getBusinessCapacity();
			Passenger[] passengers = new Passenger[totalCapacity];
			PassengerController passengerController = new PassengerController();
			for(int j = 0; j < passengersID.length; j++) {
				int id = Integer.parseInt(passengersID[j]);
				passengers[j] = passengerController.getById(database, id);
			}
			flight.setPassengers(passengers);

			flights.add(flight);
		}


		return flights;
	}

	public static void showAllFlights(Database database) throws SQLException {
		ArrayList<Flight> flights = getAllFlights(database);
		System.out.printf("%-5s %-25s %-25s %-25s %-25s %-25s %-15s %-30s %-30s%n",
				"ID", "Airplane", "Origin Airport", "Destination Airport", 
				"Departure Time", "Arrival Time", "Status", 
				"Available Economy Seats", "Available Business Seats");
		for (Flight f : flights) {
			f.print();
		}
	}

	public static void delayFlight(Database database, Scanner s) throws SQLException {
		int id = getFlightIdFromUser(database, s);

		String update = "UPDATE `flights` SET `isDelayed` = 'true' WHERE `id` = " + id + ";";
		database.getStatement().execute(update);
		System.out.println("Flight delayed succefully!");
	}

	public static void bookFlight(Database database, Scanner s) throws SQLException {
		int id = getFlightIdFromUser(database, s);

		Flight flight = getFlight(database, id);
		Passenger passenger;
		PassengerController passengerController  = new PassengerController();
		System.out.println("Enter passenger id (int): \n(-1 to get passenger by name)");
		int passID = s.nextInt();
		if(passID == -1) {
			passengerController.findByName(database, s);
			System.out.println("Enter passenger id: ");
			passID = s.nextInt();
		} 
		passenger = passengerController.getById(database, passID);

		System.out.println("1. Economy seat");
		System.out.println("2. Business seat");  
		int n = s.nextInt();

		System.out.println("Enter number of seats (int): ");
		int num = s.nextInt();

		if(n == 1) { 
			flight.setBookedEconomy(flight.getBookedEconomy() + num);  
		} else {
			flight.setBookedBusiness(flight.getBookedBusiness() + num); 
		}

		Passenger[] passengers = flight.getPassengers();
		for (int i = 0; i < passengers.length; i++) {
			if(passengers[i] == null) {
				passengers[i] = passenger;
				break;
			}
		}

		StringBuilder sb = new StringBuilder();
		for (Passenger p : flight.getPassengers()) {
			if (p != null) sb.append(p.getId()).append("<%%/>");
		}

		String passengerIds = sb.toString();
		if (!passengerIds.isEmpty()) {
			passengerIds = "'" + passengerIds + "'";
		} else {
			passengerIds = "NULL";
		}

		String update = "UPDATE flights SET bookedEconomy = " + flight.getBookedEconomy() +
				", bookedBusiness = " + flight.getBookedBusiness() +
				", passengers = " + passengerIds + 
				" WHERE id = " + flight.getId() + ";";
		database.getStatement().execute(update);
		System.out.println("booked successfully!");
	}


	public static Flight getFlight(Database database, int id) throws SQLException {
		Flight flight = new Flight();
		String select = "SELECT * FROM flights WHERE id = "+ id + ";";
		ResultSet rs = database.getStatement().executeQuery(select);
		rs.next();
		int ID = rs.getInt("id");
		int planeID = rs.getInt("airplane");
		int originID = rs.getInt("origin");
		int destID = rs.getInt("destination");
		String depTime = rs.getString("departureTime");
		String arrTime = rs.getString("arrivalTime");
		String del = rs.getString("isDelayed");
		int bookedEconomy = rs.getInt("bookedEconomy");
		int businessEconomy = rs.getInt("bookedBusiness");
		String st = rs.getString("stuff");
		String pas = rs.getString("passengers");
		boolean delayed = Boolean.parseBoolean(del);

		Airplane plane = AirplanesController.getAirplaneById(database, planeID);
		flight.setId(ID);
		flight.setAirplane(plane);


		flight.setOrigin(AirportsController.getAirportById(database, originID));


		flight.setDestination(AirportsController.getAirportById(database, destID));


		LocalDateTime departure = LocalDateTime.parse(depTime, format);
		flight.setDepartureTime(departure);


		LocalDateTime arrival = LocalDateTime.parse(arrTime, format);
		flight.setArrivalTime(arrival);


		if (delayed) flight.delay();

		flight.setBookedEconomy(bookedEconomy);
		flight.setBookedBusiness(businessEconomy);

		String [] stuffID = st.split("<%%/>");
		Employee[] stuff = new Employee[10];
		EmployeeController employeeController = new EmployeeController();
		for (int j = 0; j < stuffID.length; j++) {
			int idst = Integer.parseInt(stuffID[j]);
			stuff[j] = employeeController.getById(database, idst);
		}
		flight.setStuff(stuff);


		String[] passengersID = pas.split("<%%/>");
		int totalCapacity = plane.getEconomyCapacity() + plane.getBusinessCapacity();
		Passenger[] passengers = new Passenger[totalCapacity];
		PassengerController passengerController = new PassengerController();
		for(int j = 0; j < passengersID.length; j++) {

			int idpass = Integer.parseInt(passengersID[j]);
			passengers[j] = passengerController.getById(database, idpass);
		}
		flight.setPassengers(passengers);

		return flight;
	}

	public static void setFlightStuff(Database database, Scanner s) throws SQLException {
		int id = getFlightIdFromUser(database, s);

		Flight flight = getFlight(database, id);
		EmployeeController employeeController = new EmployeeController();
		System.out.println("1. Show all employees");
		System.out.println("2. Continue");
		int j = s.nextInt();
		if(j==1) employeeController.printAll(database);
		System.out.println("Enter employees ids (int):");
		Employee[] employees = new Employee[10];
		for (int i = 0; i < 10; i++) {
			System.out.println("id " + (i+1)+ "/10");
			int ID = s.nextInt();
			employees[i] = employeeController.getById(database, ID);
		}

		flight.setStuff(employees);

		StringBuilder bd = new StringBuilder();
		for (Employee e: flight.getStuff()) {
			if (e!=null) bd.append(e.getId()).append("<%%/>");
		}

		String update = "UPDATE flights SET stuff = '" + bd.toString() + "' WHERE id= " + id + ";";

		database.getStatement().execute(update);
		System.out.println("Stuff setted successfullt");
	}

	public static void cancelFlight(Database database, Scanner s) throws SQLException {
		int id = getFlightIdFromUser(database, s);
		String delete = "DELETE FROM `flights` WHERE `id` = " + id + " ;";
		database.getStatement().execute(delete);
		System.out.println("Flight cancelled successfully!");
	}


	public static void printFlightStuff(Database database, Scanner s) throws SQLException {
		int id = getFlightIdFromUser(database, s);

		Flight f = getFlight(database, id);


		System.out.println(String.format("%-5s %-15s %-15s %-15s %-15s %-20s", "ID", "First Name", "Last Name", "Email", "Phone Number", "Position"));

		for (Employee e : f.getStuff()) {
			if(e!=null) {
				System.out.println(String.format("%-5d %-15s %-15s %-15s %-15s %-20s", 
						e.getId(), 
						e.getFirstName(), 
						e.getLastName(), 
						e.getEmail(), 
						e.getPhone(), 
						e.getPosition()));
			}
		}

	}

	public static void printFlightPassengers(Database database, Scanner s) throws SQLException {
		int id = getFlightIdFromUser(database, s);
		Flight f = getFlight(database, id);

		System.out.println(String.format("%-5s %-15s %-15s %-15s %-15s", "ID", "First Name", "Last Name", "Email", "Phone Number"));

		for (Passenger p : f.getPassengers()) {
			if(p!=null) {
				System.out.println(String.format("%-5d %-15s %-15s %-15s %-15s", 
						p.getId(), 
						p.getFirstName(), 
						p.getLastName(), 
						p.getEmail(), 
						p.getPhone()));
			}
		}
	}


	private static int getFlightIdFromUser(Database database, Scanner s) throws SQLException {
		System.out.println("Enter flight id (int): \n(-1 to show all flights)");
		int id = s.nextInt();
		if(id == -1) {
			showAllFlights(database);
			System.out.println("Enter flight id (int): ");
			id = s.nextInt();
		}
		return id;
	}

}
