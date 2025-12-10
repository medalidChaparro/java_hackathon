package medalid.chaparro.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
<<<<<<< HEAD

public class Conexion {

=======
import javax.swing.JOptionPane;

public class Conexion {

    // Datos de tu RDS
>>>>>>> 47bfbfd1a28da7c6a1658f9867da8742a7763a96
    private static final String DB_HOST = "medalid.c5kmcoqcywvu.us-east-1.rds.amazonaws.com";
    private static final String DB_NAME = "inventario_computadoras_db";
    private static final String DB_USER = "admin";
    private static final String DB_PASSWORD = "60512110";

<<<<<<< HEAD
    private static final String URL =
            "jdbc:mysql://" + DB_HOST + ":3306/" + DB_NAME +
                    "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    public static Connection getConnection() {
        try {
            // Cargar el driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Crear nueva conexión
            Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
            System.out.println("✅ Conexión exitosa a RDS MySQL");
            return conn;

        } catch (ClassNotFoundException e) {
            System.out.println("❌ No se encontró el driver JDBC MySQL: " + e.getMessage());
            e.printStackTrace();
            return null;

        } catch (SQLException e) {
            System.out.println("❌ Error al conectar a RDS MySQL");
            e.printStackTrace();  // Muestra la causa real
            return null;
        }
    }
}
=======
    private static final String URL = "jdbc:mysql://" + DB_HOST + ":3306/" + DB_NAME +
            "?useSSL=false&serverTimezone=UTC";

    private static Connection conexion = null;

    public static Connection getConnection() {
        if (conexion == null) {
            try {
                // Cargar el driver MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");
                // Conectar a la base de datos
                conexion = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
                System.out.println("✅ Conexión exitosa a RDS MySQL");
            } catch (ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null, "❌ Driver JDBC no encontrado: " + e.getMessage());
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "❌ Error al conectar con la base de datos: " + e.getMessage());
            }
        }
        return conexion;
    }
}
>>>>>>> 47bfbfd1a28da7c6a1658f9867da8742a7763a96
