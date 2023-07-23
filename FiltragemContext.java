import java.util.HashMap;
import java.util.Map;

public class FiltragemContext {
    private Map<String, FiltragemStrategy> strategies = new HashMap<>();
    public static final String FILTRO_TODOS = "todos";
    public static final String FILTRO_ESTOQUE_MENOR_OU_IQUAL_A = "estoque_menor_igual";
    public static final String FILTRO_CATEGORIA_IGUAL_A = "categoria_igual"; 
    
    public FiltragemContext() {
        configurarEstrategias();
    }

    public void configurarEstrategias() {
        // Adicionar as estratégias aqui
        addStrategy(FILTRO_TODOS, new FiltroTodosStrategy());
        addStrategy(FILTRO_ESTOQUE_MENOR_OU_IQUAL_A, new FiltroEstoqueStrategy());
        addStrategy(FILTRO_CATEGORIA_IGUAL_A, new FiltroCategoriaIgualAStrategy());
        // Adicione outras estratégias conforme necessário
    }
    
    public  void addStrategy(String filtro, FiltragemStrategy strategy) {
        strategies.put(filtro, strategy);
    }

    public boolean filtrar(Produto produto, String filtro, String argFiltro) {
        FiltragemStrategy strategy = strategies.get(filtro);
        if (strategy == null) {
            throw new IllegalArgumentException("Filtro invalido! : " + filtro);
        }
        return strategy.verificar(produto,filtro, argFiltro);
    }
}