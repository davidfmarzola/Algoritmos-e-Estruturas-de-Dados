#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <err.h>
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
} PilhaFlexivelSeries;

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

void imprimir(PilhaFlexivelSeries s1)
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

PilhaFlexivelSeries lerAtributo(FILE *fp)
{
    PilhaFlexivelSeries s1;
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
//         imprimir(vetorObjetoPilhaFlexivelSeries[i][0]);
//     }
// }

//TIPO CELULA ===================================================================
typedef struct Celula {
	PilhaFlexivelSeries elemento;        // Elemento inserido na celula.
	struct Celula* prox; // Aponta a celula prox.
} Celula;

Celula* novaCelula(PilhaFlexivelSeries elemento) {
   Celula* nova = (Celula*) malloc(sizeof(Celula));
   nova->elemento = elemento;
   nova->prox = NULL;
   return nova;
}

//PILHA PROPRIAMENTE DITA =======================================================
Celula* topo; // Sem celula cabeca.


/**
 * Cria uma fila sem elementos.
 */
void start () {
   topo = NULL;
}


/**
 * Insere elemento na pilha (politica FILO).
 * @param x int elemento a inserir.
 */
void inserir(PilhaFlexivelSeries serie) {
   Celula* tmp = novaCelula(serie);
   tmp->prox = topo;
   topo = tmp;
   tmp = NULL;
}

/**
 * Remove elemento da pilha (politica FILO).
 * @return Elemento removido.
 */
PilhaFlexivelSeries remover() {
   if (topo == NULL) {
      errx(1, "Erro ao remover!");
   }

   PilhaFlexivelSeries resp = topo->elemento;
   Celula* tmp = topo;
   topo = topo->prox;
   tmp->prox = NULL;
   free(tmp);
   tmp = NULL;
   return resp;
}


/**
 * Mostra os elementos separados por espacos, comecando do topo.
 */
void mostrar() {
   Celula* i;
   for(i = topo; i != NULL; i = i->prox) {
      imprimir(i->elemento);
   }

}

PilhaFlexivelSeries abertura_do_arquivo(char caminhoDoArquivo[150])
{
    FILE *fp = fopen(caminhoDoArquivo, "r");

    PilhaFlexivelSeries objeto = lerAtributo(fp);

    fclose(fp);
    return objeto;
}

PilhaFlexivelSeries objeto(char nomeSerie[100]){
    char endereco[100] = "series/";
    char caminhoDoArquivo[150] = "";

    strcat(strcpy(caminhoDoArquivo, endereco), nomeSerie);
    PilhaFlexivelSeries objeto = abertura_do_arquivo(caminhoDoArquivo);
    return objeto;
}

void ler_SegundaParte(char nomeSerie[100]){
    int qtdOperacoes = 0;
    scanf("%d", &qtdOperacoes);
    
    for (int j = 0; j < qtdOperacoes; j++)
    {
        scanf(" %[^\n]", nomeSerie);
        strtok(nomeSerie, " ");
        if (nomeSerie[0] == 'I')
        {
            char *t = strtok(NULL, " ");
            inserir(objeto(t));
        }
        else
        {
            printf("(R) %s\n", remover().nome);
        }
    }
}

int main()
{
    char nomeSerie[100] = "";
    scanf(" %[^\n]", nomeSerie);
    int i = 0;

    start();
    while (!isFim(nomeSerie))
    {
        inserir(objeto(nomeSerie));
        i++;
        scanf(" %[^\n]", nomeSerie);
    }
    strcpy(nomeSerie, "");
    ler_SegundaParte(nomeSerie);
    mostrar();

    return 0;
}