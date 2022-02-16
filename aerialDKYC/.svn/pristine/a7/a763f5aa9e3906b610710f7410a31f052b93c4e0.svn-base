package com.edios.cdf.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.edios.cdf.bean.security.SiteBean;
import com.edios.cdf.entity.to.SiteEntityTO;
import com.edios.cdf.manager.SiteManager;
import com.edios.cdf.util.DeleteRecords;
import com.edios.cdf.util.PayloadBean;
import com.edios.cdf.validator.BaseResponse;

@RestController
public class SiteController extends AbstractController {

	@Autowired
	MessageSource messageSource;

	@Autowired
	SiteManager siteManager;

	@PostMapping("/fetch-sites")
	public ResponseEntity<List<SiteEntityTO>> fetchSites(@RequestBody PayloadBean payloadBean) {
		List<SiteEntityTO> siteEntityTOList = null;
		if (payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			siteEntityTOList = siteManager.fetchSites(payloadBean.getAccountId());
			return new ResponseEntity<List<SiteEntityTO>>(siteEntityTOList, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("/fetch-sites-on-criteria")
	public ResponseEntity<List<SiteEntityTO>> fetchSitesOnCriteria(@RequestBody PayloadBean payloadBean) {
		List<SiteEntityTO> siteEntityTOList = null;
		if (payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			siteEntityTOList = siteManager.fetchSitesOnCriteria(payloadBean.getSearchParameter(),
					payloadBean.getAccountId());
			return new ResponseEntity<List<SiteEntityTO>>(siteEntityTOList, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("/add-site")
	public BaseResponse addSite(@RequestBody SiteBean siteBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		siteBean.setIpAddress(request.getRemoteAddr());
		String resultString = siteManager.addSite(siteBean);
		if (resultString.equalsIgnoreCase("ADDED")) {
			baseResponse = new BaseResponse(HttpStatus.OK, "ADDED", "");
		} else if (resultString.equalsIgnoreCase("NameAlreadyExist")) {
			baseResponse = new BaseResponse(HttpStatus.ALREADY_REPORTED, "ALREADY_REPORTED",
					messageSource.getMessage("duplicateFieldMessage", new Object[] { "Site Name" }, "", Locale.US));
		} else if (resultString.equalsIgnoreCase("CodeAlreadyExist")) {
			baseResponse = new BaseResponse(HttpStatus.ALREADY_REPORTED, "ALREADY_REPORTED",
					messageSource.getMessage("duplicateFieldMessage", new Object[] { "Site Code" }, "", Locale.US));
		}
		return baseResponse;
	}

	@PostMapping("/edit-site")
	public ResponseEntity<SiteBean> findSiteById(@RequestBody PayloadBean payloadBean) {
		if (payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			SiteBean siteBean = siteManager.findSiteById(payloadBean.getId(), payloadBean.getAccountId());
			return new ResponseEntity<SiteBean>(siteBean, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
	}

	@PutMapping("/update-site")
	public BaseResponse updateSite(@RequestBody SiteBean siteBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		try {
			siteBean.setIpAddress(request.getRemoteAddr());
			String resultString = siteManager.updateSite(siteBean);
			if (resultString.equalsIgnoreCase("UPDATED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", "");
			} else if (resultString.equalsIgnoreCase("NameAlreadyExist")) {
				baseResponse = new BaseResponse(HttpStatus.CONFLICT, "CONFLICT",
						messageSource.getMessage("duplicateFieldMessage", new Object[] { "Site Name" }, "", Locale.US));
			} else if (resultString.equalsIgnoreCase("CodeAlreadyExist")) {
				baseResponse = new BaseResponse(HttpStatus.CONFLICT, "CONFLICT",
						messageSource.getMessage("duplicateFieldMessage", new Object[] { "Site Code" }, "", Locale.US));
			} else if (resultString.equalsIgnoreCase("TransactionFailed")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "TRANSACTION_FAILED",
						messageSource.getMessage("transactionFailedUpdateMessage", null, "", Locale.US));
			} else if (resultString.equalsIgnoreCase("recordDeleted")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "TRANSACTION_FAILED",
						messageSource.getMessage("transactionFailedDeleteMessage", null, "", Locale.US));
			}
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return baseResponse;
		}
	}

	@PostMapping("/delete-site")
	public BaseResponse deleteSite(@RequestBody DeleteRecords deleteRecords) {
		BaseResponse baseResponse = null;
		try {
			String resultString = siteManager.deleteSite(deleteRecords);
			if (resultString.equalsIgnoreCase("DELETED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "DELETED", "");
			} else if (resultString.equalsIgnoreCase("TransactionFailed")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "TRANSACTION_FAILED",
						messageSource.getMessage("transactionFailedUpdateMessage", null, "", Locale.US));
			} else if (resultString.equalsIgnoreCase("recordDeleted")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "TRANSACTION_FAILED",
						messageSource.getMessage("transactionFailedDeleteMessage", null, "", Locale.US));
			}
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return baseResponse;
		}
	}

}
