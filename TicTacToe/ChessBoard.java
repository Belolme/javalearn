/**
 * Created by billin on 16-7-1.
 * Chess Board for Tic Tac Toe
 */

import fj.*;
import fj.data.Array;
import fj.data.List;

/**
 * X is first hand
 * O is last hand
 */
public class ChessBoard implements Cloneable {

    public final static int CHESSBOARD_WIDTH = 3;

    private ChessUnit[][] mChessBoard;
    private char mComputerChess;
    private char mOppositeChess;

    public char getmComputerChess() {
        return mComputerChess;
    }

    public char getmOppositeChess() {
        return mOppositeChess;
    }

    private F<Integer, F<Integer, F<Character, F<ChessBoard, ChessBoard>>>> mRowAndColUpdate
            = row -> col -> ox -> chessBoard -> {

//        System.out.println("row=" + row + " " + "col=" + col); //Debug
        if (ox == ChessUnit.X) {
            for (int i = 0; i < CHESSBOARD_WIDTH; i++) {
                chessBoard.getChessBoard()[row][i].setXRowWeight(chessBoard.getChessBoard()[row][i].getXRowWeight() - 1);
                chessBoard.getChessBoard()[i][col].setXColumnWeight(chessBoard.getChessBoard()[i][col].getXColumnWeight() - 1);
                chessBoard.getChessBoard()[row][i].setORowWeight(Integer.MAX_VALUE);
                chessBoard.getChessBoard()[i][col].setOColumnWeight(Integer.MAX_VALUE);
            }
        } else {
            for (int i = 0; i < CHESSBOARD_WIDTH; i++) {
                chessBoard.getChessBoard()[row][i].setXRowWeight(Integer.MAX_VALUE);
                chessBoard.getChessBoard()[i][col].setXColumnWeight(Integer.MAX_VALUE);
                chessBoard.getChessBoard()[row][i].setORowWeight(chessBoard.getChessBoard()[row][i].getORowWeight() - 1);
                chessBoard.getChessBoard()[i][col].setOColumnWeight(chessBoard.getChessBoard()[i][col].getOColumnWeight() - 1);
            }
        }
//        printChessUnitWeight(new Position(0, 1));//Debug
        return chessBoard;
    };

    private F<Position, Boolean> mIsOutOfRange
            = position -> position.getCol() < 0 || position.getCol() >= CHESSBOARD_WIDTH || position.getRow() < 0 || position.getRow() >= CHESSBOARD_WIDTH;

    private F<Position, Position> mLeftToRight1
            = position -> new Position(position.getRow() - 1, position.getCol() - 1);

    private F<Position, Position> mLeftToRight2
            = position -> new Position(position.getRow() + 1, position.getCol() + 1);

    private F<Position, Position> mRightToLeft1
            = position -> new Position(position.getRow() - 1, position.getCol() + 1);

    private F<Position, Position> mRightToLeft2
            = position -> new Position(position.getRow() + 1, position.getCol() - 1);

    private F3<Position, F<Position, Position>, F<Position, Position>, Array<Position>> mGetCornerLineArray
            = (position, leftArrayF, rightArrayF) -> {
        List<Position> leftList = List.nil();
        List<Position> rightList = List.nil();

        Position newPosition;
        newPosition = leftArrayF.f(position);
        for (int i = 0; i < CHESSBOARD_WIDTH - 1; i++) {
            leftList = leftList.conss(newPosition);
            newPosition = leftArrayF.f(newPosition);
        }
        newPosition = rightArrayF.f(position);
        for (int i = 0; i < CHESSBOARD_WIDTH - 1; i++) {
            rightList = rightList.conss(newPosition);
            newPosition = rightArrayF.f(newPosition);
        }

        return leftList.append(rightList).cons(position).filter(pos -> !mIsOutOfRange.f(pos)).toArray();
    };

