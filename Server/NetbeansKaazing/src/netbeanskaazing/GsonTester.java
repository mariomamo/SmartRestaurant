package netbeanskaazing;

import com.google.gson.Gson;
import entity.Tavolo;

public class GsonTester {

    public static void main(String[] args) {
        Tavolo t = new Tavolo("MARIO", "PASS", "CIAO", 4, true, true);
        Gson gson = new Gson();
        String json = gson.toJson(t);
        System.out.println(json);
    }
    
}
