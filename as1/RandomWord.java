import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String champion = null;
        int wordCount = 0;

        // Read the words from standard input and select the champion
        while (!StdIn.isEmpty()) {
            String word = StdIn.readString();
            wordCount++;

            // Randomly decide if the new word becomes the champion
            if (champion == null || StdRandom.bernoulli(1.0 / wordCount)) {
                champion = word;
            }
        }

        // Print the final champion
        if (champion != null) {
            System.out.println(champion);
        }
    }
}
