package cyou.jelle;

import static org.junit.jupiter.api.Assertions.*;

class Day03Test {

    @org.junit.jupiter.api.Test
    void main() {
        var mapper = Year2022Day03.starOneMapper();
        assertEquals(16,mapper.apply("vJrwpWtwJgWrhcsFMMfFFhFp"));
        assertEquals(38,mapper.apply("jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL"));
        assertEquals(42,mapper.apply("PmmdzqPrVvPwwTWBwg"));
        assertEquals(22,mapper.apply("wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn"));
        assertEquals(20,mapper.apply("ttgJtRGJQctTZtZT"));
        assertEquals(19,mapper.apply("CrZsJsPPZsGzwwsLwLmpwMDw"));
    }
}