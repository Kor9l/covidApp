package com.covid.covidall.mapper;

import com.covid.covidall.dto.CountryDTO;
import com.covid.covidall.dto.CountryStatisticResponseDTO;
import com.covid.covidall.jpa.model.Country;
import com.covid.covidall.jpa.model.CovidStatus;
import com.covid.covidall.jpa.model.Statistic;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ResponseMapper {

  ResponseMapper INSTANCE = Mappers.getMapper(ResponseMapper.class);

  @Mapping(target = "cases", source = "cases")
  @Mapping(target = "countryCode", source = "countryCode")
  @Mapping(target = "status", expression = "java(convertToCovidStatus(responseDTO.getStatus()))")
  @Mapping(target = "date", expression = "java(convertToLocalDateTimeViaInstant(responseDTO.getDate()))")
  Statistic mapFromDTO(CountryStatisticResponseDTO responseDTO);

  @Mapping(target = "name", source = "country")
  @Mapping(target = "countryCode", source = "iSO2")
  Country mapFromDTO(CountryDTO responseDTO);

  default LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
    return dateToConvert.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime();
  }

  default CovidStatus convertToCovidStatus(String stringToConvert) {
    return CovidStatus.valueOf(StringUtils.toRootUpperCase(stringToConvert));
  }
}
