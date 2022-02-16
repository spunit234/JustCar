package com.edios.cdf.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.edios.cdf.bean.security.UserBean;
import com.edios.cdf.entity.to.UserProfileTO;
import com.edios.cdf.manager.ManageUserManager;
import com.edios.cdf.util.DeleteRecords;
import com.edios.cdf.validator.BaseResponse;

@RestController
public class ManageUsersController extends AbstractController {
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	ManageUserManager manageUserManager;

	@GetMapping("edit-user/{userID}")
	public ResponseEntity<UserBean> editUser(@PathVariable("userID") Long userID) {
		UserBean userBean = null;
		try {
			userBean = manageUserManager.finduserById(userID);
		} catch (Exception exception) {
			exception.printStackTrace();
			return new ResponseEntity<UserBean>(userBean, HttpStatus.OK);
		}
		return new ResponseEntity<UserBean>(userBean, HttpStatus.OK);
	}

	@GetMapping("edit-account-user/{userID}")
	public ResponseEntity<List<UserProfileTO>> supplierAutoLookUp(@PathVariable("userID") Long userID) {
		List<UserProfileTO> userAccountList = manageUserManager.fetchAccountUsers(userID);
		if (userAccountList.size() == 0) {
			return new ResponseEntity<List<UserProfileTO>>(userAccountList, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<UserProfileTO>>(userAccountList, HttpStatus.OK);
		}
	}

	@PostMapping("/update-user")
	public BaseResponse updateUserInfo(@RequestBody UserBean userBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		try {
			userBean.setUserIPAddress(request.getRemoteAddr());
			String resultString = manageUserManager.updateUserInfo(userBean);
			if (resultString.equalsIgnoreCase("UPDATED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", "");
			}else if (resultString.equalsIgnoreCase("UPDATED_EMAIL")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED_EMAIL", "");
			} else if (resultString.equalsIgnoreCase("TransactionFailed")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "TRANSACTION_FAILED",
						messageSource.getMessage("transactionFailedUpdateMessage", null, "", Locale.US));
			} else if (resultString.equalsIgnoreCase("recordDeleted")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "TRANSACTION_FAILED",
						messageSource.getMessage("transactionFailedDeleteMessage", null, "", Locale.US));
			} else if (resultString.equalsIgnoreCase("LESS_AMOUNT")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "LESS_AMOUNT",
						messageSource.getMessage("lessAmount", null, "", Locale.US));
			}
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return baseResponse;
		}
	}

	@PostMapping("/delete-user")
	public BaseResponse deleteUser(@RequestBody DeleteRecords deleteRecords) {
		BaseResponse baseResponse = null;
		try {
			String resultString = manageUserManager.deleteUser(deleteRecords);
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

	@PostMapping("/add-user")
	public BaseResponse addUser(@RequestBody UserBean userBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		try {
			userBean.setUserIPAddress(request.getRemoteAddr());
			String resultString = manageUserManager.addUser(userBean);
			if (resultString.equalsIgnoreCase("ADDED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "ADDED", "");
			} else if (resultString.equalsIgnoreCase("ADDED_EMAIL")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "ADDED_EMAIL", "");
			}
			else if (resultString.equalsIgnoreCase("user_exist")) {
				baseResponse = new BaseResponse(HttpStatus.ALREADY_REPORTED, "ALREADY_REPORTED",
						messageSource.getMessage("duplicateFieldMessage", new Object[] { "UserName" }, "", Locale.US));
			} else {
				baseResponse = new BaseResponse(HttpStatus.OK, "EXCEPTION", "Some Thing went wrong! please try later.");
			}
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return baseResponse;
		}
	}

}
