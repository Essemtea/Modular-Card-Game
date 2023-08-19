public class BlackjackScreen extends GameScreen {
    
    private BlackjackGame game;
    private BlackjackGame.State gameState = BlackjackGame.State.CONTINUE;

    public BlackjackScreen(BlackjackGame game) {
        this.game = game;
        printTitle();
    }

    @Override
    public BlackjackGame getGame() {
        return game;
    }

    @Override
    public void printTitle() {
        Arcade.printSection("Starting a game of " + game.getName() + "!");
    }

    public void placeBet() throws Exception {
        game.getPlayer().printBalance();

        int offeredBet = 0;
        offeredBet = Arcade.prompt("Place a bet for this round, " + game.getPlayer().getName(), game.getPlayer().getBalance());
        game.setBet(offeredBet);
        System.out.println("Placed a bet of " + offeredBet + " coins.");
    }

    public void assignCards(Player target, int amount) {
        target.giveCards(game.pickUpRandomCards(2));
    }

    public void showHand(Player target) {
        target.printHand();
        BlackjackGame.printValue(target.getHand());
    }

    public void showFirstCard(Player target) {
        Arcade.printSection(target.getName() + "\'s Card");
        Card c = target.getHand().getCards().get(0);
        c.print();
        BlackjackGame.printValue(c);

        System.out.println(target.getName() + " has " + (target.getHand().getCards().size() - 1) + " card in the hole.");
    }

    private void performMove(Player target, BlackjackGame.Move move) throws Exception {
        String moveString = "";

        switch (move) {
            case HIT:
                moveString = " chooses to hit!";
                game.performHit(target);
                break;
            case STAND:
                moveString = " chooses to stand!";
                break;
            case DOUBLE_DOWN:
                moveString = " doubles down!";
                game.performHit(target);
                game.setBet(game.getBet() * 2);
                break;
        }

        System.out.println(target.getName() + moveString);
    }

    private BlackjackGame.Move getPlayerMove(Player player) throws Exception {
        System.out.println("1. Hit (Take another card.)");
        System.out.println("2. Stand (Do nothing this round.)");
        System.out.println("3. Double down. (Take one more card then refuse to take more.)");

        boolean allowDouble = (game.getBet() * 2) <= player.getBalance();

        int choice = Arcade.prompt("Select a move", (allowDouble ? 3 : 2));

        if (choice == 1) {
            return BlackjackGame.Move.HIT;
        } else if (choice == 2) {
            return BlackjackGame.Move.STAND;
        } else if (choice == 3 & allowDouble) {
            return BlackjackGame.Move.DOUBLE_DOWN;
        } else {
            throw new Exception("Invalid player move selected.");
        }
    }

    private void declarePlayerWin() {
        Arcade.printSection(game.getPlayer().getName() + " wins!");
        System.out.println(game.getPlayer().getName() + " won " + game.getBet() + " coins!");
        game.playerWin();
    }

    private void declareDealerWin() {
        Arcade.printSection(game.getDealer().getName() + " wins!");
        System.out.println(game.getPlayer().getName() + " lost " + game.getBet() + " coins!");
        game.dealerWin();
    }

    private void declareReset() {
        Arcade.printSection("Push! The round ends in a tie!");
        System.out.println(game.getPlayer().getName() + "\'s bet of " + game.getBet() + " coins was returned.");
        game.restart();
    }

    private BlackjackGame.Move getDealerMove(Dealer dealer) {
        int total = BlackjackGame.getValue(dealer.getHand());

        if (total <= 16) {
            return BlackjackGame.Move.HIT;
        } else {
            return BlackjackGame.Move.STAND;
        }
    }

    private void resetOrExit() {
        Arcade.printSection("Game Over!");
        System.out.println("1. Play another game.");
        System.out.println("2. Exit arcade.");

        int option = Arcade.prompt("Would you like to play again?", 2);

        if (option == 1) {
            game.restart();
        } else if (option == 2) {
            game.end();
        }
    }

    @Override
    public void play() throws Exception {
        placeBet();
        assignCards(game.getDealer(), 2);
        assignCards(game.getPlayer(), 2);

        showFirstCard(game.getDealer());
        showHand(game.getPlayer());

        BlackjackGame.Move playerMove = BlackjackGame.Move.HIT;
        BlackjackGame.Move dealerMove = BlackjackGame.Move.HIT;
        gameState = game.checkPlayerWin();

        while (gameState == BlackjackGame.State.CONTINUE) {

            if (playerMove == BlackjackGame.Move.HIT) {
                playerMove = getPlayerMove(game.getPlayer());
            } else {
                playerMove = BlackjackGame.Move.STAND;
            }
            dealerMove = getDealerMove(game.getDealer());

            performMove(game.getPlayer(), playerMove);
            performMove(game.getDealer(), dealerMove);

            showHand(game.getDealer());
            showHand(game.getPlayer());

            if (playerMove == BlackjackGame.Move.STAND && dealerMove == BlackjackGame.Move.STAND) {
                int playerTotal = BlackjackGame.getValue(game.getPlayer().getHand());
                int dealerTotal = BlackjackGame.getValue(game.getDealer().getHand());
                if (playerTotal > dealerTotal) {
                    gameState = BlackjackGame.State.WIN;
                } else if (playerTotal == dealerTotal) {
                    gameState = BlackjackGame.State.RESET;
                } else {
                    gameState = BlackjackGame.State.LOSS;
                }
            } else {
                gameState = game.checkPlayerWin();
            }
        }

        switch (gameState) {
            case WIN:
                declarePlayerWin();
                break;
            case LOSS:
                declareDealerWin();
                break;
            case RESET:
                declareReset();
                break;
            case CONTINUE:
                throw new Exception("How did you even get here?");
        }

        resetOrExit();
    }
}
