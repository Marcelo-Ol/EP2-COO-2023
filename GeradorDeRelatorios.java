import java.io.PrintWriter;
import java.io.IOException;
import java.util.*;

public class GeradorDeRelatorios {

    public static final String ALG_INSERTIONSORT = "insertion";
    public static final String ALG_QUICKSORT = "quick";

    public static final String CRIT_DESC_CRESC = "descricao_c";
    public static final String CRIT_DESC_DECRES = "descricao_d"; 
    public static final String CRIT_PRECO_CRESC = "preco_c";
    public static final String CRIT_PRECO_DECRES = "preco_d";
    public static final String CRIT_ESTOQUE_CRESC = "estoque_c";
    public static final String CRIT_ESTOQUE_DECRES = "estoque_d";
    
    public static final String FILTRO_TODOS = "todos";
    public static final String FILTRO_ESTOQUE_MENOR_OU_IQUAL_A = "estoque_menor_igual";
    public static final String FILTRO_CATEGORIA_IGUAL_A = "categoria_igual";

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
        produtos.add(new ProdutoPadrao(3, "Resident Evil 4", "Games", 7, 79.90));
        produtos.add(new ProdutoPadrao(4, "iPhone", "Telefonia", 8, 4999.90));
        produtos.add(new ProdutoPadrao(5, "Calculo I", "Livros", 20, 55.00));
        produtos.add(new ProdutoPadrao(6, "Power Glove", "Games", 3, 499.90));
        produtos.add(new ProdutoPadrao(7, "Microsoft HoloLens", "Informatica", 1, 19900.00));
        produtos.add(new ProdutoPadrao(8, "OpenGL Programming Guide", "Livros", 4, 89.90));
        produtos.add(new ProdutoPadrao(9, "Vectrex", "Games", 1, 799.90));
        produtos.add(new ProdutoPadrao(10, "Carregador iPhone", "Telefonia", 15, 499.90));
        produtos.add(new ProdutoPadrao(11, "Introduction to Algorithms", "Livros", 7, 315.00));
        produtos.add(new ProdutoPadrao(12, "Daytona USA (Arcade)", "Games", 1, 12000.00));
        produtos.add(new ProdutoPadrao(13, "Neuromancer", "Livros", 5, 45.00));
        produtos.add(new ProdutoPadrao(14, "Nokia 3100", "Telefonia", 4, 249.99));
        produtos.add(new ProdutoPadrao(15, "Oculus Rift", "Games", 1, 3600.00));
        produtos.add(new ProdutoPadrao(16, "Trackball Logitech", "Informatica", 1, 250.00));
        produtos.add(new ProdutoPadrao(17, "After Burner II (Arcade)", "Games", 2, 8900.0));
        produtos.add(new ProdutoPadrao(18, "Assembly for Dummies", "Livros", 30, 129.90));
        produtos.add(new ProdutoPadrao(19, "iPhone (usado)", "Telefonia", 3, 3999.90));
        produtos.add(new ProdutoPadrao(20, "Game Programming Patterns", "Livros", 1, 299.90));
        produtos.add(new ProdutoPadrao(21, "Playstation 2", "Games", 10, 499.90));
        produtos.add(new ProdutoPadrao(22, "Carregador Nokia", "Telefonia", 14, 89.00));
        produtos.add(new ProdutoPadrao(23, "Placa Aceleradora Voodoo 2", "Informatica", 4, 189.00));
        produtos.add(new ProdutoPadrao(24, "Stunts", "Games", 3, 19.90));
        produtos.add(new ProdutoPadrao(25, "Carregador Generico", "Telefonia", 9, 30.00));
        produtos.add(new ProdutoPadrao(26, "Monitor VGA 14 polegadas", "Informatica", 2, 199.90));
        produtos.add(new ProdutoPadrao(27, "Nokia N-Gage", "Telefonia", 9, 699.00));
        produtos.add(new ProdutoPadrao(28, "Disquetes Maxell 5.25 polegadas (caixa com 10 unidades)", "Informatica", 23, 49.00));
        produtos.add(new ProdutoPadrao(29, "Alone in The Dark", "Games", 11, 59.00));
        produtos.add(new ProdutoPadrao(30, "The Art of Computer Programming Vol. 1", "Livros", 3, 240.00));
        produtos.add(new ProdutoPadrao(31, "The Art of Computer Programming Vol. 2", "Livros", 2, 200.00));
        produtos.add(new ProdutoPadrao(32, "The Art of Computer Programming Vol. 3", "Livros", 4, 270.00));        
        return produtos;
    }
}
