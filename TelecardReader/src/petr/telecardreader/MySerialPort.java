package petr.telecardreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class MySerialPort  {

	private BufferedReader bfi;
	private OutputStream out;
	SerialPort serialPort = null;

	public String odpoved = null;
	public boolean portOK = false;
	public ComRes res;

	//private int baudRate;
	private String devName;
	
	int commNumber = 0;

	
	/**
	 * kostruktor
	 * @param devName
	 * @param baudRate
	 */
	public MySerialPort(String devName, int baudRate) {

		//this.baudRate = baudRate;
		this.devName = devName;
		this.res = new ComRes();
		
		try {
			if (isAvailable(devName) == true) {
				portOK = true;
				connect(devName, baudRate);
			} else {
				portOK = false;
				return;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			portOK = false;
			e.printStackTrace();
		}

	}
	
	boolean isAvailable(String portName) {

		Enumeration ports = CommPortIdentifier.getPortIdentifiers();
		//ArrayList portList = new ArrayList();
		while (ports.hasMoreElements()) {
			CommPortIdentifier port = (CommPortIdentifier) ports.nextElement();
			if ((port.getPortType() == CommPortIdentifier.PORT_SERIAL) && (port.getName().equals(portName))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Vytvori spojeni se seriovym portem
	 * @param portName
	 * @param baudRate
	 * @throws Exception
	 */
	void connect (String portName, int baudRate) throws Exception {
		
		CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
		if ( portIdentifier.isCurrentlyOwned() ) {
			System.out.println("Error: Port is currently in use");
		} else {
			CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);

			if ( commPort instanceof SerialPort ) {
				SerialPort serialPort = (SerialPort) commPort;
				serialPort.setSerialPortParams(baudRate,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);

				bfi = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
				out = serialPort.getOutputStream();
				
				serialPort.addEventListener(new SerialEventHandler());
				serialPort.notifyOnDataAvailable(true);
				this.serialPort = serialPort;
				//System.out.println("Port otevren a cteni a zapis pripraveno");
			}
			else
			{
				System.out.println("Neni seriovy port");
			}
		}     
	}	
	
	/** vlakno, ktere posle prikaz do ctecky a ceka az event handler naplni vystupni promennou
	 * nebo neni preruseno 
	 * */
	public class SerialWriter implements Runnable {
		
		OutputStream out;
		String command;

		public SerialWriter (OutputStream out, String command) {
			
			this.out = out;
			this.command = command;
		}

		public void run() {
			
			try {    
				res.setCommand(command);
				res.setAnswer("");
				this.out.write(command.getBytes());
				//System.out.println("Command: "+command);
				while (res.getAnswer().equals("")) {
					if (java.lang.Thread.interrupted()) {
						return;
					}
					try {
						java.lang.Thread.sleep(10);
					} catch (Exception e) {
						//e.printStackTrace();
						System.out.println("Sleep interrupted...");
						return;
					}
				}
				//System.out.println("Odpoved: "+res.getAnswer());
			}                
			catch ( IOException e ) {
				e.printStackTrace();
			}            
		}
	}

	
	/** event handler starajici se o data, co se vratila ze ctecky **/
	private class SerialEventHandler implements SerialPortEventListener {
		public synchronized void serialEvent(SerialPortEvent event) {
			if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
		        try {
		            odpoved = bfi.readLine();
		            res.setAnswer(odpoved);
		        } catch (IOException ex) {
		        	ex.printStackTrace();
		        }
		        //System.out.println(odpoved);
	        }
		}
	}
	
	/**
	 * Vlakno, co ridi zobrazeni vystupnich promennych
	 * 
	 */
	public class SenderReceiver implements Runnable {

		String command;

		/**
		 * konstruktor
		 * @param command
		 */
		public SenderReceiver(String command) {
			this.command = command;
		}

		public void sendReceive(String command) {
			
			int pocPokus = 3;

			Thread th = new Thread(new SerialWriter(out, command)); 
			th.start();
			try {
				th.join(2000);
				if (th.isAlive()) {
					th.interrupt();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			TelecardReader.serialAnswer = res.getAnswer();
			if ((command.equals("DATAOK") && (TelecardReader.serialAnswer.equals("CARDstart") == false) && (TelecardReader.serialAnswer.equals("CARDend") == false))) {
				TelecardReader.commandTable1.add(new ComRes(command, TelecardReader.serialAnswer));
				//System.out.println(commNumber+": "+command+" - "+TelecardReader.serialAnswer);
				commNumber++;
			}

			if (command.equals("TEST") || command.equals("READ0") || command.equals("READ1") || command.equals("READOK") || command.equals("SENDOK")) {
				if (TelecardReader.serialAnswer != "") {
					TelecardReader.statusRegistr = TelecardReader.serialAnswer;
					TelecardReader.commProcess = false;
				} else {
					if (command.equals("TEST")) {
						pocPokus--;
						if (pocPokus == 0) {
							TelecardReader.statusRegistr = "Bez odpovědi";
							TelecardReader.commProcess = false;
						}
					}
				}
			}
			if (command.equals("DATAOK") && TelecardReader.serialAnswer.equals("CARDend") == false ) {
				TelecardReader.statusRegistr = "Přijímám data ze čtečky...";
			}
			else if (command.equals("DATAOK") && TelecardReader.serialAnswer.equals("CARDend")) {
				TelecardReader.commProcess = false;
				TelecardReader.statusRegistr = "Příjem dat ukončen.";
			}
		}
		
		public void run() {

			while (TelecardReader.commProcess == true) {

				sendReceive(TelecardReader.command);

				if (TelecardReader.command.equals("READ0") || TelecardReader.command.equals("READ1")) {
					if (TelecardReader.serialAnswer.equals("Reading...") == true) {
						sendReceive("READOK");
						sendReceive("SENDOK");
						commNumber = 0;
						sendReceive("DATAOK");
						while (TelecardReader.serialAnswer.equals("CARDend") == false) {
							sendReceive("DATAOK");
						} 
					}
				}
			}
		}
	}

	public void sendCommandToReader(String command) {

		/*
		if (!(isAvailable(devName))) {
			portOK = false;
			TelecardReader.recError(new ErrorRecord("Čtečka byla odpojena. Připojte čtečku znovu.", -17));
		}
		*/
		if (portOK) {
			Thread thr = new Thread(new SenderReceiver(command));
			thr.start();
		} else {
			TelecardReader.recError(new ErrorRecord("Sériový port "+ devName +" není k dispozici", -2));
		}

	}
}
