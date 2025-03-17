import java.io.*;
import java.util.Scanner;
import java.util.Random;

public class MergeSort {
    public static int[] createRandomArray(int arrayLength) {
        Random random = new Random();
        int[] array = new int[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            array[i] = random.nextInt(101);
        }
        return array;
    }

    public static void writeArrayToFile(int[] array, String filename) {
        try {
            FileWriter writer = new FileWriter(filename);
            for (int i = 0; i < array.length; i++) {
                writer.write(String.valueOf(array[i]));
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static int[] readFileToArray(String filename) {
        int[] array = null;
        try {
            Scanner scanner = new Scanner(new File(filename));
            int arrayLength = 0;
            while (scanner.hasNextLine()) {
                arrayLength++;
                scanner.nextLine();
            }
            scanner.close();

            array = new int[arrayLength];
            Scanner arrayScanner = new Scanner(new File(filename));
            int i = 0;
            while (arrayScanner.hasNextLine()) {
                array[i++] = Integer.parseInt(arrayScanner.nextLine());
            }
            arrayScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
        return array;
    }

    public static void mergeSort(int[] array, int left, int right) {
        if (left < right) {
            int middle = left + (right - left) / 2;

            mergeSort(array, left, middle);
            mergeSort(array, middle + 1, right);

            merge(array, left, middle, right);
        }
    }

    public static void merge(int[] array, int left, int middle, int right) {
        int n1 = middle - left + 1;
        int n2 = right - middle;

        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];

        for (int i = 0; i < n1; i++) {
            leftArray[i] = array[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArray[j] = array[middle + 1 + j];
        }

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            array[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < n2) {
            array[k] = rightArray[j];
            j++;
            k++;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter 1 to generate an array of random integers, and store as a file.");
        System.out.println("Enter 2 to read an existing file and sort the integers into an array");
        int option = sc.nextInt();

        if (option == 1) {
            System.out.println("Enter the length of the array.");
            int arrayLength = sc.nextInt();
            int[] array = createRandomArray(arrayLength);
            System.out.println("Enter the file name");
            String filename = sc.next();
            writeArrayToFile(array, filename);
            System.out.println("Array has been written to the file.");
        } else if (option == 2) {
            System.out.println("Enter file name.");
            String filename = sc.next();
            int[] array = readFileToArray(filename);
            if (array != null) {
                mergeSort(array, 0, array.length - 1);
                System.out.println("Enter the file name to store the new array.");
                String outputFilename = sc.next();
                writeArrayToFile(array, outputFilename);
                System.out.println("Sorted array has been written to the file.");
            }
        } else {
            System.out.println("Invalid input.");
        }
        sc.close();
    }
}

