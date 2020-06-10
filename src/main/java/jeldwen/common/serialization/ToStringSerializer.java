package jeldwen.common.serialization;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class ToStringSerializer extends StdSerializer<Object> {
	
	/* Serialization */
	private static final long serialVersionUID = 1L;

	/* Constructor */
	protected ToStringSerializer() {
		this(null);
	}

	/* Constructor */
	protected ToStringSerializer(Class<Object> clazz) {
		super(clazz);
	}
	
	@Override
	public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeString(value.toString());
	}
	
}