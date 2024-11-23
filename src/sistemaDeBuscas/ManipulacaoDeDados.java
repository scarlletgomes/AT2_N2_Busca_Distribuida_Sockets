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

}
