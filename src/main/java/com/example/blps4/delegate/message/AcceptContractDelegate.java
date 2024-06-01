package com.example.blps4.delegate.message;

import com.example.blps4.dto.request.MessageDto;
import com.example.blps4.service.ContractService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class AcceptContractDelegate implements JavaDelegate {
    private final ContractService contractService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        try {
            String id = delegateExecution.getVariable("messageId").toString();
            MessageDto messageDto = contractService.getMessage(Integer.parseInt(id));
            contractService.acceptContract(messageDto);
        } catch (Exception e) {
            delegateExecution.setVariable("error", e.getMessage());
            throw new BpmnError("accept_contract_error", e.getMessage());
        }

        log.info("Задача выполнена принятие контракта: " + new java.util.Date() + " " + delegateExecution.getVariable("messageId"));
    }
}
