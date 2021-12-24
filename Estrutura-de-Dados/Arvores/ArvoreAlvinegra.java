class NoAN {
    public boolean cor;
    public ArvoreAlvinegra elemento;
    public NoAN esq, dir;

    public NoAN() {
        this(null);
    }

    public NoAN(ArvoreAlvinegra elemento) {
        this(elemento, false, null, null);
    }

    public NoAN(ArvoreAlvinegra elemento, boolean cor) {
        this(elemento, cor, null, null);
    }

    public NoAN(ArvoreAlvinegra elemento, boolean cor, NoAN esq, NoAN dir) {
        this.cor = cor;
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
    }
}

class Alvinegra {
    private NoAN raiz; // Raiz da arvore.
    int numComparacoes = 0;

    public Alvinegra() {
        raiz = null;
    }

    public boolean pesquisar(String elemento) {
        System.out.print("raiz ");
        return pesquisar(elemento, raiz);
    }

    private boolean pesquisar(String elemento, NoAN i) {
        boolean resp;
        if (i == null) {
            numComparacoes++;
            resp = false;

        } else if (elemento.compareTo(i.elemento.getNome()) == 0) {
            numComparacoes++;
            resp = true;

        } else if (elemento.compareTo(i.elemento.getNome()) < 0) {
            numComparacoes++;
            System.out.print("esq ");
            resp = pesquisar(elemento, i.esq);

        } else {
            System.out.print("dir ");
            resp = pesquisar(elemento, i.dir);
        }
        return resp;
    }

    public void inserir(ArvoreAlvinegra elemento) throws Exception {

        // Se a arvore estiver vazia
        if (raiz == null) {
            numComparacoes++;
            raiz = new NoAN(elemento);
            // System.out.println("Antes, zero elementos. Agora, raiz(" + raiz.elemento +
            // ").");

            // Senao, se a arvore tiver um elemento
        } else if (raiz.esq == null && raiz.dir == null) {
            numComparacoes++;
            if (elemento.getNome().compareTo(raiz.elemento.getNome()) == 0) {
                numComparacoes++;
                raiz.esq = new NoAN(elemento);
                // System.out.println(
                // "Antes, um elemento. Agora, raiz(" + raiz.elemento + ") e esq(" +
                // raiz.esq.elemento + ").");
            } else {
                raiz.dir = new NoAN(elemento);
                // System.out.println(
                // "Antes, um elemento. Agora, raiz(" + raiz.elemento + ") e dir(" +
                // raiz.dir.elemento + ").");
            }

            // Senao, se a arvore tiver dois elementos (raiz e dir)
        } else if (raiz.esq == null) {
            numComparacoes++;
            if (elemento.getNome().compareTo(raiz.elemento.getNome()) < 0) {
                numComparacoes++;
                raiz.esq = new NoAN(elemento);
                // System.out.println("Antes, dois elementos(A). Agora, raiz(" + raiz.elemento +
                // "), esq ("
                // + raiz.esq.elemento + ") e dir(" + raiz.dir.elemento + ").");

            } else if (elemento.getNome().compareTo(raiz.dir.elemento.getNome()) < 0) {
                numComparacoes++;
                raiz.esq = new NoAN(raiz.elemento);
                raiz.elemento = elemento;
                // System.out.println("Antes, dois elementos(B). Agora, raiz(" + raiz.elemento +
                // "), esq ("
                // + raiz.esq.elemento + ") e dir(" + raiz.dir.elemento + ").");

            } else {
                raiz.esq = new NoAN(raiz.elemento);
                raiz.elemento = raiz.dir.elemento;
                raiz.dir.elemento = elemento;
                // System.out.println("Antes, dois elementos(C). Agora, raiz(" + raiz.elemento +
                // "), esq ("
                // + raiz.esq.elemento + ") e dir(" + raiz.dir.elemento + ").");
            }

            raiz.esq.cor = raiz.dir.cor = false;

            // Senao, se a arvore tiver dois elementos (raiz e esq)
        } else if (raiz.dir == null) {
            numComparacoes++;
            if (elemento.getNome().compareTo(raiz.elemento.getNome()) > 0) {
                numComparacoes++;
                raiz.dir = new NoAN(elemento);
                // System.out.println("Antes, dois elementos(D). Agora, raiz(" + raiz.elemento +
                // "), esq ("
                // + raiz.esq.elemento + ") e dir(" + raiz.dir.elemento + ").");
            } else if (elemento.getNome().compareTo(raiz.esq.elemento.getNome()) > 0) {
                numComparacoes++;
                raiz.dir = new NoAN(raiz.elemento);
                raiz.elemento = elemento;
                // System.out.println("Antes, dois elementos(E). Agora, raiz(" + raiz.elemento +
                // "), esq ("
                // + raiz.esq.elemento + ") e dir(" + raiz.dir.elemento + ").");
            } else {
                raiz.dir = new NoAN(raiz.elemento);
                raiz.elemento = raiz.esq.elemento;
                raiz.esq.elemento = elemento;
                // System.out.println("Antes, dois elementos(F). Agora, raiz(" + raiz.elemento +
                // "), esq ("
                // + raiz.esq.elemento + ") e dir(" + raiz.dir.elemento + ").");
            }

            raiz.esq.cor = raiz.dir.cor = false;

            // Senao, a arvore tem tres ou mais elementos
        } else {
            // System.out.println("Arvore com tres ou mais elementos...");
            inserir(elemento, null, null, null, raiz);
        }

        raiz.cor = false;
    }

