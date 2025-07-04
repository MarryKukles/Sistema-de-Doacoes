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
                String[] partes = linha.split(";");
             
                if (partes.length == 4 || partes.length == 5) {
                    try {
                        String tipo = partes[0];
                        String descricao = partes[1];
                        double quantidade = Double.parseDouble(partes[2].replace(",", ".")); 
                        String unidade = "";
                        String data;

                        if (partes.length == 5) { 
                            unidade = partes[3];
                            data = partes[4];
                        } else { 
                            data = partes[3];
                        }
                        
                        Doacao d = new Doacao(tipo, descricao, quantidade, unidade, data);
                        doacoes.add(d);
                    } catch (IllegalArgumentException e) {
                      
                        System.err.println("Erro ao carregar doação (dados inválidos ou formato de número/data): " + e.getMessage());
                    } 
                } else {
                    System.err.println("Linha mal formatada no arquivo de doações: " + linha);
                }
            }
        } catch (IOException e) {
            System.out.println("Arquivo de doações não encontrado ou erro de leitura, iniciando com lista vazia.");
        }
    }

    private void salvarDoacoes() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (Doacao d : doacoes) {
                bw.write(d.toString());
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
