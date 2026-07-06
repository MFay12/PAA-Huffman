import java.util.Map;
import java.util.PriorityQueue;

public class Huffman {

    // Metodo que recebe a tabela de frequências e devolve a raiz da Árvore
    public static Noh construirArvore(Map<Character, Integer> mapaFrequencias) {
        
        // A Fila de Prioridade
        PriorityQueue<Noh> fila = new PriorityQueue<>();

        // 1. Popula a fila de forma explícita e passo a passo
        // Em Java ':' em um laço for significam "para cada item contido em".
        for (Map.Entry<Character, Integer> entrada : mapaFrequencias.entrySet()) {

            // Pega a letra (a chave do mapa)
            char letra = entrada.getKey();

            // Pega a quantidade de vezes que ela apareceu (o valor do mapa)
            int quantidade = entrada.getValue();

            // Cria um novo Nó com essas informações
            Noh novoNoh = new Noh(letra, quantidade);

            // Adiciona na fila (ela se auto-ordena aqui)
            fila.add(novoNoh);
        }

        // 2. O Laço Guloso de Huffman
        // Roda até sobrar apenas a raiz na fila
        while (fila.size() > 1) {
            // Remove os dois nós de menor frequência
            // Metodo poll() para remover o menor elemento da fila
            Noh esquerda = fila.poll(); 
            Noh direita = fila.poll();

            // Cria o nó pai somando as frequências
            Noh pai = new Noh('*', esquerda.frequencia + direita.frequencia);
            pai.esquerda = esquerda;
            pai.direita = direita;

            // Insere o pai de volta na fila (ela se auto-ordena!)
            // Metodo add() para inserir ordenado
            fila.add(pai);
        }

        // Retorna o único nó que sobrou: a Raiz da Árvore
        return fila.poll(); 
    }


    // NOVO MeTODO: Caminha pela árvore e gera os códigos binários
    public static void gerarDicionario(Noh raiz, String caminho, Map<Character, String> dicionario) {
        // Caso base: se o nó for nulo, volta (segurança)
        if (raiz == null) return;

        // Se for um nó folha (não tem filhos), encontramos um caractere!
        if (raiz.esquerda == null && raiz.direita == null) {
            dicionario.put(raiz.caractere, caminho);
            return;
        }

        // Chamadas recursivas:
        // Desce para a esquerda adicionando '0' ao caminho
        gerarDicionario(raiz.esquerda, caminho + "0", dicionario);
        // Desce para a direita adicionando '1' ao caminho
        gerarDicionario(raiz.direita, caminho + "1", dicionario);
    }




}