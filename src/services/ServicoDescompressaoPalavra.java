package services;

import core.CodificadorHuffman;
// 1. Importando a sua classe nova
import core.DescompactadorPalavra;

public class ServicoDescompressaoPalavra {

    private CodificadorHuffman<String> codificador;
    // 2. Usando a sua classe nova
    private DescompactadorPalavra descompactador;

    public ServicoDescompressaoPalavra() {
        this.codificador = new CodificadorHuffman<>();
        // 3. Instanciando a classe nova
        this.descompactador = new DescompactadorPalavra();
    }

    public void executarDescompactacao(String caminhoEntrada, String caminhoSaida) throws Exception {
        // 4. Chamando o método com o nome correto
        descompactador.descompactarPorPalavra(caminhoEntrada, caminhoSaida, codificador);
        System.out.println("Descompressão por Palavra concluída com sucesso!");
    }
}