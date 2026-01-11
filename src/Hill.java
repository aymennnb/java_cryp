import java.util.Scanner;

public class Hill {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("1.Chiffrer\n2.Déchiffrer\n");
        System.out.print("Choix : ");
        int choix = scanner.nextInt();
        scanner.nextLine();

        if (choix == 1) {
            System.out.print("Entrer le texte : ");
            String texte = scanner.nextLine().toUpperCase();
            encrypt(texte, scanner);

        } else if (choix == 2) {
            System.out.print("Entrer le texte : ");
            String texte = scanner.nextLine().toUpperCase();
            decrypt(texte, scanner);

        } else {
            System.out.println("Choix introuvable !");
            System.exit(0);
        }
    }

    private static int mod26(int x) {
        x = x % 26;
        if (x < 0) x += 26;
        return x;
    }

    private static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return Math.abs(a);
    }


    private static int[][] getKeyMatrix(Scanner keyboard) {
        System.out.print("a: ");
        int a = mod26(keyboard.nextInt());
        System.out.print("b: ");
        int b = mod26(keyboard.nextInt());
        System.out.print("c: ");
        int c = mod26(keyboard.nextInt());
        System.out.print("d: ");
        int d = mod26(keyboard.nextInt());

        return new int[][] { {a, b}, {c, d} };
    }


    private static void isValidMatrix(int[][] keyMatrix) {
        int a = keyMatrix[0][0];
        int b = keyMatrix[0][1];
        int c = keyMatrix[1][0];
        int d = keyMatrix[1][1];

        int det = mod26(a * d - b * c);

        if (gcd(det, 26) != 1) {
            throw new IllegalArgumentException("La matrice n’est pas inversible modulo 26 !");
        }
    }


    private static int[][] reverseMatrix(int[][] keyMatrix) {
        int a = keyMatrix[0][0];
        int b = keyMatrix[0][1];
        int c = keyMatrix[1][0];
        int d = keyMatrix[1][1];

        int det = mod26(a * d - b * c);

        int invDet = -1;
        for (int i = 0; i < 26; i++) {
            if (mod26(det * i) == 1) {
                invDet = i;
                break;
            }
        }

        if (invDet == -1) {
            throw new IllegalArgumentException("La matrice n’a pas d’inverse mod 26 !");
        }

        int[][] inv = {
                { d, -b },
                { -c, a }
        };

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                inv[i][j] = mod26(inv[i][j] * invDet);
            }
        }

        return inv;
    }


    private static void encrypt(String phrase, Scanner keyboard) {

        phrase = phrase.replaceAll("[^A-Z]", "");

        if (phrase.length() % 2 != 0)
            phrase += "X";

        int[][] key = getKeyMatrix(keyboard);
        isValidMatrix(key);

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < phrase.length(); i += 2) {
            int x1 = phrase.charAt(i) - 'A';
            int x2 = phrase.charAt(i+1) - 'A';

            int y1 = mod26(key[0][0] * x1 + key[0][1] * x2);
            int y2 = mod26(key[1][0] * x1 + key[1][1] * x2);

            result.append((char)(y1 + 'A'));
            result.append((char)(y2 + 'A'));
        }

        System.out.println("Texte chiffré : " + result);
    }


    private static void decrypt(String phrase, Scanner keyboard) {
        phrase = phrase.replaceAll("[^A-Z]", "");

        int[][] key = getKeyMatrix(keyboard);
        isValidMatrix(key);

        int[][] inv = reverseMatrix(key);

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < phrase.length(); i += 2) {
            int x1 = phrase.charAt(i) - 'A';
            int x2 = phrase.charAt(i+1) - 'A';

            int y1 = mod26(inv[0][0] * x1 + inv[0][1] * x2);
            int y2 = mod26(inv[1][0] * x1 + inv[1][1] * x2);

            result.append((char)(y1 + 'A'));
            result.append((char)(y2 + 'A'));
        }

        System.out.println("Texte déchiffré : " + result);
    }
}
