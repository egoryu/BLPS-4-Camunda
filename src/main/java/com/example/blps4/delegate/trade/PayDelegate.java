package com.example.blps4.delegate.trade;

import com.example.blps4.dto.request.ItemDto;
import com.example.blps4.dto.request.UserDto;
import com.example.blps4.dto.request.WalletDto;
import com.example.blps4.service.TradeService;
import com.example.blps4.service.WalletService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class PayDelegate implements JavaDelegate {
    private final WalletService walletService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        try {
            String orderId = delegateExecution.getVariable("payOrderId").toString();
            String username = delegateExecution.getVariable("username").toString();
            String password = delegateExecution.getVariable("password").toString();
            log.info("kek1");
            walletService.paymentForOder(new UserDto(username, " ", password, " "), Integer.parseInt(orderId));
        } catch (Exception e) {
            delegateExecution.setVariable("error", e.getMessage());
            throw new BpmnError("pay_error", e.getMessage());
        }

        log.info("Задача выполнена оплата заказа: " + new java.util.Date() + " " + delegateExecution.getVariable("payOrderId"));
    }
}
