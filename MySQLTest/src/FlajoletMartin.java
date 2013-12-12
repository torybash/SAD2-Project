import java.util.ArrayList;


public class FlajoletMartin {
	
	
    int k;
    int ik;
    ArrayList<Pair> pairs;
    Provider provider;
    int[] genres = new int[22];
    boolean[] bV = new boolean[64]; 
    boolean[] bV2 = new boolean[64];
    boolean[] bV3 = new boolean[64];
    
	public FlajoletMartin(int k, Provider provider)
    {
        this.k =k;
        this.ik =k;
        pairs = new ArrayList<Pair>(k);
        this.provider = provider;                
    }
	
	public double countDistinct(String genre,int from,int to)
	{
	    provider.setStream(from,to, genre);
        
        
        Pair a =provider.getNextPair();
        int count1 =0;
        int count =0;                       
        
        while(a!=null)
        {        	           
        	count++;
        	//System.out.println(CountTrails(hash(a)));
        	bV[CountTrails(hash(a))]=true;
        	bV2[CountTrails(hash(a))]=true;
        	bV3[CountTrails(hash(a))]=true;
        	a = provider.getNextPair();
        	//each distinct pair generates a triangle with the genre
        	//triangles between pairs in each movie?
        	//How do we count distinct pairs?
        	
        }
        int q=0;
        for(int i =0; i<64;i++)
        {
        
        	if(!bV[i])
        		break;
        	q++;
        }
        int q2=0;
        for(int i =0; i<64;i++)
        {
        	
        	if(!bV2[i])
        		break;
        	q2++;
        }
        int q3=0;
        for(int i =0; i<64;i++)
        {
        	
        	if(!bV3[i])
        		break;
        	q3++;
        }
        
        double r = (q3+q2+q)/3.0;
        double out =2;
        out = Math.pow(2.0, r);
        System.out.println(q+" "+q2+" "+q3+" r: " +r);        
        System.out.println(out/0.77351);
        
        System.out.println();
        return out/0.77351;
	}
	
	
	public int CountTrails(int i)
	{
		return Integer.numberOfTrailingZeros(i);
		
	}
	
	public int hash(Pair a)
	{
		char[] ch = a.toString().toCharArray();
		int sum = 0;
		int count =0;
		for(char cha : ch)
		{
			count++;
			sum+=(int)cha;
			
		}
		int M=find();
		int N=find();
		int first=(int)(M+N*a.hashCode());
		//int thrid =first%second;
		return first;
	}
	
	public int find()
	{
		int m=0;
		int test=0;
		while(test==0)
		{
			m= (int)(Integer.MAX_VALUE * Math.random());
			test = m%2;
		}
		return m;
	}
	
    public boolean DumDistinct(String genre, int from, int to)
    {
    	
            System.out.println("Starting MG");                
            provider.setStream(from,to,genre);
            
            
            Pair a =provider.getNextPair();
            int count1 =0;
            int count =0;
            ArrayList<String> ps = new ArrayList<String>();
            while(a!=null)
	         {
            	count++;
	            if(!ps.contains(a.toString()))
	            {
	            	ps.add(a.toString());
	            	count1++;
	            	//System.out.println("distinct pairs: " +count1 +"/"+count);
	            }
	            a= provider.getNextPair();
	            
	            //pairs[i] = MisraGriesAlgo(k);
	    				
	    		
	    	
	    
	    		
            }
            System.out.println("distinct pairs: " +count1);
            System.out.println("total pairs: " +count);
            
            
            return true;
    }
     
    
    public boolean ProperbilityDistinct(int years, int from, int to)
    {
            System.out.println("Starting MG");                
            provider.setStream(from,to);
            boolean notTrue = true;
            while(notTrue){
	            int M = 2000;
	            //int M2 = M/2;
	            Pair a =provider.getNextPair();
	            int count =0;
	            while(a!=null)
		         {
	            	count++;
		            if(hash(Long.parseLong((a.a1+""+a.a2)),M)==1)
		            {
		            	System.out.println("true");
		            	System.out.println(count);
		            	return true;
		            }
		            
		            a =provider.getNextPair();
		            
		            //pairs[i] = MisraGriesAlgo(k);
		    				
		    		
		    		//System.out.println("The most frequent or as frequent as any other pair is: " +pqe.toString());
		    
		    		
	            }
	            System.out.println("false");
	            notTrue=false;
	            System.out.println(count);
            }
    provider.closeOff();
    System.out.println("false");
    return false;
    }
    
    
    public long hash(long actor, int m)
    {
    	//System.out.println((actor*7)%m);
    	return (actor*13)%m;
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
           // System.out.println("C: "+counter);
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
