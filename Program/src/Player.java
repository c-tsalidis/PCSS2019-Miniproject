import java.util.Scanner; // to receive player input

public class Player {
    // private variables
    private String name; // the name of the player
    private String [] cards; // the cards of the player
    private boolean isActive; // is it the player's turn to play?
    private int cardCounter; // counter of the player's amount of cards
    // default constructor
    Player() {
        this.name = "Anonymous Player";
        this.isActive = false; // by default it's not the player's turn to play
        this.cardCounter = 7; // by default the player has 7 cards
    }
    // constructor
    Player(String name) {
        this.name = name;
        this.isActive = false; // by default it's not the player's turn to play
        this.cardCounter = 7; // by default the player has 7 cards
    }
    // methods
    public String GetName() {
        return this.name;
    }
    public void SetName(String name) {
        this.name = name;
    }
    public void SetTurn(boolean isActive) {
        this.isActive = isActive;
    }
    public boolean GetTurn() {
        return this.isActive;
    }
    public String AskPlayerName() {
        Scanner input = new Scanner(System.in);
        try {
            String name = input.next();
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
        return name;
    }
    public String CreateLobby() {
        Scanner input = new Scanner(System.in);
        String lobbyName = null;
        try {
            lobbyName = input.next();
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
        return lobbyName;
    }
    public String JoinLobby() {
        Scanner input = new Scanner(System.in);
        String lobbyName = null;
        try {
            lobbyName = input.next();
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
        return lobbyName;
    }
    public void ShowCards() {
        System.out.println("These are your cards: ");
        for(int i = 0; i < this.cards.length; i++) {
            System.out.print(this.cards[i] + " | ");
        }
        System.out.println();
    }
    public String DrawCard(String [] deck) {
        String card = null;
        double randomCard = 0;
        // generate random number between 0 and the deck's total amount of cards
        randomCard = Math.random() * deck.length;
        card = deck[(int)randomCard];
        return card;
    }
    private String ChooseCard() {
        String card = null;
        Scanner input = new Scanner(System.in);
        try {
            boolean isPlayersCard = false;
            while (!isPlayersCard) {
                String s = input.next();
                for(int i = 0; i < this.cards.length; i++) {
                    if(this.cards[i] == s) {
                        card = this.cards[i];
                        isPlayersCard = true;
                        System.out.println("You chose: " + card);
                        break;
                    }
                    if(i == this.cards.length && isPlayersCard == false) {
                        System.out.println("It seems like you don't have this card in. Try again");
                    }
                }
            }
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
        return card;
    }
}
