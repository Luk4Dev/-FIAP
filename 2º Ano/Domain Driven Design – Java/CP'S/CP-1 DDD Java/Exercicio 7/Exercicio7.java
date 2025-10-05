import java.util.Scanner;

//###############################################################################################
// Exercicio 7
// Objetivo: Contar quantos espaços existem em uma String.
//###############################################################################################

public class Exercicio7 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Digite uma frase: ");
        String frase = sc.nextLine();

        int contEspacos = 0;

        //Percorre toda a string caracter por caracter
        for (int i = 0; i < frase.length(); i++) {
            if (frase.charAt(i) == ' ') {
                contEspacos++;
            }
        }

        System.out.println("Quantidade de espaços: " + contEspacos);

        sc.close();
    }
}
