//package AirlineManagmentSystem;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.OutputStream;
//import java.io.PrintStream;
//import java.sql.SQLException;
//import java.util.Scanner;
//
//public class MainGUI extends JFrame {
//    
//    private Database database;
//    private PassengerController passengerController;
//    private EmployeeController employeeController;
//    private AirplanesController airplanesController;
//    private AirportsController airportsController;
//    private FlightsController flightsController;
//
//    private JTextArea consoleArea;
//    private JTextField inputField;
//
//    public MainGUI() throws SQLException {
//        database = new Database();
//        passengerController = new PassengerController();
//        employeeController = new EmployeeController();
//        airplanesController = new AirplanesController();
//        airportsController = new AirportsController();
//        flightsController = new FlightsController();
//
//        setTitle("Airline Management System");
//        setSize(800, 600);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(new BorderLayout());
//
//        // Create and add components
//        consoleArea = new JTextArea();
//        consoleArea.setEditable(false);
//        JScrollPane scrollPane = new JScrollPane(consoleArea);
//        add(scrollPane, BorderLayout.CENTER);
//
//        inputField = new JTextField();
//        add(inputField, BorderLayout.SOUTH);
//
//        // Redirect system output to consoleArea
//        PrintStream printStream = new PrintStream(new JTextAreaOutputStream(consoleArea));
//        System.setOut(printStream);
//        System.setErr(printStream);
//
//        // Add buttons
//        JPanel buttonPanel = new JPanel(new GridLayout(6, 5));
//        addButtons(buttonPanel);
//        add(buttonPanel, BorderLayout.NORTH);
//
//        // Handle input
//        inputField.addActionListener(e -> handleInput());
//    }
//
//    private void addButtons(JPanel panel) {
//        addButton(panel, "Add new passenger", e -> executePassengerAction("addNew"));
//        addButton(panel, "Get passenger by name", e -> executePassengerAction("findByName"));
//        addButton(panel, "Print all passengers", e -> executePassengerAction("printAll"));
//        addButton(panel, "Edit passenger", e -> executePassengerAction("edit"));
//        addButton(panel, "Delete passenger", e -> executePassengerAction("delete"));
//
//        addButton(panel, "Add new employee", e -> executeEmployeeAction("addNew"));
//        addButton(panel, "Get employee by name", e -> executeEmployeeAction("findByName"));
//        addButton(panel, "Print all employees", e -> executeEmployeeAction("printAll"));
//        addButton(panel, "Edit employee", e -> executeEmployeeAction("edit"));
//        addButton(panel, "Delete employee", e -> executeEmployeeAction("delete"));
//
//        addButton(panel, "Add new plane", e -> executeAirplaneAction("addNewAirplane"));
//        addButton(panel, "Print all planes", e -> executeAirplaneAction("printAllPlanes"));
//        addButton(panel, "Edit plane", e -> executeAirplaneAction("editAirplane"));
//        addButton(panel, "Delete plane", e -> executeAirplaneAction("deleteAirplane"));
//
//        addButton(panel, "Add new airport", e -> executeAirportAction("addNewAirport"));
//        addButton(panel, "Print all airports", e -> executeAirportAction("printAllAirports"));
//        addButton(panel, "Edit airport", e -> executeAirportAction("editAirport"));
//        addButton(panel, "Delete airport", e -> executeAirportAction("deleteAirport"));
//
//        addButton(panel, "Create new flight", e -> executeFlightAction("AddNewFlight"));
//        addButton(panel, "Show all flights", e -> executeFlightAction("showAllFlights"));
//        addButton(panel, "Delay flight", e -> executeFlightAction("delayFlight"));
//        addButton(panel, "Book flight", e -> executeFlightAction("bookFlight"));
//        addButton(panel, "Set flight stuff", e -> executeFlightAction("setFlightStuff"));
//        addButton(panel, "Cancel flight", e -> executeFlightAction("cancelFlight"));
//        addButton(panel, "Show flight stuff", e -> executeFlightAction("printFlightStuff"));
//        addButton(panel, "Show flight passengers", e -> executeFlightAction("printFlightPassengers"));
//
//        JButton quitButton = new JButton("Quit");
//        quitButton.addActionListener(e -> System.exit(0));
//        panel.add(quitButton);
//    }
//
//    private void addButton(JPanel panel, String text, ActionListener actionListener) {
//        JButton button = new JButton(text);
//        button.addActionListener(actionListener);
//        panel.add(button);
//    }
//
//    private void handleInput() {
//        String input = inputField.getText();
//        // Process input (you can add more sophisticated handling here)
//        consoleArea.append("Input: " + input + "\n");
//        inputField.setText(""); // Clear the input field
//    }
//
//    private void executePassengerAction(String action) {
//        try {
//            switch (action) {
//                case "addNew":
//                    passengerController.addNew(database, new Scanner(System.in));
//                    break;
//                case "findByName":
//                    passengerController.findByName(database, new Scanner(System.in));
//                    break;
//                case "printAll":
//                    passengerController.printAll(database);
//                    break;
//                case "edit":
//                    passengerController.edit(database, new Scanner(System.in));
//                    break;
//                case "delete":
//                    passengerController.delete(database, new Scanner(System.in));
//                    break;
//            }
//        } catch (SQLException e1) {
//            e1.printStackTrace();
//        }
//    }
//
//    private void executeEmployeeAction(String action) {
//        try {
//            switch (action) {
//                case "addNew":
//                    employeeController.addNew(database, new Scanner(System.in));
//                    break;
//                case "findByName":
//                    employeeController.findByName(database, new Scanner(System.in));
//                    break;
//                case "printAll":
//                    employeeController.printAll(database);
//                    break;
//                case "edit":
//                    employeeController.edit(database, new Scanner(System.in));
//                    break;
//                case "delete":
//                    employeeController.delete(database, new Scanner(System.in));
//                    break;
//            }
//        } catch (SQLException e1) {
//            e1.printStackTrace();
//        }
//    }
//
//    private void executeAirplaneAction(String action) {
//        try {
//            switch (action) {
//                case "addNewAirplane":
//                    airplanesController.addNewAirplane(database, new Scanner(System.in));
//                    break;
//                case "printAllPlanes":
//                    airplanesController.printAllPlanes(database);
//                    break;
//                case "editAirplane":
//                    airplanesController.editAirplane(database, new Scanner(System.in));
//                    break;
//                case "deleteAirplane":
//                    airplanesController.deleteAirplane(database, new Scanner(System.in));
//                    break;
//            }
//        } catch (SQLException e1) {
//            e1.printStackTrace();
//        }
//    }
//
//    private void executeAirportAction(String action) {
//        try {
//            switch (action) {
//                case "addNewAirport":
//                    airportsController.addNewAirport(database, new Scanner(System.in));
//                    break;
//                case "printAllAirports":
//                    airportsController.printAllAirports(database);
//                    break;
//                case "editAirport":
//                    airportsController.editAirport(database, new Scanner(System.in));
//                    break;
//                case "deleteAirport":
//                    airportsController.deleteAirport(database, new Scanner(System.in));
//                    break;
//            }
//        } catch (SQLException e1) {
//            e1.printStackTrace();
//        }
//    }
//
//    private void executeFlightAction(String action) {
//        try {
//            switch (action) {
//                case "AddNewFlight":
//                    flightsController.AddNewFlight(database, new Scanner(System.in));
//                    break;
//                case "showAllFlights":
//                    flightsController.showAllFlights(database);
//                    break;
//                case "delayFlight":
//                    flightsController.delayFlight(database, new Scanner(System.in));
//                    break;
//                case "bookFlight":
//                    flightsController.bookFlight(database, new Scanner(System.in));
//                    break;
//                case "setFlightStuff":
//                    flightsController.setFlightStuff(database, new Scanner(System.in));
//                    break;
//                case "cancelFlight":
//                    flightsController.cancelFlight(database, new Scanner(System.in));
//                    break;
//                case "printFlightStuff":
//                    flightsController.printFlightStuff(database, new Scanner(System.in));
//                    break;
//                case "printFlightPassengers":
//                    flightsController.printFlightPassengers(database, new Scanner(System.in));
//                    break;
//            }
//        } catch (SQLException e1) {
//            e1.printStackTrace();
//        }
//    }
//}
//
//class JTextAreaOutputStream extends OutputStream {
//    private JTextArea textArea;
//
//    public JTextAreaOutputStream(JTextArea textArea) {
//        this.textArea = textArea;
//    }
//
//    @Override
//    public void write(int b) {
//        SwingUtilities.invokeLater(() -> textArea.append(String.valueOf((char) b)));
//    }
//
//    @Override
//    public void write(byte[] b, int off, int len) {
//        SwingUtilities.invokeLater(() -> textArea.append(new String(b, off, len)));
//    }
//}
