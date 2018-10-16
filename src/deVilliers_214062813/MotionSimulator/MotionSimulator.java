package deVilliers_214062813.MotionSimulator;

import deVilliers_214062813.MotionSimulator.*;
import deVilliers_214062813.MotionSimulator.NNa.myMain;

import java.util.ArrayList;

public class MotionSimulator {
    private double[][] transform = new double[2][2];
    private double oldx = 0.0D;

    private deVilliers_214062813.MotionSimulator.NNa.myMain myA;
    private deVilliers_214062813.MotionSimulator.NNx.myMain myX;
    private deVilliers_214062813.MotionSimulator.NNy.myMain myY;

    public MotionSimulator() {
        this.buildNNs();
    }

    private void buildNNs() {
        this.myA = new deVilliers_214062813.MotionSimulator.NNa.myMain();
        this.myX = new deVilliers_214062813.MotionSimulator.NNx.myMain();
        this.myY = new deVilliers_214062813.MotionSimulator.NNy.myMain();
    }

    private ArrayList<State> getStates(State curstate, TimedCommand prevcom, TimedCommand newcom) {
        ArrayList<State> ret = new ArrayList();
        State cur = new State(curstate.x, curstate.y, curstate.a);
        Command prev = new Command(prevcom.c.left, prevcom.c.right);
        Command newc = new Command(newcom.c.left, newcom.c.right);
        this.setInvTransform(cur.a - 90.0D);
        State pos = this.getNewXandY(cur, prev, newc);
        double ang = this.getNewA(cur, prev, newc);
        cur = new State(pos.x, pos.y, ang);
        ret.add(new State(pos.x, pos.y, ang));
        prev = new Command(newc.left, newc.right);

        for(int i = 2; i <= newcom.time; ++i) {
            this.setInvTransform(cur.a - 90.0D);
            pos = this.getNewXandY(cur, prev, newc);
            ang = this.getNewA(cur, prev, newc);
            cur = new State(pos.x, pos.y, ang);
            ret.add(new State(pos.x, pos.y, ang));
        }

        return ret;
    }

    public ArrayList<State> getPath(State initial, ArrayList<TimedCommand> coms) {
        this.oldx = 0.0D;
        ArrayList<State> ret = new ArrayList();
        ret.add(initial);
        State curState = initial;
        TimedCommand prevcom = new TimedCommand(new Command(0, 0), 1);

        for(int i = 0; i < coms.size(); ++i) {
            TimedCommand cur = (TimedCommand)coms.get(i);
            ArrayList<State> newStates = this.getStates(curState, prevcom, cur);

            for(int k = 0; k < newStates.size(); ++k) {
                ret.add(newStates.get(k));
            }

            State finals = (State)newStates.get(newStates.size() - 1);
            curState = new State(finals.x, finals.y, finals.a);
            prevcom = new TimedCommand(new Command(cur.c.left, cur.c.right), cur.time);
        }

        return ret;
    }

    private void setInvTransform(double ang) {
        double rad = 0.017453292519943295D * ang;
        this.transform[0][0] = Math.cos(rad);
        this.transform[1][1] = Math.cos(rad);
        this.transform[0][1] = -Math.sin(rad);
        this.transform[1][0] = Math.sin(rad);
    }

    private State getNewXandY(State curstate, Command prevcom, Command newcom) {
        double[] in = new double[]{(double)(newcom.left + 701) / 100.0D, (double)(newcom.right + 701) / 100.0D, (double)(prevcom.left + 701) / 100.0D, (double)(prevcom.right + 701) / 100.0D, this.oldx + 15.0D};
        double[] out = this.myX.getOutput(in);
        double localx = out[0];
        this.oldx = localx;
        double[] iny = new double[]{(double)(newcom.left + 701) / 100.0D, (double)(newcom.right + 701) / 100.0D, (double)(prevcom.left + 701) / 100.0D, (double)(prevcom.right + 701) / 100.0D};
        double[] outy = this.myY.getOutput(iny);
        double localy = outy[0];
        double dxprime = this.transform[0][0] * localx + this.transform[0][1] * localy;
        double dyprime = this.transform[1][0] * localx + this.transform[1][1] * localy;
        return new State(curstate.x + dxprime, curstate.y + dyprime, -1.0D);
    }

    private double getNewA(State curstate, Command prevcom, Command newcom) {
        double[] in = new double[]{(double)(newcom.left + 701) / 100.0D, (double)(newcom.right + 701) / 100.0D, (double)(prevcom.left + 701) / 100.0D, (double)(prevcom.right + 701) / 100.0D};
        double[] out = this.myA.getOutput(in);
        double globa = curstate.a + out[1] * 100.0D;
        if (globa < 0.0D) {
            globa += 360.0D;
        }

        if (globa >= 360.0D) {
            globa -= 360.0D;
        }

        return globa;
    }
}
