import java.util.*;

class SeriesRadixSort {
    private String nome;
    private String formato;
    private String duracao;
    private String paisDeOrigem;
    private String idiomaOriginal;
    private String emissoraDeTelevisaoOriginal;
    private String transmissaoOriginal;
    private int numeroTemporadas;
    private int numeroEpisodios;
    static Scanner ler = new Scanner(System.in);
    static final String PREFIXO = "series/";

    // construtor primário
    public SeriesRadixSort() {
        nome = "";
        formato = "";
        duracao = "";
        paisDeOrigem = "";
        idiomaOriginal = "";
        emissoraDeTelevisaoOriginal = "";
        transmissaoOriginal = "";
        numeroTemporadas = 0;
        numeroEpisodios = 0;
    }

    // construtor secundário
    public SeriesRadixSort(String nome, String formato, String duracao, String paisDeOrigem, String idiomaOriginal,
            String emissoraDeTelevisaoOriginal, String transmissaoOriginal, int numeroTemporadas, int numeroEpisodios) {
        this.nome = nome;
        this.formato = formato;
        this.duracao = duracao;
        this.paisDeOrigem = paisDeOrigem;
        this.idiomaOriginal = paisDeOrigem;
        this.emissoraDeTelevisaoOriginal = emissoraDeTelevisaoOriginal;
        this.transmissaoOriginal = transmissaoOriginal;
        this.numeroTemporadas = numeroTemporadas;
        this.numeroEpisodios = numeroEpisodios;
    }

    public static SeriesRadixSort lerAtributo(String endereco) {
        SeriesRadixSort s1 = new SeriesRadixSort();
        s1.setNome(trataNome(trataCaracteresEspeciais(removeTags(pegaLinha(endereco, "firstHeading")))).trim());
        s1.setFormato(trataCaracteresEspeciais(removeTags(pegaLinha(endereco, "Formato"))).trim());
        s1.setDuracao(trataCaracteresEspeciais(removeTags(pegaLinha(endereco, "Duração"))).trim());
        s1.setPaisDeOrigem(trataCaracteresEspeciais(removeTags(pegaLinha(endereco, "País de origem"))).trim());
        // System.out.println(s1.getFormato() + " - " + s1.getFormato().length());
        s1.setIdiomaOriginal(trataCaracteresEspeciais(removeTags(pegaLinha(endereco, "Idioma original"))).trim());
        s1.setEmissoraDeTelevisaoOriginal(
                trataCaracteresEspeciais(removeTags(pegaLinha(endereco, "Emissora de televisão original"))).trim());
        s1.setTransmissaoOriginal(
                trataCaracteresEspeciais(removeTags(pegaLinha(endereco, "Transmissão original"))).trim());
        s1.setNumeroTemporadas(
                converteParaInt(trataCaracteresEspeciais(removeTags(pegaLinha(endereco, "N.º de temporadas"))).trim()));
        s1.setNumeroEpisodios(
                converteParaInt(trataCaracteresEspeciais(removeTags(pegaLinha(endereco, "N.º de episódios"))).trim()));
        return s1;
    }

    public static void imprimir(SeriesRadixSort s1) {
        System.out.println(s1.getNome() + " " + s1.getFormato() + " " + s1.getDuracao() + " " + s1.getPaisDeOrigem()
                + " " + s1.getIdiomaOriginal() + " " + s1.getEmissoraDeTelevisaoOriginal() + " "
                + s1.getTransmissaoOriginal() + " " + s1.getNumeroTemporadas() + " " + s1.getNumeroEpisodios());
    }

    // Abre o arquivo e retorna o código fonte no vetor
    public static String[] lerCodigoFonte(String endereco) {
        String[] vetor = new String[3000];
        int linha = 0;

        if (!Arq.openRead(endereco, "UTF-8")) {
            System.err.printf("Erro ao abrir o arquivo %s\n", endereco);
            System.exit(1);
        }
        Arq.openRead(endereco, "UTF-8");

        while (Arq.hasNext()) {
            vetor[linha] = Arq.readLine();
            linha++;
        }

        Arq.close();
        return vetor;
    }

