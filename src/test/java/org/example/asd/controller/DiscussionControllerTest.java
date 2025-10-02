package org.example.asd.controller;

import org.example.asd.model.Comment;
import org.example.asd.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class DiscussionControllerTest {

    private CommentService commentService;
    private DiscussionController discussionController;
    private Model model;

    @BeforeEach
    void setUp() {
        commentService = mock(CommentService.class);
        discussionController = new DiscussionController(commentService);
        model = mock(Model.class);
    }

    @Test
    void testShowDiscussion() {
        Long articleId = 1L;
        List<Comment> comments = List.of(
                new Comment(articleId, "Alice", "Nice article!"),
                new Comment(articleId, "Bob", "Very helpful.")
        );
        when(commentService.getCommentsByArticleId(articleId)).thenReturn(comments);

        String viewName = discussionController.showDiscussion(articleId, model);

        assertEquals("discussion", viewName);
        verify(model).addAttribute(eq("comments"), eq(comments));
        verify(model).addAttribute(eq("articleId"), eq(articleId));
        verify(model).addAttribute(eq("newComment"), any(Comment.class));
    }

    @Test
    void testPostComment() {
        Long articleId = 1L;
        Comment comment = new Comment();
        comment.setArticleId(articleId);
        comment.setUsername("Charlie");
        comment.setContent("Great work!");

        String viewName = discussionController.postComment(articleId, comment);

        assertEquals("redirect:/discussion/" + articleId, viewName);
        verify(commentService).saveComment(comment);
    }
}