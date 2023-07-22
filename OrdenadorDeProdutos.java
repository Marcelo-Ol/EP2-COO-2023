import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class OrdenadorDeProdutos {

    private ArrayList<Produto> produtos;

    public OrdenadorDeProdutos(ArrayList<Produto> produtos) {
        this.produtos = new ArrayList<>(produtos);
    }

    // Método privado para particionar a lista de produtos
    private int particiona(int ini, int fim, Comparator<Produto> comparador) {
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

    // Método privado para ordenar a lista de produtos usando o algoritmo QuickSort
    private void ordenaQuickSort(int ini, int fim, Comparator<Produto> comparador) {
        if (ini < fim) {
            int q = particiona(ini, fim, comparador);
            ordenaQuickSort(ini, q, comparador);
            ordenaQuickSort(q + 1, fim, comparador);
        }
    }

    // Método privado para ordenar a lista de produtos usando o algoritmo InsertionSort
    private void ordenaInsertionSort(Comparator<Produto> comparador) {
        for (int i = 1; i < produtos.size(); i++) {
            Produto x = produtos.get(i);
            int j = i - 1;

            while (j >= 0 && comparador.compare(produtos.get(j), x) > 0) {
                produtos.set(j + 1, produtos.get(j));
                j--;
            }

            produtos.set(j + 1, x);
        }
    }

    // Método público para ordenar os produtos usando o algoritmo selecionado
    public void ordenar(String algoritmo, String criterio) {
        Comparator<Produto> comparador = null;

        if (criterio.equals(GeradorDeRelatorios.CRIT_DESC_CRESC)) {
            comparador = Comparator.comparing(Produto::getDescricao, String.CASE_INSENSITIVE_ORDER);
        } else if (criterio.equals(GeradorDeRelatorios.CRIT_PRECO_CRESC)) {
            comparador = Comparator.comparingDouble(Produto::getPreco);
        } else if (criterio.equals(GeradorDeRelatorios.CRIT_ESTOQUE_CRESC)) {
            comparador = Comparator.comparingInt(Produto::getQtdEstoque);
        } else {
            throw new IllegalArgumentException("Criterio invalido!");
        }

        if (algoritmo.equals(GeradorDeRelatorios.ALG_QUICKSORT)) {
            ordenaQuickSort(0, produtos.size() - 1, comparador);
        } else if (algoritmo.equals(GeradorDeRelatorios.ALG_INSERTIONSORT)) {
            ordenaInsertionSort(comparador);
        } else {
            throw new IllegalArgumentException("Algoritmo invalido!");
        }
    }

    // Método para retornar a lista de produtos ordenada
    public ArrayList<Produto> getProdutosOrdenados() {
        return produtos;
    }
}
