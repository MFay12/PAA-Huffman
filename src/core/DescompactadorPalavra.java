package core;

import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.util.Map;

import model.Noh;

public class DescompactadorPalavra {
    // NOVO MÉTODO: Descompressão baseada em Palavras (Strings)
    public void descompactarPorPalavra(String caminhoEntrada, String caminhoSaida, CodificadorHuffman<String> codificador) throws IOException, ClassNotFoundException {

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminhoEntrada));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(caminhoSaida), "UTF-8"))) {

            // 1. Lê o cabeçalho avisando que as chaves são Strings
            @SuppressWarnings("unchecked")
            Map<String, Integer> frequencias = (Map<String, Integer>) ois.readObject();

            // 2. Reconstrói a árvore de Palavras
            Noh<String> raiz = codificador.construirArvore(frequencias);

            int totalPalavras = raiz.frequencia;
            int palavrasDecodificadas = 0;
            Noh<String> atual = raiz;

            try {
                while (palavrasDecodificadas < totalPalavras) {
                    byte b = ois.readByte();

                    for (int i = 7; i >= 0; i--) {
                        int bit = (b >> i) & 1;

                        if (bit == 0) atual = atual.esquerda;
                        else atual = atual.direita;

                        if (atual.esquerda == null && atual.direita == null) {
                            // Grava a String inteira no ficheiro (ex: "carro" ou " ")
                            bw.write(atual.simbolo);
                            palavrasDecodificadas++;
                            atual = raiz;

                            if (palavrasDecodificadas == totalPalavras) {
                                break;
                            }
                        }
                    }
                }
            } catch (EOFException e) {
                // Fim do ficheiro alcançado
            }
        }
    }
}
