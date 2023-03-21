package cn.exsolo.batis.core.utils;

import java.util.UUID;

/**
 * Created by prestolive on 2018/7/25.
 */
public class GenerateID {

	public String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
		"o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8",
		"9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
		"U", "V", "W", "X", "Y", "Z" };
	
	public String generateUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}

	public String generateShortUuid() {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < 8; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x3E]);
		}
		return shortBuffer.toString();
	}

	public static String next(){
		return new GenerateIObjectID().toHexString();
	}

	public static void main(String[] args) {
		for(int i=0;i<5;i++){
			System.out.println(GenerateID.next());
		}

	}
	
}
