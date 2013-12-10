import java.util.ArrayList;

public class MGThread implements Runnable {
		Thread t;
		int k;
		int ik;
		Provider pr;
		int i;
		public MGThread(int k, int ik, int year, Provider p, int i, MTPairFinder e)
		{
		 t = new Thread(this, "MGTThreadK");
		 this.k=k;
		 ik=k;
		 this.pr=p;
		 this.i=i;
		 
		 i++;
//		 e.notifyAll();
		 
		}
		
		@Override
		public void run() {
			boolean notFound=true;
			while(notFound){
				
				
				Pair[] cP =MisraGriesAlgo(k);
				int min1 = cP[0].count;
				int max2 = cP[1].count + decrements;
				if(min1<max2)
				{									
					increaseK();
					pr.restartMovieOutput();
				}
				else{
					//found it, pass it back.
					notFound = false;
					MTPairFinder.setFinished(i, cP[0]);
				}
				decrements=0;
				counter=0;
				
				System.out.println(cP[0].toString());
				System.out.println(cP[1].toString());							
			}
			pr.closeOff();
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
			Pair p = pr.getNextPair();
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
					p = pr.getNextPair();
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
				p = pr.getNextPair();
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

//		for(Pair[] p : pairs)
//		{
//			System.out.println(p[0].toString());
//			System.out.println(p[1].toString());
//		}
		
		
	}