import java.util.*;

public class TRA {
    int N; // number of vertices in the graph
    boolean[][] G; // the graph as an adjacency matrix
    // G[i][j] is true if there is an edge from i to j
    int currPos;

    TRA(int size, int loc) {
        N = size;
        currPos = loc;
        setupGraph();
        System.out.println("----------Traverse-------------");
        System.out.println();
    }

    void setupGraph() {
        // set up a graph with 8 vertices that looks like:

        G = new boolean[N][N];

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

    void TRA() {
        System.out.println("The current location is: " + retCity(currPos) + "\n");
        System.out.println("Moving to the next destination: \n..[0] "
                + "manually\n..[1] First In First Out (FIFO)\n..[2] Last In First Out (LIFO)]\n.."
                + " [3] DFS \n [4] BFS\n [5] DLS \n[-1] Stop");
        Scanner inp = new Scanner(System.in);
        int ch = inp.nextInt();

        switch (ch) // The user chooses the way to traverse the graph
        {
        case -1: // Stop
            System.out.println("The program's been stopped");
            break;

        case 0:
            List<Integer> list = new ArrayList<Integer>();
            for (int i = 0; i < N; i++)
                if (G[currPos][i] == true)
                    list.add(i);

            System.out.println("\nChoose one of the following connected building: ");
            for (int i = 0; i < list.size(); i++)
                System.out.println("[" + i + "]" + retCity(list.get(i)) + "\t");
            System.out.println("\nInput: ");
            ch = inp.nextInt();

            if (ch < 0 || ch >= list.size()) {
                System.out.println("The choice is not correct,try again");
                TRA();
            }
            currPos = list.get(ch);
            TRA();
            break;

        case 1: // FIFO
            String addToQ = "[(Front) ";
            Queue<Integer> Q = new LinkedList<Integer>(); // A queue to process nodes
            for (int i = 0; i < N; i++)
                if (G[currPos][i] == true) {
                    Q.offer(i);// The connected cities are added to the queue
                    addToQ += retCity(i) + "\t";
                }
            addToQ += "]\n";

            System.out.println(addToQ);
            System.out.println(retCity(Q.peek()) + " is the destination\n");
            currPos = Q.peek();
            TRA();
            break;

        case 2: // LIFO
            String addToSta = "";
            Stack<Integer> sta = new Stack<Integer>();
            addToSta += "[";
            for (int i = 0; i < N; i++)
                if (G[currPos][i] == true) {
                    sta.push(i);// The connected cities are added to the statck
                    addToSta += retCity(i) + "\t";
                }
            addToSta += "top=>]";
            System.out.println(addToSta);
            currPos = sta.peek();

            TRA();
            break;

        case 3: // Depth-first search
            Stack<Integer> stack = new Stack<Integer>();
            List<Integer> visited = new ArrayList<Integer>();
            stack.add(currPos);
            int goal = getGoal();

            while (!stack.empty()) {
                currPos = stack.pop();
                System.out.println("Current location is " + retCity(currPos));

                if (currPos == goal) {
                    System.out.println("You have reached your goal building!!!\n");
                    System.exit(0);
                }

                visited.add(currPos);

                
                System.out.println("Connected building are:\n");
                for (int i = 0; i < N; i++)
                    if (G[currPos][i] == true && !visited.contains(i)){
                        System.out.println(retCity(i));
                        stack.push(i);
                    }

            }

        case 4: // Bredth-first search

            Queue<Integer> queue = new LinkedList<Integer>();
            List<Integer> qvisited = new ArrayList<Integer>();
            queue.add(currPos);
            int qgoal = getGoal();
            

            while (!queue.isEmpty()) {
                currPos = queue.remove();
                System.out.println("Current location is " + retCity(currPos));

                if (currPos == qgoal) {
                    System.out.println("You have reached your goal building!!!\n");
                    System.exit(0);
                }

                System.out.println("Connected building are:\n");
                for (int i = 0; i < N; i++) {
                    
                    if (G[currPos][i] == true && !qvisited.contains(i)) {
                        System.out.println(retCity(i));
                        queue.add(i);
                        qvisited.add(i);
                    }

                }

            }

        case 5:

            DLS();

        }


      
    }

    public void DLS() {
        Stack<Integer> stack = new Stack<Integer>();
        List<Integer> visited = new ArrayList<Integer>();
        int MAX_DEPTH = 3;
        int depth = 0;
        MAX_DEPTH = getLimit() ;
        stack.add(currPos);
        int goal = getGoal();
        

        while (!stack.empty()) {
            currPos = stack.pop();
            depth++;
            System.out.println("Current location is " + retCity(currPos));
            System.out.println("Current depth is " + get_level(currPos));

            if (currPos == goal) {
                System.out.println("You have reached your goal building!!!\n");
                System.exit(0);
            }
            visited.add(currPos);
            if (get_level(currPos) >= MAX_DEPTH) {
                continue;
            }
            System.out.println("Connected building are:\n");
            for (int i = 0; i < N; i++) {
                if (G[currPos][i] == true && !visited.contains(i)){
                    System.out.println(retCity(i));
                    stack.push(i);
                    visited.add(i);

                   
                }
                
            }
        }
    }

    public int  get_level(int current_pos) {

        if (current_pos == 0)
            return 0;

        int level = 0;

        
            
        for (int i = 0; i < current_pos; i++) {
            
            if (G[i][current_pos] == true) {

                // System.out.println(retCity(i) + "->" + retCity(current_pos));

                current_pos = i;
                i = -1;
                level++;
            }
        }

        return level;
        
    }

    public static void main(String[] args) {
        int cityChoice;
        Scanner inp = new Scanner(System.in);
        System.out.println("\n\nChoose a building number to start with: \n");
        for (int i = 0; i < 17; i++)// shows the list of cities
            System.out.println(retCity(i) + " building[" + i + "]");
        System.out.print("\nInput: ");
        cityChoice = inp.nextInt();// User's choice of the list

        if (cityChoice < 0 || cityChoice >= 17)// the user choice is not included in the list
        {
            System.out.println("Mistake,run the program again");
            System.exit(0);
        }

        TRA traGraph = new TRA(17, cityChoice);
        traGraph.TRA();
    }

    // the function returns city name, according to its index in V
    // array

    public static String retCity(int i) {
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

    public static int getGoal() {
        Scanner inpGoal = new Scanner(System.in);
        System.out.println("\n\nChoose a building number to reacg: \n");
        for (int i = 0; i < 17; i++)// shows the list of cities
            System.out.println(retCity(i) + " building[" + i + "]");
        System.out.print("\nInput: ");
        int goal = inpGoal.nextInt();// User's choice of the list

        if (goal < 0 || goal >= 17)// the user choice is not included in the list
        {
            System.out.println("Mistake,run the program again");
            System.exit(0);
        }

        
        return goal;
    }

    public static int getLimit() {
        Scanner inpGoal = new Scanner(System.in);
        System.out.println("\n\nChoose a Limit: \n");

        int goal = inpGoal.nextInt();// User's choice of the list

        
        return goal;
    }




}