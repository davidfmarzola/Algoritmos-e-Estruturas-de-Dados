
class QuickSortListaDinamicaDupla {
    private String nome;
    private String formato;
    private String duracao;
    private String paisDeOrigem;
    private String idiomaOriginal;
    private String emissoraDeTelevisaoOriginal;
    private String transmissaoOriginal;
    private int numeroTemporadas;
    private int numeroEpisodios;
    static final String PREFIXO = "series/";

    // construtor primário
    public QuickSortListaDinamicaDupla() {
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
    public QuickSortListaDinamicaDupla(String nome, String formato, String duracao, String paisDeOrigem, String idiomaOriginal,
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

    public static QuickSortListaDinamicaDupla lerAtributo(String endereco) {
        QuickSortListaDinamicaDupla s1 = new QuickSortListaDinamicaDupla();
        s1.setNome(trataNome(trataCaracteresEspeciais(removeTags(pegaLinha(endereco, "firstHeading")))).trim());
        s1.setFormato(trataCaracteresEspeciais(removeTags(pegaLinha(endereco, "Formato"))).trim());
        s1.setDuracao(trataCaracteresEspeciais(removeTags(pegaLinha(endereco, "Duração"))).trim());
        s1.setPaisDeOrigem(trataCaracteresEspeciais(removeTags(pegaLinha(endereco, "País de origem"))).trim());
        s1.setIdiomaOriginal(trataCaracteresEspeciais(removeTags(pegaLinha(endereco, "Idioma original"))).trim());
        s1.setEmissoraDeTelevisaoOriginal(
                trataCaracteresEspeciais(removeTags(pegaLinha(endereco, "Emissora de televisão original"))).trim());
        s1.setTransmissaoOriginal(
                trataCaracteresEspeciais(removeTags(pegaLinha(endereco, "Transmissão original"))).trim());
        s1.setNumeroTemporadas(
                converteParaInt(trataCaracteresEspeciais(removeTags(pegaLinha(endereco, "N.º de temporadas")))));
        s1.setNumeroEpisodios(
                converteParaInt(trataCaracteresEspeciais(removeTags(pegaLinha(endereco, "N.º de episódios")))));
        return s1;
    }

    public void imprimir(QuickSortListaDinamicaDupla s1) {
        System.out.println(s1.getNome() + " " + s1.getFormato() + " " + s1.getDuracao() + " " + s1.getPaisDeOrigem()
                + " " + s1.getIdiomaOriginal() + " " + s1.getEmissoraDeTelevisaoOriginal() + " "
                + s1.getTransmissaoOriginal() + " " + s1.getNumeroTemporadas() + " " + s1.getNumeroEpisodios());
    }

    static void erroNaAbertura(String endereco) {
        if (!Arq.openRead(endereco, "UTF-8")) {
            System.err.printf("Erro ao abrir o arquivo %s\n", endereco);
            System.exit(1);
        }
    }

    // Abre o arquivo e retorna o código fonte no vetorLido[i]
    public static String[] lerCodigoFonte(String endereco) {
        String[] vetorLido = new String[3000];
        int linha = 0;

        erroNaAbertura(endereco);

        Arq.openRead(endereco, "UTF-8");

        while (Arq.hasNext()) {
            vetorLido[linha] = Arq.readLine();
            linha++;
        }

        Arq.close();
        return vetorLido;
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

    public static String[] concatenarVetores(String[] primeiroVetor, String[] segundoVetor,
            int tamanho_primeiro_vetor) {
        String[] vetorConcatenado = new String[tamanho_primeiro_vetor + segundoVetor.length];
        for (int i = 0; i < tamanho_primeiro_vetor; i++) {
            vetorConcatenado[i] = primeiroVetor[i];
        }
        int k = 0;
        for (int j = tamanho_primeiro_vetor; j < vetorConcatenado.length; j++) {
            vetorConcatenado[j] = segundoVetor[k++];
        }
        return vetorConcatenado;
    }

    public static void main(String args[]) throws Exception {
        ListaDupla lista = new ListaDupla();
        CelulaDupla celula = new CelulaDupla();
        String entrada = MyIO.readLine();
        String endereco = PREFIXO;
        int i = 0;

        while (!isFim(entrada)) {
            String caminhoDoArquivo = endereco + entrada;
            lista.inserirFim(lerAtributo(caminhoDoArquivo));

            i++;
            entrada = MyIO.readLine();
        }
        lista.quicksort(0, lista.tamanho() - 1);
        lista.mostrar();
    }
}

class CelulaDupla {
    public QuickSortListaDinamicaDupla elemento;
    public CelulaDupla ant;
    public CelulaDupla prox;

    /**
     * Construtor da classe.
     */
    public CelulaDupla() {
        this(null);
    }

    /**
     * Construtor da classe.
     *
     * @param elemento int inserido na celula.
     */
    public CelulaDupla(QuickSortListaDinamicaDupla elemento) {
        this.elemento = elemento;
        this.ant = this.prox = null;
    }
}

class ListaDupla {
    public CelulaDupla primeiro;
    public CelulaDupla ultimo;
    int numComparacoes;
    int numMovimentacoes;

    /**
     * Construtor da classe que cria uma lista dupla sem elementos (somente no
     * cabeca).
     */
    public ListaDupla() {
        primeiro = new CelulaDupla();
        ultimo = primeiro;
        numComparacoes = 0;
        numMovimentacoes = 0;
    }

    // Fazer swap dos elementos da célula, pois assim só movimento uma,
    // e não duas referências no caso do swap na celula
    public void swap(int pos1, int pos2) throws Exception {

        QuickSortListaDinamicaDupla tmp = getCelula(pos1).elemento;
        getCelula(pos1).elemento = getCelula(pos2).elemento;
        getCelula(pos2).elemento = tmp;

    }

    void quicksort(int esq, int dir) throws Exception {
        int i = esq, j = dir;
        QuickSortListaDinamicaDupla pivo;
        CelulaDupla pivoCelula = primeiro.prox;
        Long tempoInicial = System.currentTimeMillis();

        for (int k = 0; k < (esq + dir) / 2; pivoCelula = pivoCelula.prox, k++)
            ;
        pivo = pivoCelula.elemento;

        while (i <= j) {
            CelulaDupla tmp = getCelula(i);
            while ((tmp.elemento.getPaisDeOrigem().compareTo(pivo.getPaisDeOrigem()) < 0)
                    || ((tmp.elemento.getPaisDeOrigem().compareTo(pivo.getPaisDeOrigem()) == 0)
                            && (tmp.elemento.getNome().compareTo(pivo.getNome()) < 0))) {
                i++;
                tmp = tmp.prox;
                numComparacoes += 3;
            }
            tmp = getCelula(j);
            while ((tmp.elemento.getPaisDeOrigem().compareTo(pivo.getPaisDeOrigem()) > 0)
                    || ((tmp.elemento.getPaisDeOrigem().compareTo(pivo.getPaisDeOrigem()) == 0)
                            && (tmp.elemento.getNome().compareTo(pivo.getNome()) > 0))) {
                j--;
                tmp = tmp.ant;
                numComparacoes += 3;
            }
            if (i <= j) {
                numMovimentacoes += 3;
                swap(i, j);
                i++;
                j--;
            }
        }
        if (esq < j)
            quicksort(esq, j);
        if (i < dir)
            quicksort(i, dir);
        Long tempoFinal = System.currentTimeMillis();
        arquivoLog(tempoFinal - tempoInicial, numComparacoes, numMovimentacoes);

    }

    private CelulaDupla getCelula(int pos) throws Exception {
        int a = 0;
        CelulaDupla i;
        for (i = primeiro.prox; a < pos; i = i.prox, a++)
            ;
        return i;
    }

    /**
     * Insere um elemento na ultima posicao da lista.
     *
     * @param serie QuickSortListaDupla elemento a ser inserido.
     */
    public void inserirFim(QuickSortListaDinamicaDupla serie) {
        ultimo.prox = new CelulaDupla(serie);
        ultimo.prox.ant = ultimo;
        ultimo = ultimo.prox;
    }

    /**
     * Mostra os elementos da lista separados por espacos.
     */
    public void mostrar() {
        for (CelulaDupla i = primeiro.prox; i != null; i = i.prox) {
            i.elemento.imprimir(i.elemento);
        }
    }

    /**
     * Calcula e retorna o tamanho, em numero de elementos, da lista.
     *
     * @return resp int tamanho
     */
    public int tamanho() {
        int tamanho = 0;
        for (CelulaDupla i = primeiro.prox; i != null; i = i.prox) {
            tamanho++;
        }
        return tamanho;
    }

    void arquivoLog(Long tempoExecucao, int numeroComparacoes, int numeroMovimentacoes) {
        Arq.openWrite("matricula_quicksort2.txt", "UTF-8");

        Arq.print("712325\t");
        Arq.print(tempoExecucao + " milissegundo(s)\t");
        Arq.print(numeroComparacoes + " comparações\t");
        Arq.print(numeroMovimentacoes + " movimentacoes");
        // Arq.print(numeroComparacoes + " comparações");

        Arq.close();
    }

}