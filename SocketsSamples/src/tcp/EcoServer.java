package tcp;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class EcoServer {

    public static void main(String[] args) {

        int port = 5000;
        ServerSocket servidor;

        try {
            servidor = new ServerSocket(port);
            String ip = InetAddress.getByName("localhost").getHostAddress();
            System.out.printf("Servidor %s aguardando conexão na porta %d\n", ip, port);

            while (true) {
                Socket cliente = servidor.accept();
                String ipCliente = cliente.getInetAddress().getHostAddress();
                System.out.println("Nova conexão com o cliente " + ipCliente);              
                Scanner entrada = new Scanner(cliente.getInputStream());
                PrintStream saida = new PrintStream(cliente.getOutputStream());
                while (entrada.hasNextLine()) {
                    String texto = entrada.nextLine();
                    System.out.println("Recebido pelo cliente " + ipCliente + ": "  + texto);                    
                    saida.println("<eco> " + texto.toUpperCase());
                }
                entrada.close();
                saida.close();
                cliente.close();
                //servidor.close();
                System.out.println("Conexão finalizada. Aguardando nova conexão...");
            }
        } catch (IOException ex) {
            System.out.println("Erro ao abrir socket do servidor.");
        }
    }
}
