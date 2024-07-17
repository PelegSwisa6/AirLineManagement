package AirlineManagmentSystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class AirportsController {

    public static void addNewAirport(Database database, Scanner s) throws SQLException {
        System.out.println("Enter city: ");
        String city = s.next();
        
        Airport airport = createAirportWithCity(city);
        int id = generateNewAirportId(database);
        airport.setId(id);
        
        String insert = createInsertQuery(airport);
        database.getStatement().execute(insert);
        
        System.out.println("Airport added successfully");
    }

    public static ArrayList<Airport> getAllAirports(Database database) throws SQLException {
        return fetchAirports(database, "SELECT * FROM airports;");
    }

    public static void printAllAirports(Database database) throws SQLException {
        ArrayList<Airport> airports = getAllAirports(database);
        System.out.println("\n--------------------------------");
        for (Airport airport : airports) {
            airport.print();
        }
        System.out.println("--------------------------------\n");
    }

    public static void editAirport(Database database, Scanner s) throws SQLException {
        
        int id = selectAirportID(database, s);
        
        Airport airport = getAirportById(database, id);
        
        System.out.println("Enter new city: \n(-1 to keep old value)");
        String city = s.next();
        if (!city.equals("-1")) airport.setCity(city);
        
        String update = "UPDATE airports SET city='" + airport.getCity() + "' WHERE id=" + airport.getId() + ";";
        database.getStatement().execute(update);
        
        System.out.println("Airport edited successfully!");
    }

 
	public static void deleteAirport(Database database, Scanner s) throws SQLException {
		int id = selectAirportID(database, s);
        Airport airport = getAirportById(database, id);
        
        String delete = "DELETE FROM airports WHERE id=" + airport.getId() + ";";
        database.getStatement().execute(delete);
        
        System.out.println("Airport deleted successfully!");
    }

    public static Airport getAirportById(Database database, int id) throws SQLException {
        String get = "SELECT * FROM airports WHERE id = " + id + ";";
        return fetchSingleAirport(database, get);
    }

    private static Airport createAirportWithCity(String city) {
        Airport airport = new Airport();
        airport.setCity(city);
        return airport;
    }

    private static int generateNewAirportId(Database database) throws SQLException {
        ArrayList<Airport> airports = getAllAirports(database);
        if (airports.size() != 0) {
            return airports.get(airports.size() - 1).getId() + 1;
        }
        return 0;
    }

    private static String createInsertQuery(Airport airport) {
        StringBuilder fields = new StringBuilder("id, city");
        StringBuilder values = new StringBuilder("'" + airport.getId() + "', '" + airport.getCity() + "'");
        return "INSERT INTO airports (" + fields.toString() + ") VALUES (" + values.toString() + ");";
    }

    private static ArrayList<Airport> fetchAirports(Database database, String query) throws SQLException {
        ArrayList<Airport> airports = new ArrayList<>();
        ResultSet rs = database.getStatement().executeQuery(query);
        while (rs.next()) {
            Airport a = new Airport();
            a.setId(rs.getInt("id"));
            a.setCity(rs.getString("city"));
            airports.add(a);
        }
        return airports;
    }

    private static Airport fetchSingleAirport(Database database, String query) throws SQLException {
        ResultSet rs = database.getStatement().executeQuery(query);
        rs.next();
        Airport airport = new Airport();
        airport.setId(rs.getInt("id"));
        airport.setCity(rs.getString("city"));
        return airport;
    }
    
    private static int selectAirportID(Database database, Scanner s) throws SQLException {
    	System.out.println("Enter id of the airport (int): (-1 to show all airports)");
    	int id = s.nextInt();
    	if(id == -1) {
        	printAllAirports(database);
        	System.out.println("Enter id of the airport (int):");
        	id = s.nextInt();
        }
		return id;
	}
}
