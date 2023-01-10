package com.ftn.clinicCentre.security.encryption;

import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;

import org.springframework.stereotype.Component;

import com.mysql.cj.x.protobuf.MysqlxExpect.Open.Condition.Key;

@Component
public class AttributeEncryptor implements AttributeConverter<String, String>{
	
	private static final String AES = "AES";
    private static final String SECRET = "cKZVywHJtR7u7qmxRsfmBTiU7doKBamF";
    
    private final SecretKeySpec key;
    private final Cipher cipher;
    
    public AttributeEncryptor() throws Exception {
        key = new SecretKeySpec(SECRET.getBytes(), AES);
        cipher = Cipher.getInstance(AES);
    }

	@Override
	public String convertToDatabaseColumn(String attribute) {
		if(attribute == null) {
			return "";
		}
		try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Base64.getEncoder().encodeToString(cipher.doFinal(attribute.getBytes()));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public String convertToEntityAttribute(String dbData) {
		try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(Base64.getDecoder().decode(dbData)));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        
        }
	}

}
