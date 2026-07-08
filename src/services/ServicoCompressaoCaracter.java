package services;

import core.CodificadorHuffman;
import core.Compactador;
import core.GerenciadorIO;
import model.Noh;

import java.util.Map;

public class ServicoCompressaoCaracter {

    private CodificadorHuffman<Character> codificador;
    private Compactador compactador;

    // Entrada: nenhuma
    // Retorno: nenhum
    // Pré-condição: Nenhuma
    // Pós-condição: As instancias de CodificadorHuffman e Compactador sao criadas e o servico fica pronto para uso
    public ServicoCompressaoCaracter() {
        this.codificador = new CodificadorHuffman();
        this.compactador = new Compactador();
    }

    // Entrada: Caminho do arquivo original (caminhoEntrada) e o caminho onde o arquivo compactado sera salvo (caminhoSaida)
    // Retorno: nenhum
    // Pré-condição: O arquivo especificado em caminhoEntrada deve existir e estar acessivel para leitura
    // Pós-condição: O arquivo eh lido, a arvore e o dicionario sao gerados, e um novo arquivo compactado em binario eh criado no caminhoSaida
    public void executarCompactacao(String caminhoEntrada, String caminhoSaida) throws Exception {
        Map<Character, Integer> frequencias = GerenciadorIO.gerarFrequencias(caminhoEntrada);

        Noh raiz = codificador.construirArvore(frequencias);

        Map<Character, String> dicionario = codificador.gerarDicionario(raiz);
        
        compactador.compactar(caminhoEntrada, caminhoSaida, dicionario, frequencias); 
        System.out.println("Compactação concluída com sucesso!");
    }
}
