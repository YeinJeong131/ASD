package org.example.asd.service;

import org.example.asd.model.Comment;
import org.example.asd.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CommentServiceTest {

    private CommentService commentService;
    private CommentRepository commentRepository;

    @BeforeEach
    void setUp() {
        commentRepository = mock(CommentRepository.class);
        commentService = new CommentService(commentRepository);
    }

    @Test
    void testGetCommentsByArticleId() {
        Comment comment = new Comment();
        comment.setArticleId(1L);
        comment.setUsername("John");
        comment.setContent("Great article!");

        when(commentRepository.findByArticleId(1L)).thenReturn(List.of(comment));

        List<Comment> result = commentService.getCommentsByArticleId(1L);

        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getUsername());
        assertEquals("Great article!", result.get(0).getContent());
    }

    @Test
    void testSaveComment() {
        Comment comment = new Comment();
        comment.setArticleId(2L);
        comment.setUsername("Jane");
        comment.setContent("Nice read!");

        commentService.saveComment(comment);

        verify(commentRepository, times(1)).save(comment);
    }
}