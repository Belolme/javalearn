import fj.*;
import fj.data.Array;
import fj.data.List;

import java.util.Scanner;

/**
 * Created by billin on 16-7-1.
 * Game for tic tac toe
 */
public class TicTacToeGame {

    private F<ChessBoard, Array<Position>> mGetChessBoardPosition
            = chessboard -> Array.range(0, ChessBoard.CHESSBOARD_WIDTH)
            .map(i -> new Position(i, 0))
            .bind(position -> Array.range(0, ChessBoard.CHESSBOARD_WIDTH).map(j -> new Position(position.getRow(), j)));


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

    private F<ChessBoard, Integer> mChessUnitCount
            = chessBoard -> mGetChessBoardPosition.f(chessBoard).filter(chessBoard::isFill).length();

//    private F<ChessBoard, Boolean> mIsCornerX
//            = chessBoard -> mChessUnitCount.f(chessBoard) == 3
//            && chessBoard.getmComputerChess() == ChessUnit.O
//            && chessBoard.getChess(new Position(1, 1)) == chessBoard.getmComputerChess()
//            && (mGetChessBoardPosition
//            .f(chessBoard)
//            .filter(chessBoard::isFill)
//            .forall(position -> mGetCornerLineArray.f(new Position(1, 1), mLeftToRight1, mLeftToRight2).exists(position::equals))
//            || mGetChessBoardPosition
//            .f(chessBoard)
//            .filter(chessBoard::isFill)
//            .forall(position -> mGetCornerLineArray.f(new Position(1, 1), mRightToLeft1, mRightToLeft2).exists(position::equals)));

    /**
     * 通过棋谱进行行棋
     * if haven't position will return (-1, -1)
     */
//    private F<ChessBoard, Position> mGetMyselfPosition
//            = chessBoard -> {
////        Array<Position> centerOrCorner = mGetChessBoardPosition.f(chessBoard)
////                .filter(position -> position.getRow()==position.getCol())
////                .append(Array.array(new Position(2,0), new Position(0, 2)));
//
//        Array<Position> threatList = mGetChessBoardPosition.f(chessBoard)
//                .filter(position -> mIsThreat.f(chessBoard.getChessBoard()[position.getRow()][position.getCol()]).f(chessBoard.getmOppositeChess()));
//
//        Array<Position> oWeight1List = mGetChessBoardPosition.f(chessBoard)
//                .filter(position -> mIsWeight.f(chessBoard.getChessBoard()[position.getRow()][position.getCol()]).f(1).f(chessBoard.getmOppositeChess()));
//
//        Array<Position> mWeight1List = mGetChessBoardPosition.f(chessBoard)
//                .filter(position -> mIsWeight.f(chessBoard.getChessBoard()[position.getRow()][position.getCol()]).f(1).f(chessBoard.getmComputerChess()));
//
//        Array<Position> weight2List = mGetChessBoardPosition.f(chessBoard)
//                .filter(position -> mIsWeight.f(chessBoard.getChessBoard()[position.getRow()][position.getCol()]).f(2).f(chessBoard.getmComputerChess()));
//
//        Array<Position> weight3List = mGetChessBoardPosition.f(chessBoard)
//                .filter(position -> mIsWeight.f(chessBoard.getChessBoard()[position.getRow()][position.getCol()]).f(3).f(chessBoard.getmComputerChess()));
//
//        Array<Position> blankList = mGetChessBoardPosition.f(chessBoard)
//                .filter(p -> !chessBoard.isFill(p));
//
//        Random random = new Random(System.currentTimeMillis());
//
//        if (mChessUnitCount.f(chessBoard) == 1) {
//            return mGetChessBoardPosition.f(chessBoard)
//                    .filter(position -> position.getCol() != 1 && position.getRow() != 1)
//                    .append(Array.array(new Position(1, 1)))
//                    .filter(position -> !chessBoard.isFill(position))
//                    .reverse()
//                    .get(0);
//        }
//        //else if (mIsCornerX.f(chessBoard)) {
//        //  return new Position(1, 0);
//        //}
//        else if (mWeight1List.isNotEmpty()) {
//            return mWeight1List.get(0);
//        } else if (oWeight1List.isNotEmpty()) {
//            return oWeight1List.get(0);
//        } else if (threatList.isNotEmpty()) {
//            System.out.println(threatList + " Threat position");//Debug
//            return threatList.get(random.nextInt(threatList.length()));
//        } else if (weight2List.isNotEmpty()) {
////            System.out.println(weight2List + "weight2 " + weight2List.length());//Debug
//            return weight2List.get(random.nextInt(weight2List.length()));
//        } else if (weight3List.isNotEmpty()) {
//            return weight3List.get(random.nextInt(weight3List.length()));
//        } else if (blankList.isNotEmpty())
//            return blankList.get(random.nextInt(blankList.length()));
//        else
//            return new Position(-1, -1);
//    };


