import java.util.ArrayList;

/**
 * As an extnesion of {@code Game}, the {@code BlackjackGame} class controls the rules and functions of
 * a full game of Blackjack. <br><br>
 * It remains largely unaware of the higher-level {@code BlackjackScreen} class' functions beyond calling its constructor.
 * 
 * @author Syed Muhammad Tahir
 * @author Haiden Hatcher
 */
public class BlackjackGame extends Game {
    public enum State {
        WIN,
        LOSS,
        CONTINUE,
        RESET;
    }
    public enum Move {
        HIT,
        STAND,
        DOUBLE_DOWN;
    }

    private Player player;
    private Dealer dealer;
    private int bet;

    /**
     * Construct a new {@code BlackjackGame}.
     * Invokes the default {@code Game} constructor to iniitalize the game's deck.
     * @param player
     */
    public BlackjackGame(Player player) {
        setName("Blackjack");
        this.player = player;
        this.dealer = Dealer.getInstance();
        this.bet = 0;
    }

    
    /** 
     * @return Player
     */
    public Player getPlayer() {
        return player;
    }

    
    /** 
     * @return Dealer
     */
    public Dealer getDealer() {
        return dealer;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int amount) throws Exception {
        if (amount < 0 || amount > player.getBalance()) {
            throw new Exception("Invalid bet amount entered.");
        }
        bet = amount;
    }

    public State checkPlayerWin() {
        State playerResult = countHand(player);
        State dealerResult = countHand(dealer);

        if (playerResult == State.LOSS) { // Player bust (even if the dealer busts)
            return State.LOSS;
        } else if (dealerResult == State.LOSS) { // Dealer bust
            return State.WIN;
        } else if (playerResult == State.WIN) {
            if (dealerResult == State.WIN) {
                return State.RESET;
            } else {
                return State.WIN;
            }
        } else {
            return State.CONTINUE;
        }
    }

    public void performHit(Player target) {
        target.giveCards(pickUpRandomCard());
    }

    public void end() {
        resetGame();
        Arcade.exitArcade();
    }

    public void restart() {
        resetGame();
        try {
            Arcade.playGame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetGame() {
        player.getHand().empty();
        dealer.getHand().empty();
        resetDeck();
    }

    public void playerWin() {
        try {
            player.giveCoins(bet);
        } catch (Exception e) {
            e.printStackTrace();
        }

        resetGame();
    }

    public void dealerWin() {
        try {
            player.takeCoins(bet);
        } catch (Exception e) {
            e.printStackTrace();
        }

        resetGame();
    }

    public static State countHand(Player target) {
        int sum = getValue(target.getHand());

        if (sum == 21) {
            return State.WIN;
        } else if (sum > 21) {
            return State.LOSS;
        } else {
            return State.CONTINUE;
        }
    }

    public static int getValue(Card... cards) {
        String type = "";
        int result;
        int aceCount = 0;
        int sum = 0;

        try {
            for (Card card : cards) {
                result = 0;
                type = card.getType();
                if (type.matches(".*\\d.*")) {
                    result = Integer.parseInt(type);
                } else if (type.equals("J") || type.equals("Q") || type.equals("K")) {
                    result = 10;
                } else if (type.equals("A")) {
                    result = 0;
                    aceCount++;
                } else {
                    throw new Exception("Card type " + type + " cannot be parsed into a value.");
                }
                sum += result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 1; i <= aceCount; i++) {
            if (sum + 11 == 21) {
                if (i == aceCount) {
                    sum += 11;
                } else {
                    sum += 1;
                }
            } else {
                sum += 1;
            }
        }

        return sum;
    }

    public static int getValue(ArrayList<Card> cards) {
        Card[] arr = new Card[cards.size()];
        cards.toArray(arr);

        int sum = getValue(arr);
        return sum;
    }

    public static int getValue(Deck deck) {
        int sum = getValue(deck.getCards());
        return sum;
    }

    public static void printValue(Card... cards) {
        System.out.println("Total Value: " + getValue(cards) + "/21");
    }

    public static void printValue(ArrayList<Card> cards) {
        Card[] arr = new Card[cards.size()];
        cards.toArray(arr);
        printValue(arr);
    }

    public static void printValue(Deck deck) {
        printValue(deck.getCards());
    }

    @Override
    public BlackjackScreen getScreen() {
        return new BlackjackScreen(this);
    }
}
