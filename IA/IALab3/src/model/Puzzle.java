import java.util.LinkedList;
import java.util.PriorityQueue;

public class Puzzle {

    final static int BLANK = 0;
    final static int BOARDWIDTH = 3;

    public Puzzle() {
    }

    private class Node implements Comparable<Node> {
        Node parent;
        String dir;
        int[][] board;
        float f, g, h;

        @Override
        public int compareTo(Node o) {
            // TODO Auto-generated method stub
            if (this.f > o.f)
                return 1;
            else if (this.f < o.f)
                return -1;
            return 0;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            Node curr = this;
            int numMoves = 0;
            LinkedList<Node> q = new LinkedList<Node>();
            // Reverse the order of nodes
            while (curr != null) {
                // curr = curr.parent;
                q.addLast(curr);
                curr = curr.parent;
                numMoves++;
            }
            // Print every node and the direction taken
            while (!q.isEmpty()) {
                Node temp = q.removeLast();
                if (temp.dir != null)
                    sb.append(temp.dir);
                sb.append("\n");
                sb.append(printBoard(temp));
                sb.append("\n");
            }
            // Number of moves
            sb.append("Number of moves: " + numMoves);
            return sb.toString();
        }

        private String printBoard(Node curr) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < BOARDWIDTH; i++) {
                for (int j = 0; j < BOARDWIDTH; j++) {
                    sb.append(curr.board[i][j]);
                }
                sb.append("\n");
            }

