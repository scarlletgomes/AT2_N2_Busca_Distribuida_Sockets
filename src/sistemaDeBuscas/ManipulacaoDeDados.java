package sistemaDeBuscas;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import org.json.JSONObject;

public class ManipulacaoDeDados {

    // Método para realizar a busca
    public static String realizarBusca(String busca, String[][] dados) {
        StringBuilder resultados = new StringBuilder();
        String buscaLower = busca.toLowerCase(); // Converte a busca para letras minúsculas

        for (String[] dado : dados) {
            String id = dado[0];
            String titulo = dado[1].toLowerCase(); // Título em minúsculas
            String introducao = dado[2].toLowerCase(); // Abstract (introdução) em minúsculas

            // Verifica se o termo está no título ou no abstract
            if (naiveStringMatching(titulo, buscaLower) || naiveStringMatching(introducao, buscaLower)) {
                resultados.append("ID: ").append(id)
                          .append("\nTítulo: ").append(dado[1])
                          .append("\nIntrodução: ").append(dado[2])
                          .append("\n\n");
            }
        }

        return resultados.toString().isEmpty() ? "[Nenhum resultado encontrado]" : resultados.toString();
    }

    // Método para ler os dados do arquivo JSON
    public static String[][] lerDados(String caminhoArquivo) {
        List<String[]> dados = new ArrayList<>();

        try {
            String conteudo = new String(Files.readAllBytes(Paths.get(caminhoArquivo)));
            JSONObject jsonObject = new JSONObject(conteudo);

            // Itera sobre os IDs no JSON
            for (String key : jsonObject.getJSONObject("title").keySet()) {
                String titulo = jsonObject.getJSONObject("title").getString(key); // Obtém o título
                String introducao = jsonObject.getJSONObject("abstract").getString(key); // Obtém o abstract
                dados.add(new String[] { key, titulo, introducao }); // Adiciona ID, título e introdução
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return dados.toArray(new String[0][]);
    }

    // Método para buscar strings (Naive String Matching)
    public static boolean naiveStringMatching(String texto, String padrao) {
        int n = texto.length();
        int m = padrao.length();

        for (int i = 0; i <= n - m; i++) {
            int j;
            for (j = 0; j < m; j++) {
                if (texto.charAt(i + j) != padrao.charAt(j)) {
                    break;
                }
            }

            if (j == m) {
                return true;
            }
        }

        return false;
    }
}