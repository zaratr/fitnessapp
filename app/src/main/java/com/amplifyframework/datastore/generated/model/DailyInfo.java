package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.BelongsTo;
import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the DailyInfo type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "DailyInfos", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
@Index(name = "byUser", fields = {"userID","calendarDate"})
public final class DailyInfo implements Model {
  public static final QueryField ID = field("DailyInfo", "id");
  public static final QueryField WEIGHT = field("DailyInfo", "weight");
  public static final QueryField USER = field("DailyInfo", "userID");
  public static final QueryField BMI = field("DailyInfo", "bmi");
  public static final QueryField CURRENT_CALORIE = field("DailyInfo", "currentCalorie");
  public static final QueryField DAY = field("DailyInfo", "day");
  public static final QueryField CALENDAR_DATE = field("DailyInfo", "calendarDate");
  public static final QueryField DATE_CREATED = field("DailyInfo", "dateCreated");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="Int") Integer weight;
  private final @ModelField(targetType="User") @BelongsTo(targetName = "userID", type = User.class) User User;
  private final @ModelField(targetType="Int") Integer bmi;
  private final @ModelField(targetType="Int") Integer currentCalorie;
  private final @ModelField(targetType="Int") Integer day;
  private final @ModelField(targetType="String") String calendarDate;
  private final @ModelField(targetType="AWSDateTime") Temporal.DateTime dateCreated;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public Integer getWeight() {
      return weight;
  }
  
  public User getUser() {
      return User;
  }
  
  public Integer getBmi() {
      return bmi;
  }
  
  public Integer getCurrentCalorie() {
      return currentCalorie;
  }
  
  public Integer getDay() {
      return day;
  }
  
  public String getCalendarDate() {
      return calendarDate;
  }
  
  public Temporal.DateTime getDateCreated() {
      return dateCreated;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private DailyInfo(String id, Integer weight, User User, Integer bmi, Integer currentCalorie, Integer day, String calendarDate, Temporal.DateTime dateCreated) {
    this.id = id;
    this.weight = weight;
    this.User = User;
    this.bmi = bmi;
    this.currentCalorie = currentCalorie;
    this.day = day;
    this.calendarDate = calendarDate;
    this.dateCreated = dateCreated;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      DailyInfo dailyInfo = (DailyInfo) obj;
      return ObjectsCompat.equals(getId(), dailyInfo.getId()) &&
              ObjectsCompat.equals(getWeight(), dailyInfo.getWeight()) &&
              ObjectsCompat.equals(getUser(), dailyInfo.getUser()) &&
              ObjectsCompat.equals(getBmi(), dailyInfo.getBmi()) &&
              ObjectsCompat.equals(getCurrentCalorie(), dailyInfo.getCurrentCalorie()) &&
              ObjectsCompat.equals(getDay(), dailyInfo.getDay()) &&
              ObjectsCompat.equals(getCalendarDate(), dailyInfo.getCalendarDate()) &&
              ObjectsCompat.equals(getDateCreated(), dailyInfo.getDateCreated()) &&
              ObjectsCompat.equals(getCreatedAt(), dailyInfo.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), dailyInfo.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getWeight())
      .append(getUser())
      .append(getBmi())
      .append(getCurrentCalorie())
      .append(getDay())
      .append(getCalendarDate())
      .append(getDateCreated())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("DailyInfo {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("weight=" + String.valueOf(getWeight()) + ", ")
      .append("User=" + String.valueOf(getUser()) + ", ")
      .append("bmi=" + String.valueOf(getBmi()) + ", ")
      .append("currentCalorie=" + String.valueOf(getCurrentCalorie()) + ", ")
      .append("day=" + String.valueOf(getDay()) + ", ")
      .append("calendarDate=" + String.valueOf(getCalendarDate()) + ", ")
      .append("dateCreated=" + String.valueOf(getDateCreated()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static BuildStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static DailyInfo justId(String id) {
    return new DailyInfo(
      id,
      null,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      weight,
      User,
      bmi,
      currentCalorie,
      day,
      calendarDate,
      dateCreated);
  }
  public interface BuildStep {
    DailyInfo build();
    BuildStep id(String id);
    BuildStep weight(Integer weight);
    BuildStep user(User user);
    BuildStep bmi(Integer bmi);
    BuildStep currentCalorie(Integer currentCalorie);
    BuildStep day(Integer day);
    BuildStep calendarDate(String calendarDate);
    BuildStep dateCreated(Temporal.DateTime dateCreated);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private Integer weight;
    private User User;
    private Integer bmi;
    private Integer currentCalorie;
    private Integer day;
    private String calendarDate;
    private Temporal.DateTime dateCreated;
    @Override
     public DailyInfo build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new DailyInfo(
          id,
          weight,
          User,
          bmi,
          currentCalorie,
          day,
          calendarDate,
          dateCreated);
    }
    
    @Override
     public BuildStep weight(Integer weight) {
        this.weight = weight;
        return this;
    }
    
    @Override
     public BuildStep user(User user) {
        this.User = user;
        return this;
    }
    
    @Override
     public BuildStep bmi(Integer bmi) {
        this.bmi = bmi;
        return this;
    }
    
    @Override
     public BuildStep currentCalorie(Integer currentCalorie) {
        this.currentCalorie = currentCalorie;
        return this;
    }
    
    @Override
     public BuildStep day(Integer day) {
        this.day = day;
        return this;
    }
    
    @Override
     public BuildStep calendarDate(String calendarDate) {
        this.calendarDate = calendarDate;
        return this;
    }
    
    @Override
     public BuildStep dateCreated(Temporal.DateTime dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }
    
    /** 
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, Integer weight, User user, Integer bmi, Integer currentCalorie, Integer day, String calendarDate, Temporal.DateTime dateCreated) {
      super.id(id);
      super.weight(weight)
        .user(user)
        .bmi(bmi)
        .currentCalorie(currentCalorie)
        .day(day)
        .calendarDate(calendarDate)
        .dateCreated(dateCreated);
    }
    
    @Override
     public CopyOfBuilder weight(Integer weight) {
      return (CopyOfBuilder) super.weight(weight);
    }
    
    @Override
     public CopyOfBuilder user(User user) {
      return (CopyOfBuilder) super.user(user);
    }
    
    @Override
     public CopyOfBuilder bmi(Integer bmi) {
      return (CopyOfBuilder) super.bmi(bmi);
    }
    
    @Override
     public CopyOfBuilder currentCalorie(Integer currentCalorie) {
      return (CopyOfBuilder) super.currentCalorie(currentCalorie);
    }
    
    @Override
     public CopyOfBuilder day(Integer day) {
      return (CopyOfBuilder) super.day(day);
    }
    
    @Override
     public CopyOfBuilder calendarDate(String calendarDate) {
      return (CopyOfBuilder) super.calendarDate(calendarDate);
    }
    
    @Override
     public CopyOfBuilder dateCreated(Temporal.DateTime dateCreated) {
      return (CopyOfBuilder) super.dateCreated(dateCreated);
    }
  }
  
}
