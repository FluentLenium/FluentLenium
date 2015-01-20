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

package org.fluentlenium.unit;


import org.fluentlenium.core.filter.matcher.CalculateService;
import org.junit.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class CheckFindSelector {

    @Test
    public void checkSimpleEqualOk() {
        assertThat(CalculateService.equal(null, "toto", "toto")).isTrue();
    }

    @Test
    public void checkSimpleEqualNok() {
        assertThat(CalculateService.equal(null, "toto", "tot")).isFalse();
    }

    @Test
    public void checkPatternEqualOk() {
        assertThat(CalculateService.equal(Pattern.compile("[to]*"), null, "toto")).isTrue();

    }

    @Test
    public void checkPatternEqualNok() {
        assertThat(CalculateService.equal(Pattern.compile("[to]?"), null, "tot")).isFalse();
    }

    @Test
    public void checkSimpleContainsOk() {
        assertThat(CalculateService.contains(null, "to", "toto")).isTrue();
    }

    @Test
    public void checkSimpleContainsNok() {
        assertThat(CalculateService.contains(null, "toto", "ecole")).isFalse();
    }

    @Test
    public void checkPatternContainsOk() {
        assertThat(CalculateService.contains(Pattern.compile("[to]*"), null, "toto")).isTrue();

    }

    @Test
    public void checkPatternContainsNok() {
        assertThat(CalculateService.contains(Pattern.compile("[ta]*]"), null, "tot")).isFalse();
    }

    @Test
    public void checkSimpleStartsWithOk() {
        assertThat(CalculateService.startsWith(null, "to", "toto")).isTrue();
    }

    @Test
    public void checkSimpleStartsWithNok() {
        assertThat(CalculateService.startsWith(null, "to", "la to to")).isFalse();
    }

    @Test
    public void checkPatternStartsWithOk() {
        assertThat(CalculateService.startsWith(Pattern.compile("[to]*"), null, "toto")).isTrue();

    }

    @Test
    public void checkPatternStartsWithNok() {
        assertThat(CalculateService.startsWith(Pattern.compile("[ta]*]"), null, "tot")).isFalse();
    }

    @Test
    public void checkSimpleEndsWithOk() {
        assertThat(CalculateService.endsWith(null, "to", "toto")).isTrue();
    }

    @Test
    public void checkSimpleEndsWithNok() {
        assertThat(CalculateService.endsWith(null, "la", "la to to")).isFalse();
    }

    @Test
    public void checkPatternEndsWithOk() {
        assertThat(CalculateService.endsWith(Pattern.compile("[to]*"), null, "toto to")).isTrue();

    }

    @Test
    public void checkPatternEndsWithNok() {
        assertThat(CalculateService.endsWith(Pattern.compile("[ta]*]"), null, "ta ta ")).isFalse();
    }
}
