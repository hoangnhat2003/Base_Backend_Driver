package backend.drivor.base.domain.response;

import lombok.Data;

@Data
public class GeneralSubmitResponse {

    private boolean success;

    private long system_time;

    public GeneralSubmitResponse(boolean success) {
        this.success = success;
        this.system_time = System.currentTimeMillis();
    }

}
