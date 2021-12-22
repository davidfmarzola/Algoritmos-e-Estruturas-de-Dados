import java.util.*;

class MatrizDinamica {
   private Celula inicio;
   private int linha, coluna;

   // alocar a matriz com this.linha linhas e this.coluna colunas
   public MatrizDinamica(int linha, int coluna) {
      this.linha = linha;
      this.coluna = coluna;
      inicio = new Celula();
      Celula i1 = inicio, j1 = inicio;

      // aloco as celulas da primeira linha
      for (int i = 0; i < coluna - 1; i++) {
         i1.dir = new Celula();
         i1.dir.esq = i1;
         i1 = i1.dir;
      }
      // aloco as celulas da primeira coluna
      for (int i = 0; i < linha - 1; i++) {
         j1.inf = new Celula();
         j1.inf.sup = j1;
         j1 = j1.inf;
      }
      // aloco as demais celulas
      Celula Ccoluna = inicio.dir;
      Celula Clinha = inicio.inf;
      Celula Ctmp = new Celula();

      for (int k = 0; k < linha - 1; k++) {
         Ctmp = Clinha;
         for (int a = 0; a < coluna - 1; a++) {
            Ctmp.dir = new Celula();
            Ctmp.dir.esq = Ctmp;
            Ctmp = Ctmp.dir;
            Ctmp.sup = Ccoluna;
            Ctmp.sup.inf = Ctmp;
            Ccoluna = Ccoluna.dir;
         }
         Ccoluna = Clinha.dir;
         Clinha = Clinha.inf;
      }
   }

   public void mostrar() {
      Celula tmp = inicio;
      Celula tmp2 = inicio;
      for (int i = 0; i < linha; i++) {
         for (int j = 0; j < coluna; j++) {
            System.out.print(tmp.elemento + " ");
            tmp = tmp.dir;
         }
         tmp2 = tmp2.inf;
         System.out.print("\n");
         tmp = tmp2;
      }
   }

   public void inserirNaMatriz(int elemento, int linha, int coluna) {
      Celula a = inicio;
      for (int i = 0; i < linha; i++) {
         a = a.inf;
      }

      for (int j = 0; j < coluna; j++) {
         a = a.dir;
      }
      a.elemento = elemento;

   }

   public void tratarLinha(String[] linha, int numLinhas, int numColunas) {
      // System.out.println(Arrays.toString(linha));
      String[] vetorTratado;
      for (int i = 0; i < numLinhas; i++) {
         // if (linha[i] != null) {
         vetorTratado = linha[i].split(" ");
         // System.out.println(Arrays.toString(vetorTratado));
         for (int j = 0; j < vetorTratado.length; j++) {
            this.inserirNaMatriz(Integer.parseInt(vetorTratado[j]), i, j);
         }
         // }
      }
      // vetorTratado = new String[] {};
      // System.out.println("Passou aqui!");
   }

   public boolean isQuadrada() {
      return this.linha == this.coluna;
   }

   public void mostrarDiagonalPrincipal() {
      Celula referencia = inicio;
      if (isQuadrada() == true) {
         System.out.print(referencia.elemento + " ");
         for (int i = 0; i < this.linha - 1; i++) {
            referencia = referencia.dir.inf;
            System.out.print(referencia.elemento + " ");
         }
      }
      System.out.print("\n");
   }

   public Celula getUltimaCelula() {
      Celula ultimo = inicio;
      for (int i = 0; i < coluna - 1; ultimo = ultimo.dir, i++)
         ;
      return ultimo;
   }

   public void mostrarDiagonalSecundaria() {
      Celula referencia = getUltimaCelula();
      if (isQuadrada() == true) {
         System.out.print(referencia.elemento + " ");
         for (int i = 0; i < this.linha - 1; i++) {
            referencia = referencia.inf.esq;
            System.out.print(referencia.elemento + " ");
         }
      }
      System.out.print("\n");
   }

   public MatrizDinamica soma(MatrizDinamica m) {
      MatrizDinamica resp = new MatrizDinamica(linha, coluna);
      Celula tmpA = this.inicio;
      Celula tmpB = m.inicio;
      Celula tmpC = resp.inicio;
      Celula a = tmpA;
      Celula b = tmpB;
      Celula c = tmpC;

      if (this.linha == m.linha && this.coluna == m.coluna) {
         for (int i = 0; i < linha; i++) {
            for (int j = 0; j < coluna; j++) {
               // sendo c (pont em resp), a (em this) e b (em m)
               c.elemento = a.elemento + b.elemento;
               c = c.dir;
               a = a.dir;
               b = b.dir;

            }
            tmpA = tmpA.inf;
            tmpB = tmpB.inf;
            tmpC = tmpC.inf;
            a = tmpA;
            b = tmpB;
            c = tmpC;
         }
         // ...
      }
      // resp.mostrar();

      return resp;

   }

   public MatrizDinamica multiplica(MatrizDinamica m) {
      MatrizDinamica resp = new MatrizDinamica(linha, coluna);
      // primeira matriz de entrada
      Celula tmpa = this.inicio;
      // segunda matriz
      Celula tmpb = m.inicio;
      Celula tmpc = resp.inicio;
      Celula a = tmpa;
      Celula b = tmpb;
      Celula c = tmpc;

      if (this.linha == m.linha && this.coluna == m.coluna) {
         for (int i = 0; i < linha; i++) {
            for (int j = 0; j < coluna; j++) {
               for (int k = 0; k < coluna; k++) {
                  // sendo c (pont em resp), a (em this) e b (em m)
                  c.elemento += (a.elemento * b.elemento);
                  a = a.dir;
                  b = b.inf;
               }
               c = c.dir;
               a = tmpa;
               b = tmpb;
               b = b.dir;
            }
            tmpc = tmpc.inf;
            c = tmpc;
            tmpa = tmpa.inf;
            a = tmpa;
            b = tmpb;
         }
      }
      // resp.mostrar();
      return resp;
   }

   public void ler() {
      String[] vetorLinha = new String[linha];

      for (int i = 0; i < linha; i++) {
         vetorLinha[i] = MyIO.readLine();
         // System.out.println(vetorLinha[i].toString());
      }
      this.tratarLinha(vetorLinha, linha, coluna);
   }

   public static void main(String[] args) {
      int numCasos = MyIO.readInt();
      int k = 0;

      while (k < numCasos) {
         int numLinhas = MyIO.readInt();
         int numColunas = MyIO.readInt();

         MatrizDinamica matriz = new MatrizDinamica(numLinhas, numColunas);
         matriz.ler();
         // matriz.mostrar();
         matriz.mostrarDiagonalPrincipal();
         matriz.mostrarDiagonalSecundaria();

         numLinhas = MyIO.readInt();
         numColunas = MyIO.readInt();

         MatrizDinamica matriz2 = new MatrizDinamica(numLinhas, numColunas);
         matriz2.ler();
         // matriz2.mostrar();

         MatrizDinamica soma = matriz.soma(matriz2);
         soma.mostrar();
         MatrizDinamica multiplica = matriz.multiplica(matriz2);
         multiplica.mostrar();

         k++;
      }
   }
}

class Celula {
   public int elemento;
   public Celula inf, sup, esq, dir;

   public Celula() {
      this(0);
   }

   public Celula(int elemento) {
      this(elemento, null, null, null, null);
   }

   public Celula(int elemento, Celula inf, Celula sup, Celula esq, Celula dir) {
      this.elemento = elemento;
      this.inf = inf;
      this.sup = sup;
      this.esq = esq;
      this.dir = dir;
   }
}
