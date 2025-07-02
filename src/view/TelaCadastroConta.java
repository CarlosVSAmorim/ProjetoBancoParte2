package view;

import javax.swing.*;
import java.awt.*;
import model.ContaCorrente;

public class TelaCadastroConta extends JDialog {
    private JTextField txtNumero, txtSaldo, txtLimite;
    private JButton btnSalvar, btnCancelar;
    private boolean salvo = false;

    public TelaCadastroConta(Frame owner, ContaCorrente conta) {
        super(owner, true);
        setTitle(conta == null ? "Nova Conta" : "Editar Conta");
        setSize(350, 200);
        setLocationRelativeTo(owner);
        setLayout(new GridLayout(4, 2, 5, 5));

        add(new JLabel("Número:"));
        txtNumero = new JTextField(conta != null ? String.valueOf(conta.getNumero()) : "");
        if (conta != null) {
            txtNumero.setEditable(false);
        }
        add(txtNumero);

        add(new JLabel("Saldo Inicial (R$):"));
        txtSaldo = new JTextField(conta != null ? String.format("%.2f", conta.getSaldo()) : "");
        add(txtSaldo);

        add(new JLabel("Limite (R$):"));
        txtLimite = new JTextField(conta != null ? String.format("%.2f", conta.getLimite()) : "");
        add(txtLimite);

        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Cancelar");

        btnSalvar.addActionListener(e -> {
            salvo = true;
            dispose();
        });

        btnCancelar.addActionListener(e -> dispose());

        add(btnSalvar);
        add(btnCancelar);
    }

    public boolean isSalvo() {
        return salvo;
    }

    public ContaCorrente getConta() {
        try {
            int numero = Integer.parseInt(txtNumero.getText());
            double saldo = Double.parseDouble(txtSaldo.getText().replace(',', '.'));
            double limite = Double.parseDouble(txtLimite.getText().replace(',', '.'));
            return new ContaCorrente(numero, saldo, limite);
        } catch (NumberFormatException e) {
            return null; // Retorna nulo se a conversão falhar
        }
    }
}