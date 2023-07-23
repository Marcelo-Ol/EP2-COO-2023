
public class FiltroTodosStrategy implements FiltragemStrategy {
    
    @Override
    public boolean verificar(Produto produto, String filtro, String argFiltro) {
        return true;
    }

}
