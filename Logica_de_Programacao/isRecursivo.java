public class isRecursivo {
    public static boolean isFim(String s) {
        return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static boolean soVogais(String palavra, int posAtual) {
        int tam = palavra.length();
        boolean retorno = true;

        if (posAtual < tam) {
            if (palavra.charAt(posAtual) == 'a' || palavra.charAt(posAtual) == 'e' || palavra.charAt(posAtual) == 'i'
                    || palavra.charAt(posAtual) == 'o' || palavra.charAt(posAtual) == 'u'
                    || palavra.charAt(posAtual) == 'A' || palavra.charAt(posAtual) == 'E'
                    || palavra.charAt(posAtual) == 'I' || palavra.charAt(posAtual) == 'O'
                    || palavra.charAt(posAtual) == 'U') {
                retorno = soVogais(palavra, posAtual + 1);
            } else {
                retorno = false;
            }
        }
        return retorno;
    }

    public static boolean soConsoantes(String palavra, int posAtual) {
        int tam = palavra.length();
        boolean retorno = true;

        if (posAtual < tam) {
            if ((palavra.charAt(posAtual) != 'a' && palavra.charAt(posAtual) != 'e' && palavra.charAt(posAtual) != 'i'
                    && palavra.charAt(posAtual) != 'o' && palavra.charAt(posAtual) != 'u'
                    && palavra.charAt(posAtual) != 'A' && palavra.charAt(posAtual) != 'E'
                    && palavra.charAt(posAtual) != 'I' && palavra.charAt(posAtual) != 'O'
                    && palavra.charAt(posAtual) != 'U')
                    && ((palavra.charAt(posAtual) > 'a' && palavra.charAt(posAtual) < 'z')
                            || (palavra.charAt(posAtual) > 'A' && palavra.charAt(posAtual) < 'Z'))) {
                retorno = soConsoantes(palavra, posAtual + 1);
            } else {
                retorno = false;
            }
        }
        return retorno;
    }

    public static boolean soInteiros(String numero, int posAtual) {
        int tam = numero.length();
        boolean retorno = true;

        if (posAtual < tam) {
            if (numero.charAt(posAtual) >= 48 && numero.charAt(posAtual) <= 57) {
                retorno = soInteiros(numero, posAtual + 1);
            } else {
                retorno = false;
            }
        }
        return retorno;
    }

    public static boolean isReal(String numero, int posAtual) {
        int tam = numero.length();
        boolean retorno = true;

        if (posAtual < tam) {
            if ((numero.charAt(posAtual) >= 48 && numero.charAt(posAtual) <= 57)
                    || (numero.charAt(posAtual) == '.' || numero.charAt(posAtual) == ',')) {
                retorno = isReal(numero, posAtual + 1);
            } else {
                retorno = false;
            }
        }
        return retorno;
    }

    public static void main(String[] args) {
        String texto = MyIO.readLine();

        while (!isFim(texto)) {

            MyIO.print(soVogais(texto, 0) ? "SIM " : "NAO ");
            MyIO.print(soConsoantes(texto, 0) ? "SIM " : "NAO ");
            MyIO.print(soInteiros(texto, 0) ? "SIM " : "NAO ");
            MyIO.print(isReal(texto, 0) ? "SIM\n" : "NAO\n");

            texto = MyIO.readLine();

        }
    }
}