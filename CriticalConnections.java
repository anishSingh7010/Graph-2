// TC: O(v + e)
// SC: O(v + e)

// Approach: Forward looking DFS to see if there
// is a cycle. If cycle is not found, that's a critical connection.
// If cycle is found, update lowest array with min value.

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CriticalConnections {
    int time;

    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        List<List<Integer>> res = new ArrayList<>();
        this.time = 0;

        int[] discovery = new int[n];
        int[] lowest = new int[n];
        Map<Integer, List<Integer>> adjList = new HashMap<>();

        for (List<Integer> c : connections) {
            if (!adjList.containsKey(c.get(0))) {
                adjList.put(c.get(0), new ArrayList<>());
            }

            if (!adjList.containsKey(c.get(1))) {
                adjList.put(c.get(1), new ArrayList<>());
            }

            adjList.get(c.get(0)).add(c.get(1));
            adjList.get(c.get(1)).add(c.get(0));
        }

        Arrays.fill(discovery, -1);
        dfs(0, -1, n, adjList, discovery, lowest, res);

        return res;
    }

    private void dfs(int node, int parent, int n, Map<Integer, List<Integer>> adjList, int[] discovery, int[] lowest,
            List<List<Integer>> res) {
        // assigning time
        discovery[node] = time;
        lowest[node] = time;
        time++;

        for (int neighbor : adjList.get(node)) {
            if (neighbor == parent) {
                continue;
            }

            if (discovery[neighbor] == -1) {
                dfs(neighbor, node, n, adjList, discovery, lowest, res);
            }

            if (lowest[neighbor] > discovery[node]) {
                res.add(Arrays.asList(node, neighbor));
            }

            lowest[node] = Math.min(lowest[node], lowest[neighbor]);
        }
    }
}