public class CiframentoRecursivo {
    public static boolean isFim(String s) {
        return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static String ciframento(String palavra, int posAtual, String res) {
        // condicao de parada
        if (palavra.length() > posAtual) {
            int palavraCifrada = palavra.charAt(posAtual) + 3;
            res += (char) palavraCifrada;
            // chamada recursiva
            return ciframento(palavra, posAtual + 1, res);
        }
        return res;
    }

    public static void main(String[] args) {
        String test = MyIO.readLine();
        String res = "";

        while (!isFim(test)) {
            MyIO.println(ciframento(test, 0, res));
            // a cada loop, a string res será esvaziada
            res = "";
            test = MyIO.readLine();
        }
    }
}