import java.util.ArrayList;
import java.util.Comparator;

public class InsertionSort implements OrdenadorDeProdutos {
    @Override
    public void ordenar(ArrayList<ProdutoFormatado> produtos, Comparator<? super Produto> comparador) {
        for (int i = 1; i < produtos.size(); i++) {
            ProdutoFormatado x = produtos.get(i);
            int j = i - 1;

            while (j >= 0 && comparador.compare(produtos.get(j).getProduto(), x.getProduto()) > 0) {
                produtos.set(j + 1, produtos.get(j));
                j--;
            }

            produtos.set(j + 1, x);
        }
    }
}
