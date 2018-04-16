package model;

public class Die {

    private int value;
    private boolean hasBeenRerolled = false;

    public Die() {
        roll();
    }

    public int getValue() { return value; }

    public void reroll() {
        if(!hasBeenRerolled) {
            hasBeenRerolled = true;
            roll();
        }
    }

    private void roll() {
        value = (int) (Math.random() * 6 + 1);
    }

    public String toString() { return "" + value; }
}
