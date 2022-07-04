package backend.drivor.base.domain.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingLocation {

    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
    private String address;
}
