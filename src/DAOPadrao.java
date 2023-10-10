import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

public abstract class DAOPadrao<T, ID> implements ICRUD<T, ID> {
    protected Connection connection;
    protected String comandoSQL;
    private String tabela;

    public DAOPadrao( String tabela) throws SQLException {
        this.connection = Banco.conectar();
        this.tabela = tabela;
    }

    public Connection getConnection() {
        return connection;
    }

    public String getComandoSQL() {
        return comandoSQL;
    }

    public void setComandoSQL(String comandoSQL) {
        this.comandoSQL = comandoSQL;
    }

    public Set<T> buscarTodos() {
        comandoSQL = "SELECT * FROM " + tabela + ";";
        try (PreparedStatement statement = connection.prepareStatement(comandoSQL);) {
            ResultSet resultSet = statement.executeQuery();
            Set<T> lista = new HashSet<>();
            // Iterable, ele é pique uma lista, só sabendo o seu próximo, parecido com uma linkedList
            while (resultSet.next()) {
                //next() muda o resultado para o próximo e ainda serve como um booleano
                // first is nulo
                lista.add(converter(resultSet));
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public void deletar(Integer id) {
        comandoSQL = "DELETE FROM " + tabela + " where id = ?";
        try (PreparedStatement statement = connection.prepareStatement(comandoSQL)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public T buscarUm(Integer id) {
        comandoSQL = "SELECT * FROM " + tabela + " where id=?";
        try (PreparedStatement statement = connection.prepareStatement(comandoSQL)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return converter(resultSet);
            }
            throw new NoSuchElementException();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {
        this.connection.close();
    }

    public abstract T converter(ResultSet resultSet) throws SQLException;

}
