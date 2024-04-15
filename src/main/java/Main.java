import com.machinezoo.sourceafis.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


public class Main {
    private static final String FilePath = "resources/DB1_B/";
    public static void main(String[] args) throws IOException {

        Map<String, Integer> dictionary = new HashMap<>();
        var options = new FingerprintImageOptions().dpi(500);
        double threshold = 40;
        boolean matches = false;
        int count = 0;
        //    var encoded = Files.readAllBytes(Paths.get("resources/DB1_B/101_1.tif"));
        //    var decoded = new FingerprintImage(encoded, options);
        //    System.out.println(decoded);
        var probe = new FingerprintTemplate(
                    new FingerprintImage(Files.readAllBytes(Paths.get("resources/DB1_B/101_1.tif"))));
        var candidate = new FingerprintTemplate(
                    new FingerprintImage(Files.readAllBytes(Paths.get("resources/DB1_B/101_2.tif"))));

        var matcher = new FingerprintMatcher(probe);
        double similarity = matcher.match(candidate);
//        System.out.println(similarity);



//1.  Comparing all to all with statistics at the end
//        for(int pi = 101; pi <= 110; pi++){
//            for (int pj = 1; pj <= 8; pj++){
//                dictionary.put(pi + "_" + pj, 0);
//                probe = new FingerprintTemplate(
//                    new FingerprintImage(Files.readAllBytes(Paths.get(
//                            FilePath + pi +"_"+ pj +".tif"))));
//
//                for(int ci = 101; ci <= 110; ci++){
//                    for (int cj = 1; cj <= 8; cj++){
//                        candidate = new FingerprintTemplate(
//                            new FingerprintImage(Files.readAllBytes(Paths.get(
//                                    FilePath + ci +"_"+ cj +".tif"))));
//                        matcher = new FingerprintMatcher(probe);
//                        similarity = matcher.match(candidate);
//                        matches = similarity >= threshold;
//                        System.out.println("Matching " + pi + "_"+ pj +".tif with " + ci + "_"+ cj +".tif");
//                        if (matches){
//                            System.out.println(similarity + "\nResult : Fingerprints matched!\n");
//                            String keyToUpdate = pi + "_" + pj; // Key to update
//                            int incrementBy = 1; // Increment value
//
//                            // Retrieve the current value associated with the key
//                            Integer currentValue = dictionary.get(keyToUpdate);
//
//                            // Check if the key exists in the map
//                            if (currentValue != null) {
//                                // Increment the value
//                                int updatedValue = currentValue + incrementBy;
//
//                                // Put the updated value back into the HashMap
//                                dictionary.put(keyToUpdate, updatedValue);
//                            }
//                        } else {
//                            System.out.println(similarity + "\nResult : No match!\n");
//                        }
//                    }
//                }
//            }
//        }
//        TreeMap<String, Integer> sortedDictionary = new TreeMap<>(dictionary);
//        for (Map.Entry<String, Integer> entry : sortedDictionary.entrySet()) {
//                    System.out.println(entry.getKey() + ": " + entry.getValue());
//        }


//2.  Comparing all fingerprints within the same individual, with statistics at the end
        for(int pi = 101; pi <= 110; pi++){
            for (int pj = 1; pj <= 8; pj++){
                dictionary.put(pi + "_" + pj, 0);
                probe = new FingerprintTemplate(
                    new FingerprintImage(Files.readAllBytes(Paths.get(
                            FilePath + pi +"_"+ pj +".tif"))));

                int ci = pi;
                    for (int cj = 1; cj <= 8; cj++){
                        candidate = new FingerprintTemplate(
                            new FingerprintImage(Files.readAllBytes(Paths.get(
                                    FilePath + ci +"_"+ cj +".tif"))));
                        matcher = new FingerprintMatcher(probe);
                        similarity = matcher.match(candidate);
                        matches = similarity >= threshold;
                        System.out.println("Matching " + pi + "_"+ pj +".tif with " + ci + "_"+ cj +".tif");
                        if (matches){
                            System.out.println(similarity + "\nResult : Fingerprints matched!\n");
                            String keyToUpdate = pi + "_" + pj; // Key to update
                            int incrementBy = 1; // Increment value

                            // Retrieve the current value associated with the key
                            Integer currentValue = dictionary.get(keyToUpdate);

                            // Check if the key exists in the map
                            if (currentValue != null) {
                                // Increment the value
                                int updatedValue = currentValue + incrementBy;

                                // Put the updated value back into the HashMap
                                dictionary.put(keyToUpdate, updatedValue);
                            }
                        } else {
                            System.out.println(similarity + "\nResult : No match!\n");
                        }
                    }

            }
        }
        System.out.println("\nTable displaying fingerprint matching statistics with threshold "+ threshold + ":");
        TreeMap<String, Integer> sortedDictionary = new TreeMap<>(dictionary);
        System.out.println("[FP name]\t[Matches]");
        System.out.println("---------------------");
        for (Map.Entry<String, Integer> entry : sortedDictionary.entrySet()) {
                    System.out.println(" " + entry.getKey() + "\t\t\t" + entry.getValue());
                    System.out.println("---------------------");
        }
    }
}
