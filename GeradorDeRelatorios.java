import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

public class GeradorDeRelatorios {

    public static final String ALG_INSERTIONSORT = "insertion";
    public static final String ALG_QUICKSORT = "quick";

    public static final String CRIT_DESC_CRESC = "descricao_c";
    public static final String CRIT_DESC_DECRES = "descricao_d"; 
    public static final String CRIT_PRECO_CRESC = "preco_c";
    public static final String CRIT_PRECO_DECRES = "preco_d";
    public static final String CRIT_ESTOQUE_CRESC = "estoque_c";
    public static final String CRIT_ESTOQUE_DECRES = "estoque_d";
    
    public static final int FORMATO_PADRAO  = 0b0000;
    public static final int FORMATO_NEGRITO = 0b0001;
    public static final int FORMATO_ITALICO = 0b0010;

    private ArrayList<Produto> produtos;
    private Map<Integer, Map<String, String>> formatoEcor;
    private String algoritmo;
    private String criterio;
    private String filtro;
    private String argFiltro;
    private int format_flags;
    private OrdenadorDeProdutos ordenador;
    private Comparator<Produto> comparador;

    public GeradorDeRelatorios(ArrayList<Produto> produtos, String algoritmo, String criterio, String filtro, String argFiltro, int format_flags, Map<Integer, Map<String, String>> formatoEcor, OrdenadorDeProdutos ordenador, Comparator<Produto> comparador) {
        this.produtos = new ArrayList<>(produtos);
        this.formatoEcor = formatoEcor;
        this.algoritmo = algoritmo;
        this.criterio = criterio;
        this.filtro = filtro;
        this.argFiltro = argFiltro;
        this.format_flags = format_flags;
        this.ordenador = ordenador;
        this.comparador = comparador;
    }


    public void debug() {
        System.out.println("Gerando relatório para ArrayList contendo " + produtos.size() + " produto(s)");
        System.out.println("parametro filtro = '" + argFiltro + "'");
    }

    public void geraRelatorio(String arquivoSaida) throws IOException {
        debug();

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

                Map<String, String> formatoEcorProduto = formatoEcor.get(p.getId());
                if (formatoEcorProduto != null) {
                    String italicoStr = formatoEcorProduto.get("italico");
                    String negritoStr = formatoEcorProduto.get("negrito");
                    String cor = formatoEcorProduto.get("cor");

                    boolean italico = Boolean.parseBoolean(italicoStr);
                    boolean negrito = Boolean.parseBoolean(negritoStr);

                    if (italico) {
                        out.print("<span style=\"font-style:italic\">");
                    }

                    if (negrito) {
                        out.print("<span style=\"font-weight:bold\">");
                    } 

                    if (cor != null && !cor.isEmpty()) {
                        out.print("<span style=\"color:" + cor + "\">");
                    }
                }
                
                out.print(p.formataParaImpressao());

                if (formatoEcorProduto != null) {
                    String cor = formatoEcorProduto.get("cor");
                    if (cor != null && !cor.isEmpty()) {
                        out.print("</span>");
                    }

                    String negritoStr = formatoEcorProduto.get("negrito");
                    if (Boolean.parseBoolean(negritoStr)) {
                        out.print("</span>");
                    } 

                    String italicoStr = formatoEcorProduto.get("italico");
                    if (Boolean.parseBoolean(italicoStr)) {
                        out.print("</span>");
                    }
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

    public static ArrayList<Produto> carregaProdutosDoCSV(String nomeArquivoCSV, Map<Integer, Map<String, String>> formatoEcor) {
        return LeitorCSV.carregaProdutosDoCSV(nomeArquivoCSV, formatoEcor);
    }
}