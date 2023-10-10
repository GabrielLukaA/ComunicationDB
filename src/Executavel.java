import com.mysql.cj.jdbc.ConnectionImpl;

import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public class Executavel {
    public static void main(String[] args) {
//        Connection connection = null;

        Usuario usuario =
                new Usuario(68, "Luka", "luka", 17,
                        new Carro(2, "Porsche", "prata", "Panamera", 1000000.0, 2018));


        //Usando o try com recurso, automáticamente a connection irá se fechar. Fazendo-se assim desnecessário o uso do connection.close();
        try (ICRUD<Carro, Integer> crudCarro =
                     new CarroDAO();
             ICRUD<Usuario, Integer> crudUsuario =
                     new UsuarioDAO()) {
            //Cadastrando um usuário com um carro

            try {
                crudCarro.buscarUm(usuario.getCarro().getId());
            } catch (NoSuchElementException e) {
                crudCarro.inserir(usuario.getCarro());
            }


            crudUsuario.inserir(usuario);

        } catch (Exception e) {
        throw new RuntimeException(e);
        }




    }


}
