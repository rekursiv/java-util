package util.ektorp;

import java.util.TimeZone;

import org.ektorp.CouchDbConnector;
import org.ektorp.impl.ObjectMapperFactory;
import org.ektorp.impl.StdObjectMapperFactory;
import org.ektorp.impl.jackson.EktorpJacksonModule;
import org.ektorp.util.Assert;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.datatype.joda.JodaModule;

public class JodaObjectMapperFactory implements ObjectMapperFactory {

	private ObjectMapper instance;
	private boolean writeDatesAsTimestamps = false;

	@Override
	public synchronized ObjectMapper createObjectMapper() {
		System.out.println("*** COM");
		ObjectMapper result = instance;
		if (result == null) {
			System.out.println("*** NEW");
			result = new ObjectMapper();
			applyDefaultConfiguration(result);
			instance = result;
		}
		return result;
	}

	@Override
	public ObjectMapper createObjectMapper(CouchDbConnector connector) {
		System.out.println("*** COM:CDC");
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JodaModule());
		applyDefaultConfiguration(objectMapper);
		objectMapper.registerModule(new EktorpJacksonModule(connector, objectMapper));
		return objectMapper;
	}

	public synchronized void setObjectMapper(ObjectMapper om) {
		Assert.notNull(om, "ObjectMapper may not be null");
		this.instance = om;
	}

	public void setWriteDatesAsTimestamps(boolean b) {
		this.writeDatesAsTimestamps = b;
	}

	private void applyDefaultConfiguration(ObjectMapper om) {
		om.registerModule(new JodaModule());
//		om.setTimeZone(TimeZone.getTimeZone("GMT+1:00"));
		om.setDateFormat(new ISO8601DateFormat());
		om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, this.writeDatesAsTimestamps);
		om.getSerializationConfig().withSerializationInclusion(JsonInclude.Include.NON_NULL);
	}

}



/*
extends StdObjectMapperFactory {

	@Override
    public ObjectMapper createObjectMapper(CouchDbConnector connector) {
        ObjectMapper mapper = super.createObjectMapper(connector);
        mapper.registerModule(new JodaModule());
        return mapper;
    }
}

*/