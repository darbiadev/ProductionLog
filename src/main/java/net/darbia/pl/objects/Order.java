package net.darbia.pl.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import net.darbia.pl.serialization.EmployeeSerializer;
import net.darbia.pl.serialization.JsonJodaDateTimeSerializer;
import net.darbia.pl.serialization.ListSerializer;
import net.darbia.pl.serialization.MapSerializer;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonRootName("order")
public class Order {

  private DateTime created;
  private int machine;
  private int orderNumber;
  private int designNumber;
  private String location;
  private int numberOfColors;
  private Employee tender;
  private Employee loader;
  private Employee puller;
  private Employee catcher;
  private Map<DateTime, String> comments;
  private boolean onHold;
  private boolean alreadyApproved;
  private DateTime DSetUp;
  private DateTime DApproved;
  private DateTime DComplete;
  private int quantity;
  private int avgU;
  private DateTime holdStart;
  private DateTime holdEnd;
  private int SUohMinutes;
  private int PohMinutes;
  private int totalHoldMinutes;
  private int mpc;
  private ArrayList<String> holdReasonsSU;
  private ArrayList<String> holdReasonsP;
  private ArrayList<Interval> holdTimesSU;
  private ArrayList<Interval> holdTimesP;
  private Map<String, Integer> SetUpMinutesRemoved;
  private Map<String, Integer> ProductionMinutesRemoved;
  private ArrayList<HashMap<String, HashMap<Object, Object>>> editedValues;
  private int suMinutes;
  private int pMinutes;

  public Order(final int machine, final int orderNumber, final int designNumber,
      final String location, final int numberOfColors, final Employee tender, final Employee loader,
      final Employee puller, final Employee catcher) {
    this.created = new DateTime();
    this.comments = new HashMap<>();
    this.onHold = false;
    this.alreadyApproved = false;
    this.SUohMinutes = 0;
    this.PohMinutes = 0;
    this.totalHoldMinutes = 0;
    this.mpc = 0;
    this.holdReasonsSU = new ArrayList<>();
    this.holdReasonsP = new ArrayList<>();
    this.holdTimesSU = new ArrayList<>();
    this.holdTimesP = new ArrayList<>();
    this.SetUpMinutesRemoved = new HashMap<>();
    this.ProductionMinutesRemoved = new HashMap<>();
    this.editedValues = new ArrayList<>();
    this.suMinutes = 0;
    this.pMinutes = 0;
    this.machine = machine;
    this.orderNumber = orderNumber;
    this.designNumber = designNumber;
    this.location = location;
    this.numberOfColors = numberOfColors;
    this.tender = tender;
    this.loader = loader;
    this.puller = puller;
    this.catcher = catcher;
  }

  @JsonProperty("total_set_up_minutes")
  public int getSuMinutes() {
    return this.suMinutes;
  }

  public void setSuMinutes(final int suMinutes) {
    this.suMinutes = suMinutes;
  }

  @JsonProperty("total_production_minutes")
  public int getpMinutes() {
    return this.pMinutes;
  }

  public void setpMinutes(final int pMinutes) {
    this.pMinutes = pMinutes;
  }

  @JsonProperty("edits")
  @JsonSerialize(using = ListSerializer.class)
  public List<HashMap<String, HashMap<Object, Object>>> getEditedValues() {
    return this.editedValues;
  }

  public void setEditedValues(
      final ArrayList<HashMap<String, HashMap<Object, Object>>> editedValues) {
    this.editedValues = editedValues;
  }

  public void addEditedValues(final HashMap<String, HashMap<Object, Object>> map) {
    this.editedValues.add(map);
  }

  @JsonProperty("production_minutes_removed")
  @JsonSerialize(using = MapSerializer.class)
  public Map<String, Integer> getProductionMinutesRemoved() {
    return this.ProductionMinutesRemoved;
  }

  public void setProductionMinutesRemoved(final Map<String, Integer> productionMinutesRemoved) {
    this.ProductionMinutesRemoved = productionMinutesRemoved;
  }

  public void addProductionMinutesRemoved(final String reason, final int minutes) {
    this.ProductionMinutesRemoved.put(reason, minutes);
  }

  @JsonProperty("set_up_minutes_removed")
  @JsonSerialize(using = MapSerializer.class)
  public Map<String, Integer> getSetUpMinutesRemoved() {
    return this.SetUpMinutesRemoved;
  }

  public void setSetUpMinutesRemoved(final Map<String, Integer> setUpMinutesRemoved) {
    this.SetUpMinutesRemoved = setUpMinutesRemoved;
  }

  public void addSetUpMinutesRemoved(final String reason, final int minutes) {
    this.SetUpMinutesRemoved.put(reason, minutes);
  }

