package tavebalak.OTTify.community.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import tavebalak.OTTify.common.ApiResponse;
import tavebalak.OTTify.community.dto.*;
import tavebalak.OTTify.community.service.CommunityService;
import tavebalak.OTTify.community.service.ReplyService;
import tavebalak.OTTify.exception.NotFoundException;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@MockBean(JpaMetamodelMappingContext.class)
@SpringBootTest
@AutoConfigureMockMvc
@Rollback(value = true)
class CommunityControllerTest {

    @MockBean
    private CommunityController communityController;
    @Autowired //가짜 객체 생성
    private CommunityService communityService;
    @Autowired
    private ReplyService replyService;

    @Autowired
    private MockMvc mockMvc; //HTTP호출

    ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    public void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(communityController).build();
    }

    @Test
    @DisplayName("POST 주제등록 컨트롤러 로직 성공")
    public void registerSubject () throws Exception{
        //given
        CommunitySubjectCreateDTO request = registerSubjectRequest();
        Gson gson = new Gson();
        String content = gson.toJson(request);

        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/discussion/subject")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                        .andExpect(status().isOk());

    }


    private CommunitySubjectCreateDTO registerSubjectRequest(){
        return  CommunitySubjectCreateDTO.builder()
                .programId(1L)
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
                .subjectId(19L)
                .comment("test content")
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
                .subjectId(19L)
                .commentId(57L)
                .content("test content")
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
                .subjectId(19L)
                .commentId(1L)
                .content("test content")
                .build();

        //when
        assertThrows(NoSuchElementException.class, ()-> replyService.saveRecomment(testContent));
    }

    @Test
    @DisplayName("POST 대댓글 등록 컨트롤러 로직 실패 - 빈 내용")
    public void getFailBadIdTestRecommentContent() throws Exception{
        //given
        ReplyRecommentCreateDTO testContent = ReplyRecommentCreateDTO.builder()
                .subjectId(19L)
                .commentId(57L)
                .content(" ")
                .build();

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/discussion/recomment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(testContent)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT 주제 수정 컨트롤러 로직 성공")
    public void 토론_주제_수정_성공() throws Exception, NotFoundException {
        //given
        Long targetSubjectId = 19L;
        String tochangeComment = "testData modify";
        CommunitySubjectDTO article = communityService.getArticle(targetSubjectId);

        CommunitySubjectEditDTO editDTO = CommunitySubjectEditDTO.builder()
                .subjectId(targetSubjectId)
                .content(tochangeComment)
                .subjectName(article.getSubjectName())
                .programId(article.getProgramId())
                .posterPath(article.getPosterPath())
                .programTitle(article.getProgramTitle())
                .build();

        when(communityController.modifySubject(any(CommunitySubjectEditDTO.class))).thenReturn(ApiResponse.success("성공적으로 토론주제를 수정하였습니다."));

        //when
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/discussion/subject")
                .content(objectMapper.writeValueAsString(editDTO))
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

}