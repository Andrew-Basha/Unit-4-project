public class WildJackHand {
    private String line;
    private String[] labelList;
    private int bid;
    private int strength;
    private static int[] bidList = new int[1000];
    private static int[] strengthList = new int[bidList.length];
    private static int[] rankList = new int[bidList.length];
    private static int handNum = 0;
    public WildJackHand(String line){
        this.line = line;
        String[] parts;
        parts = line.split("\\|");
        labelList = parts[0].split(",");
        bid = Integer.parseInt(parts[1]);
        strength = 0;
    }

    public String getLine(){return line;}
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
        int jackNum = 0;
        for (int i = 0;i < 5;i++){
            if (labelList[i].equals("Jack")) {
                jackNum++;
            }
        }
        if (numUniqueLabels[0] == 5) {
            strength += 9600000;
        } else if (numUniqueLabels[0] == 4 || numUniqueLabels[1] == 4) {
            if (jackNum == 1 || jackNum == 4) {
                strength += 1600000;
            }
            strength += 8000000;
        } else if (numUniqueLabels[0] == 3 || numUniqueLabels[1] == 3 || numUniqueLabels[2] == 3) {
            if (numUniqueLabels[0] == 2 || numUniqueLabels[1] == 2) {
                if (jackNum == 2 || jackNum == 3) {
                    strength += 3200000;
                } else if (jackNum == 1) {
                    strength += 1600000;
                }
                strength += 6400000;
            } else {
                if (jackNum == 1) {
                    strength += 3200000;
                }
                strength += 4800000;
            }
        } else {
            int twoCounter = 0;
            for (int i = 0; i < numUniqueLabels.length; i++) {
                if (numUniqueLabels[i] == 2) {
                    twoCounter++;
                }
            }
            if (twoCounter == 2) {
                if (jackNum == 2) {
                    strength += 4800000;
                } else if (jackNum == 1) {
                    strength += 3200000;
                }
                strength += 3200000;
            } else if (twoCounter == 1) {
                if (jackNum == 1 || jackNum == 2) {
                    strength += 3200000;
                }
                strength += 1600000;
            } else {
                if (jackNum == 1) {
                    strength += 1600000;
                }
            }
        }
        //arbitrary number
        strength += convertToStrength(labelList[4]);
        strength += (15 * convertToStrength(labelList[3]));
        strength += (225 * convertToStrength(labelList[2]));
        strength += (3375 * convertToStrength(labelList[1]));
        strength += (100000 * convertToStrength(labelList[0]));
        strengthList[handNum] = strength;
        handNum++;
        //System.out.println(strength);
    }

    public static void compareStrength(){
        /*for (int x = 0; x < strengthList.length; x++) {
            System.out.println(strengthList[x] + "\n---");
        }*/
        int rank = 1;
        for (int i = 0; i < strengthList.length && strengthList[i] != 0; i++) {
            for (int j = 0; j < strengthList.length && strengthList[j] != 0; j++) {
                //System.out.println(strengthList[i]);
                //System.out.println(strengthList[j] + "\n--");
                if ( strengthList[i] > strengthList[j] ) {
                    rank++;
                }
                // rank = 1 was here, It took me forever to realize I had to move it
            }
            rankList[i] = rank;
            rank = 1;
        }
    }
    public static int convertToStrength(String label) {
        if (label.equals("Ace")) {
            return 13;
        } else if (label.equals("King")) {
            return 12;
        } else if (label.equals("Queen")) {
            return 11;
        } else if (label.equals("Jack")) {
            return 1;
        } else {
            return Integer.parseInt(label);
        }
    }
}
