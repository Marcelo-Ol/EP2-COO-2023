import java.util.ArrayList;
import java.util.Comparator;

public interface OrdenadorDeProdutos {
    void ordenar(ArrayList<Produto> produtos, Comparator<Produto> comparador);
}