    private F<Integer, F<Integer, F<Character, F<ChessBoard, ChessBoard>>>> mCornerLineUpdate
            = row -> col -> ox -> chessBoard -> {

        Position pos = new Position(row, col);
//        System.out.println(pos); //Debug

        if (ox == ChessUnit.X) {
            mGetCornerLineArray.f(pos, mLeftToRight1, mLeftToRight2)
                    .foreach(position -> {
                        ChessUnit cu = chessBoard.getChessBoard()[position.getRow()][position.getCol()];
                        cu.setXLeftCornerToRightWeight(cu.getXLeftCornerToRightWeight() - 1);
                        cu.setOLeftCornerToRightWeight(Integer.MAX_VALUE);
                        return Unit.unit();
                    });
            mGetCornerLineArray.f(pos, mRightToLeft1, mRightToLeft2)
                    .foreach(position -> {
                        ChessUnit cu = chessBoard.getChessBoard()[position.getRow()][position.getCol()];
                        cu.setXRightCornerToLeftWeight(cu.getXRightCornerToLeftWeight() - 1);
                        cu.setORightCornerToLeftWeight(Integer.MAX_VALUE);
                        return Unit.unit();
                    });
        } else {
            mGetCornerLineArray.f(pos, mLeftToRight1, mLeftToRight2)
//                    .map(position -> {
//                        System.out.println(position + " Hello");
//                        return position;
//                    })//Debug
                    .foreach(position -> {
                        ChessUnit cu = chessBoard.getChessBoard()[position.getRow()][position.getCol()];
                        cu.setXLeftCornerToRightWeight(Integer.MAX_VALUE);
                        cu.setOLeftCornerToRightWeight(cu.getOLeftCornerToRightWeight() - 1);
                        return Unit.unit();
                    });
            mGetCornerLineArray.f(pos, mRightToLeft1, mRightToLeft2)
                    .foreach(position -> {
                        ChessUnit cu = chessBoard.getChessBoard()[position.getRow()][position.getCol()];
                        cu.setXRightCornerToLeftWeight(Integer.MAX_VALUE);
                        cu.setORightCornerToLeftWeight(cu.getORightCornerToLeftWeight() - 1);
                        return Unit.unit();
                    });
        }
//        printChessUnitWeight(new Position(0, 2));//Debug

        return chessBoard;
    };


    private F<Integer, F<Integer, F<Character, F<ChessBoard, ChessBoard>>>> mAddChess
            = row -> col -> ox -> chessBoard -> {

        chessBoard.getChessBoard()[row][col] = new ChessUnit(ox);
        chessBoard = mRowAndColUpdate.f(row).f(col).f(ox).f(chessBoard);
        chessBoard = mCornerLineUpdate.f(row).f(col).f(ox).f(chessBoard);
//        System.out.println("print fucking chess unit weight");//Debug
//        printChessUnitWeight(new Position(0, 1));//Debug

//        mGetChessBoardPosition.f(chessBoard).foreach(p -> { //Debug
//            System.out.println("insert " + ox + " " + p);
//            printChessUnitWeight(p);
//            return Unit.unit();
//        });

        return chessBoard;
    };

    private F<ChessBoard, Array<Position>> mGetChessBoardPosition
            = chessboard -> Array.range(0, ChessBoard.CHESSBOARD_WIDTH)
            .map(i -> new Position(i, 0))
            .bind(position -> Array.range(0, ChessBoard.CHESSBOARD_WIDTH).map(j -> new Position(position.getRow(), j)));

    public boolean isFill(Position position) {
        return getChess(position) != ChessUnit.NONE;
    }

    public char getChess(Position position) {
        return getChessBoard()[position.getRow()][position.getCol()].getUNIT();
    }

