import java.util.*;

public class AStar {
    int N; // number of vertices in the graph
    boolean[][] G;
    double [][] D;// disdance
    int[] estCost;
    int[] estCost_to_SH; // estimated cost to the goal
    int[] estCost_to_COE; // estimated cost to the goal
    int[] estCost_to_COP; // estimated cost to the goal
    int[][] tobuildingCost; // The cost among cities
    int[] funcGCost; // The cost so far to reach a building
    int[] funcFCost; // The estimated total cost through a building to the goal
    boolean[] calc; // To determine whether the path cost to a building has been calculated or not
    List<Integer> list; // list represents the nodes which have to be expanded
    int goal;
    int start;

    public static void main(String[] args) {
        new AStar();
    }

    AStar() { // The constructor
        N = 17;

        G = new boolean[N][N];
        setupGraph();
        setupDistance();
        estCost = new int[N];
        estCost_to_COE = new int[N];
        estCost_to_COP = new int[N];
        estCost_to_SH = new int[N];
        


        
        Scanner inp = new Scanner(System.in);
        System.out.println("\n\nChoose a building number to start with: \n");
        for (int i = 0; i < 17; i++)// shows the list of cities
            System.out.println(retbuilding(i) + " building[" + i + "]");
        System.out.print("\nInput: ");
        start = inp.nextInt();// User's choice of the list

        if (start < 0 || start >= 17)// the user choice is not included in the list
        {
            System.out.println("Mistake,run the program again");
            System.exit(0);
        }
        System.out.println("\n\n");

        System.out.println("\n\nChoose a building number to end with: \n");
        for (int i = 0; i < 17; i++)// shows the list of cities
            System.out.println(retbuilding(i) + " building[" + i + "]");
        System.out.print("\nInput: ");
        goal = inp.nextInt();// User's choice of the list

        if (goal < 0 || goal >= 17)// the user choice is not included in the list
        {
            System.out.println("Mistake,run the program again");
            System.exit(0);
        }
        

        setupEstCost();
        
        tobuildingCost = new int[N][N]; // cost among cities
        btwCities();
        funcGCost = new int[N]; // The cost so far to reach a building
        funcFCost = new int[N]; // The estimated total cost through a building to the goal
        calc = new boolean[N];
        list = new ArrayList<Integer>();

        System.out.println("------------------------------");
        System.out.println();
        funcGCost[start] = 0;
        assignFCost(start);
        calc[start] = true;
        list.add(start);

        // perform A* search algorithm on the graph
        AStar(start); // Arad building is considered to be the start location

        System.out.println();
        System.out.println();
    }

    void compTwoCities(int from, int to) {

        int newR = hFunc(to);
        if (newR < funcFCost[to])
            funcFCost[to] = newR;
    }

    public void AStar(int Loc) {
        System.out.println("The current location: " + retbuilding(Loc));
        System.out.println("H(" + hFunc(Loc) + ")\n");

        if (Loc == goal) { // Bucharest location = 2
            System.out.println("The goal has been reached");
            return;
        }

        for (int i = 0; i < N; i++)
            if (G[Loc][i] == true && list.contains(i))
                compTwoCities(Loc, i);
            else if (G[Loc][i] == true) {
                list.add(i);
                assignGCost(Loc, i);
                assignFCost(i);
            }
        list.remove((Object) (new Integer(Loc)));

        System.out.println("---------------Expansion--------------");
        for (int i : list) {
            System.out.println("|" + retbuilding(i) + "= H(" + hFunc(i) + ")|");
        }
        System.out.println("----------------------------------------\n\n");

        int dest = list.get(0);
        for (int i = 1; i < list.size(); i++)
            if (retFCost(list.get(i)) < retFCost(dest))
                dest = list.get(i);
        AStar(dest);
    }

    // initial setup of the graph
    public void setupGraph() {
        G[0][1] = G[1][0] = true; // notice that for each edge G[i][j] == G[j][i]
        G[0][2] = G[2][0] = true; // this means that the graph is undirected
        G[0][3] = G[3][0] = true;
        G[0][4] = G[4][0] = true;
        G[1][2] = G[2][1] = true;
        G[1][7] = G[7][1] = true;
        G[2][3] = G[3][2] = true;
        G[2][5] = G[5][2] = true;
        G[3][8] = G[8][3] = true;
        G[5][6] = G[6][5] = true;
        G[7][10] = G[10][7] = true;
        G[8][9] = G[9][8] = true;
        G[9][12] = G[12][9] = true;
        G[10][16] = G[16][10] = true;
        G[10][14] = G[14][10] = true;
        G[10][11] = G[11][10] = true;
        G[11][12] = G[12][11] = true;
        G[11][13] = G[13][11] = true;
        G[14][15] = G[15][14] = true;
        
    }

