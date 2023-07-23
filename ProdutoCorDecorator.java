import java.util.Scanner;

public class ProdutoCorDecorator extends ProdutoDecorator {
    private String cor;

    public ProdutoCorDecorator(Produto produto) {
        super(produto);
    }

    @Override
    public String formataParaImpressao() {
        return "<span style=\"color:" + cor +"\">" + produto.formataParaImpressao() + "</span>";
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getCor() {
        return cor;
    }

    public static void main(String[] args) {
        // Exemplo de uso
        Produto produto = new Produto("Notebook", 1500.00);
        ProdutoCorDecorator decorador = new ProdutoCorDecorator(produto);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite a cor desejada:");
        String cor = scanner.nextLine();

        decorador.setCor(cor);
        System.out.println(decorador.formataParaImpressao());

        scanner.close();
    }
}
