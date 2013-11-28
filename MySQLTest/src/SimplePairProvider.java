
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
	
	@Override
	public Pair getCurrentItemAsPair() {
		if(i==50)
			i=0;			
		i++;
		globalI++;		
		return p[i-1];

		
		
		
	}
	
	public boolean isCurrentRowTheLast() {
		return globalI<=stopAt;
	}

	@Override
	public boolean moveToNextRow() {
		// TODO Auto-generated method stub
		return false;
	}

}