    public void setupDistance(){
        D = new double[17][2];
        D[0][0]=26.358416;D[0][1] = 43.765968;
        D[1][0]=26.350639;D[1][1] = 43.765972;
        D[2][0]=26.350982;D[2][1] = 43.766832;
        D[3][0]=26.350544;D[3][1] = 43.767742;
        D[4][0]=26.351468;D[4][1] = 43.774361;
        D[5][0]=26.350395;D[5][1] = 43.766733;
        D[6][0]=26.348803;D[6][1] = 43.767049;
        D[7][0]=26.349347;D[7][1] = 43.765165;
        D[8][0]=26.349236;D[8][1] = 43.768625;
        D[9][0]=26.347386;D[9][1] = 43.767385;
        D[10][0]=26.348401;D[10][1] = 43.764751;
        D[11][0]=26.347167;D[11][1] = 43.766012;
        D[12][0]=26.347134;D[12][1] = 43.767424;
        D[13][0]=26.346869;D[13][1] = 43.766157;
        D[14][0]=26.345740;D[14][1] = 43.764488;
        D[15][0]=26.344790;D[15][1] = 43.764778;
        D[16][0]=26.345242;D[16][1] = 43.760455;
    }

    public void setupEstCost() {
        // estCost_to_SH[0] = 1550; // 
        // estCost_to_SH[1] = 780; // 
        // estCost_to_SH[2] = 870; // 
        // estCost_to_SH[3] = 930; // 
        // estCost_to_SH[4] = 1500; // 
        // estCost_to_SH[5] = 845; // 
        // estCost_to_SH[6] = 735; // 
        // estCost_to_SH[7] = 630; // 
        // estCost_to_SH[8] = 910; // 
        // estCost_to_SH[9] = 745; // 
        // estCost_to_SH[10] = 540; // 
        // estCost_to_SH[11] = 595; // 
        // estCost_to_SH[12] = 750; // 
        // estCost_to_SH[13] = 590; // 
        // estCost_to_SH[14] = 425; // 
        // estCost_to_SH[15] = 445; // 
        // estCost_to_SH[16] = 0;

        // estCost_to_COE[0] = 1320; // 
        // estCost_to_COE[1] = 350; // 
        // estCost_to_COE[2] = 390; // 
        // estCost_to_COE[3] = 400; // 
        // estCost_to_COE[4] = 905; // 
        // estCost_to_COE[5] = 360; // 
        // estCost_to_COE[6] = 180; // 
        // estCost_to_COE[7] = 290; // 
        // estCost_to_COE[8] = 335; // 
        // estCost_to_COE[9] = 145; // 
        // estCost_to_COE[10] = 170; // 
        // estCost_to_COE[11] = 0; // 
        // estCost_to_COE[12] = 150; // 
        // estCost_to_COE[13] = 50; // 
        // estCost_to_COE[14] = 230; // 
        // estCost_to_COE[15] = 300; // 
        // estCost_to_COE[16] = 600;

        // estCost_to_COP[0] = 930; // 
        // estCost_to_COP[1] = 180; // 
        // estCost_to_COP[2] = 110; // 
        // estCost_to_COP[3] = 0; // 
        // estCost_to_COP[4] = 610; // 
        // estCost_to_COP[5] = 100; // 
        // estCost_to_COP[6] = 215; // 
        // estCost_to_COP[7] = 330; // 
        // estCost_to_COP[8] = 155; // 
        // estCost_to_COP[9] = 355; // 
        // estCost_to_COP[10] = 390; // 
        // estCost_to_COP[11] = 425; // 
        // estCost_to_COP[12] = 380; // 
        // estCost_to_COP[13] = 455; // 
        // estCost_to_COP[14] = 630; // 
        // estCost_to_COP[15] = 710; // 
        // estCost_to_COP[16] = 940; //
        for(int i = 0 ; i < N; i++){
            estCost[i]=distance(D[start][0],D[start][1],D[i][0],D[i][1],"K");
        }
    }

    private static int distance(double lat1, double lon1, double lat2, double lon2, String unit) {
		if ((lat1 == lat2) && (lon1 == lon2)) {
			return 0;
		}
		else {
			double theta = lon1 - lon2;
			double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
			dist = Math.acos(dist);
			dist = Math.toDegrees(dist);
			dist = dist * 60 * 1.1515;
			if (unit == "K") {
				dist = dist * 1.609344;
			} else if (unit == "N") {
				dist = dist * 0.8684;
            }
            
            int Dest= (int)Math.round(dist*1000);
			return  Dest;
		}
	}

