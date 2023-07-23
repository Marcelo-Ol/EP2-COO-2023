public abstract class ProdutoPadraoDecorator implements ProdutoFormatado {
    protected ProdutoFormatado produto;

    public ProdutoPadraoDecorator(ProdutoFormatado produto) {
        this.produto = produto;
    }

    @Override
    public String formataParaImpressao() {
        return produto.formataParaImpressao();
    }

    @Override
    public Produto getProduto() {
        return produto.getProduto();
    }
}
