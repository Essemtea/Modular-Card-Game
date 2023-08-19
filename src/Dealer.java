/** 
 * A subclass of {@code Player} used in the {@code BlackjackGame} class.
 * Follows 'house rules' to determine its next move on its turn.
 * 
 * @author Syed Muhammad Tahir
 * @author Haiden Hatcher
 */
public class Dealer extends Player {
    private static Dealer instance;
    
    private Dealer() {
        setName("Dealer");
        setHand(new Deck());
    }

    
    /** 
     * @return Dealer
     */
    public static Dealer getInstance() {
        synchronized (Dealer.class) {
            if (instance == null) {
                return new Dealer();
            } else {
                return instance;
            }
        }
    }
}
