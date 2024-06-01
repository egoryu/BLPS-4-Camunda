package com.example.blps4.delegate.trade;

import com.example.blps4.dto.request.MessageDto;
import com.example.blps4.dto.request.OrderDto;
import com.example.blps4.service.ContractService;
import com.example.blps4.service.TradeService;
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
public class CreateOrderDelegate implements JavaDelegate {
    private final TradeService tradeService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        try {
            String type = delegateExecution.getVariable("payType").toString();
            String sum = delegateExecution.getVariable("paySum").toString();
            String date;
            if (delegateExecution.getVariable("payDate") == null) {
                date = LocalDateTime.now().toString();
            } else {
                date = delegateExecution.getVariable("payDate").toString();
            }
            String userId = delegateExecution.getVariable("payUserId").toString();
            String traderId = delegateExecution.getVariable("payTraderId").toString();
            String contractorId = delegateExecution.getVariable("payContractorId").toString();
            tradeService.createOrder(new OrderDto(0, Integer.parseInt(type), Integer.parseInt(sum), LocalDateTime.parse(date), Integer.parseInt(userId), Integer.parseInt(traderId), Integer.parseInt(contractorId)));
        } catch (Exception e) {
            delegateExecution.setVariable("error", e.getMessage());
            throw new BpmnError("create_order_error", e.getMessage());
        }

        log.info("Задача выполнена создание заказа: " + new java.util.Date() + " " + delegateExecution.getVariable("payUserId"));
    }
}
