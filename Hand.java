import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Hand {
    private String line;
    private String[] labelList;
    private int handType;
    private int bid;
    private int strength;
    private static int[] bidList = new int[1000];
    private static int[] strengthList = new int[bidList.length];
    private static int[] rankList = new int[bidList.length];
    private static int handNum = 0;
    public Hand(String line){
        this.line = line;
        String[] parts;
        parts = line.split("\\|");
        labelList = parts[0].split(",");
        bid = Integer.parseInt(parts[1]);
        strength = 0;
    }

    public String getLine(){
        return line;
    }
    public static int[] getRankList() {return rankList;}
    public static int[] getBidList() {return bidList;}
    public void findStrength() {
        bidList[handNum] = bid;
        String[] uniqueLabels = new String[4];
        int[] numUniqueLabels = new int[4];
        uniqueLabels[0] = labelList[0];
        numUniqueLabels[0]++;
        for (int i = 1; i < labelList.length; i++) {
            if (labelList[i].equals(uniqueLabels[0])) {
                numUniqueLabels[0]++;
            } else {
                if (labelList[i].equals(uniqueLabels[1])) {
                    numUniqueLabels[1]++;
                } else if (uniqueLabels[1] == null) {
                    numUniqueLabels[1]++;
                    uniqueLabels[1] = labelList[i];
                } else {
                    if (labelList[i].equals(uniqueLabels[2])) {
                        numUniqueLabels[2]++;
                    } else if (uniqueLabels[2] == null) {
                        numUniqueLabels[2]++;
                        uniqueLabels[2] = labelList[i];
                    } else {
                        {
                            if (labelList[i].equals(uniqueLabels[3])) {
                                numUniqueLabels[3]++;
                            } else if (uniqueLabels[3] == null) {
                                numUniqueLabels[3]++;
                                uniqueLabels[3] = labelList[i];
                            }
                        }
                    }
                }
            }
        }
        if (numUniqueLabels[0] == 5) {
            strength += 960000000;
            handType = 0;
        } else if (numUniqueLabels[0] == 4 || numUniqueLabels[1] == 4) {
            strength += 800000000;
            handType = 1;
        } else if (numUniqueLabels[0] == 3 || numUniqueLabels[1] == 3 || numUniqueLabels[2] == 3) {
            if (numUniqueLabels[0] == 2 || numUniqueLabels[1] == 2) {
                strength += 640000000;
                handType = 2;
            } else {
                strength += 480000000;
                handType = 3;
            }
        } else {
            int twoCounter = 0;
            for (int i = 0; i < numUniqueLabels.length; i++) {
                if (numUniqueLabels[i] == 2) {
                    twoCounter++;
                }
            }
            if (twoCounter == 2) {
                strength += 320000000;
                handType = 4;
            } else if (twoCounter == 1) {
                strength += 160000000;
                handType = 5;
            } else {
                handType = 6;
            }
        }
        //arbitrary number
        strength += convertToStrength(labelList[4]);
        strength += (20 * convertToStrength(labelList[3]));
        strength += (500 * convertToStrength(labelList[2]));
        strength += (35000 * convertToStrength(labelList[1]));
        strength += (1000000 * convertToStrength(labelList[0]));
        strengthList[handNum] = strength;
        handNum++;
    }

    public static void compareStrength(){
        //Test code for debugging
        /*for (int x = 0; x < strengthList.length; x++) {
            System.out.println(strengthList[x] + "\n---");
        }*/
        int rank = 1;
        for (int i = 0; i < strengthList.length && strengthList[i] != 0; i++) {
            for (int j = 0; j < strengthList.length && strengthList[j] != 0; j++) {
                //Test code for debugging
                /*System.out.println(strengthList[i]);
                System.out.println(strengthList[j] + "\n--");*/
                if ( strengthList[i] > strengthList[j] ) {
                    rank++;
                }
                // rank = 1; was here, It took me forever to realize I had to move it
            }
            rankList[i] = rank;
            rank = 1;
        }
    }
    public static int convertToStrength(String label) {
        if (label.equals("Ace")) {
            return 14;
        } else if (label.equals("King")) {
            return 13;
        } else if (label.equals("Queen")) {
            return 12;
        } else if (label.equals("Jack")) {
            return 11;
        } else {
            return Integer.parseInt(label);
        }
    }
}
