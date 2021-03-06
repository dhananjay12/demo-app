package com.djcodes.spring.demoapp.service;

import com.djcodes.spring.demoapp.config.FortuneFile;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(
    value = "app.fortune.source", havingValue = "file")
public class FortuneServiceFile implements FortuneService {

    private final FortuneFile fortuneFile;

    @Override
    public String getFortune() {
        return fortuneFile.getFortunes().get(new Random().nextInt(fortuneFile.getFortunes().size()));
    }
}

