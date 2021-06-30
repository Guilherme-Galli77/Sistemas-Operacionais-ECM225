import java.util.Random;

public class Client extends Thread {
    
    // Campo que armazena o nome do Cliente (Thread)
    private String name;

    // Referência para a conta compartilhada
    private Account account;

    /**
     * Construtor da classe Client
     * 
     * @param account
     * @param nome
     */
    public Client(Account account, String nome) {
        super(nome);
        this.name = nome;
        this.account = account;
    }

    /**
     * Override do método run.
     * Realiza a escolha aleatória da operação (deposito ou saque) e
     * a escolha aleatória do valor da operação com base em um conjunto 
     * de valores preestabelecidos
     */
    @Override
    public void run() {
        Random random = new Random();

        try {
            while (true) {
                int sorteioOperacao = random.nextInt(2);
                int sorteioValor = random.nextInt(3);

                int[] conjuntoValores = new int[4];
                conjuntoValores[0] = 10;
                conjuntoValores[1] = 20;
                conjuntoValores[2] = 50;
                conjuntoValores[3] = 100;

                if (sorteioOperacao == 0) {
                    account.deposit(conjuntoValores[sorteioValor]);
                    System.out.println("Cliente: " + Thread.currentThread().getName() + " depositou "
                            + conjuntoValores[sorteioValor]);
                    System.out.println(account.toString());
                    Thread.yield();
                } else {
                    if (account.withdraw(conjuntoValores[sorteioValor])) {
                        System.out.println("Cliente: " + Thread.currentThread().getName() + " retirou "
                                + conjuntoValores[sorteioValor]);
                        System.out.println(account.toString());
                        Thread.yield();
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
