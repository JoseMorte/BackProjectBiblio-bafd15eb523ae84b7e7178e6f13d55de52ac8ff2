package net.ausiasmarch.projectBiblio.service;

import org.springframework.stereotype.Service;

@Service
public class RandomService {
    public int getRandomInt(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }
    
}
