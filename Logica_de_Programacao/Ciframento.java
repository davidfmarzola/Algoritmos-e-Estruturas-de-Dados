class Ciframento {
    public static boolean isFim(String s) {
        return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
     }
    public static void main(String[] args) {
        String test = MyIO.readLine();
        
        while (!isFim(test)) {
            String res = "";//a cada loop, a string res será esvaziada
            for (int i = 0; i < test.length(); ++i) {
                int palavraCifrada = test.charAt(i) + 3;// cifrando
                res += (char) palavraCifrada;// concateno os caracteres para formar a String
            }
            MyIO.println(res);
            test = MyIO.readLine();
        }
    }

}