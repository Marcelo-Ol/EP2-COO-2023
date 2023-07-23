import java.util.Comparator;

public class CriterioPrecoDecrescente implements CriterioOrdenacao {
    @Override
    public Comparator<Produto> getComparator() {
        return Comparator.comparingDouble(Produto::getPreco).reversed();
    }
}