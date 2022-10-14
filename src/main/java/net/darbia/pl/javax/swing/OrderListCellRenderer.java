// 
// Decompiled by Procyon v0.5.36
// 

package net.darbia.pl.javax.swing;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.ReadableInstant;
import net.darbia.pl.objects.Order;
import java.awt.Component;
import javax.swing.JList;
import org.joda.time.format.DateTimeFormatter;
import javax.swing.DefaultListCellRenderer;

public class OrderListCellRenderer extends DefaultListCellRenderer
{
    private static final DateTimeFormatter DTF;
    
    @Override
    public final Component getListCellRendererComponent(final JList<?> list, Object value, final int index, final boolean isSelected, final boolean cellHasFocus) {
        if (value instanceof Order) {
            if (((Order)value).isOnHold()) {
                value = invokedynamic(makeConcatWithConstants:(IILjava/lang/String;)Ljava/lang/String;, ((Order)value).getMachine(), ((Order)value).getOrderNumber(), OrderListCellRenderer.DTF.print(((Order)value).getHoldStart()));
            }
            else {
                value = invokedynamic(makeConcatWithConstants:(II)Ljava/lang/String;, ((Order)value).getMachine(), ((Order)value).getOrderNumber());
            }
        }
        else {
            value = "Orders:";
        }
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        return this;
    }
    
    static {
        DTF = DateTimeFormat.forPattern("hh:mm a");
    }
}
