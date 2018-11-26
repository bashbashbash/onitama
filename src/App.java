public class App {
    private Game game;
    private Ui ui;

    App() {
        game = new Game();
        ui   = new Ui(System.out, System.in);
    }

    private void start() {
        game.start();

        while(true) {
            ui.clear();
            ui.render(game.getBoard());
            ui.render(game.getPlayers());
            ui.flush();

            PendingMove pendingMove = new PendingMove(game);
            pendingMove.pickPawn(ui.pickPawnId());
            pendingMove.pickCard(new Point(0, 1));

            Move move = pendingMove.getValidMove();
            if (move != null) {
                game.makeMove(move);
            }
        }
    }

    // main
    public static void main(String[] args) {
        App app = new App();
        app.start();
    }
}
