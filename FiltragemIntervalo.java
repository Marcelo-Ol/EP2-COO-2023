public class FiltragemIntervalo implements FiltragemStrategy {
    
    @Override
    public boolean verificar(Produto produto, String filtro, String argFiltro) {
        return produto.getPreco() == Integer.parseInt(argFiltro);
    }

}