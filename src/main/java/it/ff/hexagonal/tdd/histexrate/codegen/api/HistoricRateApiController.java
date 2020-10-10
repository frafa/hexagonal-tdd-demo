package it.ff.hexagonal.tdd.histexrate.codegen.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-10-09T22:05:16.885+02:00[Europe/Rome]")
@Controller
@RequestMapping("${openapi.API to manage historical exchange rates.base-path:}")
public class HistoricRateApiController implements HistoricRateApi {

    private final HistoricRateApiDelegate delegate;

    public HistoricRateApiController(@org.springframework.beans.factory.annotation.Autowired(required = false) HistoricRateApiDelegate delegate) {
        this.delegate = Optional.ofNullable(delegate).orElse(new HistoricRateApiDelegate() {
        });
    }

    @Override
    public HistoricRateApiDelegate getDelegate() {
        return delegate;
    }

}
