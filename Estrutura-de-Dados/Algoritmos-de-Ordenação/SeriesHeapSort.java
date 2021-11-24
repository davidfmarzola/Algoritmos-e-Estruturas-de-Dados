import java.util.*;

class SeriesHeapSort {
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
    public SeriesHeapSort() {
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
    public SeriesHeapSort(String nome, String formato, String duracao, String paisDeOrigem, String idiomaOriginal,
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

    // retorna o objeto setado
    public static SeriesHeapSort lerAtributo(String endereco) {
        SeriesHeapSort s1 = new SeriesHeapSort();
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

    public static void imprimir(SeriesHeapSort s1) {
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
        SeriesHeapSort vetorObjetoSeries[] = new SeriesHeapSort[62];
        String entrada = ler.nextLine();
        String endereco = PREFIXO;
        // i e j iniciam em 1, para faciliatar no heapsort
        int i = 1, j = 1;

        while (!isFim(entrada)) {
            String caminhoDoArquivo = endereco + entrada;
            // armazeno os objetos num vetor
            vetorObjetoSeries[i++] = lerAtributo(caminhoDoArquivo);

            entrada = ler.nextLine();
        }
        while (j < i) {
            lista.inserirFim(vetorObjetoSeries[j++]);
        }
        lista.heapsort(i);
        lista.mostrar();
        ler.close();
    }
}

class Lista {

    SeriesHeapSort[] vetorObjetoSeries;
    int indice;
    int numeroComparacoes;
    int numeroMovimentacoes;

    Lista() {
        this(62);
    }

    // indice inicia em 1, pois o vetor no HEAPSORT inicia em 1
    Lista(int tamanho) {
        vetorObjetoSeries = new SeriesHeapSort[tamanho];
        indice = 1;
        numeroComparacoes = 0;
        numeroMovimentacoes = 0;
    }

    // Comparo os filhos
    int getMaiorFilho(int i, int tam) {
        // indice i é o PAI
        int filho;
        if (2 * i == tam
                || (vetorObjetoSeries[2 * i].getFormato().compareTo(vetorObjetoSeries[2 * i + 1].getFormato()) > 0)) {
            numeroComparacoes++;
            filho = 2 * i;
        } else if (2 * i == tam
                || (vetorObjetoSeries[2 * i].getFormato().compareTo(vetorObjetoSeries[2 * i + 1].getFormato()) == 0)
                        && (vetorObjetoSeries[2 * i].getNome().compareTo(vetorObjetoSeries[2 * i + 1].getNome()) > 0)) {
            numeroComparacoes += 2;
            filho = 2 * i;
        } else {
            filho = 2 * i + 1;
        }
        return filho;
    }

    void swap(int a, int b) {
        SeriesHeapSort temp = vetorObjetoSeries[a];
        vetorObjetoSeries[a] = vetorObjetoSeries[b];
        vetorObjetoSeries[b] = temp;
    }

    void reconstruir(int tam) {
        int i = 1;
        while (i <= (tam / 2)) {
            int filho = getMaiorFilho(i, tam);
            if (vetorObjetoSeries[i].getFormato().compareTo(vetorObjetoSeries[filho].getFormato()) < 0) {
                numeroComparacoes++;
                swap(i, filho);
                numeroMovimentacoes += 3;
                i = filho;
            } else if (vetorObjetoSeries[i].getFormato().compareTo(vetorObjetoSeries[filho].getFormato()) == 0
                    && (vetorObjetoSeries[i].getNome().compareTo(vetorObjetoSeries[filho].getNome())) < 0) {
                numeroComparacoes += 2;
                // System.out.println(vetorObjetoSeries[i].getNome() + " - " +
                // vetorObjetoSeries[filho].getNome());
                swap(i, filho);
                numeroMovimentacoes += 3;
                i = filho;
            } else {
                i = tam;
            }
        }
    }

    void construir(int tam) {
        // no heapsort, vetor(indice) inicia em 1
        for (int i = tam; i > 1
                && ((vetorObjetoSeries[i].getFormato().compareTo(vetorObjetoSeries[i / 2].getFormato()) > 0)
                        || ((vetorObjetoSeries[i].getFormato().compareTo(vetorObjetoSeries[i / 2].getFormato()) == 0)
                                && (vetorObjetoSeries[i].getNome()
                                        .compareTo(vetorObjetoSeries[i / 2].getNome()) > 0))); i /= 2) {
            numeroComparacoes += 3;
            swap(i, i / 2);
            numeroMovimentacoes += 3;

        }
    }

    void heapsort(int indice) {
        // Contrução do heap
        Long tempoInicial = System.currentTimeMillis();
        for (int tam = 2; tam < indice; tam++) {
            construir(tam);
        }
        // Ordenacao propriamente dita
        int tam = indice - 1;
        while (tam > 1) {
            swap(1, tam--);
            numeroMovimentacoes += 3;
            reconstruir(tam);
        }
        Long tempoFinal = System.currentTimeMillis();
        arquivoLog(tempoFinal - tempoInicial, numeroComparacoes, numeroMovimentacoes);
    }

    void inserirFim(SeriesHeapSort serie) throws Exception {
        if (indice >= vetorObjetoSeries.length) {
            new Exception("there's impossible to insert, no memory avaliable");
        }
        vetorObjetoSeries[indice] = serie;
        indice++;
    }

    void mostrar() {
        for (int i = 1; i < indice; i++) {
            vetorObjetoSeries[i].imprimir(vetorObjetoSeries[i]);
        }
    }

    void arquivoLog(Long tempoExecucao, int numeroComparacoes, int numeroMovimentacoes) {
        Arq.openWrite("matricula_heapsort.txt", "UTF-8");

        Arq.print("712325\t");
        Arq.print(tempoExecucao + " milissegundo(s)\t");
        Arq.print(numeroComparacoes + " comparações\t");
        Arq.print(numeroMovimentacoes + " movimentacoes");
        // Arq.print(numeroComparacoes + " comparações");

        Arq.close();
    }

}
