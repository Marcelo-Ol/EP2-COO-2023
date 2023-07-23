public class FiltragemSubString implements FiltragemStrategy {
    
    @Override
    public boolean verificar(Produto produto, String filtro, String argFiltro) {
        String descricaoProduto = produto.getDescricao().toLowerCase();
        String argFiltroLowerCase = argFiltro.toLowerCase();
        return descricaoProduto.contains(argFiltroLowerCase);
    }
}