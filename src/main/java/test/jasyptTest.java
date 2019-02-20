package test;

import org.jasypt.util.text.StrongTextEncryptor;

public class jasyptTest {
	
	public static void main(String[] args) {
		
		System.out.println("SDAFDSDAFDSF");
		String str = "b";
		if((!str.equals("b") || (!str.equals("c")))){
			System.out.println(str+"<<<<<<<<");
		}
		
		StrongTextEncryptor textEncryptor = new StrongTextEncryptor();
		textEncryptor.setPassword("password");
//		String encryptedText = textEncryptor.encrypt("jwyigrywuyemkvob");
//		System.out.println("Encrypted Text : "+encryptedText);
		System.out.println("decrypted :"+textEncryptor.decrypt("6L040pr+FHLltHdm6DauJsI49Q7RapCwqrB0ym/yqwc="));
	}

}
