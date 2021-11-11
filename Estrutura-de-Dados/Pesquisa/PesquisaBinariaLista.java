import java.util.*;

//atente-se ao caminho na main

class Series {
    private String nome;

    // construtor primário (inicializando atributos da classe)
    public Series() {
        nome = "";
    }

    // construtor secundário
    public Series(String nome) {
        this.nome = nome;
    }

    // Abre o arquivo e salva o código fonte no vetor
    public static String[] lerTexto(String endereco) {
        String[] vetor = new String[3000];
        int linha = 0;

        Arq.openRead(endereco, "UTF-8");
        while (Arq.hasNext()) {
            vetor[linha] = Arq.readLine();
            linha++;
        }

        Arq.close();
        return vetor;
    }

    // funções de tratamento de dados
    public static String pegaNome(String[] texto) {
        int linha = 0;
        int i = 0;
        String str = "";

        while (!texto[linha].contains("firstHeading"))
            linha++;
        if (texto[linha].contains("firstHeading")) {
            while (texto[linha].charAt(i) != '>') {
                i++;
            }
            i++;
            while (texto[linha].charAt(i) != '>') {
                i++;
            }
            i++;
            while (texto[linha].charAt(i) != '<') {
                str += texto[linha].charAt(i++);
            }

        }

        return str;
    }

    // gets e sets
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}

class Lista {
    ArrayList<String> arrayMenor;
    ArrayList<String> arrayMaior;
    static int countComp;

    public Lista() {
        arrayMenor = new ArrayList<String>();
        arrayMaior = new ArrayList<String>();
    }

    public void InserirNome(String str) {
        arrayMenor.add(str);
    }

    public void InserirInput(String str) {
        arrayMaior.add(str);
    }

    public void Mostrar() {
        System.out.println(arrayMenor);

    }

    public void MostrarInput() {

        for (int i = 0; i < arrayMaior.size(); i++) {
            System.out.println(arrayMaior.get(i));
        }

    }

    public void Ordenacao() {
        // somente esse está desordenado
        Collections.sort(arrayMenor);
    }

    public boolean PesquisaBinaria(String elementoArrayMaior, int esq, int dir) {
        boolean resp = false;
        int i = 0;
        String x = elementoArrayMaior;
        int meio;
        meio = (esq + dir) / 2;
        while (esq <= dir) {
            meio = (esq + dir) / 2;
            if (x.equals((String) arrayMenor.get(meio))) {
                countComp++;
                resp = true;
                esq = dir + 1;
            } else if (x.compareTo((String) arrayMenor.get(meio)) > 0) {
                esq = meio + 1;
                countComp++;
            } else {
                dir = meio - 1;
                countComp++;
            }
        }
        return resp;
    }

    public void isInList(boolean resp) {
        MyIO.setCharset("UTF-8");
        System.out.println(resp ? "SIM" : "NÃO");
    }

    public void chamadaPesquisaBinaria() {
        int i = 0;
        int dir = arrayMaior.size(), esq = 0;
        while (i < arrayMaior.size()) {
            isInList(PesquisaBinaria(arrayMaior.get(i), esq, dir));
            i++;
        }
    }
}

public class PesquisaBinariaLista {

    public static void main(String[] args) {
        Lista lista = new Lista();
        Series series = new Series();

        String entrada = MyIO.readLine();

        while (!isFim(entrada)) {
            String endereco = "series/" + entrada;
            String[] vetor = series.lerTexto(endereco);
            String str = series.pegaNome(vetor);

            lista.InserirNome(str);
            entrada = MyIO.readLine();
        }
        // lista.Mostrar();

        String input = MyIO.readLine();
        while (!isFim(input)) {
            lista.InserirInput(input);
            input = MyIO.readLine();
        }
        // lista.MostrarInput();
        lista.Ordenacao();
        Long tempoInicial = System.currentTimeMillis();
        lista.chamadaPesquisaBinaria();
        Long tempoFinal = System.currentTimeMillis();
        arquivoLog(tempoFinal - tempoInicial, lista.countComp);

    }

    public static Long tempoExecucao(long inicio, long fim) {
        return fim - inicio;
    }

    public static void arquivoLog(Long tempoExecucao, int numeroComparacoes) {
        Arq.openWrite("matricula_sequencial.txt", "UTF-8");

        Arq.print("712325\t");
        Arq.print(tempoExecucao + " milisegundos\t");
        Arq.print(numeroComparacoes + " comparações");

        Arq.close();
    }

    public static boolean isFim(String s) {
        return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }
    // condição de parada do programa
}
