import java.sql.ResultSet;
import java.sql.SQLException;

public class Usuario {
    private Integer id;
    private String nome;
    private String senha;
    private Integer idade;
    private Carro carro;

    public Usuario(Integer id, String nome, String senha, Integer idade) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.idade = idade;
    }

    public Usuario(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("id_usuario");
        this.nome = resultSet.getString("nome");
        this.senha = resultSet.getString("senha");
        this.idade = resultSet.getInt("idade");
        int idCarro = resultSet.getInt("id_carro");
        if (idCarro != 0) {
            this.carro = new Carro(idCarro);
        }
    }

    public Usuario(Integer id, String nome, String senha, Integer idade, Carro carro) {
        this(id, nome, senha, idade);
        this.carro = carro;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }

    public Integer getIdade() {
        return idade;
    }

    @Override
    public String toString() {
        return "Usuario -" +
                "id:" + id +
                " -  nome:" + nome +
                " - senha:" + senha +
                " -  idade: " + idade;
    }
}
