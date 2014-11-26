/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
package org.fluentlenium.adapter.util;

import org.openqa.selenium.Beta;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author : Mathilde Lemee
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Beta
public @interface SharedDriver {
    public enum SharedType {ONCE, PER_CLASS, PER_METHOD}

    /**
     * deleteCookies default : true.
     * If deleteCookies is enabled, after each test method the cookies will be deleted
     *
     * @return
     */
    boolean deleteCookies() default true;

    SharedType type() default SharedType.ONCE;

}
