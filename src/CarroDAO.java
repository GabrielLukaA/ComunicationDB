import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

public class CarroDAO implements ICRUD<Carro, Integer> {
    private Connection con;
    private String comandoSQL;

    public CarroDAO(Connection con) {
        this.con = con;
    }

    @Override
    public void inserir( Carro obj) {
         comandoSQL = "INSERT INTO carro values(?,?,?,?,?,?);";
        try (PreparedStatement statement = con.prepareStatement(comandoSQL)) {
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
    public void atualizar( Carro obj) {
        comandoSQL = "UPDATE carro SET preco=?, modelo=? where id=?";
        try (PreparedStatement statement = con.prepareStatement(comandoSQL)) {
            statement.setDouble(1, obj.getPreco());
            statement.setString(2, obj.getModelo());
            statement.setInt(3, obj.getId());
            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deletar( Integer id) {
        comandoSQL = "DELETE FROM carro where id=?";
        try (PreparedStatement statement = con.prepareStatement(comandoSQL)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Carro buscarUm( Integer id) {
        comandoSQL = "SELECT * FROM carro where id=?";
        try (PreparedStatement statement = con.prepareStatement(comandoSQL)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int ano = resultSet.getInt("ano");
                String modelo = resultSet.getString("modelo");
                String marca = resultSet.getString("marca");
                String cor = resultSet.getString("cor");
                double preco = resultSet.getDouble("preco");
                return new Carro(id, marca, cor, modelo, preco, ano);
            }
            throw new NoSuchElementException();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<Carro> buscarTodos() {
        comandoSQL = "SELECT * FROM carro";
        Set<Carro> carros = new HashSet<>();
        try (PreparedStatement statement = con.prepareStatement(comandoSQL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int ano = resultSet.getInt("ano");
                String modelo = resultSet.getString("modelo");
                String marca = resultSet.getString("marca");
                String cor = resultSet.getString("cor");
                double preco = resultSet.getDouble("preco");
                int id = resultSet.getInt("id");
                carros.add(new Carro(id, marca, cor, modelo, preco, ano));
            }

            return carros;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
