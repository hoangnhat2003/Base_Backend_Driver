package backend.drivor.base.service.distribution;

import backend.drivor.base.config.websocket.WebsocketClientEndpoint;
import backend.drivor.base.consumer.event.BookingEvent;
import backend.drivor.base.domain.components.SendMessageAsync;
import backend.drivor.base.domain.constant.BookingHistoryStatus;
import backend.drivor.base.domain.constant.MessageConstants;
import backend.drivor.base.domain.constant.RedisConstant;
import backend.drivor.base.domain.document.Account;
import backend.drivor.base.domain.document.BookingHistory;
import backend.drivor.base.domain.document.ChatAccount;
import backend.drivor.base.domain.message.AdminMessage;
import backend.drivor.base.domain.response.BookingHistoryResponse;
import backend.drivor.base.domain.utils.GsonSingleton;
import backend.drivor.base.domain.utils.LoggerUtil;
import backend.drivor.base.domain.utils.ServiceExceptionUtils;
import backend.drivor.base.service.ServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.ContainerProvider;
import javax.websocket.MessageHandler;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

@Component
public class BookingDistribution extends ServiceBase implements BookingEvent {

    private static final String TAG = BookingDistribution.class.getSimpleName();

    @Autowired
    private SendMessageAsync sendMessageAsync;

    public void sendMessageBookingRequestUpdated(BookingHistory bookingHistory, Account account) {

        Account user = bookingHistory.getRequester_account_id() != null
                ? accountService.findAccountById(bookingHistory.getRequester_account_id())
                : null;

        ChatAccount userChatAccount = accountService.getChatAccountByAccount(user);

        Account driver = bookingHistory.getDriver_account_id() != null
                ? accountService.findAccountById(bookingHistory.getDriver_account_id())
                : null;

        ChatAccount driverChatAccount = accountService.getChatAccountByAccount(driver);

        BookingHistoryResponse response = null;

        if (user != null && driver != null) {
            response = new BookingHistoryResponse(bookingHistory, user, driver);
        } else {
            response = new BookingHistoryResponse();
        }
        AdminMessage<BookingHistoryResponse> message = new AdminMessage<>(
                driverChatAccount.getUsername(),
                userChatAccount.getUsername(),
                MessageConstants.MESSAGE_NAME_BOOKING_REQUEST_UPDATED,
                MessageConstants.MESSAGE_TYPE_NOTIFICATION,
                response);

        String messageJson = GsonSingleton.getInstance().toJson(message);

        try {
            sendMessageAsync.send(userChatAccount, messageJson);
            LoggerUtil.i(TAG, String.format("Sending messsage booking request updated to requester: %s", message.getTo()));
        } catch (Exception e) {
            LoggerUtil.exception(TAG, e);
            throw ServiceExceptionUtils.handleApplicationException(e.getMessage());
        }
    }

    @Override
    public void arrivedBookingRequest(BookingHistory bookingHistory) {

        Account account = accountService.findAccountById(bookingHistory.getDriver_account_id());

        try {
            bookingHistory.setStatus(BookingHistoryStatus.ARRIVED);
            bookingHistory.setArrived_at(System.currentTimeMillis());
            bookingHistoryRepository.save(bookingHistory);

            redisCache.setWithExpire(RedisConstant.PREFIX_BOOKING_REQUEST + ":" + bookingHistory.getRequestId(), GsonSingleton.getInstance().toJson(bookingHistory), (int) TimeUnit.MINUTES.toSeconds(30));

            sendMessageBookingRequestUpdated(bookingHistory, account);
        } catch (Exception e) {
            LoggerUtil.exception(TAG, e);
            throw ServiceExceptionUtils.handleApplicationException(e.getMessage());
        }
    }

    /**
     * Implement main function to test class {@link BookingDistribution}
     *
     * @throws Exception
     */
    public static void main(String[] vaicalon) throws Exception {

        /**
         * Account of receiver.
         * Start connect to websocket server to log message received.
         */
        String chat_username = "hoangnhat123";
        String chat_password = "hoangnhat123";
        URI uri;
        try {
            uri = new URI(String.format("ws://localhost:8099/chat/%s/%s", chat_username, chat_password));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        Session session = container.connectToServer(WebsocketClientEndpoint.class, uri);
    }
}
