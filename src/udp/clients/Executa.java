package udp.clients;

import java.util.Scanner;

public class Executa {

	public static void main(String[] args) {
		try (Scanner in = new Scanner(System.in)) {
			System.out.print("Digite o nome do Processo: ");
			String nomeProcesso = in.nextLine().toUpperCase();
			System.out.print("Digite o seu ID: ");
			int id = in.nextInt();
			System.out.print("Digite o numero da porta do Processo: ");
			int porta = in.nextInt();
			System.out.print("Digite o numero da porta do seu vizinho: ");
			int vizinho = in.nextInt();
			
			Processo processo = new Processo(nomeProcesso, id, porta, vizinho);
			processo.execute();
		}
	}
}
