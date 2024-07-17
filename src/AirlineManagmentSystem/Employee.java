package AirlineManagmentSystem;

public class Employee extends Person {

    private double salary;
    private String position;

    public Employee() {
        super();
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public void print() {
        super.print();
        System.out.println("Salary: " + getSalary());
        System.out.println("Position: " + getPosition());
    }
}
