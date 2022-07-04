package backend.drivor.base.api.controller.booking;

import backend.drivor.base.api.controller.BaseController;
import backend.drivor.base.domain.document.Account;
import backend.drivor.base.domain.request.ChangePasswordRequest;
import backend.drivor.base.domain.request.NewBookingRequest;
import backend.drivor.base.domain.response.ApiResponse;
import backend.drivor.base.domain.response.BookingHistoryResponse;
import backend.drivor.base.service.inf.AccountService;
import backend.drivor.base.service.inf.BookingService;
import backend.drivor.base.service.searchbooking.model.BookingSearchResponse;
import backend.drivor.base.service.searchbooking.service.BookingSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/booking")
public class BookingController extends BaseController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingSearchService bookingSearchService;

    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestParam(name = "passengerName", required = false) Optional<String> passengerName,
                                    @RequestParam(name = "statuses", required = false) Optional<String[]> statuses,
                                    @RequestParam(name = "billingStatus", required = false) Optional<String[]> billingStatus,
                                    @RequestParam(name = "amount", required = false) Optional<String[]> amount,
                                    @RequestParam(name = "dateType", required = false) Optional<String> dateType,
                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer _pageNo,
                                    @RequestParam(name = "pageSize", defaultValue = "20") Integer _pageSize) {

        BookingSearchResponse response = bookingSearchService.search(passengerName, statuses, billingStatus, amount, dateType, _pageNo, _pageSize);

        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.name() ,null, response));
    }

    @PostMapping("/newBookingRequest")
    public ResponseEntity<?> newBookingRequest(@Valid @RequestBody NewBookingRequest request) {

        Account account = getLoggedAccount();

        BookingHistoryResponse data = bookingService.newBookingRequest(account, request);

        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.name() ,"New booking request successfully!", data));
    }

}
