import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

public class QuickSort implements OrdenadorDeProdutos {
    @Override
    public void ordenar(ArrayList<ProdutoFormatado> produtos, Comparator<? super Produto> comparador) {
        ordenaQuickSort(produtos, 0, produtos.size() - 1, comparador);
    }

    private void ordenaQuickSort(ArrayList<ProdutoFormatado> produtos, int ini, int fim, Comparator<? super Produto> comparador) {
        if (ini < fim) {
            int q = particiona(produtos, ini, fim, comparador);
            ordenaQuickSort(produtos, ini, q, comparador);
            ordenaQuickSort(produtos, q + 1, fim, comparador);
        }
    }

    private int particiona(ArrayList<ProdutoFormatado> produtos, int ini, int fim, Comparator<? super Produto> comparador) {
        ProdutoFormatado x = produtos.get(ini);
        int i = ini - 1;
        int j = fim + 1;

        while (true) {
            do {
                j--;
            } while (comparador.compare(produtos.get(j).getProduto(), x.getProduto()) > 0);

            do {
                i++;
            } while (comparador.compare(produtos.get(i).getProduto(), x.getProduto()) < 0);

            if (i < j) {
                Collections.swap(produtos, i, j);
            } else {
                return j;
            }
        }
    }
}
