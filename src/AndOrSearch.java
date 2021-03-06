
public class AndOrSearch {

	public static void main(String[] args) {
		
		AndOrGraph graph = new AndOrGraph();
		
		// 生成規則を設定
		String[] x = {"ab", "cY"};
		String[] y = {"a", "c"};
		String[] z = {"Yc"};
		Node.setRule("X", x);
		Node.setRule("Y", y);
		Node.setRule("Z", z);
		
		// 根ノードを設定
		graph.setRoot("XYZ");
		// 探索する文字を設定
		graph.setSolution("c");
		
		graph.search();
		
		System.out.println("得られた文字列:" + graph.getSolution());	
	}

}
