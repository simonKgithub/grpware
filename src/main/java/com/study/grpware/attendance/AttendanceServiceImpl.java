package com.study.grpware.attendance;

import com.study.grpware.member.Member;
import com.study.grpware.member.MemberRepository;
import com.study.grpware.util.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    final private AttendanceRepository attendanceRepository;
    final private MemberRepository memberRepository;

    @Override
    public AttendanceEntity saveRepPlace(AttendanceDto attendanceDto) {
        Member member = memberRepository.findByEmail(CommonUtils.getMember().getEmail());

        AttendanceEntity entity = attendanceRepository.findByRepYn(true);
        if (entity == null) {
            entity = new AttendanceEntity();
            entity.setRepYn(true);
        }
        entity.setPlaceName(attendanceDto.getPlaceName());
        entity.setAddress(attendanceDto.getAddress());
        entity.setLat(attendanceDto.getLat());
        entity.setLng(attendanceDto.getLng());
        entity.setRegDate(CommonUtils.getNow());
        entity.setMember(member);

        return attendanceRepository.save(entity);
    }

    @Override
    public AttendanceEntity findRepPlace(boolean repYn) {
        return attendanceRepository.findByRepYn(repYn);
    }

    @Override
    public void attendCheck(AttendanceDto attendanceDto) {
        // 1. 사용자 위치 범위와 일치하는지 확인
        if (nowLocationCheck(attendanceDto.getLat(), attendanceDto.getLng())) {
            // 2-1. 일치 시 저장 및 성공 반환
            AttendanceEntity entity = new AttendanceEntity();
            entity.setLat(attendanceDto.getLat());
            entity.setLng(attendanceDto.getLng());
            entity.setRegDate(CommonUtils.getNow());
            entity.setMember(CommonUtils.getMember());

            attendanceRepository.save(entity);
        } else {
            // 2-2. 불일치 시
            throw new IllegalStateException("70M 이상의 거리차가 발생합니다.\n지정된 장소 범위에서 재시도하세요.");
        }
    }

    @Override
    public boolean isAttend() {
        Member member = CommonUtils.getMember();
        String now = CommonUtils.getNow();
        String today = now.substring(0, 8) + "%";

        return attendanceRepository.findByMemberAndRegDateAndRepYn(member, today, false).isPresent();
    }

    /**
     * 사용자 위치 체크
     * @param targetLat
     * @param targetLng
     */
    private boolean nowLocationCheck(String targetLat, String targetLng) {
        AttendanceEntity repEntity = attendanceRepository.findByRepYn(true);
        String repLat = repEntity.getLat();
        String repLng = repEntity.getLng();

        // 두 위지 거리 계산 거리 계산
        double distance = calDistance(targetLat, targetLng, repLat, repLng);

        return distance <= 70.0;
    }

    /**
     * 경도와 위도를 사용하여 두 위지 거리 계산
     * @param targetLat
     * @param targetLng
     * @param repLat
     * @param repLng
     */
    private double calDistance(String targetLat, String targetLng, String repLat, String repLng) {
        double lat1 = Double.valueOf(targetLat);
        double lng1 = Double.valueOf(targetLng);
        double lat2 = Double.valueOf(repLat);
        double lng2 = Double.valueOf(repLng);

        double theta = lng1 - lng2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                    + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        double degrees = rad2deg(dist);

        // 미터 단위로 변환하여 반환
        return degrees * 60 * 1.1515 * 1609.344;
    }

    /**
     * This function converts decimal degrees to radians
     * @param deg
     * @return
     */
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /**
     * This function converts radians to decimal degrees
     * @param rad
     * @return
     */
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}
