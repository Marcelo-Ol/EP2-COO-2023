import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class LeitorCSV {

    public static ArrayList<Produto> carregaProdutosDoCSV(String nomeArquivoCSV, Map<Integer, Map<String, String>> formato_linha) {
        ArrayList<Produto> produtos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivoCSV))) {
            String linha;
            boolean primeiraLinha = true; // Flag para ignorar o cabeçalho do CSV

            while ((linha = br.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }

                String[] campos = linha.split(",");
                int id = Integer.parseInt(campos[0].trim());
                String descricao = campos[1].trim();
                String categoria = campos[2].trim();
                int quantidadeEstoque = Integer.parseInt(campos[3].trim());
                double preco = Double.parseDouble(campos[4].trim());

                boolean negrito = Boolean.parseBoolean(campos[5].trim());
                boolean italico = Boolean.parseBoolean(campos[6].trim());
                String cor = campos[7].trim();

                Produto produto = new Produto() {
                    @Override
                    public void setQtdEstoque(int qtdEstoque) {

                    }

                    @Override
                    public void setPreco(double preco) {

                    }

                    @Override
                    public int getId() {
                        return id;
                    }

                    @Override
                    public String getDescricao() {
                        return descricao;
                    }

                    @Override
                    public String getCategoria() {
                        return categoria;
                    }

                    @Override
                    public int getQtdEstoque() {
                        return quantidadeEstoque;
                    }

                    @Override
                    public double getPreco() {
                        return preco;
                    }

                    @Override
                    public String formataParaImpressao() {
                        return descricao + ", " + categoria + ", " + preco + ", " + quantidadeEstoque + " unidade(s) em estoque";
                    }
                };

                produtos.add(produto);

                Map<String, String> formato_linhaProduto = new HashMap<>();
                formato_linhaProduto.put("italico", String.valueOf(italico));
                formato_linhaProduto.put("negrito", String.valueOf(negrito));
                formato_linhaProduto.put("cor", cor);
                formato_linha.put(id, formato_linhaProduto);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return produtos;
    }
}
