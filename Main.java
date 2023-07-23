import java.io.IOException;
import java.util.*;

public class Main {

    private static final int FORMATO_PADRAO = 0b0000;
    private static final int FORMATO_NEGRITO = 0b0001;
    private static final int FORMATO_ITALICO = 0b0010;

    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("Uso:");
            System.out.println("\tjava Main <algoritmo> <critério de ordenação> <critério de filtragem> <parâmetro de filtragem> <opções de formatação>");
            System.out.println("Onde:");
            System.out.println("\talgoritmo: 'quick' ou 'insertion'");
            System.out.println("\tcriterio de ordenação: 'preco_c' ou 'descricao_c' ou 'estoque_c'");
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

        String[] opcoes_formatacao = new String[2];
        opcoes_formatacao[0] = args.length > 4 ? args[4] : null;
        opcoes_formatacao[1] = args.length > 5 ? args[5] : null;
        int formato = FORMATO_PADRAO;

        for (int i = 0; i < opcoes_formatacao.length; i++) {
            String op = opcoes_formatacao[i];
            formato |= (op != null ? op.equals("negrito") ? FORMATO_NEGRITO : (op.equals("italico") ? FORMATO_ITALICO : 0) : 0);
        }

        ArrayList<Produto> produtos = GeradorDeRelatorios.carregaProdutos();
        OrdenadorDeProdutos ordenador;

        if (opcao_algoritmo.equals(GeradorDeRelatorios.ALG_INSERTIONSORT)) {
            ordenador = new InsertionSort();
        } else if (opcao_algoritmo.equals(GeradorDeRelatorios.ALG_QUICKSORT)) {
            ordenador = new QuickSort();
        } else {
            System.out.println("Algoritmo de ordenação inválido!");
            System.exit(1);
            return;
        }

        ArrayList<ProdutoFormatado> produtosFormatados = aplicarFormatacao(produtos, formato);

        // Cria uma nova instância do GeradorDeRelatorios com os produtos já formatados
        GeradorDeRelatorios gerador = new GeradorDeRelatorios(produtosFormatados, opcao_algoritmo, opcao_criterio_ord, opcao_criterio_filtro, opcao_parametro_filtro);

        try {
            gerador.geraRelatorio("saida.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para aplicar formatação aos produtos
    private static ArrayList<ProdutoFormatado> aplicarFormatacao(ArrayList<Produto> produtos, int formato) {
        ArrayList<ProdutoFormatado> produtosFormatados = new ArrayList<>();
        for (Produto produto : produtos) {
            ProdutoFormatado produtoFormatado = new ProdutoPadraoFormatado(produto);

            if ((formato & FORMATO_ITALICO) > 0) {
                produtoFormatado = new ProdutoItalicoDecorator(produtoFormatado);
            }

            if ((formato & FORMATO_NEGRITO) > 0) {
                produtoFormatado = new ProdutoNegritoDecorator(produtoFormatado);
            }

            produtosFormatados.add(produtoFormatado);
        }
        return produtosFormatados;
    }
}
