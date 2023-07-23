public class FiltroEstoqueStrategy implements FiltragemStrategy {
    
    @Override
    public boolean verificar(Produto produto, String filtro, String argFiltro) {
        return produto.getQtdEstoque() <= Integer.parseInt(argFiltro);
    }

}