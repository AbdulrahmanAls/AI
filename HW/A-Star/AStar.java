import java.util.*;

public class AStar
{
    int N; // number of vertices in the graph
    boolean[][] G; // the graph as an adjacency matrix
    int [] estCost; // estimated cost to the goal
    int [][] toCityCost; // The cost among cities
    int [] funcGCost; // The cost so far to reach a city
    int [] funcFCost; // The estimated total cost through a city to the goal
    boolean [] calc; // To determine whether the path cost to a city has been calculated or not
    List<Integer> list; // list represents the nodes which have to be expanded

    public static void main(String[] args) {
        new AStar();
    }

    AStar() { // The constructor
        N=13;
        G=new boolean[N][N];
        setupGraph();
        estCost=new int[N];
        setupEstCost();
        toCityCost=new int[N][N]; // cost among cities
        btwCities();
        funcGCost=new int[N]; // The cost so far to reach a city
        funcFCost=new int[N]; // The estimated total cost through a city to the goal
        calc= new boolean[N];
        list= new ArrayList<Integer>();

        System.out.println("------------------------------");
        System.out.println();
        int start=10;// Arad is the start
        funcGCost[start]=0;
        assignFCost(start);
        calc[start]=true;
        list.add(10);

        //perform A* search algorithm on the graph
        AStar(10); //Arad city is considered to be the start location

        System.out.println();
        System.out.println();
    }

    void compTwoCities(int from,int to) {

    int newR=retGCost(from)+btwCost(from,to)+hFunc(to);
 if(newR<funcFCost[to])
        funcFCost[to]=newR;
        }

public void AStar(int Loc){
        System.out.println("The current location: "+ retCity(Loc));
        System.out.println("F(n): "+retFCost(Loc)+" = G(" + retGCost(Loc) +") + H("+
        hFunc(Loc)+")\n");

        if(Loc==2) { // Bucharest location = 2
        System.out.println("now in Bucharest, The goal has been reached");
        return;
        }

        for(int i=0;i<N;i++)
        if (G[Loc][i]==true && list.contains(i))
        compTwoCities(Loc,i);
        else if (G[Loc][i]==true) {
        list.add(i);assignGCost(Loc,i);
        assignFCost(i);
        }
        list.remove((Object)(new Integer(Loc)));

        System.out.println("---------------Expansion--------------");
        for (int i:list) {
        System.out.println("|"+retCity(i)+" F(n): "+retFCost(i)+" = G(" + retGCost(i) +") + H("+
        hFunc(i)+")|"); }
        System.out.println("----------------------------------------\n\n");

        int dest=list.get(0);
        for(int i=1;i<list.size();i++)
        if(retFCost(list.get(i))<retFCost(dest))
        dest=list.get(i);
        AStar(dest);
        }

    //initial setup of the graph
    public void setupGraph() {
        G[0][1]=G[1][0]=true; // notice that for each edge G[i][j] == G[j][i]
        G[0][3]=G[3][0]=true; // this means that the graph is undirected
        G[1][5]=G[5][1]=true;
        G[1][7]=G[7][1]=true;
        G[2][8]=G[8][2]=true;
        G[2][5]=G[5][2]=true;
        G[3][4]=G[4][3]=true;
        G[4][6]=G[6][4]=true;
        G[5][7]=G[7][5]=true;
        G[6][10]=G[10][6]=true;
        G[7][9]=G[9][7]=true;
        G[8][9]=G[9][8]=true;
        G[9][10]=G[10][9]=true;
        G[9][12]=G[12][9]=true;
        G[10][11]=G[11][10]=true;
        G[11][12]=G[12][11]=true;
    }

    public void setupEstCost() {
        estCost=new int[N];
        estCost[0]=242; //Dobreta
        estCost[1]=160; //Craiova
        estCost[2]=0; //Bucharest
        estCost[3]=241; //Mehadia
        estCost[4]=244; //Lugoj
        estCost[5]=98; //Pitesti
        estCost[6]=329; //Timisoara
        estCost[7]=193; //Rimnicu Vilcea
        estCost[8]=178; //Fagaras......
        estCost[9]=253; //Sibiu
        estCost[10]=366; //Arad
        estCost[11]=374; //Zerind
        estCost[12]=380; //Oradea
    }



    public int hFunc(int i) {
        return estCost[i];
    }

    public int btwCost(int first,int second) {
        return toCityCost[first][second];
    }

    public void assignGCost(int from,int to) {
        funcGCost[to]=retGCost(from)+btwCost(from,to);
    }

    public int retGCost(int city) {
        return funcGCost[city];
    }

    public void assignFCost(int city) {
        funcFCost[city]=retGCost(city)+hFunc(city);
    }

    public int retFCost(int city) {
        return funcFCost[city];
    }

    void btwCities( ) {
        toCityCost=new int[N][N];
        toCityCost[0][1]=toCityCost[1][0]=120; // notice that for each edge G[i][j] == G[j][i]
        toCityCost[0][3]=toCityCost[3][0]=75; // this means that the graph is undirected
        toCityCost[1][5]=toCityCost[5][1]=138;
        toCityCost[1][7]=toCityCost[7][1]=146;
        toCityCost[2][8]=toCityCost[8][2]=211;
        toCityCost[2][5]=toCityCost[5][2]=101;
        toCityCost[3][4]=toCityCost[4][3]=70;
        toCityCost[4][6]=toCityCost[6][4]=111;
        toCityCost[5][7]=toCityCost[7][5]=97; // notice that for each edge G[i][j] == G[j][i]
        toCityCost[6][10]=toCityCost[10][6]=118;
        toCityCost[7][9]=toCityCost[9][7]=80;





 toCityCost[8][9]=toCityCost[9][8]=99;
         toCityCost[9][10]=toCityCost[10][9]=140;
         toCityCost[9][12]=toCityCost[12][9]=151;
         toCityCost[10][11]=toCityCost[11][10]=75;
         toCityCost[11][12]=toCityCost[12][11]=71;
         }

         // perform a AStar search on the aforementioned graph
         String retCity(int i) { // the function returns city name, according to its index in V array
         if(i==0)
         return "Dobreta";
         else if(i==1)
         return "Craiova";
         else if(i==2)
         return "Bucharest";
         else if(i==3)
         return "Mehadia";
         else if(i==4)
         return "Lugoj";
         else if(i==5)
         return "Pitesti";
         else if(i==6)
         return "Timisoara";
         else if(i==7)
         return "Rimnicu Vilcea";
         else if(i==8)
         return "Fagaras";
         else if(i==9)
         return "Sibiu";
         else if(i==10)
         return "Arad";
         else if(i==11)
         return "Zerind";
         else
         return "Oradea";
         }
         }