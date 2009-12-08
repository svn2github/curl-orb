// Copyright (C) 1998-2008, Sumisho Computer Systems Corp. All Rights Reserved.

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

package com.curlap.orb.generator;

import java.lang.annotation.Annotation;

/**
 * Annotation property to generate Curl source code.
 * 
 * @author Hitoshi Okada
 * @since 0.7
 */
public class AnnotationProperty implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	
	private ClassProperty annotationType;

	public ClassProperty getAnnotationType() {
		return annotationType;
	}

	public void setAnnotationType(ClassProperty annotationType) {
		this.annotationType = annotationType;
	}
	
	static AnnotationProperty[] create(Annotation[] annotations) throws GeneratorException 
	{
		int length = annotations.length;
		AnnotationProperty[] aps = new AnnotationProperty[length];
		for (int i = 0; i < length; i++)
		{
			AnnotationProperty ap = new AnnotationProperty();
			ap.setAnnotationType(new ClassProperty(annotations[i].annotationType().getName()));
			aps[i] = ap;
		}
		return aps;
	}
}
