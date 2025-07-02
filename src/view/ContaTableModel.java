package view;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import model.ContaCorrente;

public class ContaTableModel extends AbstractTableModel {
    private final String[] colunas = {"Número", "Saldo (R$)", "Limite (R$)"};
    private List<ContaCorrente> contas = new ArrayList<>();

    @Override
    public int getRowCount() {
        return contas.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ContaCorrente conta = contas.get(rowIndex);
        switch (columnIndex) {
            case 0: return conta.getNumero();
            case 1: return String.format("%.2f", conta.getSaldo());
            case 2: return String.format("%.2f", conta.getLimite());
        }
        return null;
    }

    public void setContas(List<ContaCorrente> contas) {
        this.contas = contas;
        fireTableDataChanged();
    }

    public ContaCorrente getContaAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < contas.size()) {
            return contas.get(rowIndex);
        }
        return null;
    }
}