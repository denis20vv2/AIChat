package com.example.AIChat.Sockets.Web;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.example.AIChat.Sockets.DTO.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final SocketIOServer server;

    @Autowired
    public ChatController(SocketIOServer server) {
        this.server = server;
    }

    @OnEvent("sendMessage")
    public void onSendMessage(SocketIOClient client, MessageDto data) {
        String roomId = data.getRoomId();  // Получаем roomId из данных
        server.getRoomOperations(roomId).sendEvent("message", data);  // Отправка сообщения в комнату
    }
}
