package sistemadoacoes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Doacao {
    private String tipo;
    private String descricao;
    private double quantidade;
    private String unidade;
    private LocalDate data;

    // Formato de data: DD/MM/AAAA
    // AGORA É PÚBLICO para ser acessível de outras classes no mesmo pacote
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Doacao(String tipo, String descricao, double quantidade, String unidade, String data) {
        setTipo(tipo);
        setDescricao(descricao);
        setQuantidade(quantidade);
        setUnidade(unidade);
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

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
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
        return String.join(";",
            tipo,
            descricao,
            String.valueOf(quantidade),
            unidade,
            data.format(FORMATTER)
        );
    }
}
