import java.util.*;

public class MorseService {

    private static final char DOT = '.';
    private static final char DASH = '-';
    private static final char ONE = '1';
    private static final char ZERO = '0';
    private static final char ONE_SPACE = ' ';
    private static final String ONE_SPACE_STR = " ";
    private static final String TWO_SPACES_STR = "  ";

    private static final Map<Character, String> alphaMap = new HashMap<>();
    private static final Map<String, Character> morseMap = new HashMap<>();

    static {
        alphaMap.put('A', ".-");
        alphaMap.put('B', "-...");
        alphaMap.put('C', "-.-.");
        alphaMap.put('D', "-..");
        alphaMap.put('E', ".");
        alphaMap.put('F', "..-.");
        alphaMap.put('G', "--.");
        alphaMap.put('H', "....");
        alphaMap.put('I', "..");
        alphaMap.put('J', ".---");
        alphaMap.put('K', "-.-");
        alphaMap.put('L', ".-..");
        alphaMap.put('M', "--");
        alphaMap.put('N', "-.");
        alphaMap.put('O', "---");
        alphaMap.put('P', ".--.");
        alphaMap.put('Q', "--.-");
        alphaMap.put('R', ".-.");
        alphaMap.put('S', "...");
        alphaMap.put('T', "-");
        alphaMap.put('U', "..-");
        alphaMap.put('V', "...-");
        alphaMap.put('W', ".--");
        alphaMap.put('X', "-..-");
        alphaMap.put('Y', "-.--");
        alphaMap.put('Z', "--..");
        alphaMap.put('0', "-----");
        alphaMap.put('1', ".----");
        alphaMap.put('2', "..---");
        alphaMap.put('3', "...--");
        alphaMap.put('4', "....-");
        alphaMap.put('5', ".....");
        alphaMap.put('6', "-....");
        alphaMap.put('7', "--...");
        alphaMap.put('8', "---..");
        alphaMap.put('9', "----.");

        morseMap.put(".-", 'A');
        morseMap.put("-...", 'B');
        morseMap.put("-.-.", 'C');
        morseMap.put("-..", 'D');
        morseMap.put(".", 'E');
        morseMap.put("..-.", 'F');
        morseMap.put("--.", 'G');
        morseMap.put("....", 'H');
        morseMap.put("..", 'I');
        morseMap.put(".---", 'J');
        morseMap.put("-.-", 'K');
        morseMap.put(".-..", 'L');
        morseMap.put("--", 'M');
        morseMap.put("-.", 'N');
        morseMap.put("---", 'O');
        morseMap.put(".--.", 'P');
        morseMap.put("--.-", 'Q');
        morseMap.put(".-.", 'R');
        morseMap.put("...", 'S');
        morseMap.put("-", 'T');
        morseMap.put("..-", 'U');
        morseMap.put("...-", 'V');
        morseMap.put(".--", 'W');
        morseMap.put("-..-", 'X');
        morseMap.put("-.--", 'Y');
        morseMap.put("--..", 'Z');
        morseMap.put("-----", '0');
        morseMap.put(".----", '1');
        morseMap.put("..---", '2');
        morseMap.put("...--", '3');
        morseMap.put("....-", '4');
        morseMap.put(".....", '5');
        morseMap.put("-....", '6');
        morseMap.put("--...", '7');
        morseMap.put("---..", '8');
        morseMap.put("----.", '9');
    }

    /**
     * @param morse
     * @param minOnes
     * @param maxOnes
     * @param minZeros
     * @param mediumZeros
     * @param maxZeros
     * @return
     * @throws Exception
     */
    public static String encodeMorse2Bits(String morse, int minOnes, int maxOnes, int minZeros, int mediumZeros, int maxZeros)
            throws Exception {
        if (minOnes >= maxOnes) {
            throw new Exception("maxOnes must be greater than minOnes");
        }
        //validating minZeros, mediumZeros, maxZeros
        if (minZeros > mediumZeros || minZeros >= maxZeros || mediumZeros >= maxZeros) {
            throw new Exception("minZeros must be less or equal than mediumZeros, mediumZeros must be less than maxZeros");
        }
        StringBuilder response = new StringBuilder();
        int length = morse.length();
        for (int i = 0; i < length; i++) {
            if (morse.charAt(i) == DOT) {
                //placing the ones
                for (int j = 0; j < minOnes; j++) {
                    response.append(ONE);
                }
                //placing space to separate characters
                for (int j = 0; j < minZeros; j++) {
                    response.append(ZERO);
                }
            } else if (morse.charAt(i) == DASH) {
                //placing the ones
                for (int j = 0; j < maxOnes; j++) {
                    response.append(ONE);
                }
                //placing space to separate characters
                for (int j = 0; j < minZeros; j++) {
                    response.append(ZERO);
                }
            } else if (morse.charAt(i) == ONE_SPACE) {
                if (i + 1 < length) {
                    //find the number of spaces to know if they are 1 or 2 spaces
                    if (morse.charAt(i + 1) == ONE_SPACE) {
                        //es separacion de palabtas
                        i++;
                        for (int j = 0; j < maxZeros; j++) {
                            response.append(ZERO);
                        }
                    } else {
                        //separacion de letras
                        for (int j = 0; j < mediumZeros; j++) {
                            response.append(ZERO);
                        }
                    }
                }
            } else {
                throw new Exception("Character not in morse " + morse.charAt(i));
            }
        }
        return response.toString();
    }

