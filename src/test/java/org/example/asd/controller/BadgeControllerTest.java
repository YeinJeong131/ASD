package org.example.asd.controller;

import org.example.asd.model.Badge;
import org.example.asd.service.BadgeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class BadgeControllerTest {

    private BadgeService badgeService;
    private BadgeController badgeController;
    private Model model;

    @BeforeEach
    void setUp() {
        badgeService = mock(BadgeService.class);
        model = mock(Model.class);
        badgeController = new BadgeController(badgeService); // must match constructor signature
    }

    @Test
    void testShowBadges() {
        List<Badge> badges = List.of(new Badge("Java Mastery"), new Badge("Spring Expert"));
        when(badgeService.getAllBadges()).thenReturn(badges);

        String viewName = badgeController.showBadges(model);

        assertEquals("badges", viewName);
        verify(model).addAttribute("badges", badges);
    }
}