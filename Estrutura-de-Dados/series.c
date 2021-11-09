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
//registro
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
    newLine[j-1] = '\0';
    return newLine;
}

//funções de tratamento de dados
char *trataNome(char *nome)
{
    int i = 0, j = 0;
    char *res = (char *)malloc((100) * sizeof(char));
    while (i<strlen(nome) )
    {   
        if(nome[i] == '('){
            i=strlen(nome);
        }else{
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
    if (identificaAtributo == "firstHeading")
    {
        ; //pego a mesma linha lida anteriormente
    }
    else
    {
        linha = fgets(buf, 3000, fp);
    }

    return linha;
}

char *trataCaracteresEspeciais(char *semTags){
    int i=0, j = 0;
    char *res = (char *)malloc((100) * sizeof(char));
    while(i<strlen(semTags)){
        if(semTags[i] == '&'){
            i+=6;
        }else{
            res[j] = semTags[i];
            j++;i++;
        }
    }
    return res;
}

int converteParaInt(char *semTags){
    int i = 0, j = 0;
    char *res = (char *)malloc((100) * sizeof(char));
    while(i<strlen(semTags) && isNumber(semTags[i]) == true){
        res[j] = semTags[i];
        i++;j++;
    }
    return atoi(res);
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

void imprimir(series s1)
{
    printf("%s %s %s %s %s %s %s %d %d\n", s1.nome, s1.formato, s1.duracao, s1.paisDeOrigem, s1.idiomaOriginal, s1.emissoraDeTelevisaoOriginal, s1.transmissaoOriginal, s1.numeroTemporadas, s1.numeroEpisodios);
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
        FILE *fp = fopen(caminhoDoArquivo, "r");

        imprimir(lerAtributo(fp));

        fclose(fp);
        scanf(" %[^\n]", Html);
    }

    return 0;
}
