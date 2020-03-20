package com.rtbytez.server.packethandler;

import com.corundumstudio.socketio.protocol.AckArgs;
import com.corundumstudio.socketio.protocol.Event;
import com.corundumstudio.socketio.protocol.JacksonJsonSupport;
import com.corundumstudio.socketio.protocol.JsonSupport;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.netty.util.internal.PlatformDependent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * This whole class is a hack to the netty io framework.
 * Do not modify unless you want to waste hours upon hours trying to optimize this to how RTBytez uses socket.io
 * It all started with modifying the EventInterceptor and making a whole lib in /repo to do so.
 * <p>
 * Please take this warning seriously and do not try to modify or optimize this class. Thank you.
 */
public class RTBytezJsonSupport extends JacksonJsonSupport implements JsonSupport {

    NewEventDeserializer newEventDeserializer;

    public RTBytezJsonSupport() {
        super();
    }

    @Override
    public void addEventMapping(String namespaceName, String eventName, Class<?>... eventClass) {
        newEventDeserializer.eventMapping.put(new EventKey(namespaceName, eventName), Arrays.asList(eventClass));
    }

    @Override
    public void removeEventMapping(String namespaceName, String eventName) {
        newEventDeserializer.eventMapping.remove(new EventKey(namespaceName, eventName));
    }

    protected void init(ObjectMapper objectMapper) {
        newEventDeserializer = new NewEventDeserializer();
        SimpleModule module = new SimpleModule();
        module.setSerializerModifier(modifier);
        module.addDeserializer(Event.class, newEventDeserializer);
        module.addDeserializer(AckArgs.class, ackArgsDeserializer);
        objectMapper.registerModule(module);

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN, true);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    public class NewEventDeserializer extends StdDeserializer<Event> {

        private static final long serialVersionUID = 8178797221017768689L;

        final Map<EventKey, List<Class<?>>> eventMapping = PlatformDependent.newConcurrentHashMap();


        protected NewEventDeserializer() {
            super(Event.class);
        }

        @Override
        public Event deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            ObjectMapper mapper = (ObjectMapper) jp.getCodec();
            String eventName = jp.nextTextValue();

            EventKey ek = new EventKey(namespaceClass.get(), eventName);

            List<Object> eventArgs = new ArrayList<Object>();
            Event event = new Event(eventName, eventArgs);
            List<Class<?>> eventClasses;
            if (!eventMapping.containsKey(ek)) {
                eventClasses = new ArrayList<>();
                eventClasses.add(MessageObject.class);
            } else {
                eventClasses = eventMapping.get(ek);
            }

            int i = 0;
            while (true) {
                JsonToken token = jp.nextToken();
                if (token == JsonToken.END_ARRAY) {
                    break;
                }
                if (i > eventClasses.size() - 1) {
                    log.debug("Event {} has more args than declared in handler: {}", eventName, null);
                    break;
                }
                Class<?> eventClass = eventClasses.get(i);
                Object arg = mapper.readValue(jp, eventClass);
                eventArgs.add(arg);
            }
            return event;
        }

    }
}