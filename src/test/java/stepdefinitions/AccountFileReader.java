package stepdefinitions;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class AccountFileReader {
    public static List<String> readLoginAndPassword(String uri) {
        BufferedReader reader;
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(uri);
        List<String> loginAndPass = new ArrayList<>();
        try {
            reader = new BufferedReader(new InputStreamReader(is));
            String line = reader.readLine();
            while (line != null) {
                loginAndPass.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loginAndPass;
    }
}
