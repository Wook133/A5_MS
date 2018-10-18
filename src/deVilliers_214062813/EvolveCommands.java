package deVilliers_214062813;

import deVilliers_214062813.MotionSimulator.MotionSimulator;
import deVilliers_214062813.MotionSimulator.State;
import deVilliers_214062813.MotionSimulator.TimedCommand;
import deVilliers_214062813.MotionSimulator.VisualFrame;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;
/**
 *
 * @author User
 */
public class EvolveCommands {

    ArrayList<TimedCommand> timedCommands;
    ArrayList<State> requiredPath;
    ArrayList<State> yourStates;
    ArrayList<State> myStates;
    ArrayList<timeCommandEvolve> commands;
    Random random;
    int speedMutationFactor = -1;
    double wheelsMutationFactor = 0.5;
    double mutationProbability = 0.3;
    int populationSize = 1000;

    int numberGenerations = 100;

    MotionSimulator ms;

    public EvolveCommands(){

        timedCommands = new ArrayList<TimedCommand>();
        this.random = new Random();
        ms = new MotionSimulator();

        loadrequiredStateList();
        getYourStates();
        generateRandomPopulation(populationSize);
        run();
    }

    public void run(){

        int count = 0;
        int tempCount = 0;

        while(yourStates.size() > 0){

            timeCommandEvolve ultimate = new timeCommandEvolve(yourStates, random, ms);

            while(count < numberGenerations){
                ArrayList<timeCommandEvolve> newCommands = new ArrayList<timeCommandEvolve>();
                timeCommandEvolve best = selectBest(commands);
                if(best.rank > ultimate.rank){
                    ultimate.setTimeCommand(best.command, best.speed);
                }

                System.out.println(ultimate.rank);
                newCommands.add(best);
                newCommands.add(selectSecondBest(this.commands));

                while(newCommands.size() < commands.size()){
                    timeCommandEvolve temp = firstCrossOver(tournamentSelection(), tournamentSelection());
                    //temp = mutationC(temp);
                    newCommands.add(temp);
                }

                commands = newCommands;
                count++;
            }
            tempCount++;
            timedCommands.add(new TimedCommand(ultimate.command, ultimate.speed));
            getYourStates();
            System.out.println(yourStates.size());

            if(yourStates.size() > 0){
                generateRandomPopulation(populationSize);
            }
            else{
                break;
            }

            count = 0;
        }

        getMyStates();
        VisualFrame vf = new VisualFrame(myStates);
        exporTimedCommands();
        exportBestStates(myStates);
    }

    public timeCommandEvolve tournamentSelection(){

        ArrayList<timeCommandEvolve> sample = new ArrayList<timeCommandEvolve>();
        for(int x = 0; x < 5; x++){
            sample.add(commands.get(random.nextInt(commands.size())));
        }
        return selectBest(sample);
    }

    public timeCommandEvolve selectBest(ArrayList<timeCommandEvolve> commands){

        timeCommandEvolve best = commands.get(0);
        timeCommandEvolve temp;

        for(int x = 1; x < commands.size(); x++){
            temp = commands.get(x);

            if(temp.rank > best.rank){
                best = temp;
            }
        }

        return best;
    }

    public timeCommandEvolve selectSecondBest(ArrayList<timeCommandEvolve> commands){

        timeCommandEvolve best = commands.get(0);
        timeCommandEvolve secondBest = commands.get(1);

        if(best.rank < secondBest.rank){
            timeCommandEvolve temp = best;
            best = secondBest;
            secondBest = temp;
        }

        for(int x = 2; x < commands.size(); x++){
            timeCommandEvolve temp = commands.get(x);

            if(temp.rank > best.rank){
                secondBest = best;
                best = temp;
            }
            else if(temp.rank > secondBest.rank){
                secondBest = temp;
            }
        }

        return secondBest;
    }

    public timeCommandEvolve firstCrossOver(timeCommandEvolve a, timeCommandEvolve b){

        if(random.nextBoolean()){
            a.setSpeed(b);
        }
        else{
            a.setCommand(b);
        }
        return a;
    }

    public timeCommandEvolve mutationB(){
        return new timeCommandEvolve(yourStates, random, ms);
    }
    public timeCommandEvolve mutationC(timeCommandEvolve a){
        if(random.nextBoolean()){
            a.setCommand(a.command.left, (random.nextInt(400)+1));
        }else{
            a.setCommand((random.nextInt(400)+ 1), a.command.left);
        }
        return a;
    }
    public timeCommandEvolve mutationD(timeCommandEvolve a){
        a.setSpeed(random.nextInt(5)+1);
        return a;
    }

