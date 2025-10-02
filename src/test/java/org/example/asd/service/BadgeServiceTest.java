package org.example.asd.service;

import org.example.asd.model.Badge;
import org.example.asd.repository.BadgeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class BadgeServiceTest {

    private BadgeRepository badgeRepository;
    private BadgeService badgeService;

    @BeforeEach
    void setUp() {
        badgeRepository = mock(BadgeRepository.class);
        badgeService = new BadgeService(badgeRepository);
    }

    @Test
    void testGetAllBadges() {
        List<Badge> expectedBadges = List.of(new Badge("Leader"), new Badge("Innovator"));
        when(badgeRepository.findAll()).thenReturn(expectedBadges);

        List<Badge> result = badgeService.getAllBadges();

        assertEquals(expectedBadges.size(), result.size());
        assertEquals("Leader", result.get(0).getName());
    }
}