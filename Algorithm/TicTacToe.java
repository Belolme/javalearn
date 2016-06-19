import fj.*;
import fj.data.Array;
import fj.data.List;
import fj.data.Set;

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

        System.out.println("请输入两个数字，第一个代表行数，第二个代表列数（0 为起始数字，例如：0 0）");

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
        if (ox == ChessUnit.X)
            return chessUnit.getXRowWeight() == weight || chessUnit.getXColumnWeight() == weight || chessUnit.getXLeftCornerToRightWeight() == weight || chessUnit.getXRightCornerToLeftWeight() == weight;
        else
            return chessUnit.getORowWeight() == weight || chessUnit.getOColumnWeight() == weight || chessUnit.getOLeftCornerToRightWeight() == weight || chessUnit.getORightCornerToLeftWeight() == weight;
    };

    private F4<Integer, Integer, Integer, Integer, Boolean> mIsWeight2Threat
            = (row, col, leftToRight, rightToLeft) ->
            getWeightSequence(List.arrayList(row, col, leftToRight, rightToLeft))
                    .map(p -> p._1() <= 2 && p._1().equals(p._2()))
                    .exists(aBoolean -> aBoolean);

    private F<ChessUnit, F<Character, Boolean>> mIsThreat
            = chessUnit -> ox ->
            ox == ChessUnit.X
                    ? mIsWeight2Threat.f(chessUnit.getXRowWeight(),
                    chessUnit.getXColumnWeight(),
                    chessUnit.getXLeftCornerToRightWeight(),
                    chessUnit.getXRightCornerToLeftWeight())
                    : mIsWeight2Threat.f(chessUnit.getORowWeight(),
                    chessUnit.getOColumnWeight(),
                    chessUnit.getOLeftCornerToRightWeight(),
                    chessUnit.getORightCornerToLeftWeight());

    private boolean isFill(Position position) {
        return getChess(position) != ChessUnit.NONE;
    }

    private char getChess(Position position) {
        return getChessBoard()[position.getRow()][position.getCol()].getUNIT();
    }

    private F<ChessBoard, Integer> mGetChessUnitNumber
            = chessBoard -> mGetChessBoardPosition.f(chessBoard).filter(this::isFill).length();

    private F<ChessBoard, Boolean> mIsCornerX
            = chessBoard -> mGetChessUnitNumber.f(chessBoard) == 3
            && mComputerChess == ChessUnit.O
            && getChess(new Position(1, 1)) == mComputerChess
            &&(mGetChessBoardPosition
            .f(chessBoard)
            .filter(this::isFill)
            .forall(position -> mGetCornerLineArray.f(new Position(1, 1), mLeftToRight1, mLeftToRight2).exists(position::equals))
            || mGetChessBoardPosition
            .f(chessBoard)
            .filter(this::isFill)
            .forall(position -> mGetCornerLineArray.f(new Position(1, 1), mRightToLeft1, mRightToLeft2).exists(position::equals)));

    /**
     * if haven't position will return (-1, -1)
     */
    private F<ChessBoard, Position> mGetMyselfPosition
            = chessBoard -> {
//        Array<Position> centerOrCorner = mGetChessBoardPosition.f(chessBoard)
//                .filter(position -> position.getRow()==position.getCol())
//                .append(Array.array(new Position(2,0), new Position(0, 2)));

        Array<Position> threatList = mGetChessBoardPosition.f(chessBoard)
                .filter(position -> mIsThreat.f(chessBoard.getChessBoard()[position.getRow()][position.getCol()]).f(mOppositeChess));

        Array<Position> oWeight1List = mGetChessBoardPosition.f(chessBoard)
                .filter(position -> mIsWeight.f(chessBoard.getChessBoard()[position.getRow()][position.getCol()]).f(1).f(mOppositeChess));

        Array<Position> mWeight1List = mGetChessBoardPosition.f(chessBoard)
                .filter(position -> mIsWeight.f(chessBoard.getChessBoard()[position.getRow()][position.getCol()]).f(1).f(mComputerChess));

        Array<Position> weight2List = mGetChessBoardPosition.f(chessBoard)
                .filter(position -> mIsWeight.f(chessBoard.getChessBoard()[position.getRow()][position.getCol()]).f(2).f(mComputerChess));

        Array<Position> weight3List = mGetChessBoardPosition.f(chessBoard)
                .filter(position -> mIsWeight.f(chessBoard.getChessBoard()[position.getRow()][position.getCol()]).f(3).f(mComputerChess));

        Array<Position> blankList = mGetChessBoardPosition.f(chessBoard)
                .filter(p -> !isFill(p));

        Random random = new Random(System.currentTimeMillis());

        if (mGetChessUnitNumber.f(chessBoard) == 1) {
            return mGetChessBoardPosition.f(chessBoard)
                    .filter(position -> position.getCol() != 1 && position.getRow() != 1)
                    .append(Array.array(new Position(1, 1)))
                    .filter(position -> !isFill(position))
                    .reverse()
                    .get(0);
        } else if (mIsCornerX.f(chessBoard)) {
            return new Position(1, 0);
        } else if (mWeight1List.isNotEmpty()) {
            return mWeight1List.get(0);
        } else if (oWeight1List.isNotEmpty()) {
            return oWeight1List.get(0);
        } else if (threatList.isNotEmpty()) {
            System.out.println(threatList + " Threat position");//Debug
            return threatList.get(random.nextInt(threatList.length()));
        } else if (weight2List.isNotEmpty()) {
//            System.out.println(weight2List + "weight2 " + weight2List.length());//Debug
            return weight2List.get(random.nextInt(weight2List.length()));
        } else if (weight3List.isNotEmpty()) {
            return weight3List.get(random.nextInt(weight3List.length()));
        } else if (blankList.isNotEmpty())
            return blankList.get(random.nextInt(blankList.length()));
        else
            return new Position(-1, -1);
    };

    /**
     * 写到这里的时候，我发现一开始就设计错误了，那个获取权值的方法应该只是获取对应o或x的方法，而不是自己和对方
     * 不过算了，到时候再大改一下
     * <p>
     * 2016.6.19 加了一个棋谱和重新构建了代码，使得代码更加容易理解和使用
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

        mGetChessBoardPosition.f(this).filter(position -> mGetCornerLineArray.f(position, mLeftToRight1, mLeftToRight2).length() < 2)
                .foreach(position -> {
                    ChessUnit cu = mChessBoard[position.getRow()][position.getCol()];
                    cu.setXLeftCornerToRightWeight(Integer.MAX_VALUE);
                    cu.setOLeftCornerToRightWeight(Integer.MAX_VALUE);
                    return Unit.unit();
                });

        mGetChessBoardPosition.f(this).filter(position -> mGetCornerLineArray.f(position, mRightToLeft1, mRightToLeft2).length() < 2)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        return col == position.col && row == position.row;
    }

    Position(int row, int col) {
        setRow(row);
        setCol(col);
    }
}

class ChessUnit {

    static final char O = 'o';
    static final char X = 'x';
    static final char NONE = '_';

    private char UNIT;

    private int XRowThreatWeight;
    private int XColumnThreatWeight;
    private int XRightCornerToLeftThreatWeight;
    private int XLeftCornerToRightThreatWeight;

    private int ORowThreatWeight;
    private int OColumnThreatWeight;
    private int ORightCornerToLeftThreatWeight;
    private int OLeftCornerToRightThreatWeight;

    private int XRowWeight;
    private int XColumnWeight;
    private int XRightCornerToLeftWeight;
    private int XLeftCornerToRightWeight;

    private int ORowWeight;
    private int OColumnWeight;
    private int ORightCornerToLeftWeight;
    private int OLeftCornerToRightWeight;

    @Override
    public String toString() {
        return UNIT + " ";
    }

    public ChessUnit(int weight) {
        setXColumnWeight(weight);
        setXRowWeight(weight);
        setXLeftCornerToRightWeight(weight);
        setXRightCornerToLeftWeight(weight);
        setOColumnWeight(weight);
        setORowWeight(weight);
        setOLeftCornerToRightWeight(weight);
        setORightCornerToLeftWeight(weight);

        setXRowThreatWeight(weight);
        setXColumnThreatWeight(weight);
        setXLeftCornerToRightThreatWeight(weight);
        setXRightCornerToLeftThreatWeight(weight);

        setUNIT(NONE);
    }

    public ChessUnit(char ox) {
        this(Integer.MAX_VALUE);
        setUNIT(ox);
    }

    public int getXColumnThreatWeight() {
        return XColumnThreatWeight;
    }

    public void setXColumnThreatWeight(int XColumnThreatWeight) {
        this.XColumnThreatWeight = XColumnThreatWeight;
    }

    public char getUNIT() {
        return UNIT;
    }

    public void setUNIT(char UNIT) {
        this.UNIT = UNIT;
    }

    public int getXRowThreatWeight() {
        return XRowThreatWeight;
    }

    public void setXRowThreatWeight(int XRowThreatWeight) {
        this.XRowThreatWeight = XRowThreatWeight;
    }

    public int getXRightCornerToLeftThreatWeight() {
        return XRightCornerToLeftThreatWeight;
    }

    public void setXRightCornerToLeftThreatWeight(int XRightCornerToLeftThreatWeight) {
        this.XRightCornerToLeftThreatWeight = XRightCornerToLeftThreatWeight;
    }

    public int getXLeftCornerToRightThreatWeight() {
        return XLeftCornerToRightThreatWeight;
    }

    public void setXLeftCornerToRightThreatWeight(int XLeftCornerToRightThreatWeight) {
        this.XLeftCornerToRightThreatWeight = XLeftCornerToRightThreatWeight;
    }

    public int getXRowWeight() {
        return XRowWeight;
    }

    public void setXRowWeight(int XRowWeight) {
        this.XRowWeight = XRowWeight;
    }

    public int getXColumnWeight() {
        return XColumnWeight;
    }

    public void setXColumnWeight(int XColumnWeight) {
        this.XColumnWeight = XColumnWeight;
    }

    public int getXRightCornerToLeftWeight() {
        return XRightCornerToLeftWeight;
    }

    public void setXRightCornerToLeftWeight(int XRightCornerToLeftWeight) {
        this.XRightCornerToLeftWeight = XRightCornerToLeftWeight;
    }

    public int getXLeftCornerToRightWeight() {
        return XLeftCornerToRightWeight;
    }

    public void setXLeftCornerToRightWeight(int XLeftCornerToRightWeight) {
        this.XLeftCornerToRightWeight = XLeftCornerToRightWeight;
    }

    public int getORowWeight() {
        return ORowWeight;
    }

    public void setORowWeight(int ORowWeight) {
        this.ORowWeight = ORowWeight;
    }

    public int getOColumnWeight() {
        return OColumnWeight;
    }

    public void setOColumnWeight(int OColumnWeight) {
        this.OColumnWeight = OColumnWeight;
    }

    public int getORightCornerToLeftWeight() {
        return ORightCornerToLeftWeight;
    }

    public void setORightCornerToLeftWeight(int ORightCornerToLeftWeight) {
        this.ORightCornerToLeftWeight = ORightCornerToLeftWeight;
    }

    public int getOLeftCornerToRightWeight() {
        return OLeftCornerToRightWeight;
    }

    public void setOLeftCornerToRightWeight(int OLeftCornerToRightWeight) {
        this.OLeftCornerToRightWeight = OLeftCornerToRightWeight;
    }

}
