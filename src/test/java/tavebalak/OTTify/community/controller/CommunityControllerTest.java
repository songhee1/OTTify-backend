package tavebalak.OTTify.community.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import tavebalak.OTTify.common.BaseResponse;
import tavebalak.OTTify.community.dto.*;
import tavebalak.OTTify.community.entity.Community;
import tavebalak.OTTify.community.entity.Reply;
import tavebalak.OTTify.community.service.CommunityService;
import tavebalak.OTTify.community.service.ReplyService;
import tavebalak.OTTify.error.ErrorResponse;
import tavebalak.OTTify.error.exception.GlobalExceptionHandler;
import tavebalak.OTTify.exception.NotFoundException;
import tavebalak.OTTify.program.entity.Program;
import tavebalak.OTTify.program.repository.ProgramRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CommunityControllerTest {

    @InjectMocks
    private CommunityController communityController;
    @Mock
    private CommunityService communityService;
    @Mock
    private ReplyService replyService;
    private MockMvc mockMvc; //HTTP호출

    Program program;
    Long programId;
    Long subjectId;
    Long commentId;

    private static final String testComment = "test content";


    @BeforeEach
    public void init(){ // mockMvc 초기화, 각메서드가 실행되기전에 초기화 되게 함
        mockMvc = MockMvcBuilders.standaloneSetup(communityController).build();
        // standaloneMockMvcBuilder() 호출하고 스프링 테스트 설정 커스텀으로 mockMvc 객체 생성

    }

    public void init2() throws NotFoundException {
        mockMvc = MockMvcBuilders.standaloneSetup(communityController).build();
        //given - user
        double userAverageRating = 0.55;
        String nickName = "nike";
        String profilePhoto = "https://dkfldf";
        int socialType = 2;

        //유저 서비스 완성되면 유저 save 메서드 호출

        //given - program
        String programPosterPath = "test-posterPath";
        String programTitle = "test-title";



//        Program program1 = programRepository.save(Program.builder().title(programTitle).posterPath(programPosterPath).build());
//        System.out.println(program1);
//        programId = program1.getId();
//        program = program1;

        //given - community
        String subjectContent = "이 주제는 최신 출시된 한국 OTT 영화나 드라마들 중에서 즐기거나 패스할만한 작품들에 대해 논의하는 거야! 어떤 작품들이 시청자들에게 권장되는지, 그리고 그 이유는 무엇인지 혹은 왜 그 작품들을 건너뛰어야 하는지에 대해 의견을 나누면 재미있을 것 같아요. 함께 피드백 주고받으며 새로운 작품을 찾는 재미도 있을 거예요!";
        String subjectTitle = "한국 OTT 신작 오락가락: 즐기거나 패스?";

        Community community = communityService
                .saveSubject(CommunitySubjectCreateDTO.builder()
                        .programId(program.getId())
                        .programTitle(program.getTitle())
                        .content(subjectContent)
                        .posterPath(program.getPosterPath())
                        .subjectName(subjectTitle)
                        .build());
        subjectId = community.getId();

        //given - comment
        String replyComment = "이렇게 긴박한 상황에서도 신경 쓰이는 건 왜 먹을 건지였죠?!";
        Long communityId = community.getId();
        Long userId = 1L;
        Reply reply = replyService.saveComment(ReplyCommentCreateDTO.builder()
                .subjectId(communityId)
                .comment(replyComment)
                .build());
        commentId = reply.getId();

    }


    @Test
    @DisplayName("POST 주제등록 컨트롤러 로직 성공")
    public void registerSubject () throws Exception{
        //given
        CommunitySubjectCreateDTO request = registerSubjectRequest();
        Community response = communityService.saveSubject(request);

        when(communityService.saveSubject(any(CommunitySubjectCreateDTO.class)))
                .thenReturn(response);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/discussion/subject")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(request))
        );

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data", "성공적으로 토론주제를 생성하였습니다.").exists());

    }


    private CommunitySubjectCreateDTO registerSubjectRequest(){
        return  CommunitySubjectCreateDTO.builder()
                .programId(programId)
                .subjectName("사랑과 우정의 따뜻한 이야기 '응답하라 1988'")
                .content("'응답하라 1988'은 사랑과 우정의 따뜻한 이야기를 그려냈습니다. 이 드라마가 많은 사랑을 받은 이유에 대해 토론해보세요!")
                .programTitle("스쿼드 게임")
                .posterPath("https://image.tmdb.org/t/p/w500/8Pjd1MiCbY8s9Zrxbb8SvCpY7s7.jpg")
                .build();
    }

    @Test
    @DisplayName("POST 댓글 등록 컨트롤러 로직 성공")
    void registerComment() throws Exception, NotFoundException {
        //given
        ReplyCommentCreateDTO testContent = ReplyCommentCreateDTO.builder()
                .subjectId(subjectId)
                .comment(testComment)
                .build();

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/discussion/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(testContent)))
                         .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST 대댓글 등록 컨트롤러 로직 성공")
    void registerRecomment() throws Exception {
        //given
        ReplyRecommentCreateDTO testContent = ReplyRecommentCreateDTO.builder()
                .subjectId(subjectId)
                .commentId(commentId)
                .content(testComment)
                .build();

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/discussion/recomment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(testContent)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST 대댓글 등록 컨트롤러 로직 실패 - 없는 ID")
    public void getFailBadIdTestRecommentID() throws Exception{
        //given
        ReplyRecommentCreateDTO testContent = ReplyRecommentCreateDTO.builder()
                .subjectId(10L)
                .commentId(commentId)
                .content(testComment)
                .build();

        //when
        assertThrows(NoSuchElementException.class, ()-> replyService.saveRecomment(testContent));
    }

    @Test
    @DisplayName("POST 대댓글 등록 컨트롤러 로직 실패 - 빈 내용")
    public void getFailBadIdTestRecommentContent() throws Exception, NotFoundException {
        //given
        ReplyRecommentCreateDTO testContent = ReplyRecommentCreateDTO.builder()
                .subjectId(subjectId)
                .commentId(commentId)
                .content(" ")
                .build();

        when(communityController.registerRecomment(any(ReplyRecommentCreateDTO.class)))
                .thenThrow(new RuntimeException());
        //when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/discussion/recomment")
//                        .content(objectMapper.writeValueAsString(testContent))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                 .andDo(print());
    }

    @Test
    @DisplayName("PUT 주제 수정 컨트롤러 로직 성공")
    public void 토론_주제_내용_수정_성공() throws Exception, NotFoundException {
        //given
        Long targetSubjectId = subjectId;
        String tochangeComment = testComment;
        CommunitySubjectDTO article = communityService.getArticle(targetSubjectId);

        CommunitySubjectEditDTO editDTO = CommunitySubjectEditDTO.builder()
                .subjectId(targetSubjectId)
                .content(tochangeComment)
                .subjectName(article.getTitle())
                .programId(article.getProgramId())
                .posterPath(article.getPosterPath())
                .programTitle(article.getProgramTitle())
                .build();

        when(communityController.modifySubject(any(CommunitySubjectEditDTO.class))).thenReturn(BaseResponse.success("성공적으로 토론주제를 수정하였습니다."));

        //when
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/discussion/subject")
//                .content(objectMapper.writeValueAsString(editDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"));

        System.out.println(perform.andDo(print()));

        //then
        perform
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").value("성공적으로 토론주제를 수정하였습니다."))
                .andExpect(status().isOk())
                .andDo(print());

    }
    
    @Test
    @DisplayName("PUT 댓글 수정 컨트롤러 로직 성공")
    public void 댓글_수정_성공() throws Exception, NotFoundException {
        //given
        Long targetCommentId = commentId;
        String tochangeComment = testComment;
        List<CommentDTO> commentList = replyService.getComment(targetCommentId);
        List<ReplyCommentEditDTO> replyCommentEditDTOs = new ArrayList<>();

        for (CommentDTO commentDTO : commentList) {
            replyCommentEditDTOs.add(ReplyCommentEditDTO.builder()
                    .subjectId(commentDTO.getSubjectId())
                    .commentId(commentDTO.getCommentId())
                    .comment(tochangeComment)
                    .build());
        }

        when(communityController.modifyComment(any(ReplyCommentEditDTO.class))).thenReturn(BaseResponse.success("성공적으로 토론댓글을 수정하였습니다."));

        //when, then
        for (ReplyCommentEditDTO replyCommentEditDTO : replyCommentEditDTOs) {
            mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/discussion/comment")
//                    .content(objectMapper.writeValueAsString(replyCommentEditDTO))
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8"))
                    .andExpect(jsonPath("$.data").exists())
                    .andExpect(jsonPath("$.data").value("성공적으로 토론댓글을 수정하였습니다."))
                    .andExpect(status().isOk())
                    .andDo(print());
        }

    }



    @AfterAll
    public void end() throws NotFoundException {
        replyService.deleteComment(subjectId, commentId);
        communityService.deleteSubject(subjectId);
//        programRepository.delete(program);
    }


}