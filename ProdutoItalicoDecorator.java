public class ProdutoItalicoDecorator extends ProdutoPadraoDecorator {
    public ProdutoItalicoDecorator(ProdutoFormatado produto) {
        super(produto);
    }

    @Override
    public String formataParaImpressao() {
        return "<span style=\"font-style:italic\">" + super.formataParaImpressao() + "</span>";
    }

    @Override
    public Produto getProduto() {
        return super.getProduto();
    }
}
