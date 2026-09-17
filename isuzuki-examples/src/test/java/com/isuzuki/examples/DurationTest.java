package com.isuzuki.examples;

import org.junit.jupiter.api.Test;

import java.time.Duration;

public class DurationTest {

    @Test
    public void testDuration() {
        System.out.println(Duration.ofNanos(346225714).toString());
    }

}
