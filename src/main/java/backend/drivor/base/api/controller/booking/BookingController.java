package backend.drivor.base.api.controller.booking;

import backend.drivor.base.api.controller.BaseController;
import backend.drivor.base.domain.document.Account;
import backend.drivor.base.domain.request.ChangePasswordRequest;
import backend.drivor.base.domain.request.NewBookingRequest;
import backend.drivor.base.domain.response.ApiResponse;
import backend.drivor.base.domain.response.BookingHistoryResponse;
import backend.drivor.base.service.inf.AccountService;
import backend.drivor.base.service.inf.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/booking")
public class BookingController extends BaseController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/newBookingRequest")
    public ResponseEntity<?> newBookingRequest(@Valid @RequestBody NewBookingRequest request) {

        Account account = getLoggedAccount();

        BookingHistoryResponse data = bookingService.newBookingRequest(account, request);

        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.name() ,"New booking request successfully!", data));
    }

}
