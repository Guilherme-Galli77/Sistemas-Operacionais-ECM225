/**
 * @author Guilherme Samuel de Souza Barbosa  
 * @author Guilherme Cury Galli               
 * @author Gustavo Consoleti Ramirez de Souza 
 * @author Igor Eiki Ferreira Kubota          
 */


import sun.misc.Signal;

public class App {
    public static void main(String[] args) throws Exception {
        // Cria a conta compartilhada
        Account account = new Account(1000);
        
        // Lista de clientes
        Client listaClientes[] = {
            new Client(account, "Augustus"),
            new Client(account, "Lucius"),
            new Client(account, "Claudius"),
            new Client(account, "Tiberius")
        };

        // Captura CTRL + C para interrupção da simulação
        Signal.handle(new Signal("INT"), (Signal signal) -> {
            System.out.println("Terminando a simulação...");
            for (Client cliente : listaClientes) {
                cliente.interrupt();
            }
        });

        // Inicializa os clientes
        for (Client cliente : listaClientes) {
            cliente.start();
        }
    }
}
