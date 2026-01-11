import java.util.Scanner;

public class Vigenere {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Saisissez la clé (key) : ");
        String key = scanner.nextLine().toUpperCase();

        System.out.print("Saisissez votre texte à chiffrer : ");
        String message = scanner.nextLine().toUpperCase();

        String encrypted = encrypt(message, key);
        String decrypted = decrypt(encrypted, key);

        System.out.println("\nMessage: " + message);
        System.out.println("Encrypted message: " + encrypted);
        System.out.println("Decrypted message: " + decrypted);
    }

    public static String encrypt(String line, final String key) {
        StringBuilder result = new StringBuilder();
        int Index = 0;
        int keyLen = key.length();

        for (char c : line.toCharArray()) {

            if (c >= 'A' && c <= 'Z') {
                int shift = key.charAt(Index) - 'A';
                char encryptedChar = (char) (((c - 'A' + shift) % 26) + 'A');
                result.append(encryptedChar);

                Index = (Index + 1) % keyLen;
            }
        }
        return result.toString();
    }

    public static String decrypt(String text, final String key) {
        StringBuilder result = new StringBuilder();
        int Index = 0;
        int keyLen = key.length();

        for (char c : text.toCharArray()) {

            if (c >= 'A' && c <= 'Z') {
                int shift = key.charAt(Index) - 'A';
                char decryptedChar = (char) (((c - 'A' - shift + 26) % 26) + 'A');
                result.append(decryptedChar);

                Index = (Index + 1) % keyLen;
            }
        }

        return result.toString();
    }
}
