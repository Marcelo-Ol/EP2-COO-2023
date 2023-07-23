

public class FiltroCategoriaIgualAStrategy implements FiltragemStrategy {
    
    @Override
    public boolean verificar(Produto produto, String filtro, String argFiltro) {
        return produto.getCategoria().equalsIgnoreCase(argFiltro);
    }

}