package AirlineManagmentSystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class BaseController<T extends Person> {

    protected abstract T createInstance();

    protected abstract String getTableName();

    protected abstract void setSpecificFields(T entity, ResultSet rs) throws SQLException;

    protected abstract void setSpecificInsertFields(T entity, StringBuilder fields, StringBuilder values);

    protected abstract void promptForSpecificFields(T entity, Scanner s);

    protected abstract void setSpecificEditFields(T entity, Scanner s);

    protected abstract String getSpecificUpdateFields(T entity);

    public void promptForCommonFields(T entity, Scanner s) {
        System.out.println("Enter first name: ");
        entity.setFirstName(s.next());
        System.out.println("Enter last name: ");
        entity.setLastName(s.next());
        System.out.println("Enter phone: ");
        entity.setPhone(s.next());
        System.out.println("Enter email: ");
        entity.setEmail(s.next());
    }

    public void buildCommonInsertFields(T entity, StringBuilder fields, StringBuilder values) {
        fields.append("id, firstName, lastName, phone, email");
        values.append("'").append(entity.getId()).append("', '")
              .append(entity.getFirstName()).append("', '")
              .append(entity.getLastName()).append("', '")
              .append(entity.getPhone()).append("', '")
              .append(entity.getEmail()).append("'");
    }

    public void setCommonFieldsFromResultSet(T entity, ResultSet rs) throws SQLException {
        entity.setId(rs.getInt("id"));
        entity.setFirstName(rs.getString("firstName"));
        entity.setLastName(rs.getString("lastName"));
        entity.setPhone(rs.getString("phone"));
        entity.setEmail(rs.getString("email"));
    }

    public void addNew(Database database, Scanner s) throws SQLException {
        T entity = createInstance();
        promptForCommonFields(entity, s);
        promptForSpecificFields(entity, s);
        ArrayList<T> entities = getAll(database);
        int id = entities.isEmpty() ? 0 : entities.get(entities.size() - 1).getId() + 1;
        entity.setId(id);
        StringBuilder fields = new StringBuilder();
        StringBuilder values = new StringBuilder();
        buildCommonInsertFields(entity, fields, values);
        setSpecificInsertFields(entity, fields, values);
        String insert = "INSERT INTO " + getTableName() + " (" + fields.toString() + ") VALUES (" + values.toString() + ");";
        database.getStatement().execute(insert);
        System.out.println("Entity added successfully");
    }

    public T findByName(Database database, Scanner s) throws SQLException {
        System.out.println("Enter first name: ");
        String firstName = s.next();
        System.out.println("Enter last name: ");
        String lastName = s.next();
        String get = "SELECT * FROM " + getTableName() + " WHERE firstName = \"" + firstName + "\";";
        ResultSet rs = database.getStatement().executeQuery(get);
        T entity = createInstance();
        while (rs.next()) {
            setCommonFieldsFromResultSet(entity, rs);
            setSpecificFields(entity, rs);
            if (entity.getLastName().equals(lastName)) break;
        }
        entity.print();
        return entity;
    }

    public void edit(Database database, Scanner s) throws SQLException {
        System.out.println("Enter id (int): \n(-1 to get by name)");
        int id = s.nextInt();
        T entity;
        if (id == -1) {
            entity = findByName(database, s);
        } else {
            entity = getById(database, id);
        }
        System.out.println("Enter first name: \n(-1 to keep old value)");
        String firstName = s.next();
        if (!firstName.equals("-1")) entity.setFirstName(firstName);
        System.out.println("Enter last name: \n(-1 to keep old value)");
        String lastName = s.next();
        if (!lastName.equals("-1")) entity.setLastName(lastName);
        System.out.println("Enter phone: \n(-1 to keep old value)");
        String phone = s.next();
        if (!phone.equals("-1")) entity.setPhone(phone);
        System.out.println("Enter email: \n(-1 to keep old value)");
        String email = s.next();
        if (!email.equals("-1")) entity.setEmail(email);
        setSpecificEditFields(entity, s);
        String update = "UPDATE " + getTableName() + " SET firstName='" + entity.getFirstName() + "', lastName='" + entity.getLastName() + "', phone='" + entity.getPhone() + "', email='" + entity.getEmail() + "'";
        update += getSpecificUpdateFields(entity);
        update += " WHERE id=" + entity.getId() + ";";
        database.getStatement().execute(update);
        System.out.println("Entity edited successfully!");
    }

    public void delete(Database database, Scanner s) throws SQLException {
        System.out.println("Enter id (int): \n(-1 to get by name)");
        int id = s.nextInt();
        T entity;
        if (id == -1) {
            entity = findByName(database, s);
        } else {
            entity = getById(database, id);
        }
        String delete = "DELETE FROM " + getTableName() + " WHERE id=" + entity.getId() + ";";
        database.getStatement().execute(delete);
        System.out.println("Entity deleted successfully!");
    }

    public void printAll(Database database) throws SQLException {
        ArrayList<T> entities = getAll(database);
        System.out.println("\n--------------------------------");
        for (T entity : entities) {
            entity.print();
        }
        System.out.println("--------------------------------\n");
    }

    public ArrayList<T> getAll(Database database) throws SQLException {
        String get = "SELECT * FROM " + getTableName() + ";";
        ResultSet rs = database.getStatement().executeQuery(get);
        ArrayList<T> entities = new ArrayList<>();
        while (rs.next()) {
            T entity = createInstance();
            setCommonFieldsFromResultSet(entity, rs);
            setSpecificFields(entity, rs);
            entities.add(entity);
        }
        return entities;
    }

    public T getById(Database database, int id) throws SQLException {
        String get = "SELECT * FROM " + getTableName() + " WHERE id = " + id + ";";
        ResultSet rs = database.getStatement().executeQuery(get);
        T entity = createInstance();
        rs.next();
        setCommonFieldsFromResultSet(entity, rs);
        setSpecificFields(entity, rs);
        return entity;
    }
}
