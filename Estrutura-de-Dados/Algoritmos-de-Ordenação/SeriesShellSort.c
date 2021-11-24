#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#define boolean short
#define true 1
#define false 0
//cuidado com a entrada privada(numero não deve ser fixo - constante)
//#define numLinhas 61

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

void imprimir(series s1)
{
    printf("%s %s %s %s %s %s %s %d %d\n", s1.nome, s1.formato, s1.duracao, s1.paisDeOrigem, s1.idiomaOriginal, s1.emissoraDeTelevisaoOriginal, s1.transmissaoOriginal, s1.numeroTemporadas, s1.numeroEpisodios);
}

char *remove_espacos(char *atributo)
{
    if (atributo[0] == ' ')
    {
        atributo = atributo + 1;
    }
    if (atributo[strlen(atributo) - 1] == ' ')
    {
        atributo[strlen(atributo) - 1] = '\0';
    }
    return atributo;
}

series lerAtributo(FILE *fp)
{
    series s1;
    strcpy(s1.nome, remove_espacos(trataCaracteresEspeciais(trataNome(removeTags(pegaLinha(fp, "firstHeading"))))));
    strcpy(s1.formato, remove_espacos(trataCaracteresEspeciais(removeTags(pegaLinha(fp, "Formato")))));
    strcpy(s1.duracao, remove_espacos(trataCaracteresEspeciais(removeTags(pegaLinha(fp, "Duração")))));
    strcpy(s1.paisDeOrigem, remove_espacos(trataCaracteresEspeciais(removeTags(pegaLinha(fp, "País de origem")))));
    strcpy(s1.idiomaOriginal, remove_espacos(trataCaracteresEspeciais(removeTags(pegaLinha(fp, "Idioma original")))));
    strcpy(s1.emissoraDeTelevisaoOriginal, remove_espacos(trataCaracteresEspeciais(removeTags(pegaLinha(fp, "Emissora de televisão original")))));
    strcpy(s1.transmissaoOriginal, remove_espacos(trataCaracteresEspeciais(removeTags(pegaLinha(fp, "Transmissão original")))));
    s1.numeroTemporadas = converteParaInt(trataCaracteresEspeciais(removeTags(pegaLinha(fp, "N.º de temporadas"))));
    s1.numeroEpisodios = converteParaInt(trataCaracteresEspeciais(removeTags(pegaLinha(fp, "N.º de episódios"))));
    return s1;
}

struct lista
{
    int indice;
};

// variáveis globais
series vetorObjetoSeries[100][100];
int indice = 0;
int numeroComparacoes = 0;
int numeroMovimentacoes = 0;
double tempoExec = 0.0;

void arquivoLog()
{
    FILE *fp = fopen("matricula_shellsort.txt", "wb");
    if (fp == NULL)
    {
        printf("O arquivo não pode ser aberto!");
        exit(0);
    }
    fprintf(fp, "712325\t");
    fprintf(fp, "%lf milissegundos\t", tempoExec);
    fprintf(fp, "%d comparações\t", numeroComparacoes);
    fprintf(fp, "%d movimentações\n", numeroMovimentacoes);
    fclose(fp);
}

void imprimirLista()
{
    for (int i = 0; i < indice; i++)
    {
        imprimir(vetorObjetoSeries[i][0]);
    }
}

void insercaoPorCor(int cor, int h, int tam)
{
    for (int i = (h + cor); i < tam; i += h)
    {
        series tmp = *vetorObjetoSeries[i];
        int j = i - h;
        while ((j >= 0) && (strcmp(vetorObjetoSeries[j]->idiomaOriginal, tmp.idiomaOriginal) > 0))
        {
            numeroComparacoes++;
            *vetorObjetoSeries[j + h] = *vetorObjetoSeries[j];
            numeroMovimentacoes++;
            j -= h;
        }
        while ((j >= 0) && (strcmp(vetorObjetoSeries[j]->idiomaOriginal, tmp.idiomaOriginal) == 0) && (strcmp(vetorObjetoSeries[j]->nome, tmp.nome) > 0))
        {
            numeroComparacoes += 2;
            *vetorObjetoSeries[j + h] = *vetorObjetoSeries[j];
            numeroMovimentacoes++;
            j -= h;
        }
        *vetorObjetoSeries[j + h] = tmp;
        numeroMovimentacoes++;
    }
}

void shellsort(int tam)
{
    clock_t t;   //variável para armazenar tempo
    t = clock(); //armazena tempo
    int h = 1;
    do
    {
        h = (h * 3) + 1;
    } while (h < tam);
    do
    {
        h /= 3;
        for (int cor = 0; cor < h; cor++)
        {

            insercaoPorCor(cor, h, tam);
        }
    } while (h != 1);
    t = clock() - t; //tempo final - tempo inicial
    //imprime o tempo na tela
    tempoExec = ((double)t) / ((CLOCKS_PER_SEC / 1000)); //conversão para double
    arquivoLog();
}

void inserirFim(series serie)
{
    if (indice >= 61)
        printf("there's impossible to insert, no memory avaliable\n");
    vetorObjetoSeries[indice][0] = serie;
    indice++;
}

series abertura_do_arquivo(char caminhoDoArquivo[150])
{
    FILE *fp = fopen(caminhoDoArquivo, "r");

    series objeto = lerAtributo(fp);

    fclose(fp);
    return objeto;
}

int main()
{
    char endereco[100] = "/tmp/series/";
    char Html[100] = "";
    scanf(" %[^\n]", Html);
    char caminhoDoArquivo[150] = "";
    int i = 0;

    while (!isFim(Html))
    {
        strcat(strcpy(caminhoDoArquivo, endereco), Html);
        series objeto = abertura_do_arquivo(caminhoDoArquivo);
        inserirFim(objeto);
        i++;
        scanf(" %[^\n]", Html);
    }
    shellsort(i);
    imprimirLista();

    return 0;
}
