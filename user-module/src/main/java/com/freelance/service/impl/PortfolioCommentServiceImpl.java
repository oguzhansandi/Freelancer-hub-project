package com.freelance.service.impl;

import com.freelance.exception.BaseException;
import com.freelance.exception.ErrorMessage;
import com.freelance.exception.MessageType;
import com.freelance.model.*;
import com.freelance.repository.PortfolioCommentRepository;
import com.freelance.repository.PortfolioRepository;
import com.freelance.repository.UserRepository;
import com.freelance.service.IPortfolioCommentService;
import com.freelance.services.CommonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PortfolioCommentServiceImpl implements IPortfolioCommentService {

    @Autowired
    private PortfolioCommentRepository portfolioCommentRepository;

    @Autowired
    private CommonService commonService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PortfolioCommentResponse addComment(Long portfolioId, PortfolioCommentRequest request) {
        String username = commonService.getCurrentUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.USER_NOT_FOUND, "username : " + username)
                ));

        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.DATA_NOT_FOUND, "portfolio bulunamadı")
                ));

        PortfolioComment comment = new PortfolioComment();
        comment.setUser(user);
        comment.setPortfolio(portfolio);
        comment.setContent(request.getContent());
        comment.setCreateDate(new Date());

        portfolioCommentRepository.save(comment);
        PortfolioCommentResponse commentResponse = new PortfolioCommentResponse();

        modelMapper.map(comment, commentResponse);
        commentResponse.setUser(user.getUsername());
        return commentResponse;
    }

    @Override
    public List<PortfolioCommentResponse> getCommentList(Long portfolioId) {

        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.DATA_NOT_FOUND, "porfolyo bulunamadı.")
                ));
        List<PortfolioComment> comments = portfolio.getComments();

        return comments.stream().map(
                comment->{
                    PortfolioCommentResponse response = new PortfolioCommentResponse();
                    modelMapper.map(comment, response);
                    response.setUser(comment.getUser().getUsername());
                    return response;
                }).toList();
    }
}
