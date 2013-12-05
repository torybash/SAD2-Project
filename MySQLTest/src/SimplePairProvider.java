
public class SimplePairProvider implements Provider {

	
	Pair[] p;
	int i =0;
	int maxI =50;
	int stopAt = 137;
	int globalI = 0;
	public SimplePairProvider()
	{
		p= new Pair[50];
		for(int i=0;i<50;i++)
		{
			p[i] = new Pair(i, i+1);
		}
		
	}
	

	
	public boolean isCurrentRowTheLast() {
		return globalI<=stopAt;
	}

	

	@Override
	public Pair getNextPair() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void setStream(int from, int to) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void executeMovieRetrieval() {
		// TODO Auto-generated method stub
		
	}


}
