package org.example.asd.controller;

import org.example.asd.model.Comment;
import org.example.asd.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class DiscussionController {

    private final CommentService commentService;

    public DiscussionController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/discussion/{articleId}")
    public String showDiscussion(@PathVariable Long articleId, Model model) {
        List<Comment> comments = commentService.getCommentsByArticleId(articleId);
        model.addAttribute("comments", comments);
        model.addAttribute("newComment", new Comment()); // for form binding
        model.addAttribute("articleId", articleId);
        return "discussion"; // must match templates/discussion.html
    }

    @PostMapping("/discussion/{articleId}")
    public String postComment(@PathVariable Long articleId,
                              @ModelAttribute("newComment") Comment comment) {
        comment.setArticleId(articleId);
        commentService.saveComment(comment);
        return "redirect:/discussion/" + articleId;
    }
}