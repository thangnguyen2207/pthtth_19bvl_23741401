package tk1;


import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    static List<ClientHandler> clients = new ArrayList<>();
    static String[] rooms = {"Study", "Game", "Trải Nghiệm"};

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(12345)) {
            System.out.println("Server đang chạy...");

            while (true) {
                Socket socket = server.accept();
                ClientHandler client = new ClientHandler(socket);
                new Thread(client).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static synchronized void broadcast(String message, String room, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client.room.equals(room) && client != sender) {
                client.out.println(message);
            }
        }
    }

    static synchronized void sendRooms(PrintWriter out) {
        out.println("ROOMS|Study|Game|Trải Nghiệm");
    }

    static class ClientHandler implements Runnable {
        Socket socket;
        BufferedReader in;
        PrintWriter out;
        String name;
        String room = "";

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), "UTF-8"));
                out = new PrintWriter(
                    new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

                name = in.readLine();

                synchronized (clients) {
                    clients.add(this);
                }

                sendRooms(out);

                String message;

                while ((message = in.readLine()) != null) {
                    if (message.startsWith("JOIN|")) {
                        int choice = Integer.parseInt(message.substring(5));

                        if (choice >= 1 && choice <= rooms.length) {
                            room = rooms[choice - 1];
                            out.println("JOINED|" + room);
                            broadcast(name + " đã tham gia phòng.", room, this);
                        }
                    } else if (message.equals("/quit")) {
                        if (!room.isEmpty()) {
                            broadcast(name + " đã rời phòng.", room, this);
                        }

                        room = "";
                        sendRooms(out);
                    } else if (message.equals("/EXIT")) {
                        break;
                    } else if (!room.isEmpty()) {
                        broadcast(name + ": " + message, room, this);
                    }
                }
            } catch (IOException e) {
            } finally {
                synchronized (clients) {
                    clients.remove(this);
                }

                if (!room.isEmpty()) {
                    broadcast(name + " đã ngắt kết nối.", room, this);
                }

                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
    }
}

