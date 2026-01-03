import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n;
    private final boolean[][] open;
    private final WeightedQuickUnionUF uf;
    private final int virtualTop;
    private final int virtualBottom;
    private int openCount;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();

        this.n = n;
        this.open = new boolean[n][n];
        this.uf = new WeightedQuickUnionUF(n * n + 2);
        this.virtualTop = n * n;
        this.virtualBottom = n * n + 1;
        this.openCount = 0;
    }

    public void open(int row, int col) {
        validate(row, col);

        int r = row - 1;
        int c = col - 1;

        if (open[r][c]) return;

        open[r][c] = true;
        openCount++;

        int index = r * n + c;

        if (r > 0 && open[r - 1][c]) uf.union(index, (r - 1) * n + c);
        if (r < n - 1 && open[r + 1][c]) uf.union(index, (r + 1) * n + c);
        if (c > 0 && open[r][c - 1]) uf.union(index, r * n + (c - 1));
        if (c < n - 1 && open[r][c + 1]) uf.union(index, r * n + (c + 1));

        if (r == 0) uf.union(index, virtualTop);
        if (r == n - 1) uf.union(index, virtualBottom);
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        return open[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        validate(row, col);
        if (!isOpen(row, col)) return false;
        int index = (row - 1) * n + (col - 1);
        return uf.find(index) == uf.find(virtualTop);
    }

    public int numberOfOpenSites() {
        return openCount;
    }

    public boolean percolates() {
        return uf.find(virtualTop) == uf.find(virtualBottom);
    }

    private void validate(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException();
        }
    }
}
