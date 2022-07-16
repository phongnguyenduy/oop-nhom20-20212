package Model.Connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    private static Connection connection = null;
    private final static String URL = "jdbc:postgresql://localhost:5432/postgres";
    private final static String user = "postgres";
    //private final static String password = "minhctthvn667";
    private final static String password = "121102";

    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return connection;
    }
}
