package services;

import core.CodificadorHuffman;
import core.Descompactador;
import core.GerenciadorIO;
import model.Noh;

public class ServicoDescompressaoCaracter {
    
    private CodificadorHuffman<Character> codificador;
    private Descompactador descompactador;


    // Entrada: nenhuma
    // Retorno: nenhum
    // Pré-condição: Nenhuma
    // Pós-condição: As instancias de CodificadorHuffman e Descompactador sao criadas e o servico fica pronto para uso
    public ServicoDescompressaoCaracter() {
    this.codificador = new CodificadorHuffman();
    this.descompactador = new Descompactador();
    }

    // Entrada: Caminho do arquivo compactado (caminhoEntrada) e o caminho onde o arquivo restaurado sera salvo (caminhoSaida)
    // Retorno: nenhum
    // Pré-condição: O arquivo em caminhoEntrada deve existir, estar acessivel e ser um binario compactado pelo sistema
    // Pós-condição: O arquivo binario eh decodificado usando a arvore reconstruida e o texto original eh restaurado no caminhoSaida
    public void executarDescompactacao(String caminhoEntrada, String caminhoSaida) throws Exception {
        descompactador.descompactar(caminhoEntrada, caminhoSaida, codificador);
        System.out.println("Descompressão concluída com sucesso!");
    }
}