    private F<ChessBoard, Character> mIsTurn = chessBoard -> {
        if (mChessUnitCount.f(chessBoard) % 2 == 0)
            return ChessUnit.X;

        return ChessUnit.O;
    };


    private F<ChessBoard, F<Integer, Array<Position>>> mWeightPosition = chessBoard -> weight -> mGetChessBoardPosition.f(chessBoard).
            filter(position -> mIsWeight.f(chessBoard.getChessBoard()[position.getRow()][position.getCol()]).f(weight).f(ChessUnit.O) ||
                    mIsWeight.f(chessBoard.getChessBoard()[position.getRow()][position.getCol()]).f(weight).f(ChessUnit.X));


    private F<ChessBoard, F<Character, Array<Position>>> mThreatWeightPosition = chessBoard -> ox ->
            mGetChessBoardPosition.f(chessBoard).filter(position -> mIsThreat.f(chessBoard.getChessBoard()[position.getRow()][position.getCol()]).f(ox));


    private Array<ChessBoard> getIsomorphismChessBoard(ChessBoard chessBoard) {
        Array<ChessBoard> chessBoardArray = Array.array(chessBoard);

        for (int i = 0; i < 3; i++) {
            chessBoard = chessBoard.turnRight();
            chessBoardArray = chessBoardArray.append(Array.array(chessBoard));
        }
        return chessBoardArray;
    }


    private F<ChessBoard, F<ChessBoard, Boolean>> mIsomorphism = chessBoardOrg -> chessBoardDet -> Array.array(chessBoardOrg)
            .bind(this::getIsomorphismChessBoard)
            .exists(chessBoard -> chessBoard.equals(chessBoardDet));


    private List<ChessBoard> isomorphism(List<ChessBoard> chessBoards) {
        if (chessBoards.isEmpty() || chessBoards.length() == 1)
            return chessBoards;

//        for (int i = 0; i < chessBoards.length(); i++) {
//            for (int j = i + 1; j < chessBoards.length(); j++) {
//                System.out.println("i_________________________" + i);
//                chessBoards.foreach(chessBoard -> {
//                    chessBoard.printChessBoard();
//                    return Unit.unit();
//                });
//                System.out.println("------------------------------" + i);
//                if (isomorphism.f(chessBoards.get(i)).f(chessBoards.get(j))) {
//                    System.out.println("Hello j= " + j);
//
//                }
//            }
//        }

        List<ChessBoard> chessBoardList = isomorphism(chessBoards.tail());
        if (chessBoardList.exists(chessBoard -> mIsomorphism.f(chessBoard).f(chessBoards.head()))) {
            return chessBoardList;
        }

        return chessBoardList.conss(chessBoards.head());
    }


    private F<ChessBoard, Array<ChessBoard>> mSon = chessBoard -> {

        Array<Position> positions;
        F<Array<Position>, Array<ChessBoard>> getChessboard = positions1 -> positions1.map(position -> {
            ChessBoard chessBoard1 = chessBoard.clone();
            chessBoard1.setChess(position, mIsTurn.f(chessBoard));
            return chessBoard1;
        });

        positions = mWeightPosition.f(chessBoard).f(1);
        if (positions.length() > 0) {
            return getChessboard.f(positions);
        }

        //这个得要再考虑
        positions = mThreatWeightPosition.f(chessBoard).f(ChessUnit.X).append(mThreatWeightPosition.f(chessBoard).f(ChessUnit.O));
        if (positions.length() > 0) {
            return getChessboard.f(positions);
        }

        positions = mWeightPosition.f(chessBoard).f(2);
        if (positions.length() > 0)
            return getChessboard.f(positions);

        positions = mGetChessBoardPosition.f(chessBoard).filter(position -> !chessBoard.isFill(position));
        return isomorphism(getChessboard.f(positions).toList()).toArray();
    };


    private Integer chessBoardValues(ChessBoard chessBoard) {

        if (!chessBoard.isOver()) {
            if (mIsTurn.f(chessBoard) == chessBoard.getmComputerChess()) {
                return mSon.f(chessBoard).map(this::chessBoardValues).toList().maximum(Ord.intOrd);
            } else {
                return mSon.f(chessBoard).map(this::chessBoardValues).toList().minimum(Ord.intOrd);
            }
        }

        if (chessBoard.isWin(chessBoard.getmComputerChess())) {
            return Integer.MAX_VALUE;
        } else if (chessBoard.isWin(chessBoard.getmOppositeChess())) {
            return Integer.MIN_VALUE;
        } else {
            return 0;
        }
    }


