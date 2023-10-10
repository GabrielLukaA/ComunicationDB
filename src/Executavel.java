import com.mysql.cj.jdbc.ConnectionImpl;

import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public class Executavel {
    public static void main(String[] args) throws SQLException {
        String urlBanco = "jdbc:mysql://localhost:3306/db_integracao"; // Padrão para comunicar.
        // após as duas barras colocar o servidor e a porta. Endereço localhost é 127.0.0.1, depois da porta ainda
        // falta especificar qual banco de dados que a conexão será feita
        String username = "root";
        String senha = "root";
//        Connection connection = null;

        //Usando o try com recurso, automáticamente a connection irá se fechar. Fazendo-se assim desnecessário o uso do connection.close();
        try (Connection connection = DriverManager.getConnection(urlBanco, username, senha)) {

            //Cadastrando um usuário com um carro
            Usuario usuario = new Usuario(668, "Luka", "luka", 17,
                    new Carro(2, "Porsche", "prata", "Panamera", 1000000.0, 2018));
            ICRUD<Carro, Integer> crudCarro = new CarroDAO(connection);

            try {
                crudCarro.buscarUm( usuario.getCarro().getId());
            } catch (NoSuchElementException e) {
                crudCarro.inserir( usuario.getCarro());

            }
            ICRUD<Usuario, Integer> crudUsuario = new UsuarioDAO(connection);
            crudUsuario.inserir( usuario);

            //Cadastrando um usuário sem um carro
            Usuario usuario2 = new Usuario(298, "Abacate", "aaaa", 17);
            ICRUD<Carro, Integer> crudCarro2 = new CarroDAO(connection);

            try {
                crudCarro2.buscarUm( usuario2.getCarro().getId());
            } catch (NoSuchElementException e) {
                crudCarro2.inserir( usuario2.getCarro());

            } catch (NullPointerException ignore) {

            }
            ICRUD<Usuario, Integer> crudUsuario2 = new UsuarioDAO(connection);
            crudUsuario2.inserir(usuario2);
            //Deletar  carro
            ICRUD<Carro, Integer> crudCarro3 = new CarroDAO(connection);
            int idCarroDeletar = 1;ICRUD<Usuario, Integer> crudUsuario3 = new UsuarioDAO(connection);
            Set<Usuario> usuarios = crudUsuario3.buscarTodos();
            for (Usuario usuarioFor : usuarios) {

                try {
                    if (usuarioFor.getCarro().getId() == idCarroDeletar) {
                        usuarioFor.setCarro(null);
                        crudUsuario3.atualizar( usuarioFor);
                    }
                } catch (NullPointerException ignore) {

                }

            }
            crudCarro3.deletar(1);
            try {
                usuario = crudUsuario3.buscarUm(298);
                System.out.println("O usuário " + usuario + " E seu carro: " + crudCarro3.buscarUm(usuario.getCarro().getId()));
            } catch (NoSuchElementException e) {
                System.out.println(usuario + "\n e esse usuário não possui carro.");
            }


//
//            System.out.println(connection);
////            inserir( new Usuario(23456, "romario", "romario", 20));
////
//            atualizar( new Usuario(2, "KKKKKK", "consegui", 4));
////            System.out.println(buscarUm( 1111));
////            deletar( 922);
//
//            System.out.println(buscarTodos(connection));
//            //Devemos sempre lembrar de fechar a conexão

        } catch (SQLException e) {
            throw new RuntimeException(e);
            // O finally sempre será executado!!!
        }
//
//        finally {
//            connection.close();
//        }


    }


}
