import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class UsuarioDAO extends DAOPadrao<Usuario, Integer> {


    public UsuarioDAO() throws SQLException {
        super("usuario");

    }

    @Override
    public void atualizar(Usuario usuario) {
        comandoSQL = "UPDATE usuario SET nome =? , senha = ?, idade=?, id_carro=? WHERE id = ?";
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
            System.out.println("O usuário atualizado é o " + buscarUm(usuario.getId()));
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
    public Usuario converter(ResultSet resultSet) throws SQLException {
        return new Usuario(resultSet);
    }

}
