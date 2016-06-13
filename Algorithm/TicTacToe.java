import fj.*;
import fj.data.Array;
import fj.data.List;

import java.util.Random;
import java.util.Scanner;

/**
 * Created by billin on 16-6-12.
 * A AI of TicTacToe
 */
public class TicTacToe {

    public static void main(String[] args) {

        int row;
        int col;

        ChessBoard chessBoard = new ChessBoard(ChessUnit.X);

        for (int i = 0; i < 5; i++) {
            Scanner scanner = new Scanner(System.in);
            row = scanner.nextInt();
            col = scanner.nextInt();

            Position position = new Position(row, col);

            chessBoard.setChess(position);
            chessBoard.printChessBoard();
            System.out.println();
        }
    }
}

class ChessGame {

    public void test() {
        ChessBoard chessBoard = new ChessBoard(ChessUnit.X);
//        chessBoard.printChessUnitWeight(new Position(0, 1));
        chessBoard.printChessBoard();
        System.out.println();

        chessBoard.setChess(new Position(1, 1));
//        chessBoard.printChessUnitWeight(new Position(0, 1));
        chessBoard.printChessBoard();
        System.out.println();

        chessBoard.setChess(new Position(1, 2));
//        chessBoard.printChessUnitWeight(new Position(0, 1));
        chessBoard.printChessBoard();
        System.out.println();

        chessBoard.setChess(new Position(2, 0));
//        chessBoard.printChessUnitWeight(new Position(0, 1));
        chessBoard.printChessBoard();

        chessBoard.setChess(new Position(2, 1));
        chessBoard.printChessBoard();
    }
}

/**
 * X is first hand
 * O is last hand
 */
class ChessBoard {

    public static final int CHESSBOARD_WIDTH = 3;

    private ChessUnit[][] mChessBoard;
    private char mComputerChess;
    private char mOppositeChess;

