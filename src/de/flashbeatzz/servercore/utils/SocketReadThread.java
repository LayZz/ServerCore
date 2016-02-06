package de.flashbeatzz.servercore.utils;

import de.flashbeatzz.servercore.ServerCore;
import de.flashbeatzz.servercore.utils.events.MessageEvent;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class SocketReadThread implements Runnable {

    @Override
    public void run() {
        Socket socket = ServerCore.socket;
        try {
            Scanner scanner = new Scanner(socket.getInputStream());
            while (true) {
                if(scanner.hasNext()) {
                    String[] array = scanner.nextLine().split("/§§/");
                    String header = array[0];
                    String message = array[1];
                    Bukkit.getPluginManager().callEvent(new MessageEvent(header, message));
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
