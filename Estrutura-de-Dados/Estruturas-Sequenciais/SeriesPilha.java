import java.io.IOException;
import java.util.*;

class SeriesPilha {
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
    static final String PREFIXO = "/tmp/series/";

    // construtor primário
    public SeriesPilha() {
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
    public SeriesPilha(String nome, String formato, String duracao, String paisDeOrigem, String idiomaOriginal,
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

    public static SeriesPilha lerAtributo(String endereco) {
        SeriesPilha s1 = new SeriesPilha();
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

    public void imprimir(SeriesPilha s1) {
        System.out.println(s1.getNome() + " " + s1.getFormato() + " " + s1.getDuracao() + " " + s1.getPaisDeOrigem()
                + " " + s1.getIdiomaOriginal() + " " + s1.getEmissoraDeTelevisaoOriginal() + " "
                + s1.getTransmissaoOriginal() + " " + s1.getNumeroTemporadas() + " " + s1.getNumeroEpisodios());
    }

    // Abre o arquivo e retorna o código fonte no vetorLido[i]
    public static String[] lerCodigoFonte(String endereco) {
        String[] vetorLido = new String[3000];
        int linha = 0;

        if (!Arq.openRead(endereco, "UTF-8")) {
            System.err.printf("Erro ao abrir o arquivo %s\n", endereco);
            System.exit(1);
        }
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
        String entrada = ler.nextLine();
        int i = 0;
        String[] primeiraParte = new String[35];
        while (!isFim(entrada)) {
            // empilho a primeira parte
            primeiraParte[i++] = entrada;
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
        String[] vetorConcatenado = concatenarVetores(primeiraParte, segundaParte, i);
        empilhar(vetorConcatenado, i);
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

    public static void empilhar(String[] vetor, int primeiraParte) throws Exception {
        PilhaSequencial pilha = new PilhaSequencial();
        for (int j = 0; j < primeiraParte; j++) {
            pilha.empilhar(lerAtributo(PREFIXO + vetor[j]));
        }
        for (int i = primeiraParte; i < vetor.length; i++) {
            if (vetor[i].charAt(0) == 'I') {
                String[] vetorTratado = vetor[i].split(" ");
                pilha.empilhar(lerAtributo(PREFIXO + vetorTratado[1]));
            } else {
                pilha.adicionaNomesRemovidos(pilha.desempilhar());
            }
        }
        pilha.imprimirObjetosRemovidos();
        pilha.imprimirObjetosSetados();
    }

}

class PilhaSequencial {
    public SeriesPilha[] objSeries;
    static ArrayList<String> objetosRemovidos;
    public int tamanho_da_pilha;

    PilhaSequencial() {
        this(40);
    }

    PilhaSequencial(int tamanho) {
        tamanho_da_pilha = 0;
        objSeries = new Series[tamanho];
        objetosRemovidos = new ArrayList<String>();
        // objetosRemovidos = new ArrayList<String>();
    }

    public boolean pilhaVazia() {
        // isEmpty
        return (tamanho_da_pilha == 0);
    }

    public SeriesPilha desempilhar() throws Exception {
        // remover (desempilhar, pop)
        if (pilhaVazia())
            throw new Exception("Erro: pilha vazia!");
        return objSeries[--tamanho_da_pilha];
    }

    public void empilhar(SeriesPilha valor) throws Exception {
        // inserir (empilhar, push)
        if (tamanho_da_pilha >= objSeries.length) {
            throw new Exception("Erro: tamanho incompatível!");
        }
        objSeries[tamanho_da_pilha++] = valor;
    }

    public void adicionaNomesRemovidos(SeriesPilha series) throws Exception {
        String nome = series.getNome();
        objetosRemovidos.add(nome);
    }

    public void imprimirObjetosRemovidos() throws Exception {
        for (int i = 0; i < objetosRemovidos.size(); i++) {
            System.out.println("(R) " + objetosRemovidos.get(i));
        }
    }

    public void imprimirObjetosSetados() throws Exception {
        for (int i = tamanho_da_pilha - 1; i >= 0; i--) {
            objSeries[i].imprimir(objSeries[i]);
        }
    }

}
