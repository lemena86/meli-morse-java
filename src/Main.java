import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        String humanText = "test tecnico meli";
        String inMorse1 = encode2Morse(humanText);
        String inBits1 = encodeMorse2Bits(inMorse1,1,2,1,1,2);
        String inBits2 = encodeMorse2Bits(inMorse1,5,8,1,1,5);
        String inMorse2 = decodeBits2Morse(inBits1);
        String inMorse3 = decodeBits2Morse(inBits2);
        String toHuman1 = translate2Human(inMorse1);
        String toHuman2 = translate2Human(inMorse2);
        String toHuman3 = translate2Human(inMorse3);
        System.out.println(humanText);
        System.out.println(toHuman1);
        System.out.println(toHuman2);
        System.out.println(toHuman3);

        //searching the first and last '1'
        //String bitsHolaMeli = "110110110110011101110111001101110110110011011100011101110011001101110110110011011";
        //Test tecnico mercado libre
        //String test1 = "11110011110011110011110001111100111110011111000111100111110011110011110001111001111100000111110011111000111100011110011111001111001111000111100111100";
        //.... --- .-.. .-  -- . .-.. ..
        //System.out.println("---decodeBits2Morse---");
        //System.out.println(decodeBits2Morse(bitsHolaMeli));
        //System.out.println();
        //System.out.println("---translate2Human---");
        //System.out.println(translate2Human(decodeBits2Morse(test1)));
        //System.out.println();
        //String morse = ". ... - ---     . ...     ..- -. .-     .--. .-. ..- . -... .-     -.. .     --.- ..- .     ..-. ..- -. -.-. .. --- -. .-     -- ..     .--. .- .-. ... . .-.";
        //esto es una prueba de que funciona mi parser
        //System.out.println(translate2Human(morse));
        //String phrase = "segunda prueba de que el parser esta ok";
        //... . --. ..- -. -.. .-  .--. .-. ..- . -... .-  -.. .  --.- ..- .  . .-..  .--. .- .-. ... . .-.  . ... - .-  --- -.-
        //String morse1 = "... . --. ..- -. -.. .-  .--. .-. ..- . -... .-  -.. .  --.- ..- .  . .-..  .--. .- .-. ... . .-.  . ... - .-  --- -.-";
        //String morse2 = "... . --. ..- -. -.. .-   .--. .-. ..- . -... .-   -.. .   --.- ..- .   . .-..   .--. .- .-. ... . .-.   . ... - .-   --- -.-";
        //System.out.println(encode2Morse(phrase));
        //System.out.println(translate2Human(encode2Morse(phrase)));

        //String morseHolaMeli = ".... --- .-.. .-  -- . .-.. ..";
        //System.out.println(translate2Human(morseHolaMeli));
        //System.out.println("---encode2Morse---");
        //String holaMeli = "HOLA MELI";
        //System.out.println(encode2Morse(translate2Human(decodeBits2Morse(bitsHolaMeli))));


        //String h = "Test tecnico mercado libre";
        //String m = encode2Morse(h);
        //System.out.println(m);
        //System.out.println(translate2Human(m));
        //String b = "1100100101010011000110010011010110100110100101001101011010001101101100011011001001011010011010110100010110011010100110110110001011010100101001101010100101101001";
        //System.out.println(translate2Human(decodeBits2Morse(b)));

        //String morse = ".... --- .-.. .-  -- . .-.. ..";
        //System.out.println(encodeMorse2Bits(morse, 4, 5, 2, 1, 3));
    }

    public static String decodeBits2Morse(String bits) {
        int start = bits.indexOf('1'), end = bits.lastIndexOf('1') + 1;

        //search min and max ocurrence of 0 and 1
        int minZero = Integer.MAX_VALUE, maxZero = 0, minOne = Integer.MAX_VALUE, maxOne = 0, count, j;
        for (int i = start; i < end; ) {
            //search min and max ocurrence of 1
            if (bits.charAt(i) == '1') {
                j = i;
                count = 0;
                while (j < end && bits.charAt(j) == '1') {
                    count++;
                    j++;
                }
                if (count < minOne) minOne = count;
                if (count > maxOne) maxOne = count;
            }
            //search min and max ocurrence of 0
            else {
                j = i;
                count = 0;
                while (j < end && bits.charAt(j) == '0') {
                    count++;
                    j++;
                }
                if (count < minZero) minZero = count;
                if (count > maxZero) maxZero = count;
            }
            i = j;
        }
        //loop bits to find dot, dash, pause or long pause
        StringBuilder response = new StringBuilder();
        for (int i = start; i < end; ) {
            //search one
            if (bits.charAt(i) == '1') {
                j = i;
                count = 0;
                while (j < end && bits.charAt(j) == '1') {
                    count++;
                    j++;
                }
                if (count == minOne) response.append(".");
                else if (count == maxOne) response.append("-");
            }
            //search zero
            else {
                j = i;
                count = 0;
                while (j < end && bits.charAt(j) == '0') {
                    count++;
                    j++;
                }
                if (count == minZero) response.append("");
                else if (count == maxZero) response.append("  ");//separacion letras
                else response.append(" ");//separacion palabras
            }
            i = j;
        }
        return response.toString();
    }


    public static String translate2Human(String morse) {
        StringBuilder response = new StringBuilder();
        StringBuilder letter;
        Map<String, Character> morseMap = loadMorseMap();
        int length = morse.length(), j, count;
        for (int i = 0; i < length; ) {
            if (morse.charAt(i) == ' ') {
                j = i;
                count = 0;
                while (j < length && morse.charAt(j) == ' ') {
                    j++;
                    count++;
                }
                if (count > 1) response.append(" ");
            } else {
                letter = new StringBuilder();
                j = i;
                while (j < length && morse.charAt(j) != ' ') {
                    letter.append(morse.charAt(j));
                    j++;
                }
                response.append(morseMap.get(letter.toString()));
            }
            i = j;
        }
        return response.toString();
    }

    public static String encode2Morse(String phrase) {
        phrase = phrase.toUpperCase();
        StringBuilder response = new StringBuilder();
        Map<Character, String> alphaMap = loadAlphaMap();
        int length = phrase.length(), j;
        for (int i = 0; i < length; ) {
            if (phrase.charAt(i) == ' ') {
                j = i;
                while (j < length && phrase.charAt(j) == ' ') j++;
                i = j;
                response.append(" ");
            } else {
                response.append(alphaMap.get(phrase.charAt(i))).append(" ");
                i++;
            }
        }

        if (response.toString().length() > 0) response.deleteCharAt(response.length() - 1);
        return response.toString();
    }

    public static String encodeMorse2Bits(String morse,
                                          int minOne/*la cantidad de unos para el punto (.)*/,
                                          int maxOne/*la cantidad de unos para el espacio(-)*/,
                                          int minZeros/*la cantidad de zeros para separar caracteres de la misma letra*/,
                                          int mediumZeros/*la cantidad de zeros para separar letras*/,
                                          int maxZeros/*la cantidad de zeros para separar palabras*/) {
        StringBuilder response = new StringBuilder();
        for (int i = 0; i < morse.length(); i++) {
            if (morse.charAt(i) == '.') {
                //colocando los 1
                for (int j = 0; j < minOne; j++) {
                    response.append("1");
                }
                //colocando el espacio para separar caracteres
                for (int j = 0; j < minZeros; j++) {
                    response.append("0");
                }
            }
            if (morse.charAt(i) == '-') {
                //colocando los 1
                for (int j = 0; j < maxOne; j++) {
                    response.append("1");
                }
                //colocando el espacio para separar caracteres
                for (int j = 0; j < minZeros; j++) {
                    response.append("0");
                }
            }
            if (morse.charAt(i) == ' ') {
                //buscar la cantidad de espacios para saber si son 1 o 2 espacios
                if (morse.charAt(i + 1) == ' ') {
                    //es separacion de palabtas
                    i++;
                    for (int j = 0; j < maxZeros; j++) {
                        response.append("0");
                    }
                } else {
                    //separacion de letras
                    for (int j = 0; j < mediumZeros; j++) {
                        response.append("0");
                    }
                }
            }
        }
        return response.toString();
    }


    public static Map<Character, String> loadAlphaMap() {
        Map<Character, String> alpha = new HashMap<Character, String>();
        alpha.put('A', ".-");
        alpha.put('B', "-...");
        alpha.put('C', "-.-.");
        alpha.put('D', "-..");
        alpha.put('E', ".");
        alpha.put('F', "..-.");
        alpha.put('G', "--.");
        alpha.put('H', "....");
        alpha.put('I', "..");
        alpha.put('J', ".---");
        alpha.put('K', "-.-");
        alpha.put('L', ".-..");
        alpha.put('M', "--");
        alpha.put('N', "-.");
        alpha.put('O', "---");
        alpha.put('P', ".--.");
        alpha.put('Q', "--.-");
        alpha.put('R', ".-.");
        alpha.put('S', "...");
        alpha.put('T', "-");
        alpha.put('U', "..-");
        alpha.put('V', "...-");
        alpha.put('W', ".--");
        alpha.put('X', "-..-");
        alpha.put('Y', "-.--");
        alpha.put('Z', "--..");
        alpha.put('0', "-----");
        alpha.put('1', ".----");
        alpha.put('2', "..---");
        alpha.put('3', "...--");
        alpha.put('4', "....-");
        alpha.put('5', ".....");
        alpha.put('6', "-....");
        alpha.put('7', "--...");
        alpha.put('8', "---..");
        alpha.put('9', "----.");

        return alpha;
    }

    public static Map<String, Character> loadMorseMap() {
        Map<String, Character> morse = new HashMap<String, Character>();
        morse.put(".-", 'A');
        morse.put("-...", 'B');
        morse.put("-.-.", 'C');
        morse.put("-..", 'D');
        morse.put(".", 'E');
        morse.put("..-.", 'F');
        morse.put("--.", 'G');
        morse.put("....", 'H');
        morse.put("..", 'I');
        morse.put(".---", 'J');
        morse.put("-.-", 'K');
        morse.put(".-..", 'L');
        morse.put("--", 'M');
        morse.put("-.", 'N');
        morse.put("---", 'O');
        morse.put(".--.", 'P');
        morse.put("--.-", 'Q');
        morse.put(".-.", 'R');
        morse.put("...", 'S');
        morse.put("-", 'T');
        morse.put("..-", 'U');
        morse.put("...-", 'V');
        morse.put(".--", 'W');
        morse.put("-..-", 'X');
        morse.put("-.--", 'Y');
        morse.put("--..", 'Z');
        morse.put("-----", '0');
        morse.put(".----", '1');
        morse.put("..---", '2');
        morse.put("...--", '3');
        morse.put("....-", '4');
        morse.put(".....", '5');
        morse.put("-....", '6');
        morse.put("--...", '7');
        morse.put("---..", '8');
        morse.put("----.", '9');

        return morse;
    }
}
