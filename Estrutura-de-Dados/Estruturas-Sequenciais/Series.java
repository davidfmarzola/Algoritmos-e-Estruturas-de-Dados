import java.util.*;

class Series {
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
    public Series() {
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
    public Series(String nome, String formato, String duracao, String paisDeOrigem, String idiomaOriginal,
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

    public static Series lerAtributo(String endereco) {
        Series s1 = new Series();
        s1.setNome(trataNome(trataCaracteresEspeciais(removeTags(pegaLinha(endereco, "firstHeading")))));
        s1.setFormato(trataCaracteresEspeciais(removeTags(pegaLinha(endereco, "Formato"))));
        s1.setDuracao(trataCaracteresEspeciais(removeTags(pegaLinha(endereco, "Duração"))));
        s1.setPaisDeOrigem(trataCaracteresEspeciais(removeTags(pegaLinha(endereco, "País de origem"))));
        s1.setIdiomaOriginal(trataCaracteresEspeciais(removeTags(pegaLinha(endereco, "Idioma original"))));
        s1.setEmissoraDeTelevisaoOriginal(
                trataCaracteresEspeciais(removeTags(pegaLinha(endereco, "Emissora de televisão original"))));
        s1.setTransmissaoOriginal(trataCaracteresEspeciais(removeTags(pegaLinha(endereco, "Transmissão original"))));
        s1.setNumeroTemporadas(
                converteParaInt(trataCaracteresEspeciais(removeTags(pegaLinha(endereco, "N.º de temporadas")))));
        s1.setNumeroEpisodios(
                converteParaInt(trataCaracteresEspeciais(removeTags(pegaLinha(endereco, "N.º de episódios")))));
        return s1;
    }

    public static void imprimir(Series s1) {
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
        Series serie = new Series();
        String entrada = ler.nextLine();
        String endereco = PREFIXO;

        while (!isFim(entrada)) {
            String caminhoDoArquivo = endereco + entrada;
            lista.inserirFim(lerAtributo(caminhoDoArquivo));

            entrada = ler.nextLine();
        }
        int qtdOperacoes = ler.nextInt();
        String[] segundaParte = new String[qtdOperacoes];

        int j = 0;
        // armazenando a segunda parte(entrada) no array
        segundaParte[j] = ler.nextLine();
        while (j < qtdOperacoes) {
            segundaParte[j] = ler.nextLine();
            j++;
        }
        insereLista(segundaParte);
        lista.imprimirObjetosRemovidos(serie);
        lista.imprimirObjetosSetados();
        ler.close();
    }

    public static void insereLista(String[] vetorLido) throws Exception {
        for (int i = 0; i < 30; i++) {
            String[] vetorTratado = vetorLido[i].split(" ");
            if (vetorTratado[0].compareTo("II") == 0) {
                Lista.inserirInicio(lerAtributo(PREFIXO + vetorTratado[1]));
            } else if (vetorTratado[0].compareTo("IF") == 0) {
                Lista.inserirFim(lerAtributo(PREFIXO + vetorTratado[1]));
            } else if (vetorTratado[0].compareTo("I*") == 0) {
                Lista.inserir(lerAtributo(PREFIXO + vetorTratado[2]), Integer.parseInt(vetorTratado[1]));
            } else if (vetorTratado[0].compareTo("RI") == 0) {
                Lista.removerInicio();
            } else if (vetorTratado[0].compareTo("RF") == 0) {
                Lista.removerFim();
            } else if (vetorTratado[0].compareTo("R*") == 0) {
                Lista.remover(Integer.parseInt(vetorTratado[1]));
            }
        }

    }

}

class Lista {

    static Series[] objSeries;
    static ArrayList<String> objetosRemovidos;
    static int indice;

    Lista() {
        this(50);
    }

    Lista(int tamanho) {
        objSeries = new Series[tamanho];
        indice = 0;
        objetosRemovidos = new ArrayList<String>();
    }

    public static void inserirInicio(Series serie) throws Exception {
        if (indice >= objSeries.length)
            throw new Exception("Erro!");

        for (int i = indice; i > 0; i--) {
            objSeries[i] = objSeries[i - 1];
        }

        objSeries[0] = serie;
        indice++;
    }

    public static void inserirFim(Series serie) throws Exception {
        if (indice >= objSeries.length) {
            new Exception("there's impossible to insert, no memory avaliable");
        }
        objSeries[indice] = serie;
        indice++;
    }

    public static void inserir(Series serie, int pos) throws Exception {
        if (indice >= objSeries.length || pos < 0 || pos > indice) {
            System.out.println("Erro ao inserir");
            System.exit(0);
        }

        // levar elementos para o fim do array
        for (int i = indice; i > pos; i--) {
            objSeries[i] = objSeries[i - 1];
        }

        objSeries[pos] = serie;
        indice++;

    }

    public static void removerInicio() throws Exception {
        Series series = new Series();
        // validar remocao
        if (indice == 0) {
            System.out.println("Erro ao remover!");
            System.exit(0);
        }

        Series resp = objSeries[0];
        indice--;

        for (int i = 0; i < indice; i++) {
            objSeries[i] = objSeries[i + 1];
        }

        adicionaObjetosRemovidos(resp);
    }

    public static void removerFim() throws Exception {
        // validar remocao
        if (indice == 0) {
            System.out.println("Erro ao remover!");
            System.exit(0);
        }
        adicionaObjetosRemovidos(objSeries[--indice]);
    }

    public static void remover(int pos) throws Exception {
        // validar remocao
        if (indice == 0 || pos < 0 || pos >= indice) {
            System.out.println("Erro ao remover!");
            System.exit(0);
        }

        Series resp = objSeries[pos];
        indice--;

        for (int i = pos; i < indice; i++) {
            objSeries[i] = objSeries[i + 1];
        }
        adicionaObjetosRemovidos(resp);
    }

    public static void adicionaObjetosRemovidos(Series serie) throws Exception {
        String nome = serie.getNome();
        objetosRemovidos.add(nome);
    }

    public static void imprimirObjetosRemovidos(Series serie) {
        for (int i = 0; i < objetosRemovidos.size(); i++) {
            System.out.println("(R) " + objetosRemovidos.get(i));
        }
    }

    public static void imprimirObjetosSetados() {
        for (int i = 0; i < indice; i++) {
            objSeries[i].imprimir(objSeries[i]);
        }
    }

}
