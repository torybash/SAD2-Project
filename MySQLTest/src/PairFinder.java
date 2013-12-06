import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PairFinder {

	int k;
	int ik;
	ArrayList<Pair> pairs;
	Provider provider;
	
	
	
	public PairFinder(int k, Provider provider)
	{
		this.k =k;
		this.ik =k;
		pairs = new ArrayList<Pair>(k);
		this.provider = provider;		
	}
	
	
	public void MisraGriesAlgo(int years, int from, int to)
	{
		System.out.println("Starting MG");		
		
		if(to-from<years){
			provider.setStream(from, to);
		}
		else
		{
			int count = to-from-years+1;
			int fromCount=from;
			int toCount=from+years;
			Pair[][] pairs = new Pair[count][2];
			for(int i=0; i<count;i++)
			{
							
				provider.setStream(fromCount,toCount);
				pairs[i] = MisraGriesAlgo(k);
				
				Pair[] cP = pairs[i];
				int min1 = cP[0].count;
				int max2 = cP[1].count + decrements;
				if(min1<max2)
				{
					i--; //restarting this iteration
					
					increaseK();
					provider.restartMovieOutput();
				}
				else{
					System.out.println(i+1+"/"+count);
					toCount+=i;	
					fromCount+=1;
				}
				decrements=0;
				
				System.out.println(cP[0].toString());
				System.out.println(cP[1].toString());
				
				
			}
			
//			for(Pair[] p : pairs)
//			{
//				System.out.println(p[0].toString());
//				System.out.println(p[1].toString());
//			}
			
			
		}
		provider.closeOff();
	}
	
	private void increaseK() {
		k=ik*k;
	}
	
	private int decrements;
	private int counter;
	public Pair[] MisraGriesAlgo(int k)
	{		
		System.out.println("Starting MisraGriesAlgo(int k) with k=" + k);
		
		boolean checker =true;
		Pair p = provider.getNextPair();
	
		while(p!=null)
		{				
			counter++;
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
				p = provider.getNextPair();
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
					decrements++;
					//reduce all by 1 count-
					for (Pair e : pairs) {
						e.count--;
					}
				}
			}
			//provider.moveToNextRow();
			p = provider.getNextPair();
		}
			
		Pair result =new Pair(0,0);
		Pair result2 =new Pair(0,0);	
		for(Pair e : pairs)
		{
			if(e.count>=result.count)
			{
				result = e;
			}else if(e.count>=result2.count)
			{
				result2 = e;				
			}

		}
		System.out.println("C: "+counter);
		System.out.println("D: " +decrements);
		Pair[] ps = new Pair[2];
		ps[0] = result;
		ps[1] = result2;
		return ps;
		
		
	}



	public void StupidAlgo() {
		
		provider.setStream(1904, 1910);
		
		ArrayList<Pair> pairs = new ArrayList<Pair>();
		
		Pair pair = provider.getNextPair();
		
		while (pair != null){		
			if(pairs.contains(pair))
			{
				int c = pairs.get(pairs.indexOf(pair)).count;
				pairs.remove(pairs.indexOf(pair));
				pair.count=c+1;
				pairs.add(pair);
			}
			else
			{
				pairs.add(pair);
			}
			
			
			pair = provider.getNextPair();
		}
		
		//provider.CloseOff();
		
		Pair result =new Pair(0,0);		
		for(Pair e : pairs)
		{
			if(e.count>=result.count)
			{
				result = e;
			}
		}
		
		
		System.out.println(result.toString());
		
	}

	
	
}
