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

    @GetMapping("/discussion/{articleId}")
    public String showDiscussion(@PathVariable String articleId, Model model) {
        model.addAttribute("comments", commentService.getComments(articleId));
        model.addAttribute("articleId", articleId);
        model.addAttribute("comment", new Comment());
        return "discussion";
    }

    @PostMapping("/discussion")
    public String postComment(@ModelAttribute Comment comment) {
        commentService.save(comment);
        return "redirect:/discussion/" + comment.getArticleId();
    }
}