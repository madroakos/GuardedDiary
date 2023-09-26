import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        passwordHashing passwordHashing = new passwordHashing();
        new loginPage(passwordHashing);
    }
}