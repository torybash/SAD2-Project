
public class Main {
	public static void main(String[] arg) {
		//MySQLTest test = new MySQLTest();
		
		//test.run();
		SQLSimpleProvider ssp = new SQLSimpleProvider();
		PairFinder pf = new PairFinder(95, ssp);
		pf.MisraGriesAlgo(9, 1900, 1910);
		//System.out.println(pf.MisraGriesAlgo());
		//pf.StupidAlgo();//231087 - 430349: 145

		//pf.MisraGriesAlgo(2,1980,1990);
		}
}
