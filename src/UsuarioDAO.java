import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class UsuarioDAO implements ICRUD<Usuario, Integer> {

    private Connection connection;
    private String comandoSQL;

    public UsuarioDAO(Connection con) {
        this.connection = con;
    }

    @Override
    public void atualizar( Usuario usuario) {
        comandoSQL = "UPDATE usuario SET nome =? , senha = ?, idade=?, id_carro=? WHERE id_usuario = ?";
        try (PreparedStatement statement = connection.prepareStatement(comandoSQL);) {

            statement.setString(1, usuario.getNome());
            statement.setString(2, usuario.getSenha());
            statement.setInt(3, usuario.getIdade());

            try {
                statement.setInt(4, usuario.getCarro().getId());
            } catch (NullPointerException e) {
                statement.setNull(4, 0);
            }
            statement.setInt(5, usuario.getId());
            statement.execute();
            System.out.println("O usuário atualizado é o " + buscarUm( usuario.getId()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Usuario buscarUm( Integer id) {
        comandoSQL = "SELECT * FROM usuario where id_usuario= ?";
        try (PreparedStatement statement = connection.prepareStatement(comandoSQL);) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            // Iterable, ele é pique uma lista, só sabendo o seu próximo, parecido com uma linkedList
            resultSet.next();
            int idade = resultSet.getInt("idade");
            String nome = resultSet.getString("nome");
            String senha = resultSet.getString("senha");
            Integer idCarro = resultSet.getInt("id_carro");
            Usuario usuario = new Usuario(id, nome, senha, idade, new Carro(idCarro));
            return usuario;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deletar( Integer id) {
        comandoSQL = "DELETE from usuario where id_usuario= ?";
        try (PreparedStatement statement = connection.prepareStatement(comandoSQL);) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void inserir(Usuario usuario) {
        comandoSQL = "INSERT INTO usuario " +
                "VALUES(?,?,?,?,?);";
//        String comandoSQL = "INSERT INTO usuario VALUES(" + usuario.getId() + ",'" + usuario.getNome() + "','" + usuario.getSenha() + "'," + usuario.getIdade() + ");";

        try (PreparedStatement statement = connection.prepareStatement(comandoSQL);) {
            statement.setInt(1, usuario.getId());
            statement.setString(2, usuario.getNome());
            statement.setString(3, usuario.getSenha());
            statement.setInt(4, usuario.getIdade());
            try {
                statement.setInt(5, usuario.getCarro().getId());
            } catch (NullPointerException e) {
                statement.setNull(5, 0);
            }
//            String comandoSQL = "INSERT INTO usuario VALUES(2, 'user', 'user',0)";  Criando estáticamente
            statement.execute();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<Usuario> buscarTodos() {
        comandoSQL = "SELECT * FROM usuario;";
        try (PreparedStatement statement = connection.prepareStatement(comandoSQL);) {
            ResultSet resultSet = statement.executeQuery();
            Set<Usuario> listaUsers = new HashSet<>();
            ICRUD<Carro, Integer> icrudCarro = new CarroDAO(connection);
            // Iterable, ele é pique uma lista, só sabendo o seu próximo, parecido com uma linkedList
            while (resultSet.next()) {
                //next() muda o resultado para o próximo e ainda serve como um booleano
                // first is nulo
                int id = resultSet.getInt("id_usuario");
                int idade = resultSet.getInt("idade");
                String nome = resultSet.getString("nome");
                String senha = resultSet.getString("senha");
                Integer idCarro = resultSet.getInt("id_carro");
                Usuario usuario = new Usuario(id, nome, senha, idade, new Carro(idCarro));
                // jeito melhor pegando todos os dados =  icrudCarro.buscarUm(connection, idCarro);
                listaUsers.add(usuario);
            }
            return listaUsers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
