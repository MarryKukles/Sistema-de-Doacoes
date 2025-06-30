import java.util.Scanner;

public class SistemaPrincipal {
    private GerenciadorDoacoes gerenciador;
    private Scanner scanner;

    public SistemaPrincipal() {
        gerenciador = new GerenciadorDoacoes();
        scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        int opcao = -1;
        while (opcao != 4) {
            System.out.println("\n====== MENU DE DOAÇÕES ======");
            System.out.println("1. Cadastrar nova doação");
            System.out.println("2. Exibir total de doações");
            System.out.println("3. Exibir relatório de doações");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1:
                        cadastrarDoacao();
                        break;
                    case 2:
                        System.out.println("Total de doações: " + gerenciador.calcularTotal());
                        break;
                    case 3:
                        gerenciador.exibirRelatorio();
                        break;
                    case 4:
                        System.out.println("Encerrando o sistema. Obrigado por ajudar!");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Digite um número.");
            }
        }
    }

    private void cadastrarDoacao() {
        System.out.print("Informe o tipo da doação (dinheiro, alimento, roupa, etc): ");
        String tipo = scanner.nextLine();

        double quantidadeOuValor = 0;
        boolean entradaValida = false;
        while (!entradaValida) {
            System.out.print("Informe a quantidade ou valor: ");
            try {
                quantidadeOuValor = Double.parseDouble(scanner.nextLine());
                entradaValida = true;
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Tente novamente.");
            }
        }

        System.out.print("Informe a data da doação (ex: 2025-06-27): ");
        String data = scanner.nextLine();

        Doacao novaDoacao = new Doacao(tipo, quantidadeOuValor, data);
        gerenciador.adicionarDoacao(novaDoacao);
        System.out.println("Doação cadastrada com sucesso!");
    }

    public static void main(String[] args) {
        SistemaPrincipal sistema = new SistemaPrincipal();
        sistema.exibirMenu();
    }
}
