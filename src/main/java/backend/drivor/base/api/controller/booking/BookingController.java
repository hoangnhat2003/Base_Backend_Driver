package backend.drivor.base.api.controller.booking;

import backend.drivor.base.api.controller.BaseController;
import backend.drivor.base.domain.document.Account;
import backend.drivor.base.domain.document.BookingHistory;
import backend.drivor.base.domain.repository.BookingHistoryRepository;
import backend.drivor.base.domain.request.AcceptBookingRequest;
import backend.drivor.base.domain.request.DriverArrivedRequest;
import backend.drivor.base.domain.request.NewBookingRequest;
import backend.drivor.base.domain.response.ApiResponse;
import backend.drivor.base.domain.response.BookingHistoryResponse;
import backend.drivor.base.domain.response.GeneralSubmitResponse;
import backend.drivor.base.service.distribution.BookingDistribution;
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
    private BookingDistribution bookingDistribution;

    @Autowired
    private BookingHistoryRepository bookingHistoryRepository;
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

        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.name(), null, response));
    }

    @PostMapping("/new")
    public ResponseEntity<?> newBookingRequest(@Valid @RequestBody NewBookingRequest request) {

        Account account = getLoggedAccount();

        BookingHistoryResponse data = bookingService.newBookingRequest(account, request);

        ApiResponse<BookingHistoryResponse> response = new ApiResponse<>();
        response.setCode(HttpStatus.OK.name());
        response.setMessage("New booking request successfully!");
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/accept")
    public ResponseEntity<?> acceptBookingRequest(@Valid @RequestBody AcceptBookingRequest request) {

        Account account = getLoggedAccount();

        GeneralSubmitResponse data = bookingService.acceptBookingRequest(account, request);

        ApiResponse<GeneralSubmitResponse> response = new ApiResponse<>();
        response.setCode(HttpStatus.OK.name());
        response.setMessage("Arrived booking request successfully!");
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/arrived")
    public ResponseEntity<?> arrivedBookingRequest(@Valid @RequestBody DriverArrivedRequest request) {

        Account account = getLoggedAccount();
        GeneralSubmitResponse data = bookingService.arrivedBookingRequest(account, request);

        ApiResponse<GeneralSubmitResponse> response = new ApiResponse<>();
        response.setCode(HttpStatus.OK.name());
        response.setMessage("Arrived booking request successfully!");
        response.setData(data);

        return ResponseEntity.ok(response);
    }

}
