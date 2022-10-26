package DBCon;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBCon {
    private static final Connection con = Connect();
    private static Connection Connect() {
        Connection con = null;
        try {
            // Establish connection
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sdp", "postgres", "Post123gre");
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return con;
    }

    public static Connection getCon() {
        return con;
    }
}