            return sb.toString();
        }
    }

    public Node aStar(int[][] start, int[][] goal) {
        Node n = new Node();
        n.board = start;
        n.f = 0;
        n.g = 0;
        n.h = cost(n.board, goal);
        n.f = n.g + n.h;
        LinkedList<Node> closedSet = new LinkedList<Node>(); // The set of nodes

        PriorityQueue<Node> openSet = new PriorityQueue<Node>(); // The set of

        openSet.offer(n);
        while (!openSet.isEmpty()) {
            // take node with lowest f score
            Node q = openSet.poll();

            if (same(q.board, goal))
                return q;

            closedSet.add(q);

            LinkedList<Node> successors = successors(q);
            for (Node s : successors) {

                if (same(s.board, goal)) {
                    return s;
                }

                float tentG = q.g + cost(q.board, s.board);
                float tentF = tentG + cost(s.board, goal);

                boolean closedSkip = false;
                for (Node c : closedSet) {
                    if (same(s.board, c.board) && c.f <= tentF) {
                        closedSkip = true;
                        break;
                    }
                }
                if (closedSkip)
                    continue;

                boolean notInOpen = true;
                boolean smaller = true;

                for (Node o : openSet) {
                    if (same(s.board, o.board)) {
                        notInOpen = false;
                        break;
                    }
                }

                for (Node o : openSet) {
                    if (s.f < o.f) {
                        smaller = true;
                        break;
                    }
                }

                if (notInOpen || smaller) {
                    // fix fields accordingly
                    s.parent = q;
                    s.g = tentG;
                    s.f = tentF;

                    if (notInOpen)
                        openSet.offer(s);

                }
            }
        }
        return null;
    }

    private boolean same(int[][] board, int[][] goal2) {
        for (int i = 0; i < BOARDWIDTH; i++) {
            for (int j = 0; j < BOARDWIDTH; j++) {
                if (board[i][j] != goal2[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private LinkedList<Node> successors(Node q) {
        int x = 0;
        int y = 0;


        int[] indices = indexOf(q.board, BLANK);
        x = indices[0];
        y = indices[1];

        LinkedList<Node> successors = new LinkedList<Node>();

        int[][] s_up = new int[BOARDWIDTH][BOARDWIDTH];
        int[][] s_down = new int[BOARDWIDTH][BOARDWIDTH];
        int[][] s_left = new int[BOARDWIDTH][BOARDWIDTH];
        int[][] s_right = new int[BOARDWIDTH][BOARDWIDTH];

        // initialise all four matrices
        for (int i = 0; i < BOARDWIDTH; i++) {
            for (int j = 0; j < BOARDWIDTH; j++) {
                s_up[i][j] = q.board[i][j];
            }
        }

        for (int i = 0; i < BOARDWIDTH; i++) {
            for (int j = 0; j < BOARDWIDTH; j++) {
                s_down[i][j] = q.board[i][j];
            }
        }

        for (int i = 0; i < BOARDWIDTH; i++) {
            for (int j = 0; j < BOARDWIDTH; j++) {
                s_left[i][j] = q.board[i][j];
            }
        }

        for (int i = 0; i < BOARDWIDTH; i++) {
            for (int j = 0; j < BOARDWIDTH; j++) {
                s_right[i][j] = q.board[i][j];
            }
        }

        int temp = 0;
        if (x > BLANK) {
            temp = s_up[x][y];
            s_up[x][y] = s_up[x - 1][y];
            s_up[x - 1][y] = temp;
            Node n = new Node();
            n.board = s_up;
            n.parent = q;
            n.dir = "UP";
            successors.add(n);
        }
        // down
        if (x < BOARDWIDTH - 1) {
            temp = s_down[x][y];
            s_down[x][y] = s_down[x + 1][y];
            s_down[x + 1][y] = temp;
            Node n = new Node();
            n.board = s_down;
            n.parent = q;
            n.dir = "DOWN";
            successors.add(n);
        }
        // left
        if (y > BLANK) {
            temp = s_left[x][y];
            s_left[x][y] = s_left[x][y - 1];
            s_left[x][y - 1] = temp;
            Node n = new Node();
            n.board = s_left;
            n.parent = q;
            n.dir = "LEFT";
            successors.add(n);
        }
        // right
        if (y < BOARDWIDTH - 1) {
            temp = s_right[x][y];
            s_right[x][y] = s_right[x][y + 1];
            s_right[x][y + 1] = temp;
            Node n = new Node();
            n.board = s_right;
            n.parent = q;
            n.dir = "RIGHT";
            successors.add(n);
        }
        return successors;
    }

    private int cost(int[][] from, int[][] to) {
        int sum = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (from[i][j] == BLANK)
                    continue;
                int[] indexFrom = indexOf(from, from[i][j]);
                int[] indexTo = indexOf(to, from[i][j]);
                sum += Math.abs(indexFrom[0] - indexTo[0])
                        + Math.abs(indexFrom[1] - indexTo[1]);
            }
        }
        return sum;
    }

    private int[] indexOf(int[][] board, int tile) {
        int[] index = new int[2];
        for (int i = 0; i < BOARDWIDTH; i++) {
            for (int j = 0; j < BOARDWIDTH; j++) {
                if (board[i][j] == tile) {
                    index[0] = i;
                    index[1] = j;
                    return index;
                }
            }
        }
        return null;
    }

    private boolean solvable(int[][] puzzle) {
        int[] oneDim = new int[BOARDWIDTH * BOARDWIDTH];
        int k = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                oneDim[k] = puzzle[i][j];
                k++;
            }
        }

        int inversions = 0;
        for (int i = 0; i < oneDim.length; i++) {
            for (int j = i; j < oneDim.length; j++) {
                if (oneDim[i] == 0)
                    break;
                if (oneDim[i] < oneDim[j])
                    inversions++;
            }
        }

        if (puzzle[0].length % 2 != 0)
            return (inversions % 2 == 0);


        return true;
    }

    public static void main(String[] args) {
        Puzzle p = new Puzzle();


        int[][] puzzle = { { 8, 0, 6 }, { 4, 5, 7 }, { 2, 1, 3 } };
        int[][] goal = { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 } };

        if (!p.solvable(puzzle)) {
            System.err.println("Board is not solvable!");
        } else {
            System.out.println(p.aStar(puzzle, goal));
        }
    }

}