package view;

import javax.swing.*;
import java.awt.*;
import controller.ContaController;

public class TelaPrincipal extends JFrame {
    private JTable tabelaContas;
    private ContaTableModel tableModel;
    private JButton btnNova, btnEditar, btnExcluir, btnOperacoes;

    public TelaPrincipal() {
        setTitle("Gerenciamento de Contas Bancárias");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tableModel = new ContaTableModel();
        tabelaContas = new JTable(tableModel);
        tabelaContas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tabelaContas), BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        btnNova = new JButton("Nova Conta");
        btnEditar = new JButton("Editar Conta");
        btnExcluir = new JButton("Excluir Conta");
        btnOperacoes = new JButton("Realizar Operações / Extrato");

        painelBotoes.add(btnNova);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnOperacoes);
        add(painelBotoes, BorderLayout.SOUTH);
    }

    public void setController(ContaController controller) {
        btnNova.addActionListener(e -> controller.abrirTelaCadastro(null));
        btnEditar.addActionListener(e -> {
            int selectedRow = tabelaContas.getSelectedRow();
            if (selectedRow != -1) {
                ContaTableModel model = (ContaTableModel) tabelaContas.getModel();
                controller.abrirTelaCadastro(model.getContaAt(selectedRow));
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma conta para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });
        btnExcluir.addActionListener(e -> {
            int selectedRow = tabelaContas.getSelectedRow();
            if (selectedRow != -1) {
                controller.excluirConta(selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma conta para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });
        btnOperacoes.addActionListener(e -> {
            int selectedRow = tabelaContas.getSelectedRow();
            if (selectedRow != -1) {
                controller.abrirTelaOperacoes(selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma conta para realizar operações.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    public ContaTableModel getTableModel() {
        return tableModel;
    }
}