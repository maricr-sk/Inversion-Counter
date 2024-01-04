import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Class with two different methods to count inversions in an array of integers.
 * @author Marissa Crevecoeur, mac2538
 * @version 1.0.0 November 17, 2022
 */
public class InversionCounter {

    private static long mergesortHelper(int [] array, int [] scratch, int low, int high, long num){
        if(low < high){
            int mid = low + (high-low)/2;
            num = mergesortHelper(array, scratch, low, mid, num);
            num = mergesortHelper(array, scratch, mid+1, high, num);
            int i = low, j = mid +1;
            for(int k = low; k <= high; k++){
                if(i <= mid && (j > high || array[i] <= array[j])){
                    scratch[k] = array[i++];
                }
                else{
                    if(k != j){
                            num++;
                    }
                    scratch[k] = array[j++];
                }
            }
            for(int k = low; k <= high; k++){
                array[k] = scratch[k];
            }
        }
        return num;
    }

    public static long mergesort(int [] array){
        int [] scratch = new int[array.length];
        long l = mergesortHelper(array, scratch, 0, array.length -1, 0);
        return l;
    }

    /**
     * Returns the number of inversions in an array of integers.
     * This method uses nested loops to run in Theta(n^2) time.
     * @param array the array to process
     * @return the number of inversions in an array of integers
     */
    public static long countInversionsSlow(int[] array) {
        // TODO
        long count = 0;
        for(int i = 0; i < array.length-1; i++){
            for(int k = 0; k < array.length-1-i; k++){
                if (array[k] > array[k+1]) {
                    count++;
                    int temp = array[k];
                    array[k] = array[k+1];
                    array[k+1] = temp;
                }
            }
        }
        return count;
    }

    /**
     * Returns the number of inversions in an array of integers.
     * This method uses mergesort to run in Theta(n lg n) time.
     * @param array the array to process
     * @return the number of inversions in an array of integers
     */
    public static long countInversionsFast(int[] array) {
        // Make a copy of the array so you don't actually sort the original
        // array.
        int[] arrayCopy = new int[array.length],
              scratch =  new int[array.length];
        System.arraycopy(array, 0, arrayCopy, 0, array.length);
        // TODO - fix return statement
        return mergesort(arrayCopy);
    }

    /**
     * Reads an array of integers from stdin.
     * @return an array of integers
     * @throws IOException if data cannot be read from stdin
     * @throws NumberFormatException if there is an invalid character in the
     * input stream
     */
    private static int[] readArrayFromStdin() throws IOException,
                                                     NumberFormatException {
        List<Integer> intList = new LinkedList<>();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        int value = 0, index = 0, ch;
        boolean valueFound = false;
        while ((ch = reader.read()) != -1) {
            if (ch >= '0' && ch <= '9') {
                valueFound = true;
                value = value * 10 + (ch - 48);
            } else if (ch == ' ' || ch == '\n' || ch == '\r') {
                if (valueFound) {
                    intList.add(value);
                    value = 0;
                }
                valueFound = false;
                if (ch != ' ') {
                    break;
                }
            } else {
                throw new NumberFormatException(
                        "Invalid character '" + (char)ch +
                        "' found at index " + index + " in input stream.");
            }
            index++;
        }

        int[] array = new int[intList.size()];
        Iterator<Integer> iterator = intList.iterator();
        index = 0;
        while (iterator.hasNext()) {
            array[index++] = iterator.next();
        }
        return array;
    }

    public static void main(String[] args) {
        // TODO
        StringBuilder builder = new StringBuilder();
        for (String arg : args) {
            builder.append(arg);
        }
        String input = builder.toString().trim();
        if(args.length > 1){
            System.err.println("Usage: java InversionCounter [slow]");
            System.exit(1);
        }
        if(!input.equals("slow") && !input.equals("")){
            System.err.println("Error: Unrecognized option '" + input + "'.");
            System.exit(1);
        }

        System.out.print("Enter sequence of integers, each followed by a space: ");

        try {
            int [] arr = readArrayFromStdin();
            if(arr.length == 0){
                throw new Exception();
            }
            if (input.equals("slow")) {
                System.out.print("Number of inversions: " + countInversionsSlow(arr));
            }
            else{
                System.out.print("Number of inversions: " + countInversionsFast(arr));
            }
            System.exit(0);
        } catch (NumberFormatException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
        catch (IOException io){
            System.err.println("Error: Cannot read from stdin.");
            System.exit(1);
        }
        catch(Exception ee){
            System.err.println("Error: Sequence of integers not received.");
            System.exit(1);
        }

    }
}
