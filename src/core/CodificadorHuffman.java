package core;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import model.Noh;

public class CodificadorHuffman<T> {

    // Entrada: Um mapa contendo os caracteres e suas respectivas frequencias no texto
    // Retorno: O Noh raiz da Arvore de Huffman construida
    // Pré-condição: O mapa de frequencias nao deve ser nulo ou estar vazio
    // Pós-condição: A Arvore de Huffman eh construida agrupando progressivamente os nos de menores frequencias e a raiz eh retornada
    public Noh<T> construirArvore(Map< T, Integer> frequencias) {
        PriorityQueue<Noh<T>> fila = new PriorityQueue<>();
        
        for (Map.Entry< T, Integer> entry : frequencias.entrySet()) {
            fila.add(new Noh<>(entry.getKey(), entry.getValue()));
        }

        while (fila.size() > 1) {
            Noh<T> esquerda = fila.poll(); // poll() remove e retorna o menor
            Noh<T> direita = fila.poll();
            //Como o nó pai nãp guarda nem letra nem palavra, passamos null para o simbolo
            Noh<T> pai = new Noh<>(null, esquerda.frequencia + direita.frequencia);
            pai.esquerda = esquerda;
            pai.direita = direita;
            
            fila.add(pai);
        }
        return fila.poll(); // Retorna a raiz
    }

    // Entrada: O Noh raiz da Arvore de Huffman
    // Retorno: Um mapa contendo cada caractere do texto associado ao seu codigo binario (String)
    // Pré-condição: A raiz fornecida deve pertencer a uma Arvore de Huffman ja construida e valida
    // Pós-condição: O dicionario de conversao de caracteres para bits eh instanciado e retornado populado
    public Map<T, String> gerarDicionario(Noh<T> raiz) {
        Map<T, String> dicionario = new HashMap<>();
        gerarCaminho(raiz, "", dicionario);
        return dicionario;
    }

    // Entrada: O Noh atual sendo visitado, o caminho acumulado em String ("0" e "1"), e a referencia do mapa dicionario
    // Retorno: nenhum
    // Pré-condição: Nenhuma
    // Pós-condição: A arvore eh percorrida recursivamente, preenchendo o dicionario com o codigo correspondente quando atinge uma folha
    private void gerarCaminho(Noh<T> noh, String caminho, Map<T, String> dicionario) {
        if (noh == null) return;

        if (noh.esquerda == null && noh.direita == null) {
            // Alterado de 'caractere' para 'simbolo'
            dicionario.put(noh.simbolo, caminho);
        }

        gerarCaminho(noh.esquerda, caminho + "0", dicionario);
        gerarCaminho(noh.direita, caminho + "1", dicionario);
    }
}