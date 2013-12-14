
public class Main {
	public static void main(String[] arg) {
		//MySQLTest test = new MySQLTest();
		
		//test.run();
		
		//SQLSimpleProvider ssp2 = new SQLSimpleProvider();
		
		Provider ssp = new SQLSimpleProvider();
		PairFinder pf = new PairFinder(2, ssp);
		
		System.out.println("Misra-Gries:");
		long timerMG = System.currentTimeMillis();
		pf.MisraGriesAlgo(1,1910,1911);
		timerMG = System.currentTimeMillis() - timerMG;
		System.out.println(timerMG + "ms");

		System.out.println("Stupid-Algo:");
		long timer = System.currentTimeMillis();
		pf.StupidAlgo(1910, 1911);
		timer = System.currentTimeMillis() - timer;
		System.out.println(timer + "ms");
		
		ssp.closeOff();

//		int count=0;
//		double total = 0;
//		while(count<=10){
//			SQLSimpleProvider ssp = new SQLSimpleProvider();
//			
//			FlajoletMartin fm = new FlajoletMartin(2,ssp);
//			total=total+fm.countDistinct("", 1910, 1925);
//			count++;
//		}
//		System.out.println(total/count);
//		SQLSimpleProvider ssp = new SQLSimpleProvider();
//		
//		FlajoletMartin fm = new FlajoletMartin(2,ssp);
//		fm.DumDistinct("", 1910, 1925);
			
	}
	
}
		/*ssp = new SQLSimpleProvider();
		fm = new FlajoletMartin(2,ssp);
		fm.ProperbilityDistinct(5, 1910, 1911);
		//System.out.println(pf.MisraGriesAlgo());
		//pf.StupidAlgo();//231087 - 430349: 145

		//pf.MisraGriesAlgo(2,1980,1990);
		}
}
*/