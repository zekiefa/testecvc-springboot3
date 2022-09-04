package br.com.cvc.evaluation.fixtures;

import java.math.BigDecimal;

import br.com.cvc.evaluation.broker.dto.Price;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import org.jeasy.random.EasyRandom;

public class PriceFixture implements TemplateLoader {
    public static final String VALIDO = "valido";

    @Override
    public void load() {
        final var easyRandom = new EasyRandom();
        Fixture.of(Price.class).addTemplate(VALIDO, new Rule(){
            {
                add("adult", easyRandom.nextObject(BigDecimal.class));
                add("child", easyRandom.nextObject(BigDecimal.class));
            }
        });
    }
}
