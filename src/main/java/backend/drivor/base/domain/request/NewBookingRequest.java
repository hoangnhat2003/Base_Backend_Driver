package backend.drivor.base.domain.request;

import backend.drivor.base.domain.model.BookingLocation;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class NewBookingRequest {

    @NotNull
    @NotEmpty
    private String request_id;
    @NotNull
    private BookingLocation from;
    @NotNull
    private BookingLocation to;
    private Double distance;
    private String distance_unit;
    @NotEmpty
    @NotNull
    private String vehicle_id;
    private String pay_type;
    private Integer hours;
    private List<Integer> preview_hours;
    private String note;
}
