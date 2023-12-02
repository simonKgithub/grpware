package com.study.grpware.announce;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;

    public List<AnnouncementEntity> findAll(){
        return announcementRepository.findAll();
    }
}
