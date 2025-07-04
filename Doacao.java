package sistemadoacoes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Doacao {
    private String tipo;
    private String descricao;
    private double quantidade;
    private String unidade; // NOVO: Adicionado o campo unidade
    private LocalDate data;

    // Formato de data: DD/MM/AAAA
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Construtor original (ainda pode ser útil se tiver outras fontes de dados)
    // public Doacao(String tipo, String descricao, double quantidade, String data) {
    //     this(tipo, descricao, quantidade, "", data); // Chama o novo construtor com unidade vazia
    // }

    // NOVO Construtor que inclui 'unidade'
    public Doacao(String tipo, String descricao, double quantidade, String unidade, String data) {
        setTipo(tipo);
        setDescricao(descricao);
        setQuantidade(quantidade);
        setUnidade(unidade); // Definir a unidade
        setData(data);
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo da doação não pode ser vazio");
        }
        this.tipo = tipo.trim();
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao != null ? descricao : "";
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        if (quantidade < 0) {
            throw new IllegalArgumentException("Quantidade não pode ser negativa");
        }
        this.quantidade = quantidade;
    }

    public String getUnidade() { // Getter para unidade
        return unidade;
    }

    public void setUnidade(String unidade) { // Setter para unidade
        this.unidade = unidade != null ? unidade.trim() : "";
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(String data) {
        try {
            this.data = LocalDate.parse(data, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Data inválida. Use o formato DD/MM/AAAA");
        }
    }

    @Override
    public String toString() {
        // Ajustado para incluir a unidade, separada por ponto e vírgula
        // Se a unidade for vazia (ex: para Dinheiro ou Roupa), ela será salva como string vazia.
        return String.join(";",
            tipo,
            descricao,
            String.valueOf(quantidade),
            unidade, // Inclui a unidade
            data.format(FORMATTER)
        );
    }
}
