import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class passwordHashing {
    String password;
    String user;
    passwordHashing() throws IOException {
        init();
    }

    public String getHashOf (String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");

        byte[] messageDigest = md.digest(input.getBytes());

        BigInteger bigInt = new BigInteger(1, messageDigest);

        return bigInt.toString(16);
    }

    private void init() throws IOException {
        File file = new File("authFile.pw");
        String temp;
        if (file.exists()) {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            try {
                temp = bufferedReader.readLine();
                user = temp.split(";")[0];
                password = temp.split(";")[1];

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            bufferedReader.close();
            fileReader.close();
        }
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public void saveAccount(String user, String password) throws IOException, NoSuchAlgorithmException {
        String account = user + ";" + getHashOf(password);
        File file = new File("authFile.pw");
        FileWriter fileWriter = new FileWriter(file, false);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(account);
        bufferedWriter.close();
        fileWriter.close();

        init();
    }
}
