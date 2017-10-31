import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static final int encodeMorse2Bits = 1;

    public static void main(String[] args) {
        try {
            //encode morse to bits
            String morse = "- . ... -  - . -.-. -. .. -.-. ---  -- . .-.. ..";
            String bits = MorseService.encodeMorse2Bits(morse, 1, 2, 1, 1, 2);
            System.out.println(bits);



            //test decode morse2bits
            String originalMorse = "- . ... -  - . -.-. -. .. -.-. ---  -- . .-.. ..";
            for (int i = 0; i < 1000; i++) {
                int minOnes = ThreadLocalRandom.current().nextInt(1, 10);
                int maxOnes = ThreadLocalRandom.current().nextInt(minOnes + 1, 20);
                int minZeros = ThreadLocalRandom.current().nextInt(1, 10);
                int mediumZeros = ThreadLocalRandom.current().nextInt(minZeros, 20);
                int maxZeros = ThreadLocalRandom.current().nextInt(mediumZeros + 1, 30);
                morse = "- . ... -  - . -.-. -. .. -.-. ---  -- . .-.. ..";
                String inBits = MorseService.encodeMorse2Bits(morse, minOnes, maxOnes, minZeros, mediumZeros, maxZeros);
                String decode = MorseService.decodeBits2Morse(inBits);
                if (!decode.equals(originalMorse)) {
                    System.out.println("Error");
                    break;
                }
            }


            //test transalte to human
            //morse = ".... q-";
            //System.out.println(MorseService.translate2Human(morse));
            //morse = "......";
            //System.out.println(MorseService.translate2Human(morse));
            morse = "- . ... -  - . -.-. -. .. -.-. ---  -- . .-.. ..";
            System.out.println(MorseService.translate2Human(morse));

            //test translate to morse
            //String human = "TEST TECNICO, MELI";
            String human = "TEST TECNICO MELI";
            System.out.println(MorseService.encode2Morse(human));


            System.out.println();
            System.out.println("----TEST--");
            String humanText = "Lorem ipsum dolor sit amet consectetur adipiscing elit";
            //String humanText = "hola meli";
            String inMorse1 = MorseService.encode2Morse(humanText);
            System.out.println(inMorse1);
            String inBits1 = MorseService.encodeMorse2Bits(inMorse1, 1, 2, 1, 1, 2);
            String inBits2 = MorseService.encodeMorse2Bits(inMorse1, 5, 8, 1, 1, 5);
            String inMorse2 = MorseService.decodeBits2Morse(inBits1);
            String inMorse3 = MorseService.decodeBits2Morse(inBits2);
            String toHuman1 = MorseService.translate2Human(inMorse1);
            String toHuman2 = MorseService.translate2Human(inMorse2);
            String toHuman3 = MorseService.translate2Human(inMorse3);
            System.out.println(humanText);
            System.out.println(toHuman1);
            System.out.println(toHuman2);
            System.out.println(toHuman3);

            //System.out.println(MorseService.encodeMorse2Bits("- . ... -  - . -.-. -. .. -.-. ---  -- . .-.. ..",1,2,3,3,4));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }


}
