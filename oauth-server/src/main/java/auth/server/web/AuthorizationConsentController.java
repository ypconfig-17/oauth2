/*
 * Copyright 2020-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package auth.server.web;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Daniel Garnier-Moiroux
 */
@Controller
public class AuthorizationConsentController {


	@Autowired
	private JdbcTemplate jdbcTemplate;

	@GetMapping(value = "/oauth2/list")
	public String consent(Model model) {

		List<Map<String, Object>> mapList = jdbcTemplate.queryForList("select id,registered_client_id,principal_name,authorization_grant_type,attributes,`state` from  oauth2_authorization");
		model.addAttribute("authorizationBeans",mapList);
		return "list";
	}




}
