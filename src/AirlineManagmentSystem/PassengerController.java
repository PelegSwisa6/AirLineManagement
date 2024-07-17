package AirlineManagmentSystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class PassengerController extends BaseController<Passenger> {

    @Override
    public Passenger createInstance() {
        return new Passenger();
    }

    @Override
    public String getTableName() {
        return "passengers";
    }

    @Override
    public void setSpecificFields(Passenger entity, ResultSet rs) throws SQLException {
        
    }

    @Override
    public void setSpecificInsertFields(Passenger entity, StringBuilder fields, StringBuilder values) {
        
    }

    @Override
    public void setSpecificEditFields(Passenger entity, Scanner s) {
        
    }

    @Override
    public String getSpecificUpdateFields(Passenger entity) {
        return "";
    }

	@Override
	public void promptForSpecificFields(Passenger entity, Scanner s) {
				
	}
}
