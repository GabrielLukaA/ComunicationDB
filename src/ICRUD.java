import java.sql.Connection;
import java.text.CompactNumberFormat;
import java.util.*;

public interface ICRUD<T, ID> {
    void inserir(T obj);

    void atualizar(T obj);

    void deletar(ID id);

    T buscarUm(ID id);

    Set<T> buscarTodos();

}
