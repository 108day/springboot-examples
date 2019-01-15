/**
 * Copyright 2018 
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.chat.common.validator;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;

import io.chat.common.vo.AppException;

/**
 * 数据校验
 * @author 
 * @email 
 * @date 2017-03-23 15:50
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new AppException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new AppException(message);
        }
    }

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void isEmpty(Object object, String message) {
    	
    	 if (object == null) {
             throw new AppException(message);
         }
    	 
    	if(object instanceof Map) {
    		Map map =(Map) object;
    		if (map == null || map.size() == 0) {
                throw new IllegalArgumentException(message);
            }
    	}
    	
    	if(object instanceof JSONArray) {
    		JSONArray array =(JSONArray) object;
    		if (array == null || array.size() == 0) {
                throw new IllegalArgumentException(message);
            }
    	}

    	if(object instanceof Collection) {
    		Collection collection =(Collection) object;
    		if (collection == null || collection.size() == 0) {
                throw new IllegalArgumentException(message);
            }
    	}
    	
    	
    }
}