    public timeCommandEvolve mutation(timeCommandEvolve a){

        int indicator = random.nextInt(2);

        if(indicator == 0){

            int rightSpeed = a.command.right + (int)Math.ceil(a.command.right*fiftyFifty()*wheelsMutationFactor);

            if(rightSpeed > 400){
                rightSpeed = 400;
            }else if(rightSpeed < 0){
                rightSpeed = 0;
            }

            a.setCommand(rightSpeed, a.command.left);
        }
        else if(indicator == 1){
            int leftSpeed = a.command.left + (int)Math.ceil(a.command.left*fiftyFifty()*wheelsMutationFactor);

            if(leftSpeed > 400){
                leftSpeed = 400;
            }else if(leftSpeed < 0){
                leftSpeed = 0;
            }
        }

        return a;
    }

    public void generateRandomPopulation(int number){

        commands = new ArrayList<timeCommandEvolve>(number);

        for(int x  = 0; x < number; x++){
            commands.add(new timeCommandEvolve(yourStates, random, ms));
        }

    }

    private void exportBestStates(ArrayList<State> states){
        try{
            FileOutputStream fos = new FileOutputStream("best.csv");
            PrintWriter pw = new PrintWriter(fos);


            State temp;

            for(int x = 0; x < states.size(); x++){
                temp = states.get(x);
                pw.println(temp.x+","+temp.y+","+temp.a);
            }

            pw.flush();
            fos.close();

        }catch(Exception e){
            System.out.println(e);
        }
    }

    private void exporTimedCommands(){
        try{
            FileOutputStream fos = new FileOutputStream("bestCommands.csv");
            PrintWriter pw = new PrintWriter(fos);


            TimedCommand temp;

            for(int x = 0; x < timedCommands.size(); x++){
                temp = timedCommands.get(x);
                pw.println(temp.c.left+","+temp.c.right+","+temp.time);
            }

            pw.flush();
            fos.close();

        }catch(Exception e){
            System.out.println(e);
        }
    }

    public State getLastState(){

        if(timedCommands.size() == 0){
            return new State(0, 0, 45);
        }else{
            ArrayList<State> myStates = ms.getPath(new State(0, 0, 45), timedCommands);
            return myStates.get(myStates.size() - 1);
        }
    }

    public void getMyStates(){

        myStates = ms.getPath(new State(0, 0, 90), timedCommands);
    }

    public void getYourStates(){

        if(timedCommands.size() > 0){
            ArrayList<State> tempStates = ms.getPath(new State(0, 0, 90), timedCommands);

            int difference = requiredPath.size() - tempStates.size();

            yourStates = new ArrayList<State>();

            if((tempStates.size()) < (requiredPath.size())){

                for(int x = (tempStates.size() - 1); x < requiredPath.size(); x++){
                    yourStates.add(requiredPath.get(x));
                }
            }
        }
        else{
            yourStates = requiredPath;
        }
    }
    public void loadrequiredStateList()
    {
        requiredPath = new ArrayList<State>(100);
        try {
            BufferedReader reader = new BufferedReader(new FileReader("states.csv"));
            String line = null;
            Scanner scanner = null;
            int index = 0;
            reader.readLine();
            while ((line = reader.readLine()) != null)
            {
                scanner = new Scanner(line);
                while (scanner.hasNext())
                {
                    String data = scanner.next();
                    String[] srow = data.split(",");
                    double x, y, a;
                    x = Double.valueOf(srow[0]);
                    y = Double.valueOf(srow[1]);
                    a = Double.valueOf(srow[2]);
                    State curDS = new State(x, y, a);
                    System.out.println(curDS.toString());
                    requiredPath.add(curDS);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

   /* private void loadrequiredStateList(){
        try{
            requiredPath = new ArrayList<State>(100);

            String fileName = "states.csv";

            FileInputStream fis = new FileInputStream(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);


            while(br.h){
                String[] ss = s.split(",");
              /*  StringTokenizer t = new StringTokenizer(br.readLine(), ",");
                State st = new State(Double.parseDouble(t.nextToken()), Double.parseDouble(t.nextToken()), Double.parseDouble(t.nextToken()));*/
            /*  State st = new State(Double.valueOf(ss[0]), Double.valueOf(ss[1]), Double.valueOf(ss[2]));
              System.out.println(st.toString());
              requiredPath.add(st);
            }

            br.close();
            isr.close();
            fis.close();
        }
        catch(Exception e){

            System.out.println(e);
        }
    }*/

    private int fiftyFifty(){
        boolean value = random.nextBoolean();

        if(value)   return 1;
        else return -1;
    }
}

