import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    /*
    Inside a chipset producing factory, there are older and newer machines. Newer machines are able to produce
    a higher number chipsets per minute, but both new and old machines consume the same amount of energy
    per hour. Given a list of N machines by the number of chipsets/minute, detect the machines that should be
    started to produce K chipsets per minute with minimum waste. In case there are multiple solutions, all the
    possible solutions will be displayed.

        Input file format:
        First line contains the number of machines
        Second line contains the chipset/minute for every machine
        Third line contains number of chipsets to be produced.

        Output file format :
        First line contains nr of solutions
        Next lines contains each solution
        Last line contains the waste(nr of extra pieces)

     ---

     This is an instance of the "subset sum problem" https://en.wikipedia.org/wiki/Subset_sum_problem.

     The particularity is that no one ensures that the waste will be always 0. This means that we don't exactly know the target sum (K) beforehand, thus I just compute all possible sums for all Ks.
     There could be performance improvements, but this solves the problem for the limited amount of time that I had.

     See MainTest for (very few) tests.

     Possible improvements:
     - Performance.
     - Error handling, I'm not checking many edge cases.
     - Add more tests.
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        ArrayList<Integer> machines = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            machines.add(sc.nextInt());
        }
        int k = sc.nextInt();

        Map<Integer, List<List<Integer>>> allSums = computeAllSums(machines);
        if (allSums.isEmpty()) throw new IllegalStateException("There are no results for the given input");

        printResults(k, allSums);
    }

    private static void printResults(int k, Map<Integer, List<List<Integer>>> allSums) {
        int waste = 0;
        while (true) {
            List<List<Integer>> results = allSums.get(k + waste);
            if (results == null) {
                waste++;
                continue;
            }
            System.out.println("Nr solutions=" + results.size());
            results.forEach(x -> System.out.println(x.toString()
                    .replace("[", "")
                    .replace("]", "")));
            System.out.println("Waste=" + waste);
            break;
        }
    }

    /*
    Given a list of integers, computes all possible subsets whose sum is S.
    Returns a Map where every key is a target sum (S), and its value are all different combinations of elements whose sum is S.

    This has a O(N^N) complexity so it shouldn't be used with a big amount of elements.
     */
    static Map<Integer, List<List<Integer>>> computeAllSums(ArrayList<Integer> machines) {
        Map<Integer, List<List<Integer>>> allSums = new HashMap<>();
        computeAllSums(machines, new ArrayList<>(), allSums);
        return allSums;
    }

    static void computeAllSums(ArrayList<Integer> machines, ArrayList<Integer> partial, Map<Integer, List<List<Integer>>> allSums) {
        int s = 0;
        for (int x : partial) s += x;
        if (s != 0) {
            allSums.computeIfAbsent(s, k -> new ArrayList<>());
            allSums.get(s).add(new ArrayList<>(partial));
        }

        for (int i = 0; i < machines.size(); i++) {
            ArrayList<Integer> remaining = new ArrayList<>();
            int n = machines.get(i);
            for (int j = i + 1; j < machines.size(); j++)
                remaining.add(machines.get(j));
            ArrayList<Integer> newPartial = new ArrayList<>(partial);
            newPartial.add(n);
            computeAllSums(remaining, newPartial, allSums);
        }
    }

}
