package com.edios.cdf.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.edios.cdf.entity.to.RoleRightsEntityTO;
import com.edios.cdf.entity.to.RoleRightsTO;
import com.edios.cdf.manager.RoleRightsManager;
import com.edios.cdf.manager.SecurityManager;

@RestController
public class RoleRightsController extends AbstractController {
  
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	SecurityManager securityManager;
	
	@Autowired
	RoleRightsManager roleRightsManager;
	
	@PostMapping("/role-rights")
	public ResponseEntity<RoleRightsEntityTO> fetchRoleRights(@RequestBody RoleRightsEntityTO payloadBean) {
		RoleRightsEntityTO rightsEntityTO=null;
			rightsEntityTO = securityManager.fetchRoleRights(payloadBean);
		return new ResponseEntity<RoleRightsEntityTO>(rightsEntityTO, HttpStatus.OK);
	}
	
	@GetMapping("/fetch-menus-for-role-rights")
	public ResponseEntity<List<RoleRightsTO>> fetchRoleRightsMenus() {
		List<RoleRightsTO> roleRightsTO=null;
		roleRightsTO = roleRightsManager.fetchRoleRightsMenus();
		return new ResponseEntity<List<RoleRightsTO>>(roleRightsTO, HttpStatus.OK);
	}
	
	@GetMapping("/fetch-role-rights/{roleID}")
	public ResponseEntity<List<RoleRightsTO>> fetchRoleRightsByRole(@PathVariable("roleID") Long roleID ) {
		List<RoleRightsTO> roleRightsTO=null;
		roleRightsTO = roleRightsManager.fetchRoleRightsByRole(roleID);
		return new ResponseEntity<List<RoleRightsTO>>(roleRightsTO, HttpStatus.OK);
	}
}
