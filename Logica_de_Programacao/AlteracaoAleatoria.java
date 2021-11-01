import java.util.Random;

public class AlteracaoAleatoria {
    public static boolean isFim(String s) {
        return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static void main(String[] args) {

        // A classe Random do Java gera números (ou letras) aleatórios
        // instanciando um objeto "gerador" da classe "Random"
        Random gerador = new Random();
        // seed gera números aleatórios
        gerador.setSeed(4);

        String palavra = MyIO.readLine();
        String auxiliar = "";

        while (!isFim(palavra)) {
            
            auxiliar = "";
            char primeiraLetraSorteada = (char) ( 'a' + (Math.abs(gerador.nextInt()) % 26));
            char segundaLetraSorteada = (char) ( 'a' + (Math.abs(gerador.nextInt()) % 26));
                
            for (int i = 0; i < palavra.length(); i++) {
                if (palavra.charAt(i) == primeiraLetraSorteada) {
                    // concateno a string "auxiliar" com o caractere "letraSorteada"
                    auxiliar += segundaLetraSorteada;
                }else {
                    auxiliar += palavra.charAt(i);
                }
            }

            MyIO.println(auxiliar);
            palavra = MyIO.readLine();
        }
    }
}
