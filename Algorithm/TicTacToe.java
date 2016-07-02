import java.util.Scanner;

/**
 * Created by billin on 16-6-12.
 * A AI of TicTacToe
 */
public class TicTacToe {

    private static ChessBoard chessBoard;

    private static Position getPosition() {
        Scanner scanner = new Scanner(System.in);
        int row = scanner.nextInt();
        int col = scanner.nextInt();
        return new Position(row, col);
    }

    private static void startGame() {
        System.out.println("请输入两个数字，第一个代表行数，第二个代表列数（0 为起始数字，例如：0 0）");
        chessBoard = new ChessBoard(ChessUnit.O);
        chessBoard.printChessBoard();

        chessBoard.setmResultExecutor(new ChessBoard.ResultExecutor() {
            @Override
            public void iWin() {
                System.out.println("You win");
                chessBoard.printChessBoard();
                resumeGame();
            }

            @Override
            public void flat() {
                System.out.println("The match is flat");
                chessBoard.printChessBoard();
                resumeGame();
            }

            @Override
            public void iLost() {
                System.out.println("You lost");
                chessBoard.printChessBoard();
                resumeGame();
            }

            @Override
            public void notOver() {
//                ChessBoard chessBoard1 = chessBoard.clone();
//                chessBoard1.setChess(new Position(1, 1));
//                chessBoard1.printChessBoard();
                chessBoard.printChessBoard();
                chessBoard.setChess(getPosition(), chessBoard.getmOppositeChess()).getmResultExecutor().execute();
            }
        });

        chessBoard.setChess(getPosition(), chessBoard.getmOppositeChess()).getmResultExecutor().execute();
    }

    private static void resumeGame() {
        System.out.println("Press 'Y' to resume game; Press 'N' to exit");
        Scanner scanner = new Scanner(System.in);
        String tmp = scanner.next();
        if (tmp.equals("Y") || tmp.equals("y"))
            startGame();
        else
            System.exit(0);
    }

    public static void main(String[] args) {


        startGame();

//        for (int i = 0; i < 5; i++) {
//            Scanner scanner = new Scanner(System.in);
//            int row = scanner.nextInt();
//            int col = scanner.nextInt();
//            Position position = new Position(row, col);
//
//            ChessBoard.ResultExecutor resultExecutor = chessBoard.setChess(position);
//            System.out.println(resultExecutor.getmResult());
//            chessBoard.printChessBoard();
//            resultExecutor.execute();
//        }
    }
}
//
//class ChessGame {
//
//    public void test() {
//        ChessBoard chessBoard = new ChessBoard(ChessUnit.X);
////        chessBoard.printChessUnitWeight(new Position(0, 1));
//        chessBoard.printChessBoard();
//        System.out.println();
//
//        chessBoard.setChess(new Position(1, 1));
////        chessBoard.printChessUnitWeight(new Position(0, 1));
//        chessBoard.printChessBoard();
//        System.out.println();
//
//        chessBoard.setChess(new Position(1, 2));
////        chessBoard.printChessUnitWeight(new Position(0, 1));
//        chessBoard.printChessBoard();
//        System.out.println();
//
//        chessBoard.setChess(new Position(2, 0));
////        chessBoard.printChessUnitWeight(new Position(0, 1));
//        chessBoard.printChessBoard();
//
//        chessBoard.setChess(new Position(2, 1));
//        chessBoard.printChessBoard();
//    }
//}