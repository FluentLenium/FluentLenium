package org.fest.assertions.fluentlenium.custom;

import org.fest.assertions.GenericAssert;
import org.fluentlenium.core.domain.FluentList;

import java.util.List;

public class FluentListAssert extends GenericAssert<FluentListAssert, FluentList> {
    public FluentListAssert(FluentList<?> actual) {
        super(FluentListAssert.class, actual);
    }

   /**
     * check if at least one element of the FluentList contains the text
     *
     * @return
     */
    public FluentListAssert hasText(String textToFind) {
        List<String> actualTexts = actual.getTexts();
        for(String text : actualTexts) {
            if(text.contains(textToFind)){
                return this;
            }
        }
        super.fail("No selected elements contains text: " + textToFind + " . Actual texts found : " + actualTexts);
        return this;
    }

   /**
     * check if at no element of the FluentList contains the text
     *
     * @return
     */
    public FluentListAssert hasNotText(String textToFind) {
        List<String> actualTexts = actual.getTexts();
        for(String text : actualTexts) {
            if(text.contains(textToFind)){
                super.fail("At least one selected elements contains text: " + textToFind + " . Actual texts found : " + actualTexts);
            }
        }
        return this;
    }

	public FluentListAssert hasSize(int expectedSize) {
		if(actual.size() != expectedSize) {
			super.fail("Expected size: " + expectedSize + ". Actual size: " + actual.size() + ".");
		}
		return this;
	}
	
	public FluentListSizeBuilder hasSize() {
		return new FluentListSizeBuilder(actual.size(), this);
	}
	
   /**
     * check if an element of the FluentList has the id
     *
	 * @param idToFind
     * @return
     */
	public FluentListAssert hasId(String idToFind) {
		List actualIds = actual.getIds();
		if (!actualIds.contains(idToFind)) {
			super.fail("No selected elements has id: " + idToFind + " . Actual texts found : " + actualIds);
		}
		return this;
	}

   /**
     * check if at least one element of the FluentList contains the text
     *
     * @return
     */
    public FluentListAssert hasClass(String classToFind) {
        List<String> actualClasses = actual.getAttributes("class");
        if (!actualClasses.contains(classToFind)) {
			super.fail("No selected elements has class: " + classToFind + " . Actual classes found : " + actualClasses);
        }
        return this;
    }

	/*
	 * Used in FluentListSizeBuilder to raise AssertionError
	 */
	void internalFail(String reason) {
		super.fail(reason);
	}

}
