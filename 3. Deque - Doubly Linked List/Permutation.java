import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Permutation <k>");
            return;
        }

        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            rq.enqueue(item);
        }

        for (int i = 0; i < k; i++) {
            if (!rq.isEmpty()) {
                System.out.println(rq.dequeue());
            }
        }
    }
}
