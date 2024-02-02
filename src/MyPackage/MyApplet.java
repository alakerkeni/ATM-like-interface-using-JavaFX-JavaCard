package MyPackage;
import javacard.framework.APDU;
import javacard.framework.Applet;
import javacard.framework.ISOException;
import javacard.framework.ISO7816;
import javacard.framework.Util;

public class MyApplet extends Applet {

    /* Constantes */
    public static final byte CLA_MONAPPLET = (byte) 0xB0;
    public static final byte INS_INCREMENTER_COMPTEUR = 0x00;
    public static final byte INS_DECREMENTER_COMPTEUR = 0x01;
    public static final byte INS_INTERROGER_COMPTEUR = 0x02;
    public static final byte INS_INITIALISER_COMPTEUR = 0x03;
   

    private short compteur;

    private MyApplet() {
        compteur = 10;
       
    }

    public static void install(byte bArray[], short bOffset, byte bLength)
            throws ISOException {
        new MyApplet().register();
    }

    public void process(APDU apdu) throws ISOException {
        byte[] buffer = apdu.getBuffer();
        if (this.selectingApplet())
            return;
    

       

        switch (buffer[ISO7816.OFFSET_INS]) {
        	
            case INS_INCREMENTER_COMPTEUR:
                compteur++;
                break;
            case INS_DECREMENTER_COMPTEUR:
                compteur--;
                break;
            case INS_INTERROGER_COMPTEUR:
            	buffer[0] = (byte) ((compteur >> 8) & 0xFF); // Store the most significant byte
            	buffer[1] = (byte) (compteur & 0xFF);        // Store the least significant byte
        		apdu.setOutgoingAndSend((short) 0, (short) 2);
           
                break;
            case INS_INITIALISER_COMPTEUR:
                apdu.setIncomingAndReceive();
                short inputValue = Util.getShort(buffer, ISO7816.OFFSET_CDATA);             
                compteur = inputValue;
                break;
            default:
                ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
        }
    }
}