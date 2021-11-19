#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define boolean short
#define true 1
#define false 0

boolean isFim(char s[])
{
    return (s[0] == 'F' && s[1] == 'I' && s[2] == 'M');
}

boolean isNumber(char s)
{
    return s >= '0' && s <= '9';
}

typedef struct
{
    char nome[100];
    char formato[100];
    char duracao[100];
    char paisDeOrigem[100];
    char idiomaOriginal[100];
    char emissoraDeTelevisaoOriginal[100];
    char transmissaoOriginal[150];
    int numeroTemporadas;
    int numeroEpisodios;
} series;

char *removeTags(char *old)
{
    char *newLine = (char *)malloc((100) * sizeof(char));
    int i = 0, j = 0;
    //trata o espaço em nome
    while (old[i] != '<')
    {
        i++;
    }
    while (i < strlen(old))
    {
        if (old[i] == '<')
        {
            i++;
            while (old[i] != '>')
                i++;
        }
        else
        {
            newLine[j] = old[i];
            j++;
        }
        i++;
    }
    //substituo o '\n' por '\0'
    newLine[j - 1] = '\0';
    return newLine;
}

//funções de tratamento
char *trataNome(char *nome)
{
    int i = 0, j = 0;
    char *res = (char *)malloc((100) * sizeof(char));
    while (i < strlen(nome))
    {
        if (nome[i] == '(')
        {
            i = strlen(nome);
        }
        else
        {
            res[j] = nome[i];
            i++;
            j++;
        }
    }
    return res;
}

char *pegaLinha(FILE *fp, char *identificaAtributo)
{
    int i = 0;
    char *linha = (char *)malloc((100) * sizeof(char));
    char buf[3000];
    //lendo o codigo fonte
    fgets(buf, 3000, fp);
    while (strstr(buf, identificaAtributo) == NULL)
    {
        linha = fgets(buf, 3000, fp);
    }
    if (strstr(identificaAtributo, "firstHeading") != NULL)
    {
        ; //pego a mesma linha lida anteriormente
    }
    else
    {
        linha = fgets(buf, 3000, fp);
    }

    return linha;
}

char *trataCaracteresEspeciais(char *semTags)
{
    int i = 0, j = 0;
    char *res = (char *)malloc((100) * sizeof(char));
    while (i < strlen(semTags))
    {
        if (semTags[i] == '&')
        {
            i += 6;
        }
        else
        {
            res[j] = semTags[i];
            j++;
            i++;
        }
    }
    return res;
}

int converteParaInt(char *semTags)
{
    int i = 0, j = 0;
    char *res = (char *)malloc((100) * sizeof(char));
    while (i < strlen(semTags) && isNumber(semTags[i]) == true)
    {
        res[j] = semTags[i];
        i++;
        j++;
    }
    return atoi(res);
}

series lerAtributo(FILE *fp)
{
    series s1;
    strcpy(s1.nome, trataCaracteresEspeciais(trataNome(removeTags(pegaLinha(fp, "firstHeading")))));
    strcpy(s1.formato, trataCaracteresEspeciais(removeTags(pegaLinha(fp, "Formato"))));
    strcpy(s1.duracao, trataCaracteresEspeciais(removeTags(pegaLinha(fp, "Duração"))));
    strcpy(s1.paisDeOrigem, trataCaracteresEspeciais(removeTags(pegaLinha(fp, "País de origem"))));
    strcpy(s1.idiomaOriginal, trataCaracteresEspeciais(removeTags(pegaLinha(fp, "Idioma original"))));
    strcpy(s1.emissoraDeTelevisaoOriginal, trataCaracteresEspeciais(removeTags(pegaLinha(fp, "Emissora de televisão original"))));
    strcpy(s1.transmissaoOriginal, trataCaracteresEspeciais(removeTags(pegaLinha(fp, "Transmissão original"))));
    s1.numeroTemporadas = converteParaInt(trataCaracteresEspeciais(removeTags(pegaLinha(fp, "N.º de temporadas"))));
    s1.numeroEpisodios = converteParaInt(trataCaracteresEspeciais(removeTags(pegaLinha(fp, "N.º de episódios"))));
    return s1;
}

void imprimir(series s1)
{
    printf("%s %s %s %s %s %s %s %d %d\n", s1.nome, s1.formato, s1.duracao, s1.paisDeOrigem, s1.idiomaOriginal, s1.emissoraDeTelevisaoOriginal, s1.transmissaoOriginal, s1.numeroTemporadas, s1.numeroEpisodios);
}

struct lista
{
    int indice;
};

series objSeries[50][100];
int indice = 0;

void imprimirLista()
{
    for (int i = 0; i < indice; i++)
    {
        imprimir(objSeries[i][0]);
    }
}

