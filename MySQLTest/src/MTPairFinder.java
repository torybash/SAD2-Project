import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MTPairFinder {

	int k;
	int ik;
	public static Pair[] pairs;
	
	public static int count;
	public static int[] finished;
	Provider provider;
	
	public synchronized static void setFinished(int i, Pair p)
	{
		if(finished==null)
		{
			finished = new int[count];
			pairs = new Pair[count];
		}
		finished[i] = 1;
			pairs[i]=p;
		
	}
	
	public MTPairFinder(int k, Provider provider)
	{
		this.k =k;
		this.ik =k;	
		this.provider = provider;		
	}
	
	
	public void MisraGriesAlgo(int years, int from, int to)
	{
		try{
			Pair[] pairs = new Pair[count];
		System.out.println("Starting MG");
		int count = to-from-years+1;
		MTPairFinder.count = count;
		{
			
			int fromCount=from;
			int toCount=from+years;
			
			for(int i=0; i<count;i++)
			{
				
				Provider ps = provider.copy();
				ps.setStream(fromCount,toCount);
				if(i==0)
				 {MGThread t = new MGThread(k,ik,fromCount,ps, i, this);
				 t.run();
				 k=t.k;}
				 
				else{
				new Thread(new MGThread(k,ik,fromCount,ps, i, this)).start();
				}
				
				 toCount+=1;        
                 fromCount+=1;	
			
				
			}
		}
		
//		this.wait();
		Pair pqe = null;
		for(int q=0; q<count;q++)
		{		
			
			if(MTPairFinder.finished==null || MTPairFinder.finished[q]==0)
			{
				Thread.sleep(500);
				q--;
			}
			else{
				if(pqe==null || pqe.count<MTPairFinder.pairs[q].count)
				{
					pqe=MTPairFinder.pairs[q];
				}
			}
			
				
		}
		System.out.println("The most frequent or as frequent as any other pair is: " +pqe.toString());
		
		}
		catch(Exception e){System.out.println("error: "+e.getStackTrace()+e.getMessage());}
//		provider.closeOff();
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
		ArrayList<Pair> pairs = new ArrayList<Pair>();
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
		//System.out.println("C: "+counter);
		//System.out.println("D: " +decrements);
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
