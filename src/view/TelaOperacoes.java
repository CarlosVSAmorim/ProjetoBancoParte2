package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.*;
import model.ContaCorrente;
import model.Movimentacao;

public class TelaOperacoes extends JDialog {
    private JList<Movimentacao> listExtrato;
    private DefaultListModel<Movimentacao> listModel;
    private JButton btnDepositar, btnSacar, btnTransferir;
    private JLabel lblSaldo;
    private ContaCorrente conta;

    public TelaOperacoes(JFrame owner, ContaCorrente conta, List<Movimentacao> movimentacoes) {
        super(owner, "Operações e Extrato - Conta " + conta.getNumero(), true);
        this.conta = conta;
        setSize(500, 400);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));

        // Painel Superior (Saldo e Operações)
        JPanel topPanel = new JPanel(new BorderLayout());
        lblSaldo = new JLabel();
        atualizarSaldoLabel();
        lblSaldo.setHorizontalAlignment(SwingConstants.CENTER);
        lblSaldo.setFont(lblSaldo.getFont().deriveFont(16.0f));
        topPanel.add(lblSaldo, BorderLayout.NORTH);

        JPanel botoesPanel = new JPanel();
        btnDepositar = new JButton("Depositar");
        btnSacar = new JButton("Sacar");
        btnTransferir = new JButton("Transferir");
        botoesPanel.add(btnDepositar);
        botoesPanel.add(btnSacar);
        botoesPanel.add(btnTransferir);
        topPanel.add(botoesPanel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // Centro (Extrato)
        listModel = new DefaultListModel<>();
        movimentacoes.forEach(listModel::addElement);
        listExtrato = new JList<>(listModel);
        add(new JScrollPane(listExtrato), BorderLayout.CENTER);
    }

    public void atualizarSaldoLabel() {
        lblSaldo.setText(String.format("Saldo: R$%.2f (Limite: R$%.2f)", conta.getSaldo(), conta.getLimite()));
    }

    public void adicionarMovimentacaoNaLista(Movimentacao mov) {
        listModel.add(0, mov); // Adiciona no topo
    }

    public JButton getBtnDepositar() { return btnDepositar; }
    public JButton getBtnSacar() { return btnSacar; }
    public JButton getBtnTransferir() { return btnTransferir; }
}