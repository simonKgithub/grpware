package com.study.grpware.attendance;

import com.study.grpware.member.Member;
import com.study.grpware.member.MemberRepository;
import com.study.grpware.util.CommonUtils;
import lombok.Builder;
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
        AttendanceEntity entity = new AttendanceEntity();
        entity.setPlaceName(attendanceDto.getPlaceName());
        entity.setAddress(attendanceDto.getAddress());
        entity.setLat(attendanceDto.getLat());
        entity.setLng(attendanceDto.getLng());
        entity.setRepYn(true);
        entity.setRegDate(CommonUtils.getNow());
        entity.setMember(member);

        return attendanceRepository.save(entity);
    }

    @Override
    public AttendanceEntity findRepPlace(boolean repYn) {
        return attendanceRepository.findByRepYn(repYn);
    }
}
