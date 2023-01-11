package com.mycard.demo.privacy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import org.springframework.stereotype.Service;

import com.mycard.demo.user.UserRepository;

@Service
public class PrivacyService {
    private final UserRepository userRepository;
    private final PrivacyRepository privacyRepository;

    public PrivacyService(UserRepository userRepository, PrivacyRepository privacyRepository) {
        this.userRepository = userRepository;
        this.privacyRepository = privacyRepository;
    }

    public PrivacyDTO getPrivacy(String userId) {
        Privacy privacy = this.privacyRepository.findByUser(this.userRepository.findByUserId(userId).get());

        PrivacyDTO privacyDTO = new PrivacyDTO();
        privacyDTO.setProfileImgPath(privacy.getProfileImgPath());
        privacyDTO.setName(privacy.getName());
        privacyDTO.setPhoneNum(privacy.getPhoneNum());
        privacyDTO.setEmail(privacy.getEmail());
        privacyDTO.setEmpLogoImgPath(privacy.getEmpLogoImgPath());
        privacyDTO.setEmpName(privacy.getEmpName());
        privacyDTO.setEmpTel(privacy.getEmpTel());
        privacyDTO.setEmpFax(privacy.getEmpFax());
        privacyDTO.setEmpEmail(privacy.getEmpEmail());
        privacyDTO.setEmpAddress(privacy.getEmpAddress());
        privacyDTO.setSimpleIntro(privacy.getSimpleIntro());

        return privacyDTO;
    }

    public void updatePrivacy(PrivacyUpdateForm privacyUpdateForm, String userId) throws IOException {
        Privacy privacy = this.privacyRepository.findByUser(this.userRepository.findByUserId(userId).get());

        String profileImgPathStr = makePath(true, userId);
        String empLogoImgPathStr = makePath(false, userId);

        if (privacyUpdateForm.getProfileBase64() != null) {
            saveFile(privacyUpdateForm.getProfileBase64(), profileImgPathStr);
        } else {
            profileImgPathStr = privacy.getProfileImgPath();
        }

        if (privacyUpdateForm.getEmpLogoBase64() != null) {
            saveFile(privacyUpdateForm.getEmpLogoBase64(), empLogoImgPathStr);
        } else {
            empLogoImgPathStr = privacy.getEmpLogoImgPath();
        }

        privacy.updateProfileImgPath(profileImgPathStr);
        privacy.updateName(privacyUpdateForm.getName());
        privacy.updatePhoneNum(privacyUpdateForm.getPhoneNum());
        privacy.updateEmail(privacyUpdateForm.getEmail());
        privacy.updateEmpLogoImgPath(empLogoImgPathStr);
        privacy.updateEmpName(privacyUpdateForm.getEmpName());
        privacy.updateEmpTel(privacyUpdateForm.getEmpTel());
        privacy.updateEmpFax(privacyUpdateForm.getEmpFax());
        privacy.updateEmpEmail(privacyUpdateForm.getEmpEmail());
        privacy.updateEmpAddress(privacyUpdateForm.getEmpAddress());
        privacy.updateSimpleIntro(privacyUpdateForm.getSimpleIntro());

        this.privacyRepository.save(privacy);
    }

    private String makePath(boolean checkType, String userId) {
        String uploadBase = "C:\\upload\\";

        File uploadDir = new File(uploadBase);

        if (!uploadDir.exists()) {
            try {
                uploadDir.mkdirs();
            } catch (Exception e) {
                e.getStackTrace();
            }
        }

        if (checkType == true) {
            return uploadBase + userId + "_PROFILE.png";
        } else {
            return uploadBase + userId + "_EMPLOGO.svg";
        }
    }

    private void saveFile(String base64Data, String path) throws IOException {
        byte[] decodeBytes = Base64.getDecoder().decode(base64Data);

        try {
            Files.write(Paths.get(path), decodeBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
