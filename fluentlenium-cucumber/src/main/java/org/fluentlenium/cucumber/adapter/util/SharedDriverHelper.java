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
package org.fluentlenium.cucumber.adapter.util;

import org.fluentlenium.cucumber.adapter.FluentCucumberAdapter;

public final class SharedDriverHelper {


    public static SharedDriver getSharedBrowser(final Class classe) {
        Class<?> cls;
        for (cls = classe; FluentCucumberAdapter.class.isAssignableFrom(cls); cls = cls.getSuperclass()) {
            if (cls.isAnnotationPresent(SharedDriver.class)) {
                return cls.getAnnotation(SharedDriver.class);
            }
        }
        return null;
    }

    public boolean isSharedDriver(final Class classe) {
        return (getSharedBrowser(classe) != null);
    }

    public static boolean isSharedDriverPerFeature(final Class classe) {
        SharedDriver sharedBrowser = getSharedBrowser(classe);
        return (sharedBrowser != null && sharedBrowser.type() == SharedDriver.SharedType.PER_FEATURE);
    }

    public static boolean isSharedDriverPerScenario(final Class classe) {
        SharedDriver sharedBrowser = getSharedBrowser(classe);
        return (sharedBrowser != null && sharedBrowser.type() == SharedDriver.SharedType.PER_SCENARIO);
    }

    public static boolean isDefaultSharedDriver(final Class classe) {
        SharedDriver sharedBrowser = getSharedBrowser(classe);
        return (sharedBrowser == null);
    }
}