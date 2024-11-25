package sistemaDeBuscas;

import java.io.*;
import java.net.*;

public class ServidorB {
    private static final int PORT_B = 6000;

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT_B)) {
			System.out.println("Servidor B está rodando na porta " + PORT_B);

			while (true) {
			    Socket socket = serverSocket.accept();
			    Thread requestHandler = new Thread(new Runnable() {
			        public void run() {
			            handleRequest(socket);
			        }
			    });
			    requestHandler.start();
			}
		}
    }

    private static void handleRequest(Socket socket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String busca = in.readLine();
            System.out.println("Busca recebida do Servidor A: " + busca);
            
            String[][] dadosRemotos = ManipulacaoDeDados.lerDados("arquivos_dados\\data_B.json"); //!!
            
            String resultado = ManipulacaoDeDados.realizarBusca(busca, dadosRemotos);
            out.println(resultado);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
