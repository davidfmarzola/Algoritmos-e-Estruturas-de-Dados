#include <stdlib.h>
#include <stdio.h>

void leitura(FILE *fp)
{
    fp = fopen("arquivo.txt", "r");

    if (!feof(fp))
    {
        double num;
        fread(&num, sizeof(double), 1, fp);
        //fscanf(fp, "%lf", &num);
        leitura(fp);

        if (num == (int)num)
        {
            printf("%d", (int)num);
        }
        else
        {
            printf("%lf", num);
        }
    }
    fclose(fp);
}

int main()
{
    FILE *fp = fopen("arquivo.txt", "wb");
    if (fp == NULL)
    {
        printf("O arquivo não pode ser aberto!");
        exit(0);
    }

    //qtd. de numeros(entradas)
    int n = 0;
    scanf("%d", &n);
    int i = 0;
    
    //Lendo dados do teclado e escrevendo-os no arquivo
    while (i < n)
    {
        double d;
        scanf("%lf", &d);
        fprintf(fp, "%lf\n", d);
        //fread(&d, sizeof(double), 1, fp);
        i++;
    }
    leitura(fp);
    fclose(fp);

    //leitura e escrita de tras pra frente dos dados do arquivo

    return 0;
}
