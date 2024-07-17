package AirlineManagmentSystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class AirplanesController {

    public static void addNewAirplane(Database database, Scanner s) throws SQLException {
        System.out.println("Enter economy capacity (int): ");
        int economyCapacity = s.nextInt();
        System.out.println("Enter business capacity (int): ");
        int businessCapacity = s.nextInt();
        System.out.println("Enter model: ");
        String model = s.next();

        Airplane airplane = new Airplane();
        airplane.setEconomyCapacity(economyCapacity);
        airplane.setBusinessCapacity(businessCapacity);
        airplane.setModel(model);

        int id = generateNewAirplaneId(database);
        airplane.setId(id);
        String insert = buildInsertQuery(airplane);
        executeUpdate(database, insert);

        System.out.println("Airplane added successfully");
    }
 
    public static ArrayList<Airplane> getAllPlanes(Database database) throws SQLException {
        ArrayList<Airplane> planes = new ArrayList<>();
        String get = "SELECT * FROM airplanes;";
        ResultSet rs = database.getStatement().executeQuery(get);
        while (rs.next()) {
            Airplane a = new Airplane();
            a.setId(rs.getInt("id"));
            a.setEconomyCapacity(rs.getInt("economyCapacity"));
            a.setBusinessCapacity(rs.getInt("businessCapacity"));
            a.setModel(rs.getString("model"));
            planes.add(a);
        }
        return planes;
    }

    public static void printAllPlanes(Database database) throws SQLException {
        ArrayList<Airplane> planes = getAllPlanes(database);
        System.out.println("\n--------------------------------");
        for (Airplane plane : planes) {
            plane.print();
        }
        System.out.println("--------------------------------\n");
    }

    public static void editAirplane(Database database, Scanner s) throws SQLException {   
        int id = selectAirplanesID(database, s);
        Airplane airplane = getAirplaneById(database, id);

        if (airplane == null) {
            System.out.println("Airplane with id " + id + " not found.");
            return;
        }

        System.out.println("Enter new economy capacity (-1 to keep old value): ");
        int economyCapacity = s.nextInt();
        if (economyCapacity != -1) {
            airplane.setEconomyCapacity(economyCapacity);
        }

        System.out.println("Enter new business capacity (-1 to keep old value): ");
        int businessCapacity = s.nextInt();
        if (businessCapacity != -1) {
            airplane.setBusinessCapacity(businessCapacity);
        }

        System.out.println("Enter new model (-1 to keep old value): ");
        String model = s.next();
        if (!model.equals("-1")) {
            airplane.setModel(model);
        }

        String update = buildUpdateQuery(airplane);
        executeUpdate(database, update);

        System.out.println("Airplane updated successfully");
    }

    public void deleteAirplane(Database database, Scanner s) throws SQLException {
        int id = selectAirplanesID(database, s);

        String delete = buildDeleteQuery(id);
        executeUpdate(database, delete);

        System.out.println("Airplane deleted successfully");
    }

    public static Airplane getAirplaneById(Database database, int id) throws SQLException {
        String get = "SELECT * FROM airplanes WHERE id = " + id + ";";
        ResultSet rs = database.getStatement().executeQuery(get);

        if (rs.next()) {
            Airplane airplane = new Airplane();
            airplane.setId(rs.getInt("id"));
            airplane.setEconomyCapacity(rs.getInt("economyCapacity"));
            airplane.setBusinessCapacity(rs.getInt("businessCapacity"));
            airplane.setModel(rs.getString("model"));
            return airplane;
        }
        return null;
    }
    
    private static int generateNewAirplaneId(Database database) throws SQLException {
        ArrayList<Airplane> planes = getAllPlanes(database);
        if (!planes.isEmpty()) {
            return planes.get(planes.size() - 1).getId() + 1;
        }
        return 0;
    }
    
    private static String buildInsertQuery(Airplane airplane) {
        StringBuilder fields = new StringBuilder("id, economyCapacity, businessCapacity, model");
        StringBuilder values = new StringBuilder("'" + airplane.getId() + "', '"
                + airplane.getEconomyCapacity() + "', '"
                + airplane.getBusinessCapacity() + "', '"
                + airplane.getModel() + "'");
        return "INSERT INTO airplanes (" + fields.toString() + ") VALUES (" + values.toString() + ");";
    }
    
    private static void executeUpdate(Database database, String query) throws SQLException {
        database.getStatement().execute(query);
    }
    
    private static String buildUpdateQuery(Airplane airplane) {
        return "UPDATE airplanes SET economyCapacity = '" + airplane.getEconomyCapacity() +
               "', businessCapacity = '" + airplane.getBusinessCapacity() +
               "', model = '" + airplane.getModel() +
               "' WHERE id = " + airplane.getId() + ";";
    }

    private static String buildDeleteQuery(int id) {
        return "DELETE FROM airplanes WHERE id = " + id + ";";
    }
    
    public static int selectAirplanesID(Database database, Scanner s) throws SQLException {
    	System.out.println("Enter id of the airplane (int): (-1 to show all airports)");
    	int id = s.nextInt();
    	if(id == -1) {
        	printAllPlanes(database);
        	System.out.println("Enter id of the airplane (int):");
        	id = s.nextInt();
        }
		return id;
	}
}
