import java.util.*;

public class AndOrGraph {

	ArrayList<Node>	nodes = new ArrayList<Node>();		// AND/OR�O���t��\�����I�z��
	Node			current;							// ���ݒ��ڂ��Ă���m�[�h
	String			solution;							// �T���Ώۂ�\���ϐ�
	
	public void setRoot(String st){
		if(!nodes.isEmpty()){
			System.out.println("���݂̃O���t���폜���܂�");
			nodes.clear();
		}
		current = new Node(0, st, 0, 0);
		nodes.add(current);
	}
	
	public void setSolution(String st){
		solution = st;
	}
	
	// idx�Ŏw�肳�ꂽ�m�[�h��z�񂩂�폜����
	public void deleteNode(int idx){
		// �e�m�[�h����̃G�b�W���폜����
		nodes.get(nodes.get(idx).getFrom()).removeTo(idx);
		nodes.remove(idx);
	}
	
	// �m�[�h�̒T����Ԃ𒲂�,������郁�\�b�h
	public void setNodeState(Node nd){
		if(nd.isUnknown()){
			if(nd.isNodeLeaf()){
				// Leaf��solution�ƈ�v�����Ƃ��ɉ������
				if(nd.getStr().equals(solution)){
					nd.setSolved();
					System.out.println("\"" + solution +"\" is found");
				}else{
					nd.setUnsolved();
				}
			}else if(nd.isNodeAND()){
				int i;
				// AND�m�[�h�͎q�m�[�h�����ׂĉ����ꂽ�Ƃ��ɉ������
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
				// OR�m�[�h�͎q�m�[�h�̏��Ȃ��Ƃ�1�������ꂽ�Ƃ��ɉ������
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
	
	// nodes�̏������ׂĕ\������
	public void printNodes(){
		System.out.println("Print Nodes(current idx = " + current.getIdx() + ")");
		for(int i=0; i<nodes.size(); i++){
			nodes.get(i).printStatus();
		}
		System.out.println("End");
	}
	
	// ���O���t��T�����郁�\�b�h
	public void search(){
		// ���m�[�h���������܂Ń��[�v
		while(nodes.get(0).isUnknown()){
			// current�m�[�h��W�J���郋�[�v
			while(current.isUnknown()){
				try{
					printNodes();
					// �q�m�[�h�𐶐���,���O���t�ɒǉ�����
					current = current.generateNextNode(nodes.size());
					System.out.println("Generated node: ");
					current.printStatus();
					System.out.println();
					nodes.add(current);
				}catch(RuntimeException e){
					System.out.println("Error");
				}
				// �m�[�h�̒T����Ԃ��X�V����
				setNodeState(current);
			}
			System.out.println("Return to parent node:" + current.getFrom());
			// �e�m�[�h�֖߂�,�T����Ԃ��X�V����
			current = nodes.get(current.getFrom());
			setNodeState(current);
			
			// solved��unsolved�ŕʏ��������邽�߂̃R�[�h
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
		System.out.println("�T���I��\n���O���t:");
		printNodes();
		
		if(!nodes.isEmpty() && nodes.get(0).isSolved()){
			System.out.println("�T������\n���O���tnodes�𓾂�ꂽ");
		}else{
			System.out.println("�T�����s");
		}		
	}
	
	// ���O���t���當������擾���郁�\�b�h
	public String getSolution(){
		String result = "";
		
		if(nodes.isEmpty() || !nodes.get(0).isSolved()){
			System.out.println("���̃O���t�͉�����Ă��Ȃ�");
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

