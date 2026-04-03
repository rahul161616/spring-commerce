package com.jugger.springcommerce.modules.order.repository;

import com.jugger.springcommerce.modules.order.model.PaymentSubmission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentSubmissionRepository extends JpaRepository<PaymentSubmission, Long> {
    boolean existsByOrderId(Long orderId);
    Optional<PaymentSubmission> findByOrderId(Long orderId);
}
