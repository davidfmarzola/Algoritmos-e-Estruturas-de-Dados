#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#define boolean short
#define true 1
#define false 0

boolean isFim(char s[])
{
    return (s[0] == 'F' && s[1] == 'I' && s[2] == 'M');
}

boolean palindromo(char texto[], int i, int j)
{

    // condição de parada
    if (i >= ((strlen(texto) - 1) / 2))
    {
        if (texto[i] != texto[j])
        {
            return false;
        }
        else
        {
            // chamada recursiva
            palindromo(texto, i - 1, j + i);
        }
    }
    return true;
}

int main()
{
    char texto[1000];
    fgets(texto, 1000, stdin);

    while (!isFim(texto))
    {
        printf(palindromo(texto, strlen(texto) - 2, 0) ? "SIM\n" : "NAO\n");
        fgets(texto, 1000, stdin);
    }
}
