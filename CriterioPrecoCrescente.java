import java.util.Comparator;

public class CriterioPrecoCrescente implements CriterioOrdenacao {
    @Override
    public Comparator<Produto> getComparator() {
        return Comparator.comparingDouble(Produto::getPreco);
    }
}