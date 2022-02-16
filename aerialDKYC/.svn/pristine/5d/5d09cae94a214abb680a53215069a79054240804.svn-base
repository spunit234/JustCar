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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.edios.cdf.bean.security.UserBean;
import com.edios.cdf.entity.to.AccountUserEntityTO;
import com.edios.cdf.entity.to.UserEntityTO;
import com.edios.cdf.entity.to.UserProfileTO;
import com.edios.cdf.manager.UserProfileManager;
import com.edios.cdf.util.PayloadBean;
import com.edios.cdf.validator.BaseResponse;

@RestController
public class UserProfileController extends AbstractController {

	@Autowired
	MessageSource messageSource;
	
	@Autowired
	UserProfileManager userProfileManager;

	@PostMapping("/fetch-default-account")
	public ResponseEntity<List<UserProfileTO>> fetchDefaultAccount(@RequestBody PayloadBean payloadBean) {
		List<UserProfileTO> userBean = userProfileManager.fetchDefaultAccount(payloadBean.getId());
		return new ResponseEntity<List<UserProfileTO>>(userBean, HttpStatus.OK);
	}

	@PostMapping("/fetch-default-role")
	public ResponseEntity<List<UserProfileTO>> fetchDefaultRole(@RequestBody PayloadBean payloadBean) {
		List<UserProfileTO> userBean = userProfileManager.fetchDefaultRole(payloadBean.getId());
		return new ResponseEntity<List<UserProfileTO>>(userBean, HttpStatus.OK);
	}

	@PostMapping("/fetch-default-site")
	public ResponseEntity<List<UserProfileTO>> fetchDefaultSite(@RequestBody PayloadBean payloadBean) {
		List<UserProfileTO> userBean = userProfileManager.fetchDefaultSite(payloadBean.getId());
		return new ResponseEntity<List<UserProfileTO>>(userBean, HttpStatus.OK);
	}

	@PostMapping("/fetch-user-profile-information")
	public ResponseEntity<UserBean> fetchUserProfileInfo(@RequestBody PayloadBean payloadBean) {
		UserBean userBean = userProfileManager.fetchUserProfileInfo(payloadBean);
		return new ResponseEntity<UserBean>(userBean, HttpStatus.OK);
	}

