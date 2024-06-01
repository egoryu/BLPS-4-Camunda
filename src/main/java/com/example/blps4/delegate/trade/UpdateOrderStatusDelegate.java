package com.example.blps4.delegate.trade;

import com.example.blps4.dto.request.OrderDto;
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
public class UpdateOrderStatusDelegate implements JavaDelegate {
    private final TradeService tradeService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        try {
            String orderId = delegateExecution.getVariable("payOrderId").toString();
            String orderStatus = delegateExecution.getVariable("isContinue").toString();
            tradeService.updateOrder(Integer.parseInt(orderId), Integer.parseInt(orderStatus));
        } catch (Exception e) {
            delegateExecution.setVariable("error", e.getMessage());
            throw new BpmnError("update_order_status_error", e.getMessage());
        }

        log.info("Задача выполнена обновление статуса заказа: " + new java.util.Date() + " " + delegateExecution.getVariable("payOrderId"));
    }
}
