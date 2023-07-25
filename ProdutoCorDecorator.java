import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ProdutoCorDecorator extends ProdutoDecorator {
    private static String cor;

    public ProdutoCorDecorator(Produto produto) {
        super(produto);
    }

    @Override
    public String formataParaImpressao() {
        return "<span style=\"color:" + cor + "\">" + produto.formataParaImpressao() + "</span>";
    }

    public static void main(String[] args){
        BufferedReader br = null;
        String linha = "";

        try{

            br = new BufferedReader(new FileReader("produtos.csv"));

            while ((linha = br.readLine()) != null){
                
                String[] produtos = linha.split(",");

                cor = produtos[produtos.length-1];
            }

        } catch (FileNotFoundException e){

            e.printStackTrace();

        } catch (IOException e){

            e.printStackTrace();

        } finally {

            if(br != null){

                try{
                    
                    br.close();
                    
                } catch (IOException e){

                    e.printStackTrace();
                }
            }
        }
    }
}
