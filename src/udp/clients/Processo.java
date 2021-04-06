package udp.clients;
import java.net.DatagramSocket;
import java.net.DatagramPacket; 
import java.net.InetAddress;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Processo {
	
	String nomeProcesso;
	int id;
	int porta;
	int vizinho;
	public Processo (String nome, int id, int porta, int vizinho) {
		this.nomeProcesso = nome;
		this.id = id;
		this.porta = porta;
		this.vizinho = vizinho;
	}
	
	public void execute() {
		try {
			try (Scanner in = new Scanner(System.in)) {
				DatagramPacket packet;
				
				DatagramSocket socket = new DatagramSocket(porta);
				System.out.println("Cliente "+nomeProcesso+" UDP executando na porta "+porta+", com ID: "+id);
				
				ExecutorService executorService = Executors.newCachedThreadPool();
				executorService.execute(new Recebedor(socket, vizinho, id ));

				while(true) {
					byte[] output = new byte[1024];
					
					System.out.println("Msg a ser Enviada: ");
					String msg = in.nextLine();
					msg = msg.replace("\n", "").replace("\"", "");
					msg = msg+":"+id;
					output = msg.getBytes();
					packet = new DatagramPacket(output, output.length, InetAddress.getLocalHost(), vizinho);				

					socket.send(packet);
				}
			}
		
		}catch(IOException e) {
			System.err.println("Erro no envio de mensagem");
		}
	}
}
