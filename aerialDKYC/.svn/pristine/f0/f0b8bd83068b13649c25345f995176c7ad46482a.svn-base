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

import com.edios.cdf.bean.common.ApplicationParameterBean;
import com.edios.cdf.bean.security.RoleBean;
import com.edios.cdf.entity.to.MenuEntityTO;
import com.edios.cdf.entity.to.RoleEntityTO;
import com.edios.cdf.manager.RoleManager;
import com.edios.cdf.util.DeleteRecords;
import com.edios.cdf.util.PayloadBean;
import com.edios.cdf.validator.BaseResponse;
import com.google.gson.Gson;

@RestController
public class RoleController extends AbstractController {

	@Autowired
	MessageSource messageSource;

	@Autowired
	RoleManager roleManager;

	@PostMapping("/fetch-roles")
	public ResponseEntity<List<RoleEntityTO>> fetchRoles(@RequestBody PayloadBean payloadBean) {
		List<RoleEntityTO> roleEntityTOList = null;
		if (payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			roleEntityTOList = roleManager.fetchRoles(payloadBean.getAccountId());
			return new ResponseEntity<List<RoleEntityTO>>(roleEntityTOList, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("/fetch-roles-on-criteria")
	public ResponseEntity<List<RoleEntityTO>> fetchRolesOnCriteria(@RequestBody PayloadBean payloadBean) {
		List<RoleEntityTO> roleEntityTOList = null;
		if (payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			roleEntityTOList = roleManager.fetchRolesOnCriteria(payloadBean.getSearchParameter(),payloadBean.getAccountId());
			return new ResponseEntity<List<RoleEntityTO>>(roleEntityTOList, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("/add-role")
	public BaseResponse addRole(@RequestBody RoleBean roleBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		roleBean.setIpAddress(request.getRemoteAddr());
		String resultString = roleManager.addRole(roleBean);
		if (resultString.equalsIgnoreCase("ADDED")) {
			baseResponse = new BaseResponse(HttpStatus.OK, "ADDED", "");
		} else if (resultString.equalsIgnoreCase("NameAlreadyExist")) {
			baseResponse = new BaseResponse(HttpStatus.ALREADY_REPORTED, "ALREADY_REPORTED",
					messageSource.getMessage("duplicateFieldMessage", new Object[] { "Role Name" }, "", Locale.US));
		} /*else if (resultString.equalsIgnoreCase("CodeAlreadyExist")) {
			baseResponse = new BaseResponse(HttpStatus.ALREADY_REPORTED, "ALREADY_REPORTED",
					messageSource.getMessage("duplicateFieldMessage", new Object[] { "Site Code" }, "", Locale.US));
		}*/
		return baseResponse;
	}

	@PostMapping("/edit-role")
	public ResponseEntity<RoleBean> findRoleById(@RequestBody PayloadBean payloadBean) {
		if (payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			RoleBean roleBean = roleManager.findRoleById(payloadBean.getId(), payloadBean.getAccountId());
			return new ResponseEntity<RoleBean>(roleBean, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
	}
	
	@PutMapping("/update-role")
	public BaseResponse updateSite(@RequestBody RoleBean roleBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		try {
			roleBean.setIpAddress(request.getRemoteAddr());
			String resultString = roleManager.updateRole(roleBean);
			if (resultString.equalsIgnoreCase("UPDATED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", "");
			} else if (resultString.equalsIgnoreCase("NameAlreadyExist")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "ALREADY_EXISTS",
						messageSource.getMessage("duplicateFieldMessage", new Object[] { "Role Name" }, "", Locale.US));
			} /*else if (resultString.equalsIgnoreCase("CodeAlreadyExist")) {
				baseResponse = new BaseResponse(HttpStatus.CONFLICT, "CONFLICT",
						messageSource.getMessage("duplicateFieldMessage", new Object[] { "Site Code" }, "", Locale.US));
			} */else if (resultString.equalsIgnoreCase("TransactionFailed")) {
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
	
	@PostMapping("/delete-role")
	public BaseResponse deleteSite(@RequestBody DeleteRecords deleteRecords) {
		BaseResponse baseResponse = null;
		try {
		    String resultString = roleManager.deleteRole(deleteRecords);
			if (resultString.equalsIgnoreCase("DELETED")) {
			   baseResponse = new BaseResponse(HttpStatus.OK, "DELETED", ""); 
			} else if (resultString.equalsIgnoreCase("TransactionFailed")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "TRANSACTION_FAILED",
						messageSource.getMessage("transactionFailedUpdateMessage", null, "", Locale.US));
			} else if (resultString.equalsIgnoreCase("recordDeleted")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "TRANSACTION_FAILED",
						messageSource.getMessage("transactionFailedDeleteMessage", null, "", Locale.US));
			}else if (resultString.equalsIgnoreCase("ROLE_EXISTS")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "ALREADY_EXISTS",
						messageSource.getMessage("transactionFailedNotDeletedMessage", null, "", Locale.US));
			} 
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return baseResponse;
		}
	}
	
	@PostMapping("/fetch-menus")
	public ResponseEntity<List<MenuEntityTO>> fetchAccountMenus(@RequestBody PayloadBean payloadBean) {
		List<MenuEntityTO> menuEntityTOList=null;
		if(payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			menuEntityTOList = roleManager.fetchAccountMenus();
		}
		 Gson gson = new Gson();
		   // response = gson.toJson(new ResponseEntity<List<MenuEntityTO>>(menuEntityTOList, HttpStatus.OK));
		System.out.println("Fetch Menu Data is :::: "+gson.toJson(new ResponseEntity<List<MenuEntityTO>>(menuEntityTOList, HttpStatus.OK)));
		return new ResponseEntity<List<MenuEntityTO>>(menuEntityTOList, HttpStatus.OK);
		
	}

}
