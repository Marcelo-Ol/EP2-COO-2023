public class ProdutoPadraoFormatado implements ProdutoFormatado {
    private Produto produto;

    public ProdutoPadraoFormatado(Produto produto) {
        this.produto = produto;
    }

    @Override
    public String formataParaImpressao() {
        return produto.formataParaImpressao();
    }

    @Override
    public Produto getProduto() {
        return produto;
    }
}
