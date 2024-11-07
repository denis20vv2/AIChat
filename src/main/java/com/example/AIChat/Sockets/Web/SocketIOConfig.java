package com.example.AIChat.Sockets.Web;

import com.corundumstudio.socketio.SocketIOServer;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class SocketIOConfig {

    private final SocketIOServer server;
    private final SocketIoService socketIoService;
    @Autowired
    public SocketIOConfig(SocketIoService socketIoService) {
        this.socketIoService = socketIoService;

        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname("192.168.1.63");
        config.setPort(8081); // Порт для Socket.IO сервера

        // Инициализация сервера
        this.server = new SocketIOServer(config);
    }

    @Bean
    public SocketIOServer socketIOServer() {
        return server;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startSocketIOServer() {
        server.addListeners(socketIoService); // Регистрируем слушатели после загрузки контекста
        server.start();
        System.out.println("Socket.IO server started on port 8081");
    }

    @PreDestroy
    public void stopSocketIOServer() {
        server.stop();
        System.out.println("Socket.IO server stopped");
    }
}
