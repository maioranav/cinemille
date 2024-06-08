package org.vm93.cinemille.payload;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateRangeDTO {
	LocalDate dateFrom;
	LocalDate dateTo;
}
