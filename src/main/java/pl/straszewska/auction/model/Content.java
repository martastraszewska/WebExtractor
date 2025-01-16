package pl.straszewska.auction.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Content {
    private String name;

    private String contentHash;

    private Long timestamp;

    private String websiteUrl;

    private String dataSelector;
}