    private F<Integer, F<Integer, F<Character, F<ChessBoard, ChessBoard>>>> mRowAndColUpdate
            = row -> col -> ox -> chessBoard -> {

//        System.out.println("row=" + row + " " + "col=" + col); //Debug
        if (ox == mComputerChess) {
            for (int i = 0; i < CHESSBOARD_WIDTH; i++) {
                chessBoard.getChessBoard()[row][i].setmRowWeight(chessBoard.getChessBoard()[row][i].getmRowWeight() - 1);
                chessBoard.getChessBoard()[i][col].setmColumnWeight(chessBoard.getChessBoard()[i][col].getmColumnWeight() - 1);
                chessBoard.getChessBoard()[row][i].setoRowWeight(Integer.MAX_VALUE);
                chessBoard.getChessBoard()[i][col].setoColumnWeight(Integer.MAX_VALUE);
            }
        } else {
            for (int i = 0; i < CHESSBOARD_WIDTH; i++) {
                chessBoard.getChessBoard()[row][i].setmRowWeight(Integer.MAX_VALUE);
                chessBoard.getChessBoard()[i][col].setmColumnWeight(Integer.MAX_VALUE);
                chessBoard.getChessBoard()[row][i].setoRowWeight(chessBoard.getChessBoard()[row][i].getoRowWeight() - 1);
                chessBoard.getChessBoard()[i][col].setoColumnWeight(chessBoard.getChessBoard()[i][col].getoColumnWeight() - 1);
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

    private F3<Position, F<Position, Position>, F<Position, Position>, Array<Position>> mGetOrderLineArray
            = (position, leftArrayF, rightArrayF) -> {
        List<Position> leftList = List.nil();
        List<Position> rightList = List.nil();

        Position newPosition;
        newPosition = leftArrayF.f(position);
        for (int i = 0; i < CHESSBOARD_WIDTH; i++) {
            leftList = leftList.conss(newPosition);
            newPosition = leftArrayF.f(newPosition);
        }
        newPosition = rightArrayF.f(position);
        for (int i = 0; i < CHESSBOARD_WIDTH; i++) {
            rightList = rightList.conss(newPosition);
            newPosition = rightArrayF.f(newPosition);
        }

        return leftList.append(rightList).filter(pos -> !mIsOutOfRange.f(pos)).toArray();
    };

    private F<Integer, F<Integer, F<Character, F<ChessBoard, ChessBoard>>>> mCornerLineUpdate
            = row -> col -> ox -> chessBoard -> {

        Position pos = new Position(row, col);
//        System.out.println(pos); //Debug

        if (ox == mComputerChess) {
            mGetOrderLineArray.f(pos, mLeftToRight1, mLeftToRight2)
//                    .map(position -> {
//                        System.out.println(position + " Hi");
//                        return position;
//                    })//Debug
                    .foreach(position -> {
                        ChessUnit cu = chessBoard.getChessBoard()[position.getRow()][position.getCol()];
                        cu.setmLeftCornerToRightWeight(cu.getmLeftCornerToRightWeight() - 1);
                        cu.setoLeftCornerToRightWeight(Integer.MAX_VALUE);
                        return Unit.unit();
                    });
            mGetOrderLineArray.f(pos, mRightToLeft1, mRightToLeft2)
                    .foreach(position -> {
                        ChessUnit cu = chessBoard.getChessBoard()[position.getRow()][position.getCol()];
                        cu.setmRightCornerToLeftWeight(cu.getmRightCornerToLeftWeight() - 1);
                        cu.setoRightCornerToLeftWeight(Integer.MAX_VALUE);
                        return Unit.unit();
                    });
        } else {
            mGetOrderLineArray.f(pos, mLeftToRight1, mLeftToRight2)
//                    .map(position -> {
//                        System.out.println(position + " Hello");
//                        return position;
//                    })//Debug
                    .foreach(position -> {
                        ChessUnit cu = chessBoard.getChessBoard()[position.getRow()][position.getCol()];
                        cu.setmLeftCornerToRightWeight(Integer.MAX_VALUE);
                        cu.setoLeftCornerToRightWeight(cu.getoLeftCornerToRightWeight() - 1);
                        return Unit.unit();
                    });
            mGetOrderLineArray.f(pos, mRightToLeft1, mRightToLeft2)
                    .foreach(position -> {
                        ChessUnit cu = chessBoard.getChessBoard()[position.getRow()][position.getCol()];
                        cu.setmRightCornerToLeftWeight(Integer.MAX_VALUE);
                        cu.setoRightCornerToLeftWeight(cu.getoRightCornerToLeftWeight() - 1);
                        return Unit.unit();
                    });
        }
//        printChessUnitWeight(new Position(0, 2));//Debug

        return chessBoard;
    };

    private F<ChessBoard, Array<Position>> mGetChessBoardPosition
            = chessboard -> Array.range(0, CHESSBOARD_WIDTH)
            .map(i -> new Position(i, 0))
            .bind(position -> Array.range(0, CHESSBOARD_WIDTH).map(j -> new Position(position.getRow(), j)));

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


    private List<P2<Integer, Integer>> getWeightSequence(List<Integer> weights) {
        if (weights.length() == 2)
            return List.arrayList(P.p(weights.head(), weights.tail().head()));

        int i = weights.head();
        return getWeightSequence(weights.tail()).append(weights.map(j -> P.p(i, j)).tail());
    }

    private F<ChessUnit, F<Integer, F<Character, Boolean>>> mIsWeight
            = chessUnit -> weight -> ox -> {
        if (ox == mComputerChess)
            return chessUnit.getmRowWeight() == weight || chessUnit.getmColumnWeight() == weight || chessUnit.getmLeftCornerToRightWeight() == weight || chessUnit.getmRightCornerToLeftWeight() == weight;
        else
            return chessUnit.getoRowWeight() == weight || chessUnit.getoColumnWeight() == weight || chessUnit.getoLeftCornerToRightWeight() == weight || chessUnit.getoRightCornerToLeftWeight() == weight;
    };

    private F<ChessUnit, Boolean> mIsThreat
            = chessUnit -> mIsWeight.f(chessUnit).f(1).f(mOppositeChess) || getWeightSequence(
            List.arrayList(chessUnit.getoRowWeight(),
                    chessUnit.getoColumnWeight(),
                    chessUnit.getoLeftCornerToRightWeight(),
                    chessUnit.getoRightCornerToLeftWeight()))
            .map(p -> p._1() <= 2 && p._1().equals(p._2())).exists(aBoolean -> aBoolean);

    /**
     * if haven't position will return (-1, -1)
     */
    private F<ChessBoard, Position> mGetMyselfPosition
            = chessBoard -> {
//        Array<Position> centerOrCorner = mGetChessBoardPosition.f(chessBoard)
//                .filter(position -> position.getRow()==position.getCol())
//                .append(Array.array(new Position(2,0), new Position(0, 2)));

        Array<Position> threatList = mGetChessBoardPosition.f(chessBoard)
                .filter(position -> mIsThreat.f(chessBoard.getChessBoard()[position.getRow()][position.getCol()]));

        Array<Position> oWeight1List = mGetChessBoardPosition.f(chessBoard)
                .filter(position -> mIsWeight.f(chessBoard.getChessBoard()[position.getRow()][position.getCol()]).f(1).f(mOppositeChess));

        Array<Position> mWeight1List = mGetChessBoardPosition.f(chessBoard)
                .filter(position -> mIsWeight.f(chessBoard.getChessBoard()[position.getRow()][position.getCol()]).f(1).f(mComputerChess));

        Array<Position> weight2List = mGetChessBoardPosition.f(chessBoard)
                .filter(position -> mIsWeight.f(chessBoard.getChessBoard()[position.getRow()][position.getCol()]).f(2).f(mComputerChess));

        Array<Position> weight3List = mGetChessBoardPosition.f(chessBoard)
                .filter(position -> mIsWeight.f(chessBoard.getChessBoard()[position.getRow()][position.getCol()]).f(3).f(mComputerChess));

        Random random = new Random(System.currentTimeMillis());

        if (mWeight1List.isNotEmpty()){
          return mWeight1List.get(0);
        } else if (oWeight1List.isNotEmpty()){
            return oWeight1List.get(0);
        } else if (threatList.isNotEmpty()) {
//            System.out.println(threatList + " Threat position");//Debug
            return threatList.get(random.nextInt(threatList.length()));
        } else if (weight2List.isNotEmpty()) {
//            System.out.println(weight2List.get(0).toString() + "weight2" + weight2List.length());//Debug
            return weight2List.get(random.nextInt(weight2List.length()));
        } else if (weight3List.isNotEmpty()) {
            return weight3List.get(random.nextInt(weight3List.length()));
        } else
            return new Position(-1, -1);
    };

    /**
     * 写到这里的时候，我发现一开始就设计错误了，那个获取权值的方法应该只是获取对应o或x的方法，而不是自己和对方
     * 不过算了，到时候再大改一下
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

        mGetChessBoardPosition.f(this).filter(position -> mGetOrderLineArray.f(position, mLeftToRight1, mLeftToRight2).length() < 2)
                .foreach(position -> {
                    ChessUnit cu = mChessBoard[position.getRow()][position.getCol()];
                    cu.setmLeftCornerToRightWeight(Integer.MAX_VALUE);
                    cu.setoLeftCornerToRightWeight(Integer.MAX_VALUE);
                    return Unit.unit();
                });

        mGetChessBoardPosition.f(this).filter(position -> mGetOrderLineArray.f(position, mRightToLeft1, mRightToLeft2).length() < 2)
                .foreach(position -> {
                    ChessUnit cu = mChessBoard[position.getRow()][position.getCol()];
                    cu.setmRightCornerToLeftWeight(Integer.MAX_VALUE);
                    cu.setoRightCornerToLeftWeight(Integer.MAX_VALUE);
                    return Unit.unit();
                });
    }


    /**
     * @param ox Opposite chess
     */
    public ChessBoard(char ox) {

        initChessBoard();

        if (ox == ChessUnit.O)
            mComputerChess = ChessUnit.X;
        else
            mComputerChess = ChessUnit.O;

        mOppositeChess = ox;
    }

    public ChessBoard setChess(Position position) {
        ChessBoard chessBoard = mAddChess.f(position.getRow()).f(position.getCol()).f(mOppositeChess).f(this);
        Position pos = mGetMyselfPosition.f(chessBoard);

        if (pos.getCol() != -1) {
//            System.out.println("position.row=" + pos.getRow() + " position.col=" + pos.getCol()); //Debug
            chessBoard = mAddChess.f(pos.getRow()).f(pos.getCol()).f(mComputerChess).f(chessBoard);
        } else {
            System.out.println("The match is over"); //Debug
        }

        return chessBoard;
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
                chessUnit.getmRowWeight(),
                chessUnit.getmColumnWeight(),
                chessUnit.getmLeftCornerToRightWeight(),
                chessUnit.getmRightCornerToLeftWeight(),
                chessUnit.getoRowWeight(),
                chessUnit.getoColumnWeight(),
                chessUnit.getoLeftCornerToRightWeight(),
                chessUnit.getoRightCornerToLeftWeight(),
                chessUnit.getUNIT()));
    }
}

class Position {
    int getCol() {
        return col;
    }

