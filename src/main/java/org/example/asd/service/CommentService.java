package org.example.asd.service;

import org.example.asd.model.Comment;
import org.example.asd.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getComments(String articleId) {
        return commentRepository.findByArticleId(articleId);
    }

    public void save(Comment comment) {
        commentRepository.save(comment);
    }
}