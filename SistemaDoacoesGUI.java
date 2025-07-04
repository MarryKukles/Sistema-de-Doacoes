package sistemadoacoes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.Objects;
import java.util.List; 

public class SistemaDoacoesGUI extends JFrame {
    private GerenciadorDoacoes gerenciador;

    private final Color PRIMARY_DARK = new Color(43, 43, 43); // Cor de fundo principal 
    private final Color SECONDARY_DARK = new Color(60, 63, 65);
    private final Color TEXT_LIGHT = new Color(204, 204, 204); 
    
    private final Color ACCENT_GREEN = new Color(76, 175, 80); // Verde para ações positivas
    private final Color ACCENT_BLUE = new Color(33, 150, 243); // Azul para ações informativas
    private final Color ACCENT_PURPLE = new Color(156, 39, 176); // Roxo para relatórios
    private final Color ACCENT_RED = new Color(244, 67, 54); // Vermelho para ações de perigo
    private final Color ACCENT_GREY = new Color(96, 125, 139); // Cinza azulado para sair

    public SistemaDoacoesGUI() {
        gerenciador = GerenciadorDoacoes.getInstance();
        configurarJanela();
        initUI();
    }

    private void configurarJanela() {
        setTitle("Sistema de Doações");
        setSize(700, 500);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        
        getContentPane().setBackground(PRIMARY_DARK);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmarSaida();
            }
        });
    }

    private void initUI() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(PRIMARY_DARK);
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 15, 15, 15);

        // Cores hexadecimais convertidas para Color
        JButton btnCadastrar = criarBotao("Cadastrar Doação", new Color(0x58d68d)); // #58d68d
        JButton btnTotal = criarBotao("Exibir Total em Dinheiro", new Color(0x5dade2)); // #5dade2
        JButton btnRelatorio = criarBotao("Relatório Completo", new Color(0xa569bd)); // #a569bd
        JButton btnLimpar = criarBotao("Limpar Doações", new Color(0xec7063)); // #ec7063
        JButton btnSair = criarBotao("Sair", new Color(0x99a3a4)); // #99a3a4

        btnCadastrar.addActionListener(e -> cadastrarDoacao());
        btnTotal.addActionListener(e -> exibirTotalDinheiro());
        btnRelatorio.addActionListener(e -> exibirRelatorio());
        btnLimpar.addActionListener(e -> limparDoacoes());
        btnSair.addActionListener(e -> confirmarSaida());

        painel.add(btnCadastrar, gbc);
        painel.add(btnTotal, gbc);
        painel.add(btnRelatorio, gbc);
        painel.add(btnLimpar, gbc);
        painel.add(btnSair, gbc);

        add(painel);
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false); 
        botao.setFont(new Font("Arial", Font.BOLD, 16)); // Aumenta a fonte
        botao.setPreferredSize(new Dimension(250, 60)); // Aumenta a altura do botão
        
        botao.setOpaque(true); 
        
    
        botao.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(cor.darker(), 2), 
            BorderFactory.createEmptyBorder(10, 20, 10, 20) 
        ));
        
        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            private Color originalColor = cor; 

            public void mouseEntered(java.awt.event.MouseEvent evt) {

                botao.setBackground(originalColor.brighter()); 
                botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botao.setBackground(originalColor); 
                botao.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); 
            }
        });
        return botao;
    }

    private void cadastrarDoacao() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setBackground(SECONDARY_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        UIManager.put("ComboBox.background", SECONDARY_DARK);
        UIManager.put("ComboBox.foreground", TEXT_LIGHT);
        UIManager.put("ComboBox.selectionBackground", ACCENT_BLUE);
        UIManager.put("ComboBox.selectionForeground", Color.WHITE);
        UIManager.put("TextField.background", SECONDARY_DARK);
        UIManager.put("TextField.foreground", TEXT_LIGHT);
        UIManager.put("TextField.caretForeground", TEXT_LIGHT);
        UIManager.put("Label.foreground", TEXT_LIGHT);

        JComboBox<String> tipoCombo = new JComboBox<>(new String[]{"Dinheiro", "Alimento", "Roupa"});
        JTextField descricaoField = new JTextField();
        JTextField quantidadeField = new JTextField();
        JTextField unidadeField = new JTextField();
        JTextField dataField = new JTextField();
        
        tipoCombo.setBackground(SECONDARY_DARK);
        tipoCombo.setForeground(TEXT_LIGHT);
        descricaoField.setBackground(SECONDARY_DARK);
        descricaoField.setForeground(TEXT_LIGHT);
        quantidadeField.setBackground(SECONDARY_DARK);
        quantidadeField.setForeground(TEXT_LIGHT);
        unidadeField.setBackground(SECONDARY_DARK);
        unidadeField.setForeground(TEXT_LIGHT);
        dataField.setBackground(SECONDARY_DARK);
        dataField.setForeground(TEXT_LIGHT);

        descricaoField.setEnabled(false);
        unidadeField.setEnabled(false);

        tipoCombo.addActionListener(e -> {
            String tipo = (String) tipoCombo.getSelectedItem();
            descricaoField.setEnabled(!Objects.equals(tipo, "Dinheiro"));
            descricaoField.setText("");
            
            unidadeField.setEnabled(Objects.equals(tipo, "Alimento"));
            unidadeField.setText("");
            
            if (Objects.equals(tipo, "Dinheiro")) {
                quantidadeField.setText("");
            }
        });
        
        panel.add(new JLabel("Tipo de Doação:"));
        panel.add(tipoCombo);
        panel.add(new JLabel("Descrição (alimento/roupa):"));
        panel.add(descricaoField);
        panel.add(new JLabel("Quantidade/Valor:"));
        panel.add(quantidadeField);
        panel.add(new JLabel("Unidade (g, kg, l, etc. - para Alimento):"));
        panel.add(unidadeField);
        panel.add(new JLabel("Data (DD/MM/AAAA):"));
        panel.add(dataField);
        
        UIManager.put("OptionPane.background", SECONDARY_DARK);
        UIManager.put("Panel.background", SECONDARY_DARK);
        UIManager.put("OptionPane.messageForeground", TEXT_LIGHT);
        UIManager.put("Button.background", ACCENT_BLUE);
        UIManager.put("Button.foreground", Color.WHITE);


        int result = JOptionPane.showConfirmDialog(
            this, panel, "Cadastrar Doação",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        UIManager.put("ComboBox.background", null);
        UIManager.put("ComboBox.foreground", null);
        UIManager.put("ComboBox.selectionBackground", null);
        UIManager.put("ComboBox.selectionForeground", null);
        UIManager.put("TextField.background", null);
        UIManager.put("TextField.foreground", null);
        UIManager.put("TextField.caretForeground", null);
        UIManager.put("Label.foreground", null);
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        UIManager.put("OptionPane.messageForeground", null);
        UIManager.put("Button.background", null);
        UIManager.put("Button.foreground", null);


        if (result == JOptionPane.OK_OPTION) {
            try {
                String tipo = (String) tipoCombo.getSelectedItem();
                String descricao = descricaoField.getText().trim();
                double quantidade = Double.parseDouble(quantidadeField.getText().trim().replace(",", "."));
                String unidade = unidadeField.getText().trim();
                String data = dataField.getText().trim();
                
                if (!Objects.equals(tipo, "Dinheiro") && descricao.isEmpty()) {
                    throw new IllegalArgumentException("Descrição é obrigatória para " + tipo);
                }
                
                if (Objects.equals(tipo, "Alimento") && unidade.isEmpty()) {
                    throw new IllegalArgumentException("Unidade é obrigatória para Alimento (ex: g, kg, l)");
                }
                
                Doacao novaDoacao = new Doacao(tipo, descricao, quantidade, unidade, data);
                gerenciador.adicionarDoacao(novaDoacao);
                
                JOptionPane.showMessageDialog(this, "Doação cadastrada com sucesso!", 
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Quantidade/Valor inválido! Use números e ponto como separador decimal.", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), 
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void exibirTotalDinheiro() {
        double total = gerenciador.calcularTotalDinheiro();
        UIManager.put("OptionPane.background", SECONDARY_DARK);
        UIManager.put("Panel.background", SECONDARY_DARK);
        UIManager.put("OptionPane.messageForeground", TEXT_LIGHT);
        UIManager.put("Button.background", ACCENT_BLUE);
        UIManager.put("Button.foreground", Color.WHITE);

        JOptionPane.showMessageDialog(this, 
            String.format(Locale.getDefault(), "Total doado em dinheiro: R$ %.2f", total),
            "Total em Dinheiro", JOptionPane.INFORMATION_MESSAGE);
        
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        UIManager.put("OptionPane.messageForeground", null);
        UIManager.put("Button.background", null);
        UIManager.put("Button.foreground", null);
    }

    private void exibirRelatorio() {
        System.out.println("Entrando em exibirRelatorio()..."); 
        List<Doacao> currentDoacoes = gerenciador.getDoacoes();
        System.out.println("Número de doações encontradas: " + currentDoacoes.size()); 

        if (currentDoacoes.isEmpty()) {
            UIManager.put("OptionPane.background", SECONDARY_DARK);
            UIManager.put("Panel.background", SECONDARY_DARK);
            UIManager.put("OptionPane.messageForeground", TEXT_LIGHT);
            UIManager.put("Button.background", ACCENT_BLUE);
            UIManager.put("Button.foreground", Color.WHITE);
            JOptionPane.showMessageDialog(this, "Nenhuma doação cadastrada.", 
                "Relatório", JOptionPane.INFORMATION_MESSAGE);
            UIManager.put("OptionPane.background", null);
            UIManager.put("Panel.background", null);
            UIManager.put("OptionPane.messageForeground", null);
            UIManager.put("Button.background", null);
            UIManager.put("Button.foreground", null);
            System.out.println("Nenhuma doação cadastrada, exibindo mensagem informativa e retornando."); 
            return;
        }

        System.out.println("Doações encontradas, preparando a tabela do relatório..."); 

        String[] colunas = {"Tipo", "Descrição", "Quantidade/Valor", "Unidade", "Data"}; 
        DefaultTableModel model = new DefaultTableModel(colunas, 0);

        for (Doacao d : gerenciador.getDoacoes()) {
            String quantidadeDisplay;
            String unidadeDisplay = d.getUnidade();

            if (d.getTipo().equals("Dinheiro")) {
                quantidadeDisplay = String.format(Locale.getDefault(), "R$ %.2f", d.getQuantidade());
                unidadeDisplay = "-";
            } else {
                quantidadeDisplay = String.format(Locale.getDefault(), "%.2f", d.getQuantidade());
                if (unidadeDisplay.isEmpty()) {
                    unidadeDisplay = "-";
                }
            }
            
            model.addRow(new Object[]{
                d.getTipo(), 
                d.getDescricao(),
                quantidadeDisplay, 
                unidadeDisplay,
                d.getData().format(Doacao.FORMATTER)
            });
        }

        JTable tabela = new JTable(model);
        tabela.setFont(new Font("Arial", Font.PLAIN, 14));
        tabela.setRowHeight(25);
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        
        tabela.setBackground(SECONDARY_DARK);
        tabela.setForeground(TEXT_LIGHT);
        tabela.setGridColor(PRIMARY_DARK.brighter());
        tabela.getTableHeader().setBackground(PRIMARY_DARK);
        tabela.getTableHeader().setForeground(TEXT_LIGHT);
        tabela.setSelectionBackground(ACCENT_BLUE.darker());
        tabela.setSelectionForeground(Color.WHITE);

        tabela.getColumnModel().getColumn(0).setPreferredWidth(100);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(150);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(120);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(80);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(100);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setPreferredSize(new Dimension(650, 400));
        scroll.setBackground(SECONDARY_DARK);
        scroll.getViewport().setBackground(SECONDARY_DARK);


        JFrame janelaRelatorio = new JFrame("Relatório de Doações");
        janelaRelatorio.setSize(750, 500); 
        janelaRelatorio.setLocationRelativeTo(this);
        janelaRelatorio.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        janelaRelatorio.getContentPane().setBackground(PRIMARY_DARK);
        janelaRelatorio.add(scroll);
        janelaRelatorio.setVisible(true);
        System.out.println("Janela de relatório criada e setada para visível.");
    }

    private void limparDoacoes() {
        UIManager.put("OptionPane.background", SECONDARY_DARK);
        UIManager.put("Panel.background", SECONDARY_DARK);
        UIManager.put("OptionPane.messageForeground", TEXT_LIGHT);
        UIManager.put("Button.background", ACCENT_BLUE);
        UIManager.put("Button.foreground", Color.WHITE);

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Tem certeza que deseja limpar TODAS as doações?", 
            "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            gerenciador.limparDoacoes();
            JOptionPane.showMessageDialog(this, 
                "Todas as doações foram removidas.", 
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        UIManager.put("OptionPane.messageForeground", null);
        UIManager.put("Button.background", null);
        UIManager.put("Button.foreground", null);
    }

    private void confirmarSaida() {
        UIManager.put("OptionPane.background", SECONDARY_DARK);
        UIManager.put("Panel.background", SECONDARY_DARK);
        UIManager.put("OptionPane.messageForeground", TEXT_LIGHT);
        UIManager.put("Button.background", ACCENT_BLUE);
        UIManager.put("Button.foreground", Color.WHITE);

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Deseja realmente sair do sistema?", 
            "Confirmar Saída", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            System.exit(0);
        }
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        UIManager.put("OptionPane.messageForeground", null);
        UIManager.put("Button.background", null);
        UIManager.put("Button.foreground", null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new SistemaDoacoesGUI().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
