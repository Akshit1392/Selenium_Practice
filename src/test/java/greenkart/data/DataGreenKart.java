package greenkart.data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataGreenKart {
	public List<HashMap<String, Integer>> getJasonDataToMap(String path) throws IOException {
		//Read Json to String
		String jsonContent = FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//src//test//java//greenkart//data//OrderList.json"), StandardCharsets.UTF_8);

		//String to HashMap -> Jackson Databind (read data from a JSON string --> java objects)--> deserialization
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, Integer>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, Integer>>>(){});

		return data;
	}
}
