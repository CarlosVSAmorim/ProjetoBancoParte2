package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Movimentacao {
    private LocalDateTime data;
    private double valor;
    private TipoTransacao tipo;

    public Movimentacao(double valor, TipoTransacao tipo) {
        this.data = LocalDateTime.now();
        this.valor = valor;
        this.tipo = tipo;
    }

    public Movimentacao(LocalDateTime data, double valor, TipoTransacao tipo) {
        this.data = data;
        this.valor = valor;
        this.tipo = tipo;
    }

    public LocalDateTime getData() { return data; }
    public double getValor() { return valor; }
    public TipoTransacao getTipo() { return tipo; }

    @Override
    public String toString() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String tipoStr = tipo == TipoTransacao.CREDITO ? "Crédito" : "Débito";
        return String.format("%s - %s de R$%.2f", data.format(formato), tipoStr, valor);
    }
}