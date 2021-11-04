import java.io.*;
import java.util.Locale;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Arquivo {
    public static String formataDados(String dado) {
        dado = dado.replaceAll(",", ".");
        return dado;
    }

    public static void main(String[] args) {

        try {
            RandomAccessFile raf = new RandomAccessFile("Arquivo.txt", "rw");
            DecimalFormat df = new DecimalFormat();

            // set the file pointer at 0 position
            // metodo seek análogo ao array
            raf.seek(0);

            int n = MyIO.readInt();
            int i = 0;
            // metodo de leitura e escrita sequencial dos dados no arquivo
            while (i < n) {
                float d = MyIO.readFloat();
                // calling this method will advance the file position of the RandomAccessFile by
                // 1
                raf.writeFloat(d);
                // avanço 4 bytes(float) no arquivo
                raf.seek(i * 4);
                raf.readFloat();
                i++;
            }
            /*
             * return the file pointer // long position = raf.getFilePointer(); the position
             * varies according to the number of bytes of the RandomAccessFile
             */
            // função para leitura de tras pra frente
            for (int j = n - 1; j >= 0; j--) {
                raf.seek(j * 4);
                float num = raf.readFloat();
                // printa o dado do ponteiro correspondente
                // inteiro
                if (num % 1 == 0) {
                    // inteiro
                    MyIO.println((int) num);
                } else {
                    df.format(num);
                    String s = num + "";
                    MyIO.println(formataDados(s));
                }

            }

            raf.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
