package sistemadoacoes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class GerenciadorDoacoes {
    private static GerenciadorDoacoes instance;
    private ArrayList<Doacao> doacoes;
    private static final String ARQUIVO = "doacoes.txt";

    private GerenciadorDoacoes() {
        doacoes = new ArrayList<>();
        carregarDoacoes();
    }

    public static synchronized GerenciadorDoacoes getInstance() {
        if (instance == null) {
            instance = new GerenciadorDoacoes();
        }
        return instance;
    }

    public void adicionarDoacao(Doacao d) {
        doacoes.add(d);
        salvarDoacoes();
    }

    public double calcularTotalDinheiro() {
        return doacoes.stream()
                .filter(d -> d.getTipo().equalsIgnoreCase("Dinheiro"))
                .mapToDouble(Doacao::getQuantidade)
                .sum();
    }

    public List<Doacao> getDoacoes() {
        return new ArrayList<>(doacoes);
    }

    private void carregarDoacoes() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";", -1); // Use -1 para manter strings vazias no final
                
                String tipo = partes[0];
                String descricao = partes[1];
                double quantidade = Double.parseDouble(partes[2]);
                String unidade = ""; // Valor padrão para unidade
                String data = partes[3];

                // Verifica se há a parte da unidade (compatibilidade com arquivos antigos e novos)
                if (partes.length >= 5) { 
                    unidade = partes[3]; // A unidade é a 4ª parte (índice 3)
                    data = partes[4];    // A data é a 5ª parte (índice 4)
                } 
                // Se partes.length for 4, significa que é um formato antigo sem unidade, ou dinheiro/roupa salvo sem unidade
                // Nesse caso, 'data' já está em partes[3] e 'unidade' permanece vazia ("").

                try {
                    Doacao d = new Doacao(
                        tipo, 
                        descricao, 
                        quantidade, 
                        unidade, // Passa a unidade
                        data
                    );
                    doacoes.add(d);
                } catch (IllegalArgumentException e) {
                    System.err.println("Erro ao carregar doação (linha ignorada): " + linha + " - " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Arquivo de doações não encontrado, iniciando com lista vazia.");
        }
    }

    private void salvarDoacoes() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (Doacao d : doacoes) {
                bw.write(d.toString()); // toString() da Doacao agora salva a unidade
                bw.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar doações: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void limparDoacoes() {
        doacoes.clear();
        salvarDoacoes();
    }
}