  public boolean isAlreadyApproved() {
    return this.alreadyApproved;
  }

  public void setAlreadyApproved(final boolean alreadyApproved) {
    this.alreadyApproved = alreadyApproved;
  }

  @JsonProperty("created")
  @JsonSerialize(using = JsonJodaDateTimeSerializer.class)
  public DateTime getCreated() {
    return this.created;
  }

  public void setCreated(final DateTime created) {
    this.created = created;
  }

  @JsonProperty("machine_number")
  public int getMachine() {
    return this.machine;
  }

  public void setMachine(final int machine) {
    this.machine = machine;
  }

  @JsonProperty("order_number")
  public int getOrderNumber() {
    return this.orderNumber;
  }

  public void setOrderNumber(final int orderNumber) {
    this.orderNumber = orderNumber;
  }

  @JsonProperty("design_number")
  public int getDesignNumber() {
    return this.designNumber;
  }

  public void setDesignNumber(final int designNumber) {
    this.designNumber = designNumber;
  }

  @JsonProperty("location")
  public String getLocation() {
    return this.location;
  }

  public void setLocation(final String location) {
    this.location = location;
  }

  @JsonProperty("number_of_colors")
  public int getNumberOfColors() {
    return this.numberOfColors;
  }

  public void setNumberOfColors(final int numberOfColors) {
    this.numberOfColors = numberOfColors;
  }

  @JsonProperty("tender_id")
  @JsonSerialize(using = EmployeeSerializer.class)
  public Employee getTender() {
    return this.tender;
  }

  public void setTender(final Employee tender) {
    this.tender = tender;
  }

  @JsonProperty("loader_id")
  @JsonSerialize(using = EmployeeSerializer.class)
  public Employee getLoader() {
    return this.loader;
  }

  public void setLoader(final Employee loader) {
    this.loader = loader;
  }

  @JsonProperty("puller_id")
  @JsonSerialize(using = EmployeeSerializer.class)
  public Employee getPuller() {
    return this.puller;
  }

  public void setPuller(final Employee puller) {
    this.puller = puller;
  }

  @JsonProperty("catcher_id")
  @JsonSerialize(using = EmployeeSerializer.class)
  public Employee getCatcher() {
    return this.catcher;
  }

  public void setCatcher(final Employee catcher) {
    this.catcher = catcher;
  }

  @JsonProperty("comments")
  @JsonSerialize(using = MapSerializer.class)
  public Map<DateTime, String> getComments() {
    return this.comments;
  }

  public void setComments(final Map<DateTime, String> comments) {
    this.comments = comments;
  }

  public void addComment(final DateTime dateTime, final String comment) {
    this.comments.put(dateTime, comment);
  }

  public boolean isOnHold() {
    return this.onHold;
  }

  public void setOnHold(final boolean onHold) {
    this.onHold = onHold;
  }

  @JsonProperty("set_up")
  @JsonSerialize(using = JsonJodaDateTimeSerializer.class)
  public DateTime getDSetUp() {
    return this.DSetUp;
  }

  public void setDSetUp(final DateTime dSetUp) {
    this.DSetUp = dSetUp;
  }

  @JsonProperty("approved")
  @JsonSerialize(using = JsonJodaDateTimeSerializer.class)
  public DateTime getDApproved() {
    return this.DApproved;
  }

  public void setDApproved(final DateTime dApproved) {
    this.DApproved = dApproved;
  }

  @JsonProperty("complete")
  @JsonSerialize(using = JsonJodaDateTimeSerializer.class)
  public DateTime getDComplete() {
    return this.DComplete;
  }

  public void setDComplete(final DateTime dComplete) {
    this.DComplete = dComplete;
  }

  @JsonProperty("quantity")
  public int getQuantity() {
    return this.quantity;
  }

  public void setQuantity(final int quantity) {
    this.quantity = quantity;
  }

  @JsonProperty("average_units_per_hour")
  public int getAvgU() {
    return this.avgU;
  }

  public void setAvgU(final int avgU) {
    this.avgU = avgU;
  }

  public DateTime getHoldStart() {
    return this.holdStart;
  }

  public void setHoldStart(final DateTime holdStart) {
    this.holdStart = holdStart;
  }

  public DateTime getHoldEnd() {
    return this.holdEnd;
  }

  public void setHoldEnd(final DateTime holdEnd) {
    this.holdEnd = holdEnd;
  }

  @JsonProperty("set_up_hold_minutes")
  public int getSUohMinutes() {
    return this.SUohMinutes;
  }

  public void setSUohMinutes(final int sUohMinutes) {
    this.SUohMinutes = sUohMinutes;
  }

  @JsonProperty("production_hold_minutes")
  public int getPohMinutes() {
    return this.PohMinutes;
  }

