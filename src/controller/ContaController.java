package controller;

import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import model.*;
import dao.*;
import view.*;

public class ContaController {
    private TelaPrincipal viewPrincipal;
    private ContaDAO contaDAO;
    private MovimentacaoDAO movimentacaoDAO;

    public ContaController(TelaPrincipal viewPrincipal, ContaDAO contaDAO) {
        this.viewPrincipal = viewPrincipal;
        this.contaDAO = contaDAO;
        this.movimentacaoDAO = new MovimentacaoDAO();
        this.viewPrincipal.setController(this);
        atualizarTabelaContas();
    }

    public void atualizarTabelaContas() {
        try {
            List<ContaCorrente> contas = contaDAO.listarTodas();
            viewPrincipal.getTableModel().setContas(contas);
        } catch (SQLException e) {
            mostrarErro("Erro ao carregar contas do banco de dados: " + e.getMessage());
        }
    }

    public void abrirTelaCadastro(ContaCorrente contaParaEditar) {
        TelaCadastroConta telaCadastro = new TelaCadastroConta(viewPrincipal, contaParaEditar);
        telaCadastro.setVisible(true);

        if (telaCadastro.isSalvo()) {
            ContaCorrente conta = telaCadastro.getConta();
            if (conta == null) {
                mostrarErro("Dados inválidos. Verifique os campos e tente novamente.");
                return;
            }

            try {
                if (contaParaEditar == null) { // Criando nova conta
                    if (contaDAO.existe(conta.getNumero())) {
                        mostrarErro("Já existe uma conta com este número.");
                        return;
                    }
                    contaDAO.adicionar(conta);
                    JOptionPane.showMessageDialog(viewPrincipal, "Conta criada com sucesso!");
                } else { // Editando conta existente
                    contaDAO.atualizar(conta);
                    JOptionPane.showMessageDialog(viewPrincipal, "Conta atualizada com sucesso!");
                }
                atualizarTabelaContas();
            } catch (SQLException e) {
                mostrarErro("Erro ao salvar no banco de dados: " + e.getMessage());
            }
        }
    }

