public class ProdutoItalicoDecorator extends ProdutoDecorator {
    public ProdutoItalicoDecorator(Produto produto) {
        super(produto);
    }

    @Override
    public String formataParaImpressao() {
        return "<span style=\"font-style:italic\">" + produto.formataParaImpressao() + "</span>";
    }
}