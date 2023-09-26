
public class Move
{
	public int selectedX, selectedY, selectedType, destinationX, destinationY, index;
	public boolean hasMove = false;
	public boolean inCheck = false;
	public boolean checkMate = false;
	
	public Move()
	{
		
	}
	
	public int getSelX()
	{
		return selectedX;
	}
	
	public int getSelY()
	{
		return selectedY;
	}
	
	public int getType()
	{
		return selectedType;
	}
	
	public int getDesX()
	{
		return destinationX;
	}
	
	public int getDesY()
	{
		return destinationY;
	}
	
	public int getIndex()
	{
		return index;
	}
}
