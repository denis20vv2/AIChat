package com.example.AIChat.Sockets.Web;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SocketServerRunner implements CommandLineRunner {

    private final SocketIOServer server;

    @Autowired
    public SocketServerRunner(SocketIOServer server) {
        this.server = server;
    }

    @Override
    public void run(String... args) {
        server.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> server.stop()));
    }
}
