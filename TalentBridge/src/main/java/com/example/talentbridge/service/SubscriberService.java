package com.example.talentbridge.service;

import com.example.talentbridge.dto.request.subscriber.DefaultSubscriberRequestDto;
import com.example.talentbridge.dto.response.subscriber.DefaultSubscriberResponseDto;


public interface SubscriberService {
    DefaultSubscriberResponseDto saveSelfSubscriber(
            DefaultSubscriberRequestDto defaultSubscriberRequestDto
    );

    DefaultSubscriberResponseDto findSelfSubscriber();

    DefaultSubscriberResponseDto updateSelfSubscriber(
            DefaultSubscriberRequestDto defaultSubscriberRequestDto
    );

    void deleteSelfSubscriber();
}
