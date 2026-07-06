package core;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import model.Noh;

public class CodificadorHuffman {

    // Entrada: Um mapa contendo os caracteres e suas respectivas frequencias no texto
    // Retorno: O Noh raiz da Arvore de Huffman construida
    // Pré-condição: O mapa de frequencias nao deve ser nulo ou estar vazio
    // Pós-condição: A Arvore de Huffman eh construida agrupando progressivamente os nos de menores frequencias e a raiz eh retornada
    public Noh construirArvore(Map<Character, Integer> frequencias) {
        PriorityQueue<Noh> fila = new PriorityQueue<>();
        
        for (Map.Entry<Character, Integer> entry : frequencias.entrySet()) {
            fila.add(new Noh(entry.getKey(), entry.getValue()));
        }

        while (fila.size() > 1) {
            Noh esquerda = fila.poll(); // poll() remove e retorna o menor
            Noh direita = fila.poll();
            
            Noh pai = new Noh('*', esquerda.frequencia + direita.frequencia);
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
    public Map<Character, String> gerarDicionario(Noh raiz) {
        Map<Character, String> dicionario = new HashMap<>();
        gerarCaminho(raiz, "", dicionario);
        return dicionario;
    }

    // Entrada: O Noh atual sendo visitado, o caminho acumulado em String ("0" e "1"), e a referencia do mapa dicionario
    // Retorno: nenhum
    // Pré-condição: Nenhuma
    // Pós-condição: A arvore eh percorrida recursivamente, preenchendo o dicionario com o codigo correspondente quando atinge uma folha
    private void gerarCaminho(Noh noh, String caminho, Map<Character, String> dicionario) {
        if (noh == null) return;

        // se for um nó folha (não tem filhos), guarda o caractere e o caminho de 0s e 1s
        if (noh.esquerda == null && noh.direita == null) {
            dicionario.put(noh.caractere, caminho);
        }

        gerarCaminho(noh.esquerda, caminho + "0", dicionario);
        gerarCaminho(noh.direita, caminho + "1", dicionario);
    }
}