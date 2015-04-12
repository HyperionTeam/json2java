package bertrand.json;


/**
 * @author Bertrand Huang
 *
 */
public class Node {
	
	public static final String OBJECT_WITHOUT_NAME = "EMPTY_OBJECT_NAME";
	public static final String LIST_WITHOUT_NAME = "EMPTY_LIST_NAME";
	
	private String name;
	private TYPE type;
	private int depth;
	private BELONG belong;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TYPE getType() {
		return type;
	}

	public void setType(TYPE type) {
		this.type = type;
	}
	
	public int getDepth() {
		return depth;
	}
	
	public void setDepth(int depth) {
		this.depth = depth;
	}
	
	public BELONG getBelong() {
		return belong;
	}
	
	public void setBelong(BELONG belong) {
		this.belong = belong;
	}

	public static enum TYPE {
		INTEGER("Integer"),
		LONG("Long"),
		SHORT("Short"),
		FLOAT("Float"),
		DOUBLE("Double"),
		BYTE("Byte"),
		BOOLEAN("Boolean"),
		CHAR("Char"),
		STRING("String"),
		LIST("List"),
		OBJECT(""),
		NULL("");
		
		private String name;
		
		private TYPE(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
	}
	
	public static enum BELONG {
		OBJECT, LIST
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name).append('|').append(type);
		return sb.toString();
		
	}

	
}
