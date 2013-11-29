import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
			
			if(checker && pairs.size()<k)
			{
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
			if(e.count>=result.count)
			{
				result = e;
			}
		}
		return result;
		
	}



	public void StupidAlgo() {
		
		System.out.println("Start algo");

		Map<Pair, Integer> map = new HashMap<Pair, Integer>();
		
		Pair pair = provider.getNextPair();
		
		while (pair != null){
			if (map.containsKey(pair)){
				int currentVal = map.get(pair);
				map.put(pair, currentVal + 1);
			}else{
				map.put(pair, 1);
			}
			
			if (pair.a1 == 151786) System.out.println(pair);
			
			pair = provider.getNextPair();
		}
		
		//provider.CloseOff();
		
		for(Integer val : map.values()){
			if (val > 1) System.out.println(val);
		} 
		
		System.out.println("Stop algo");
		
	}

	
	
}
