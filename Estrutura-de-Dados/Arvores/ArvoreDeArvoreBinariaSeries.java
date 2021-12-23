
class No {
    public char elemento; // Conteudo do no.
    public No esq; // No da esquerda.
    public No dir; // No da direita.
    public No2 raizSegundaArvore;

    No(char elemento) {
        this.elemento = elemento;
        this.esq = this.dir = null;
        this.raizSegundaArvore = null;
    }

    No(char elemento, No esq, No dir) {
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
        this.raizSegundaArvore = null;
    }
}

class No2 {
    public ArvoreDeArvoreBinariaSeries serie; // Conteudo do no.
    public No2 esq; // No da esquerda.
    public No2 dir; // No da direita.

    No2(ArvoreDeArvoreBinariaSeries serie) {
        this.serie = serie;
        this.esq = this.dir = null;
    }

    No2(ArvoreDeArvoreBinariaSeries serie, No2 esq, No2 dir) {
        this.serie = serie;
        this.esq = esq;
        this.dir = dir;
    }
}

class ArvoreArvore {
    private No raiz; // Raiz da arvore.
    int numComparacoes; // if
    int numMovimentacoes; // troca de elementos

    public ArvoreArvore() throws Exception {
        numComparacoes = 0;
        numMovimentacoes = 0;
        raiz = null;
        char[] vetorCaracteres = { 'D', 'R', 'Z', 'X', 'V', 'B', 'F', 'P', 'U', 'I', 'G', 'E', 'J', 'L', 'H', 'T', 'A',
                'W', 'S', 'O', 'M', 'N', 'K', 'C', 'Y', 'Q' };

        for (int i = 0; i < vetorCaracteres.length; i++) {
            this.inserirLetra(vetorCaracteres[i]);
        }

    }

    public void inserirLetra(char letra) throws Exception {
        raiz = inserirLetra(letra, raiz);
    }

    private No inserirLetra(char letra, No i) throws Exception {
        // pior caso

        if (i == null) {
            numComparacoes++;
            i = new No(letra);

        } else if (letra < i.elemento) {
            numComparacoes++;
            i.esq = inserirLetra(letra, i.esq);

        } else if (letra > i.elemento) {
            numComparacoes++;
            i.dir = inserirLetra(letra, i.dir);

        } else {
            throw new Exception("Erro ao inserir!");
        }

        return i;
    }

    public void inserirSerie(ArvoreDeArvoreBinariaSeries s) throws Exception {
        // System.out.println(s.getNome());
        inserirSerie(s, raiz);
    }

    public void inserirSerie(ArvoreDeArvoreBinariaSeries s, No i) throws Exception {
        if (i == null) {
            numComparacoes++;
            i = new No(s.getNome().charAt(0));
            inserirSerie(s, i.raizSegundaArvore);

        } else if (s.getNome().toUpperCase().charAt(0) < i.elemento) {
            numComparacoes++;
            inserirSerie(s, i.esq);

        } else if (s.getNome().toUpperCase().charAt(0) > i.elemento) {
            numComparacoes++;
            inserirSerie(s, i.dir);

        } else {
            i.raizSegundaArvore = inserirSerie(s, i.raizSegundaArvore);
        }
    }

    private No2 inserirSerie(ArvoreDeArvoreBinariaSeries s, No2 i) throws Exception {
        numComparacoes += 3;
        if (i == null) {
            numComparacoes++;
            i = new No2(s);

        } else if (s.getNome().compareTo(i.serie.getNome()) < 0) {
            numComparacoes++;
            i.esq = inserirSerie(s, i.esq);

        } else if (s.getNome().compareTo(i.serie.getNome()) > 0) {
            numComparacoes++;
            i.dir = inserirSerie(s, i.dir);

        } else {
            throw new Exception("Erro ao inserir: elemento existente!");
        }

        return i;
    }

    public boolean pesquisarPrimeiraArvore(String nome) {
        boolean resp = false;
        System.out.print("raiz ");
        resp = pesquisarPrimeiraArvore(raiz, nome);
        return resp;
    }

    public boolean pesquisarPrimeiraArvore(No i, String nome) {
        boolean resp = false;

        if (i == null) {
            numComparacoes++;
            resp = false;
        } else if (pesquisarSegundaArvore(i.raizSegundaArvore, nome) == true) {
            numComparacoes++;
            resp = true;
        } else {
            System.out.print("esq ");
            resp = pesquisarPrimeiraArvore(i.esq, nome);

            if (resp == false) {
                numComparacoes++;
                System.out.print("dir ");
                resp = pesquisarPrimeiraArvore(i.dir, nome);
            }
        }

        return resp;
    }

    public boolean pesquisarSegundaArvore(No2 i, String nome) {
        boolean resp = false;

        if (i == null) {
            numComparacoes++;
            resp = false;
        } else if (i.serie.getNome().compareTo(nome) == 0) {
            numComparacoes++;
            resp = true;
        } else {
            System.out.print("ESQ ");
            resp = pesquisarSegundaArvore(i.esq, nome);

            if (resp == false) {
                numComparacoes++;
                System.out.print("DIR ");
                resp = pesquisarSegundaArvore(i.dir, nome);
            }
        }

        return resp;
    }

}

class ArvoreDeArvoreBinariaSeries {
    private String nome;
    private String formato;
    private String duracao;
    private String paisDeOrigem;
    private String idiomaOriginal;
    private String emissoraDeTelevisaoOriginal;
    private String transmissaoOriginal;
    private int numeroTemporadas;
    private int numeroEpisodios;

    // construtor primário
    public ArvoreDeArvoreBinariaSeries() {
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
    public ArvoreDeArvoreBinariaSeries(String nome, String formato, String duracao, String paisDeOrigem,
            String idiomaOriginal,
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

    public static ArvoreDeArvoreBinariaSeries lerAtributo(String endereco) {
        ArvoreDeArvoreBinariaSeries s1 = new ArvoreDeArvoreBinariaSeries();
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

    public void imprimir(ArvoreDeArvoreBinariaSeries s1) {
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

    public static void main(String args[]) throws Exception {
        ArvoreArvore arvoreDupla = new ArvoreArvore();
        String entrada = MyIO.readLine();
        Long tempoInicial = System.currentTimeMillis();

        while (!isFim(entrada)) {
            String caminhoDoArquivo = "series/" + entrada;
            arvoreDupla.inserirSerie(lerAtributo(caminhoDoArquivo));
            entrada = MyIO.readLine();
        }

        entrada = MyIO.readLine();
        while (!isFim(entrada)) {
            System.out.print(arvoreDupla.pesquisarPrimeiraArvore(entrada) ? " SIM\n" : " NAO\n");
            entrada = MyIO.readLine();
        }
        Long tempoFinal = System.currentTimeMillis();
        arquivoLog(tempoFinal - tempoInicial, arvoreDupla.numComparacoes, arvoreDupla.numMovimentacoes);

    }

    public static void arquivoLog(Long tempoExecucao, int numeroComparacoes, int numeroMovimentacoes) {
        Arq.openWrite("matricula_arvoreArvore.txt", "UTF-8");

        Arq.print("712325\t");
        Arq.print(tempoExecucao + " milissegundo(s)\t");
        Arq.print(numeroComparacoes + " comparações\t");
        Arq.print(numeroMovimentacoes + " movimentacoes");

        Arq.close();
    }

}