    /**
     * 写到这里的时候，我发现一开始就设计错误了，那个获取权值的方法应该只是获取对应o或x的方法，而不是自己和对方
     * 不过算了，到时候再大改一下
     * <p>
     * 2016.6.19 加了一个棋谱和重新构建了代码，使得代码更加容易理解和使用
     * <p>
     * <p>2016.7.1 为tic tac toe 加入最大最小值搜索树</p>
     */
//    private F<ChessBoard, Position> mGetOppositePosition
//            = chessBoard -> {
//
//    };

//    private F<ChessBoard, F<Character, Position>> mGetPosition
//            = chessBoard -> ox -> {
//
//    };
    private void initChessBoard() {
        mChessBoard = new ChessUnit[CHESSBOARD_WIDTH][CHESSBOARD_WIDTH];

        for (int i = 0; i < CHESSBOARD_WIDTH; i++) {
            for (int j = 0; j < CHESSBOARD_WIDTH; j++) {
                mChessBoard[i][j] = new ChessUnit(CHESSBOARD_WIDTH);
            }
        }

        mGetChessBoardPosition.f(this).filter(position -> mGetCornerLineArray.f(position, mLeftToRight1, mLeftToRight2).length() <= 2)
                .foreach(position -> {
                    ChessUnit cu = mChessBoard[position.getRow()][position.getCol()];
                    cu.setXLeftCornerToRightWeight(Integer.MAX_VALUE);
                    cu.setOLeftCornerToRightWeight(Integer.MAX_VALUE);
                    return Unit.unit();
                });

        mGetChessBoardPosition.f(this).filter(position -> mGetCornerLineArray.f(position, mRightToLeft1, mRightToLeft2).length() <= 2)
                .foreach(position -> {
                    ChessUnit cu = mChessBoard[position.getRow()][position.getCol()];
                    cu.setXRightCornerToLeftWeight(Integer.MAX_VALUE);
                    cu.setORightCornerToLeftWeight(Integer.MAX_VALUE);
                    return Unit.unit();
                });
    }

    /**
     * @param ox Opposite chess
     */
    ChessBoard(char ox) {

        initChessBoard();

        if (ox == ChessUnit.O)
            mComputerChess = ChessUnit.X;
        else
            mComputerChess = ChessUnit.O;

        mOppositeChess = ox;
//
//        if (ox == ChessUnit.O) {
//            Position position = mGetMyselfPosition.f(this);
//            mAddChess.f(position.getRow()).f(position.getCol()).f(mComputerChess).f(this);
//        }
    }


    public boolean isWin(char ox) {
        int rowCount = 0;
        int colCount = 0;

        for (int i = 0; i < CHESSBOARD_WIDTH; i++) {
            for (int j = 0; j < CHESSBOARD_WIDTH; j++) {
                if (getChessBoard()[i][j].getUNIT() == ox)
                    rowCount++;
                else
                    rowCount = 0;

                if (getChessBoard()[j][i].getUNIT() == ox)
                    colCount++;
                else
                    colCount = 0;

                if (rowCount >= 3 || colCount >= 3)
                    return true;
            }
            rowCount = 0;
            colCount = 0;
        }

//        System.out.println("Hello");
        return mGetCornerLineArray.f(new Position(1, 1), mLeftToRight1, mLeftToRight2)
                .forall(position -> mGetChessBoardPosition.f(this).filter(this::isFill).filter(position1 -> getChess(position) == ox).exists(position::equals))
                || mGetCornerLineArray.f(new Position(1, 1), mRightToLeft1, mRightToLeft2)
                .forall(position -> mGetChessBoardPosition.f(this).filter(this::isFill).filter(position1 -> getChess(position) == ox).exists(position::equals));
    }

    public boolean isFlat() {
        return !isWin(ChessUnit.O) && !isWin(ChessUnit.X) && mGetChessBoardPosition.f(this).filter(position -> !isFill(position)).isEmpty();
    }

    public boolean isOver() {
        return isFlat() || isWin(ChessUnit.O) || isWin(ChessUnit.X);
    }

    private final static int WIN = 0;
    private final static int LOST = 1;
    private final static int FLAT = 2;
    private final static int NOT_OVER = 3;

    abstract static class ResultExecutor {

        public abstract void iWin();

        public abstract void flat();

        public abstract void iLost();

        public abstract void notOver();

        private void setmResult(int mResult) {
            this.mResult = mResult;
        }

        public int getmResult() {
            return mResult;
        }

        private int mResult = NOT_OVER;

        public void execute() {
            switch (mResult) {
                case WIN:
                    iWin();
                    break;
                case LOST:
                    iLost();
                    break;
                case FLAT:
                    flat();
                    break;
                case NOT_OVER:
                    notOver();
                    break;
                default:
                    notOver();
                    break;
            }
        }
    }

    void setmResultExecutor(ResultExecutor mResultExecutor) {
        this.mResultExecutor = mResultExecutor;
    }

    ResultExecutor getmResultExecutor() {
        updatemResultExecutorValue();
        return mResultExecutor;
    }