    /**
     * @param bits
     * @return
     * @throws Exception
     */
    public static String decodeBits2Morse(String bits) throws Exception {
        class CharacterCount {
            char character;
            int count;

            public CharacterCount(char character, int count) {
                this.character = character;
                this.count = count;
            }
        }

        //start and end of message
        int start = bits.indexOf(ONE), end = bits.lastIndexOf(ONE) + 1;
        if (start == -1) throw new Exception("Bit 1 not found in string");

        //search ocurrences of zeros and ones
        int[] getCountResult;
        Set<Integer> setZeros = new TreeSet<>();
        List<CharacterCount> characterCounts = new ArrayList<>();
        int minOne = Integer.MAX_VALUE, maxOne = Integer.MIN_VALUE,
                minZero = Integer.MAX_VALUE, medZero = Integer.MAX_VALUE, maxZero = Integer.MIN_VALUE,
                changesOne = 0, changesZeros = 0,
                count;

        for (int i = start; i < end; ) {
            getCountResult = getCountInARow(bits, bits.charAt(i), i, end);
            count = getCountResult[0];
            //search ocurrence of 1
            if (bits.charAt(i) == ONE) {
                if (count <= minOne) {
                    //update minOne if is greater than count
                    if (count < minOne) {
                        if (minOne != Integer.MAX_VALUE) maxOne = minOne;
                        minOne = count;
                        changesOne++;
                    }
                } else if (count >= maxOne) {
                    //only update maxOne if is less than count
                    if (count > maxOne) {
                        maxOne = count;
                        changesOne++;
                    }
                } else {
                    throw new Exception("More than two ones in a row in the string (e.g 10110111)");
                }
                i = getCountResult[1];//i
                characterCounts.add(new CharacterCount(ONE, count));
            }
            //search ocurrence of 0
            else if (bits.charAt(i) == ZERO) {
                if (count <= minZero) {
                    //update minZero if is greater than count
                    if (count < minZero) {
                        //if (medZero != Integer.MAX_VALUE) maxZero = medZero;
                        if (minZero != Integer.MAX_VALUE) medZero = minZero;
                        minZero = count;
                        changesZeros++;
                    }
                } else if (count <= medZero) {
                    //update medZero if is greater than count
                    if (count < medZero) {
                        //if (medZero != Integer.MAX_VALUE) maxZero = medZero;
                        medZero = count;
                        changesZeros++;
                    }
                } else if (count >= maxZero) {
                    //update maxZero if is less than count
                    if (count > maxZero) {
                        maxZero = count;
                        changesZeros++;
                    }
                } else {
                    throw new Exception("More than three zeros in a row in the string (e.g 10110010001000010)");
                }
                i = getCountResult[1];//i
                characterCounts.add(new CharacterCount(ZERO, count));

            } else {
                throw new Exception("Character not a bit");
            }
        }

        //verify ones
        if (changesOne > 2) throw new Exception("More than two ones in a row in the string (e.g 10110111)");
        //for zeros maximum 3
        if (changesZeros > 3)
            throw new Exception("More than three zeros in a row in the string (e.g 10110010001000010)");

        Map<Integer, Character> mapOnes = new HashMap();
        mapOnes.put(minOne, DOT);
        mapOnes.put(maxOne, DASH);

        Map<Integer, String> mapZeros = new HashMap();
        mapZeros.put(minZero, "");
        mapZeros.put(medZero, ONE_SPACE_STR);
        mapZeros.put(maxZero, TWO_SPACES_STR);

        //loop for array to create the response
        StringBuilder response = new StringBuilder();
        for (CharacterCount characterCount : characterCounts) {
            if (characterCount.character == ONE) response.append(mapOnes.get(characterCount.count));
            else response.append(mapZeros.get(characterCount.count));
        }
        return response.toString();
    }

