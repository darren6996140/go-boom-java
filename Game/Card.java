package Game;

public class Card {
    private String suit;//花色
    private String rank;//数字
 
    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
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