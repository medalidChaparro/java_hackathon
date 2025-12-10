package medalid.chaparro;

import medalid.chaparro.database.Conexion;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection conn = Conexion.getConnection();
        if (conn != null) {
            System.out.println("¡Conexión lista!");
        } else {
            System.out.println("Error en la conexión.");
        }
    }
}
