public class Dealer extends Player {
    private static Dealer instance;
    
    private Dealer() {
        setName("Dealer");
        setHand(new Deck());
    }

    public static Dealer getInstance() {
        if (instance == null) {
            return new Dealer();
        } else {
            return instance;
        }
    }
}
