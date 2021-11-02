public class Is {
    //posso retornar uma expressão booleana, contanto que não esteja dentro de uma condicional ou um loop
    public static boolean isFim(String s) {
        return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static boolean isLetra(String palavra)
    {
        boolean retorno = false;
        for(int i = 0; i<palavra.length(); i++){
            if (palavra.charAt(i) >= 'A' && palavra.charAt(i) <= 'Z' || palavra.charAt(i) >= 'a' && palavra.charAt(i) <= 'z') retorno = true;
        }

        return retorno;
    }

    public static boolean isVogal(String palavra) { // retorno se É VOGAL
        // AO PRINTAR NA MAIN NÃO DÁ CERTO, MAS AO COMPARAR AS STRINGS NAS FUNÇÕES COM
        // ESTA FUNÇÃO DÁ CERTO
        // se não é vogal é consoante
        boolean retorno = false;
        for (int i = 0; i < palavra.length(); i++) {
            if (palavra.charAt(i) == 'a' || palavra.charAt(i) == 'e' || palavra.charAt(i) == 'i'
                    || palavra.charAt(i) == 'o' || palavra.charAt(i) == 'u')
                retorno = true;
        }
        return retorno;
    }

    public static boolean isConsoante(String palavra) { // vogais = a, e, i, o ou u !!!
        // se não é vogal é consoante
        boolean retorno = false;
        for (int i = 0; i < palavra.length(); i++) {
            if (palavra.charAt(i) != 'a' && palavra.charAt(i) != 'e' && palavra.charAt(i) != 'i'
                    && palavra.charAt(i) != 'o' && palavra.charAt(i) != 'u')
                retorno = true;// SE for diferente de TODAS AS VOGAIS(E a, E b...)
        }
        return retorno;
    }

    public static boolean soConsoantes(String palavra) { // Retorna se tem SOMENTE CONSOANTES
        // AO PRINTAR NA MAIN NÃO DÁ CERTO, MAS AO COMPARAR AS STRINGS NAS FUNÇÕES COM
        // ESTA FUNÇÃO DÁ CERTO
        boolean retorno = false;
        for (int i = 0; i < palavra.length(); i++) {
            if (isVogal(palavra) == false && isLetra(palavra) == true)
                retorno = true;
            else
                i = palavra.length();
        }
        return retorno;
    }

    public static boolean soVogais(String palavra) {
        boolean retorno = false;
        for (int i = 0; i < palavra.length(); i++) {
            if (isConsoante(palavra) == false)
                retorno = true;
            else
                i = palavra.length();
        }
        return retorno;
    }

    public static boolean isInteiro(String numero) {
        boolean retorno = true;
        // MyIO.println("Número passado como parâmetro: "+numero); // verificar número
        // que veio
        for (int i = 0; i < numero.length(); i++) {// String com valor "int"
            // devo converter o valor "int" da string para para o valor na tabela asc2
            // MyIO.println("Valor da posição na tabela asc2: " + (int) numero.charAt(i));
            if (numero.charAt(i) < 48 || numero.charAt(i) > 57)
                retorno = false;
        }
        return retorno;
    }

    public static boolean isReal(String numeroReal) {
        boolean retorno = false;
        // primeiro converto o caractere na posição "i" para int
        // MyIO.println("número que veio? " + numero);
        for (int i = 0; i < numeroReal.length(); i++) {// String com valor "int"
            // devo converter o valor "int" da string para para o valor na tabela asc2
            // todo número inteiro é real
            if (isInteiro(numeroReal) == true || (numeroReal.charAt(i) == '.' || numeroReal.charAt(i) == ',')
                && !isLetra(numeroReal)) {
                retorno = true;
            }
        }
        return retorno;
    }

    public static void main(String[] args) {

        String texto = MyIO.readLine();

        while (!isFim(texto)) {

            MyIO.print(soVogais(texto) ? "SIM " : "NAO ");
            MyIO.print(soConsoantes(texto) ? "SIM " : "NAO ");
            MyIO.print(isInteiro(texto) ? "SIM " : "NAO ");
            MyIO.print(isReal(texto) ? "SIM\n" : "NAO\n");

            texto = MyIO.readLine();

        }
    }

}
