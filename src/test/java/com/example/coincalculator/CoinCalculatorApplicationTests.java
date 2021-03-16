package com.example.coincalculator;

import com.example.coincalculator.api.CoinController;
import com.example.coincalculator.config.CacheClient;
import com.example.coincalculator.exception.AmountNotSufficientOrExceedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoinCalculatorApplication.class)
@AutoConfigureMockMvc
public class CoinCalculatorApplicationTests {

    public static String BTC_AMOUNT = "/api/v1/coin-api/%s/%s";
    public static String REAL_PRICE = "/api/v1/coin-api/%s/coin-type/%s/%s";

    @Autowired
    CacheClient cacheClient;

    @Autowired
    CoinController coinController;

    @Autowired
    private MockMvc mockMvc;

    private static final ObjectMapper mapper = new ObjectMapper();

    ResultMatcher statusOK = MockMvcResultMatchers.status().isOk();


    private String realAmount = "200";
    private String currencyType = "USD";
    private String coinAmount = "0.00234";
    private String coinType = "BTC";

    /**
     * @return Calculated btc amount for given real price
     */
    @Test
    public void calculateRealPriceWithBtcAmount_andSuccess() throws Exception {

        final MockHttpServletRequestBuilder getRequest = get(String.format(BTC_AMOUNT,realAmount, currencyType ))
                .contentType(APPLICATION_JSON);

        final MvcResult mvcResult = mockMvc
                .perform(getRequest)
                .andExpect(statusOK)
                .andReturn();


        mockMvc
                .perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("USD"));

    }

    /**
     * @return Calculated real price for given btc amount
     */
    @Test
    public void calculateBtcAmountWithRealPrice_andSuccess() throws Exception {

        final MockHttpServletRequestBuilder getRequest = get(String.format(REAL_PRICE,currencyType, coinAmount, coinType))
                .contentType(APPLICATION_JSON);

        final MvcResult mvcResult = mockMvc
                .perform(getRequest)
                .andExpect(statusOK)
                .andReturn();

        mockMvc
                .perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("USD"));

    }

    @Test( expected = Exception.class)
    public void whenAmountIsNotProvidedWell_andError() throws Exception {

        final MockHttpServletRequestBuilder getRequest = get(String.format(BTC_AMOUNT,"21", currencyType ))
                .contentType(APPLICATION_JSON);

        final MvcResult mvcResult = mockMvc
                .perform(getRequest)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();


        mockMvc
                .perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("USD"));

    }



    <T> T doGetAndReturn(String uri, ResultMatcher status, Class<T> type) throws Exception {
        MvcResult result = doGet(uri)
                .andExpect(status)
                .andReturn();
        return fromJsonToObject(result.getResponse().getContentAsString(), type);
    }

    ResultActions doGet(String uri) throws Exception {
        return mockMvc.perform(get(uri).contentType(APPLICATION_JSON));
    }

    private <T> T fromJsonToObject(String data, Class<T> type) throws IOException {
        return (T) mapper.readValue(data, type);
    }

}
