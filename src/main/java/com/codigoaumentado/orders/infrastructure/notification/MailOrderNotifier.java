package com.codigoaumentado.orders.infrastructure.notification;

import com.codigoaumentado.orders.application.port.OrderNotifier;
import com.codigoaumentado.orders.domain.Order;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class MailOrderNotifier implements OrderNotifier {

    private final Mailer mailer;
    private final String recipient;

    public MailOrderNotifier(Mailer mailer, @ConfigProperty(name = "orders.notification.to") String recipient) {
        this.mailer = mailer;
        this.recipient = recipient;
    }

    @Override
    public void orderPlaced(Long orderId, Order order) {
        mailer.send(Mail.withText(recipient, "Pedido " + orderId + " creado", "Total: " + order.total()));
    }
}
