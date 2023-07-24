import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ProdutoCorDecorator extends ProdutoDecorator {
    private String cor;

    public ProdutoCorDecorator(Produto produto) {
        super(produto);
    }

    @Override
    public String formataParaImpressao() {
        return "<span style=\"color" + cor + "\">" + produto.formataParaImpressao() + "</span>";
    }

    public static void main(String[] args){
        ProdutoCorDecorator obj = new ProdutoCorDecorator(produto);
        obj.run();
    }

    public void run(){
        String arquivoCSV = "produtos.csv";
        BufferedReader br = null;
        String linha = "";
        String csvDivisor = ",";

        try{

            br = new BufferedReader(new FileReader(arquivoCSV));

            while ((linha = br.readLine()) != null){
                
                String[] produto = linha.split(csvDivisor);

                cor = produto[produto.length-1];
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
