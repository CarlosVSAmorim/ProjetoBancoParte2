package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Conta {
    protected int numero;
    protected double saldo;
    protected List<Movimentacao> movimentacoes;

    public Conta(int numero, double saldoInicial) {
        this.numero = numero;
        this.saldo = saldoInicial;
        this.movimentacoes = new ArrayList<>();
    }

    public abstract void emitirExtrato();

    public int getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }
}