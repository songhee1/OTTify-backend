package tavebalak.OTTify.community.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import tavebalak.OTTify.common.constant.Role;
import tavebalak.OTTify.common.constant.SocialType;
import tavebalak.OTTify.community.entity.Community;

import tavebalak.OTTify.program.entity.Program;
import tavebalak.OTTify.program.repository.ProgramRepository;
import tavebalak.OTTify.user.entity.User;
import tavebalak.OTTify.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE,
        connection = EmbeddedDatabaseConnection.H2)
public class CommunityRepositoryTest {
    @Autowired
    private CommunityRepository communityRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProgramRepository programRepository;

    List<Long> numList = new ArrayList<>();

    public void makeList(){
        for(long i=1;i<=50;i++){
            numList.add(i);
        }
    }

    @Test
    @DisplayName("전체 주제에 대해 페이징된 커뮤니티 목록 확인")
    public void findCommunitiesBy() throws Exception{
        //given
        User user = userRepository.save(
                User.builder()
                        .email("test-email")
                        .nickName("test-nickName")
                        .profilePhoto("test-url")
                        .socialType(SocialType.GOOGLE) // 적절한 값으로 변경
                        .role(Role.USER) // 적절한 값으로 변경
                        .build()
        );
        makeList();
        for(int i=1;i<=50;i++) communityRepository.save(makeCommunity(user));

        //when
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.DESC, "createdAt");
        userRepository.save(user);
        Page<Community> communities = communityRepository.findCommunitiesBy(pageRequest);

        //then
        assertThat(communities.getNumber()).isEqualTo(0);
        assertThat(communities.getSize()).isEqualTo(10);
    }

    public Community makeCommunity(User user){
        Collections.shuffle(numList);
        Program program = programRepository.save(Program.builder().title("test-title").build());

        return Community.builder()
                .title("test-title")
                .content("test-content")
                .user(user)
                .program(program)
                .build();
    }
}
