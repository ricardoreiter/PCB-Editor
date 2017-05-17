package se.com.component;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class BoardLoader {

	public static void saveBoard(Board board, File file) throws IOException {
		Gson g = new Gson();
		String json = g.toJson(board);
		FileWriter fileWriter = new FileWriter(file, false);
		try {
			fileWriter.write(json);
			fileWriter.flush();
		} finally {
			fileWriter.close();
		}
	}
	
	public static Board loadBoard(File file) throws IOException {
		Gson g = new Gson();
		FileReader fileReader = new FileReader(file);
		try {
			return g.fromJson(fileReader, Board.class);
		} finally {
			fileReader.close();
		}
	}
	
}
