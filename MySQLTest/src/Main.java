
public class Main {
	public static void main(String[] arg) {
		//MySQLTest test = new MySQLTest();
		
		//test.run();
		SQLSimpleProvider ssp = new SQLSimpleProvider();
		ssp.setStream();
		PairFinder pf = new PairFinder(4, ssp);
		System.out.println(pf.MisraGriesAlgo().toString());
	}
}
