
public class AndOrSearch {

	public static void main(String[] args) {
		
		AndOrGraph graph = new AndOrGraph();
		
		// �����K����ݒ�
		String[] x = {"ab", "cY"};
		String[] y = {"a", "c"};
		String[] z = {"Yc"};
		Node.setRule("X", x);
		Node.setRule("Y", y);
		Node.setRule("Z", z);
		
		// ���m�[�h��ݒ�
		graph.setRoot("XYZ");
		// �T�����镶����ݒ�
		graph.setSolution("c");
		
		graph.search();
		
		System.out.println("����ꂽ������:" + graph.getSolution());	
	}

}
