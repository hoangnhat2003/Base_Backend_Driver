package backend.drivor.base.domain.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class DriverArrivedRequest {

    @NotNull
    @NotEmpty
    private String request_id;

    private Double latitude;
    private Double longitude;
}
