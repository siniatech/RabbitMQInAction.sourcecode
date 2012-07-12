package rabbitmqinaction.sourcecode;

import java.util.HashMap;
import java.util.Map;

public class GenericConfiguration {

    public static final boolean ACTIVE = false;
    public static final boolean PASSIVE = false;
    public static final boolean DURABLE = true;
    public static final boolean NON_DURABLE = false;
    public static final boolean AUTO_DELETE = true;
    public static final boolean NON_AUTO_DELETE = false;
    public static final String DIRECT_EXCHANGE_TYPE = "direct";
    public static final String HOST = "localhost";
    public static final String PLAIN_CONTENT_TYPE = "text/plain";
    public static final Map<String, Object> EMPTY_MAP = new HashMap<String, Object>();

}
