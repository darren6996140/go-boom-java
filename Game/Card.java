package Game;

public class Card {
    private String suit;
    private String rank;
 
    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Card(String cardStr) {
    // Assumes cardStr is in the format "rank,suit"
    String[] parts = cardStr.split(",");
    if (parts.length < 2) {
        System.out.println("Invalid card string: " + cardStr);
        return;
    }
    this.rank = parts[0];
    this.suit = parts[1];
}
 
    public String getSuit() {
        return suit;
    }
 
    public void setSuit(String suit) {
        this.suit = suit;
    }
 
    public String getRank() {
        return rank;
    }
 
    public void setRank(String rank) {
        this.rank = rank;
    }
 
    @Override
    public String toString() {
        return suit+rank;
    }
}

