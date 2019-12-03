package so.a56210680;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public class Integers8Bit {
	
	public static void main(String[] args) {
		byte[] arr = new byte[] { (byte) 255, (byte) 128, (byte) 64, (byte) 0 };
		byte[] arr2 = new byte[] { (byte) 1, (byte) 128, (byte) 64, (byte) 0 };
		
		output(arr);
		output(arr2);
	}
	
	private static void output(byte[] bytes) {
		int transform = transformToInt(bytes);
		outBytes(bytes);
		System.out.println(transform);
		outBytes(transformToBytes(transform));
	}
	
	private static void outBytes(byte... bytes) {
		System.out.print(bytes[0] + "-");
		System.out.print(bytes[1] + "-");
		System.out.print(bytes[2] + "-");
		System.out.println(bytes[3]);
	}
	
	private static int transformToInt(byte[] array) {
		return ByteBuffer.wrap(array).order(ByteOrder.BIG_ENDIAN).getInt();
	}
	
	private static byte[] transformToBytes(int value) {
		return ByteBuffer.allocate(4).putInt(value).array();
	}
}
