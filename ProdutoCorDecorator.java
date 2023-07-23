public class ProdutoCorDecorator extends ProdutoDecorator {
    public ProdutoCorDecorator(Produto produto) {
        super(produto);
    }

    @Override
    public String formataParaImpressao() {
        return "<span style=\"color:red\">" + produto.formataParaImpressao() + "</span>";
    }
}
