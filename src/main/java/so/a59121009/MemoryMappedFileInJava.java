package so.a59121009;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Random;

public class MemoryMappedFileInJava {
	
	//file
	File file;
	//channel to connect the file content to buffer
	FileChannel channel;
	//buffer
	MappedByteBuffer buffer;
	//buffer max size in bytes
	final int BUFFER_MAX = 32;
	
	@SuppressWarnings("resource")
	MemoryMappedFileInJava() {
		file = new File("file.txt");
		//creare a channel to write and read
		try {
			channel = new RandomAccessFile(file, "rw").getChannel();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// map the file content to buffer
		try {
			buffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, BUFFER_MAX);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//read the value from buffer
	int readInt() {
		buffer.position(0);
		return buffer.getInt();
	}
	
	//send the message to buffer
	void writeInt(int number) {
		buffer.position(0);
		buffer.putInt(number);
		buffer.putChar('\0');
	}
	
	//close the channel 
	void fecharCanal() {
		try {
			channel.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//lets make some tests with random numbers
	static void run() {
		Random r = new Random();
		MemoryMappedFileInJava communication = new MemoryMappedFileInJava();
		
		int number = r.nextInt(5) + 1;
		communication.writeInt(5);
		String prefix = "the given number from buffer is --> ";
		
		System.out.print(prefix + communication.readInt());
	}
	
	public static void main(String[] args) {
		run();
	}
}