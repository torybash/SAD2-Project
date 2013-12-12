
public interface Provider {

	
	
	
	
	public void setStream(int from, int to);
	public void setStream(int from, int to,String genre);
	
	public Provider copy();
	public Pair getNextPair();
	
	public void executeMovieRetrieval();
	
	public void closeOff();

	public void restartMovieOutput();
}
