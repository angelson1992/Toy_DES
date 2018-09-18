import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

class DES {

    private boolean verboseTest = true;
    private BitSet key;
    private int keySize;
    private BitSet subKey1;
    private BitSet subKey2;
    private int inputSize;
    private Integer[] table = {2, 6, 3, 1, 4, 8, 5, 7};
    private List<Integer> initialPermutation = Arrays.asList(table);
    private List<Integer> inversePermutation;
    private int[][] sBox1 = {{1, 0, 3, 2},
                             {3, 2, 1, 0},
                             {0, 2, 1, 3},
                             {3, 1, 3, 2}};
    private int[][] sBox2 = {{0, 1, 2, 3},
                             {2, 0, 1, 3},
                             {3, 0, 1, 0},
                             {2, 1, 0, 3}};

    DES(BitSet keyParam, int keySizeParam){
        key = keyParam;
        keySize = keySizeParam;
        keySchedule();
    }

    public BitSet permutation(BitSet inputBits, List<Integer> permutationTable){

        BitSet answer = new BitSet(permutationTable.size());

        for(int i = 0; i < permutationTable.size(); i++){

            //Set the permutationTable[ith] number of the answer bitset to the inputBit[ith] value
            //Because permutationTables count from 1 instead of from 0, permutationTable has a -1 to work with
            answer.set(permutationTable.get(i)-1, inputBits.get(i));

        }

        if(verboseTest){
            System.out.println("Permutation has begun.\n" +
                               "We are permuting the input " + inputBits + "\n" +
                               "by using the table " + permutationTable + "\n" +
                               "and the result is " + answer + "\n" +
                               "Permutation has ended.\n\n");
        }

        return answer;

    }

    //This is just a utility function to get integers from binary
    public int BitsToInt(BitSet input, int size){

        String binaryString = "";

        for(int i = 0; i < size; i++){

            if(input.get(i)){
                binaryString = binaryString + "1";
            }else{
                binaryString = binaryString + "0";
            }

        }

        if(verboseTest){
            System.out.println("A BitSet is being converted to a decimal integer.\n" +
                               "We are converting " + input + "\n" +
                               "The BitSet was converted to the string " + binaryString + "\n" +
                               "And the integer " + Integer.parseInt(binaryString, 2) + "\n\n");
        }

        return Integer.parseInt(binaryString, 2);

    }

    //This method simulates the leftward bitshift on BitSets
    public BitSet leftShiftRotation(BitSet inputBits, int size){

        BitSet answer = new BitSet(inputBits.size());

        //Set the first bit of the input into the last bit of the answer
        answer.set(size-1, inputBits.get(0));

        for(int i = 1; i < size; i++){

            //Set all bits of the input into the bit directly to it's left in the answer
            //except the first bit
            answer.set(i-1, inputBits.get(i));

        }

        if(verboseTest){
            System.out.println("LeftShiftRotation has been called.\n" +
                               "The input being shifted is " + inputBits + "\n" +
                               "The value gotten is " + answer + "\n\n");
        }

        return answer;

    }

    //A simple function to append two BitSets together.
    public BitSet append(BitSet first, int firstSize, BitSet second, int secondSize){

        //This effectively copies the first part
        BitSet answer = (BitSet) first.clone();

        //This adds the second part to the copy of the first part
        for(int i = firstSize; i < firstSize+secondSize; i++){

            answer.set(i, second.get(i-firstSize));

        }

        if(verboseTest){

            System.out.println("The append function has been called for BitSets.\n" +
                               "The values being appended are " + first + " and " + second + "\n" +
                               "The result is " + answer + "\n\n");

        }

        return answer;

    }

