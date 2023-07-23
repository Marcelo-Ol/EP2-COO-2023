public class FiltragemSubString implements FiltragemStrategy {
    
    @Override
    public boolean verificar(Produto produto, String filtro, String argFiltro) {
        double precoProduto = produto.getPreco();
        return precoProduto >= precoMinimo && precoProduto <= precoMaximo;
    }
}