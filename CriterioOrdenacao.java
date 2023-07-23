import java.util.Comparator;

public interface CriterioOrdenacao {
    Comparator<Produto> getComparator();
}
