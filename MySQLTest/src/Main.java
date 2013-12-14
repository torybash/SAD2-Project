

public class Main {
	public static void main(String[] arg) {
		//MySQLTest test = new MySQLTest();
		
		//test.run();
		
		//SQLSimpleProvider ssp2 = new SQLSimpleProvider();

		//break;
		SQLSimpleProvider ssp2 = new SQLSimpleProvider();
		PairFinder pfs3 = new PairFinder(2,ssp2);
		pfs3.StupidAlgo(1910, 1940);
		start();
		SQLSimpleProvider ssp4 = new SQLSimpleProvider();
		PairFinder pfs = new PairFinder(2,ssp4);
		pfs.MisraGriesAlgo(2, 1910, 1940);
		stop();
		System.out.println("Time of Sekvential: " +Main.getElapsedTimeSecs());
		start();
		SQLSimpleProvider ssp3 = new SQLSimpleProvider();
		MTPairFinder pfs2 = new MTPairFinder(2,ssp3);
		pfs2.MisraGriesAlgo(2, 1910, 1940);
		stop();
		System.out.println("Multicore MG : "+ Main.getElapsedTimeSecs());
		
		int count=0;
		double total = 0;
		while(count<=10){
		SQLSimpleProvider ssp = new SQLSimpleProvider();
		
		FlajoletMartin fm = new FlajoletMartin(2,ssp);
		total=total+fm.countDistinct("", 1910, 1940);
		count++;}
		System.out.println(total/count);
		SQLSimpleProvider ssp = new SQLSimpleProvider();
		
		FlajoletMartin fm = new FlajoletMartin(2,ssp);
		fm.DumDistinct("", 1910, 1940);
			
	}
	
	 private static long startTime = 0;
	  private static long stopTime = 0;
	  private static boolean running = false;


	  public static void start() {
	    startTime = System.currentTimeMillis();
	    running = true;
	  }


	  public static void stop() {
	    stopTime = System.currentTimeMillis();
	    running = false;
	  }


	  //elaspsed time in milliseconds
	  public static long getElapsedTime() {
	    long elapsed;
	    if (running) {
	      elapsed = (System.currentTimeMillis() - startTime);
	    }
	    else {
	      elapsed = (stopTime - startTime);
	    }
	    return elapsed;
	  }


	  //elaspsed time in seconds
	  public static long getElapsedTimeSecs() {
	    long elapsed;
	    if (running) {
	      elapsed = ((System.currentTimeMillis() - startTime) / 1000);
	    }
	    else {
	      elapsed = ((stopTime - startTime) / 1000);
	    }
	    return elapsed;
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