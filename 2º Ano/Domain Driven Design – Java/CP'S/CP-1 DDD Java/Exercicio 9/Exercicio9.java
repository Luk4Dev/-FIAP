import java.util.Scanner;

//###############################################################################################
// Exercicio 9
// Objetivo: Validar CPF (sem usar arrays).
// Regras simplificadas: verificar tamanho = 11 e calcular dígitos verificadores.
// Esse eu precisei de algumas ajudas externas para entender o cálculo dos dígitos.
//###############################################################################################

public class Exercicio9 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Digite um CPF apenas com números (11 dígitos): ");
        String cpf = sc.nextLine();

        // Verifica se tem 11 dígitos
        if (cpf.length() != 11) {
            System.out.println("CPF inválido! Deve ter 11 dígitos.");
            return;
        }

        // Cálculo do 1º dígito verificador
        int soma = 0;
        int peso = 10;
        for (int i = 0; i < 9; i++) {
            int num = cpf.charAt(i) - '0';
            soma += num * peso;
            peso--;
        }
        int resto = soma % 11;
        int digito1 = (resto < 2) ? 0 : 11 - resto;

        // Cálculo do 2º dígito verificador
        soma = 0;
        peso = 11;
        for (int i = 0; i < 10; i++) {
            int num = cpf.charAt(i) - '0';
            soma += num * peso;
            peso--;
        }
        resto = soma % 11;
        int digito2 = (resto < 2) ? 0 : 11 - resto;

        // Confere os dígitos calculados com os informados
        if (digito1 == (cpf.charAt(9) - '0') && digito2 == (cpf.charAt(10) - '0')) {
            System.out.println("CPF válido!");
        } else {
            System.out.println("CPF inválido!");
        }

        sc.close();
    }
}
