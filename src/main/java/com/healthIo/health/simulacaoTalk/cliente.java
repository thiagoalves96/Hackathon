package com.healthIo.health.simulacaoTalk;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

/*
 * @author Thiago Alves Dos Santos
 */

public class Cliente {
    private static Socket conexaoSocket;
    private static DataInputStream entradaDados;
    private static DataOutputStream saidaDados;
    private static Scanner scanner;

    public static void main(String[] args) {

        try {
            scanner = new Scanner(System.in);

            // Conectar com o servidor
            conexaoSocket = new Socket("localhost", 50000);

            // Enviar um número de CPF como string
            saidaDados = new DataOutputStream(conexaoSocket.getOutputStream());
            System.out.println("Digite um CPF para verificação: ");
            String cpf = scanner.nextLine();
            saidaDados.writeUTF(cpf); // Envia a string com informações de comprimento
            saidaDados.flush();

            // Receber a resposta do servidor
            entradaDados = new DataInputStream(conexaoSocket.getInputStream());
            String resposta = entradaDados.readUTF(); // Lê a resposta como string
            System.out.println("Resposta do servidor: " + resposta);

            // Fechar conexão
            conexaoSocket.close();

        } catch (Exception ex) {
            Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}
