/*
 * Copyright 2020 the original author or authors.
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
package auth.reource.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Joe Grandja
 * @since 0.0.1
 */
@RestController
public class UserController {

	@GetMapping("/getUser")
	public Map<String,String> getMessages() {
		Map<String, String> user = new HashMap<>();
		user.put("username", "1809168104");
		user.put("password", "123456");
		user.put("nickname", "杨十七");
		return user;
	}
}
