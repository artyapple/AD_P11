package conversation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import hybrid.services.Hybrid;
import rsa.keys.RSAKey;
import rsa.keys.RSAKeyPair;
import rsa.services.RSAService;

public class EncryptedConversation {
	public static void main(String[] args) throws IOException
    {
       
        
        RSAKeyPair rsa = new RSAService().genKeyPair();
        RSAKey e = rsa.getPrivateKey();
        RSAKey d = rsa.getPublicKey();
        //int firstModul = rsa.ge;
        Hybrid hybridKey = new Hybrid();
        System.out.println("Konversationsdaten:\n");
        
        //System.out.printf("Public Key: %d,  N(p*q): %d. \n", e, );
        System.out.printf("Public Key: %d",e);
         InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
        
        
        
        while(true){
            
           
            System.out.println("\nZu verschlüsselnden Text eingeben: \n");
            String inputText = br.readLine();
            System.out.println("Public-Key eingeben:\n");
            String inputText2 = br.readLine();
            
            int publicKey = Integer.parseInt(inputText2);
//            System.out.println("N eingeben:\n");
//            String inputText3 = br.readLine();
//            int hauptModul = Integer.parseInt(inputText3);
            

          
            System.out.println("Verschlüsselt:\n");
//----------------------------------------------------------------------------------------------------------
            //Leider ist ist sind unsere Keys in einer eigenen Klasse. Deshalb ist das übergeben der Keys in der
            // kommandozeile echt umständlich.... Hast du eine andere Idee wie wir sonst eine kleine Testumgebung
            //bauen können????
//-----------------------------------------------------------------------------------------------------------
            System.out.println(hybridKey.encryption(inputText, publicKey));
            
            
            System.out.println("\nText eingeben, der zu entschlüsseln ist: \n");
            String text = br.readLine();
            String received = hybridKey.decryptReceivedString(text, d, firstModul);
            System.out.println("Entschlüsselter Text:\n");
            System.out.println(received);
            
            
            
        }
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        

    }

}
