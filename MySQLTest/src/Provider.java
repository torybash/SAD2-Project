
public interface Provider {

	
	
	
	
	public void setStream(int from, int to);
	
	public Pair getNextPair();
	
	public void executeMovieRetrieval();
	
	public void closeOff();

	public void restartMovieOutput();
}
