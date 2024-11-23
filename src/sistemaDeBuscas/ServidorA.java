package sistemaDeBuscas;

import java.io.*;
import java.net.*;

public class ServidorA {
    private static final int PORT_A = 5000;
    private static final String SERVER_B_HOST = "localhost";
    private static final int PORT_B = 6000;

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT_A)) {
			System.out.println("Servidor A está rodando na porta " + PORT_A);

			while (true) {
			    Socket clientSocket = serverSocket.accept();
			    Thread clientHandler = new Thread(new Runnable() {
			        public void run() {
			            handleClient(clientSocket);
			        }
			    });
			    clientHandler.start();
			}
		}
    }

    private static void handleClient(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String busca = in.readLine();
            System.out.println("Busca recebida do cliente: " + busca);
            
            //String[][] dadosLocais = ManipulacaoDeDados.lerDados("arquivos_dados\\data_A.json");
            
            // MECANISMO DE BUSCAS
            Thread threadLocal = new Thread(new Runnable() {
                public void run() {
                    //String resultadoLocal = //FUNÇÃO DE BUSCA
                    synchronized (out) {
                        //out.println("Resultados do Servidor A:\n" + resultadoLocal);
                        //out.println(resultadoLocal);
                        out.println("FINAL_DOS_RESULTADOS");
                    }
                }
            });
            
            Thread threadRemoto = new Thread(new Runnable() {
                public void run() {
                    String resultadoRemoto = enviarParaServidorB(busca);
                    synchronized (out) {
                        out.println("Resultados do Servidor B:\n" + resultadoRemoto);
                        out.println(resultadoRemoto);
                        out.println("FINAL_DOS_RESULTADOS");
                    }
                }
            });
            threadLocal.start();
            threadRemoto.start();

            threadRemoto.join();
            threadRemoto.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String enviarParaServidorB(String busca) {
        try (Socket socketB = new Socket(SERVER_B_HOST, PORT_B);
             PrintWriter out = new PrintWriter(socketB.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socketB.getInputStream()))) {

            out.println(busca);
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return "Erro na comunicação com o Servidor B";
        }
    }
}
