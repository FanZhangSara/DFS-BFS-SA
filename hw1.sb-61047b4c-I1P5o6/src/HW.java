import java.io.*;
import java.util.*;
public class HW {
    public static int  numl;
    public static boolean flag;
    public static String s;
    public static int[][] fin;
    public static long startTime;

    public static class
    node {
        int val;
        int number;
        int x;
        int y;
        node(int x){ val = x;}
        int [][] board;
    }

    public static void main(String[] args) throws Exception {
        int[][] board = readFileByLines("input.txt");
        // DFS
        if(s.equals("DFS"))
        {
            int m = board.length;
            flag = false;
            int [][] res = new int[m][m];
            if(dfs(board, 0,0, 0, numl, res)){
                System.out.println("OK");
                for(int i = 0; i < board.length;i++){
                    for(int j = 0; j< board[0].length;j++)
                        System.out.print(board[i][j]);
                    System.out.println();
                }
                output(board);
            }
            else{
                System.out.println("FAIL");
                outputfail();
            }
        }
        //BFS
        if(s.equals("BFS")){
            if(bfs(board,  numl))
            {
                System.out.println("OK");
                for (int i = 0; i < fin.length; i++)
                {
                    for (int j = 0; j < fin[0].length; j++)
                        System.out.print(fin[i][j]);
                    System.out.println();
                }
                output(fin);
            }
            else{
                System.out.print("FAIL");
                outputfail();
            }
        }
        //SA
        if(s.equals("SA")){
            startTime = System.currentTimeMillis();
            sa(board, numl);

            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++)
                    if(board[i][j]==1 && !validate(board,i,j)){
                        System.out.println("FAIL");
                        outputfail();
                        return;
                    }
            }
            System.out.println("OK");
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++)
                    System.out.print(board[i][j]);
                System.out.println();
            }
            output(board);
