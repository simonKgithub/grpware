package com.study.grpware.attendance;

import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional //테스트에서 선언할 경우 테스트 종료 후 rollback 됨
@TestPropertySource(locations = "classpath:application.properties")
class AttendanceServiceImplTest {
    @Autowired AttendanceServiceImpl attendanceServiceImpl;

//    @Test
    @DisplayName("70m 이내인지 check")
    void locationAreaTest(){
        String targetLat = "36.343004432782024";
        String targetLng = "127.38642968007845";
        String repLat = "36.34353732411248";
        String repLng = "127.38605914377665";

        //double distance = attendanceServiceImpl.calDistance(targetLat, targetLng, repLat, repLng);

        //assertTrue(distance <= 70.0);
    }

}