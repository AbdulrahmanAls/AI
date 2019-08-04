import java.util.*; 
import java.lang.Math;
import java.util.Random;

public class CleanRoom {


    public static void main(String[] args) {
    
        int stateA = 1;
        int stateB = 1;
        char location = 'A';
        int round = 0;
        Random rand = new Random(); 

        for(int room = 0; room <= 5 ;room++){

            stateA =  rand.nextInt(2); 
            stateB =  rand.nextInt(2); 
            System.out.println("A:"+stateA+"  B:"+stateB+"  room:"+room);


            while(round != 1 ){
                if(location == 'A'){
                    if(stateA == 0){
                        System.out.println("A: its Clean, go Right");
                        location = 'B';
                    }else if(stateA == 1){
                        System.out.println("A: Suck");
                        stateA = 0;
                    }
                    
                }else if (location == 'B'){
                    if(stateB == 0){
                        System.out.println("B: its Clean, go Left");
                        location = 'A';
                        round++;
                    }else if(stateB == 1){
                        System.out.println("B: Suck");
                        stateB = 0;
                    }
                }
            }
            round = 0;
            

        }

        
    
    }
}