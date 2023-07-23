import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class QuickSort implements OrdenadorDeProdutos {
    @Override
    public void ordenar(ArrayList<Produto> produtos, Comparator<Produto> comparador) {
        ordenaQuickSort(produtos, 0, produtos.size() - 1, comparador);
    }

    private void ordenaQuickSort(ArrayList<Produto> produtos, int ini, int fim, Comparator<Produto> comparador) {
        if (ini < fim) {
            int q = particiona(produtos, ini, fim, comparador);
            ordenaQuickSort(produtos, ini, q, comparador);
            ordenaQuickSort(produtos, q + 1, fim, comparador);
        }
    }

    private int particiona(ArrayList<Produto> produtos, int ini, int fim, Comparator<Produto> comparador) {
        Produto x = produtos.get(ini);
        int i = ini - 1;
        int j = fim + 1;

        while (true) {
            do {
                j--;
            } while (comparador.compare(produtos.get(j), x) > 0);

            do {
                i++;
            } while (comparador.compare(produtos.get(i), x) < 0);

            if (i < j) {
                Collections.swap(produtos, i, j);
            } else {
                return j;
            }
        }
    }
}
