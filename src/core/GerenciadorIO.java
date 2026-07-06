package core;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class GerenciadorIO {

    // Entrada: O caminho (String) do arquivo de texto a ser lido
    // Retorno: Um mapa (Map) contendo cada caractere lido e o numero de vezes que ele aparece
    // Pré-condição: O arquivo no caminho informado deve existir e ser um arquivo de texto legivel
    // Pós-condição: O arquivo eh lido inteiramente, a tabela de frequencias eh contabilizada e o mapa eh retornado
    public static Map<Character, Integer> gerarFrequencias(String caminhoArquivo) throws IOException {
        Map<Character, Integer> mapaFrequencias = new HashMap<>();
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(caminhoArquivo), "UTF-8"))) {
            int charLido;
            while ((charLido = br.read()) != -1) {
                char caractere = (char) charLido;
                mapaFrequencias.put(caractere, mapaFrequencias.getOrDefault(caractere, 0) + 1);
            }
        }
        return mapaFrequencias;
    }
}
