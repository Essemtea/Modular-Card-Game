public class Card {
    public enum Signs {
        HEARTS, SPADES, DIAMONDS, CLUBS;
    }

    private static char HEARTS_CHAR = 'H';
    private static char SPADES_CHAR = 'S';
    private static char DIAMONDS_CHAR = 'D';
    private static char CLUB_CHAR = 'C';

    private String type;
    private Signs sign;

    public Card(String type, Signs sign) {
        this.type = type;
        this.sign = sign;
    }

    public Card(int typeInt, Signs sign) throws Exception {
        if (typeInt == 1) {
            this.type = "A";
        } else if (typeInt > 1 && typeInt <= 10) {
            this.type = Integer.toString(typeInt);
        } else if (typeInt == 11) {
            this.type = "J";
        } else if (typeInt == 12) {
            this.type = "Q";
        } else if (typeInt == 13) {
            this.type = "K";
        } else {
            throw new Exception("Card number outside of accepted range (1-13): " + typeInt);
        }
        this.sign = sign;
    }
    
    public String getType() {
        return this.type;
    }

    public Signs getSign() {
        return this.sign;
    }

    public char getSignChar() {
        char result = '\0';

        switch (sign) {
            case HEARTS:
                result = HEARTS_CHAR;
                break;
            case SPADES:
                result = SPADES_CHAR;
                break;
            case DIAMONDS:
                result = DIAMONDS_CHAR;
                break;
            case CLUBS:
                result = CLUB_CHAR;
                break;
        }

        return result;
    }

    public void print() {
        CardPrinter.printCards(this);
    }

    @Override
    public String toString() {
        return CardPrinter.getDeckString(this);
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        if (obj instanceof Card) {
            Card otherCard = (Card) obj;
            if (type.equals(otherCard.getType()) && sign.equals(otherCard.getSign())) {
                result = true;
            }
        }
        return result;
    }
}