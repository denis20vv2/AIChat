package com.example.AIChat.Sockets.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class DTOMes {
    private boolean busy; // Поле соответствует ключу "isBusy" из JSON

    // Геттер для поля isBusy
    public boolean getBusy() {
        return busy;
    }

    // Сеттер для десериализации JSON
    public void setBusy(boolean busy) {
        this.busy = busy;
    }
}

