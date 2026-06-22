import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class lerArq {

    public static void main(String[] args) {
        String caminhoArquivo = "src/teste.txt"; // Ajustar o caminho se necessário

        try {
            // 1. Abrindo o arquivo garantindo a leitura em UTF-8
            FileInputStream fis = new FileInputStream(caminhoArquivo);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);

            // 2. O BufferedReader é o que salva a memória em arquivos de 200MB
            BufferedReader br = new BufferedReader(isr);

            System.out.println("--- Lendo por Caractere ---");

            // Lemos a primeira letra FORA do while
            int codigoCaractere = br.read();
            // O método read() puxa um caractere por vez até chegar no fim (-1)
            while (codigoCaractere != -1) {
                char caractere = (char) codigoCaractere;
                System.out.print(caractere);

                //Anda pra frente
                codigoCaractere = br.read();
            }

            br.close();
            System.out.println("\n\nLeitura finalizada com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}


