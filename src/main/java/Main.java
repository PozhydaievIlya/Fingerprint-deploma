import com.machinezoo.sourceafis.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import com.aspose.cells.*;


public class Main {
    private static final String FilePath = "resources/DB2_B/";
    public static void main(String[] args) throws IOException {

        Map<String, Integer> dictionary = new HashMap<>();
        List<Double> metricsGenuine = new ArrayList<Double>();
        List<Double> metricsImpostor = new ArrayList<Double>();
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

////2.  Comparing all fingerprints within the same individual, with statistics at the end
//        for(int pi = 101; pi <= 110; pi++){
//            for (int pj = 1; pj <= 8; pj++){
//                dictionary.put(pi + "_" + pj, 0);
//                probe = new FingerprintTemplate(
//                    new FingerprintImage(Files.readAllBytes(Paths.get(
//                            FilePath + pi +"_"+ pj +".tif"))));
//
//                int ci = pi;
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
//
//            }
//        }
//        System.out.println("\nTable displaying fingerprint matching statistics with threshold "+ threshold + ":");
//        TreeMap<String, Integer> sortedDictionary = new TreeMap<>(dictionary);
//        System.out.println("[FP name]\t[Matches]");
//        System.out.println("---------------------");
//        for (Map.Entry<String, Integer> entry : sortedDictionary.entrySet()) {
//                    System.out.println(" " + entry.getKey() + "\t\t\t" + entry.getValue());
//                    System.out.println("---------------------");
//        }
//    }
//}

// 3.  Genuine
        for(int pi = 101; pi <= 110; pi++){
            for (int pj = 1; pj <= 8; pj++){
                dictionary.put(pi + "_" + pj, 0);
                probe = new FingerprintTemplate(
                    new FingerprintImage(Files.readAllBytes(Paths.get(
                            FilePath + pi +"_"+ pj +".tif"))));

                int ci = pi;
                    for (int cj = 1; cj <= 8; cj++){
                        if (cj == pj) {
                            continue;
                        }else{
                            candidate = new FingerprintTemplate(
                                new FingerprintImage(Files.readAllBytes(Paths.get(
                                        FilePath + ci +"_"+ cj +".tif"))));
                            matcher = new FingerprintMatcher(probe);
                            similarity = matcher.match(candidate);
                            if (similarity != 0){
                                metricsGenuine.add(similarity);
                            }
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
        }
        System.out.println("\nGenuine test with threshold "+ threshold + ":");
        TreeMap<String, Integer> sortedDictionaryGenuine = new TreeMap<>(dictionary);
        System.out.println("[FP name]\t[Matches]");
        System.out.println("---------------------");
        for (Map.Entry<String, Integer> entry : sortedDictionaryGenuine.entrySet()) {
                    System.out.println(" " + entry.getKey() + "\t\t\t" + entry.getValue());
                    System.out.println("---------------------");
        }



//1.  Impostor
        for(int pi = 101; pi <= 110; pi++){
            for (int pj = 1; pj <= 8; pj++){
                dictionary.put(pi + "_" + pj, 0);
                probe = new FingerprintTemplate(
                    new FingerprintImage(Files.readAllBytes(Paths.get(
                            FilePath + pi +"_"+ pj +".tif"))));

                        for(int ci = 101; ci <= 110; ci++){
                        if (ci == pi){
                            continue;
                        }
                        for (int cj = 1; cj <= 8; cj++){
                            candidate = new FingerprintTemplate(
                            new FingerprintImage(Files.readAllBytes(Paths.get(
                                    FilePath + ci +"_"+ cj +".tif"))));
                        matcher = new FingerprintMatcher(probe);
                        similarity = matcher.match(candidate);
                        if (similarity != 0){
                                metricsImpostor.add(similarity);
                            }

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
        }
        System.out.println("\nImpostor test with threshold "+ threshold + ":");
        TreeMap<String, Integer> sortedDictionaryImpostor = new TreeMap<>(dictionary);
        System.out.println("[FP name]\t[Matches]");
        System.out.println("---------------------");
        for (Map.Entry<String, Integer> entry : sortedDictionaryImpostor.entrySet()) {
                    System.out.println(" " + entry.getKey() + "\t\t\t" + entry.getValue());
                    System.out.println("---------------------");
        }
        double minMetricsGenuine = metricsGenuine.get(0);
        double maxMetricsGenuine = metricsGenuine.get(0);
        System.out.println("\nGenuine:");
        for (double i : metricsGenuine){
            if (i > maxMetricsGenuine){
                maxMetricsGenuine = i;
            }
            if (i < minMetricsGenuine && i != 0){
                minMetricsGenuine = i;
            }
            System.out.printf("%.5f ",i);
        }

        System.out.println("\nmin:" + minMetricsGenuine);
        System.out.println("max:" + maxMetricsGenuine);

        System.out.println("\nImpostor:");
        for (double i : metricsImpostor){
            System.out.printf("%.5f ",i);
        }
        double minMetricsImpostor = Collections.min(metricsImpostor);
        double maxMetricsImpostor = Collections.max(metricsImpostor);
        System.out.println("\nmin:" + minMetricsImpostor);
        System.out.println("max:" + maxMetricsImpostor);




        // Instantiate a new Excel workbook instance
        Workbook ExcelWorkbook = new Workbook();
        // Get reference to first worksheet in the workbook
        Worksheet ExcelWorksheet = ExcelWorkbook.getWorksheets().get(0);
        // Get reference to Cells collection in the first worksheet
        Cells WorksheetCells = ExcelWorksheet.getCells();
        int ExcelIndex = 1;
        for (double i : metricsGenuine){
            WorksheetCells.get("A" + ExcelIndex).putValue(i);
            ExcelIndex++;
        }
        WorksheetCells.get("J1").putValue("GMin: " + minMetricsGenuine);
        WorksheetCells.get("J2").putValue("GMax: " + maxMetricsGenuine);

        ExcelIndex = 1;
        for (double i : metricsImpostor){
            WorksheetCells.get("F" + ExcelIndex).putValue(i);
            ExcelIndex++;
        }
        WorksheetCells.get("J4").putValue("IMin: " + minMetricsImpostor);
        WorksheetCells.get("J5").putValue("IMax: " + maxMetricsImpostor);

        try {
            ExcelWorkbook.save("C:\\Users\\int9p\\IdeaProjects\\Fingerprint\\resources\\output\\FP_stat.xlsx");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
