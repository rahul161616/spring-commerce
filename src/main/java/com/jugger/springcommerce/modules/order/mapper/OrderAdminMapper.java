package com.jugger.springcommerce.modules.order.mapper;

import com.jugger.springcommerce.modules.order.dto.OrderAdminResponse;
import com.jugger.springcommerce.modules.order.enums.OrderStatus;
import com.jugger.springcommerce.modules.order.enums.PaymentProvider;
import com.jugger.springcommerce.modules.order.enums.PaymentVerificationStatus;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OrderAdminMapper {

    public OrderAdminResponse mapToAdminResponse(ResultSet rs) throws SQLException {
        return OrderAdminResponse.builder()
                .id(rs.getLong("id"))
                .orderCode(rs.getString("order_code"))
                .sessionId(rs.getString("session_id"))
                .customerName(rs.getString("customer_name"))
                .customerPhone(rs.getString("customer_phone"))
                .customerEmail(rs.getString("customer_email"))
                .currencyCode(rs.getString("currency_code"))
                .subtotalAmount(rs.getBigDecimal("subtotal_amount"))
                .discountAmount(rs.getBigDecimal("discount_amount"))
                .taxAmount(rs.getBigDecimal("tax_amount"))
                .shippingAmount(rs.getBigDecimal("shipping_amount"))
                .grandTotalAmount(rs.getBigDecimal("grand_total_amount"))
                .status(OrderStatus.valueOf(rs.getString("status")))
                .notes(rs.getString("notes"))
                .itemCount(rs.getInt("item_count"))
                .createdAt(rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toInstant() : null)
                .paymentSubmissionId(rs.getObject("payment_submission_id", Long.class))
                .paymentProvider(rs.getString("payment_provider") != null ? PaymentProvider.valueOf(rs.getString("payment_provider")) : null)
                .paymentVerificationStatus(
                        rs.getString("payment_verification_status") != null
                                ? PaymentVerificationStatus.valueOf(rs.getString("payment_verification_status"))
                                : null
                )
                .expectedAmount(rs.getBigDecimal("expected_amount"))
                .paidAmount(rs.getBigDecimal("paid_amount"))
                .payerMobile(rs.getString("payer_mobile"))
                .transactionReference(rs.getString("transaction_reference"))
                .receiptImageUrl(rs.getString("receipt_image_url"))
                .paymentRemarks(rs.getString("payment_remarks"))
                .submittedAt(rs.getTimestamp("submitted_at") != null ? rs.getTimestamp("submitted_at").toInstant() : null)
                .build();
    }
}
