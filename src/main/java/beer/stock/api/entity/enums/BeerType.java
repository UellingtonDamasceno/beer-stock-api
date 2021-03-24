package beer.stock.api.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author Uellington Damasceno
 */
@Getter
@AllArgsConstructor
public enum BeerType {
    LARGER("Larger"),
    MALZBIZER("Malzbizer"),
    WITBIER("Witbier"),
    WEISS("Weiss"),
    ALE("Ale"),
    IPA("IPA"),
    STOUT("Stout");

    private final String description;

}
