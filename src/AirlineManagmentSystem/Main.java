package AirlineManagmentSystem;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) throws SQLException {
        Database database = new Database();
        Scanner s = new Scanner(System.in);

        PassengerController passengerController = new PassengerController();
        EmployeeController employeeController = new EmployeeController();
        AirplanesController airplanesController = new AirplanesController();
        int i = 0;

        do {
            System.out.println("Welcome to Airline Management System");
            System.out.println("1. Add new passenger");
            System.out.println("2. Get passenger by name");
            System.out.println("3. Print all passengers");
            System.out.println("4. Edit passenger");
            System.out.println("5. Delete passenger");
            System.out.println("6. Add new employee");
            System.out.println("7. Get employee by name");
            System.out.println("8. Print all employees");
            System.out.println("9. Edit employee");
            System.out.println("10. Delete employee");
            System.out.println("11. Add new plane");
            System.out.println("12. Print all planes");
            System.out.println("13. Edit plane");
            System.out.println("14. Delete plane");
            System.out.println("15. Add new airport");
            System.out.println("16. Print all airports");
            System.out.println("17. Edit airport");
            System.out.println("18. Delete airport");
            System.out.println("19. Create new flight");
            System.out.println("20. Show all flights");
            System.out.println("21. Delay flight");
            System.out.println("22. Book flight");
            System.out.println("23. Set flight stuff");
            System.out.println("24. Canecl flight");
            System.out.println("25. Show flight stuff");
            System.out.println("26. Show flight passengers");
            System.out.println("27. Quit");

            i = s.nextInt();
            switch (i) {
                case 1:
                    passengerController.addNew(database, s);
                    break;
                case 2:
                    passengerController.findByName(database, s);
                    break;
                case 3:
                    passengerController.printAll(database);
                    break;
                case 4:
                    passengerController.edit(database, s);
                    break;
                case 5:
                    passengerController.delete(database, s);
                    break;
                case 6:
                    employeeController.addNew(database, s);
                    break;
                case 7:
                    employeeController.findByName(database, s);
                    break;
                case 8:
                    employeeController.printAll(database);
                    break;
                case 9:
                    employeeController.edit(database, s);
                    break;
                case 10:
                    employeeController.delete(database, s);
                    break;
                case 11:
                	AirplanesController.addNewAirplane(database, s);
                    break;
                case 12:
                	AirplanesController.printAllPlanes(database);
                    break;
                case 13:
                	AirplanesController.editAirplane(database, s);
                    break;
                case 14:
                	airplanesController.deleteAirplane(database, s);
                    break;
                case 15:
                	AirportsController.addNewAirport(database, s);
                    break;
                case 16:
                	AirportsController.printAllAirports(database);
                    break;
                case 17:
                	AirportsController.editAirport(database, s);
                    break;
                case 18:
                	AirportsController.deleteAirport(database, s);
                    break;
                case 19:
                	FlightsController.AddNewFlight(database, s);
                    break;
                case 20:
                	FlightsController.showAllFlights(database);
                    break;
                case 21: 
                	FlightsController.delayFlight(database, s);
                	break;
                case 22: 
                	FlightsController.bookFlight(database, s);
                	break;
                case 23:
                	FlightsController.setFlightStuff(database, s);
                	break;
                case 24:
                	FlightsController.cancelFlight(database, s);
                	break;
                case 25:
                	FlightsController.printFlightStuff(database, s);
                	break;
                case 26:
                	FlightsController.printFlightPassengers(database, s);
                	break;
                case 27:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
                    break;
            }
        } while (i != 27);

        s.close();
    }
}