	@PutMapping("/update-user-profile")
	public BaseResponse updateUserProfile(@RequestBody UserBean userBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		try {
			userBean.setUserIPAddress(request.getRemoteAddr());
			String resultString = userProfileManager.updateUserProfile(userBean);
			if (resultString.equalsIgnoreCase("UPDATED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "UPDATED", "");
			} else if (resultString.equalsIgnoreCase("NameAlreadyExist")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "CONFLICT",
						messageSource.getMessage("duplicateFieldMessage", new Object[] { "User Name" }, "", Locale.US));
			}  else if (resultString.equalsIgnoreCase("TransactionFailed")) {
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

	@GetMapping("/fetch-account-details")
	public ResponseEntity<List<AccountUserEntityTO>> fetchAccountDetails() {
		List<AccountUserEntityTO> userBean = userProfileManager.fetchAccountDetails();
		return new ResponseEntity<List<AccountUserEntityTO>>(userBean, HttpStatus.OK);
	}

	@GetMapping("/fetch-role-details")
	public ResponseEntity<List<AccountUserEntityTO>> fetchRoleDetails() {
		List<AccountUserEntityTO> userBean = userProfileManager.fetchRoleDetails();
		return new ResponseEntity<List<AccountUserEntityTO>>(userBean, HttpStatus.OK);
	}

	@GetMapping("/fetch-site-details/{accountID}")
	public ResponseEntity<List<AccountUserEntityTO>> fetchSiteDetails(@PathVariable("accountID") Integer accountID) {
		List<AccountUserEntityTO> userBean = userProfileManager.fetchSiteDetails(accountID);
		return new ResponseEntity<List<AccountUserEntityTO>>(userBean, HttpStatus.OK);
	}

	/*@PostMapping("/add-user")
	public BaseResponse addSupplierStatus(@RequestBody UserBean userBean, HttpServletRequest request) {
		BaseResponse baseResponse = null;
		try {
			System.out.println("ASDsadsadsadsadsadsa");
			userBean.setUserIPAddress(request.getRemoteAddr());
			String resultString = userProfileManager.addUser(userBean);
			if (resultString.equalsIgnoreCase("ADDED")) {
				baseResponse = new BaseResponse(HttpStatus.OK, "ADDED", "");
			} else if (resultString.equalsIgnoreCase("user_exist")) {
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
	}*/

	// @PostMapping("/get-account-name")
	// public ResponseEntity<AccountBean> getAccountName(@RequestBody PayloadBean
	// payloadBean){
	// AccountBean accountEntityTO=
	// userProfileManager.getAccountName(payloadBean.getId());
	// return new ResponseEntity<AccountBean>(accountEntityTO,HttpStatus.OK);
	// }

	@PostMapping("/fetch-users")
	public ResponseEntity<List<UserEntityTO>> fetchUsers(@RequestBody PayloadBean payloadBean) {
		List<UserEntityTO> userEntityTo = null;
		if (payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			userEntityTo = userProfileManager.fetchUsersOnCriteria(payloadBean.getSearchParameter());
			return new ResponseEntity<List<UserEntityTO>>(userEntityTo, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("/fetch-users-on-criteria")
	public ResponseEntity<List<UserEntityTO>> fetchUsersOnCriteria(@RequestBody PayloadBean payloadBean) {
		List<UserEntityTO> userEntityTo = null;
		if (payloadBean.getSignatureKey().equals(messageSource.getMessage("signatureKey", null, "", Locale.US))) {
			userEntityTo = userProfileManager.fetchUsersOnCriteria(payloadBean.getSearchParameter());
			return new ResponseEntity<List<UserEntityTO>>(userEntityTo, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
	}
	/*
	 * @PostMapping("/fetch-users") public ResponseEntity<List<UserEntityTO>>
	 * fetchUsers(@RequestBody PayloadBean payloadBean) { List<UserEntityTO>
	 * siteEntityTOList = null; if
	 * (payloadBean.getSignatureKey().equals(messageSource.getMessage(
	 * "signatureKey", null, "", Locale.US))) { siteEntityTOList =
	 * userProfileManager.fetchUsersOnCriteria(payloadBean.getSearchParameter());
	 * return new ResponseEntity<List<UserEntityTO>>(siteEntityTOList,
	 * HttpStatus.OK); } else { return new ResponseEntity<>(null,
	 * HttpStatus.UNAUTHORIZED); } }
	 */
	// @PostMapping("/delete-user")
	// public BaseResponse deleteUser(@RequestBody DeleteRecords deleteRecords) {
	// BaseResponse baseResponse = null;
	// try {
	// String resultString = userProfileManager.deleteUser(deleteRecords);
	// if (resultString.equalsIgnoreCase("DELETED")) {
	// baseResponse = new BaseResponse(HttpStatus.OK, "DELETED", "");
	// } else if (resultString.equalsIgnoreCase("TransactionFailed")) {
	// baseResponse = new BaseResponse(HttpStatus.OK, "TRANSACTION_FAILED",
	// messageSource.getMessage("transactionFailedUpdateMessage", null, "",
	// Locale.US));
	// } else if (resultString.equalsIgnoreCase("recordDeleted")) {
	// baseResponse = new BaseResponse(HttpStatus.OK, "TRANSACTION_FAILED",
	// messageSource.getMessage("transactionFailedDeleteMessage", null, "",
	// Locale.US));
	// }
	// return baseResponse;
	// } catch (Exception e) {
	// e.printStackTrace();
	// return baseResponse;
	// }
	// }
	//

}
