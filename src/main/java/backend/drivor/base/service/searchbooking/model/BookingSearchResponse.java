package backend.drivor.base.service.searchbooking.model;

import backend.drivor.base.domain.model.BookingLocation;
import backend.drivor.base.domain.model.VehicleInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingSearchResponse {

    private List<BookingSearchModel> data;

    private int totalElements;

    private int totalPages;

}
