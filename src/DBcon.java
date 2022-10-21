import java.sql.Connection;
import java.sql.DriverManager;

public class DBcon {
    private static Connection con = Connect();
    private static Connection Connect() {
        Connection con = null;
        try {
            // Establish connection
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "Post123gre");
            con.close();
        } catch (Exception e) {
            System.out.println(e);
            System.exit(1);
        }
        return con;
    }

    public static Connection getCon() {
        return con;
    }
}
