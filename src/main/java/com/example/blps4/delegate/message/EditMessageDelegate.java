package com.example.blps4.delegate.message;

import com.example.blps4.dto.request.MessageDto;
import com.example.blps4.service.ContractService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@AllArgsConstructor
public class EditMessageDelegate implements JavaDelegate {
    private final ContractService contractService;
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        try {
            String id = delegateExecution.getVariable("messageId").toString();
            String usernameFrom = delegateExecution.getVariable("messageUsernameFrom").toString();
            String usernameTo = delegateExecution.getVariable("messageUsernameTo").toString();
            String text = delegateExecution.getVariable("messageText").toString();
            String date;
            if (delegateExecution.getVariable("messageDate") == null) {
                date = LocalDateTime.now().toString();
            } else {
                date = delegateExecution.getVariable("messageDate").toString();
            }
            String type = delegateExecution.getVariable("messageType").toString();
            contractService.editMessage(new MessageDto(Integer.parseInt(id), LocalDateTime.parse(date), text, Integer.parseInt(type), usernameFrom, usernameTo));
        } catch (Exception e) {
            delegateExecution.setVariable("error", e.getMessage());
            throw new BpmnError("edit_message_error", e.getMessage());
        }

        log.info("Задача выполнена изменение сообщения: " + new java.util.Date() + " " + delegateExecution.getVariable("messageUsernameFrom"));
    }
}
