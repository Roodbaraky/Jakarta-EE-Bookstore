package org.pluralsight;

import java.util.Random;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class IsbnGenerator {
    public String generateNumber() {
        return "13-45678-"+Math.abs(new Random().nextInt());
    }
}
