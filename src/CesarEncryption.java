import java.util.Scanner;

public class CesarEncryption {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String message = inputFromUser(scanner);
        int shift = getShiftFromUser(scanner);
        String encrypted = encrypt(message, shift);
        String decrypted = decrypt(encrypted, shift);

        System.out.println("Message initial : " + message);
        System.out.println("Message chiffré : " + encrypted);
        System.out.println("Message déchiffré : " + decrypted);
    }

    public static String inputFromUser(Scanner scanner) {
        System.out.print("Entrez un message pour le chiffré (ligne de texte) : ");
        return scanner.nextLine().toUpperCase();
    }

    public static int getShiftFromUser(Scanner scanner) {
        int shift;
        do {
            System.out.print("Entrez une longueur (clé) (entier > 0) : ");
            shift = scanner.nextInt();
        } while (shift <= 0);
        return shift;
    }

    public static String encrypt(String message, int shift) {

        char[] normal = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        char[] shifted = new char[26];

        for (int i = 0; i < 26; i++) {
            shifted[i] = normal[(i + shift) % 26];
        }

        StringBuilder result = new StringBuilder();

        for (char c : message.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                int index = c - 'A';
                result.append(shifted[index]);
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }

    public static String decrypt(String message, int shift) {
        return encrypt(message, 26 - (shift % 26));
    }
}