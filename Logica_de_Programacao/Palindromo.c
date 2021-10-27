#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#define boolean short
#define true 1
#define false 0

/*Os únicos métodos permitidos são char charAt(int) e int length()
da classe String.*/

boolean isFim(char s[])
{
    return (s[0] == 'F' && s[1] == 'I' && s[2] == 'M');
}
short palindro(char s[])
{

    int tamanho = strlen(s)-1;
    for (int i = (strlen(s) - 2) / 2; i >= 0; i--)
    {
        if (s[i] != s[tamanho - 2 - i])
            return false; 
    }
    return true; 
}
int main()
{
    char texto[1000];
    fgets(texto, 1000, stdin);

    boolean palindromo = true;

    while (!isFim(texto))
    {
        /*PROBLEMA: Não identifica a palavra FIM no redirecionamento devido ao '\n' - devido ao '\n'*/
        printf(palindro(texto) ? "SIM\n" : "NAO\n");
        
        fgets(texto, 1000, stdin);
    }
    return 0;
}
