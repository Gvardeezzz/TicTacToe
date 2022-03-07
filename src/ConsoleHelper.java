import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleHelper {
    public static void printMessage(String message){
        System.out.println(message);
    }

    public static String readMessage() {
        String result = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            result = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


}
