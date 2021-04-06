package udp.clients;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.DatagramPacket;
import java.io.IOException;

public class Recebedor implements Runnable {
	private DatagramSocket socket;
	private int vizinho;
	private int id;
	public Recebedor(DatagramSocket socket, int vizinho, int id) {
		this.socket = socket;
		this.vizinho = vizinho;
		this.id = id;
	}
	
	public String [] msgSeparada(String msg) {
		
		String [] arrayString = msg.split(":");
		return arrayString;
	}
	
	public String msgUnificada(String [] msgSeparada, int id) {
		String msgUnificada = msgSeparada[0];
		for (int i = 1; i<msgSeparada.length; i++) {
			msgUnificada = msgUnificada+":"+msgSeparada[i].replaceAll("[^0-9]", "");
		}
		
		return msgUnificada+":"+id;
	}
	
	public boolean verificaId(String [] msg) {
		
		if (Integer.parseInt(msg[1].replaceAll("[^0-9]", "")) == id) {
			return true;
		}
		return false;
	}

	@Override
	public void run() {
		while(true) {
			try {
				byte[] input = new byte[1024];
				byte[] output = new byte[1024];
				DatagramPacket inputPacket = new DatagramPacket(input, input.length);
				DatagramPacket outputPacket;

				socket.receive(inputPacket);
				String msg = new String(inputPacket.getData());
				String[] msgSeparada = msgSeparada(msg);
				if (!verificaId(msgSeparada)) {
					msg = msgUnificada(msgSeparada, id);
					output = msg.getBytes();
					outputPacket = new DatagramPacket(output, output.length, InetAddress.getLocalHost() ,vizinho);
					socket.send(outputPacket);
				}	
				System.out.print("msgRecebida: ");
				System.out.println(msgSeparada[0]);
				System.out.println("Mensagem Completa: "+msg);
			}catch(IOException e) {
				System.err.println("Erro ao receber mensagem");
			}
		}
	}
}
