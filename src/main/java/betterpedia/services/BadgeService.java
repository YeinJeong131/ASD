package betterpedia.services;

import betterpedia.model.Badge;
import betterpedia.repository.BadgeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BadgeService {

    private final BadgeRepository badgeRepository;

    public BadgeService(BadgeRepository badgeRepository) {
        this.badgeRepository = badgeRepository;
    }

    public List<Badge> getAllBadges() {
        return badgeRepository.findAll();
    }

    public Badge updateBadge(String username, int commentsToAdd, int editsToAdd) {
        Badge badge = badgeRepository.findByUsername(username)
                .orElseGet(() -> {
                    Badge b = new Badge();
                    b.setUsername(username);
                    return b;
                });

        badge.setCommentCount(badge.getCommentCount() + commentsToAdd);
        badge.setEditCount(badge.getEditCount() + editsToAdd);

        int total = badge.getCommentCount() + badge.getEditCount();
        if (total >= 20) badge.setBadgeLevel("Gold");
        else if (total >= 10) badge.setBadgeLevel("Silver");
        else badge.setBadgeLevel("Bronze");

        return badgeRepository.save(badge);
    }
}