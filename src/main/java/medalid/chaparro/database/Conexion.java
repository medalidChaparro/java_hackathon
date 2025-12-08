package medalid.chaparro.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexion {

    // Datos de tu RDS
    private static final String DB_HOST = "medalid.c5kmcoqcywvu.us-east-1.rds.amazonaws.com";
    private static final String DB_NAME = "inventario_computadoras_db";
    private static final String DB_USER = "admin";
    private static final String DB_PASSWORD = "60512110";

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