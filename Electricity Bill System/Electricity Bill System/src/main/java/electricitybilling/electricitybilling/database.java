package electricitybilling.electricitybilling;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class database {
    Connection connection;
    public Statement statement;

    database() {
        try {
            // Create a connection to the MySQL database
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Bill_system", "root", "Nirush");
            // Create a statement object to execute SQL queries
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
