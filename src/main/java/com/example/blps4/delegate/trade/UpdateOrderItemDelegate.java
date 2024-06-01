package com.example.blps4.delegate.trade;

import com.example.blps4.dto.request.ItemDto;
import com.example.blps4.dto.request.OrderDto;
import com.example.blps4.dto.request.WalletDto;
import com.example.blps4.service.TradeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class UpdateOrderItemDelegate implements JavaDelegate {
    private final TradeService tradeService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        try {
            String orderId = delegateExecution.getVariable("payOrderId").toString();
            String itemId = delegateExecution.getVariable("payItemId").toString();
            tradeService.updateOrderItem(new WalletDto(0, 0, List.of(new ItemDto(Integer.parseInt(itemId), " ", 0, " "))), Integer.parseInt(orderId));
        } catch (Exception e) {
            delegateExecution.setVariable("error", e.getMessage());
            throw new BpmnError("update_order_item_error", e.getMessage());
        }

        log.info("Задача выполнена обновление заказа: " + new java.util.Date() + " " + delegateExecution.getVariable("payOrderId"));
    }
}