    // funções de tratamento de dados
    public static String removeTags(String line) {
        String newLine = "";
        int i = 0;
        while (i < line.length() && line.charAt(i) != '<') {
            i++;
        }
        while (i < line.length()) {
            if (line.charAt(i) == '<') {
                i++;
                while (line.charAt(i) != '>')
                    i++;
            } else {
                newLine += line.charAt(i);
            }
            i++;
        }
        return newLine;
    }

    public static String pegaLinha(String endereco, String identificaAtributo) {
        String[] codigoFonte = lerCodigoFonte(endereco);
        int i = 0;
        String linha = "";
        while (i < codigoFonte.length) {
            if (identificaAtributo == "firstHeading" && codigoFonte[i].contains("firstHeading")) {
                linha = codigoFonte[i];
                i = codigoFonte.length;
            } else if (codigoFonte[i].contains(identificaAtributo)) {
                linha = codigoFonte[i + 1];
                i = codigoFonte.length;
            }
            i++;
        }
        return linha;
    }

    public static String trataCaracteresEspeciais(String semTags) {
        int i = 0;
        String res = "";
        while (i < semTags.length()) {
            if (semTags.charAt(i) == '&') {
                i += 6;
            }
            res += semTags.charAt(i);
            i++;
        }
        return res;
    }

    public static String trataNome(String semTags) {
        int i = 0;
        String res = "";
        while (i < semTags.length()) {
            if (semTags.charAt(i) == '(') {
                i = semTags.length();
            } else {
                res += semTags.charAt(i);
                i++;
            }
        }
        // remover espaço em branco
        // if (res.charAt(res.length() - 1) == ' ') {
        // res = res.replaceFirst(".$", "");
        // }
        return res;
    }

    public static int converteParaInt(String semTags) {
        int i = 0;
        String res = "";
        while (i < semTags.length() && isNumber(semTags.charAt(i)) != false) {
            res += semTags.charAt(i);
            i++;
        }
        return Integer.parseInt(res);
    }

    // gets e sets
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public String getPaisDeOrigem() {
        return paisDeOrigem;
    }

    public void setPaisDeOrigem(String paisDeOrigem) {
        this.paisDeOrigem = paisDeOrigem;
    }

    public String getIdiomaOriginal() {
        return idiomaOriginal;
    }

    public void setIdiomaOriginal(String idiomaOriginal) {
        this.idiomaOriginal = idiomaOriginal;
    }

    public String getEmissoraDeTelevisaoOriginal() {
        return emissoraDeTelevisaoOriginal;
    }

    public void setEmissoraDeTelevisaoOriginal(String emissoraDeTelevisaoOriginal) {
        this.emissoraDeTelevisaoOriginal = emissoraDeTelevisaoOriginal;
    }

    public String getTransmissaoOriginal() {
        return transmissaoOriginal;
    }

    public void setTransmissaoOriginal(String transmissaoOriginal) {
        this.transmissaoOriginal = transmissaoOriginal;
    }

    public int getNumeroTemporadas() {
        return numeroTemporadas;
    }

    public void setNumeroTemporadas(int numeroTemporadas) {
        this.numeroTemporadas = numeroTemporadas;
    }

    public int getNumeroEpisodios() {
        return numeroEpisodios;
    }

    public void setNumeroEpisodios(int numeroEpisodios) {
        this.numeroEpisodios = numeroEpisodios;
    }

