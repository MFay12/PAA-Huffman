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
    // NOVO MÉTODO: Lê o arquivo agrupando letras em palavras e separando pontuações
    public static Map<String, Integer> gerarFrequenciasPalavras(String caminhoArquivo) throws IOException {
        Map<String, Integer> mapaFrequencias = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(caminhoArquivo), "UTF-8"))) {
            StringBuilder construtorPalavra = new StringBuilder();
            int charLido;

            while ((charLido = br.read()) != -1) {
                char c = (char) charLido;

                // Se for letra ou número, vai aglomerando para formar a palavra
                if (Character.isLetterOrDigit(c)) {
                    construtorPalavra.append(c);
                } else {
                    // Se bateu num espaço ou pontuação, salva a palavra que estava se formando
                    if (construtorPalavra.length() > 0) {
                        String palavra = construtorPalavra.toString();
                        mapaFrequencias.put(palavra, mapaFrequencias.getOrDefault(palavra, 0) + 1);
                        construtorPalavra.setLength(0); // Limpa para a próxima palavra
                    }

                    // Salva o espaço ou pontuação como um símbolo individual no mapa
                    String separador = String.valueOf(c);
                    mapaFrequencias.put(separador, mapaFrequencias.getOrDefault(separador, 0) + 1);
                }
            }

            // Garante que a última palavra do texto também seja salva
            if (construtorPalavra.length() > 0) {
                String palavra = construtorPalavra.toString();
                mapaFrequencias.put(palavra, mapaFrequencias.getOrDefault(palavra, 0) + 1);
            }
        }
        return mapaFrequencias;
    }
}
