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

public class Descompactador {

    // Entrada: Caminho do arquivo compactado (caminhoEntrada), caminho do arquivo restaurado (caminhoSaida) e a instancia do codificador
    // Retorno: nenhum
    // Pré-condição: O arquivo binario de entrada deve existir e possuir um cabecalho valido gravado pelo Compactador
    // Pós-condição: O cabecalho eh lido para reconstruir a arvore, os bytes sao lidos e decodificados bit a bit navegando na arvore, e o texto eh salvo
    public void descompactar(String caminhoEntrada, String caminhoSaida, CodificadorHuffman codificador) throws IOException, ClassNotFoundException {
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminhoEntrada));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(caminhoSaida), "UTF-8"))) {
             
             // lê o cabeçalho
             @SuppressWarnings("unchecked")
             Map<Character, Integer> frequencias = (Map<Character, Integer>) ois.readObject();

             // reconstrói a árvore
             Noh raiz = codificador.construirArvore(frequencias);
             
             int totalCaracteres = raiz.frequencia;
             int caracteresDecodificados = 0;
             Noh atual = raiz;

             // le os bytes e navega na árvore
             try {
                 while (caracteresDecodificados < totalCaracteres) {
                     byte b = ois.readByte();
                     
                     // bit a bit do byte atual
                     for (int i = 7; i >= 0; i--) {
                         int bit = (b >> i) & 1; // extrai o bit específico
                         
                         if (bit == 0) atual = atual.esquerda;
                         else atual = atual.direita;

                         // se chegar em um nó folha
                         if (atual.esquerda == null && atual.direita == null) {
                             bw.write(atual.caractere);
                             caracteresDecodificados++;
                             atual = raiz; // volta pro topo da árvore para a próxima letra
                             
                             // finalizou, quebra o laço forçando a ignorar os bits de lixo restantes
                             if (caracteresDecodificados == totalCaracteres) {
                                 break; 
                             }
                         }
                     }
                 }
             } catch (EOFException e) {
             }
        }
    }
}
