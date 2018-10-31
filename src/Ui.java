import org.jetbrains.annotations.NotNull;

import java.io.PrintStream;
import java.util.ArrayList;

final class Ui {
    private PrintStream out;
    private char[][]    buffer;

    Ui(PrintStream out) {
        this.out = out;
    }

    // compositing
    void render(@NotNull Board board) {
        char[][] grid = board.getGrid();

        buffer = grid.clone();
        for(int i = 0; i < grid.length; i++) {
            buffer[i] = grid[i].clone();
        }
    }

    void render(@NotNull ArrayList<Player> players) {
        if(buffer == null) {
            throw new NoBoardError();
        }

        for(Player player : players) {
            for(Point pawn : player.getPawns()) {
                render(pawn);
            }
        }
    }

    private void render(Point point) {
        if(buffer == null) {
            throw new NoBoardError();
        }

        int x = point.getX();
        int y = point.getY();

        if(y >= buffer.length || x >= buffer[y].length) {
            throw new OutOfBoardError();
        }

        buffer[y][x] = '#';
    }

    // display
    void flush() {
        if(buffer == null) {
            throw new NoBoardError();
        }

        for(char[] row : buffer) {
            int i = 0;

            for(char cell : row) {
                out.print(cell);
                out.print(i++ == row.length - 1 ? '\n' : ' ');
            }
        }

        buffer = null;
    }

    // errors
    class NoBoardError extends RuntimeException {
    }

    class OutOfBoardError extends RuntimeException {
    }
}