    private void balancear(NoAN bisavo, NoAN avo, NoAN pai, NoAN i) {

        // Se o pai tambem e preto, reequilibrar a arvore, rotacionando o avo
        if (pai.cor == true) {
            numComparacoes++;
            // 4 tipos de reequilibrios e acoplamento
            if (pai.elemento.getNome().compareTo(avo.elemento.getNome()) > 0) { // rotacao a esquerda ou
                numComparacoes++;
                // direita-esquerda
                if (i.elemento.getNome().compareTo(pai.elemento.getNome()) > 0) {
                    numComparacoes++;
                    avo = rotacaoEsq(avo);
                } else {
                    avo = rotacaoDirEsq(avo);
                }

            } else { // rotacao a direita ou esquerda-direita
                if (i.elemento.getNome().compareTo(pai.elemento.getNome()) < 0) {
                    numComparacoes++;
                    avo = rotacaoDir(avo);
                } else {
                    avo = rotacaoEsqDir(avo);
                }
            }

            if (bisavo == null) {
                numComparacoes++;
                raiz = avo;
            } else if (avo.elemento.getNome().compareTo(bisavo.elemento.getNome()) < 0) {
                numComparacoes++;
                bisavo.esq = avo;
            } else {
                bisavo.dir = avo;
            }

            // reestabelecer as cores apos a rotacao
            avo.cor = false;
            avo.esq.cor = avo.dir.cor = true;
            // System.out.println("Reestabeler cores: avo(" + avo.elemento + "->branco) e
            // avo.esq / avo.dir("
            // + avo.esq.elemento + "," + avo.dir.elemento + "-> pretos)");
        } // if(pai.cor == true)
    }

    private void inserir(ArvoreAlvinegra elemento, NoAN bisavo, NoAN avo, NoAN pai, NoAN i) throws Exception {
        if (i == null) {
            numComparacoes++;
            if (elemento.getNome().compareTo(pai.elemento.getNome()) < 0) {
                numComparacoes++;
                i = pai.esq = new NoAN(elemento, true);
            } else {
                i = pai.dir = new NoAN(elemento, true);
            }

            if (pai.cor == true) {
                numComparacoes++;
                balancear(bisavo, avo, pai, i);
            }

        } else {

            // Achou um 4-no: eh preciso fragmeta-lo e reequilibrar a arvore
            if (i.esq != null && i.dir != null && i.esq.cor == true && i.dir.cor == true) {
                numComparacoes++;
                i.cor = true;
                i.esq.cor = i.dir.cor = false;
                if (i == raiz) {
                    i.cor = false;
                } else if (pai.cor == true) {
                    numComparacoes++;
                    balancear(bisavo, avo, pai, i);
                }
            }

            if (elemento.getNome().compareTo(i.elemento.getNome()) < 0) {
                numComparacoes++;
                inserir(elemento, avo, pai, i, i.esq);
            } else if (elemento.getNome().compareTo(i.elemento.getNome()) > 0) {
                numComparacoes++;
                inserir(elemento, avo, pai, i, i.dir);
            } else {
                throw new Exception("Erro inserir (elemento repetido)!");
            }
        }
    }

    private NoAN rotacaoDir(NoAN no) {
        // System.out.println("Rotacao DIR(" + no.elemento + ")");
        NoAN noEsq = no.esq;
        NoAN noEsqDir = noEsq.dir;

        noEsq.dir = no;
        no.esq = noEsqDir;

        return noEsq;
    }

    private NoAN rotacaoEsq(NoAN no) {
        // System.out.println("Rotacao ESQ(" + no.elemento + ")");
        NoAN noDir = no.dir;
        NoAN noDirEsq = noDir.esq;

        noDir.esq = no;
        no.dir = noDirEsq;
        return noDir;
    }

    private NoAN rotacaoDirEsq(NoAN no) {
        no.dir = rotacaoDir(no.dir);
        return rotacaoEsq(no);
    }

    private NoAN rotacaoEsqDir(NoAN no) {
        no.esq = rotacaoEsq(no.esq);
        return rotacaoDir(no);
    }
}

class ArvoreAlvinegra {
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
    public ArvoreAlvinegra() {
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
    public ArvoreAlvinegra(String nome, String formato, String duracao, String paisDeOrigem, String idiomaOriginal,
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

    public static ArvoreAlvinegra lerAtributo(String endereco) {
        ArvoreAlvinegra s1 = new ArvoreAlvinegra();
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

    public void imprimir(ArvoreAlvinegra s1) {
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
        Alvinegra arvoreAlvinegra = new Alvinegra();
        String entrada = MyIO.readLine();
        int numMovimentacoes = 0; // Nas árvores não há movimentações
        String endereco = PREFIXO;
        Long tempoInicial = System.currentTimeMillis();

        while (!isFim(entrada)) {
            String caminhoDoArquivo = endereco + entrada;
            arvoreAlvinegra.inserir(lerAtributo(caminhoDoArquivo));
            entrada = MyIO.readLine();
        }

        entrada = MyIO.readLine();
        while (!isFim(entrada)) {
            System.out.print(arvoreAlvinegra.pesquisar(entrada) ? "SIM\n" : "NAO\n");
            entrada = MyIO.readLine();
        }
        Long tempoFinal = System.currentTimeMillis();
        arquivoLog(tempoFinal - tempoInicial, arvoreAlvinegra.numComparacoes);

    }

    public static void arquivoLog(Long tempoExecucao, int numeroComparacoes, int numeroMovimentacoes) {
        Arq.openWrite("matricula_alvinegra.txt", "UTF-8");

        Arq.print("712325\t");
        Arq.print(tempoExecucao + " milissegundo(s)\t");
        Arq.print(numeroComparacoes + " comparações\t");

        Arq.close();
    }

}