    public void excluirConta(int rowIndex) {
        ContaCorrente conta = viewPrincipal.getTableModel().getContaAt(rowIndex);
        int confirm = JOptionPane.showConfirmDialog(viewPrincipal,
                "Tem certeza que deseja excluir a conta " + conta.getNumero() + "?",
                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                contaDAO.remover(conta.getNumero());
                JOptionPane.showMessageDialog(viewPrincipal, "Conta excluída com sucesso!");
                atualizarTabelaContas();
            } catch (SQLException e) {
                mostrarErro("Erro ao excluir conta: " + e.getMessage());
            }
        }
    }

    public void abrirTelaOperacoes(int rowIndex) {
        ContaCorrente conta = viewPrincipal.getTableModel().getContaAt(rowIndex);
        try {
            List<Movimentacao> movimentacoes = movimentacaoDAO.listarPorConta(conta.getNumero());
            TelaOperacoes telaOp = new TelaOperacoes(viewPrincipal, conta, movimentacoes);

            telaOp.getBtnDepositar().addActionListener(e -> realizarDeposito(conta, telaOp));
            telaOp.getBtnSacar().addActionListener(e -> realizarSaque(conta, telaOp));
            telaOp.getBtnTransferir().addActionListener(e -> realizarTransferencia(conta, telaOp));

            telaOp.setVisible(true);
            atualizarTabelaContas(); // Atualiza a tabela principal ao fechar a janela
        } catch (SQLException e) {
            mostrarErro("Erro ao carregar movimentações: " + e.getMessage());
        }
    }

    private void realizarDeposito(ContaCorrente conta, TelaOperacoes telaOp) {
        String valorStr = JOptionPane.showInputDialog(telaOp, "Digite o valor a depositar:", "Depósito", JOptionPane.PLAIN_MESSAGE);
        if (valorStr != null) {
            try {
                double valor = Double.parseDouble(valorStr.replace(',', '.'));
                if (valor <= 0) {
                    mostrarErro("O valor do depósito deve ser positivo.");
                    return;
                }

                conta.depositar(valor);
                Movimentacao mov = new Movimentacao(valor, TipoTransacao.CREDITO);

                contaDAO.atualizar(conta);
                movimentacaoDAO.adicionar(mov, conta.getNumero());

                telaOp.adicionarMovimentacaoNaLista(mov);
                telaOp.atualizarSaldoLabel();
                JOptionPane.showMessageDialog(telaOp, "Depósito realizado com sucesso!");
            } catch (NumberFormatException ex) {
                mostrarErro("Valor inválido.");
            } catch (SQLException ex) {
                mostrarErro("Erro ao processar depósito: " + ex.getMessage());
            }
        }
    }

    private void realizarSaque(ContaCorrente conta, TelaOperacoes telaOp) {
        String valorStr = JOptionPane.showInputDialog(telaOp, "Digite o valor a sacar:", "Saque", JOptionPane.PLAIN_MESSAGE);
        if (valorStr != null) {
            try {
                double valor = Double.parseDouble(valorStr.replace(',', '.'));
                conta.sacar(valor); // Lança exceção se não for possível
                Movimentacao mov = new Movimentacao(valor, TipoTransacao.DEBITO);

                contaDAO.atualizar(conta);
                movimentacaoDAO.adicionar(mov, conta.getNumero());

                telaOp.adicionarMovimentacaoNaLista(mov);
                telaOp.atualizarSaldoLabel();
                JOptionPane.showMessageDialog(telaOp, "Saque realizado com sucesso!");
            } catch (NumberFormatException ex) {
                mostrarErro("Valor inválido.");
            } catch (SaldoInsuficienteException | IllegalArgumentException ex) {
                mostrarErro(ex.getMessage());
            } catch (SQLException ex) {
                mostrarErro("Erro ao processar saque: " + ex.getMessage());
            }
        }
    }

    private void realizarTransferencia(ContaCorrente origem, TelaOperacoes telaOp) {
        String numDestinoStr = JOptionPane.showInputDialog(telaOp, "Digite o número da conta de destino:", "Transferência", JOptionPane.PLAIN_MESSAGE);
        if (numDestinoStr == null) return;

        String valorStr = JOptionPane.showInputDialog(telaOp, "Digite o valor a transferir:", "Transferência", JOptionPane.PLAIN_MESSAGE);
        if (valorStr == null) return;

        try {
            int numDestino = Integer.parseInt(numDestinoStr);
            double valor = Double.parseDouble(valorStr.replace(',', '.'));

            if (origem.getNumero() == numDestino) {
                mostrarErro("A conta de origem e destino não podem ser a mesma.");
                return;
            }

            ContaCorrente destino = contaDAO.buscarPorNumero(numDestino);
            if (destino == null) {
                mostrarErro("Conta de destino não encontrada.");
                return;
            }

            // Lógica de transação (simplificada)
            origem.transferir(destino, valor); // Lança exceção se der erro no saque

            // Se chegou aqui, o saque da origem foi bem-sucedido
            contaDAO.atualizar(origem);
            movimentacaoDAO.adicionar(new Movimentacao(valor, TipoTransacao.DEBITO), origem.getNumero());

            contaDAO.atualizar(destino);
            movimentacaoDAO.adicionar(new Movimentacao(valor, TipoTransacao.CREDITO), destino.getNumero());

            telaOp.adicionarMovimentacaoNaLista(new Movimentacao(valor, TipoTransacao.DEBITO));
            telaOp.atualizarSaldoLabel();
            JOptionPane.showMessageDialog(telaOp, "Transferência realizada com sucesso!");

        } catch (NumberFormatException ex) {
            mostrarErro("Número da conta ou valor inválido.");
        } catch (SaldoInsuficienteException | IllegalArgumentException ex) {
            mostrarErro("Erro na transferência: " + ex.getMessage());
        } catch (SQLException ex) {
            mostrarErro("Erro de banco de dados na transferência: " + ex.getMessage());
        }
    }

    private void mostrarErro(String mensagem) {
        JOptionPane.showMessageDialog(viewPrincipal, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}