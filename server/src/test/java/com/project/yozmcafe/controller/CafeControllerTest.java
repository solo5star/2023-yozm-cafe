package com.project.yozmcafe.controller;

import com.project.yozmcafe.controller.dto.cafe.CafeResponse;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.cafe.UnViewedCafe;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberRepository;
import com.project.yozmcafe.fixture.Fixture;
import com.project.yozmcafe.service.auth.JwtTokenProvider;
import com.project.yozmcafe.util.AcceptanceContext;
import com.project.yozmcafe.util.UnViewedCafeRepository;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CafeControllerTest {

    @LocalServerPort
    int port;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AcceptanceContext context;

    @Autowired
    private CafeRepository cafeRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private UnViewedCafeRepository unViewedCafeCafeRepository;

    private Member member;

    @BeforeEach
    void setUp() {
        cafeRepository.save(Fixture.getCafe("n1", "address1", 1));
        cafeRepository.save(Fixture.getCafe("n2", "address2", 1));
        cafeRepository.save(Fixture.getCafe("n3", "address3", 1));
        cafeRepository.save(Fixture.getCafe("n4", "address4", 1));
        RestAssured.port = port;
    }

    @Test
    @DisplayName("로그인 되지 않은 사용자가 /cafes?page=? 에 GET요청을 보내면 페이지에 해당하는 서로 다른 카페 정보들을 5개씩 응답한다.")
    void getCafesSuccessByUnLoginUser() {
        //given
        cafeRepository.save(Fixture.getCafe("n5", "address5", 1));

        //when
        context.invokeHttpGet("guest/cafes?page=1");

        //then
        Set<Long> cafeResponseIds = getCafeResponseIds(context.response);
        assertAll(
                () -> assertThat(context.response.statusCode()).isEqualTo(200),
                () -> assertThat(cafeResponseIds).hasSize(5)
        );
    }

    @Test
    @DisplayName("로그인 되지 않은 사용자가 /cafes?page=? 에 GET요청을 보낸 경우 page가 최대 page를 초과하는 경우 빈배열을 반환한다.")
    void getCafesEmptyByUnLoginUser() {
        //when
        context.invokeHttpGet("guest/cafes?page=2000");

        //then
        assertAll(
                () -> assertThat(context.response.statusCode()).isEqualTo(200),
                () -> assertThat(context.response.jsonPath().getList("$")).isEmpty()
        );
    }

    @Test
    @DisplayName("로그인한 사용자가 /cafes?page=?에 GET 요청을 보내면 아직보지않은 랜덤한,서로 다른 카페정보를 5개씩 응답한다.")
    void getCafesWithMember() {
        //given
        cafeRepository.save(Fixture.getCafe("n5", "address5", 1));
        given(jwtTokenProvider.getMemberId(anyString())).willReturn("토큰");
        saveMemberAndUnViewedCafes();

        //when
        context.invokeHttpGetWithToken("/cafes?page=1");
        final Response response = context.response;

        //then
        Set<Long> cafeIds = getCafeResponseIds(response);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(200),
                () -> assertThat(cafeIds).hasSize(5)
        );
    }

    @Test
    @DisplayName("로그인한 사용자가 /cafes?page=?에 GET 요청을 보낼 때, 아직보지 않은 카페가 5개 미만이면 그 수만큼의 서로 다른 카페를 응답한다.")
    void getCafesWithMemberWhenCafeLessThan() {
        //given
        given(jwtTokenProvider.getMemberId(anyString())).willReturn("토큰");
        saveMemberAndUnViewedCafes();

        //when
        context.invokeHttpGetWithToken("/cafes?page=1");
        Response response = context.response;

        //then
        Set<Long> cafeIds = getCafeResponseIds(response);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(200),
                () -> assertThat(cafeIds).hasSize(4)
        );
    }

    private void saveMemberAndUnViewedCafes() {
        member = memberRepository.save(new Member("토큰", "name1", "image"));

        final List<Cafe> allCafes = cafeRepository.findAll();
        final List<UnViewedCafe> allUnViewedCafes = allCafes.stream()
                .map(savedCafe -> new UnViewedCafe(savedCafe, member))
                .toList();
        unViewedCafeCafeRepository.saveAll(allUnViewedCafes);

        member.addUnViewedCafes(allCafes);
    }

    private static Set<Long> getCafeResponseIds(Response response) {
        Set<Long> cafeIds = new HashSet<>();
        List<CafeResponse> cafeResponses = response.jsonPath().getList(".", CafeResponse.class);

        cafeIds.addAll(cafeResponses.stream().map(CafeResponse::id)
                .collect(Collectors.toList()));
        return cafeIds;
    }
}
