import java.util.*;

class Solution {
    public int minimumIncompatibility(int[] nums, int k) {
        int n = nums.length;
        int groupSize = n / k;

        Map<Integer, Integer> freq = new HashMap<>();
        for (int num : nums) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
            if (freq.get(num) > k) {
                return -1;
            }
        }

        int total = 1 << n;
        int[] cost = new int[total];
        Arrays.fill(cost, -1);

        // Calculate incompatibility of valid groups
        for (int mask = 0; mask < total; mask++) {
            if (Integer.bitCount(mask) == groupSize) {
                int min = Integer.MAX_VALUE;
                int max = Integer.MIN_VALUE;
                boolean[] seen = new boolean[17];
                boolean valid = true;

                for (int i = 0; i < n; i++) {
                    if ((mask & (1 << i)) != 0) {
                        if (seen[nums[i]]) {
                            valid = false;
                            break;
                        }
                        seen[nums[i]] = true;
                        min = Math.min(min, nums[i]);
                        max = Math.max(max, nums[i]);
                    }
                }

                if (valid) {
                    cost[mask] = max - min;
                }
            }
        }

        int[] dp = new int[total];
        Arrays.fill(dp, Integer.MAX_VALUE / 2);
        dp[0] = 0;

        for (int mask = 0; mask < total; mask++) {
            if (dp[mask] >= Integer.MAX_VALUE / 2) {
                continue;
            }

            // Find first unused index to avoid duplicate group selections
            int first = 0;
            while (first < n && (mask & (1 << first)) != 0) {
                first++;
            }

            if (first == n) {
                continue;
            }

            for (int group = 0; group < total; group++) {
                if ((group & (1 << first)) != 0 &&
                    (group & mask) == 0 &&
                    cost[group] != -1) {

                    dp[mask | group] = Math.min(
                        dp[mask | group],
                        dp[mask] + cost[group]
                    );
                }
            }
        }

        return dp[total - 1] >= Integer.MAX_VALUE / 2 ? -1 : dp[total - 1];
    }
}
