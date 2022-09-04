package br.com.cvc.evaluation.service.mapper;

import br.com.cvc.evaluation.broker.dto.BrokerHotel;
import br.com.cvc.evaluation.domain.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
                unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HotelMapper {
    Hotel toDomain(final BrokerHotel broker);
}
