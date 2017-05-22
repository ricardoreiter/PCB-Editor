package se.com.component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Board Loader/Saver 
 */
public abstract class BoardLoader {

	public static void saveBoard(Board board, File file) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file, false));
		Serializable serializableObject = board;
		try {
			oos.writeObject(serializableObject);
		} finally {
			oos.close();
		}
	}
	
	public static Board loadBoard(File file) throws ClassNotFoundException, IOException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
		try {
			return (Board) ois.readObject();
		} finally {
			ois.close();
		}
	}
	
}
