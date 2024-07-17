package AirlineManagmentSystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class EmployeeController extends BaseController<Employee> {

    @Override
	public Employee createInstance() {
        return new Employee();
    }

    @Override
	public String getTableName() {
        return "employees";
    }

    @Override
	public void setSpecificFields(Employee entity, ResultSet rs) throws SQLException {
        entity.setSalary(rs.getDouble("salary"));
        entity.setPosition(rs.getString("position"));
    }

    @Override
	public void setSpecificInsertFields(Employee entity, StringBuilder fields, StringBuilder values) {
        fields.append(", salary, position");
        values.append(", '").append(entity.getSalary()).append("', '").append(entity.getPosition()).append("'");
    }

    @Override
	public void setSpecificEditFields(Employee entity, Scanner s) {
        System.out.println("Enter salary: \n(-1 to keep old value)");
        double salary = s.nextDouble();
        if (salary != -1) entity.setSalary(salary);

        System.out.println("Enter position: \n(-1 to keep old value)");
        String position = s.next();
        if (!position.equals("-1")) entity.setPosition(position);
    }

    @Override
	public String getSpecificUpdateFields(Employee entity) {
        return ", salary='" + entity.getSalary() + "', position='" + entity.getPosition() + "'";
    }

    @Override
	public void promptForSpecificFields(Employee entity, Scanner s) {
        System.out.println("Enter salary: ");
        double salary = s.nextDouble();
        entity.setSalary(salary);

        System.out.println("Enter position: ");
        String position = s.next();
        entity.setPosition(position);
    }
}
