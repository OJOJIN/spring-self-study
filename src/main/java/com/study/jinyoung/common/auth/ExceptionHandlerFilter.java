package com.study.jinyoung.common.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.jinyoung.common.error.ApplicationError;
import com.study.jinyoung.common.error.ForbiddenException;
import com.study.jinyoung.common.error.UnauthorizedException;
import com.study.jinyoung.common.error.dto.ErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (UnauthorizedException e) {
            handleUnauthorizedException(response, e);
        } catch (ForbiddenException e) {
            handleForbiddenException(response, e);
        } catch (Exception ee) {
            handleException(response);
        }
    }

    private void handleUnauthorizedException(HttpServletResponse response, Exception e) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        if (e instanceof UnauthorizedException ue) {
            response.setStatus(ue.getError().getStatus().value());;
            response.getWriter().write(objectMapper.writeValueAsString(ErrorResponse.of(ue.getError())));
        }
    }


    private void handleForbiddenException(HttpServletResponse response, Exception e) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        if (e instanceof ForbiddenException fe) {
            response.setStatus(fe.getError().getStatus().value());;
            response.getWriter().write(objectMapper.writeValueAsString(ErrorResponse.of(fe.getError())));
        }
    }

    private void handleException(HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        response.setStatus(ApplicationError.INTERNAL_SERVER_ERROR.getStatus().value());
        response.getWriter().write(objectMapper.writeValueAsString(ErrorResponse.of(ApplicationError.INTERNAL_SERVER_ERROR)));
    }
}
