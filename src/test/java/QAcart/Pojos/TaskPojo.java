package QAcart.Pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskPojo {
    private String item;
    private Boolean isCompleted;
    private String userID;
    private String createdAt;
    @JsonProperty("_id")
    private String id;
    @JsonProperty("__v")
    private String v;

    public TaskPojo() {
    }
    public TaskPojo(String item) {
        this.item = item;
    }
    public TaskPojo(String item, Boolean isCompleted) {
        this.item = item;
        this.isCompleted = isCompleted;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Boolean getisCompleted() {
        return isCompleted;
    }

    public void setisCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    @JsonProperty("_id")
    public String getId() {
        return id;
    }
    @JsonProperty("_id")
    public void setId(String id) {
        this.id = id;
    }
    @JsonProperty("__v")
    public String getV() {
        return v;
    }
    @JsonProperty("__v")
    public void setV(String v) {
        this.v = v;
    }
}
