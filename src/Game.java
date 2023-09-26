import java.awt.BorderLayout;
//import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel implements Runnable, MouseListener, MouseMotionListener
{
    public final int FRAME_WIDTH = 800;
    public final int FRAME_HEIGHT = 800;
    public final int BOARD_X = 100;
    public final int BOARD_Y = 100;
    public final int BOARD_SIZE = 600;
    public final int SQUARE_SIZE = BOARD_SIZE/8;
    
    static final int P = 1;
    static final int N = 3;
    static final int B = 4;
    static final int R = 5;
    static final int Q = 10;
    static final int K = 50;
    static int[][] board = {{-R, -P, 0, 0, 0, 0, P, R},
			{-N, -P, 0, 0, 0, 0, P, N},
			{-B, -P, 0, 0, 0, 0, P, B},
			{-K, -P, 0, 0, 0, 0, P, K},
			{-Q, -P, 0, 0, 0, 0, P, Q},
			{-B, -P, 0, 0, 0, 0, P, B},
			{-N, -P, 0, 0, 0, 0, P, N},
			{-R, -P, 0, 0, 0, 0, P, R}};
    
    static int playerIsWhite = 0;			//Takes 0,1: 0 for player is white, 1 for player is black.
    
    static int mouseX = 0;
    static int mouseY = 0;
    static int mouseTileX = 0;
    static int mouseTileY = 0;
    static boolean click = false;
    static boolean playerTurn = true;
    static boolean pieceSelected = false;
    static int selectedX, selectedY, selectedType, selectedIndex;
    
    static int turn = 1;
    
    public List<Piece> wPiecesList = new ArrayList<Piece>();
    public List<Piece> bPiecesList = new ArrayList<Piece>();
    public List<Integer> movesList = new ArrayList<Integer>();
    

    JFrame frame;
    
    Brain brain;
    public static Long seed = (long) 3.14;

    boolean running = false;
    
    Image chessPieceImage = new ImageIcon("chess_pieces.png").getImage();

    public Game()
    {
        setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setMaximumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));

        frame = new JFrame("Chess");
        
        brain = new Brain(board, seed, this);

        frame.setLayout(new BorderLayout());
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.add(this, BorderLayout.CENTER);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.GRAY);

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

    }

    public synchronized void start()
    {
        running = true;
        new Thread(this).start();
    }

    public synchronized void stop()
    {
        running = false;
    }

    public void init()
    {
    	
    	for (int j=0; j<8; j++)
    	{
    		for (int i=0; i<8; i++)
    	    {
    		if (board[i][j] > 0)
    			wPiecesList.add(new Piece(board[i][j], i, j, i+8*j));
    		if (board[i][j] < 0)
    			bPiecesList.add(new Piece(board[i][j], i, j, i+8*j));
    	    }
    	}
    	
    	System.out.println("Turn " + Game.turn);
    }

    public void run()
    {
        init();
    }

    public static void main(String[] args)
    {
        new Game().start();
    }

    public void getPieces()
	{
    	wPiecesList.clear();
		bPiecesList.clear();
		for (int j=0; j<8; j++)
    	{
    		for (int i=0; i<8; i++)
    	    {
    			if (Game.board[i][j] > 0)
	    			wPiecesList.add(new Piece(Game.board[i][j], i, j, 0));
	    		if (Game.board[i][j] < 0)
	    			bPiecesList.add(new Piece(Game.board[i][j], i, j, 0));
    	    }
    	}
	}
    
    public void getMoves(int[][] b, List<Piece> list, int index)
    {
    	movesList.clear();
    	movesList = list.get(index).getMoves(b);
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
    
    public void computerTurnEnd()
	{
    	turn ++;
    	System.out.println("Turn " + turn);
    	movesList.clear();
    	
    	getPieces();
    	
		playerTurn = true;
		
		repaint();
	}
    
    
    @Override
    public void mouseDragged(MouseEvent e)
    {
    	click = true;
    	mouseX = e.getX();
    	mouseY = e.getY();
    	repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e){}

    @Override
    public void mouseClicked(MouseEvent e){}

    @Override
    public void mouseEntered(MouseEvent e){}

    @Override
    public void mouseExited(MouseEvent e){}

    @Override
    public void mousePressed(MouseEvent e)
    {
    	click = true;
    	mouseX = e.getX();
    	mouseY = e.getY();
    	mouseTileX = (mouseX-BOARD_X)/SQUARE_SIZE;
    	mouseTileY = (mouseY-BOARD_Y)/SQUARE_SIZE;
    	
    	if (mouseTileX >= 0 && mouseTileX < 8 && mouseTileY >= 0 && mouseTileY < 8)
    	{
    		if (playerTurn && board[mouseTileX][mouseTileY] > 0)
    		{
    			pieceSelected = true;
    			selectedX = mouseTileX;
    			selectedY = mouseTileY;
    			selectedType = board[mouseTileX][mouseTileY];
    			selectedIndex = getIndex(wPiecesList, selectedX, selectedY);
    			
    			getMoves(board, wPiecesList, selectedIndex);
    		}
    		if (playerTurn && movesList.contains(mouseTileX + 8*mouseTileY))
    		{
    			pieceSelected = false;
    			board[selectedX][selectedY] = 0;
    			board[mouseTileX][mouseTileY] = selectedType;
    			
    			
    			playerTurn = false;
    			brain.turnStart();
    		}
    	}
    	else
    		pieceSelected = false;
    	
    	repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
    	click = false;
    	repaint();
    }
    
    
    public void paintComponent(Graphics g)
    {
    	super.paintComponent(g);
        for (int i=0; i<8; i++)
        {
            for (int j=0; j<8; j++)
            {
                if ((i+j)%2 == 0)
                    g.setColor(new Color(240, 230, 210));
                else
                    g.setColor(new Color(80, 40, 40));
                g.fillRect(BOARD_X+i*SQUARE_SIZE, BOARD_Y+j*SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
        
        if (playerTurn && pieceSelected)// && click)
        {
        	g.setColor(Color.GREEN);
        	g.fillRect(BOARD_X+selectedX*SQUARE_SIZE+2, BOARD_Y+selectedY*SQUARE_SIZE+2, SQUARE_SIZE-4, SQUARE_SIZE-4);
        	
        	g.setColor(new Color(180, 60, 40));
        	for (int m: movesList)
            {
        		int moveTileX = m%8;
        		int moveTileY = m/8;
        		g.fillRect(BOARD_X+moveTileX*SQUARE_SIZE+2, BOARD_Y+moveTileY*SQUARE_SIZE+2, SQUARE_SIZE-4, SQUARE_SIZE-4);
            }
        }
        
        for (int i=0; i<8; i++)
        {
            for (int j=0; j<8; j++)
            {
            	int spriteX = -1;
                int spriteY = -1;
                switch (board[i][j])
                {
                	case P: spriteX = 5; spriteY = playerIsWhite;
                		break;
                	case N: spriteX = 3; spriteY = playerIsWhite;
            			break;
                	case B: spriteX = 2; spriteY = playerIsWhite;
            			break;
                	case R: spriteX = 4; spriteY = playerIsWhite;
            			break;
                	case Q: spriteX = 0; spriteY = playerIsWhite;
            			break;
                	case K: spriteX = 1; spriteY = playerIsWhite;
            			break;
                	case -P: spriteX = 5; spriteY = 1-playerIsWhite;
            			break;
                	case -N: spriteX = 3; spriteY = 1-playerIsWhite;
        				break;
                	case -B: spriteX = 2; spriteY = 1-playerIsWhite;
        				break;
                	case -R: spriteX = 4; spriteY = 1-playerIsWhite;
        				break;
                	case -Q: spriteX = 0; spriteY = 1-playerIsWhite;
        				break;
                	case -K: spriteX = 1; spriteY = 1-playerIsWhite;
        				break;
                }
        				
        			if (spriteX != -1 && spriteY != -1)
        				g.drawImage(chessPieceImage, BOARD_X+i*SQUARE_SIZE, BOARD_Y+j*SQUARE_SIZE, BOARD_X+(i+1)*SQUARE_SIZE, BOARD_Y+(j+1)*SQUARE_SIZE, spriteX*83, spriteY*83, (spriteX+1)*83, (spriteY+1)*83, this);
            }
        }
        
        if (playerTurn)
        	g.drawImage(chessPieceImage, 10, 10, 10+SQUARE_SIZE, 10+SQUARE_SIZE, 83, 83*playerIsWhite, 2*83, 83*(1+playerIsWhite), this);
        else
        	g.drawImage(chessPieceImage, 10, 10, 10+SQUARE_SIZE, 10+SQUARE_SIZE, 83, 83*(1-playerIsWhite), 2*83, 83*(2-playerIsWhite), this);
        
        if (click)
        {
        	g.setColor(Color.BLACK);
        	g.drawOval(mouseX-8,  mouseY-8, 16, 16);
        	g.setColor(Color.WHITE);
        	g.drawOval(mouseX-9,  mouseY-9, 18, 18);
        }
    }
}