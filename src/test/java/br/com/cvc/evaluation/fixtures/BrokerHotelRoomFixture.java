package br.com.cvc.evaluation.fixtures;

import br.com.cvc.evaluation.broker.dto.BrokerHotelRoom;
import br.com.cvc.evaluation.broker.dto.Price;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class BrokerHotelRoomFixture implements TemplateLoader {
    public static final String VALIDO = "valido";

    @Override
    public void load() {
        Fixture.of(BrokerHotelRoom.class).addTemplate(VALIDO, new Rule(){
            {
                add("roomID", random(Integer.class, range(1, 999)));
                add("categoryName", name());
                add("price", one(Price.class, PriceFixture.VALIDO));
            }
        });
    }
}
