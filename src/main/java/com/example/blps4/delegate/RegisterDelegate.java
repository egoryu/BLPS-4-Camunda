package com.example.blps4.delegate;

import com.example.blps4.dto.request.UserDto;
import com.example.blps4.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class RegisterDelegate implements JavaDelegate {
    private final AuthService authService;
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        try {
            String username = delegateExecution.getVariable("username").toString();
            String email = delegateExecution.getVariable("email").toString();
            String password = delegateExecution.getVariable("password").toString();
            String role = delegateExecution.getVariable("role").toString();
            authService.registerUser(new UserDto(username, email, password, role));
        } catch (Exception e) {
            delegateExecution.setVariable("error", e.getMessage());
            throw new BpmnError("register_error", e.getMessage());
        }

        log.info("Задача выполнена регистрация: " + new java.util.Date() + " " + delegateExecution.getVariable("username"));
    }
}
