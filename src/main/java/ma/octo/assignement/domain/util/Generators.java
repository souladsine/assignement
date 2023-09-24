package ma.octo.assignement.domain.util;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Generators {

    public static String randomUsername(){
        return  _generateRandomString(10);
    }

    public static String UUID(){
        return _generateRandomString(4) + "-" + _generateRandomString(4) + "-" + _generateRandomString(4) + "-"+ _generateRandomString(4);
    }

    private static String _generateRandomString(int maxLength){
        byte[] array = new byte[maxLength]; // length is bounded by 7
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }
}
