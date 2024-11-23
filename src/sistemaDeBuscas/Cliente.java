package sistemaDeBuscas;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {
    private static final String SERVER_A_HOST = "localhost";
    private static final int PORT_A = 5000;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (Socket socket = new Socket(SERVER_A_HOST, PORT_A);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("Conexão estabelecida com o Servidor A.");
            System.out.println("Digite sua busca ou 'sair' para encerrar:");

            while (true) {
                System.out.print("Busca: ");
                String busca = scanner.nextLine();

                if (busca.equalsIgnoreCase("sair")) {
                    System.out.println("Encerrando o programa...");
                    break;
                }

                out.println(busca);
                System.out.println("Busca enviada: " + busca);

                String resultado;
                System.out.println("Resultados:");
                while ((resultado = in.readLine()) != null) {
                    if (resultado.equals("FINAL_DOS_RESULTADOS")) {
                        break;
                    }
                    System.out.println(resultado);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro na comunicação com o servidor: " + e.getMessage());
            e.printStackTrace();
        }

        scanner.close();
    }
}
