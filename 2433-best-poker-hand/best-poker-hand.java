class Solution {
    public String bestHand(int[] ranks, char[] suits) {
        // Check if all suits are the same
        boolean flush = true;
        for (int i = 1; i < suits.length; i++) {
            if (suits[i] != suits[0]) {
                flush = false;
                break;
            }
        }

        if (flush) {
            return "Flush";
        }

        // Count ranks
        int[] count = new int[14];
        int max = 0;

        for (int rank : ranks) {
            count[rank]++;
            max = Math.max(max, count[rank]);
        }

        if (max >= 3) {
            return "Three of a Kind";
        } else if (max == 2) {
            return "Pair";
        }

        return "High Card";
    }
}
