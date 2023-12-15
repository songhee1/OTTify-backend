package tavebalak.OTTify.community.controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tavebalak.OTTify.common.ApiResponse;
import tavebalak.OTTify.community.dto.CommunitySubjectCreateDTO;
import tavebalak.OTTify.community.repository.CommunityRepository;
import tavebalak.OTTify.community.service.CommunityService;
import tavebalak.OTTify.community.service.CommunityServiceImpl;
import tavebalak.OTTify.community.service.ReplyService;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@MockBean(JpaMetamodelMappingContext.class)
@SpringBootTest
@AutoConfigureMockMvc
class CommunityControllerTest {

    @MockBean
    private CommunityController communityController;
    @Autowired //가짜 객체 생성
    private CommunityService communityService;

    @Autowired
    private MockMvc mockMvc; //HTTP호출


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
        ApiResponse apiResponse = subjectResponse();

        //when
//        ResultActions resultActions = mockMvc.perform(
//                MockMvcRequestBuilders.post("/api/v1/discussion/subject")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new Gson().toJson(request))
//        );

        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/discussion/subject")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                        .andExpect(status().isOk());

    }

    private ApiResponse<String> subjectResponse() {
        return ApiResponse.success("성공적으로 토론주제를 생성하였습니다.");
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
    @DisplayName("POST 댓글 등록 컨트롤러 로직 확인")
    void registerComment() throws Exception{
        //given

        //when

        //then
    }

    @Test
    void registerRecomment() {
    }
}