// Os únicos métodos permitidos são char charAt(int) e int length()
// da classe String.

class Palindromo {
   public static boolean isFim(String s) {
      return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
   }

   public static void main(String[] args) {
      // Mostra os caracteres com acento - naturalmente, o java não os reconhece
      Boolean palindromo = true;

      String texto = MyIO.readLine();

      while (!isFim(texto)) {

         for (int i = (texto.length() - 1) / 2; i >= 0; i--) {

            if (texto.charAt(i) != texto.charAt(texto.length() - 1 - i)) {
               palindromo = false;
            } else
               palindromo = true;
         }

         MyIO.println(palindromo ? "SIM" : "NAO");
         texto = MyIO.readLine();

      }
   }
}
