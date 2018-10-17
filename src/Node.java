import java.util.*;

public class Node {
	// �񋓎q�Ńm�[�h�̎�ނ�\��
	enum NodeType {ANDNODE, ORNODE, LEAF, DEFAULT};
	enum NodeState {SOLVED, UNSOLVED, UNKNOWN};
	
	int					idx;							// �m�[�h����ӎ��ʂ���index
	String				str;							// �m�[�h�ɂ����镶����
	int					next = 0;						// AND�m�[�h�F���ɓW�J���镶�����w��
														// OR�m�[�h �F���ɓK�p���鐶���K�����w��
	int					from;							// �e�m�[�h���w��index
	ArrayList<Integer>	to = new ArrayList<Integer>();	// �q�m�[�h���w��index�̔z��
	NodeType			type;							// �m�[�h�̎�ނ�\���񋓎q
	NodeState			state = NodeState.UNKNOWN;		// �m�[�h�̒T����Ԃ�\���񋓎q
	int					depth = 0;						// �m�[�h�̐[��(�O���t�̏o�͂Ɏg�p)
	
	// �����K�����i�[����A�z�z��
	static HashMap<String, String[]> rule = new HashMap<String, String[]>();
	
	public Node(int id){
		idx = id;
	}
	
	public Node(int id, String s){
		idx = id;
		str = s;
		decideNodeType();
	}
	
	public Node(int id, String s, int parent, int dp){
		idx = id;
		str = s;
		decideNodeType();
		from = parent;
		depth = dp;
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////  �ȉ��̃��\�b�h�̓t�B�[���h�Ƃ̃C���^�t�F�[�X  ////////////
	///////////////////////////////////////////////////////////////////////////

	/*
	// idx�͌����I�ɃR���X�g���N�^�ő������
	public void setIdx(int index){
		idx = index;
	}
	*/
	
	public int getIdx(){
		return idx;
	}
	
	public void setStr(String input){
		str = input;
	}
	
	public String getStr(){
		return str;
	}

	public void setNext(int id){
		next = id;
	}
	
	public int getNext(){
		return next;
	}
	
	
	public void setFrom(int f){
		from = f;
	}
	
	public int getFrom(){
		return from;
	}
	
	public void addTo(int t){
		to.add(t);
	}
	
	public void removeTo(int nodeId){
		for(int i=0; i<to.size(); i++)
			if(to.get(i) == nodeId)
				to.remove(i);
	}
	
	public ArrayList<Integer> getTo(){
		return to;
	}
	
	/*
	// �����I��type�̓R���X�g���N�^�ő������
	public void setType(NodeType nt){
		type = nt;
	}
	*/
	
	public boolean isNodeAND(){
		return type == NodeType.ANDNODE;
	}
	
	public boolean isNodeOR(){
		return type == NodeType.ORNODE;
	}
	
	public boolean isNodeLeaf(){
		return type == NodeType.LEAF;
	}
	
	public void setSolved(){
		state = NodeState.SOLVED;
	}
	
	public void setUnsolved(){
		state = NodeState.UNSOLVED;
	}
	
	public boolean isSolved(){
		return state == NodeState.SOLVED;
	}
	
	public boolean isUnsolved(){
		return state == NodeState.UNSOLVED;
	}
	
	public boolean isUnknown(){
		return state == NodeState.UNKNOWN;
	}
	
	// �����K����ǉ�����
	public static void setRule(String key, String[] value){
		rule.put(key, value);
	}
	
	public static String[] getRule(String input){
		return rule.get(input);
	}
	
	//////////////////////////////////////////////////////////////////////////
	///////////////  �t�B�[���h�ւ̃C���^�t�F�[�X�����܂�  ///////////////////
	//////////////////////////////////////////////////////////////////////////
	
	// �e�t�B�[���h�̒l���o�͂���
	public void printStatus(){
		for(int i=0; i<depth; i++)
			System.out.print("\t");
		
		System.out.println(str + " idx:" + idx + " strlen:" + str.length() + 
				" from:" + from + " to:" + to + " state:" + state);
		/* �f�o�b�O�p
		System.out.println(str + " idx:" + idx + " strlen:" + str.length() + 
				" next:" + next + " type:" + type + " from:" + from + " to:" + to + " state:" + state);
		*/
	}
	
	// �m�[�h�̎�ނ𔻒肵�Ctype�֊i�[����
	public void decideNodeType(){
		if(str.length() > 1){
			type = NodeType.ANDNODE;
		}else if(str.length() == 1){
			char[] ch = str.toCharArray();
			type = ( Character.isUpperCase(ch[0]) ? NodeType.ORNODE :
				   ( Character.isLowerCase(ch[0]) ? NodeType.LEAF : NodeType.DEFAULT ) );
		}
	}
	
	// �q�m�[�h�����ׂēW�J�ς݂����肷��
	public boolean isNodeExpanded(){
		if(isNodeAND()){
			// AND�m�[�h�Ȃ當����̒�����next�Ƃ̔�r�Ŕ��肷��
			if(next >= str.length())
				return true;
			else
				return false;
		}else if(isNodeOR()){
			// OR�m�[�h�Ȃ琶���K���̐���next�Ƃ̔�r�Ŕ��肷��
			if(next >= rule.get(str).length)
				return true;
			else
				return false;
		}else{
			// Leaf�͓W�J�ς݂Ƃ��Ĉ���
			return true;
		}
	}
	
	// �q�m�[�h����W�J���ĕԂ�
	public Node generateNextNode(int id)
	{
		String st=null;
		
		try{
			if(type == NodeType.ANDNODE){
				st = str.substring(next++, next);
				to.add(id);
				return new Node(id, st, idx, depth+1);
			}else if(type == NodeType.ORNODE){
				st = rule.get(str)[next++];
				to.add(id);
				return new Node(id, st, idx, depth+1);
			}else{
				throw new RuntimeException();
			}
		}catch(RuntimeException e){
			throw new RuntimeException();
		}
	}
}
