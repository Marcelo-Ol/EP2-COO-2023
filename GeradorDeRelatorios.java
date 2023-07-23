import java.io.PrintWriter;
import java.io.IOException;
import java.util.*;

public class GeradorDeRelatorios {

    public static final String ALG_INSERTIONSORT = "insertion";
    public static final String ALG_QUICKSORT = "quick";

    public static final String CRIT_DESC_CRESC = "descricao_c";
    public static final String CRIT_DESC_DECRES = "descricao_d"; // Novo critério de ordenação decrescente
    public static final String CRIT_PRECO_CRESC = "preco_c";
    public static final String CRIT_PRECO_DECRES = "preco_d"; // Novo critério de ordenação decrescente
    public static final String CRIT_ESTOQUE_CRESC = "estoque_c";
    public static final String CRIT_ESTOQUE_DECRES = "estoque_d"; // Novo critério de ordenação decrescente
    
    public static final String FILTRO_TODOS = "todos";
    public static final String FILTRO_ESTOQUE_MENOR_OU_IQUAL_A = "estoque_menor_igual";
    public static final String FILTRO_CATEGORIA_IGUAL_A = "categoria_igual";

	public static final String FILTRO_DESCRICAO_IGUAL_A = "descricao_igual"; 
    public static final String FILTRO_INTERVALO_IGUAL_A = "intervalo_igual"; 
    // operador bit a bit "ou" pode ser usado para combinar mais de  
	// um estilo de formatacao simultaneamente (veja como no main)
	public static final int FORMATO_PADRAO  = 0b0000;
	public static final int FORMATO_NEGRITO = 0b0001;
	public static final int FORMATO_ITALICO = 0b0010;

    private ArrayList<Produto> produtos;
    private String algoritmo;
    private String criterio;
    private String filtro;
    private String argFiltro;
    private int format_flags;

    public GeradorDeRelatorios(ArrayList<Produto> produtos, String algoritmo, String criterio, String filtro, String argFiltro, int format_flags){
        this.produtos = new ArrayList<>(produtos);
        this.algoritmo = algoritmo;
        this.criterio = criterio;
        this.filtro = filtro;
        this.argFiltro = argFiltro;
        this.format_flags = format_flags;
    }

    public void debug() {
        System.out.println("Gerando relatório para ArrayList contendo " + produtos.size() + " produto(s)");
        System.out.println("parametro filtro = '" + argFiltro + "'");
    }

    public void geraRelatorio(String arquivoSaida) throws IOException {
        debug();

        Comparator<Produto> comparador = null;

        if (criterio.equals(CRIT_DESC_CRESC)) {
            comparador = Comparator.comparing(Produto::getDescricao, String.CASE_INSENSITIVE_ORDER);
        } else if (criterio.equals(CRIT_DESC_DECRES)) {
            comparador = Comparator.comparing(Produto::getDescricao, String.CASE_INSENSITIVE_ORDER).reversed();
        } else if (criterio.equals(CRIT_PRECO_CRESC)) {
            comparador = Comparator.comparingDouble(Produto::getPreco);
        } else if (criterio.equals(CRIT_PRECO_DECRES)) {
            comparador = Comparator.comparingDouble(Produto::getPreco).reversed();
        } else if (criterio.equals(CRIT_ESTOQUE_CRESC)) {
            comparador = Comparator.comparingInt(Produto::getQtdEstoque);
        } else if (criterio.equals(CRIT_ESTOQUE_DECRES)) {
            comparador = Comparator.comparingInt(Produto::getQtdEstoque).reversed();
        } else {
            throw new IllegalArgumentException("Criterio invalido!");
        }

        OrdenadorDeProdutos ordenador;

        if (algoritmo.equals(ALG_INSERTIONSORT)) {
            ordenador = new InsertionSort();
        } else if (algoritmo.equals(ALG_QUICKSORT)) {
            ordenador = new QuickSort();
        } else {
            throw new IllegalArgumentException("Algoritmo de ordenação inválido!");
        }

        ordenador.ordenar(produtos, comparador);

        PrintWriter out = new PrintWriter(arquivoSaida);

        out.println("<!DOCTYPE html><html>");
        out.println("<head><title>Relatorio de produtos</title></head>");
        out.println("<body>");
        out.println("Relatorio de Produtos:");
        out.println("<ul>");

        int count = 0;
        FiltragemContext context = new FiltragemContext(); 
        for (int i = 0; i < produtos.size(); i++) {
            Produto p = produtos.get(i);
            boolean selecionado = context.filtrar(p, filtro, argFiltro);

            if (selecionado) {
                out.print("<li>");

                if ((format_flags & FORMATO_ITALICO) > 0) {
                    out.print("<span style=\"font-style:italic\">");
                }

                if ((format_flags & FORMATO_NEGRITO) > 0) {
                    out.print("<span style=\"font-weight:bold\">");
                } 
            
                out.print(p.formataParaImpressao());

                if ((format_flags & FORMATO_NEGRITO) > 0) {
                    out.print("</span>");
                } 

                if ((format_flags & FORMATO_ITALICO) > 0) {
                    out.print("</span>");
                }

                out.println("</li>");
                count++;
            }
        }

        out.println("</ul>");
        out.println(count + " produtos listados, de um total de " + produtos.size() + ".");
        out.println("</body>");
        out.println("</html>");

        out.close();
    }

    public static ArrayList<Produto> carregaProdutos() {
        ArrayList<Produto> produtos = new ArrayList<>();

        produtos.add(new ProdutoPadrao(1, "O Hobbit", "Livros", 2, 34.90));
        produtos.add(new ProdutoPadrao(2, "Notebook Core i7", "Informatica", 5, 1999.90));
        // Mais produtos adicionados...

        return produtos;
    }
}
