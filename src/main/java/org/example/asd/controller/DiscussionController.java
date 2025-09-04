package org.example.asd.controller;

import org.example.asd.model.Comment;
import org.example.asd.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DiscussionController {

    private final CommentService commentService;

    public DiscussionController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/discussion")
    public String showDiscussion(@RequestParam(defaultValue = "1") Long articleId, Model model) {
        model.addAttribute("articleId", articleId);
        model.addAttribute("comments", commentService.getCommentsByArticleId(articleId));
        model.addAttribute("comment", new Comment()); // for form binding
        return "discussion";
    }

    @PostMapping("/discussion")
    public String postComment(@ModelAttribute Comment comment) {
        commentService.saveComment(comment);
        return "redirect:/discussion?articleId=" + comment.getArticleId();
    }
}