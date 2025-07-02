package model;

public interface Transferivel {
    void transferir(Conta destino, double valor) throws SaldoInsuficienteException;
}