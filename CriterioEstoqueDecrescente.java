import java.util.Comparator;

public class CriterioEstoqueDecrescente implements CriterioOrdenacao {
    @Override
    public Comparator<Produto> getComparator() {
        return Comparator.comparingInt(Produto::getQtdEstoque).reversed();
    }
}
