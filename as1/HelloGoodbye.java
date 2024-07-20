
public class HelloGoodbye {
    public static void main(String[] args) {
        
        String name1 = args[0];
        String name2 = args[1];

        // Print hello message with the provided names
        System.out.println("Hello " + name1 + " and " + name2 + ".");

        // Print goodbye message with the names in reverse order
        System.out.println("Goodbye " + name2 + " and " + name1 + ".");
    }
}