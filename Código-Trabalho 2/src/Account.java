import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {

    // Campo que armazena o saldo da conta
    private double balance;

    // Campo responsável pelo acesso do saldo da conta (campo compartilhado)
    private final Lock lock = new ReentrantLock();

    // Campo de controle do buffer
    private final Condition bufferNotFull = lock.newCondition();

    // Campo de controle do buffer
    private final Condition bufferNotEmpty = lock.newCondition();

    /**
     * Construtor da classe Account
     * 
     * @param balance
     */
    public Account(double balance) {
        this.balance = balance;
        System.out.println("Conta criada com saldo inicial de: " + balance);
    }

    /**
     * Método para realizar depósito na conta
     * 
     * @param valor
     */
    public void deposit(double valor) throws InterruptedException {
        lock.lock();

        try {
            this.balance += valor;
            bufferNotFull.signalAll();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Méotodo para realizar saque na conta
     * 
     * @param valor
     * @return
     */
    public boolean withdraw(double valor) throws InterruptedException {
        lock.lock();

        try {
            if (this.balance < valor) {
                return false;
            } else {
                this.balance -= valor;
                bufferNotEmpty.signalAll();
                return true;
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return "Conta: saldo atualizado de " + this.balance;
    }
}
