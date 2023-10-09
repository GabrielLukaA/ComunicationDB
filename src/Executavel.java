import com.mysql.cj.jdbc.ConnectionImpl;

import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Executavel {
    public static void main(String[] args) throws SQLException {
        String urlBanco = "jdbc:mysql://localhost:3306/db_integracao"; // Padrão para comunicar.
        // após as duas barras colocar o servidor e a porta. Endereço localhost é 127.0.0.1, depois da porta ainda
        // falta especificar qual banco de dados que a conexão será feita
        String usuario = "root";
        String senha = "root";
//        Connection connection = null;

        //Usando o try com recurso, automáticamente a connection irá se fechar. Fazendo-se assim desnecessário o uso do connection.close();
        try (Connection connection = DriverManager.getConnection(urlBanco, usuario, senha)) {

            System.out.println(connection);
//            inserir(connection, new Usuario(23456, "romario", "romario", 20));

            atualizar(connection, new Usuario(1111, "KKKKKK", "consegui", 4));
            System.out.println(buscarUm(connection, 1111));
//            deletar(connection, 100);

            System.out.println(buscarTodos(connection));
            //Devemos sempre lembrar de fechar a conexão

        } catch (SQLException e) {
            throw new RuntimeException(e);
            // O finally sempre será executado!!!
        }
//
//        finally {
//            connection.close();
//        }


    }

    private static void atualizar(Connection connection, Usuario usuario) {
        try {
            Statement statement = connection.createStatement();
            String comandoSQL = "UPDATE usuario SET nome ='" + usuario.getNome() + "', senha = '" + usuario.getSenha() + "' WHERE id_usuario = " + usuario.getId();
            statement.execute(comandoSQL);
            System.out.println("O usuário atualizado é o " + buscarUm(connection, usuario.getId()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private static Usuario buscarUm(Connection connection, Integer id) {
        String comandoSQL = "SELECT * FROM usuario where id_usuario=" + id;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(comandoSQL);
            // Iterable, ele é pique uma lista, só sabendo o seu próximo, parecido com uma linkedList
            resultSet.next();
            int idade = resultSet.getInt("idade");
            String nome = resultSet.getString("nome");
            String senha = resultSet.getString("senha");
            Usuario usuario = new Usuario(id, nome, senha, idade);
            return usuario;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void deletar(Connection connection, Integer id) {
        try {
            Statement statement = connection.createStatement();
            String comandoSQL = "DELETE from usuario where id_usuario=" + id;
            statement.execute(comandoSQL);
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