    public int hFunc(int i) {

        // if (goal == 16)
        //     return estCost_to_SH[i];
        // else if (goal == 3)
        //     return estCost_to_COP[i];


        return estCost[i];
    }

    public int btwCost(int first, int second) {
        return tobuildingCost[first][second];
    }

    public void assignGCost(int from, int to) {
        funcGCost[to] = retGCost(from) + btwCost(from, to);
    }

    public int retGCost(int building) {
        return funcGCost[building];
    }

    public void assignFCost(int building) {
        funcFCost[building] = hFunc(building);
    }

    public int retFCost(int building) {
        return funcFCost[building];
    }

    void btwCities() {
        tobuildingCost[0][1] = tobuildingCost[1][0] = distance(D[0][0],D[0][1],D[1][0],D[1][1],"K"); // notice that for each edge tobuildingCost[i][j] == tobuildingCost[j][i]
        tobuildingCost[0][2] = tobuildingCost[2][0] = distance(D[0][0],D[0][1],D[2][0],D[2][1],"K"); // this means that the graph is undirected
        tobuildingCost[0][3] = tobuildingCost[3][0] = distance(D[0][0],D[0][1],D[3][0],D[3][1],"K");
        tobuildingCost[0][4] = tobuildingCost[4][0] = distance(D[0][0],D[0][1],D[4][0],D[4][1],"K");
        tobuildingCost[1][2] = tobuildingCost[2][1] = distance(D[1][0],D[1][1],D[2][0],D[2][1],"K");
        tobuildingCost[1][7] = tobuildingCost[7][1] = distance(D[1][0],D[1][1],D[7][0],D[7][1],"K");
        tobuildingCost[2][3] = tobuildingCost[3][2] = distance(D[2][0],D[2][1],D[3][0],D[3][1],"K");
        tobuildingCost[2][5] = tobuildingCost[5][2] = distance(D[2][0],D[2][1],D[5][0],D[5][1],"K");
        tobuildingCost[3][8] = tobuildingCost[8][3] = distance(D[3][0],D[3][1],D[8][0],D[8][1],"K");
        tobuildingCost[5][6] = tobuildingCost[6][5] = distance(D[5][0],D[5][1],D[6][0],D[6][1],"K");
        tobuildingCost[7][10] = tobuildingCost[10][7] = distance(D[7][0],D[7][1],D[10][0],D[10][1],"K");
        tobuildingCost[8][9] = tobuildingCost[9][8] = distance(D[8][0],D[8][1],D[9][0],D[9][1],"K");
        tobuildingCost[9][12] = tobuildingCost[12][9] = distance(D[12][0],D[12][1],D[9][0],D[9][1],"K");
        tobuildingCost[10][16] = tobuildingCost[16][10] = distance(D[10][0],D[10][1],D[16][0],D[16][1],"K");
        tobuildingCost[10][14] = tobuildingCost[14][10] = distance(D[10][0],D[10][1],D[14][0],D[14][1],"K");
        tobuildingCost[10][11] = tobuildingCost[11][10] = distance(D[10][0],D[10][1],D[11][0],D[11][1],"K");
        tobuildingCost[11][12] = tobuildingCost[12][11] = distance(D[12][0],D[12][1],D[11][0],D[11][1],"K");
        tobuildingCost[11][13] = tobuildingCost[13][11] = distance(D[13][0],D[10][1],D[13][0],D[11][1],"K");
        tobuildingCost[14][15] = tobuildingCost[15][14] = distance(D[14][0],D[14][1],D[15][0],D[15][1],"K");
    }
    // perform a AStar search on the aforementioned graph
    String retbuilding(int i) { // the function returns building name, according to its index in V array
        if (i == 0)
            return "COC";
        else if (i == 1)
            return "COD";
        else if (i == 2)
            return "COAV";
        else if (i == 3)
            return "COP";
        else if (i == 4)
            return "H";
        else if (i == 5)
            return "COM";
        else if (i == 6)
            return "QU";
        else if (i == 7)
            return "COAL";
        else if (i == 8)
            return "COB";
        else if (i == 9)
            return "COS";
        else if (i == 10)
            return "COSAIS";
        else if (i == 11)
            return "COE";
        else if (i == 12)
            return "COAM";
        else if (i == 13)
            return "COAP";
        else if (i == 14)
            return "SEU";
        else if (i == 15)
            return "COED";

        else
            return "SH";
    }
}