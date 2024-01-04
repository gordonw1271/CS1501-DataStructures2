package cs1501_p2;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

public class AutoCompleter implements AutoComplete_Inter
{
    private DLB dlb;
    private UserHistory userHistory;

    public AutoCompleter(String englishDict){
        //initialize user history
        this.userHistory = new UserHistory();
        this.dlb = new DLB();

        // read file and add each word to dictionary
        BufferedReader engDict = null;
        try {
            engDict = new BufferedReader(new FileReader(englishDict));
            String line;
            while ((line = engDict.readLine()) != null) {
                this.dlb.add(line);
            }
            engDict.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // ---------------------------------------------------
    public AutoCompleter(String englishDict, String userInfo){
        //initialize user history
        this.userHistory = new UserHistory();
        this.dlb = new DLB();

        // read file and add each word to dictionary
        BufferedReader engDict = null;
        try {
            engDict = new BufferedReader(new FileReader(englishDict));
            String line;
            while ((line = engDict.readLine()) != null) {
                this.dlb.add(line);
            }
            engDict.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader useHist = null;
        try {
            useHist = new BufferedReader(new FileReader(userInfo));
            String line;
            while ((line = useHist.readLine()) != null) {
                this.userHistory.add(line);
            }
            useHist.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    // ---------------------------------------------------
    public ArrayList<String> nextChar(char next){
        this.dlb.searchByChar(next);
        this.userHistory.searchByChar(next);

        ArrayList<String> out = this.userHistory.suggest();;

        if(out.size()<5){
            ArrayList<String> dictOut = this.dlb.suggest();
            int ind = 0;
            boolean add = true;

            while(out.size()<5 && ind<dictOut.size()){
                for(int i =0;i<out.size();i++){
                    if(dictOut.get(ind).equals(out.get(i))){
                        add = false;
                    }
                }
                if(add){
                    out.add(dictOut.get(ind));
                }
                add = true;
                ind++;
            }
        }
        return out;
    }
    // ---------------------------------------------------
    public void finishWord(String cur){
        this.userHistory.add(cur);
        this.dlb.resetByChar();
        this.userHistory.resetByChar();
    }
    // ---------------------------------------------------
    public void saveUserHistory(String fname){
        ArrayList<String> userHist = this.userHistory.acTraverse();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fname))) {
            for(int i = 1;i<userHist.size();i = i+2){
                for(int x = 0;x<Integer.parseInt(userHist.get(i));x++){
                    bw.write(userHist.get(i-1)+"\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}