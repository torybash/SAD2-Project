
public class Pair implements Comparable  {
	int a1;
	int a2;
	int count;
	
	public  Pair(int a1, int a2)
	{
		this.a1 = a1;
		this.a2 = a2;
		this.count = 0;
	}
	
	@Override
	public String toString()
	{
		return ""+a1 + " - " + a2 + ": " + count ;
	}
	
	
	public String toString2()
	{
		return a1 + "-" + a2;
	}
	
	@Override
	public int compareTo(Object a) {
		if(a instanceof Pair)
		{
			Pair p = (Pair) a;
			if((p.a1 == a1 && p.a2 == a2) || (p.a1 == a2 && p.a2 ==a1))
				return 0;
				
			return -1;
		}
		return -1;
	}
	
	@Override
	public boolean equals(Object a){
		if(a instanceof Pair){
			Pair p = (Pair) a;
			if((p.a1 == a1 && p.a2 == a2) || (p.a1 == a2 && p.a2 ==a1)) return true;
		}
		return false;
	}
}
