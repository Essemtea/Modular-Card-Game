import java.util.ArrayList;
import java.util.Scanner;

/**
 * The {@code Arcade} class statically manages and maintains an {@code ArrayList} of {@code Game}s,
 * and utilizes their accompanying {@code GameScreen}s to guide the player through available games.
 * 
 * @author Syed Muhammad Tahir
 * @author Haiden Hatcher
 */
public abstract class Arcade {
    private static final int TITLE_LENGTH = 64;
    private static Scanner s = new Scanner(System.in);

    private static Player player;
    private static ArrayList<Game> gameList;
    private static Game game;
    private static GameScreen screen;

    static {
        // For faster testing.
        // player = Player.getInstance("Mo");

        player = Player.getInstance(prompt("Enter your name"));
        gameList = new ArrayList<Game>();
        gameList.add(new BlackjackGame(player));
        // For future additions.
    }


    
    /** 
     * @param title
     */
    public static void printSection(String title) {
        String divider = "";
        for (int i = 0; i < TITLE_LENGTH - title.length(); i += 2) {
            divider += "=";
        }
        System.out.printf("\n%s  %s  %s\n\n", divider, title, divider);
        try {
            Thread.sleep(750);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Promtps the user to enter a {@code String}.
     * @param message
     * @return A {@code String} containing the user's non-empty response.
     */
    public static String prompt(String message) {
        String input = "";
        try {
            while (input == "") {
                System.out.print(message + ": ");
                input = s.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return input;
    }

    /**
     * Prompts the user to select a numbered option from a previously displayed list.
     * Uses nested while-loops to avoid throwing {@code NumberFormatException}.
     * @param message
     * @param limit
     * @return An {@code int} representing an option selected from a list.
     */
    public static int prompt(String message, int limit) {
        int choice = -1;
        String input = "";
        try {
            while (choice <= 0 || choice > limit) {
                System.out.print(message + ": ");
                input = s.nextLine();
                if (input.matches(".*\\d+.*")) {
                    choice = Integer.parseInt(input);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return choice;
    }

    public static void exitArcade() {
        printSection("Thank you for visiting the arcade!");
        System.exit(0);
    }

    
    /** 
     * @throws Exception
     */
    public static void playGame() throws Exception {
        if (gameList.isEmpty()) {
            throw new Exception("List of games is empty.");
        }

        printSection("Welcome to the arcade!");
        player.printBalance();

        int i = 1;
        for (Game game : gameList) {
            System.out.printf("%d. %s\n", i, game.getName());
            i++;
        }
        System.out.println(i + ". Exit arcade.");
        
        int choice = prompt("Select a game", gameList.size() + 1);

        if (choice == gameList.size() + 1) {
            exitArcade();
        } else {
            setGame(gameList.get(choice - 1));
        }
    }

    public static void setGame(Game newGame) throws Exception {
        game = newGame;
        screen = game.getScreen();
        screen.play();
    }

    public static void main(String[] args) throws Exception {
        try {
            playGame();
        } catch (Exception e) {
            e.printStackTrace();
            exitArcade();
        } finally {
            s.close();
        }
    }
}