//            File file = new File("D:"+ File.separator+"output.txt");
//            OutputStream out = null;
//            out = new FileOutputStream(file);
//            out.write(board);
        }

    }

    public static void outputfail() throws  Exception {
        File file = new File("output.txt");
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("FAIL");
        bw.close();
        fw.close();
    }

    public static void output(int[][] board) throws  Exception{
        File file = new File("output.txt");
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("OK");
        bw.newLine();
        for(int[] v : board){
            for(int d: v){
                bw.write(String.valueOf(d));
            }
            bw.newLine();
        }
        bw.close();
        fw.close();
//       String filepath = "D:/output.txt";
//       OutputStream out = new FileOutputStream(filepath);
//       ObjectOutputStream oout = new ObjectOutputStream(out);
//       oout.writeObject(board);
    }

    public static int[][] readFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 4;
            s = reader.readLine();  //first
            //System.out.println(s);
            int n = Integer.parseInt(reader.readLine());  //second
            //System.out.println(n);
            int p = Integer.parseInt(reader.readLine());  //third
            //System.out.println(p);
            numl = p;
            int[][] board = new int[n][n];

            while ((tempString = reader.readLine()) != null) {
                for(int i = 0; i < n ;i++){
                    board[line-4][i] = Integer.parseInt(tempString.substring(i,i+1));
                }
                line++;
            }
            reader.close();
            return board;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return new int[0][0];
    }


    private static boolean dfs(int[][] board, int x, int y, int num, int p, int[][] res) {
        if(flag)
            return true;
        if (num == p) {
            for(int i = 0;i < board.length;i++){
                for(int j = 0; j < board[0].length; j++){
                    res[i][j] = board[i][j];
                }
            }
            flag = true;
            return true;
        }
        if (x >= board.length || y >= board[0].length)
            return false;
        for (int i = x; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if(i==x&&j<y)
                    continue;
                if (validate(board, i, j)) {
                    board[i][j] = 1;
                    if (j == board[0].length - 1)
                        dfs(board, i+1, 0, num + 1, p, res);
                    else
                        dfs(board, i, j + 1, num + 1, p, res);
                    if(flag) {
                        return true;
                    }
                    board[i][j] = 0;
                }
            }
        }
        return false;
    }

    private static boolean bfs(int[][] board, int p) {
        Queue<node> q = new LinkedList<node>();
        for(int i = 0 ; i < board.length;i++){
            for(int j = 0; j < board[0].length; j++){
                if(board[i][j]!=2){
                    node n = new node(1);
                    n.number=1;
                    n.x = i;
                    n.y = j;
                    int i2;
                    n.board = new int[board.length][board.length];
                    for(int a = 0;a<board.length;a++){
                        for(i2= 0;i2< board[0].length; i2++)
                            n.board[a][i2] = board[a][i2];
                    }
                    //n.board=board;
                    n.board[i][j] = 1;
                    q.add(n);
                }
            }
        }
        while(!q.isEmpty()){
            node n = q.poll();

            if(n.number == p){
                fin = n.board;
                return true;}

            for(int i = n.x; i < board.length; i++){
                for(int j = 0; j < board[0].length;j++){
                    if(i==n.x && j<= n.y)
                        continue;
                    if(board[i][j]!=2 &&validate(n.board,i,j)){
                        node newNode = new node(1);
                        newNode.x = i;
                        newNode.y = j;

                        newNode.number = n.number+1;
                        int i2;
                        newNode.board = new int[board.length][board.length];
                        for(int a = 0;a<n.board.length;a++){
                            for(i2= 0;i2< n.board[0].length; i2++)
                                newNode.board[a][i2] = n.board[a][i2];
                        }

                        //newNode.board =n.board;
                        newNode.board[i][j] = 1;
                        q.add(newNode);
                    }
                }
            }
        }

        return false;
    }


    private static void sa(int[][] board, int p) {
        int len = board.length;
        int existnum =0;
        List<node> nums = new ArrayList<node>();
        while(existnum < p){
            int i = new Random().nextInt(len);
            int j = new Random().nextInt(len);
            if(board[i][j]==0){
                board[i][j] = 1;
                node n = new node(existnum);
                existnum++;
                n.x = i;
                n.y = j;
                nums.add(n);
            }
        }
        //System.out.println();
//        for (int i = 0; i < board.length; i++) {
//            for (int j = 0; j < board[0].length; j++)
//                System.out.print(board[i][j]);
//            System.out.println();
//        }
//        System.out.println();

        double temperature = 100;
        int curState = Integer.MAX_VALUE;
        while(curState!=0){
            long endTime = System.currentTimeMillis();
            long diff = endTime - startTime;
            if(diff > 290000){
                return;
            }
            //choose a queen ramdomly
            int ram = (int)(Math.random()*nums.size());
            int m = nums.get(ram).x;
            int n = nums.get(ram).y;  //(x,y)
            // int val = list.get(m).val;
            curState = calculateCost(board);
            if(curState==0)
                return;

            int i = new Random().nextInt(len);
            int j = new Random().nextInt(len);
            if(board[i][j]==2 || board[i][j]==1)
                continue;
            if(board[i][j]==0){
                board[i][j] = 1;
                board[m][n] = 0;
            }
            int nextState = calculateCost(board);

            boolean better = false;
            int delta = nextState - curState;
            if(delta<0){
                better = true;
            }else if( Math.pow(Math.E,(-1)*delta/temperature) > Math.random())
                better = true;
            if(better)  //put queen
            {
                nums.get(ram).x = i;
                nums.get(ram).y = j;
            }
            else{
                board[m][n]=1;
                board[i][j]=0;
            }


            temperature *= 0.99;

        }
    }


    private static int calculateCost(int[][] board) {

        int number = 0;
        for(int x = 0;x < board.length;x++){
            for(int y = 0; y < board[0].length; y++){
                if(board[x][y] == 1){

                    for (int i = y - 1; i >= 0; i--) {
                        if (board[x][i] == 1){
                            number++;
                        }
                        if (board[x][i] == 2)
                            break;
                    }
                    for (int i = y + 1; i < board.length; i++) {
                        if (board[x][i] == 1){
                            number++;
                        }
                        if (board[x][i] == 2)
                            break;
                    }

                    for (int i = x - 1; i >= 0; i--) {
                        if (board[i][y] == 1)
                            number++;
                        if (board[i][y] == 2)
                            break;
                    }
                    for (int i = x + 1; i < board.length; i++) {
                        if (board[i][y] == 1)
                            number++;
                        if (board[i][y] == 2)
                            break;
                    }

                    for (int i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--) {
                        if (board[i][j] == 1)
                            number++;
                        if (board[i][j] == 2)
                            break;
                    }
                    for (int i = x + 1, j = y + 1; i < board.length && j < board[0].length; i++, j++) {
                        if (board[i][j] == 1)
                            number++;
                        if (board[i][j] == 2)
                            break;
                    }

                    for (int i = x - 1, j = y + 1; i >= 0 && j < board[0].length; i--, j++) {
                        if (board[i][j] == 1)
                            number++;
                        if (board[i][j] == 2)
                            break;
                    }
                    for (int i = x + 1, j = y - 1; i < board.length && j >= 0; i++, j--) {
                        if (board[i][j] == 1)
                            number++;
                        if (board[i][j] == 2)
                            break;
                    }
                }
            }
        }

        return number;
    }

    private static boolean validate(int[][] board, int x, int y) {

        if(board[x][y]==2)
            return false;
        for(int i = y-1; i >= 0; i--){
            if(board[x][i]==1)
                return false;
            if(board[x][i]==2)
                break;
        }
        for(int i = x-1; i>=0; i--){
            if(board[i][y]==1)
                return false;
            if(board[i][y]==2)
                break;
        }
        for(int i = x-1,j = y-1; i >=0 &&j>=0;i--,j--){
            if(board[i][j]==1)
                return false;
            if(board[i][j]==2)
                break;
        }
        for(int i = x-1,j = y+1; i>=0 && j < board[0].length;i--,j++){
            if(board[i][j]==1)
                return false;
            if(board[i][j]==2)
                break;
        }

        return true;
    }

}

