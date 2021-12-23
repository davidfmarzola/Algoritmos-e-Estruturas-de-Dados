import java.util.*;

class No {
    public ArvoreBinariaSeries elemento; // Conteudo do no.
    public No esq, dir; // Filhos da esq e dir.

    public No(ArvoreBinariaSeries elemento) {
        this(elemento, null, null);
    }

    public No(ArvoreBinariaSeries elemento, No esq, No dir) {
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
    }
}

class ArvoreBinaria {
    private No raiz; // Raiz da arvore.
    private ArvoreBinariaSeries serie;// adicionei
    int numComparacoes; // compareTo
    int numMovimentacoes; // movimentações(caminhamentos) do ponteiro

    public ArvoreBinaria() {
        raiz = null;
        serie = new ArvoreBinariaSeries();// adicionei
    }

    public boolean pesquisar(String nomeSerie) {
        System.out.print(" raiz ");
        return pesquisar(nomeSerie, raiz);
    }

    private boolean pesquisar(String nomeSerie, No i) {
        // no pior caso
        numComparacoes += 3;
        numMovimentacoes++;
        boolean resp;
        if (i == null) {
            System.out.print("NAO\n");
            resp = false;

        } else if (nomeSerie.compareTo(i.elemento.getNome()) == 0) {
            System.out.print("SIM\n");
            resp = true;

        } else if (nomeSerie.compareTo(i.elemento.getNome()) < 0) {
            System.out.print("esq ");
            resp = pesquisar(nomeSerie, i.esq);
        } else {
            System.out.print("dir ");
            resp = pesquisar(nomeSerie, i.dir);
        }
        return resp;
    }

    public void inserir(ArvoreBinariaSeries serie) throws Exception {
        raiz = inserir(serie, raiz);
    }

    private No inserir(ArvoreBinariaSeries serie, No i) throws Exception {
        numComparacoes += 3;
        numMovimentacoes++;
        if (i == null) {
            i = new No(serie);

        } else if (serie.getNome().compareTo(i.elemento.getNome()) < 0) {
            i.esq = inserir(serie, i.esq);

        } else if (serie.getNome().compareTo(i.elemento.getNome()) > 0) {
            i.dir = inserir(serie, i.dir);

        } else {
            throw new Exception("Erro ao inserir!");
        }

        return i;
    }

    public void inserirPai(ArvoreBinariaSeries serie) throws Exception {
        numComparacoes += 3;
        if (raiz == null) {
            raiz = new No(serie);
        } else if (serie.getNome().compareTo(raiz.elemento.getNome()) < 0) {
            inserirPai(serie, raiz.esq, raiz);

        } else if (serie.getNome().compareTo(raiz.elemento.getNome()) > 0) {
            inserirPai(serie, raiz.dir, raiz);

        } else {
            throw new Exception("Erro ao inserirPai!");
        }
    }

    private void inserirPai(ArvoreBinariaSeries serie, No i, No pai) throws Exception {
        numComparacoes += 3;
        numMovimentacoes++;
        if (i == null) {
            if (serie.getNome().compareTo(i.elemento.getNome()) < 0) {
                pai.esq = new No(serie);
            } else {
                pai.dir = new No(serie);
            }
        } else if (serie.getNome().compareTo(i.elemento.getNome()) < 0) {
            inserirPai(serie, i.esq, i);

        } else if (serie.getNome().compareTo(i.elemento.getNome()) > 0) {
            inserirPai(serie, i.dir, i);

        } else {
            throw new Exception("Erro ao inserirPai!");
        }
    }

    public void remover(String nomeSerie) throws Exception {
        raiz = remover(nomeSerie, raiz);
    }

    private No remover(String nomeSerie, No i) throws Exception {
        // metódo desnecessário nesse caso
        numComparacoes += 5;
        numMovimentacoes++;
        if (i == null) {

        } else if (nomeSerie.compareTo(i.elemento.getNome()) < 0) {
            i.esq = remover(nomeSerie, i.esq);

        } else if (nomeSerie.compareTo(i.elemento.getNome()) > 0) {
            i.dir = remover(nomeSerie, i.dir);

            // Sem no a direita.
        } else if (i.dir == null) {
            i = i.esq;

            // Sem no a esquerda.
        } else if (i.esq == null) {
            i = i.dir;

            // No a esquerda e no a direita.
        } else {
            i.esq = maiorEsq(i, i.esq);
        }

        return i;
    }

    private No maiorEsq(No i, No j) {
        numComparacoes++;
        numMovimentacoes += 2;
        // Encontrou o maximo da subarvore esquerda.
        if (j.dir == null) {
            i.elemento = j.elemento; // Substitui i por j.
            j = j.esq; // Substitui j por j.ESQ.

            // Existe no a direita.
        } else {
            // Caminha para direita.
            j.dir = maiorEsq(i, j.dir);
        }
        return j;
    }

}

class ArvoreBinariaSeries {
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
    public ArvoreBinariaSeries() {
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

    public int compareTo(String nome2) {
        return 0;
    }

    // construtor secundário
    public ArvoreBinariaSeries(String nome, String formato, String duracao, String paisDeOrigem, String idiomaOriginal,
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

    public static ArvoreBinariaSeries lerAtributo(String endereco) {
        ArvoreBinariaSeries s1 = new ArvoreBinariaSeries();
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

    public void imprimir(ArvoreBinariaSeries s1) {
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
        ArvoreBinaria arvore = new ArvoreBinaria();
        Long tempoInicial = System.currentTimeMillis();
        String entrada = MyIO.readLine();

        while (!isFim(entrada)) {
            String caminhoDoArquivo = "series/" + entrada;
            arvore.inserir(lerAtributo(caminhoDoArquivo));

            entrada = MyIO.readLine();
        }
        int qtdOperacoes = MyIO.readInt();
        String[] segundaParte = new String[qtdOperacoes];
        int j = 0;
        // armazenando a segunda parte(entrada) no array
        while (j < qtdOperacoes) {
            segundaParte[j] = MyIO.readLine();
            j++;
        }
        inserirERemover(segundaParte, j, arvore);

        entrada = MyIO.readLine();
        while (!isFim(entrada)) {
            arvore.pesquisar(entrada);
            entrada = MyIO.readLine();
        }
        Long tempoFinal = System.currentTimeMillis();
        arquivoLog(tempoFinal - tempoInicial, arvore.numComparacoes, arvore.numMovimentacoes);
    }

    public static void inserirERemover(String[] vetorLido, int j, ArvoreBinaria arvore) throws Exception {
        for (int i = 0; i < j; i++) {
            String[] vetorTratado = vetorLido[i].split(" ", 2);
            if (vetorTratado[0].compareTo("I") == 0) {
                arvore.inserir(lerAtributo(PREFIXO + vetorTratado[1]));
            } else if (vetorTratado[0].compareTo("R") == 0) {
                arvore.remover(vetorTratado[1]);
            }
        }
    }

    public static void arquivoLog(Long tempoExecucao, int numeroComparacoes, int numeroMovimentacoes) {
        Arq.openWrite("matricula_arvoreBinaria.txt", "UTF-8");

        Arq.print("712325\t");
        Arq.print(tempoExecucao + " milissegundo(s)\t");
        Arq.print(numeroComparacoes + " comparações\t");
        Arq.print(numeroMovimentacoes + " movimentacoes");

        Arq.close();
    }

}
