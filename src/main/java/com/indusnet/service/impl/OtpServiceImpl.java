package com.indusnet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.indusnet.model.OtpModel;
import com.indusnet.model.common.OtpResponse;
import com.indusnet.repository.OtpRepository;
import com.indusnet.service.OtpService;
import com.indusnet.util.Util;

@Service
public class OtpServiceImpl implements OtpService {

	@Autowired
	OtpRepository otpRepository;
	
	@Autowired
	Util util;
	@Override
	public OtpResponse createOtp(OtpModel otpModel) {
		String sec =""+ otpModel.getStartTime();
		String otp = util.generateTOTP256(sec, 180, otpModel.getDigit().toString());
		OtpModel model = otpRepository.findByMobile(otpModel.getMobile());
		if(model == null) {
			otpRepository.save(otpModel);
		}
		else {
			otpModel.setId(model.getId());
			otpRepository.save(otpModel);
		}
		return new OtpResponse(HttpStatus.OK.value(), "Otp is successfully generated", otp);
	}

}
