package sistemadoacoes;

import java.util.Scanner;

public class SistemaPrincipal {
    private GerenciadorDoacoes gerenciador;
    private Scanner scanner;

    public SistemaPrincipal() {
        gerenciador = GerenciadorDoacoes.getInstance();
        scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        int opcao = -1;
        while (opcao != 5) {
            System.out.println("\n====== MENU DE DOAÇÕES ======");
            System.out.println("1. Cadastrar nova doação");
            System.out.println("2. Exibir total de doações em dinheiro");
            System.out.println("3. Exibir relatório de doações");
            System.out.println("4. Limpar todas as doações");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1:
                        cadastrarDoacao();
                        break;
                    case 2:
                        exibirTotalDinheiro();
                        break;
                    case 3:
                        exibirRelatorioConsole();
                        break;
                    case 4:
                        limparDoacoes();
                        break;
                    case 5:
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
        try {
            System.out.println("\n===== NOVA DOAÇÃO =====");
            
            System.out.println("Tipos disponíveis:");
            System.out.println("1. Dinheiro");
            System.out.println("2. Alimento");
            System.out.println("3. Roupa");
            System.out.print("Escolha o tipo (1-3): ");
            int tipoOp = Integer.parseInt(scanner.nextLine());
            
            String tipo = "";
            String descricao = "";
            
            switch (tipoOp) {
                case 1:
                    tipo = "Dinheiro";
                    break;
                case 2:
                    tipo = "Alimento";
                    System.out.print("Informe qual alimento: ");
                    descricao = scanner.nextLine();
                    break;
                case 3:
                    tipo = "Roupa";
                    System.out.print("Informe o tipo de roupa: ");
                    descricao = scanner.nextLine();
                    break;
                default:
                    throw new IllegalArgumentException("Opção inválida");
            }
            
            System.out.print("Informe a " + (tipo.equals("Dinheiro") ? "valor" : "quantidade") + ": ");
            double quantidade = Double.parseDouble(scanner.nextLine());
            
            System.out.print("Informe a data (AAAA-MM-DD): ");
            String data = scanner.nextLine();
            
            Doacao novaDoacao = new Doacao(tipo, descricao, quantidade, data);
            gerenciador.adicionarDoacao(novaDoacao);
            System.out.println("Doação cadastrada com sucesso!");
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido! Use números para quantidade/valor.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void exibirTotalDinheiro() {
        double total = gerenciador.calcularTotalDinheiro();
        System.out.printf("\nTotal de doações em dinheiro: R$ %.2f%n", total);
    }

    private void exibirRelatorioConsole() {
        System.out.println("\n===== RELATÓRIO DE DOAÇÕES =====");
        System.out.printf("%-15s %-20s %-15s %-10s%n", "Tipo", "Descrição", "Quantidade", "Data");
        System.out.println("--------------------------------------------------");
        
        for (Doacao d : gerenciador.getDoacoes()) {
            System.out.printf("%-15s %-20s %-15.2f %-10s%n", 
                d.getTipo(), 
                d.getDescricao(), 
                d.getQuantidade(), 
                d.getData());
        }
        
        if (gerenciador.getDoacoes().isEmpty()) {
            System.out.println("Nenhuma doação cadastrada.");
        }
    }

    private void limparDoacoes() {
        System.out.print("\nTem certeza que deseja limpar TODAS as doações? (s/n): ");
        String confirmacao = scanner.nextLine().toLowerCase();
        
        if (confirmacao.equals("s")) {
            gerenciador.limparDoacoes();
            System.out.println("Todas as doações foram removidas.");
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    public static void main(String[] args) {
        SistemaPrincipal sistema = new SistemaPrincipal();
        sistema.exibirMenu();
    }
}
