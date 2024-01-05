package tavebalak.OTTify.program.dto.searchTrending.openApiRequest;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SearchTrendingOpenApiProgramInfo extends CommonOpenApiProgramInfo {
    //영화
    private String title;
    private String original_title;
    private String release_date;

    //TV
    private String name;
    private String original_name;
    private String first_air_date;

    //trending 시 사용
    private String media_type;

    //공통
    private List<Long> genre_ids=new ArrayList<>();
}
