
public class AndOrSearch {

	public static void main(String[] args) {
		
		AndOrGraph graph = new AndOrGraph();
		
		// ¶¬‹K‘¥‚ğİ’è
		String[] x = {"ab", "cY"};
		String[] y = {"a", "c"};
		String[] z = {"Yc"};
		Node.setRule("X", x);
		Node.setRule("Y", y);
		Node.setRule("Z", z);
		
		// ªƒm[ƒh‚ğİ’è
		graph.setRoot("XYZ");
		// ’Tõ‚·‚é•¶š‚ğİ’è
		graph.setSolution("c");
		
		graph.search();
		
		System.out.println("“¾‚ç‚ê‚½•¶š—ñ:" + graph.getSolution());	
	}

}