    /**
     * @param bits
     * @return
     * @throws Exception
     * @deprecated More complexity than the other one
     */
    @Deprecated
    public static String decodeBits2MorseMoreComplexity(String bits) throws Exception {
        class CharacterCount {
            char character;
            int count;

            public CharacterCount(char character, int count) {
                this.character = character;
                this.count = count;
            }
        }

        int start = bits.indexOf(ONE), end = bits.lastIndexOf(ONE) + 1;
        if (start == -1) throw new Exception("Bit 1 not found in string");

        //search ocurrences of zeros and ones
        int[] getCountResult;
        Set<Integer> setOnes = new TreeSet<>();
        Set<Integer> setZeros = new TreeSet<>();
        List<CharacterCount> characterCounts = new ArrayList<>();

        for (int i = start; i < end; ) {
            //search ocurrence of 1
            if (bits.charAt(i) == ONE) {
                getCountResult = getCountInARow(bits, bits.charAt(i), i, end);
                setOnes.add(getCountResult[0]);//count
                i = getCountResult[1];//i
                characterCounts.add(new CharacterCount(ONE, getCountResult[0]));
            }
            //search ocurrence of 0
            else if (bits.charAt(i) == ZERO) {
                getCountResult = getCountInARow(bits, bits.charAt(i), i, end);
                setZeros.add(getCountResult[0]);//count
                i = getCountResult[1];//i
                characterCounts.add(new CharacterCount(ZERO, getCountResult[0]));

            } else {
                throw new Exception("Character not a bit");
            }
        }

        //verify sets
        if (setOnes.size() > 2) throw new Exception("More than two ones in a row in the string (e.g 10110111)");
        //para los zeros el maximo es 3
        if (setZeros.size() > 3)
            throw new Exception("More than three zeros in a row in the string (e.g 10110010001000010)");

        Map<Integer, Character> mapOnes = new HashMap();
        Map<Integer, String> mapZeros = new HashMap();
        Iterator<Integer> it = setOnes.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (i == 0) mapOnes.put(it.next(), DOT);
            else mapOnes.put(it.next(), DASH);
            i++;
        }
        i = 0;
        it = setZeros.iterator();
        while (it.hasNext()) {
            if (i == 0) mapZeros.put(it.next(), "");
            else if (i == 1) mapZeros.put(it.next(), ONE_SPACE_STR);
            else mapZeros.put(it.next(), TWO_SPACES_STR);
            i++;
        }
        //loop for array to create the response
        StringBuilder response = new StringBuilder();
        for (CharacterCount characterCount : characterCounts) {
            if (characterCount.character == ONE) response.append(mapOnes.get(characterCount.count));
            else response.append(mapZeros.get(characterCount.count));
        }
        return response.toString();
    }

    private static int[] getCountInARow(String bits, char character, int start, int end) {
        int i = start, count = 0;
        while (i < end && bits.charAt(i) == character) {
            count++;
            i++;
        }
        return new int[]{count, i};
    }

    /**
     * @param morse
     * @return
     */
    public static String translate2Human(String morse) throws Exception {
        StringBuilder response = new StringBuilder();
        StringBuilder letter;
        int length = morse.length(), j, count;
        Character human;
        for (int i = 0; i < length; ) {
            if (morse.charAt(i) == ONE_SPACE) {
                j = i;
                count = 0;
                while (j < length && morse.charAt(j) == ONE_SPACE) {
                    j++;
                    count++;
                }
                if (count > 1) response.append(ONE_SPACE);
            } else if (morse.charAt(i) == DOT || morse.charAt(i) == DASH) {
                letter = new StringBuilder();
                j = i;
                while (j < length && morse.charAt(j) != ONE_SPACE) {
                    letter.append(morse.charAt(j));
                    j++;
                }
                human = morseMap.get(letter.toString());
                if (human != null) response.append(morseMap.get(letter.toString()));
                else throw new Exception("Morse letter not valid");
            } else {
                throw new Exception("Character not in morse");
            }
            i = j;
        }
        return response.toString();
    }

    /**
     * @param phrase
     * @return
     */
    public static String encode2Morse(String phrase) throws Exception {
        phrase = phrase.toUpperCase();
        StringBuilder response = new StringBuilder();
        int length = phrase.length(), j;
        String morse;
        for (int i = 0; i < length; ) {
            if (phrase.charAt(i) == ONE_SPACE) {
                j = i;
                while (j < length && phrase.charAt(j) == ONE_SPACE) j++;
                i = j;
                response.append(" ");
            } else {
                morse = alphaMap.get(phrase.charAt(i));
                if (morse != null) response.append(alphaMap.get(phrase.charAt(i)));
                else throw new Exception("AlphaNumeric not valid");
                i++;
            }
            if (i < length) {
                response.append(ONE_SPACE);
            }
        }

        return response.toString();
    }


}
