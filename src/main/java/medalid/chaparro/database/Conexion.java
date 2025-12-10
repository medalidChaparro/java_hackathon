package medalid.chaparro.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    // Datos de conexión a tu base de datos RDS
    private static final String DB_HOST = "medalid.c5kmcoqcywvu.us-east-1.rds.amazonaws.com";
    private static final String DB_NAME = "inventario_computadoras_db"; // Tu BD de computadoras
    private static final String DB_USER = "admin";
    private static final String DB_PASSWORD = "60512110";

    // URL de conexión completa con parámetros necesarios
    private static final String URL =
            "jdbc:mysql://" + DB_HOST + ":3306/" + DB_NAME +
                    "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    /**
     * Método que devuelve una nueva conexión al servidor MySQL en AWS RDS.
     * Cada vez que se llama, se crea una conexión nueva.
     */
    public static Connection getConnection() {
        try {
            // Cargar el driver JDBC de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Crear y retornar la conexión
            Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
            System.out.println("✅ Conexión exitosa a RDS MySQL");
            return conn;

        } catch (ClassNotFoundException e) {
            System.out.println("❌ No se encontró el driver JDBC: " + e.getMessage());
            return null;

        } catch (SQLException e) {
            System.out.println("❌ Error al conectar a MySQL");
            e.printStackTrace(); // Muestra los detalles del error
            return null;
        }
    }
}