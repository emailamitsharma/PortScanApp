/**
 * 
 */
package com.aks.portScanApp.util;

import java.io.IOException;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * @author Amit.Sharma
 *
 */
@SuppressWarnings("serial")
public class CustomJsonDateTimeSerializer extends StdSerializer<LocalDateTime>{

	protected CustomJsonDateTimeSerializer(Class<LocalDateTime> t) {
		super(t);
	}
	
	public CustomJsonDateTimeSerializer(){
		this(null);
	}

	@Override
	public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
			gen.writeString(String.valueOf(value.getYear()) + PortScanAppConstants.HYPHEN_STRING 
							+ String.valueOf(value.getMonthValue())+ PortScanAppConstants.HYPHEN_STRING
							+ String.valueOf(value.getDayOfMonth())
							+ PortScanAppConstants.SPACE
							+ String.valueOf(value.getHour()) + PortScanAppConstants.COLON_STRING
							+ String.valueOf(value.getMinute())+ PortScanAppConstants.COLON_STRING
							+ String.valueOf(value.getSecond()));			
	}

}