    void setCol(int col) {
        this.col = col;
    }

    @Override
    public String toString() {
        return "Position{" +
                "col=" + col +
                ", row=" + row +
                '}';
    }

    int getRow() {
        return row;
    }

    void setRow(int row) {
        this.row = row;
    }

    private int col;
    private int row;

    Position(int row, int col) {
        setRow(row);
        setCol(col);
    }
}

class ChessUnit {

    public static final char O = 'o';
    public static final char X = 'x';

    private char UNIT = '_';

    private int mRowThreatWeight;
    private int mColumnThreatWeight;
    private int mRightCornerToLeftThreatWeight;
    private int mLeftCornerToRightThreatWeight;

    private int mRowWeight;
    private int mColumnWeight;
    private int mRightCornerToLeftWeight;
    private int mLeftCornerToRightWeight;

    private int oRowWeight;
    private int oColumnWeight;
    private int oRightCornerToLeftWeight;
    private int oLeftCornerToRightWeight;

    public ChessUnit(int weight) {
        setmColumnWeight(weight);
        setmRowWeight(weight);
        setmLeftCornerToRightWeight(weight);
        setmRightCornerToLeftWeight(weight);
        setoColumnWeight(weight);
        setoRowWeight(weight);
        setoLeftCornerToRightWeight(weight);
        setoRightCornerToLeftWeight(weight);

        setmRowThreatWeight(weight);
        setmColumnThreatWeight(weight);
        setmLeftCornerToRightThreatWeight(weight);
        setmRightCornerToLeftThreatWeight(weight);

        setUNIT('_');
    }

