package model;

public class ContaCorrente extends Conta implements Transferivel {
    private double limite;

    public ContaCorrente(int numero, double saldoInicial, double limite) {
        super(numero, saldoInicial);
        this.limite = limite;
    }

    public void depositar(double valor) {
        if (valor > 0) {
            this.saldo += valor;
        }
    }

    public void sacar(double valor) throws SaldoInsuficienteException {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor do saque deve ser positivo.");
        }
        if (valor > this.saldo + this.limite) {
            throw new SaldoInsuficienteException("Saldo e limite insuficientes. Disponível: R$" + String.format("%.2f", this.saldo + this.limite));
        }
        this.saldo -= valor;
    }

    @Override
    public void transferir(Conta destino, double valor) throws SaldoInsuficienteException {
        this.sacar(valor);
        if (destino instanceof ContaCorrente) {
            ((ContaCorrente) destino).depositar(valor);
        }
    }

    @Override
    public void emitirExtrato() {
        // Lógica movida para a GUI
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }
}