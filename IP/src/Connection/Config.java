package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Config {

    public static Connection setKoneksi() {
        String db = "jdbc:mysql://localhost:3306/nilai";
        Connection koneksi = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            koneksi = (Connection) DriverManager.getConnection(db, "root", "");
            System.out.println("-> Connected ");
        } catch (ClassNotFoundException | SQLException e) {

        }
        return koneksi;
    }

    public static int executeUpdate(String SQL) {
        int status = 0;
        try {
            Statement st = setKoneksi().createStatement();
            status = st.executeUpdate(SQL);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return status;
    }

    public static ResultSet executeQuery(String SQL) {
        ResultSet rs = null;
        try {
            Statement st = setKoneksi().createStatement();
            rs = st.executeQuery(SQL);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return rs;
    }
}
