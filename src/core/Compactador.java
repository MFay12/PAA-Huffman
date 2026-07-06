package core;
import java.io.*;
import java.util.Map;

public class Compactador {

    // Entrada: Caminhos de entrada e saida, o dicionario de codigos binarios (0s e 1s) e o mapa de frequencias
    // Retorno: nenhum
    // Pré-condição: O arquivo de entrada deve existir. Dicionario e frequencias nao devem ser nulos
    // Pós-condição: O mapa de frequencias eh gravado no arquivo de saida (cabecalho) e os caracteres do texto original sao convertidos em bits (bytes fechados) e salvos
    public void compactar(String caminhoEntrada, String caminhoSaida, Map<Character, String> dicionario, Map<Character, Integer> frequencias) throws IOException {
        
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminhoSaida));
             BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(caminhoEntrada), "UTF-8"))) {
            
            // escreve a tabela de frequências no início do arquivo binário
            oos.writeObject(frequencias);

            int buffer = 0;
            int bitsNoBuffer = 0; // quando chegar a 8, grava o byte

            int charl;
            while ((charl = br.read()) != -1) {
                char caractere = (char) charl;
                String codigoBinario = dicionario.get(caractere);

                for (char bitChar : codigoBinario.toCharArray()) {
                    buffer = buffer << 1; // desloca os bits para a esquerda
                    if (bitChar == '1') {
                        buffer = buffer | 1; // seta o último bit como 1
                    }
                    bitsNoBuffer++;

                    // quando fechar 1 byte, escreve no arquivo
                    if (bitsNoBuffer == 8) {
                        oos.writeByte(buffer);
                        buffer = 0;
                        bitsNoBuffer = 0;
                    }
                }
            }

            // padding
            // se o último byte não ficou cheio, empurra eles para a esquerda
            if (bitsNoBuffer > 0) {
                buffer = buffer << (8 - bitsNoBuffer);
                oos.writeByte(buffer);
            }
            
            oos.writeByte(bitsNoBuffer); // guardando o padding no final do arquivo
        }
    }    
}