    public ChessUnit(char ox) {
        this(Integer.MAX_VALUE);
        setUNIT(ox);
    }

    @Override
    public String toString() {
        return UNIT + " ";
    }

    public int getmRowThreatWeight() {
        return mRowThreatWeight;
    }

    public void setmRowThreatWeight(int mRowThreatWeight) {
        this.mRowThreatWeight = mRowThreatWeight;
    }

    public int getmColumnThreatWeight() {
        return mColumnThreatWeight;
    }

    public void setmColumnThreatWeight(int mColumnThreatWeight) {
        this.mColumnThreatWeight = mColumnThreatWeight;
    }

    public int getmRightCornerToLeftThreatWeight() {
        return mRightCornerToLeftThreatWeight;
    }

    public void setmRightCornerToLeftThreatWeight(int mRightCornerToLeftThreatWeight) {
        this.mRightCornerToLeftThreatWeight = mRightCornerToLeftThreatWeight;
    }

    public int getmLeftCornerToRightThreatWeight() {
        return mLeftCornerToRightThreatWeight;
    }

    public void setmLeftCornerToRightThreatWeight(int mLeftCornerToRightThreatWeight) {
        this.mLeftCornerToRightThreatWeight = mLeftCornerToRightThreatWeight;
    }


    public int getoLeftCornerToRightWeight() {
        return oLeftCornerToRightWeight;
    }

    public void setoLeftCornerToRightWeight(int oLeftCornerToRightWeight) {
        this.oLeftCornerToRightWeight = oLeftCornerToRightWeight;
    }

    public char getUNIT() {
        return UNIT;
    }

    public void setUNIT(char UNIT) {
        this.UNIT = UNIT;
    }

    public int getmRowWeight() {
        return mRowWeight;
    }

    public void setmRowWeight(int mRowWeight) {
        this.mRowWeight = mRowWeight;
    }

    public int getmColumnWeight() {
        return mColumnWeight;
    }

    public void setmColumnWeight(int mColumnWeight) {
        this.mColumnWeight = mColumnWeight;
    }

    public int getmRightCornerToLeftWeight() {
        return mRightCornerToLeftWeight;
    }

    public void setmRightCornerToLeftWeight(int mRightCornerToLeftWeight) {
        this.mRightCornerToLeftWeight = mRightCornerToLeftWeight;
    }

    public int getmLeftCornerToRightWeight() {
        return mLeftCornerToRightWeight;
    }

    public void setmLeftCornerToRightWeight(int mLeftCornerToRightWeight) {
        this.mLeftCornerToRightWeight = mLeftCornerToRightWeight;
    }

    public int getoRowWeight() {
        return oRowWeight;
    }

    public void setoRowWeight(int oRowWeight) {
        this.oRowWeight = oRowWeight;
    }

    public int getoColumnWeight() {
        return oColumnWeight;
    }

    public void setoColumnWeight(int oColumnWeight) {
        this.oColumnWeight = oColumnWeight;
    }

    public int getoRightCornerToLeftWeight() {
        return oRightCornerToLeftWeight;
    }

    public void setoRightCornerToLeftWeight(int oRightCornerToLeftWeight) {
        this.oRightCornerToLeftWeight = oRightCornerToLeftWeight;
    }


}
