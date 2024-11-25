package sistemaDeBuscas;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import org.json.JSONObject;

public class ManipulacaoDeDados {
    public static String[][] lerDados(String caminhoArquivo) {
        List<String[]> dados = new ArrayList<>();

        try {
            String conteudo = new String(Files.readAllBytes(Paths.get(caminhoArquivo)));
            JSONObject jsonObject = new JSONObject(conteudo);
            JSONObject titles = jsonObject.getJSONObject("title");

            for (String key : titles.keySet()) {
                String titulo = titles.getString(key);
                dados.add(new String[] { key, titulo });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return dados.toArray(new String[0][]);
    }

    public static String realizarBusca(String busca, String[][] dados) {
        StringBuilder resultados = new StringBuilder();
        String buscaLower = busca.toLowerCase(); 

        for (String[] dado : dados) {
            String id = dado[0];
            String titulo = dado[1].toLowerCase();

            if (naiveStringMatching(titulo, buscaLower)) {
                resultados.append("ID: ").append(id)
                          .append("\nTítulo: ").append(dado[1])
                          .append("\n\n");
            }
        }

        return resultados.toString().isEmpty() ? "[Nenhum resultado encontrado]" : resultados.toString();
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
