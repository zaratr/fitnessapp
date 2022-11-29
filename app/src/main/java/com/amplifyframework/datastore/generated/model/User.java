package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.HasMany;
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

/** This is an auto generated class representing the User type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Users", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class User implements Model {
  public static final QueryField ID = field("User", "id");
  public static final QueryField USERNAME = field("User", "username");
  public static final QueryField AGE = field("User", "age");
  public static final QueryField HEIGHT = field("User", "height");
  public static final QueryField TARGET_WEIGHT = field("User", "targetWeight");
  public static final QueryField CURRENT_WEIGHT = field("User", "currentWeight");
  public static final QueryField PROFILE_IMG_KEY = field("User", "profileImgKey");
  public static final QueryField ACTIVITY_LEVEL = field("User", "activityLevel");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String username;
  private final @ModelField(targetType="Int") Integer age;
  private final @ModelField(targetType="Int") Integer height;
  private final @ModelField(targetType="Int") Integer targetWeight;
  private final @ModelField(targetType="Int") Integer currentWeight;
  private final @ModelField(targetType="String") String profileImgKey;
  private final @ModelField(targetType="ActivityEnum") ActivityEnum activityLevel;
  private final @ModelField(targetType="DailyInfo") @HasMany(associatedWith = "User", type = DailyInfo.class) List<DailyInfo> DailyInfos = null;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getUsername() {
      return username;
  }
  
  public Integer getAge() {
      return age;
  }
  
  public Integer getHeight() {
      return height;
  }
  
  public Integer getTargetWeight() {
      return targetWeight;
  }
  
  public Integer getCurrentWeight() {
      return currentWeight;
  }
  
  public String getProfileImgKey() {
      return profileImgKey;
  }
  
  public ActivityEnum getActivityLevel() {
      return activityLevel;
  }
  
  public List<DailyInfo> getDailyInfos() {
      return DailyInfos;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private User(String id, String username, Integer age, Integer height, Integer targetWeight, Integer currentWeight, String profileImgKey, ActivityEnum activityLevel) {
    this.id = id;
    this.username = username;
    this.age = age;
    this.height = height;
    this.targetWeight = targetWeight;
    this.currentWeight = currentWeight;
    this.profileImgKey = profileImgKey;
    this.activityLevel = activityLevel;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      User user = (User) obj;
      return ObjectsCompat.equals(getId(), user.getId()) &&
              ObjectsCompat.equals(getUsername(), user.getUsername()) &&
              ObjectsCompat.equals(getAge(), user.getAge()) &&
              ObjectsCompat.equals(getHeight(), user.getHeight()) &&
              ObjectsCompat.equals(getTargetWeight(), user.getTargetWeight()) &&
              ObjectsCompat.equals(getCurrentWeight(), user.getCurrentWeight()) &&
              ObjectsCompat.equals(getProfileImgKey(), user.getProfileImgKey()) &&
              ObjectsCompat.equals(getActivityLevel(), user.getActivityLevel()) &&
              ObjectsCompat.equals(getCreatedAt(), user.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), user.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getUsername())
      .append(getAge())
      .append(getHeight())
      .append(getTargetWeight())
      .append(getCurrentWeight())
      .append(getProfileImgKey())
      .append(getActivityLevel())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("User {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("username=" + String.valueOf(getUsername()) + ", ")
      .append("age=" + String.valueOf(getAge()) + ", ")
      .append("height=" + String.valueOf(getHeight()) + ", ")
      .append("targetWeight=" + String.valueOf(getTargetWeight()) + ", ")
      .append("currentWeight=" + String.valueOf(getCurrentWeight()) + ", ")
      .append("profileImgKey=" + String.valueOf(getProfileImgKey()) + ", ")
      .append("activityLevel=" + String.valueOf(getActivityLevel()) + ", ")
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
  public static User justId(String id) {
    return new User(
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
      username,
      age,
      height,
      targetWeight,
      currentWeight,
      profileImgKey,
      activityLevel);
  }
  public interface BuildStep {
    User build();
    BuildStep id(String id);
    BuildStep username(String username);
    BuildStep age(Integer age);
    BuildStep height(Integer height);
    BuildStep targetWeight(Integer targetWeight);
    BuildStep currentWeight(Integer currentWeight);
    BuildStep profileImgKey(String profileImgKey);
    BuildStep activityLevel(ActivityEnum activityLevel);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private String username;
    private Integer age;
    private Integer height;
    private Integer targetWeight;
    private Integer currentWeight;
    private String profileImgKey;
    private ActivityEnum activityLevel;
    @Override
     public User build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new User(
          id,
          username,
          age,
          height,
          targetWeight,
          currentWeight,
          profileImgKey,
          activityLevel);
    }
    
    @Override
     public BuildStep username(String username) {
        this.username = username;
        return this;
    }
    
    @Override
     public BuildStep age(Integer age) {
        this.age = age;
        return this;
    }
    
    @Override
     public BuildStep height(Integer height) {
        this.height = height;
        return this;
    }
    
    @Override
     public BuildStep targetWeight(Integer targetWeight) {
        this.targetWeight = targetWeight;
        return this;
    }
    
    @Override
     public BuildStep currentWeight(Integer currentWeight) {
        this.currentWeight = currentWeight;
        return this;
    }
    
    @Override
     public BuildStep profileImgKey(String profileImgKey) {
        this.profileImgKey = profileImgKey;
        return this;
    }
    
    @Override
     public BuildStep activityLevel(ActivityEnum activityLevel) {
        this.activityLevel = activityLevel;
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
    private CopyOfBuilder(String id, String username, Integer age, Integer height, Integer targetWeight, Integer currentWeight, String profileImgKey, ActivityEnum activityLevel) {
      super.id(id);
      super.username(username)
        .age(age)
        .height(height)
        .targetWeight(targetWeight)
        .currentWeight(currentWeight)
        .profileImgKey(profileImgKey)
        .activityLevel(activityLevel);
    }
    
    @Override
     public CopyOfBuilder username(String username) {
      return (CopyOfBuilder) super.username(username);
    }
    
    @Override
     public CopyOfBuilder age(Integer age) {
      return (CopyOfBuilder) super.age(age);
    }
    
    @Override
     public CopyOfBuilder height(Integer height) {
      return (CopyOfBuilder) super.height(height);
    }
    
    @Override
     public CopyOfBuilder targetWeight(Integer targetWeight) {
      return (CopyOfBuilder) super.targetWeight(targetWeight);
    }
    
    @Override
     public CopyOfBuilder currentWeight(Integer currentWeight) {
      return (CopyOfBuilder) super.currentWeight(currentWeight);
    }
    
    @Override
     public CopyOfBuilder profileImgKey(String profileImgKey) {
      return (CopyOfBuilder) super.profileImgKey(profileImgKey);
    }
    
    @Override
     public CopyOfBuilder activityLevel(ActivityEnum activityLevel) {
      return (CopyOfBuilder) super.activityLevel(activityLevel);
    }
  }
  
}
