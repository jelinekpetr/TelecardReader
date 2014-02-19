package petr.telecardreader;


import gnu.io.CommPortIdentifier;
import java.util.ArrayList;
import java.util.Enumeration;

	/**
 * trida pro zjisteni seriovych portu
 *
 * TODO
 *
 * @author Ales Dostal <a href="mailto:a.dostal@gmail.com">a.dostal@gmail.com</a>
 */
public class ListSerialPorts {

    /** atribut obsahujici vybrany seriovy port */
    private CommPortIdentifier selectedPort = null;

    /** seznam vsech seriovych portu, ktere jsou k dispozici */
    private ArrayList<CommPortIdentifier> seriovePorty = new ArrayList<CommPortIdentifier>();

    /** Creates a new instance of ListSerialPorts */
    @SuppressWarnings("rawtypes")
    
	public ListSerialPorts() {
    	
        Enumeration seznamPortu = CommPortIdentifier.getPortIdentifiers();
        while (seznamPortu.hasMoreElements()) {
            CommPortIdentifier isp = (CommPortIdentifier) seznamPortu.nextElement();
            seriovePorty.add(isp);
        }
    
    }    

    public ArrayList<CommPortIdentifier> getSeriovePorty() {

        return seriovePorty;
    }

    public CommPortIdentifier getSelectedSerialPort() {
    	
        return selectedPort;
        
    }

    public CommPortIdentifier getPort(String portName) {
    	
        CommPortIdentifier result = null;
        
        for (CommPortIdentifier i : getSeriovePorty()) {
            if (i.getName().equals(portName)) {
                result = i;
            }
        }
        
        return result;
    }
}
