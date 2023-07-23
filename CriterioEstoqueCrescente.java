import java.util.Comparator;

public class CriterioEstoqueCrescente implements CriterioOrdenacao {
    @Override
    public Comparator<Produto> getComparator() {
        return Comparator.comparingInt(Produto::getQtdEstoque);
    }
}