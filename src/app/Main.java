package app;

import java.util.Scanner;
import services.ServicoCompressaoCaracter;
import services.ServicoCompressaoPalavra;
import services.ServicoDescompressaoCaracter;
import services.ServicoDescompressaoPalavra;

public class Main {

    // Entrada: Argumentos da linha de comando (String[] args)
    // Retorno: nenhum
    // Pré-condição: Nenhuma
    // Pós-condição: O menu principal com as opcoes do sistema eh exibido na tela, as entradas do usuario sao lidas e as operacoes correspondentes sao executadas
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ServicoCompressaoCaracter servicoCompCaracter = new ServicoCompressaoCaracter();
        ServicoDescompressaoCaracter servicoDescCaracter = new ServicoDescompressaoCaracter();

        // 2. AQUI: Faltava instanciar os serviços de Palavras!
        ServicoCompressaoPalavra servicoCompPalavra = new ServicoCompressaoPalavra();
        ServicoDescompressaoPalavra servicoDescPalavra = new ServicoDescompressaoPalavra();
        boolean rodando = true;

        while (rodando) {
            System.out.println("\n=================== SISTEMA DE COMPRESSÃO DE ARQUIVOS HUFFMAN ===================");
            System.out.println("1. Compactar arquivo (usando codificação por caracter)");
            System.out.println("2. Descompactar arquivo (usando codificação por caracter)");
            System.out.println("3. Compactar arquivo (usando codificação por palavra)");
            System.out.println("4. Descompactar arquivo (usando codificação por palavra)");
            System.out.println("0. Sair");
            System.out.println("===================================================================================");
            System.out.print("\nDigite o número da opção que deseja utilizar: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao == 0) {
                rodando = false;
                System.out.println("Encerrando...");
                continue;
            }

            System.out.print("\nPor gentileza, informe o caminho completo do arquivo de origem: ");
            String caminhoEntrada = scanner.nextLine();

            System.out.print("\nInforme o caminho para salvar o arquivo de destino: ");
            String caminhoSaida = scanner.nextLine();

            // Adicionado o bloco 'try' para capturar as exceções das operações de arquivo
            try {
                switch (opcao) {
                    case 1:
                        System.out.println("Iniciando compressão por caracter para  " + caminhoEntrada);
                        servicoCompCaracter.executarCompactacao(caminhoEntrada, caminhoSaida);
                        break;
                    case 2:
                        System.out.println("Iniciando descompressão por caracter para " + caminhoEntrada);
                        servicoDescCaracter.executarDescompactacao(caminhoEntrada, caminhoSaida);
                        break;
                    case 3:
                        System.out.println("Iniciando compressão por palavra para " + caminhoEntrada);
                        servicoCompPalavra.executarCompactacao(caminhoEntrada, caminhoSaida);
                        break;
                    case 4:
                        System.out.println("Iniciando descompressão por palavra para " + caminhoEntrada);
                        servicoDescPalavra.executarDescompactacao(caminhoEntrada, caminhoSaida);
                        break;
                    default:
                        System.out.println("Opção inválida... Tente novamente.");
                }
            } catch (Exception e) {
                System.out.println("Erro na operação: " + e.getMessage());
                e.printStackTrace();
            }
        }
        scanner.close();
    }
}