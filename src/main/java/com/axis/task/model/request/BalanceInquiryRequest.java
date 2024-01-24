package com.axis.task.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class BalanceInquiryRequest {
    private String accountId;
}
