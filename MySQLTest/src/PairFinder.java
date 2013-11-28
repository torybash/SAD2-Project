import java.util.ArrayList;
import java.util.List;


public class PairFinder {

	int k;
	ArrayList<Pair> pairs;
	Provider provider;
	
	
	
	public PairFinder(int k, Provider provider)
	{
		this.k =k;
		pairs = new ArrayList<Pair>(k);
		this.provider = provider;		
	}
	
	
	public Pair MisraGriesAlgo()
	{
		
		boolean checker =true;
		while(provider.Next())
		{			
			Pair p = provider.NextItem();
			
			if(checker && pairs.size()<k)
			{
				System.out.println("adding" +pairs.size());
				int i = pairs.indexOf(p);
				if(!(i>-1))
					pairs.add(p);
				else
					pairs.get(i).count++;
					
				if(pairs.size()==k)
					checker=false;
				continue;
			}
			System.out.println("After adding");
			int i = pairs.indexOf(p);
			if(i>-1)
			{
				pairs.get(i).count++;
			}
			else
			{
				int indexOfPair = -1;				
				for (Pair e : pairs) {
					if(e.count==0)
					{
						indexOfPair = pairs.indexOf(e);
						break;//try e=p;
					}
				}
				if(indexOfPair>-1)
				{
					//replace
					pairs.remove(indexOfPair);					
					pairs.add(p);
					
				}
				else
				{
					//reduce all by 1 count-
					for (Pair e : pairs) {
						e.count--;
					}
				}
			}
			
			}
			
		Pair result =new Pair(0,0);		
		for(Pair e : pairs)
		{
			if(e.count>result.count)
			{
				result = e;
			}
		}
		return result;
		
	}

	
	
}
