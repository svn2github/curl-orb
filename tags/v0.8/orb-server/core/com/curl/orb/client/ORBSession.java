// Copyright (C) 1998-2010, Sumisho Computer Systems Corp. All Rights Reserved.

// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
//     http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.curl.orb.client;

import org.apache.commons.httpclient.HttpClient;

public class ORBSession 
{
	private HttpClient httpClient;
	
	HttpClient getHttpClient()
	{
		return httpClient;
	}
	
	public ORBSession()
	{
		httpClient = new HttpClient();
	}
}
