import com.mysql.cj.jdbc.ConnectionImpl;

import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Executavel {
    public static void main(String[] args) {
        String urlBanco = "jdbc:mysql://localhost:3306/db_integracao"; // Padrão para comunicar.
        // após as duas barras colocar o servidor e a porta. Endereço localhost é 127.0.0.1, depois da porta ainda
        // falta especificar qual banco de dados que a conexão será feita
        String usuario = "root";
        String senha = "root";
        try {
            Connection connection = DriverManager.getConnection(urlBanco, usuario, senha);
            System.out.println(connection);
            inserir(connection, new Usuario(9452, "romario", "romario", 20));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    private static void inserir(Connection connection, Usuario usuario) {
        try {

            Statement statement = connection.createStatement();
            String comandoSQL = "INSERT INTO usuario VALUES(" + usuario.getId() + ",'" + usuario.getNome() + "','" + usuario.getSenha() + "'," + usuario.getIdade() + ");";
//            String comandoSQL = "INSERT INTO usuario VALUES(2, 'user', 'user',0)";  Criando estáticamente
            statement.execute(comandoSQL);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private static Set<Usuario> buscarTodos(Connection connection) {
        String comandoSQL = "SELECT * FROM usuario;";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(comandoSQL);
            Set<Usuario> listaUsers = new HashSet<>();
            // Iterable, ele é pique uma lista, só sabendo o seu próximo, parecido com uma linkedList
            while (resultSet.next()) {
                //next() muda o resultado para o próximo e ainda serve como um booleano
                // first is nulo
                int id = resultSet.getInt("id_usuario");
                int idade = resultSet.getInt("idade");
                String nome = resultSet.getString("nome");
                String senha = resultSet.getString("senha");
                Usuario usuario = new Usuario(id, nome, senha, idade);
                listaUsers.add(usuario);
            }
            return listaUsers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
