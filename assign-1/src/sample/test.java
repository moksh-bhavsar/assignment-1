package sample;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import javafx.collections.*;

public class test {

    public static ObservableList<TestFile> getAllFiles(File file) throws IOException{
        Map<String, Float> probabilityMapSF = new TreeMap<>();
        File dataDirTrain = new File("F:" + File.separator + "Software_Sys_Dev_Int" + File.separator + "assignment-1" + File.separator + "data" + File.separator + "train");
        File dataDirTest = new File("F:" + File.separator + "Software_Sys_Dev_Int" + File.separator + "assignment-1" + File.separator + "data" + File.separator + "test");
        ObservableList<TestFile> prsf = FXCollections.observableArrayList();

        if (dataDirTrain.exists()) {

            wordCounter word_counter = new wordCounter();

            try {
                word_counter.parseFile(dataDirTrain);
            } catch (FileNotFoundException e) {
                System.err.println("Invalid input dir: " + dataDirTrain.getAbsolutePath());
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (String word : word_counter.trainHamFreq.keySet()) {
                float times = word_counter.trainHamFreq.get(word);
                word_counter.probabilityMapWH.put(word, times / 2752);
            }

            for (String word : word_counter.trainSpamFreq.keySet()) {
                float times = word_counter.trainSpamFreq.get(word);
                word_counter.probabilityMapWS.put(word, (times / 501));
            }

            for (String word : word_counter.trainSpamFreq.keySet()) {
                float input;
                if (word_counter.probabilityMapWH.get(word) == null) {
                    input = 1;
                }else {
                    input = word_counter.probabilityMapWS.get(word) / (word_counter.probabilityMapWS.get(word) + word_counter.probabilityMapWH.get(word));
                }
                word_counter.probabilityMapSW.put(word, input);
            }

            for (String word: word_counter.trainHamFreq.keySet()){
                float input;
                if (word_counter.probabilityMapWH.get(word) == null){
                    input = 1;
                }else if (word_counter.probabilityMapWS.get(word) == null) {
                    input = 0;
                }else {
                    input = word_counter.probabilityMapWS.get(word) / (word_counter.probabilityMapWS.get(word) + word_counter.probabilityMapWH.get(word));
                }
                word_counter.probabilityMapSW.put(word, input);
            }

            File[] content = dataDirTest.listFiles();
            for (File current : content) {
                if (current.isDirectory()) {
                    File[] current_content = current.listFiles();
                    for (File curr : current_content) {
                        Scanner scanner = new Scanner(curr);
                        float n = 0;
                        while (scanner.hasNext()) {
                            String token = scanner.next();
                            if (word_counter.probabilityMapSW.get(token) != null) {
                                n += (Math.log(1 - word_counter.probabilityMapSW.get(token)) - Math.log(word_counter.probabilityMapSW.get(token)));
                            }
                        }
                        double input = 1 + Math.pow((Math.E), n);
                        probabilityMapSF.put(curr.getName(), (float) (1 / input));
                        TestFile current_file = new TestFile(curr.getName(), probabilityMapSF.get(curr.getName()), current.getName());
                        prsf.add(current_file);
                    }
                }

            }

        }
        return prsf;
    }
}
