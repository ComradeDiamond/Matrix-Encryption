import java.util.ArrayList;

//Imagine not having a nice little package to just deal with everything file
import java.io.File;
import java.io.FileWriter;
import java.nio.file.*;

/**
 * Encrypts something using the matrix encryption in class.
 * @author Justin
 * @since 2/25/21
 */
public class Encryptor
{
    /**
     * Inputs the plainText by row major.
     * Outputs the encrypted text by using column major.
     * Not very secure lmao.
     * @param plainText Text to be encrypted
     * @return The encrypted text
     */
    public static String encrypt(String plainText)
    {
        int row = (int) Math.sqrt(plainText.length());
        int column = (int) Math.ceil(Math.sqrt(plainText.length()));

        //In case the Math.sqrt rounded down incorrectly, we can always fix this by adding a row
        if (row * column < plainText.length())
        {
            row++;
        }

        String[][] matrix = new String[row][column];

        //Fills out the matrix using row major
        for (int r = 0; r < row; r++)
        {
            for (int c = 0; c < column; c++)
            {
                int position = r * column + c;

                if (position >= plainText.length()) continue;

                matrix[r][c] = plainText.substring(position, position + 1);
            }
        }

        //Encrypts using column major
        String strOut = "";

        for (int c = 0; c < column; c++)
        {
            for (int r = 0; r < row; r++)
            {
                if (matrix[r][c] == null) continue;

                strOut += matrix[r][c];
            }
        }

        return strOut; 
    }

    /**
     * Decrypts the encrypted text
     * @param encrypted The encrypted text
     * @postcondition The plaintext returned is all uppercase. Encrypted is unchanged.
     * @return Plaintext
     */
    public static String decrypt(String encrypted)
    {
        int row = (int) Math.sqrt(encrypted.length());
        int column = (int) Math.ceil(Math.sqrt(encrypted.length()));

        //In case the Math.sqrt rounded down incorrectly, we can always fix this by adding a row
        if (row * column < encrypted.length())
        {
            row++;
        }

        String[][] matrix = new String[row][column];
        ArrayList<Integer> rowIndexes = new ArrayList<>();

        //Fill out by column major
        for (int c = 0; c < column; c++)
        {
            for (int r = 0; r < row; r++)
            {
                int position = c * row + r;
                
                //Checks whether or not a blank is present in anything except the last row
                //If yes, jot that index in the arrayList
                if (position >= encrypted.length())
                {
                    if (r != row - 1)
                    {
                        rowIndexes.add(r);
                    }
                    continue;
                }

                matrix[r][c] = encrypted.substring(position, position + 1);
            }
        }

        //System.out.println(rowIndexes);

        //While there are wack empty spots, try to fill them in
        while (rowIndexes.size() > 0)
        {
            int rowIdx = rowIndexes.remove(0);

            //Tracks how many iterations to skip until we get to the null space
            int skipIdx = matrix.length - rowIdx - 2;

            //Shifts the matrix cipher down until it reaches
            //matrix[rows - 1][columns - rows + num]
            for (int c = column - 1, r = row - 1; 
                r != row - 1 ||  c != column - row + rowIdx;)
            {
                //This is row major btw
                int beforeR = r - 1 < 0 ? row - 1 : r - 1;
                int beforeC = beforeR == row - 1 ? c - 1 : c;

                //Skips the first few iterations
                if (skipIdx > 0)
                {
                    skipIdx--;
                }
                else
                {
                    //"Shift up" aka swap indexes .-. Idk why can't machines be smart
                    matrix[r][c] = matrix[beforeR][beforeC];
                }

                //Recalibrates the c and r
                c = beforeC;
                r = beforeR;
            }

            //Restore that index we were looking for to null
            matrix[row - 1][column - row + rowIdx] = null;
        }

        //Now piece together the string and decrypt it
        String plainText = "";

        for (String[] arr : matrix)
        {
            for (String str : arr)
            {
                if (str == null)
                {
                    continue;
                }

                plainText += str;
            }
        }

        return plainText;
    }

    /**
     * Encrypts a .txt file.
     * @param path File path. This uses relative pathing
     * @postcondition This is a one way operation and will edit the file.
     * @throws Exception If something wonky happens when you try to read the file
     */
    public static void encryptFile(String path) throws Exception
    {
        //Why is read and write in so many different files
        File leFile = new File(path);
        Path lePath = Path.of(path);
        
        String fileText = Files.readString(lePath);

        String encrypted = Encryptor.encrypt(fileText);

        FileWriter leWrite = new FileWriter(leFile, false);
        leWrite.write(encrypted);
        leWrite.close();

        System.out.println("Encryption Complete.");
    }

    /**
     * Decrypts a .txt file.
     * @param path The pathname of the file
     * @postcondition This is a one way operation that will edit the file.
     * @throws Exception if something goes wrong while decrypting
     */
    public static void decryptFile(String path) throws Exception
    {
        File leFile = new File(path);

        Path lePath = Path.of(path);
        String encrypted = Files.readString(lePath);

        String decrypted = Encryptor.decrypt(encrypted);

        FileWriter leWrite = new FileWriter(leFile, false);
        leWrite.write(decrypted);
        leWrite.close();

        System.out.println("File Decrypted.");
    }
}