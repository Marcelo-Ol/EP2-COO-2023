import java.util.Comparator;

public class CriterioDescricaoDecrescente implements CriterioOrdenacao {
    @Override
    public Comparator<Produto> getComparator() {
        return Comparator.comparing(Produto::getDescricao, String.CASE_INSENSITIVE_ORDER).reversed();
    }
}