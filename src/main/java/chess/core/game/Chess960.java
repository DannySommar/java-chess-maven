package chess.core.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import chess.core.CastlingRights;
import chess.core.Colour;
import chess.core.Position;
import chess.core.piece.*;

public class Chess960 extends Game
{

    public Chess960(String p1, String p2)
    {
        super(p1, p2);
    }

    
    @Override
    protected void initializePosition()
    {
        Piece[][] board = new Piece[8][8];

        for (int file = 0; file < 8; file++) // pawns
        {
        board[file][1] = new Pawn(Colour.WHITE);
        board[file][6] = new Pawn(Colour.BLACK);
        }

        Random random = new Random();
        int kingFile = random.nextInt(6) + 1; // 1 to 6

        System.out.println("King file: " + kingFile);

        int rookQueenFile = random.nextInt(kingFile);
        System.out.println("King rook: " + rookQueenFile);
        int rookKingFile = random.nextInt(7-kingFile) + kingFile+1;
        System.out.println("Queen rook: " + rookKingFile);

        int bishop1, bishop2;
        do
        {
            bishop1 = random.nextInt(8);
            bishop2 = random.nextInt(8);
        }
        while   (bishop1 == bishop2 ||                                                          // 2 diffrent files for bishops
                (bishop1 % 2) == (bishop2 % 2) ||                                               // they must not be the same colour
                bishop1 == rookKingFile || bishop1 == kingFile || bishop1 == rookQueenFile ||
                bishop2 == rookKingFile || bishop2 == kingFile || bishop2 == rookQueenFile);    // and not be on other pieces

        // queen and horseys in remaining positions
        List<Integer> remainingPositions = new ArrayList<>();
        for (int i = 0; i < 8; i++)
        {
            if (i != rookKingFile && i != rookQueenFile && i != kingFile && i != bishop1 && i != bishop2)
            {
                remainingPositions.add(i);
            }
        }
        Collections.shuffle(remainingPositions);
        
        //white pieces
        board[rookKingFile][0] = new Rook(Colour.WHITE);
        board[rookQueenFile][0] = new Rook(Colour.WHITE);
        board[kingFile][0] = new King(Colour.WHITE);
        board[bishop1][0] = new Bishop(Colour.WHITE);
        board[bishop2][0] = new Bishop(Colour.WHITE);
        
        board[remainingPositions.get(0)][0] = new Queen(Colour.WHITE);
        board[remainingPositions.get(1)][0] = new Horse(Colour.WHITE);
        board[remainingPositions.get(2)][0] = new Horse(Colour.WHITE);

        CastlingRights castlingRightsWhite = CastlingRights.forChess960(kingFile, rookKingFile, rookQueenFile);

        //black pieces
        board[rookKingFile][7] = new Rook(Colour.BLACK);
        board[rookQueenFile][7] = new Rook(Colour.BLACK);
        board[kingFile][7] = new King(Colour.BLACK);
        board[bishop1][7] = new Bishop(Colour.BLACK);
        board[bishop2][7] = new Bishop(Colour.BLACK);
        
        board[remainingPositions.get(0)][7] = new Queen(Colour.BLACK);
        board[remainingPositions.get(1)][7] = new Horse(Colour.BLACK);
        board[remainingPositions.get(2)][7] = new Horse(Colour.BLACK);


        CastlingRights castlingRightsBlack = CastlingRights.forChess960(kingFile, rookKingFile, rookQueenFile);
        
        
        this.startingPosition = new Position(board, Colour.WHITE, castlingRightsWhite, castlingRightsBlack, -1);
    }
}