    public List<BitSet> keySchedule(){

        if(verboseTest){System.out.println("The key scheduler was been called.\n");}

        List<BitSet> answer = new ArrayList<>();

        //P10 Permutation table representation
        Integer[] TenBitPermutation = {3, 5, 2, 7, 4, 10, 1, 9, 8, 6};
        List<Integer> TenBitPerm = Arrays.asList(TenBitPermutation);

        //The permutation step that gets us the P10 block in key schedule on the slides
        BitSet P10Block = permutation(key, TenBitPerm);

        //Splitting P10 block in half to work on
        BitSet leftHalf = P10Block.get(0, 5);
        BitSet rightHalf = P10Block.get(5, 10);

        //Doing the leftshift on the halfs of the P10 block
        leftHalf = leftShiftRotation(leftHalf, 5);
        rightHalf = leftShiftRotation(rightHalf, 5);

        //Putting the modified halfs back together
        BitSet toBePermuted = append(leftHalf, 5, rightHalf, 5);

        //P8 Permutation table representation
        Integer[] TenToEightBitPermutation = {6, 3, 7, 4, 8, 5, 10, 9};
        List<Integer> TenEightPerm = Arrays.asList(TenToEightBitPermutation);

        //Use of reduction permutation to get the first subkey
        subKey1 = permutation(toBePermuted, TenEightPerm);
        answer.add(subKey1);

        //Doing the leftshifts on the halfs again for the second subkey
        leftHalf = leftShiftRotation(leftHalf, 5);
        rightHalf = leftShiftRotation(rightHalf, 5);

        //Putting the remodified halfs back together
        BitSet toBePermuted2ElectricBoogaloo = append(leftHalf, 5, rightHalf, 5);

        //Use of reduction permutation to get the second subkey
        subKey2 = permutation(toBePermuted2ElectricBoogaloo, TenEightPerm);
        answer.add(subKey2);

        if(verboseTest){
            System.out.println("We worked on the key " + key + "\n" +
                               "And we got the subkeys " + answer + "\n" +
                               "The key scheduler has complete.\n\n");
        }

        return answer;
    }

    public BitSet fFunctionBox(BitSet input, BitSet key){

        //Expansion permutation representation
        Integer[] FourToEightBitPermutation = {4, 1, 2, 3, 2, 3, 4, 1};
        List<Integer> FourEightPerm = Arrays.asList(FourToEightBitPermutation);

        //Use of expansion permutation
        BitSet P4to8Block = permutation(input, FourEightPerm);

        //TODO Finish this function
        return new BitSet();

    }


}

public class Main {

    public static void main(String[] args) {

        BitSet key = new BitSet(10);
        key.set(4);
        key.set(8);
        key.set(2);

        BitSet test = new BitSet(8);
        test.set(0);
        test.set(3);
        test.set(6);

        Integer[] table = {2, 6, 3, 1, 4, 8, 5, 7};
        List<Integer> initialPermutation = Arrays.asList(table);

        Integer[] inverseTable = {4, 1, 3, 5, 7, 2, 8, 6};
        List<Integer> inversePermutation = Arrays.asList(inverseTable);

        DES blockCypher = new DES(key, 10);
        BitSet permuted = blockCypher.permutation(test, initialPermutation);
        BitSet inversePermuted = blockCypher.permutation(permuted, inversePermutation);

        if(false) {
            System.out.println("The plaintext is " + test);
            System.out.println("The initial permutation is " + permuted);
            System.out.println("The permuted text inversed is " + inversePermuted);
            System.out.println("The plaintext rotated left is " + blockCypher.leftShiftRotation(test, 8));
            System.out.println("The permuted text rotated left is " + blockCypher.leftShiftRotation(permuted, 8));
            System.out.println(Integer.parseInt("11010", 2));
            System.out.println("Test is converted as " + blockCypher.BitsToInt(test, 8) + ".\n"
              + "permuted is converted as " + blockCypher.BitsToInt(permuted, 8) + ".\n"
              + "Rotated test is converted as " + blockCypher.BitsToInt(blockCypher.leftShiftRotation(test, 8), 8) + ".\n"
              + "Rotated permuted is converted as " + blockCypher.BitsToInt(blockCypher.leftShiftRotation(permuted, 8), 8) + ".");
            System.out.println("Test and permuted appended is " + blockCypher.append(test, 8, permuted, 8));
            System.out.println("permuted and inversePermuted appended is " + blockCypher.append(permuted, 8, inversePermuted, 8));
        }

    }

}
