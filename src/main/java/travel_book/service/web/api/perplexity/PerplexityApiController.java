package travel_book.service.web.api.perplexity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
public class PerplexityApiController {

    private final PerplexityApiService perplexityApiService;

    //@Autowired    // 이게 없으면 컨테이너에 등록되어있더라도 주입을 못하겠지..? -> 컨트롤 안에 서비스가 아니고, 컨트롤, 서비스 각각 컴포넌트 스캔 돼서 Autowired가 없어도 되는건가?
    public PerplexityApiController(PerplexityApiService perplexityApiService) {
        this.perplexityApiService = perplexityApiService;
    }
    // 웹 질의 -> 컨트롤러 -> API 호출 -> API 응답 -> 웹 응답
    @PostMapping("/api/perplexity")
    public PerplexityApiModel ApiRequest(@RequestBody PerplexityApiModel model) throws IOException {
        perplexityApiService.requestQuery(model);  // 여기서 model 넘겨주지말고 요청질문 보내주고 responseContent(응답값) 리턴 받아서 값 저장하는게 메모리가 더 좋으려나 -> 근데 여기는 컨트롤이니깐.. 넘겨서 서비스에서 처리하자..

        return model;
    }
}
