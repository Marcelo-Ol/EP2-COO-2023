import java.util.ArrayList;
import java.util.Comparator;

public interface OrdenadorDeProdutos {
    void ordenar(ArrayList<ProdutoFormatado> produtos, Comparator<? super Produto> comparador);
}
