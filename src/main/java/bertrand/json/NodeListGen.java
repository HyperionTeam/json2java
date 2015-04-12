package bertrand.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

import bertrand.json.Node.BELONG;
import bertrand.json.Node.TYPE;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class NodeListGen {
	
	private Logger logger = Logger.getLogger(NodeListGen.class);
	private List<Node> nodeList = new ArrayList<Node>();
	
	public List<Node> getNodeList() {
		return nodeList;
	}
	
	public void gen(String filePath) {
		try {
			FileReader reader = new FileReader(new File(filePath));
			JsonParser parser = new JsonParser();
			JsonElement jsonElement = parser.parse(reader);
			if(!jsonElement.isJsonObject()) {
				logger.error("Illegal Json!");
				return;
			}
			dealObject(jsonElement.getAsJsonObject(), 0);
			printNodeList();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void printNodeList() {
		for (Node node : nodeList) {
			for(int i=0;i<node.getDepth();i++) {
				System.out.print("  ");
			}
			System.out.println(node);
		}
	}
	
	private void dealObject(JsonObject jsonObject,int depth) {
		Set<Map.Entry<String, JsonElement>> set = jsonObject.entrySet();
		for (Entry<String, JsonElement> entry : set) {
			Node node = new Node();
			node.setName(entry.getKey());
			JsonElement jsonElement = entry.getValue();
			TYPE type = getTypeFromElement(jsonElement);
			node.setType(type);
			node.setDepth(depth);
			node.setBelong(BELONG.OBJECT);
			nodeList.add(node);
			if(TYPE.OBJECT == type) {
				dealObject(jsonElement.getAsJsonObject(), depth + 1);
			}else if(TYPE.LIST == type) {
				dealArray(jsonElement.getAsJsonArray(), depth + 1);
			}
		}
	}
	
	private void dealArray(JsonArray jsonArray, int depth) {
		if(jsonArray.size() != 0) {
			JsonElement jsonElement = jsonArray.get(0);
			TYPE type = getTypeFromElement(jsonElement);
			if(TYPE.OBJECT == type) {
				dealObject(jsonElement.getAsJsonObject(), depth + 1);
			}else if(TYPE.LIST == type) {
				dealArray(jsonElement.getAsJsonArray(), depth + 1);
			}else {
				Node node = new Node();
				node.setName(null);
				node.setType(type);
				node.setDepth(depth);
				node.setBelong(BELONG.LIST);
				nodeList.add(node);
			}
		}
	}

	private TYPE getTypeFromElement(JsonElement jsonElement) {
		if(jsonElement.isJsonArray()) {
			return TYPE.LIST;
		}else if(jsonElement.isJsonObject()) {
			return TYPE.OBJECT;
		}else if(jsonElement.isJsonPrimitive()) {
			return getPrimitiveType(jsonElement.getAsJsonPrimitive());
		}else {
			return TYPE.NULL;
		}
	}
	
	private TYPE getPrimitiveType(JsonPrimitive primitive) {
		if(primitive.isNumber()) {
			Number number = primitive.getAsNumber();
			if(number instanceof Byte) {
				return TYPE.BYTE;
			}else if(number instanceof Integer) {
				return TYPE.INTEGER;
			}else if(number instanceof Long) {
				return TYPE.LONG;
			}else if(number instanceof Short) {
				return TYPE.SHORT;
			}else if(number instanceof Float) {
				return TYPE.FLOAT;
			}else {
				return TYPE.DOUBLE;
			}
		}else if(primitive.isBoolean()) {
			return TYPE.BOOLEAN;
		}else {
			return TYPE.STRING;
		}
	}
	
	public static void main(String[] args) {
		NodeListGen gen = new NodeListGen();
		gen.gen("test.json");
	}
}
