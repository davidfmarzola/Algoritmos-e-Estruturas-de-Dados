#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>
#include <string.h>

#define boolean short

boolean isFim(char s[])
{
    //printf("%c %c %c", s[0], s[1], s[2]);
    return (s[0] == 'F' && s[1] == 'I' && s[2] == 'M');
}
boolean isInteiro(char numero[100])
{ // primeiro converto o caractere na posição
    // "i" para int
    boolean retorno = true;
    // MyIO.println("Número passado como parâmetro: "+numero); // verificar número
    // que veio
    for (int i = 0; i < strlen(numero)-1; i++)
    { // String com valor "int"
        // devo converter o valor "int" da string para para o valor na tabela asc2
        // MyIO.println("Valor da posição na tabela asc2: " + (int) numero.charAt(i));
        if (numero[i] < 48 || numero[i] > 57)
            //printf("%c", numero[i]);
            retorno = false;
    }
    return retorno;
}

bool isLetra(char palavra[])
{
    for (int i = 0; i < strlen(palavra)-1; i= i+1)
    {
        //if((palavra[i] >= '0' && palavra[i] <= '9')) return false;
        if ((palavra[i] >= 'A') && (palavra[i] <= 'Z') || (palavra[i] >= 'a') && (palavra[i] <= 'z')){
            ;
            //se receber false uma vez, encerra o loop
        }else {return false;}
    }
    return true;
}



boolean isConsoante(char palavra[])
{   // vogais = a, e, i, o ou u !!!
    // se não é vogal é consoante
    for (int i = 0; i < strlen(palavra)-1; i++)
    {
        if (palavra[i] != 'a' && palavra[i] != 'e' && palavra[i] != 'i' && palavra[i] != 'o' && palavra[i] != 'u'&&isLetra(palavra)==true){
            ; // SE for diferente de TODAS AS VOGAIS(E a, E b...)
        }else{ return false;}
    }
    return true;
}

boolean isVogal(char palavra[])
{ // retorno se É VOGAL
    // se não é vogal é consoante
    for (int i = 0; i < strlen(palavra)-1; i++)
    {
        if (palavra[i] == 'a' || palavra[i] == 'e' || palavra[i] == 'i' || palavra[i] == 'o' || palavra[i] == 'u'){
            ;
        }else {return false;}
    }
    return true;
}

boolean isReal(char numeroReal[])
{
    for (int i = 0; i < strlen(numeroReal)-1; i++)
    {
        // todo número inteiro é real
        if ((numeroReal[i] == '.' || numeroReal[i] == ',' || (numeroReal[i]>=48 && numeroReal[i]<=57)) && isLetra(numeroReal)==false && numeroReal[i]!=' ')
        {
            ;
        }else {return false;}
    }
    return true;
}

int main()
{
    char texto[100];
    fgets(texto, 100, stdin);

    while (!isFim(texto))
    {
        printf(isVogal(texto) ? "SIM " : "NAO "); 
        printf(isConsoante(texto) ? "SIM ":"NAO ");
        printf(isInteiro(texto) ? "SIM " : "NAO ");
        printf(isReal(texto) ? "SIM \n" : "NAO\n");

        fgets(texto, 100, stdin);
    }
}