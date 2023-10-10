import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CarroDAO extends DAOPadrao<Carro, Integer> {

    @Override
    public Carro converter(ResultSet resultSet) throws SQLException {
        return new Carro(resultSet);
    }

    public CarroDAO() throws SQLException {
        super("carro");
    }


    @Override
    public void inserir(Carro obj) {
        comandoSQL = "INSERT INTO carro values(?,?,?,?,?,?);";
        try (PreparedStatement statement = connection.prepareStatement(comandoSQL)) {
            statement.setInt(1, obj.getId());
            statement.setString(2, obj.getMarca());
            statement.setString(3, obj.getModelo());
            statement.setInt(4, obj.getAno());
            statement.setString(5, obj.getCor());
            statement.setDouble(6, obj.getPreco());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void atualizar(Carro obj) {
        comandoSQL = "UPDATE carro SET preco=?, modelo=? where id=?";
        try (PreparedStatement statement = connection.prepareStatement(comandoSQL)) {
            statement.setDouble(1, obj.getPreco());
            statement.setString(2, obj.getModelo());
            statement.setInt(3, obj.getId());
            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
