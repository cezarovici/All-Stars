package PaooGame.GameObjects;

public class PlayerControlTemplate {
    private int up;
    private int down;
    private int left;
    private int right;

    public PlayerControlTemplate(int []directions) {
        this.up = directions[0];
        this.down = directions[1];
        this.left = directions[2];
        this.right = directions[3];
    }

    public int getUpKey() {
        return up;
    }

    public int getDownKey() {
        return down;
    }

    public  int getLeftKey() {
        return left;
    }

    public int getRightKey() {
        return right;
    }
}
