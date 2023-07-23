public class FiltragemIntervalo implements FiltragemStrategy {
        

    @Override
    public boolean verificar(Produto produto, String filtro, String argFiltro) {
        String[] separar = argFiltro.split("/");
        double inferior = Double.parseDouble(separar[0]);
        double superior = Double.parseDouble(separar[1]);
        return inferior <= produto.getPreco() && produto.getPreco() <= superior;
    }

}