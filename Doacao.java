public class Doacao {
    private String tipo;
    private double quantidadeOuValor;
    private String data;

    public Doacao(String tipo, double quantidadeOuValor, String data) {
        this.tipo = tipo;
        this.quantidadeOuValor = quantidadeOuValor;
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getQuantidadeOuValor() {
        return quantidadeOuValor;
    }

    public void setQuantidadeOuValor(double quantidadeOuValor) {
        this.quantidadeOuValor = quantidadeOuValor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return tipo + ";" + quantidadeOuValor + ";" + data;
    }
}
