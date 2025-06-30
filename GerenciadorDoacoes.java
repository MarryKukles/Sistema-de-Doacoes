import java.io.*;
import java.util.ArrayList;

public class GerenciadorDoacoes {
    private ArrayList<Doacao> listaDoacoes;
    private final String arquivo = "doacoes.txt";

    public GerenciadorDoacoes() {
        listaDoacoes = new ArrayList<>();
        carregarDoacoesDoArquivo();
    }

    public void adicionarDoacao(Doacao doacao) {
        listaDoacoes.add(doacao);
        salvarDoacoesEmArquivo();
    }

    public double calcularTotal() {
        double total = 0;
        for (Doacao d : listaDoacoes) {
            total += d.getQuantidadeOuValor();
        }
        return total;
    }

    public void salvarDoacoesEmArquivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {
            for (Doacao d : listaDoacoes) {
                writer.write(d.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar as doações: " + e.getMessage());
        }
    }

    public void carregarDoacoesDoArquivo() {
        File f = new File(arquivo);
        if (!f.exists()) {
            return; // se o arquivo não existir, nada para carregar
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 3) {
                    String tipo = partes[0];
                    double quantidadeOuValor = Double.parseDouble(partes[1]);
                    String data = partes[2];
                    Doacao doacao = new Doacao(tipo, quantidadeOuValor, data);
                    listaDoacoes.add(doacao);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Erro ao carregar as doações: " + e.getMessage());
        }
    }

    public void exibirRelatorio() {
        if (listaDoacoes.isEmpty()) {
            System.out.println("Nenhuma doação cadastrada ainda.");
        } else {
            System.out.println("===== RELATÓRIO DE DOAÇÕES =====");
            for (Doacao d : listaDoacoes) {
                System.out.println("Tipo: " + d.getTipo() +
                                   ", Quantidade/Valor: " + d.getQuantidadeOuValor() +
                                   ", Data: " + d.getData());
            }
        }
    }
}
