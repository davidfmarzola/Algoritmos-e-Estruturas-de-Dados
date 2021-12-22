#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <err.h>
#include <math.h>
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
} FilaFlexivelSeries;

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

void imprimir(FilaFlexivelSeries s1)
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

FilaFlexivelSeries lerAtributo(FILE *fp)
{
    FilaFlexivelSeries s1;
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

// void imprimirLista()
// {
//     for (int i = 0; i < indice; i++)
//     {
//         imprimir(vetorObjetoSeries[i][0]);
//     }
// }

//TIPO CELULA ===================================================================
typedef struct Celula
{
    FilaFlexivelSeries elemento;     // Elemento inserido na celula.
    struct Celula *prox; // Aponta a celula prox.
} Celula;

Celula *novaCelula(FilaFlexivelSeries elemento)
{
    Celula *nova = (Celula *)malloc(sizeof(Celula));
    nova->elemento = elemento;
    nova->prox = NULL;
    return nova;
}

//FILA PROPRIAMENTE DITA ========================================================
Celula *primeiro;
Celula *ultimo;
int indice;
int tamanho;

/**
 * Cria uma fila sem elementos (somente no cabeca).
 */
void start()
{
    FilaFlexivelSeries s1; // não declarado(armazena lixo)
    primeiro = novaCelula(s1);
    ultimo = primeiro;
    indice = 0;
    tamanho = 6;
}

void getMedia()
{
    //printf("indice: %d\n", indice);
    int count = 0;
    for (Celula *i = primeiro->prox; i != NULL; i = i->prox)
    {
        count = count + i->elemento.numeroTemporadas;
    }
    double media = (double)count / indice;
    printf("%d\n", (int)(round(media)));
}

/**
 * Remove elemento da fila (politica FIFO).
 * @return Elemento removido.
 */
FilaFlexivelSeries remover()
{
    if (primeiro == ultimo)
    {
        errx(1, "Erro ao remover!");
    }
    Celula *tmp = primeiro;
    primeiro = primeiro->prox;
    FilaFlexivelSeries resp = primeiro->elemento;
    tmp->prox = NULL;
    free(tmp);
    tmp = NULL;
    indice--;
    return resp;
}

/**
 * Insere elemento na fila (politica FIFO).
 * @param x int Elemento a inserir.
 */
void inserir(FilaFlexivelSeries serie)
{
    ultimo->prox = novaCelula(serie);
    ultimo = ultimo->prox;

    indice++;
    if (indice == tamanho)
        remover();
    getMedia();
}

/**
 * Mostra os elementos separados por espacos.
 */
void mostrar()
{
    Celula *i;
    for (i = primeiro->prox; i != NULL; i = i->prox)
    {
        printf("%d\n", i->elemento.numeroTemporadas);
    }
}

FilaFlexivelSeries abertura_do_arquivo(char caminhoDoArquivo[150])
{
    FILE *fp = fopen(caminhoDoArquivo, "r");

    FilaFlexivelSeries objeto = lerAtributo(fp);

    fclose(fp);
    return objeto;
}

int main()
{
    char endereco[100] = "series/";
    char Html[100] = "";
    scanf(" %[^\n]", Html);
    char caminhoDoArquivo[150] = "";
    int i = 0;

    start();
    while (!isFim(Html))
    {
        strcat(strcpy(caminhoDoArquivo, endereco), Html);
        FilaFlexivelSeries objeto = abertura_do_arquivo(caminhoDoArquivo);
        inserir(objeto);
        i++;
        scanf(" %[^\n]", Html);
    }
    int qtdOperacoes = 0;
    scanf("%d", &qtdOperacoes);
    //scanf(" %[^\n]", Html);
    for (int j = 0; j < qtdOperacoes; j++)
    {
        scanf(" %[^\n]", Html);
        strtok(Html, " ");
        if (Html[0] == 'I')
        {
            char *t = strtok(NULL, " ");
            strcat(strcpy(caminhoDoArquivo, endereco), t);
            FilaFlexivelSeries objeto = abertura_do_arquivo(caminhoDoArquivo);
            inserir(objeto);
        }
        else
        {
            remover();
        }
    }
    //mostrar();

    return 0;
}
