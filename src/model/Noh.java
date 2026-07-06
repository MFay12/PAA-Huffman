package model;

public class Noh implements Comparable<Noh> {
    public char caractere;
    public int frequencia;
    public Noh esquerda;
    public Noh direita;

    // Entrada: Caractere a ser armazenado no nó e sua frequencia de aparicao
    // Retorno: nenhum
    // Pré-condição: Nenhuma
    // Pós-condição: Um novo objeto Noh eh instanciado com o caractere e a frequencia informados
    public Noh(char caractere, int frequencia) {
        this.caractere = caractere;
        this.frequencia = frequencia;
    }

    // Entrada: Um outro objeto Noh para comparacao
    // Retorno: Um valor negativo, zero ou positivo se a frequencia deste nó for menor, igual ou maior que a do outro nó
    // Pré-condição: O objeto 'outro' nao deve ser nulo
    // Pós-condição: A ordenacao baseada nas frequencias dos nos eh estabelecida (usada pela PriorityQueue)
    @Override
    public int compareTo(Noh outro) {
        return Integer.compare(this.frequencia, outro.frequencia);
    }

    // Entrada: nenhuma
    // Retorno: Uma String formatada representando o nó
    // Pré-condição: Nenhuma
    // Pós-condição: Uma string legivel contendo o caractere e a frequencia eh retornada para exibicao no console
    @Override
    public String toString() {
        return "['" + caractere + "' : " + frequencia + "]";
    }
}
