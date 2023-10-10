import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Banco {

    private static final String URL_BANCO = "jdbc:mysql://localhost:3306/db_integracao"; // Padrão para comunicar.
    // após as duas barras colocar o servidor e a porta. Endereço localhost é 127.0.0.1, depois da porta ainda
    // falta especificar qual banco de dados que a conexão será feita
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";


    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(
                URL_BANCO,USERNAME,PASSWORD);
    }


}
