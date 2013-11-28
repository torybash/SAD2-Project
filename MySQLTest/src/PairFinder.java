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
		while(!provider.isCurrentRowTheLast())
		{			
			
			Pair p = provider.getCurrentItemAsPair();
			if(p==null)
				break;
			System.out.print(""+p.a1 + " - " + p.a2 + " : ");
			
			if(checker && pairs.size()<k)
			{
				System.out.println("adding" +pairs.size());
				int i = pairs.indexOf(p);
				if(!(i>-1)){
					pairs.add(p);
				}else{
					pairs.get(i).count++;
				}
				
				if(pairs.size()==k){
					checker=false;
				}
				provider.moveToNextRow();
				continue;
			}
			System.out.println("After adding");
			int i = pairs.indexOf(p);
			if(i>-1)
			{
				pairs.get(i).count++;
			}
			else
			{ //If code has found the pair already in pairs.
				
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
				p.count++;
					pairs.add(p);
					
				}
				else
				{
					System.out.println("reduced all by 1");
					//reduce all by 1 count-
					for (Pair e : pairs) {
						e.count--;
					}
				}
			}
			provider.moveToNextRow();
			
		}
			
		Pair result =new Pair(0,0);		
		for(Pair e : pairs)
		{
			System.out.println(e.a1 + ", " + e.a2 + " : " + e.count);
			if(e.count>=result.count)
			{
				result = e;
			}
		}
		return result;
		
	}

	
	
}
