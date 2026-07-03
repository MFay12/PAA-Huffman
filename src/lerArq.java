import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class lerArq {
    
    // 1. A Estrutura do Noh (Implementando Comparable para ordenar sozinho)
    public static class Noh implements Comparable<Noh> {
        char caractere;
        int frequencia;
        Noh esquerda;
        Noh direita;

        public Noh(char caractere, int frequencia) {
            this.caractere = caractere;
            this.frequencia = frequencia;
        }

        // Esta é a regra de ouro que ensina o Java a ordenar a lista
        @Override
        public int compareTo(Noh outro) {
            // Retorna negativo se a minha frequência for menor, positivo se for maior
            return Integer.compare(this.frequencia, outro.frequencia);
        }
        
        // Método apenas para imprimir bonitinho no console
        @Override
        public String toString() {
            return "['" + caractere + "' : " + frequencia + "]";
        }
    }

    public static void main(String[] args) {
        // Lembre-se de colocar um arquivo .txt real aqui para testar
        String caminhoArquivo = "PAAT1/src/dataset_200MB_real.txt";

        try {
            // Restaurando a inicialização do leitor para o código compilar
            FileInputStream fis = new FileInputStream(caminhoArquivo);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            
            Map<Character, Integer> mapaFrequencias = new HashMap<>();

            System.out.println("Lendo o arquivo e contando caracteres...");
            
            int codigoCaractere;
            while ((codigoCaractere = br.read()) != -1) {
                char caractere = (char) codigoCaractere;
                mapaFrequencias.put(caractere, mapaFrequencias.getOrDefault(caractere, 0) + 1);
            }

            br.close();
            System.out.println("Leitura finalizada! Caracteres únicos: " + mapaFrequencias.size());

            // --- TRANSFERINDO DO MAPA PARA A LISTA DE NÓS ---
            
            System.out.println("\nTransferindo dados para a Lista de Nós...");
            List<Noh> listaDeNos = new ArrayList<>();

            // O for-each varre cada "par" (chave e valor) dentro do mapa
            for (Map.Entry<Character, Integer> entrada : mapaFrequencias.entrySet()) {
                char letra = entrada.getKey();
                int freq = entrada.getValue();
                
                // Instancia o Noh e joga na lista
                listaDeNos.add(new Noh(letra, freq));
            }

            // A mágica: como o Noh implementa Comparable, Collections.sort ordena a lista inteira instantaneamente!
            Collections.sort(listaDeNos);

            System.out.println("Lista criada e ordenada com sucesso!\n");
            
            // Teste de Sanidade: Imprimindo os 5 caracteres menos frequentes
            System.out.println("Os 5 caracteres MENOS frequentes (Que ficarão no fundo da Árvore): ");
            for (int i = 0; i < Math.min(5, listaDeNos.size()); i++) {
                System.out.println(listaDeNos.get(i));
            }

            // --- CONSTRUINDO A ÁRVORE DE HUFFMAN ---
            System.out.println("\nMontando a Árvore...");

            // O laço roda até sobrar apenas 1 nó na lista (que será a Raiz)
            while (listaDeNos.size() > 1) {
                
                // 1. Remove os dois nós com as menores frequências (índices 0 e 1)
                Noh esquerda = listaDeNos.remove(0);
                Noh direita = listaDeNos.remove(0);
                
                // 2. Cria o Nó Pai somando as frequências
                // Usamos o caractere '*' (ou qualquer outro) porque o pai não guarda letra, só os filhos guardam
                Noh pai = new Noh('*', esquerda.frequencia + direita.frequencia);
                pai.esquerda = esquerda;
                pai.direita = direita;
                
                // 3. Adiciona o pai de volta na lista
                listaDeNos.add(pai);
                
                // 4. Reordena a lista para que o pai caia na posição correta
                Collections.sort(listaDeNos);
            }
            
            // 5. O troféu do dia: A Raiz!
            Noh raizDaArvore = listaDeNos.get(0);
            
            System.out.println("Árvore construída com sucesso!");
            System.out.println("Frequência total na raiz: " + raizDaArvore.frequencia);

        } catch (Exception e) {
            System.out.println("Deu ruim ao ler o arquivo: " + e.getMessage());
        }
    }
}