    private ResultExecutor mResultExecutor = new ResultExecutor() {
        @Override
        public void iWin() {
            printChessBoard();
            System.out.println("You are winner");
        }

        @Override
        public void flat() {
            printChessBoard();
            System.out.println("The match is flat");
        }

        @Override
        public void iLost() {
            printChessBoard();
            System.out.println("You lost");
        }

        @Override
        public void notOver() {
            printChessBoard();
        }
    };

    private void updatemResultExecutorValue() {
        if (!isOver())
            mResultExecutor.setmResult(NOT_OVER);
        else if (isWin(mOppositeChess))
            mResultExecutor.setmResult(WIN);
        else if (isFlat())
            mResultExecutor.setmResult(FLAT);
        else
            mResultExecutor.setmResult(LOST);
    }

    public ChessBoard setChess(Position position, char chess) {
        ChessBoard chessBoard = mAddChess.f(position.getRow()).f(position.getCol()).f(chess).f(this);
//        Position pos = mGetMyselfPosition.f(chessBoard);
//
//        if (pos.getCol() != -1) {
////            System.out.println("position.row=" + pos.getRow() + " position.col=" + pos.getCol()); //Debug
//            mAddChess.f(pos.getRow()).f(pos.getCol()).f(mComputerChess).f(chessBoard);
//        }
        return this;
    }

    public ChessUnit[][] getChessBoard() {
        return mChessBoard;
    }


    /**
     * Debug
     */
    public void printChessBoard() {
        for (ChessUnit[] chessUnits : mChessBoard) {
            for (ChessUnit chessUnit : chessUnits) {
                System.out.print(chessUnit.toString());
            }
            System.out.println();
        }
    }

    /**
     * Debug
     *
     * @param position ChessUnit position
     */
    public void printChessUnitWeight(Position position) {
        ChessUnit chessUnit = getChessBoard()[position.getRow()][position.getCol()];
        printChessUnitWeight(chessUnit);
    }

    /**
     * Debug
     *
     * @param chessUnit ChessUnit to be print
     */
    public void printChessUnitWeight(ChessUnit chessUnit) {
        System.out.println(String.format("%d %d %d %d | %d %d %d %d %c",
                chessUnit.getXRowWeight(),
                chessUnit.getXColumnWeight(),
                chessUnit.getXLeftCornerToRightWeight(),
                chessUnit.getXRightCornerToLeftWeight(),
                chessUnit.getORowWeight(),
                chessUnit.getOColumnWeight(),
                chessUnit.getOLeftCornerToRightWeight(),
                chessUnit.getORightCornerToLeftWeight(),
                chessUnit.getUNIT()));
    }

    public ChessBoard clone() {
        ChessBoard chessBoard = null;

        try {
            chessBoard = (ChessBoard) super.clone();
            chessBoard.mChessBoard = getmChessBoardCopy();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return chessBoard;
    }

    public ChessBoard turnRight() {
        ChessBoard chessBoard = clone();

        ChessUnit[][] chessUnits = new ChessUnit[CHESSBOARD_WIDTH][CHESSBOARD_WIDTH];
        for (int i = 0; i < CHESSBOARD_WIDTH; i++) {
            for (int j = 0; j < CHESSBOARD_WIDTH; j++) {
                chessUnits[j][2-i] = chessBoard.mChessBoard[i][j];
            }
        }

        chessBoard.mChessBoard = chessUnits;
        return chessBoard;
    }

    private ChessUnit[][] getmChessBoardCopy() {
        ChessUnit[][] chessUnit = new ChessUnit[CHESSBOARD_WIDTH][CHESSBOARD_WIDTH];
        for (int i = 0; i < CHESSBOARD_WIDTH; i++) {
            for (int j = 0; j < CHESSBOARD_WIDTH; j++) {
                chessUnit[i][j] = mChessBoard[i][j].clone();
            }
        }
        return chessUnit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChessBoard that = (ChessBoard) o;

        if (mComputerChess != that.mComputerChess) return false;
        if (mOppositeChess != that.mOppositeChess) return false;
        for (int i=0; i<CHESSBOARD_WIDTH; i++){
            for (int j=0; j<CHESSBOARD_WIDTH; j++){
                if (mChessBoard[i][j].getUNIT()!=that.mChessBoard[i][j].getUNIT()) {
                    return false;
                }
            }
        }

        return true;
    }
}