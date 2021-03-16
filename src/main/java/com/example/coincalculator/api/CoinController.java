package com.example.coincalculator.api;

import com.example.coincalculator.model.CurrencyResponse;
import com.example.coincalculator.service.PriceCalculationService;
import com.example.coincalculator.validation.PriceValidationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@RestController
@RequestMapping("/api/v1/coin-api")
public class CoinController {

    private Logger logger = LoggerFactory.getLogger(CoinController.class);

    private PriceCalculationService priceCalculationService;
    private PriceValidationServiceImpl priceValidationService;
    private Executor executor;

    public CoinController(PriceCalculationService priceCalculationService, PriceValidationServiceImpl priceValidationService, Executor executor) {
        this.priceCalculationService = priceCalculationService;
        this.priceValidationService = priceValidationService;
        this.executor = executor;
    }

    @GetMapping(path = "/{amount}/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Async api for getting current calculated price only USD and EUR allowed",
            summary = "Calculated price will change max in every 10 seconds")
    private CompletableFuture<ResponseEntity<CurrencyResponse>> getBtcAmount(@NotNull @PathVariable("amount") BigDecimal amount,
                                                                             @NotNull @PathVariable("type") String type) {
        logger.info("Retrieving amount and currency type {} {} ", amount, type);

        priceValidationService.validateAmountAndType(type, amount);

        CompletableFuture<CurrencyResponse> result = CompletableFuture.supplyAsync(() ->
                priceCalculationService.calculatePriceWithAmount(type, amount), executor);

        return result.thenApply(ResponseEntity::ok);
    }

    @GetMapping(path = "/{type}/coin-type/{coinAmount}/{coinType}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Async api for getting current calculated price only USD and EUR allowed",
            summary = "Calculated price will change max in every 10 seconds")
    private CompletableFuture<ResponseEntity<CurrencyResponse>> getPrice(@NotNull @PathVariable("coinAmount") BigDecimal coinAmount,
                                                                         @NotNull @PathVariable("type") String type,
                                                                         @NotNull @PathVariable("coinType") String coinType) {
        priceValidationService.validateCoinType(type, coinAmount, coinType);

        CompletableFuture<CurrencyResponse> result = CompletableFuture.supplyAsync(() ->
                priceCalculationService.calculatePriceWithCoinAmount(coinType, coinAmount, type), executor);

        return result.thenApply(ResponseEntity::ok);
    }


}
