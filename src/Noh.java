public class Noh implements Comparable<Noh> {
    char caractere;
    int frequencia;
    Noh esquerda;
    Noh direita;

    // Construtor
    public Noh(char caractere, int frequencia) {
        this.caractere = caractere;
        this.frequencia = frequencia;
    }

    // A regra de ouro para a Fila de Prioridade saber quem é o "menor"
    @Override
    public int compareTo(Noh outro) {
        return Integer.compare(this.frequencia, outro.frequencia);
    }

    /* Para debugar bonitinho depois, se precisar
    @Override
    public String toString() {
        return "['" + caractere + "' : " + frequencia + "]";
    }*/
}