    // condição de parada do programa
    public static boolean isFim(String s) {
        return (s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static boolean isNumber(char s) {
        return s >= '0' && s <= '9';
    }

    public static void main(String[] args) throws Exception {
        Lista lista = new Lista();
        SeriesRadixSort vetorObjetoSeries[] = new SeriesRadixSort[61];
        String entrada = ler.nextLine();
        String endereco = PREFIXO;
        int i = 0, j = 0;

        while (!isFim(entrada)) {
            String caminhoDoArquivo = endereco + entrada;
            // armazeno os objetos num vetor
            vetorObjetoSeries[i++] = lerAtributo(caminhoDoArquivo);

            entrada = ler.nextLine();
        }
        while (j < i) {
            lista.inserirFim(vetorObjetoSeries[j++]);
        }
        lista.radixsort(i);
        lista.mostrar();
        ler.close();
    }
}

class Lista {

    SeriesRadixSort[] vetorObjetoSeries;
    int indice;
    int numeroComparacoes;
    int numeroMovimentacoes;
    Long tempoInicial;
    Long tempoFinal;

    Lista() {
        this(61);
    }

    Lista(int tamanho) {
        vetorObjetoSeries = new SeriesRadixSort[tamanho];
        indice = 0;
        numeroComparacoes = 0;
        numeroMovimentacoes = 0;
    }

    // OBRIGATÓRIO para vetores já ou quase ordenados
    void insertionsort(int tam) {
        for (int i = 1; i < tam; i++) {
            SeriesRadixSort tmp = vetorObjetoSeries[i];
            int j = i - 1;
            // comparo com o valor doidao de I, pois é o ÍNDICE de TMP
            while ((j >= 0) && (valorDoidao(j) == valorDoidao(i)
                    && (vetorObjetoSeries[j].getNome().compareTo(tmp.getNome()) > 0))) {
                vetorObjetoSeries[j + 1] = vetorObjetoSeries[j];
                j--;
            }
            vetorObjetoSeries[j + 1] = tmp;
        }
    }

    int valorDoidao(int indice) {
        return vetorObjetoSeries[indice].getNumeroEpisodios() * 1000 + vetorObjetoSeries[indice].getNumeroTemporadas();
    }

    // A function to do counting sort of arr[] according to
    // the digit represented by exp.
    void countingsort(int numSeries, int expoente) {
        SeriesRadixSort output[] = new SeriesRadixSort[numSeries]; // output array
        int i;
        int count[] = new int[10];
        Arrays.fill(count, 0);

        // Store count of occurrences in count[]
        for (i = 0; i < numSeries; i++)
            count[(valorDoidao(i) / expoente) % 10]++;

        // Change count[i] so that count[i] now contains
        // actual position of this digit in output[]
        for (i = 1; i < 10; i++)
            count[i] += count[i - 1];

        // Build the output array
        for (i = numSeries - 1; i >= 0; i--) {
            numeroMovimentacoes++;
            output[count[(valorDoidao(i) / expoente) % 10] - 1] = vetorObjetoSeries[i];
            count[(valorDoidao(i) / expoente) % 10]--;
        }

        // Copy the output array to arr[], so that arr[] now
        // contains sorted numbers according to current digit
        for (i = 0; i < numSeries; i++) {
            numeroMovimentacoes++;
            vetorObjetoSeries[i] = output[i];
        }
    }

    int getMaior(int numSeries) {
        int maior = valorDoidao(0);

        for (int i = 1; i < numSeries; i++) {
            if (maior < valorDoidao(i)) {
                maior = valorDoidao(i);
            }
        }
        return maior;
    }

    // The main function to that sorts arr[] of size n using
    // Radix Sort
    void radixsort(int numSeries) {
        tempoInicial = System.currentTimeMillis();
        // Find the maximum number to know number of digits
        int maior = getMaior(numSeries);

        // Do counting sort for every digit. Note that instead
        // of passing digit number, exp is passed. exp is 10^i
        // where i is current digit number
        for (int expoente = 1; maior / expoente > 0; expoente *= 10)
            countingsort(numSeries, expoente);
        tempoFinal = System.currentTimeMillis();
        arquivoLog(tempoFinal - tempoInicial, numeroComparacoes, numeroMovimentacoes);
        insertionsort(numSeries);
    }

    void inserirFim(SeriesRadixSort serie) throws Exception {
        if (indice >= vetorObjetoSeries.length) {
            new Exception("there's impossible to insert, no memory avaliable");
        }
        vetorObjetoSeries[indice] = serie;
        indice++;
    }

    void mostrar() {
        for (int i = 0; i < indice; i++) {
            vetorObjetoSeries[i].imprimir(vetorObjetoSeries[i]);
        }
    }

    void arquivoLog(Long tempoExecucao, int numeroComparacoes, int numeroMovimentacoes) {
        Arq.openWrite("matricula_radixsort.txt", "UTF-8");

        Arq.print("712325\t");
        Arq.print(tempoExecucao + " milissegundo(s)\t");
        Arq.print(numeroComparacoes + " comparações\t");
        Arq.print(numeroMovimentacoes + " movimentacoes");
        // Arq.print(numeroComparacoes + " comparações");

        Arq.close();
    }

}