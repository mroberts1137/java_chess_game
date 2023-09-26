import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Brain
{
	static final int P = 1;
    static final int N = 3;
    static final int B = 4;
    static final int R = 5;
    static final int Q = 10;
    static final int K = 50;
    
    static final int ITERATIONS = 100;		//number of times to repeat monte carlo search
    static final int DEPTH = 1;				//number of ply (half turns) to search
	
    public List<Piece> bPiecesList = new ArrayList<Piece>();
    public List<Piece> wPiecesList = new ArrayList<Piece>();
    public List<Piece> virtualB = new ArrayList<Piece>();
    public List<Piece> virtualW = new ArrayList<Piece>();
    public List<Integer> movesList = new ArrayList<Integer>();
    
    public int selectedType;
    public int selectedX;
    public int selectedY;
    public int destinationX;
    public int destinationY;
    
    private Random rnd;
    public long seed;
    
    public int[][] board;
    public int[][] virtualBoard = new int[8][8];
    
    public int boardValue;
    
    public Game game;
    
    public Brain(int[][] board, long seed, Game game)
    {
    	this.game = game;
    	this.board = board;
    	this.seed = seed;
    	//rnd = new Random(seed);
    	rnd = new Random();
    }
    
    public void turnStart()
    {
    	Move move = new Move();
    	Move virtualMove = new Move();
    	int bestValue;
    	int tempValue;
    	
    	bPiecesList = getBPieces(Game.board);
    	wPiecesList = getWPieces(Game.board);
    	
    	numberOfMoves(Game.board, bPiecesList);
    	
    	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    	//AI goes here
    	
    	boardValue = evaluateBoard(Game.board, wPiecesList);
    	bestValue = boardValue;
    	
    	for (int i=0; i<ITERATIONS; i++)
    	{
    		setVirtualBoard(Game.board);
    		virtualB.clear();
    		virtualB = getBPieces(virtualBoard);
    		virtualW.clear();
    		virtualW = getWPieces(virtualBoard);
    		
    		virtualMove = randomMove(virtualBoard, virtualB);
    		
    		if (virtualMove.hasMove)
    		{
    			int opponentIndex;
				if (virtualBoard[virtualMove.destinationX][virtualMove.destinationY] > 0)
				{
    				opponentIndex = getIndex(virtualW, virtualMove.destinationX, virtualMove.destinationY);
    				virtualW.remove(opponentIndex);
				}
				
    			virtualBoard[virtualMove.selectedX][virtualMove.selectedY] = 0;
    			virtualBoard[virtualMove.destinationX][virtualMove.destinationY] = virtualMove.selectedType;
    			virtualB.get(virtualMove.index).setX(destinationX);
    			virtualB.get(virtualMove.index).setY(destinationY);
    			tempValue = evaluateBoard(virtualBoard, virtualW);
    			System.out.println("Iteration: " + i + ", (" + virtualMove.selectedX + ", " + virtualMove.selectedY + "), Value: " + tempValue);
	    		if (tempValue <= bestValue || i==0)
	    		{
	    			move = virtualMove;
	    			bestValue = tempValue;
	    		}
    		}
    		else
    		{
    			break;
    		}
    	}
    	
    	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    	
    	if (move.hasMove)
    	{
    		Game.board[move.selectedX][move.selectedY] = 0;
    		Game.board[move.destinationX][move.destinationY] = move.selectedType;
    	}
    	
    	virtualW.clear();
		virtualW = getWPieces(virtualBoard);
    	boardValue = evaluateBoard(Game.board, virtualW);
    	System.out.println("Board Value: " + boardValue);
    	
    	game.computerTurnEnd();
    }
    
    public Move randomMove(int[][] b, List<Piece> list)
    {
    	//Returns randomly selected piece type, x, y, and randomly selected move x,y.
    	
    	Move move = new Move();
    	
    	movesList.clear();
    	
    	while(list.size() > 0 && movesList.size() == 0)
    	{
	    	int randomPiece = rnd.nextInt(list.size());
	    	move.selectedType = list.get(randomPiece).getType();
	    	move.selectedX = list.get(randomPiece).getX();
	    	move.selectedY = list.get(randomPiece).getY();
	    	move.index = randomPiece;
	    	
	    	getMoves(b, list, randomPiece);
	    	
	    	if (movesList.size() == 0)
	    		list.remove(randomPiece);
	    }
    	
    	if (movesList.size() > 0)
	    {
    		move.hasMove = true;
	    	int randomSquare = rnd.nextInt(movesList.size());
	    	move.destinationX = movesList.get(randomSquare)%8;
	    	move.destinationY = movesList.get(randomSquare)/8;
    	}
    	
    	return move;
    }
    
    public int numberOfMoves(int[][] b, List<Piece> list)
    {
    	int moves = 0;
    	
    	for (int i=0; i<list.size(); i++)
    	{
    		getMoves(b, list, i);
    		moves += movesList.size();
    	}
    	
    	System.out.println("Number of moves: " + moves);
    	return moves;
    }
    
    public List<Piece> getBPieces(int[][] b)
	{
		List<Piece> list = new ArrayList<Piece>();
		for (int j=0; j<8; j++)
    	{
    		for (int i=0; i<8; i++)
    	    {
	    		if (b[i][j] < 0)
	    			list.add(new Piece(b[i][j], i, j, 0));
    	    }
    	}
		return list;
	}
    
    public List<Piece> getWPieces(int[][] b)
	{
		List<Piece> list = new ArrayList<Piece>();
		for (int j=0; j<8; j++)
    	{
    		for (int i=0; i<8; i++)
    	    {
	    		if (b[i][j] > 0)
	    			list.add(new Piece(b[i][j], i, j, 0));
    	    }
    	}
		return list;
	}
    
    public int getIndex(List<Piece> list, int x, int y)
    {
    	int index = 0;
    	for (int i=0; i<list.size(); i++)
    	{
    		if (x == list.get(i).getX() && y == list.get(i).getY())
    		{
    			index = i;
    			break;
    		}
    	}
    	return index;
    }
	
	public int evaluateBoard(int[][] b, List<Piece> opponent)
	{
		int[][] danger = new int[8][8];
		for (int i=0; i<opponent.size(); i++)
		{
			getJumps(b, opponent, i);
			if (movesList.size() > 0)
				for (int j = 0; j<movesList.size(); j++)
				{
					danger[movesList.get(j)%8][movesList.get(j)/8] = 1;
				}
		}
		
		int value = 0;
		for (int j=0; j<8; j++)
    	{
			for (int i=0; i<8; i++)
    	    {
    			if (danger[i][j] != 1)
    				value += b[i][j];
    	    }
    	}
		
		return value;
	}
	
	public void getMoves(int[][] b, List<Piece> list, int index)
    {
    	movesList.clear();
    	movesList = list.get(index).getMoves(b);
    }
	
	public void getJumps(int[][] b, List<Piece> list, int index)
    {
    	movesList.clear();
    	movesList = list.get(index).getJumps(b);
    }
	
	public void setVirtualBoard(int[][] b)
	{
		for (int j=0; j<8; j++)
    	{
    		for (int i=0; i<8; i++)
    	    {
	    		virtualBoard[i][j] = b[i][j];
    	    }
    	}
	}
	
}
