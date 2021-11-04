import java.io.*;
import java.net.*;

class Teste {

    public static boolean isFim(String s) {
        return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static String getHtml(String endereco) {
        URL url;
        InputStream is = null;
        BufferedReader br;
        String resp = "", line;

        try {
            url = new URL(endereco);
            is = url.openStream(); // throws an IOException
            br = new BufferedReader(new InputStreamReader(is));

            while ((line = br.readLine()) != null) {
                resp += line + "\n";
            }
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        try {
            is.close();
        } catch (IOException ioe) {
            // nothing to see here

        }

        return resp;
    }

    public static void contarLetras(String palavra) {
        int countX1 = 0, countX2 = 0, countX3 = 0, countX4 = 0, countX5 = 0, countX6 = 0, countX7 = 0, countX8 = 0,
                countX9 = 0, countX10 = 0, countX11 = 0, countX12 = 0, countX13 = 0, countX14 = 0, countX15 = 0,
                countX16 = 0, countX17 = 0, countX18 = 0, countX19 = 0, countX20 = 0, countX21 = 0, countX22 = 0;
        int table = quantTable(palavra);
        for (int i = 0; i < palavra.length(); i++) {
            if (palavra.charAt(i) == 'a')// a
                countX1++;
            else if (palavra.charAt(i) == 'e')// e
                countX2++;
            if (palavra.charAt(i) == 'i')// i
                countX3++;
            else if (palavra.charAt(i) == 'o')// o
                countX4++;
            if (palavra.charAt(i) == 'u')// u
                countX5++;
            else if (palavra.charAt(i) == '�')// �
                countX6++;
            if (palavra.charAt(i) == '�')// �
                countX7++;
            else if (palavra.charAt(i) == '�')// �
                countX8++;
            if (palavra.charAt(i) == '�')// �
                countX9++;
            else if (palavra.charAt(i) == '�')// �
                countX10++;
            if (palavra.charAt(i) == '�')// �
                countX11++;
            else if (palavra.charAt(i) == '�')// �
                countX12++;
            if (palavra.charAt(i) == '�')// �
                countX13++;
            else if (palavra.charAt(i) == '�')// �
                countX14++;
            if (palavra.charAt(i) == '�')// �
                countX15++;
            else if (palavra.charAt(i) == '�')// �
                countX16++;
            if (palavra.charAt(i) == '�')// �
                countX17++;
            else if (palavra.charAt(i) == '�')// �
                countX18++;
            if (palavra.charAt(i) == '�')// �
                countX19++;
            else if (palavra.charAt(i) == '�')// �
                countX20++;
            if (palavra.charAt(i) == '�')// �
                countX21++;
            else if (palavra.charAt(i) == '�')// �
                countX22++;
        }
        countX1 -= table;
        countX2 -= table;
        // MyIO.setCharset(ISO 8859-1);
        MyIO.print("a(" + countX1 + ") e(" + countX2 + ") i(" + countX3 + ") o(" + countX4 + ") u(" + countX5 + ") "
                + "�(" + countX6 + ") ");
        MyIO.print("�(" + countX7 + ") " + "�(" + countX8 + ") " + "�(" + countX9 + ") " + "�(" + countX10 + ") " + "�("
                + countX11 + ") " + "�(" + countX12 + ") ");
        MyIO.print("�(" + countX13 + ") " + "�(" + countX14 + ") " + "�(" + countX15 + ") " + "�(" + countX16 + ") "
                + "�(" + countX17 + ") " + "�(" + countX18 + ") ");
        MyIO.print("�(" + countX19 + ") " + "�(" + countX20 + ") " + "�(" + countX21 + ") " + "�(" + countX22 + ") ");
    }

    public static void numeroConsoantes(String palavra) { // vogais = a, e, i, o ou u !!!
        // se n�o � vogal � consoante
        int count = 0;
        int br = quantBr(palavra);
        int table = quantTable(palavra);
        for (int i = 0; i < palavra.length(); i++) {
            if (palavra.charAt(i) > 'a' && palavra.charAt(i) != 'e' && palavra.charAt(i) != 'i'
                    && palavra.charAt(i) != 'o' && palavra.charAt(i) != 'u' && palavra.charAt(i) != ' '
                    && palavra.charAt(i) <= 'z') {
                // pois se for >= 192 vai contar os caracteres com acento
                count++;// SE for diferente de TODAS AS VOGAIS(E a, E b...)
            }
        }
        count -= 2 * br + 3 * table;
        MyIO.print("consoante(" + count + ") <br>(" + br + ") <table>(" + table + ") ");
    }

    public static int quantBr(String str) {
        int quant = 0;
        for (int i = 0; i < str.length(); i++) {
            char letra = str.charAt(i);
            if (letra == '<')
                if (str.charAt(i + 1) == '\u0062')
                    if (str.charAt(i + 2) == '\u0072')
                        if (str.charAt(i + 3) == '>')
                            quant++;
        } // fim for i
        return quant;
    }// fim qunatBr

    public static int quantTable(String str) {
        int quant = 0;
        for (int i = 0; i < str.length(); i++) {
            char letra = str.charAt(i);
            if (letra == '<')
                if (str.charAt(i + 1) == '\u0074')
                    if (str.charAt(i + 2) == '\u0061')
                        if (str.charAt(i + 3) == '\u0062')
                            if (str.charAt(i + 4) == '\u006c')
                                if (str.charAt(i + 5) == '\u0065')
                                    if (str.charAt(i + 6) == '>')
                                        quant++;
        } // fim for i
        return quant;
    }// fim quantTable

    public static void main(String[] args) {
        // Mostra os caracteres com acento
        MyIO.setCharset("UTF-8");
        // String endereco, html;
        // endereco = "http://maratona.crc.pucminas.br/series/Friends.html";
        // html = getHtml(endereco);
        // System.out.print(html);
        // contarLetras(html);
        // numeroConsoantes(html);

        String titulo = MyIO.readLine();

        while (!isFim(titulo)) {

            String html = MyIO.readLine();
            String endereco = getHtml(html);

            contarLetras(endereco);
            numeroConsoantes(endereco);
            MyIO.print(titulo);
            MyIO.print("\n");

            titulo = MyIO.readLine();

        }
    }
}