import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Part3 {
    public static void main(String[] args) {
        try {
            File input = new File("src/data");
            Scanner inputReader = new Scanner(input);
            String[] parts;
            String[] labelList;
            int[] handType = new int[7];
            // handType[0] represents five of a kind
            // handType[1] represents four of a kind
            // handType[2] represents full house
            // handType[3] represents three of a kind
            // handType[4] represents two pair
            // handType[5] represents one pair
            // handType[6] represents high card
            while (inputReader.hasNextLine()) {
                WildJackHand hand = new WildJackHand(inputReader.nextLine());
                hand.findStrength();
                String[] uniqueLabels = new String[4];
                int[] numUniqueLabels = new int[4];
                parts = hand.getLine().split("\\|");
                labelList = parts[0].split(",");
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
                    handType[0]++;
                } else if (numUniqueLabels[0] == 4 || numUniqueLabels[1] == 4) {
                    handType[1]++;
                } else if (numUniqueLabels[0] == 3 || numUniqueLabels[1] == 3 || numUniqueLabels[2] == 3) {
                    if (numUniqueLabels[0] == 2 || numUniqueLabels[1] == 2) {
                        handType[2]++;
                    } else {
                        handType[3]++;
                    }
                } else {
                    int twoCounter = 0;
                    for (int i = 0; i < numUniqueLabels.length; i++){
                        if (numUniqueLabels[i] == 2) {
                            twoCounter++;
                        }
                    }
                    if (twoCounter == 2) {
                        handType[4]++;
                    } else if (twoCounter == 1) {
                        handType[5]++;
                    } else {
                        handType[6]++;
                    }
                }
            }
            WildJackHand.compareStrength();
            int[] rankList = WildJackHand.getRankList();
            int[] bidList = WildJackHand.getBidList();
            //Test code for debugging
            /*for (int x = 0; x < rankList.length; x++) {
                System.out.println(rankList[x]);
                System.out.println(bidList[x] + "\n----");
            }*/
            int total = 0;
            for (int i = 0; i < rankList.length; i++) {
                total += rankList[i] * bidList[i];
            }
            System.out.println("Five of a kind hands: " + handType[0] + "\nFour of a kind hands: " + handType[1] + "\nFull house hands: " + handType[2] + "\nThree of a kind hands: " + handType[3] + "\nTwo pair hands: " + handType[4] + "\nOne pair hands: " + handType[5] + "\nHigh card hands: " + handType[6]);
            System.out.println("Total bid value with Jacks wild: " + total);
        }
        catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException e");
        }
    }
}