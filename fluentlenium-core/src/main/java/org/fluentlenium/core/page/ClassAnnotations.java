package org.fluentlenium.core.page;

import org.openqa.selenium.By;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.pagefactory.AbstractAnnotations;

/**
 * Inspired by {@link org.openqa.selenium.support.pagefactory.Annotations}, but use a Class instead of a Field
 * to retrieve the annotations.
 */
public class ClassAnnotations extends AbstractAnnotations {
    private final Class<?> cls;

    /**
     * @param cls Class expected to be a Page Object
     */
    public ClassAnnotations(Class<?> cls) {
        this.cls = cls;
    }

    /**
     * {@inheritDoc}
     *
     * @return true if @CacheLookup annotation exists on a class
     */
    public boolean isLookupCached() {
        return cls.getAnnotation(CacheLookup.class) != null;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Looks for one of {@link org.openqa.selenium.support.FindBy},
     * {@link org.openqa.selenium.support.FindBys} or
     * {@link org.openqa.selenium.support.FindAll} class annotations. In case
     * no annotaions provided for field, returns null.
     *
     * @throws IllegalArgumentException when more than one annotation on a class provided
     */
    public By buildBy() {
        assertValidAnnotations();

        By ans = null;

        FindBys findBys = cls.getAnnotation(FindBys.class);
        if (findBys != null) {
            ans = buildByFromFindBys(findBys);
        }

        FindAll findAll = cls.getAnnotation(FindAll.class);
        if (ans == null && findAll != null) {
            ans = buildBysFromFindByOneOf(findAll);
        }

        FindBy findBy = cls.getAnnotation(FindBy.class);
        if (ans == null && findBy != null) {
            ans = buildByFromFindBy(findBy);
        }

        return ans;
    }

    protected Class<?> getCls() {
        return cls;
    }

    protected void assertValidAnnotations() {
        FindBys findBys = cls.getAnnotation(FindBys.class);
        FindAll findAll = cls.getAnnotation(FindAll.class); // NOPMD PrematureDeclaration
        FindBy findBy = cls.getAnnotation(FindBy.class);
        if (findBys != null && findBy != null) {
            throw new IllegalArgumentException(
                    "If you use a '@FindBys' annotation, " + "you must not also use a '@FindBy' annotation");
        }
        if (findAll != null && findBy != null) {
            throw new IllegalArgumentException(
                    "If you use a '@FindAll' annotation, " + "you must not also use a '@FindBy' annotation");
        }
        if (findAll != null && findBys != null) {
            throw new IllegalArgumentException(
                    "If you use a '@FindAll' annotation, " + "you must not also use a '@FindBys' annotation");
        }
    }
}
