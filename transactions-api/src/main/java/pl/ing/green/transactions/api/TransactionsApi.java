/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (6.4.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package pl.ing.green.transactions.api;

import pl.ing.green.transactions.model.Account;
import java.util.List;
import pl.ing.green.transactions.model.Transaction;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-05-06T15:43:02.480471400+02:00[Europe/Warsaw]")
@Validated
@Tag(name = "transactions", description = "the transactions API")
public interface TransactionsApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /transactions/report
     * Execute report
     *
     * @param transaction  (required)
     * @return Successful operation (status code 200)
     */
    @Operation(
        operationId = "report",
        description = "Execute report",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Account.class)))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/transactions/report",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    default ResponseEntity<List<Account>> report(
        @Parameter(name = "Transaction", description = "", required = true) @Valid@Size(max = 100000)  @RequestBody List<Transaction> transaction
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"debitCount\" : 2, \"balance\" : 0.8008282, \"creditCount\" : 2, \"account\" : \"3.2309111922661937E+25\" }, { \"debitCount\" : 2, \"balance\" : 0.8008282, \"creditCount\" : 2, \"account\" : \"3.2309111922661937E+25\" } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
