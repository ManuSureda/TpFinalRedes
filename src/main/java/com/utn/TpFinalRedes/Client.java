package com.utn.TpFinalRedes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client extends Thread{

//    public static void main(String[] args) {
//        Thread client = new Client();
//        client.start();
//    }

    public Client() {
    }

    @Override
    public void run() {
        DataInputStream in;
        DataOutputStream out;

        Scanner sc = new Scanner(System.in);
        String msg = "";

        int flag = 0;

        String ip;
        Integer port;

        Socket client = null;
        try {

            while (flag == 0){
                System.out.println("ingrese su direccion ip: ");
//              ip = sc.nextLine(); aca por alguna razon si uso el sc.nextLine() no lo toma y pasa directamente al port = sc.nextInt()
                ip = new DataInputStream(System.in).readLine();
                System.out.println("ingrese el puerto: ");
                port = Integer.parseInt(new DataInputStream(System.in).readLine());
                try{
                    client = new Socket(ip,port);
                    flag++;//si se logra conectar salgo del while, sino atrapo la exception, con lo cual flag seguira siendo 0
                }catch (IOException i){
                    System.out.println("Ah ocurrido un error al intentar conectarse" +
                            " por favor, ingrese los datos nuevamente...");
                }
            }



            System.out.println("Aguarde hasta recibir el mensaje de bienvenido por favor...");
            in = new DataInputStream(client.getInputStream());
            String start = in.readUTF();
            System.out.println(start);



            while (!msg.equals("x")) {
                //con esto recibire los mensajes del servidor
                in = new DataInputStream(client.getInputStream());
                //con esto mandare mensajes al Servidor
                out = new DataOutputStream(client.getOutputStream());

                //en este punto el servidor esta esperando un mensaje del cliente
                System.out.println("que quiere decirle al servidor?: ");
                msg = sc.next();

                out.writeUTF(msg);//aca mande mi mensaje al server

                if (msg.equals("x")) {
                    String bye = in.readUTF();
                    System.out.println(bye);
                    client.close();
                } else {
                    String ans = in.readUTF();//y aca leo la respuesta del server
                    System.out.println("respuesta del server: " + ans);
                }
            }
            sc.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
