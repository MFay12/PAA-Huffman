import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Sistema {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        // O Menu exigido pelas regras do trabalho
        while (opcao != 5) {
            System.out.println("\n=== COMPACTADOR HUFFMAN ===");
            System.out.println("1. Compactar arquivo (por caracter)");
            System.out.println("2. Descompactar arquivo (por caracter)");
            System.out.println("3. Compactar arquivo (por palavra)");
            System.out.println("4. Descompactar arquivo (por palavra)");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o "Enter" do buffer do teclado

            if (opcao == 1) {
                System.out.print("Digite o caminho do arquivo para compactar (ex: PAAT1/src/teste.txt): ");
                String caminhoArquivo = scanner.nextLine();
                
                // Buffer
                try {
                    FileInputStream fis = new FileInputStream(caminhoArquivo);
                    InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
                    BufferedReader br = new BufferedReader(isr);
                    
                    Map<Character, Integer> mapaFrequencias = new HashMap<>();
                    int codigoCaractere;
                    
                    System.out.println("Lendo o arquivo e contando frequências...");
                    while ((codigoCaractere = br.read()) != -1) {
                        char caractere = (char) codigoCaractere;
                        mapaFrequencias.put(caractere, mapaFrequencias.getOrDefault(caractere, 0) + 1);
                    }
                    br.close();
                    
                    System.out.println("Leitura finalizada. Construindo árvore...");
                    
                    // --- A INTEGRAÇÃO ---
                    // Aqui nós mandamos o seu mapa pronto para a classe Huffman trabalhar!
                    Noh raiz = Huffman.construirArvore(mapaFrequencias);
                    
                    System.out.println("Árvore construída com sucesso usando Fila de Prioridade!");
                    System.out.println("Frequência total na raiz: " + raiz.frequencia);

                    // ---DICIONÁRIO ---
                    System.out.println("Gerando dicionário de bits...");
                    Map<Character, String> dicionarioHuffman = new HashMap<>();

                    // Chama a função recursiva passando a raiz, um caminho inicial vazio e o mapa
                    Huffman.gerarDicionario(raiz, "", dicionarioHuffman);

                    System.out.println("Dicionário gerado com sucesso! Total de símbolos: " + dicionarioHuffman.size());

                    // Teste de sanidade: vamos ver o código gerado para a letra 'a' e para o espaço em branco
                    System.out.println("Código da letra 'a': " + dicionarioHuffman.get('a'));
                    System.out.println("Código do espaço ' ': " + dicionarioHuffman.get(' '));

                    gravarArquivoCompactado(caminhoArquivo, caminhoArquivo + ".huff", dicionarioHuffman);


                } catch (Exception e) {
                    System.out.println("Erro ao processar o arquivo: " + e.getMessage());
                }
                
            } else if (opcao >= 2 && opcao <= 4) {
                System.out.println("Calma lá! Esta funcionalidade ainda será desenvolvida.");
            }
        }
        
        System.out.println("Encerrando o sistema...");
        scanner.close();
    }
    // Metodo responsável por ler o texto original e ejetar os bytes comprimidos
    public static void gravarArquivoCompactado(String caminhoEntrada, String caminhoSaida, Map<Character, String> dicionario) {
        try {
            // Leitores e Escritores
            FileInputStream fis = new FileInputStream(caminhoEntrada);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);

            // FileOutputStream grava bytes puros, não texto!
            java.io.FileOutputStream fos = new java.io.FileOutputStream(caminhoSaida);

            StringBuilder funilDeBits = new StringBuilder();
            int codigoCaractere;

            System.out.println("Iniciando a compressão real (gravando bits)...");

            // 1. Lê o arquivo original de novo, caractere por caractere
            while ((codigoCaractere = br.read()) != -1) {
                char caractere = (char) codigoCaractere;

                // Pega o código de Huffman (ex: "101") e joga no funil
                String codigo = dicionario.get(caractere);
                if (codigo != null) {
                    funilDeBits.append(codigo);
                }

                // 2. Quando o funil acumular 8 bits (1 byte), nós ejetamos no arquivo!
                while (funilDeBits.length() >= 8) {
                    // Pega os 8 primeiros '0's e '1's
                    String byteTexto = funilDeBits.substring(0, 8);

                    // O Integer.parseInt com base 2 converte a String binária para um número real da máquina
                    int byteReal = Integer.parseInt(byteTexto, 2);

                    // Grava no arquivo final
                    fos.write(byteReal);

                    // Remove os 8 bits que já foram gravados do funil
                    funilDeBits.delete(0, 8);
                }
            }

            // 3. Lidando com a "rebarba" (O último byte incompleto)
            if (funilDeBits.length() > 0) {
                // Preenche com '0's no final até dar 8 bits para fechar o pacote
                while (funilDeBits.length() < 8) {
                    funilDeBits.append("0");
                }
                int byteReal = Integer.parseInt(funilDeBits.toString(), 2);
                fos.write(byteReal);
            }

            br.close();
            fos.close();
            System.out.println("Sucesso! Arquivo compactado salvo em: " + caminhoSaida);

        } catch (Exception e) {
            System.out.println("Erro na gravação: " + e.getMessage());
        }
    }
}