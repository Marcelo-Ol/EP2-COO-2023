public abstract class ProdutoDecorator implements Produto {
    protected Produto produto;

    public ProdutoDecorator(Produto produto) {
        this.produto = produto;
    }

    @Override
    public String getDescricao() {
        return produto.getDescricao();
    }

    @Override
    public double getPreco() {
        return produto.getPreco();
    }

    @Override
    public int getQtdEstoque() {
        return produto.getQtdEstoque();
    }

    @Override
    public String getCategoria() {
        return produto.getCategoria();
    }

    @Override
    public abstract String formataParaImpressao();

    @Override
    public int getId() {
        return produto.getId();
    }

    @Override
    public void setPreco(double preco) {
        produto.setPreco(preco);
    }

    @Override
    public void setQtdEstoque(int qtdEstoque) {
        produto.setQtdEstoque(qtdEstoque);
    }
}