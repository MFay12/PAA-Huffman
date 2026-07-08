package services;

import core.CodificadorHuffman;
import core.Compactador;
import core.GerenciadorIO;
import model.Noh;

import java.util.Map;

public class ServicoCompressaoPalavra {

    private CodificadorHuffman<String> codificador;
    private Compactador compactador;

    public ServicoCompressaoPalavra() {
        this.codificador = new CodificadorHuffman<>();
        this.compactador = new Compactador();
    }

    public void executarCompactacao(String caminhoEntrada, String caminhoSaida) throws Exception {
        Map<String, Integer> frequencias = GerenciadorIO.gerarFrequenciasPalavras(caminhoEntrada);
        Noh<String> raiz = codificador.construirArvore(frequencias);
        Map<String, String> dicionario = codificador.gerarDicionario(raiz);

        compactador.compactarPorPalavra(caminhoEntrada, caminhoSaida, dicionario, frequencias);

        System.out.println("Compactação por Palavra concluída com sucesso!");
    }
}