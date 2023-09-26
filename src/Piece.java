import java.util.ArrayList;
import java.util.List;

public class Piece
{
	public int x, y;
	public int type;
	public int id;
	
	Piece(int type, int x, int y, int id)
	{
		this.x = x;
		this.y = y;
		this.type = type;
		this.id = id;
	}
	
	public int getType()
	{
		return type;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public int getID()
	{
		return id;
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	public void setY(int y)
	{
		this.y = y;
	}
	
	public List<Integer> getMoves(int[][] b)
    {
		List<Integer> movesList = new ArrayList<Integer>();
    	
    	//Pawn
    	if (type == -Game.P)
    	{
    		if (y+1<8 && b[x][y+1]==0)
    			movesList.add(x + 8*(y+1));
    		if (y==1 && b[x][y+1]==0 && b[x][y+2]==0)
    			movesList.add(x + 8*(y+2));
    		if (y+1<8 && x+1<8 && b[x+1][y+1]>0)
    			movesList.add(x+1 + 8*(y+1));
    		if (y+1<8 && x-1>=0 && b[x-1][y+1]>0)
    			movesList.add(x-1 + 8*(y+1));
    	}
    	//Knight
    	if (type == -Game.N)
    	{
    		if (x+2<8 && y-1>=0 && b[x+2][y-1]>=0)
    			movesList.add(x+2 + 8*(y-1));
    		if (x+1<8 && y-2>=0 && b[x+1][y-2]>=0)
    			movesList.add(x+1 + 8*(y-2));
    		if (x-1>=0 && y-2>=0 && b[x-1][y-2]>=0)
    			movesList.add(x-1 + 8*(y-2));
    		if (x-2>=0 && y-1>=0 && b[x-2][y-1]>=0)
    			movesList.add(x-2 + 8*(y-1));
    		if (x-2>=0 && y+1<8 && b[x-2][y+1]>=0)
    			movesList.add(x-2 + 8*(y+1));
    		if (x-1>=0 && y+2<8 && b[x-1][y+2]>=0)
    			movesList.add(x-1 + 8*(y+2));
    		if (x+1<8 && y+2<8 && b[x+1][y+2]>=0)
    			movesList.add(x+1 + 8*(y+2));
    		if (x+2<8 && y+1<8 && b[x+2][y+1]>=0)
    			movesList.add(x+2 + 8*(y+1));
    	}
    	
    	//Bishop
    	if (type == -Game.B)
    	{
    		for (int u=-1; u<=1; u+=2)
    		for (int v=-1; v<=1; v+=2)
    		{
    			for (int i=1; i<8; i++)
    			{
    				if (x+u*i>=0 && x+u*i<8 && y+v*i>=0 && y+v*i<8)
    				{
    					if (b[x+u*i][y+v*i] == 0)
    						movesList.add(x+u*i + 8*(y+v*i));
    					if (b[x+u*i][y+v*i] < 0)
    						break;
    					if (b[x+u*i][y+v*i] > 0)
    					{
    						movesList.add(x+u*i + 8*(y+v*i));
    						break;
    					}
    				}
    				else break;
    			}
    		}
    	}
    	
    	//Rook
    	if (type == -Game.R)
    	{
    		for (int u=-1; u<=1; u+=2)
    		{
    			for (int i=1; i<8; i++)
    			{
    				if (x+u*i>=0 && x+u*i<8)
    				{
    					if (b[x+u*i][y] == 0)
    						movesList.add(x+u*i + 8*y);
    					if (b[x+u*i][y] < 0)
    						break;
    					if (b[x+u*i][y] > 0)
    					{
    						movesList.add(x+u*i + 8*y);
    						break;
    					}
    				}
    				else break;
    			}
    			for (int i=1; i<8; i++)
    			{
    				if (y+u*i>=0 && y+u*i<8)
    				{
    					if (b[x][y+u*i] == 0)
    						movesList.add(x + 8*(y+u*i));
    					if (b[x][y+u*i] < 0)
    						break;
    					if (b[x][y+u*i] > 0)
    					{
    						movesList.add(x + 8*(y+u*i));
    						break;
    					}
    				}
    				else break;
    			}
    		}
    	}
    	
    	//Queen
    	if (type == -Game.Q)
    	{
    		for (int u=-1; u<=1; u++)
    		for (int v=-1; v<=1; v++)
    		{
    			for (int i=1; i<8; i++)
    			{
    				if (x+u*i>=0 && x+u*i<8 && y+v*i>=0 && y+v*i<8)
    				{
    					if (b[x+u*i][y+v*i] == 0)
    						movesList.add(x+u*i + 8*(y+v*i));
    					if (b[x+u*i][y+v*i] < 0)
    						break;
    					if (b[x+u*i][y+v*i] > 0)
    					{
    						movesList.add(x+u*i + 8*(y+v*i));
    						break;
    					}
    				}
    				else break;
    			}
    		}
    	}
    	//King
    	if (type == -Game.K)
    	{
    		for (int u=-1; u<=1; u++)
    		for (int v=-1; v<=1; v++)
    		{
    			if (x+u>=0 && x+u<8 && y+v>=0 && y+v<8)
    			{
    				if (b[x+u][y+v] >= 0)
    					movesList.add(x+u + 8*(y+v));
    			}
    		}
    	}
    	
    	//Pawn
    	if (type == Game.P)
    	{
    		if (y-1>=0 && b[x][y-1]==0)
    			movesList.add(x + 8*(y-1));
    		if (y==6 && b[x][y-1]==0 && b[x][y-2]==0)
    			movesList.add(x + 8*(y-2));
    		if (y-1>=0 && x+1<8 && b[x+1][y-1]<0)
    			movesList.add(x+1 + 8*(y-1));
    		if (y-1>=0 && x-1>=0 && b[x-1][y-1]<0)
    			movesList.add(x-1 + 8*(y-1));
    	}
    	//Knight
    	if (type == Game.N)
    	{
    		if (x+2<8 && y-1>=0 && b[x+2][y-1]<=0)
    			movesList.add(x+2 + 8*(y-1));
    		if (x+1<8 && y-2>=0 && b[x+1][y-2]<=0)
    			movesList.add(x+1 + 8*(y-2));
    		if (x-1>=0 && y-2>=0 && b[x-1][y-2]<=0)
    			movesList.add(x-1 + 8*(y-2));
    		if (x-2>=0 && y-1>=0 && b[x-2][y-1]<=0)
    			movesList.add(x-2 + 8*(y-1));
    		if (x-2>=0 && y+1<8 && b[x-2][y+1]<=0)
    			movesList.add(x-2 + 8*(y+1));
    		if (x-1>=0 && y+2<8 && b[x-1][y+2]<=0)
    			movesList.add(x-1 + 8*(y+2));
    		if (x+1<8 && y+2<8 && b[x+1][y+2]<=0)
    			movesList.add(x+1 + 8*(y+2));
    		if (x+2<8 && y+1<8 && b[x+2][y+1]<=0)
    			movesList.add(x+2 + 8*(y+1));
    	}
    	
    	//Bishop
    	if (type == Game.B)
    	{
    		for (int u=-1; u<=1; u+=2)
    		for (int v=-1; v<=1; v+=2)
    		{
    			for (int i=1; i<8; i++)
    			{
    				if (x+u*i>=0 && x+u*i<8 && y+v*i>=0 && y+v*i<8)
    				{
    					if (b[x+u*i][y+v*i] == 0)
    						movesList.add(x+u*i + 8*(y+v*i));
    					if (b[x+u*i][y+v*i] > 0)
    						break;
    					if (b[x+u*i][y+v*i] < 0)
    					{
    						movesList.add(x+u*i + 8*(y+v*i));
    						break;
    					}
    				}
    				else break;
    			}
    		}
    	}
    	
    	//Rook
    	if (type == Game.R)
    	{
    		for (int u=-1; u<=1; u+=2)
    		{
    			for (int i=1; i<8; i++)
    			{
    				if (x+u*i>=0 && x+u*i<8)
    				{
    					if (b[x+u*i][y] == 0)
    						movesList.add(x+u*i + 8*y);
    					if (b[x+u*i][y] > 0)
    						break;
    					if (b[x+u*i][y] < 0)
    					{
    						movesList.add(x+u*i + 8*y);
    						break;
    					}
    				}
    				else break;
    			}
    			for (int i=1; i<8; i++)
    			{
    				if (y+u*i>=0 && y+u*i<8)
    				{
    					if (b[x][y+u*i] == 0)
    						movesList.add(x + 8*(y+u*i));
    					if (b[x][y+u*i] > 0)
    						break;
    					if (b[x][y+u*i] < 0)
    					{
    						movesList.add(x + 8*(y+u*i));
    						break;
    					}
    				}
    				else break;
    			}
    		}
    	}
    	
    	//Queen
    	if (type == Game.Q)
    	{
    		for (int u=-1; u<=1; u++)
    		for (int v=-1; v<=1; v++)
    		{
    			for (int i=1; i<8; i++)
    			{
    				if (x+u*i>=0 && x+u*i<8 && y+v*i>=0 && y+v*i<8)
    				{
    					if (b[x+u*i][y+v*i] == 0)
    						movesList.add(x+u*i + 8*(y+v*i));
    					if (b[x+u*i][y+v*i] > 0)
    						break;
    					if (b[x+u*i][y+v*i] < 0)
    					{
    						movesList.add(x+u*i + 8*(y+v*i));
    						break;
    					}
    				}
    				else break;
    			}
    		}
    	}
    	//King
    	if (type == Game.K)
    	{
    		for (int u=-1; u<=1; u++)
    		for (int v=-1; v<=1; v++)
    		{
    			if (x+u>=0 && x+u<8 && y+v>=0 && y+v<8)
    			{
    				if (b[x+u][y+v] <= 0)
    					movesList.add(x+u + 8*(y+v));
    			}
    		}
    	}
    	
    	return movesList;
    }
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public List<Integer> getJumps(int[][] b)
    {
		List<Integer> movesList = new ArrayList<Integer>();
    	
    	//Pawn
    	if (type == -Game.P)
    	{
    		if (y+1<8 && x+1<8 && b[x+1][y+1]>0)
    			movesList.add(x+1 + 8*(y+1));
    		if (y+1<8 && x-1>=0 && b[x-1][y+1]>0)
    			movesList.add(x-1 + 8*(y+1));
    	}
    	//Knight
    	if (type == -Game.N)
    	{
    		if (x+2<8 && y-1>=0 && b[x+2][y-1]>=0)
    			movesList.add(x+2 + 8*(y-1));
    		if (x+1<8 && y-2>=0 && b[x+1][y-2]>=0)
    			movesList.add(x+1 + 8*(y-2));
    		if (x-1>=0 && y-2>=0 && b[x-1][y-2]>=0)
    			movesList.add(x-1 + 8*(y-2));
    		if (x-2>=0 && y-1>=0 && b[x-2][y-1]>=0)
    			movesList.add(x-2 + 8*(y-1));
    		if (x-2>=0 && y+1<8 && b[x-2][y+1]>=0)
    			movesList.add(x-2 + 8*(y+1));
    		if (x-1>=0 && y+2<8 && b[x-1][y+2]>=0)
    			movesList.add(x-1 + 8*(y+2));
    		if (x+1<8 && y+2<8 && b[x+1][y+2]>=0)
    			movesList.add(x+1 + 8*(y+2));
    		if (x+2<8 && y+1<8 && b[x+2][y+1]>=0)
    			movesList.add(x+2 + 8*(y+1));
    	}
    	
    	//Bishop
    	if (type == -Game.B)
    	{
    		for (int u=-1; u<=1; u+=2)
    		for (int v=-1; v<=1; v+=2)
    		{
    			for (int i=1; i<8; i++)
    			{
    				if (x+u*i>=0 && x+u*i<8 && y+v*i>=0 && y+v*i<8)
    				{
    					if (b[x+u*i][y+v*i] == 0)
    						movesList.add(x+u*i + 8*(y+v*i));
    					if (b[x+u*i][y+v*i] < 0)
    						break;
    					if (b[x+u*i][y+v*i] > 0)
    					{
    						movesList.add(x+u*i + 8*(y+v*i));
    						break;
    					}
    				}
    				else break;
    			}
    		}
    	}
    	
    	//Rook
    	if (type == -Game.R)
    	{
    		for (int u=-1; u<=1; u+=2)
    		{
    			for (int i=1; i<8; i++)
    			{
    				if (x+u*i>=0 && x+u*i<8)
    				{
    					if (b[x+u*i][y] == 0)
    						movesList.add(x+u*i + 8*y);
    					if (b[x+u*i][y] < 0)
    						break;
    					if (b[x+u*i][y] > 0)
    					{
    						movesList.add(x+u*i + 8*y);
    						break;
    					}
    				}
    				else break;
    			}
    			for (int i=1; i<8; i++)
    			{
    				if (y+u*i>=0 && y+u*i<8)
    				{
    					if (b[x][y+u*i] == 0)
    						movesList.add(x + 8*(y+u*i));
    					if (b[x][y+u*i] < 0)
    						break;
    					if (b[x][y+u*i] > 0)
    					{
    						movesList.add(x + 8*(y+u*i));
    						break;
    					}
    				}
    				else break;
    			}
    		}
    	}
    	
    	//Queen
    	if (type == -Game.Q)
    	{
    		for (int u=-1; u<=1; u++)
    		for (int v=-1; v<=1; v++)
    		{
    			for (int i=1; i<8; i++)
    			{
    				if (x+u*i>=0 && x+u*i<8 && y+v*i>=0 && y+v*i<8)
    				{
    					if (b[x+u*i][y+v*i] == 0)
    						movesList.add(x+u*i + 8*(y+v*i));
    					if (b[x+u*i][y+v*i] < 0)
    						break;
    					if (b[x+u*i][y+v*i] > 0)
    					{
    						movesList.add(x+u*i + 8*(y+v*i));
    						break;
    					}
    				}
    				else break;
    			}
    		}
    	}
    	//King
    	if (type == -Game.K)
    	{
    		for (int u=-1; u<=1; u++)
    		for (int v=-1; v<=1; v++)
    		{
    			if (x+u>=0 && x+u<8 && y+v>=0 && y+v<8)
    			{
    				if (b[x+u][y+v] >= 0)
    					movesList.add(x+u + 8*(y+v));
    			}
    		}
    	}
    	
    	//Pawn
    	if (type == Game.P)
    	{
    		if (y-1>=0 && x+1<8 && b[x+1][y-1]<0)
    			movesList.add(x+1 + 8*(y-1));
    		if (y-1>=0 && x-1>=0 && b[x-1][y-1]<0)
    			movesList.add(x-1 + 8*(y-1));
    	}
    	//Knight
    	if (type == Game.N)
    	{
    		if (x+2<8 && y-1>=0 && b[x+2][y-1]<=0)
    			movesList.add(x+2 + 8*(y-1));
    		if (x+1<8 && y-2>=0 && b[x+1][y-2]<=0)
    			movesList.add(x+1 + 8*(y-2));
    		if (x-1>=0 && y-2>=0 && b[x-1][y-2]<=0)
    			movesList.add(x-1 + 8*(y-2));
    		if (x-2>=0 && y-1>=0 && b[x-2][y-1]<=0)
    			movesList.add(x-2 + 8*(y-1));
    		if (x-2>=0 && y+1<8 && b[x-2][y+1]<=0)
    			movesList.add(x-2 + 8*(y+1));
    		if (x-1>=0 && y+2<8 && b[x-1][y+2]<=0)
    			movesList.add(x-1 + 8*(y+2));
    		if (x+1<8 && y+2<8 && b[x+1][y+2]<=0)
    			movesList.add(x+1 + 8*(y+2));
    		if (x+2<8 && y+1<8 && b[x+2][y+1]<=0)
    			movesList.add(x+2 + 8*(y+1));
    	}
    	
    	//Bishop
    	if (type == Game.B)
    	{
    		for (int u=-1; u<=1; u+=2)
    		for (int v=-1; v<=1; v+=2)
    		{
    			for (int i=1; i<8; i++)
    			{
    				if (x+u*i>=0 && x+u*i<8 && y+v*i>=0 && y+v*i<8)
    				{
    					if (b[x+u*i][y+v*i] == 0)
    						movesList.add(x+u*i + 8*(y+v*i));
    					if (b[x+u*i][y+v*i] > 0)
    						break;
    					if (b[x+u*i][y+v*i] < 0)
    					{
    						movesList.add(x+u*i + 8*(y+v*i));
    						break;
    					}
    				}
    				else break;
    			}
    		}
    	}
    	
    	//Rook
    	if (type == Game.R)
    	{
    		for (int u=-1; u<=1; u+=2)
    		{
    			for (int i=1; i<8; i++)
    			{
    				if (x+u*i>=0 && x+u*i<8)
    				{
    					if (b[x+u*i][y] == 0)
    						movesList.add(x+u*i + 8*y);
    					if (b[x+u*i][y] > 0)
    						break;
    					if (b[x+u*i][y] < 0)
    					{
    						movesList.add(x+u*i + 8*y);
    						break;
    					}
    				}
    				else break;
    			}
    			for (int i=1; i<8; i++)
    			{
    				if (y+u*i>=0 && y+u*i<8)
    				{
    					if (b[x][y+u*i] == 0)
    						movesList.add(x + 8*(y+u*i));
    					if (b[x][y+u*i] > 0)
    						break;
    					if (b[x][y+u*i] < 0)
    					{
    						movesList.add(x + 8*(y+u*i));
    						break;
    					}
    				}
    				else break;
    			}
    		}
    	}
    	
    	//Queen
    	if (type == Game.Q)
    	{
    		for (int u=-1; u<=1; u++)
    		for (int v=-1; v<=1; v++)
    		{
    			for (int i=1; i<8; i++)
    			{
    				if (x+u*i>=0 && x+u*i<8 && y+v*i>=0 && y+v*i<8)
    				{
    					if (b[x+u*i][y+v*i] == 0)
    						movesList.add(x+u*i + 8*(y+v*i));
    					if (b[x+u*i][y+v*i] > 0)
    						break;
    					if (b[x+u*i][y+v*i] < 0)
    					{
    						movesList.add(x+u*i + 8*(y+v*i));
    						break;
    					}
    				}
    				else break;
    			}
    		}
    	}
    	//King
    	if (type == Game.K)
    	{
    		for (int u=-1; u<=1; u++)
    		for (int v=-1; v<=1; v++)
    		{
    			if (x+u>=0 && x+u<8 && y+v>=0 && y+v<8)
    			{
    				if (b[x+u][y+v] <= 0)
    					movesList.add(x+u + 8*(y+v));
    			}
    		}
    	}
    	
    	return movesList;
    }
}
