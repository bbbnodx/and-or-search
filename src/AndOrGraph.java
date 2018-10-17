import java.util.*;

public class AndOrGraph {

	ArrayList<Node>	nodes = new ArrayList<Node>();		// AND/ORグラフを表す動的配列
	Node			current;							// 現在注目しているノード
	String			solution;							// 探索対象を表す変数
	
	public void setRoot(String st){
		if(!nodes.isEmpty()){
			System.out.println("現在のグラフを削除します");
			nodes.clear();
		}
		current = new Node(0, st, 0, 0);
		nodes.add(current);
	}
	
	public void setSolution(String st){
		solution = st;
	}
	
	// idxで指定されたノードを配列から削除する
	public void deleteNode(int idx){
		// 親ノードからのエッジを削除する
		nodes.get(nodes.get(idx).getFrom()).removeTo(idx);
		nodes.remove(idx);
	}
	
	// ノードの探索状態を調べ,代入するメソッド
	public void setNodeState(Node nd){
		if(nd.isUnknown()){
			if(nd.isNodeLeaf()){
				// Leafはsolutionと一致したときに解かれる
				if(nd.getStr().equals(solution)){
					nd.setSolved();
					System.out.println("\"" + solution +"\" is found");
				}else{
					nd.setUnsolved();
				}
			}else if(nd.isNodeAND()){
				int i;
				// ANDノードは子ノードがすべて解かれたときに解かれる
				for(i=0; i<nd.getTo().size(); i++){
					if(nodes.get(nd.getTo().get(i)).isUnsolved()){
						nd.setUnsolved();
						break;
					}
				}
				if(nd.isNodeExpanded() && i == nd.getTo().size())
					nd.setSolved();
			}else if(nd.isNodeOR()){
				int i;
				// ORノードは子ノードの少なくとも1つが解かれたときに解かれる
				for( i=0; i<nd.getTo().size(); i++){
					if(nodes.get(nd.getTo().get(i)).isSolved()){
						nd.setSolved();
						break;
					}
				}
				if(nd.isNodeExpanded() && i == nd.getTo().size())
					nd.setUnsolved();
			}
		}
	}
	
	// nodesの情報をすべて表示する
	public void printNodes(){
		System.out.println("Print Nodes(current idx = " + current.getIdx() + ")");
		for(int i=0; i<nodes.size(); i++){
			nodes.get(i).printStatus();
		}
		System.out.println("End");
	}
	
	// 解グラフを探索するメソッド
	public void search(){
		// 根ノードが解かれるまでループ
		while(nodes.get(0).isUnknown()){
			// currentノードを展開するループ
			while(current.isUnknown()){
				try{
					printNodes();
					// 子ノードを生成し,解グラフに追加する
					current = current.generateNextNode(nodes.size());
					System.out.println("Generated node: ");
					current.printStatus();
					System.out.println();
					nodes.add(current);
				}catch(RuntimeException e){
					System.out.println("Error");
				}
				// ノードの探索状態を更新する
				setNodeState(current);
			}
			System.out.println("Return to parent node:" + current.getFrom());
			// 親ノードへ戻り,探索状態を更新する
			current = nodes.get(current.getFrom());
			setNodeState(current);
			
			// solvedとunsolvedで別処理をするためのコード
			/*
			if(current.isSolved()){
				current = nodes.get(current.getFrom());
				SetNodeState(current);
			}else{
				int idx = current.getIdx();
				current = nodes.get(current.getFrom());
				nodes.remove(idx);
				current.removeTo(idx);
				SetNodeState(current);
			}
			*/
		}
		System.out.println("探索終了\n解グラフ:");
		printNodes();
		
		if(!nodes.isEmpty() && nodes.get(0).isSolved()){
			System.out.println("探索成功\n解グラフnodesを得られた");
		}else{
			System.out.println("探索失敗");
		}		
	}
	
	// 解グラフから文字列を取得するメソッド
	public String getSolution(){
		String result = "";
		
		if(nodes.isEmpty() || !nodes.get(0).isSolved()){
			System.out.println("このグラフは解かれていない");
			return result;
		}
		
		for(int i=0; i<nodes.size(); i++){
			if(nodes.get(i).isNodeLeaf() && nodes.get(i).isSolved()){
				result += nodes.get(i).getStr();
			}
		}
		return result;
	}
}

