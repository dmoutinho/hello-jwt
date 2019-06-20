package br.com.dmoutinho.hello_jwt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.JWTVerifier;

/**
 * This file is intended to be used on a IDE for testing purposes.
 * ClassLoader.getSystemResource won't work in a JAR
 */
public class JWTVerifierTest {

	public static byte[] getFileInBytes(File f) throws IOException{
		FileInputStream fis = new FileInputStream(f);
		byte[] fbytes = new byte[(int) f.length()];
		fis.read(fbytes);
		fis.close();
		return fbytes;
	}
	
	public static RSAPublicKey publicKey() throws Exception {
        
		KeyFactory kf = KeyFactory.getInstance("RSA");

        String publicKeyContent = new String(getFileInBytes(new File("certificate/public_key.pem")));
        
        publicKeyContent = publicKeyContent.replaceAll("\\n", "").replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");;

        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyContent));
        RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(keySpecX509);

        System.out.println(pubKey);
        
        return pubKey;
		
	}
	
	public static void validate(RSAPublicKey publicKey) {
		
		String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaGVzdGVuIERvZSIsImFkbWluIjp0cnVlLCJpYXQiOjE1MTYyMzkwMjJ9.Y94MJadiIfgfbTMFdIJLZDkIeYOy5y09hg9lljulHJ901x6UL537yMu3MVTLZqM1XbNaQSYyQ5iVCjIVCUaaH6n68wIBxYMjl7XvQwfpXdutzFZREPIgv_DXYKheY7AOGGkl5pp0qpRJmkL4cw-fquyOntOvIClw4pUHp3j94x2E9MtsIbPXiTW_30fPBpAOIrBTSj-cru4a_76zkmi6bfOpwJXhSZiSws6u9Rdx1iF_FjdGcfq_7_WrsLm-nX4sQ1h7_mk1SkcA2BiZQiYcQJ0o3FnWaL4Dl2TUQuonkTIXfSkHTqfAuOKokXdSP_n96uabgf3D_gzeMatfdsoJow";
				
		//RSAPublicKey publicKey = null;
		RSAPrivateKey privateKey = null;
		
		try {
			
		    Algorithm algorithm = Algorithm.RSA256(publicKey,privateKey);
		    
		    JWTVerifier verifier = JWT.require(algorithm)
		        //.withIssuer("auth0")
		        .build(); //Reusable verifier instance
		    
		    verifier.verify(token);
		
		} catch (Exception exception){
			exception.printStackTrace();
		}
		
	}
	
    public static void main(String[] args) {

    	try {
    		
    		RSAPublicKey publicKey = publicKey();
    		
    		validate(publicKey);
            
		} catch (Exception e) {
			
			e.printStackTrace();

		}

    }
    
}