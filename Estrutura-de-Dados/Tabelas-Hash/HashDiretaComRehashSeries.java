class HashDiretoRehash {
    HashDiretaComRehashSeries tabela[];
    int m;
    int numComparacoes = 0;
    // int NULO = -1;

    public HashDiretoRehash() {
        this(21);
    }

    public HashDiretoRehash(int m) {
        this.m = m;
        this.tabela = new HashDiretaComRehashSeries[this.m];
        for (int i = 0; i < m; i++) {
            numComparacoes++;
            tabela[i] = null;
        }
        numComparacoes++;
    }

    public int h(int elemento) {
        return elemento % m;

    }

    public int reh(int elemento) {
        return (++elemento % m) % m;
    }

    public int getValorAsc(String str) {
        int valorAsc = 0;
        // str = str.trim();
        for (int i = 0; i < str.length(); i++) {
            numComparacoes++;
            valorAsc += (int) str.charAt(i);
        }
        numComparacoes++;

        return valorAsc;
    }

    public boolean inserir(HashDiretaComRehashSeries elemento) {
        boolean resp = false;
        int ascNome = getValorAsc(elemento.getNome());

        // if (elemento != null) {

        int pos = h(ascNome);

        numComparacoes++;
        if (tabela[pos] == null) {
            tabela[pos] = elemento;
            resp = true;

        } else {
            pos = reh(ascNome);

            numComparacoes++;
            if (tabela[pos] == null) {
                tabela[pos] = elemento;
                resp = true;
            }
        }
        // }

        return resp;
    }

    public void mostrar() {
        for (int i = 0; i < m; i++) {
            numComparacoes += 2;
            if (tabela[i] != null) {
                System.out.println(tabela[i].getNome());
            }
        }
        numComparacoes++;
    }

    public boolean pesquisar(String elemento) {
        boolean resp = false;
        int ascNome = getValorAsc(elemento);

        int pos = h(ascNome);
        numComparacoes++;
        if (tabela[pos] != null) {
            numComparacoes++;
            if (tabela[pos].getNome().equals(elemento)) {
                resp = true;

            } else {
                pos = reh(ascNome);
                numComparacoes++;
                if (tabela[pos].getNome().equals(elemento)) {
                    resp = true;
                }
            }
        }
        return resp;
    }
}

class HashDiretaComRehashSeries {
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
    public HashDiretaComRehashSeries() {
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
    public HashDiretaComRehashSeries(String nome, String formato, String duracao, String paisDeOrigem,
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

    public static HashDiretaComRehashSeries lerAtributo(String endereco) {
        HashDiretaComRehashSeries s1 = new HashDiretaComRehashSeries();
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

    public void imprimir(HashDiretaComRehashSeries s1) {
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
        HashDiretoRehash hashDiretoRehash = new HashDiretoRehash();
        String entrada = MyIO.readLine();
        HashDiretaComRehashSeries[] vetorObjetoSeries = new HashDiretaComRehashSeries[61];

        Long tempoInicial = System.currentTimeMillis();
        // inserindo no array
        while (!isFim(entrada)) {
            String caminhoDoArquivo = "series/" + entrada;
            hashDiretoRehash.inserir(lerAtributo(caminhoDoArquivo));

            entrada = MyIO.readLine();
        }
        // hashDiretoRehash.mostrar();
        entrada = MyIO.readLine();
        // pesquisando no array
        while (!isFim(entrada)) {
            System.out.println(hashDiretoRehash.pesquisar(entrada) ? " SIM" : " NAO");

            entrada = MyIO.readLine();
        }

        Long tempoFinal = System.currentTimeMillis();
        arquivoLog(tempoFinal - tempoInicial, hashDiretoRehash.numComparacoes);

    }

    public static void arquivoLog(Long tempoExecucao, int numComparacoes) {
        Arq.openWrite("matricula_hashRehash.txt", "UTF-8");

        Arq.print("712325\t");
        Arq.print(tempoExecucao + " milissegundo(s)\t");
        Arq.print(numComparacoes + " comparações\t");

        Arq.close();
    }

}
