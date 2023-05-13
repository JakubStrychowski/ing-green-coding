package pl.ing.green.onlinegame.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Clan
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-05-12T15:57:22.308427800+02:00[Europe/Warsaw]")
public class Clan {

  @JsonProperty("numberOfPlayers")
  private Integer numberOfPlayers;

  @JsonProperty("points")
  private Integer points;
  
  public Clan() {
      
  }
  

    public Clan(int numberOfPlayers, int numberOfPoints) {
        this.numberOfPlayers = numberOfPlayers;
        this.points = numberOfPoints;
    }

  public Clan numberOfPlayers(Integer numberOfPlayers) {
    this.numberOfPlayers = numberOfPlayers;
    return this;
  }

  /**
   * Get numberOfPlayers
   * minimum: 1
   * maximum: 1000
   * @return numberOfPlayers
  */
  @Min(1) @Max(1000) 
  @Schema(name = "numberOfPlayers", example = "10", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public Integer getNumberOfPlayers() {
    return numberOfPlayers;
  }

  public void setNumberOfPlayers(Integer numberOfPlayers) {
    this.numberOfPlayers = numberOfPlayers;
  }

  public Clan points(Integer points) {
    this.points = points;
    return this;
  }

  /**
   * Get points
   * minimum: 1
   * maximum: 1000000
   * @return points
  */
  @Min(1) @Max(1000000) 
  @Schema(name = "points", example = "500", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public Integer getPoints() {
    return points;
  }

  public void setPoints(Integer points) {
    this.points = points;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Clan clan = (Clan) o;
    return Objects.equals(this.numberOfPlayers, clan.numberOfPlayers) &&
        Objects.equals(this.points, clan.points);
  }

  @Override
  public int hashCode() {
    return Objects.hash(numberOfPlayers, points);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append('{').append(numberOfPlayers).append(',').append(points).append('}');
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

    public int compareTo(Clan c2) {
        if (this.points == c2.points) { 
            if (this.numberOfPlayers == c2.numberOfPlayers) {
                return 0;
            } else {
                return this.numberOfPlayers > c2.numberOfPlayers ? -1 : 1;
            }
        } else {
            return this.points > c2.points ? 1 : -1;
        }
    }
}

