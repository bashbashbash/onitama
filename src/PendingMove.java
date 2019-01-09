import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class PendingMove {
    // dependency
    @NotNull
    private Game game;

    // form properties
    @Nullable
    private int pawnId;

    @Nullable
    private Point card;

    PendingMove(@NotNull Game game) {
        this.game = game;
    }

    // commands
    void pickPawn(int pawnId) {
        this.pawnId = pawnId;
    }

    void pickCard(@NotNull Point card) {
        this.card = card;
    }

    // queries
    @NotNull
    Result getValidMove() {
        Pawn pawn = game.findCurrentPlayerPawnById(pawnId);
        if(pawn == null) {
            return new Result(new InvalidPawnId(pawnId));
        }

        Point delta = card;
        if(!game.isFirstPlayerCurrentPlayer()) {
            delta = delta.mirrorX();
        }

        Point newPosition = pawn.getPosition().add(delta);
        if(!game.getBoard().containsPoint(newPosition)) {
            return new Result(new PositionOutOfBounds(newPosition));
        }

        if(game.findPawnByPosition(newPosition) != null) {
            return new Result(new PositionWasOccuppied(newPosition));
        }

        return new Result(new Move(pawnId, newPosition));
    }

    class Result {
        @Nullable
        private Error error;

        @Nullable
        private Move move;

        Result(@NotNull Move move) {
            this.move = move;
        }

        Result(@NotNull Error error) {
            this.error = error;
        }

        // accessors
        @Nullable Error getError() {
            return error;
        }

        @Nullable Move getMove() {
            return move;
        }
    }

    class InvalidPawnId extends Error {
        private int pawnId;

        InvalidPawnId(int pawnId) {
            this.pawnId = pawnId;
        }

        int getPawnId() {
            return pawnId;
        }
    }

    class PositionOutOfBounds extends Error {
        @NotNull
        private Point newPosition;

        PositionOutOfBounds(@NotNull Point newPosition) {
            this.newPosition = newPosition;
        }

        @NotNull Point getNewPosition() {
            return newPosition;
        }
    }

    class PositionWasOccuppied extends Error {
        @NotNull
        private Point newPosition;

        PositionWasOccuppied(@NotNull Point newPosition) {
            this.newPosition = newPosition;
        }

        @NotNull Point getNewPosition() {
            return newPosition;
        }
    }
}
