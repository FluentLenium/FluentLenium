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


public final class SharedDriverHelper {

    public boolean isSharedDriver(final Class<?> classe) {
        return (classe.getAnnotation(SharedDriver.class) != null);
    }

    public static boolean isDeleteCookies(final Class<?> classe) {
        SharedDriver sharedBrowser = classe.getAnnotation(SharedDriver.class);
        return (sharedBrowser != null && sharedBrowser.deleteCookies());
    }

    public static boolean isSharedDriverOnce(final Class<?> classe) {
        SharedDriver sharedBrowser = classe.getAnnotation(SharedDriver.class);
        return (sharedBrowser != null && sharedBrowser.type() == SharedDriver.SharedType.ONCE);
    }

    public static boolean isSharedDriverPerClass(final Class<?> classe) {
        SharedDriver sharedBrowser = classe.getAnnotation(SharedDriver.class);
        return (sharedBrowser != null && sharedBrowser.type() == SharedDriver.SharedType.PER_CLASS);
    }

    public static boolean isSharedDriverPerMethod(final Class<?> classe) {
        SharedDriver sharedBrowser = classe.getAnnotation(SharedDriver.class);
        return (sharedBrowser != null && sharedBrowser.type() == SharedDriver.SharedType.PER_METHOD);
    }

    public static boolean isDefaultSharedDriver(final Class<?> classe) {
        SharedDriver sharedBrowser = classe.getAnnotation(SharedDriver.class);
        return (sharedBrowser == null);
    }
}