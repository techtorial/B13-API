package pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PokemonPojo {

    private int count;
    private String next;
    private String previous;
    private List<ResultPojo> results;
}
