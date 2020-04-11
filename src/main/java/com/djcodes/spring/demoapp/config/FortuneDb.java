package com.djcodes.spring.demoapp.config;

import com.djcodes.spring.demoapp.data.Fortune;
import com.djcodes.spring.demoapp.data.FortuneRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(
    value = "app.fortune.source", havingValue = "db", matchIfMissing = true)
public class FortuneDb {

    private final FortuneRepository fortuneRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void ready() {
        int id = 1;
        List<String> fortunesList = List.of("A beautiful, smart, and loving person will be coming into your life.",
            "A dubious friend may be an enemy in camouflage.",
            "A faithful friend is a strong defense.",
            "A feather in the hand is better than a bird in the air.",
            "A fresh start will put you on your way.",
            "A friend asks only for your time not your money.",
            "A friend is a present you give yourself.",
            "A gambler not only will lose what he has, but also will lose what he doesn’t have.",
            "A golden egg of opportunity falls into your lap this month.",
            "A good friendship is often more important than a passionate romance.",
            "A good time to finish up old tasks.",
            "A hunch is creativity trying to tell you something.",
            "A lifetime friend shall soon be made.",
            "A lifetime of happiness lies ahead of you.",
            "A light heart carries you through all the hard times.",
            "A new perspective will come with the new year.",
            "A person is never to (sic) old to learn.",
            "A person of words and not deeds is like a garden full of weeds.",
            "A pleasant surprise is waiting for you.",
            "A short pencil is usually better than a long memory any day.",
            "A small donation is call for. It’s the right thing to do.",
            "A smile is your personal welcome mat.",
            "A smooth long journey! Great expectations.",
            "A soft voice may be awfully persuasive.",
            "A truly rich life contains love and art in abundance.",
            "Accept something that you cannot change, and you will feel better.");

            for (String fortune : fortunesList) {
                this.fortuneRepository.save(new Fortune(id++,fortune));
            }
    }

}
