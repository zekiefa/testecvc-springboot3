package br.com.cvc.evaluation.fixtures;

import java.util.Collections;

import br.com.cvc.evaluation.broker.dto.BrokerHotel;
import br.com.cvc.evaluation.broker.dto.BrokerHotelRoom;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class BrokerHotelFixture implements TemplateLoader {
    public static final String VALID = "valid";
    public static final String VALID_WITHOUT_ROOM = "valid-without-room";
    public static final String VALID_WITH_EMPTY_ROOM_LIST = "valid-with-empty-room-list";

    @Override
    public void load() {
        Fixture.of(BrokerHotel.class).addTemplate(VALID, new Rule(){
            {
                add("id", random(Integer.class, range(1, 999)));
                add("name", name());
                add("cityCode", random(Integer.class, range(1000, 1999)));
                add("cityName", name());
                add("rooms", has(3).of(BrokerHotelRoom.class, BrokerHotelRoomFixture.VALIDO));
            }
        });

        Fixture.of(BrokerHotel.class).addTemplate(VALID_WITHOUT_ROOM)
                        .inherits(VALID, new Rule(){
            {
                add("rooms", null);
            }
        });

        Fixture.of(BrokerHotel.class).addTemplate(VALID_WITH_EMPTY_ROOM_LIST)
                        .inherits(VALID, new Rule(){
                            {
                                add("rooms", Collections.emptyList());
                            }
                        });
    }
}
