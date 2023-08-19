import java.util.ArrayList;

public class CardPrinter {
    private static final int LINE_COUNT = 7;

    private CardPrinter() {}

    private static String[] getCardLines(Card card) {
        String cardLines[] = new String[LINE_COUNT];

        cardLines[0] = "┌─────────┐";
        cardLines[1] = "│ " + card.getSignChar() + "       │";
        cardLines[2] = "│         │";
        cardLines[3] = "│   " + String.format("%2s", card.getType()) + "    │";
        cardLines[4] = "│         │";
        cardLines[5] = "│       " + card.getSignChar() + " │";
        cardLines[6] = "└─────────┘";

        return cardLines;
    }

    public static String getDeckString(Card... cards) {
        String deckString = "";
        String[][] deckLines = new String[cards.length][LINE_COUNT];

        for (int i = 0; i < cards.length; i++) {
            deckLines[i] = getCardLines(cards[i]);
        }

        for (int i = 0; i < LINE_COUNT; i++) {
            for (String[] line : deckLines) {
                deckString += line[i];
            }
            deckString += "\n";
        }

        return deckString;
    }

    public static void printCards(Card... cards) {
        System.out.println(getDeckString(cards));
    }

    public static String getDeckString(ArrayList<Card> cards) {
        return getDeckString(cards.toArray(new Card[cards.size()]));
    }

    public static void printCards(ArrayList<Card> cards) {
        System.out.println(getDeckString(cards));
    }

    public static String getDeckString(Deck deck) {
        return getDeckString(deck.getCards());
    }

    public static void printCards(Deck deck) {
        System.out.println(getDeckString(deck));
    }
}
