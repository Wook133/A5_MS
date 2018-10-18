package deVilliers_214062813.MotionSimulator;

public class Command {
    public int left;
    public int right;

    public Command(int left, int right) {
        this.left = left;
        this.right = right;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }
}
