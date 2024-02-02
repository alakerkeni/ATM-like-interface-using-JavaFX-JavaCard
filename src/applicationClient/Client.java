package applicationClient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.Socket;
import com.sun.javacard.apduio.CadT1Client;
import com.sun.javacard.apduio.Apdu;
import MyPackage.MyApplet;

public class Client {
	
	CadT1Client cad;
    Socket sckCarte;
	public void connexion() {
        System.out.println("connected");
        try {
            sckCarte = new Socket("localhost", 9025);
            sckCarte.setTcpNoDelay(true);
            BufferedInputStream input = new BufferedInputStream(sckCarte.getInputStream());
            BufferedOutputStream output = new BufferedOutputStream(sckCarte.getOutputStream());
            cad = new CadT1Client(input, output);
        } catch (Exception e) {
            System.out.println("Erreur : impossible de se connecter a la Javacard");
            return;
        }
        /* Mise sous tension de la carte */
        try {
            cad.powerUp();
        } catch (Exception e) {
            System.out.println("Erreur lors de l'envoi de la commande Powerup a la Javacard");
            return;
        }
	}
      public void selection() {
            Apdu apdu = new Apdu();
            apdu.command[Apdu.CLA] = 0x00;
            apdu.command[Apdu.INS] = (byte) 0xA4;
            apdu.command[Apdu.P1] = 0x04;
            apdu.command[Apdu.P2] = 0x00;
            byte[] appletAID = { 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x00, 0x00 };
            apdu.setDataIn(appletAID);
            
            try {
                cad.exchangeApdu(apdu);
                if (apdu.getStatus() != 0x9000) {
                    System.out.println("Erreur lors de la sélection de l'applet");
                    System.exit(1);
                }
            } catch (Exception e) {
                System.out.println("Erreur lors de l'échange APDU");
                e.printStackTrace();
            }
        }
      public void invocation1() {    
  	        try {	              	            
  	            Apdu apdu = new Apdu();
  	            apdu.command[Apdu.CLA] = MyApplet.CLA_MONAPPLET;
  	            apdu.command[Apdu.P1] = 0x00;
  	            apdu.command[Apdu.P2] = 0x00;
  	            apdu.setLe(0x7f);
  	            
  	            
  	                    apdu.command[Apdu.INS] = MyApplet.INS_INTERROGER_COMPTEUR;
  	                    cad.exchangeApdu(apdu);
  	                    if (apdu.getStatus() != 0x9000) {
  	                        System.out.println("Erreur : status word différent de 0x9000");
  	                    } else {
  	                        System.out.println("Valeur du compteur : " + apdu.dataOut[0]);
  	                    }
  	                    
  	        } catch (Exception e) {
  	            System.out.println("Erreur lors de la lecture de l'entrée");
  	            e.printStackTrace();
  	        }
  	    }
      
      public void invocation2() {    
	        try {	              	            
	            Apdu apdu = new Apdu();
	            apdu.command[Apdu.CLA] = MyApplet.CLA_MONAPPLET;
	            apdu.command[Apdu.P1] = 0x00;
	            apdu.command[Apdu.P2] = 0x00;
	            apdu.setLe(0x7f);
	            
	            
	            apdu.command[Apdu.INS] = MyApplet.INS_INCREMENTER_COMPTEUR;
                cad.exchangeApdu(apdu);
                if (apdu.getStatus() != 0x9000) {
                    System.out.println("Erreur : status word différent de 0x9000");
                } else {
                    System.out.println("OK");
                }
	                    
	        } catch (Exception e) {
	            System.out.println("Erreur lors de la lecture de l'entrée");
	            e.printStackTrace();
	        }
	    }
      
      
      public void invocation3() {    
	        try {	              	            
	            Apdu apdu = new Apdu();
	            apdu.command[Apdu.CLA] = MyApplet.CLA_MONAPPLET;
	            apdu.command[Apdu.P1] = 0x00;
	            apdu.command[Apdu.P2] = 0x00;
	            apdu.setLe(0x7f);
	            
	            
	            apdu.command[Apdu.INS] = MyApplet.INS_DECREMENTER_COMPTEUR;
                cad.exchangeApdu(apdu);
                if (apdu.getStatus() != 0x9000) {
                    System.out.println("Erreur : status word différent de 0x9000");
                } else {
                    System.out.println("OK");
                }
	                    
	        } catch (Exception e) {
	            System.out.println("Erreur lors de la lecture de l'entrée");
	            e.printStackTrace();
	        }
	    }
      
      public void invocation4(int valueToSend) {    
    	    try {        
    	        Apdu apdu = new Apdu();
    	        apdu.command[Apdu.CLA] = MyApplet.CLA_MONAPPLET;
    	        apdu.command[Apdu.P1] = 0x00;
    	        apdu.command[Apdu.P2] = 0x00;
    	        apdu.setLe(0x7f);
    	        
    	        apdu.command[Apdu.INS] = MyApplet.INS_INITIALISER_COMPTEUR;
    	        
    	        byte[] donnees = new byte[4]; // Since you're passing an int (4 bytes)
    	        donnees[0] = (byte) ((valueToSend >> 24) & 0xFF); // Most significant byte
    	        donnees[1] = (byte) ((valueToSend >> 16) & 0xFF);
    	        donnees[2] = (byte) ((valueToSend >> 8) & 0xFF);
    	        donnees[3] = (byte) (valueToSend & 0xFF); // Least significant byte
    	        
    	        apdu.setDataIn(donnees);
    	        cad.exchangeApdu(apdu);
    	        
    	        if (apdu.getStatus() != 0x9000) {
    	            System.out.println("Erreur : status word différent de 0x9000");
    	        } else {
    	            System.out.println("OK");
    	        }
    	                            
    	    } catch (Exception e) {
    	        System.out.println("Erreur lors de la lecture de l'entrée");
    	        e.printStackTrace();
    	    }
    	}

  	
      	public void mise_hors_tension() {
      		
      		try {
      			cad.powerDown();
      			System.out.println("disconnected");
      			} catch (Exception e) {
      			System.out.println("Erreur lors de l'envoi de la commande Powerdown a la Javacard");
      			return;
      			}
      	}

}