    private ChessBoard getAIPosition() {
        return mSon.f(mChessBoard).toList().map(chessBoard -> P.p(chessBoard, chessBoardValues(chessBoard)))
                .sort(Ord.ord(p1 -> p2 -> {
                    final int v = p1._2().compareTo(p2._2());
                    return v < 0 ? Ordering.LT : v == 0 ? Ordering.EQ : Ordering.GT;
                })).map(p -> {
//                    System.out.println(chessBoardValues(p._1()) + " hello");     //Debug
//                    p._1().printChessBoard();
                    return p;
                }).reverse().head()._1();
    }


    private ChessBoard mChessBoard;


    private ChessBoard.ResultExecutor mResultExecutor = new ChessBoard.ResultExecutor() {
        @Override
        public void iWin() {
            System.out.println();
            System.out.println("You win");
            mChessBoard.printChessBoard();
            resumeGame();
        }

        @Override
        public void flat() {
            System.out.println();
            System.out.println("The match is flat");
            mChessBoard.printChessBoard();
            resumeGame();
        }

        @Override
        public void iLost() {
            System.out.println();
            System.out.println("You lost");
            mChessBoard.printChessBoard();
            resumeGame();
        }

        @Override
        public void notOver() {
//                ChessBoard chessBoard1 = chessBoard.clone();
//                chessBoard1.setChess(new Position(1, 1));
//                chessBoard1.printChessBoard();
            mChessBoard.printChessBoard();
            System.out.println();
            run();
        }
    };


    private Position getPosition() {
        Scanner scanner = new Scanner(System.in);
        int row = scanner.nextInt();
        int col = scanner.nextInt();
        return new Position(row, col);
    }


    private void run() {
        if (mIsTurn.f(mChessBoard) == mChessBoard.getmOppositeChess()) {
            mChessBoard.setChess(getPosition(), mChessBoard.getmOppositeChess()).getmResultExecutor().execute();
        } else {
//            long start = System.currentTimeMillis();
            mChessBoard = getAIPosition();
//            long end = System.currentTimeMillis();
//            System.out.println("Time = " + (end - start)); //Debug

            mChessBoard.getmResultExecutor().execute();
        }
    }


    private void startGame() {
        System.out.println("请输入两个数字，第一个代表行数，第二个代表列数（0 为起始数字，例如：0 0）");
        mChessBoard = new ChessBoard(ChessUnit.O);
        mChessBoard.setmResultExecutor(mResultExecutor);
        run();
    }


    private void resumeGame() {
        System.out.println("Press 'Y' to resume game; Press 'N' to exit");
        Scanner scanner = new Scanner(System.in);
        String tmp = scanner.next();
        if (tmp.equals("Y") || tmp.equals("y"))
            startGame();
        else
            System.exit(0);
    }


    public static void main(String[] args) {
        TicTacToeGame ticTacToeGame = new TicTacToeGame();
        ticTacToeGame.startGame();

//        ChessBoard chessBoard = new ChessBoard(ChessUnit.X);
//        chessBoard.setChess(new Position(0, 2), ChessUnit.X);
//
//        ticTacToeGame.mIsomorphism(Array.array(chessBoard, chessBoard.turnRight(), chessBoard.turnRight().turnRight()).toList()).foreach(chessBoard1 -> {
//            chessBoard1.printChessBoard();
//            return Unit.unit();
//        });

//        F<ChessBoard, F<ChessBoard, Boolean>> isomorphism = chessBoardOrg -> chessBoardDet -> {
//
//            ChessBoard turn = chessBoardOrg;
//            for (int c = 0; c < 3; c++) {
//                turn = turn.turnRight();
//                if (turn.equals(chessBoardDet))
//                    return true;
//            }
//            return false;
//        };
//
//        System.out.println(isomorphism.f(chessBoard).f(chessBoard.turnRight()));

//        System.out.println(ticTacToeGame.chessBoardValues(chessBoard));
//        chessBoard.printChessBoard();
//        System.out.println("----------------------------------");
//
//        ticTacToeGame.mSon.f(chessBoard).foreach(chessBoard1 -> {
//            System.out.println(ticTacToeGame.chessBoardValues(chessBoard1));
//            chessBoard1.updatemResultExecutorValue();
//
//            chessBoard1.printChessBoard();
//            System.out.println();
//            return Unit.unit();
//        });
    }
}