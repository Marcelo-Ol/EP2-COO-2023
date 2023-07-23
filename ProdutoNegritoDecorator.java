public class ProdutoNegritoDecorator extends ProdutoDecorator {
    public ProdutoNegritoDecorator(Produto produto) {
        super(produto);
    }

    @Override
    public String formataParaImpressao() {
        return "<span style=\"font-weight:bold\">" + produto.formataParaImpressao() + "</span>";
    }
}