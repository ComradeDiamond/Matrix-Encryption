public class Main 
{
    public static void main(String[] args) throws Exception
    {
        //testCase();
        //decrypt();
        fileMess();
    }

    /**
     * Just messing with files lol
     * Feel free to add any
     * Put all "encryptable" files in Files folder.
     * Java relative pathing: `C:Files/<insert file name here />`
     */
    public static void fileMess() throws Exception
    {
        Encryptor.decryptFile("C:Files/SoftwareSlogan.txt");
        Encryptor.decryptFile("C:Files/DoNotDecrypt.txt");
    }

    /**
     * Try to decrypt some stuff lol
     */
    public static void decrypt()
    {
        System.out.println(Encryptor.decrypt("WS IH ANYL G AT?ISH"));

        //13 words - irregular to test
        System.out.println(Encryptor.decrypt("HOOEE MLF LRM"));
        
        System.out.println(Encryptor.decrypt(Encryptor.encrypt("I like pineapple pizza")));
    }

    /**
     * Testing cases in the google classroom thing
     */
    public static void testCase()
    {
        //Case 1: String with < length of perfect square
        String case1 = "Java is fun!";
        System.out.println(case1 + " has a length of " + case1.length());
        System.out.println("Encrypted: " + Encryptor.encrypt(case1));
        System.out.println();

        //Case 2: String with length of a perfect square
        String case2 = "String perfect ?";
        System.out.println(case2 + " has a length of " + case2.length());
        System.out.println("Encrypted: " + Encryptor.encrypt(case2));
        System.out.println();

        //Case 3: String with > length of a perfect square
        String case3 = "Why is LAS a thing?";
        System.out.println(case3 + " has a length of " + case3.length());
        System.out.println("Encrypted: " + Encryptor.encrypt(case3));
        System.out.println();
    }
}
