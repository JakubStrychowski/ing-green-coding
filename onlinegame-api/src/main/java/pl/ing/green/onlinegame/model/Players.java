package pl.ing.green.onlinegame.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * Players
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-05-12T15:57:22.308427800+02:00[Europe/Warsaw]")
public class Players {

  @JsonProperty("groupCount")
  private int groupCount;

  @JsonProperty("clans")
  @Valid
  private List<Clan> clans = null;

  public Players groupCount(int groupCount) {
    this.groupCount = groupCount;
    return this;
  }

  /**
   * Number of players in single group
   * minimum: 1
   * maximum: 1000
   * @return groupCount
  */
  @Min(1) @Max(1000) 
  @Schema(name = "groupCount", example = "6", description = "Number of players in single group", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public int getGroupCount() {
    return groupCount;
  }

  public void setGroupCount(int groupCount) {
    this.groupCount = groupCount;
  }

  public Players clans(List<Clan> clans) {
    this.clans = clans;
    return this;
  }

  public Players addClansItem(Clan clansItem) {
    if (this.clans == null) {
      this.clans = new ArrayList<>();
    }
    this.clans.add(clansItem);
    return this;
  }

  /**
   * Get clans
   * @return clans
  */
  @Valid @Size(max = 20000) 
  @Schema(name = "clans", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public List<Clan> getClans() {
    return clans;
  }

  public void setClans(List<Clan> clans) {
    this.clans = clans;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Players players = (Players) o;
    return Objects.equals(this.groupCount, players.groupCount) &&
        Objects.equals(this.clans, players.clans);
  }

  @Override
  public int hashCode() {
    return Objects.hash(groupCount, clans);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Players {\n");
    sb.append("    groupCount: ").append(toIndentedString(groupCount)).append("\n");
    sb.append("    clans: ").append(toIndentedString(clans)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

