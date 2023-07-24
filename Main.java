import java.io.IOException;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("Uso:");
            System.out.println("\tjava Main <algoritmo> <critério de ordenação> <critério de filtragem> <parâmetro de filtragem> <opções de formatação>");
            System.out.println("Onde:");
            System.out.println("\talgoritmo: 'quick' ou 'insertion'");
            System.out.println("\tcriterio de ordenação: 'preco_c' ou 'descricao_c' ou 'estoque_c' ou 'preco_d' ou 'descricao_d' ou 'estoque_d' ou 'intervalo_igual' ou 'descricao_igual'");
            System.out.println("\tcriterio de filtragem: 'todos' ou 'estoque_menor_igual' ou 'categoria_igual'");
            System.out.println("\tparâmetro de filtragem: argumentos adicionais necessários para a filtragem");
            System.out.println("\topções de formatação: 'negrito' e/ou 'italico'");
            System.out.println();
            System.exit(1);
        }

        String opcao_algoritmo = args[0];
        String opcao_criterio_ord = args[1];
        String opcao_criterio_filtro = args[2];
        String opcao_parametro_filtro = args[3];
        String nomeArquivoCSV = args[4];

        String[] opcoes_formatacao = new String[2];
        opcoes_formatacao[0] = args.length > 5 ? args[5] : null;
        opcoes_formatacao[1] = args.length > 6 ? args[6] : null;
        int format_flags = GeradorDeRelatorios.FORMATO_PADRAO;

        for (int i = 0; i < opcoes_formatacao.length; i++) {
            String op = opcoes_formatacao[i];
            format_flags |= (op != null ? op.equals("negrito") ? GeradorDeRelatorios.FORMATO_NEGRITO : (op.equals("italico") ? GeradorDeRelatorios.FORMATO_ITALICO : 0) : 0);
        }

        ArrayList<Produto> produtos = GeradorDeRelatorios.carregaProdutosDoCSV(nomeArquivoCSV);
        OrdenadorDeProdutos ordenador;

        Map<String, OrdenadorDeProdutos> estrategiaOrdenacao = new HashMap<>();
        estrategiaOrdenacao.put(GeradorDeRelatorios.ALG_INSERTIONSORT, new InsertionSort());
        estrategiaOrdenacao.put(GeradorDeRelatorios.ALG_QUICKSORT, new QuickSort());

        ordenador = estrategiaOrdenacao.get(opcao_algoritmo);
        if (ordenador == null) {
            System.out.println("Algoritmo de ordenação inválido!");
            System.exit(1);
            return;
        }

        Map<String, CriterioOrdenacao> criteriosOrdenacao = new HashMap<>();
        criteriosOrdenacao.put(GeradorDeRelatorios.CRIT_PRECO_CRESC, new CriterioPrecoCrescente());
        criteriosOrdenacao.put(GeradorDeRelatorios.CRIT_DESC_CRESC, new CriterioDescricaoCrescente());
        criteriosOrdenacao.put(GeradorDeRelatorios.CRIT_ESTOQUE_CRESC, new CriterioEstoqueCrescente());
        criteriosOrdenacao.put(GeradorDeRelatorios.CRIT_PRECO_DECRES, new CriterioPrecoDecrescente());
        criteriosOrdenacao.put(GeradorDeRelatorios.CRIT_DESC_DECRES, new CriterioDescricaoDecrescente());
        criteriosOrdenacao.put(GeradorDeRelatorios.CRIT_ESTOQUE_DECRES, new CriterioEstoqueDecrescente());

        CriterioOrdenacao criterioOrdenacao = criteriosOrdenacao.get(opcao_criterio_ord);
        if (criterioOrdenacao == null) {
            System.out.println("Criterio de ordenação inválido!");
            System.exit(1);
            return;
        }

        Comparator<Produto> comparador = criteriosOrdenacao.get(opcao_criterio_ord).getComparator();

        GeradorDeRelatorios gerador = new GeradorDeRelatorios(
                produtos, opcao_algoritmo, opcao_criterio_ord, opcao_criterio_filtro,
                opcao_parametro_filtro, format_flags, ordenador, comparador
        );

        try {
            gerador.geraRelatorio("saida.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}