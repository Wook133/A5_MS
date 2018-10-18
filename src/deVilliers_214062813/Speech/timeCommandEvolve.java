package deVilliers_214062813.Speech;

import deVilliers_214062813.MotionSimulator.Command;
import deVilliers_214062813.MotionSimulator.MotionSimulator;
import deVilliers_214062813.MotionSimulator.State;
import deVilliers_214062813.MotionSimulator.TimedCommand;

import java.util.ArrayList;
import java.util.Random;

public class timeCommandEvolve {

    ArrayList<State> myStates;
    ArrayList<State> yourStates;
    ArrayList<TimedCommand> timedCommands;
    public Command command;
    public int speed;
    public double rank;
    Random random;
    MotionSimulator ms;

    State startState;

    public timeCommandEvolve(ArrayList<State> yourStates, Random random, MotionSimulator ms){

        this.startState = yourStates.get(0);
        this.yourStates = yourStates;
        this.random = random;
        this.ms = ms;

        generateRandomCommand();
        calculateRank();
    }

    public void setSpeed(timeCommandEvolve a){
        this.speed = a.speed;

        timedCommands = new ArrayList<TimedCommand>();
        timedCommands.add(new TimedCommand(command, this.speed));

        myStates = ms.getPath(startState, timedCommands);

        calculateRank();
    }

    public void setCommand(timeCommandEvolve a){
        this.command = a.command;

        timedCommands = new ArrayList<TimedCommand>();
        timedCommands.add(new TimedCommand(this.command, speed));

        myStates = ms.getPath(startState, timedCommands);

        calculateRank();
    }

    public void setTimeCommand(Command c, int Speed){
        this.command = c;
        this.speed = Speed;
        timedCommands = new ArrayList<TimedCommand>();
        timedCommands.add(new TimedCommand(this.command, speed));

        myStates = ms.getPath(startState, timedCommands);

        calculateRank();
    }

    public void setSpeed(int speed){
        this.speed = speed;

        timedCommands = new ArrayList<TimedCommand>();
        timedCommands.add(new TimedCommand(command, this.speed));

        myStates = ms.getPath(startState, timedCommands);

        calculateRank();
    }

    public void setCommand(int left, int right){
        this.command = new Command(left, right);

        timedCommands = new ArrayList<TimedCommand>();
        timedCommands.add(new TimedCommand(this.command, speed));

        myStates = ms.getPath(startState, timedCommands);

        calculateRank();
    }

    public void setYourStates(ArrayList<State> yourStates){
        this.yourStates = yourStates;
        startState = yourStates.get(0);
    }

    public void generateRandomCommand()
    {
        command = new Command((random.nextInt(100)+1), (random.nextInt(100)+1));
        speed = random.nextInt(5) + 1;
        timedCommands = new ArrayList<TimedCommand>(1);

        timedCommands.add(new TimedCommand(new Command(this.command.left, this.command.right), this.speed));

        myStates = ms.getPath(startState, timedCommands);
    }


    public void calculateRank(){

        rank = 0;
        State mine;
        State yours;

        for(int x = 0; x < myStates.size(); x++){

            mine = myStates.get(x);

            if(x < yourStates.size()){
                yours = yourStates.get(x);
            }
            else{
                yours = new State(99999, 99999, 0);
            }

            rank += calculateDistance(mine, yours);
        }

        rank = rank/myStates.size();
        rank = 1/rank;
    }

    private double calculateDistance(State a, State b){
        return Math.sqrt(Math.pow(a.x-b.x, 2) + Math.pow(a.y-b.y, 2));
    }

    private int fiftyFifty(){
        boolean value = random.nextBoolean();

        if(value)   return 1;
        else return -1;
    }
}

