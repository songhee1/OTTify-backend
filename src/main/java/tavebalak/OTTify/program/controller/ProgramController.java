package tavebalak.OTTify.program.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tavebalak.OTTify.common.BaseResponse;
import tavebalak.OTTify.program.service.ProgramShowAndSaveService;

@RestController
@RequiredArgsConstructor
public class ProgramController {
    private final ProgramShowAndSaveService programShowAndSaveService;

    @GetMapping("/api/search")
    public BaseResponse search(@RequestParam("name") String name){
        return BaseResponse.success(programShowAndSaveService.searchByName(name));
    }
}
