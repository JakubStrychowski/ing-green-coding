package pl.ing.green.atmservice.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.annotation.Generated;

/**
 * Task
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-05-10T23:05:31.627387+02:00[Europe/Warsaw]")
public class Task {

    @JsonProperty("region")
    private int region;


    @JsonProperty("requestType")
    private RequestTypeEnum requestType;

    @JsonProperty("atmId")
    private int atmId;
    
    public Task() {
        
    }
    
    public Task(int region, int atmId, RequestTypeEnum type) {
        this.region = region;
        this.atmId = atmId;
        this.requestType = type;
    }

    public Task region(int region) {
        this.region = region;
        return this;
    }

    /**
     * Get region minimum: 1 maximum: 9999
     *
     * @return region
     */
    @Min(1)
    @Max(9999)
    @Schema(name = "region", example = "10", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public Task requestType(RequestTypeEnum requestType) {
        this.requestType = requestType;
        return this;
    }

    /**
     * Type of request
     *
     * @return requestType
     */
    @Schema(name = "requestType", example = "STANDARD", description = "Type of request", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public RequestTypeEnum getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestTypeEnum requestType) {
        this.requestType = requestType;
    }

    public Task atmId(int atmId) {
        this.atmId = atmId;
        return this;
    }

    /**
     * Get atmId minimum: 1 maximum: 9999
     *
     * @return atmId
     */
    @Min(1)
    @Max(9999)
    @Schema(name = "atmId", example = "500", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public int getAtmId() {
        return atmId;
    }

    public void setAtmId(int atmId) {
        this.atmId = atmId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Task task = (Task) o;
        return Objects.equals(this.region, task.region)
                && Objects.equals(this.requestType, task.requestType)
                && Objects.equals(this.atmId, task.atmId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(region, requestType, atmId);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Task {\n");
        sb.append("    region: ").append(toIndentedString(region)).append("\n");
        sb.append("    requestType: ").append(toIndentedString(requestType)).append("\n");
        sb.append("    atmId: ").append(toIndentedString(atmId)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
