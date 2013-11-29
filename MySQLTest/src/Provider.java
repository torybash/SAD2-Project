
public interface Provider {

	
	
	public Pair getCurrentItemAsPair();
	public boolean isCurrentRowTheLast();
	public boolean moveToNextRow();
	
	public Pair getNextPair();
	public void CloseOff();
}
