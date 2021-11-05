public class PalindromoRecursivo {
    public static boolean isFim(String s) {
        return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static boolean palindromo(String texto, int i) {
        Boolean palindromo = true;

        // condição de parada
        if (i >= ((texto.length() - 1) / 2)) {

            if (texto.charAt(i) != texto.charAt(texto.length() - 1 - i)) {
                palindromo = false;
            } else {
                // chamada recursiva
                palindromo(texto, i - 1);
            }

        }
        return palindromo;
    }

    public static void main(String[] args) {
        Boolean palindromo = true;
        String texto = MyIO.readLine();

        while (!isFim(texto)) {
            MyIO.println(palindromo(texto, texto.length() - 1) ? "SIM" : "NAO");
            texto = MyIO.readLine();
        }
    }
}