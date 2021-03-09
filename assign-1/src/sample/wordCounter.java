package sample;
import java.io.*;
import java.util.*;

public class wordCounter {
    public Map<String, Integer> trainHamFreq;
    public Map<String, Integer> trainSpamFreq;
    public Map<String, Float> probabilityMapWS;
    public Map<String, Float> probabilityMapWH;
    public Map<String, Float> probabilityMapSW;


    public wordCounter(){
        trainHamFreq = new TreeMap<>();
        trainSpamFreq = new TreeMap<>();
        probabilityMapSW = new TreeMap<>();
        probabilityMapWH = new TreeMap<>();
        probabilityMapWS = new TreeMap<>();
    }

    public void parseFile(File file) throws IOException{
        if (file.isDirectory()){
            File[] content = file.listFiles();
            for(File current:content){
                parseFile(current);
            }
        }else{
            Scanner scanner = new Scanner(file);
            String dir = file.getParent();
            while(scanner.hasNext()){
                String token = scanner.next();
                if (isValidWord(token)){
                    if (dir.contains("ham")) {
                        countWord(token, trainHamFreq);
                    }else{
                        countWord(token, trainSpamFreq);
                    }
                }
            }
        }
    }

    private boolean isValidWord(String word){
        String allLetters = "^[a-zA-z]+$";
        return (word.matches(allLetters));
    }

    private void countWord(String word, Map<String, Integer> map){
        if(map.containsKey(word)){
            int previous = map.get(word);
            map.put(word, previous+1);
        }else{
            map.put(word,1);
        }
    }

    public static void main(String[] args){

    }

}
