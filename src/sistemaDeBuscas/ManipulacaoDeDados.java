package sistemaDeBuscas;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import org.json.JSONObject;

public class ManipulacaoDeDados {

    public static String realizarBusca(String busca, String[][] dados) {
        StringBuilder resultados = new StringBuilder();
        String buscaLower = busca.toLowerCase();

        for (String[] dado : dados) {
            String id = dado[0];
            String titulo = dado[1].toLowerCase();
            String introducao = dado[2].toLowerCase();

            if (naiveStringMatching(titulo, buscaLower) || naiveStringMatching(introducao, buscaLower)) {
                resultados.append("ID: ").append(id)
                          .append("\nTítulo: ").append(dado[1])
                          .append("\nIntrodução: ").append(dado[2])
                          .append("\n\n");
            }
        }

        return resultados.toString().isEmpty() ? "[Nenhum resultado encontrado]" : resultados.toString();
    }

    public static String[][] lerDados(String caminhoArquivo) {
        List<String[]> dados = new ArrayList<>();

        try {
            String conteudo = new String(Files.readAllBytes(Paths.get(caminhoArquivo)));
            JSONObject jsonObject = new JSONObject(conteudo);

            for (String key : jsonObject.getJSONObject("title").keySet()) {
                String titulo = jsonObject.getJSONObject("title").getString(key);
                String introducao = jsonObject.getJSONObject("abstract").getString(key);
                dados.add(new String[] { key, titulo, introducao });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return dados.toArray(new String[0][]);
    }

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