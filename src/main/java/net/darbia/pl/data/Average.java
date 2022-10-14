// 
// Decompiled by Procyon v0.5.36
// 

package net.darbia.pl.data;

import org.apache.logging.log4j.LogManager;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.io.FileOutputStream;
import java.io.File;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.logging.log4j.Logger;

public class Average implements Runnable
{
    private static final Logger LOGGER;
    
    @Override
    public void run() {
        final Map<String, ArrayList<Integer>> su = CalculateAverages.calculateSetUpAverages();
        final Map<String, ArrayList<Integer>> po = CalculateAverages.calculateProductionAverages();
        final HashMap<String, ArrayList<Integer>> toJSON = new HashMap<String, ArrayList<Integer>>();
        toJSON.putAll(su);
        toJSON.putAll(po);
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            final OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(new File("./ProductionLog/averages.json")), StandardCharsets.UTF_8);
            try {
                out.write(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(toJSON));
                out.close();
            }
            catch (Throwable t) {
                try {
                    out.close();
                }
                catch (Throwable exception) {
                    t.addSuppressed(exception);
                }
                throw t;
            }
        }
        catch (IOException ioe) {
            Average.LOGGER.error(ioe);
        }
    }
    
    static {
        LOGGER = LogManager.getLogger(Average.class);
    }
}
