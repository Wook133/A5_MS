package deVilliers_214062813.MotionSimulator;

public class TimedCommand {
    public Command c;
    public int time;

    public TimedCommand(Command c, int time) {
        this.c = c;
        this.time = time;
    }

    public Command getC() {
        return c;
    }

    public int getTime() {
        return time;
    }


}
