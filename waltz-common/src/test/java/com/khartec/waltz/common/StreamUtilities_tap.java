package com.khartec.waltz.common;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StreamUtilities_tap {

    @Test
    public void simpleTap(){
        Set<String> tapped = new HashSet<>();
        Function<String, String> tapper = StreamUtilities.tap(tapped::add);

        Stream.of("hello", "world").map(tapper).collect(Collectors.toList());

        assertEquals(2, tapped.size());
        assertTrue(tapped.contains("hello"));
        assertTrue(tapped.contains("world"));
    }
}