void inserirFim(series serie)
{
    if (indice >= 50)
        printf("there's impossible to insert, no memory avaliable\n");
    objSeries[indice][0] = serie;
    indice++;
}

void inserirInicio(series serie)
{
    if (indice >= 50)
        printf("Erro!");

    for (int i = indice; i > 0; i--)
    {
        objSeries[i][0] = objSeries[i - 1][0];
    }
    objSeries[0][0] = serie;
    indice++;
}

void inserir(series serie, int pos)
{
    if (indice >= 50 || pos < 0 || pos > indice)
    {
        printf("Erro ao inserir");
        exit(0);
    }

    // levar elementos para o fim do array
    for (int i = indice; i > pos; i--)
    {
        objSeries[i][0] = objSeries[i - 1][0];
    }
    objSeries[pos][0] = serie;
    indice++;
}

series removerInicio()
{
    if (indice == 0)
    {
        printf("Erro ao remover!");
        exit(0);
    }
    series resp = objSeries[0][0];
    indice--;

    for (int i = 0; i < indice; i++)
    {
        objSeries[i][0] = objSeries[i + 1][0];
    }
    return resp;
}

series removerFim()
{
    // validar remocao
    if (indice == 0)
    {
        printf("Erro ao remover!");
        exit(0);
    }
    return *objSeries[--indice];
}

series remover(int pos)
{
    // validar remocao
    if (indice == 0 || pos < 0 || pos >= indice)
    {
        printf("Erro ao remover!");
        exit(0);
    }

    series resp = objSeries[pos][0];
    indice--;

    for (int i = pos; i < indice; i++)
    {
        objSeries[i][0] = objSeries[i + 1][0];
    }
    return resp;
}

void imprimirObjetosRemovidos(series objetoRemovido)
{
    printf("(R) %s\n", objetoRemovido.nome);
}

series abertura_do_arquivo(char caminhoDoArquivo[50])
{
    FILE *fp = fopen(caminhoDoArquivo, "r");

    series objeto = lerAtributo(fp);

    fclose(fp);
    return objeto;
}

void chamaInserirFimOuInicio(char *insercao)
{
    char *t = strtok(NULL, " ");
    char caminhoDoArquivo[50] = "series/";
    strcat(caminhoDoArquivo, t);
    series objeto = abertura_do_arquivo(caminhoDoArquivo);
    if(strcmp(insercao, "IF") == 0)
        inserirFim(objeto);
    else
        inserirInicio(objeto);
}

void chama_inserir_posicao()
{
    char *t = strtok(NULL, " ");
    int pos = atoi(t);
    t = strtok(NULL, " ");
    char *str = t;
    char caminhoDoArquivo[50] = "series/";
    strcat(caminhoDoArquivo, str);
    series objeto = abertura_do_arquivo(caminhoDoArquivo);
    inserir(objeto, pos);
}

void chamadaInsercoes(char *t)
{
    if (strcmp(t, "IF") == 0 || strcmp(t, "II") == 0)
    {
        chamaInserirFimOuInicio(t);
    }
    else
    {
        chama_inserir_posicao();
    }
}

void chamadaRemocoes(char *t)
{
    series objetoRemovido;
    if (strcmp(t, "RI") == 0)
    {
        objetoRemovido = removerInicio();
    }
    else if (strcmp(t, "RF") == 0)
    {
        objetoRemovido = removerFim();
    }
    else
    {
        t = strtok(NULL, " ");
        int pos = atoi(t);
        objetoRemovido = remover(pos);        
    }
    imprimirObjetosRemovidos(objetoRemovido);
}

void verificaOperacoes(char vetorLido[][100], int j)
{
    char *t;

    for (int i = 0; i < j; i++)
    {
        t = strtok(vetorLido[i], " ");
        if (t[0] == 'I')
        {
            chamadaInsercoes(t);
        }
        else
        {
            chamadaRemocoes(t);
        }
    }
    imprimirLista();
}

int main()
{
    char endereco[100] = "series/";
    char Html[100] = "";
    scanf(" %[^\n]", Html);
    char caminhoDoArquivo[150] = "";

    while (!isFim(Html))
    {
        strcat(strcpy(caminhoDoArquivo, endereco), Html);
        series objeto = abertura_do_arquivo(caminhoDoArquivo);
        inserirFim(objeto);
        scanf(" %[^\n]", Html);
    }
    int qtdOperacoes = 0;
    scanf("%d\n", &qtdOperacoes);
    char segundaParte[qtdOperacoes][100];
    int j = 0;

    while (j < qtdOperacoes)
    {
        scanf(" %[^\n]", segundaParte[j]);
        j++;
    }
    verificaOperacoes(segundaParte, j);

    return 0;
}