  public void setPohMinutes(final int pohMinutes) {
    this.PohMinutes = pohMinutes;
  }

  @JsonProperty("total_hold_minutes")
  public int getTotalHoldMinutes() {
    return this.totalHoldMinutes;
  }

  public void setTotalHoldMinutes(final int totalHoldMinutes) {
    this.totalHoldMinutes = totalHoldMinutes;
  }

  @JsonProperty("average_minutes_per_color")
  public int getMpc() {
    return this.mpc;
  }

  public void setMpc(final int mpc) {
    this.mpc = mpc;
  }

  public void addHoldTimeSU(final Interval interval) {
    this.holdTimesSU.add(interval);
  }

  public void addHoldTimeP(final Interval interval) {
    this.holdTimesP.add(interval);
  }

  @JsonProperty("set_up_hold_times")
  @JsonSerialize(using = ListSerializer.class)
  public List<Interval> getHoldTimesSU() {
    return this.holdTimesSU;
  }

  public void setHoldTimesSU(final ArrayList<Interval> holdTimesSU) {
    this.holdTimesSU = holdTimesSU;
  }

  @JsonProperty("production_hold_times")
  @JsonSerialize(using = ListSerializer.class)
  public List<Interval> getHoldTimesP() {
    return this.holdTimesP;
  }

  public void setHoldTimesP(final ArrayList<Interval> holdTimesP) {
    this.holdTimesP = holdTimesP;
  }

  @JsonProperty("set_up_hold_reasons")
  @JsonSerialize(using = ListSerializer.class)
  public List<String> getHoldReasonsSU() {
    return this.holdReasonsSU;
  }

  public void setHoldReasonsSU(final ArrayList<String> holdReasonsSU) {
    this.holdReasonsSU = holdReasonsSU;
  }

  public void addHoldReasonSU(final String reason) {
    this.holdReasonsSU.add(reason);
  }

  @JsonProperty("production_hold_reasons")
  @JsonSerialize(using = ListSerializer.class)
  public List<String> getHoldReasonsP() {
    return this.holdReasonsP;
  }

  public void setHoldReasonsP(final ArrayList<String> holdReasonsP) {
    this.holdReasonsP = holdReasonsP;
  }

  public void addHoldReasonP(final String reason) {
    this.holdReasonsP.add(reason);
  }

  @JsonProperty("total_time")
  public String getTotalTime() {
    final PeriodFormatter formatter = new PeriodFormatterBuilder().printZeroAlways()
        .minimumPrintedDigits(2).appendHours().appendSeparator(":").appendMinutes().toFormatter();
    Duration total_time;
    if (this.alreadyApproved) {
      total_time = new Duration(this.getDApproved(), this.getDComplete());
    } else {
      total_time = new Duration(this.getDSetUp(), this.getDComplete());
    }
    for (final Interval i : this.getHoldTimesSU()) {
      total_time = total_time.minus(i.toDuration());
    }
    for (final Interval i : this.getHoldTimesP()) {
      total_time = total_time.minus(i.toDuration());
    }
    return formatter.print(total_time.toPeriod());
  }

  @JsonProperty("completed")
  public boolean isCompleted() {
    return true;
  }

  @Override
  public String toString() {
    return "Order{" + "alreadyApproved=" + this.alreadyApproved
        + ", avgU=" + this.avgU
        + ", catcher=" + this.catcher
        + ", comments=" + this.comments
        + ", created=" + this.created
        + ", DApproved=" + this.DApproved
        + ", DComplete=" + this.DComplete
        + ", designNumber=" + this.designNumber
        + ", DSetUp=" + this.DSetUp
        + ", holdEnd=" + this.holdEnd
        + ", holdReasonsP=" + this.holdReasonsP
        + ", holdReasonsSU=" + this.holdReasonsSU
        + ", holdStart=" + this.holdStart
        + ", holdTimesP=" + this.holdTimesP
        + ", holdTimesSU=" + this.holdTimesSU
        + ", loader=" + this.loader
        + ", location='" + this.location + '\''
        + ", machine=" + this.machine
        + ", mpc=" + this.mpc
        + ", numberOfColors=" + this.numberOfColors
        + ", onHold=" + this.onHold
        + ", orderNumber=" + this.orderNumber
        + ", PohMinutes=" + this.PohMinutes
        + ", ProductionMinutesRemoved=" + this.ProductionMinutesRemoved
        + ", puller=" + this.puller
        + ", quantity=" + this.quantity
        + ", SetUpMinutesRemoved=" + this.SetUpMinutesRemoved
        + ", SUohMinutes=" + this.SUohMinutes
        + ", tender=" + this.tender
        + ", totalHoldMinutes=" + this.totalHoldMinutes
        + '}';
  }
}
