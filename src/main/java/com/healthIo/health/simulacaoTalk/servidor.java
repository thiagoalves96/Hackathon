package com.healthIo.health.simulacaoTalk;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;
/*
 * @author Thiago Alves Dos Santos
 */

public class Servidor {
    private static ServerSocket servidorServerSocket;
    private static Socket conexaoSocket;
    private static DataInputStream entradaDados;
    private static DataOutputStream saidaDados;

    public static void main(String[] args) {
        try {
            // Especificar uma porta e aguardar conexão
            servidorServerSocket = new ServerSocket(50000);
            System.out.println("Servidor aguardando conexão...");
            conexaoSocket = servidorServerSocket.accept();

            // Receber CPF do cliente
            entradaDados = new DataInputStream(conexaoSocket.getInputStream());
            String cpf = entradaDados.readUTF();

            // Realizar verificação do CPF
            String resulString;
            if (isValided(cpf)) {
                resulString = "O CPF É VÁLIDO!";
            } else {
                resulString = "O CPF NÃO É VÁLIDO!";
            }

            // Retornar resposta ao cliente
            saidaDados = new DataOutputStream(conexaoSocket.getOutputStream());
            saidaDados.writeUTF(resulString); // Envia a resposta como string
            saidaDados.flush();

            // Finalizar conexão
            conexaoSocket.close();
            servidorServerSocket.close();

        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    public static boolean isValided(String cpf) {

        if (cpf == null)
            return false;

        cpf = cpf.replaceAll("\\D", "");

        if (!cpf.matches("\\d{11}$") || cpf.matches("(\\d)\\1{10}"))
            return false;
        try {
            int sum1 = 0, sum2 = 0;

            for (int i = 0; i < 9; i++) {
                int digit = Character.getNumericValue(cpf.charAt(i));
                sum1 += digit * (10 - i);
                sum2 += digit * (11 - i);
            }

            int dv1 = 11 - (sum1 % 11);
            dv1 = (dv1 >= 10) ? 0 : dv1;
            sum2 += dv1 * 2;

            int dv2 = 11 - (sum2 % 11);
            dv2 = (dv2 >= 10) ? 0 : dv2;

            return dv1 == Character.getNumericValue(cpf.charAt(9)) &&
                    dv2 == Character.getNumericValue(cpf.charAt(10));
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
