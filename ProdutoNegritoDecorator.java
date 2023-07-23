public class ProdutoNegritoDecorator extends ProdutoPadraoDecorator {
    public ProdutoNegritoDecorator(ProdutoFormatado produto) {
        super(produto);
    }

    @Override
    public String formataParaImpressao() {
        return "<span style=\"font-weight:bold\">" + super.formataParaImpressao() + "</span>";
    }

    @Override
    public Produto getProduto() {
        return super.getProduto();
    }
}
