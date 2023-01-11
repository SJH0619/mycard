package com.mycard.demo.subscrib;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.mycard.demo.privacy.Privacy;
import com.mycard.demo.privacy.PrivacyRepository;
import com.mycard.demo.user.User;
import com.mycard.demo.user.UserRepository;

@Service
public class SubscribService {
    private final SubscribRepository subscribRepository;
    private final UserRepository userRepository;
    private final PrivacyRepository privacyRepository;

    public SubscribService(SubscribRepository subscribRepository, UserRepository userRepository, PrivacyRepository privacyRepository) {
        this.subscribRepository = subscribRepository;
        this.userRepository = userRepository;
        this.privacyRepository = privacyRepository;
    }

    public List<SubscribDTO> getMyWallet(String userId) {
        Set<Subscrib> subscribs = this.subscribRepository.findBySubUserId(userId).get();

        Iterator<Subscrib> subscribIterator = subscribs.iterator();

        List<String> pubUserIdList = new ArrayList<>();
        List<SubscribDTO> subscribDTOs = new ArrayList<>();

        while(subscribIterator.hasNext()) {
            pubUserIdList.add(subscribIterator.next().getPubUserId());
        }

        for (String pubUserId: pubUserIdList) {
            User pubUser = this.userRepository.findByUserId(pubUserId).get();
            Privacy pubUserPrivacy = this.privacyRepository.findByUser(pubUser);

            SubscribDTO subscribDTO = new SubscribDTO();
            subscribDTO.setPubUserId(pubUserId);
            subscribDTO.setPubEmpLogoImgPath(pubUserPrivacy.getEmpLogoImgPath());
            subscribDTO.setPubEmpName(pubUserPrivacy.getEmpName());
            subscribDTO.setPubName(pubUserPrivacy.getName());
            subscribDTO.setPubPhoneNum(pubUserPrivacy.getPhoneNum());
            subscribDTO.setPubEmail(pubUserPrivacy.getEmail());

            subscribDTOs.add(subscribDTO);
        }

        return subscribDTOs;
    }

    public void addBusinessCard(String subUserId, String pubUserId, Boolean checkPubUserExist) {
        if((subUserId.equals(pubUserId) == false) &&
            checkPubUserExist &&
            (!checkRelationExist(subUserId, pubUserId))) {
            Subscrib subscrib = Subscrib.builder()
                .subUserId(subUserId)
                .pubUserId(pubUserId)
                .build();

            this.subscribRepository.save(subscrib);

            Subscrib reverseSubscrib = Subscrib.builder()
                .subUserId(pubUserId)
                .pubUserId(subUserId)
                .build();

            this.subscribRepository.save(reverseSubscrib);
        }
    }

    public void removeBusinessCard(String subUserId, String pubUserId) {
        if (checkRelationExist(subUserId, pubUserId)) {
            this.subscribRepository.deleteBySubUserIdAndPubUserId(subUserId, pubUserId);
            this.subscribRepository.deleteBySubUserIdAndPubUserId(pubUserId, subUserId);
        }
    }

    private Boolean checkRelationExist(String subUserId, String pubUserId) {
        Optional<Subscrib> result = this.subscribRepository.findBySubUserIdAndPubUserId(subUserId, pubUserId);

        return result.isPresent();
    }
}
