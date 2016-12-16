package org.fluentlenium.core.conditions.wait;

import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.components.DefaultComponentInstantiator;
import org.fluentlenium.core.conditions.FluentConditions;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.wait.FluentWait;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WaitConditionTest {
    @Mock
    private FluentControl control;

    @Mock
    private WebElement element;

    private FluentWait wait;

    private ComponentInstantiator instantiator;

    @Before
    public void before() {
        instantiator = new DefaultComponentInstantiator(control);

        wait = spy(new FluentWait(control).atMost(1000).pollingEvery(50));
    }

    @Test
    public void testWaitEnabled() {
        FluentConditions conditions = WaitConditionProxy.one(wait, "context", new Supplier<List<? extends FluentWebElement>>() {
            @Override
            public List<? extends FluentWebElement> get() {
                FluentList<FluentWebElement> list = instantiator.newFluentList();
                FluentWebElement fluentWebElement = instantiator.newFluent(element);
                list.add(fluentWebElement);
                return list;
            }
        });

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                when(element.isEnabled()).thenReturn(true);
            }
        }, 100L);

        conditions.enabled();
        verify(wait).untilPredicate(any(Predicate.class));
    }

    @Test
    public void testWaitNotEnabled() {
        when(element.isEnabled()).thenReturn(true);

        FluentConditions conditions = WaitConditionProxy.one(wait, "context", new Supplier<List<? extends FluentWebElement>>() {
            @Override
            public List<? extends FluentWebElement> get() {
                FluentList<FluentWebElement> list = instantiator.newFluentList();
                FluentWebElement fluentWebElement = instantiator.newFluent(element);
                list.add(fluentWebElement);
                return list;
            }
        });

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                when(element.isEnabled()).thenReturn(false);
            }
        }, 100L);

        conditions.not().enabled();
        verify(wait).untilPredicate(any(Predicate.class));
    }

    @Test(expected = TimeoutException.class)
    public void testWaitEnabledTimeout() {
        FluentConditions conditions = WaitConditionProxy.one(wait, "context", new Supplier<List<? extends FluentWebElement>>() {
            @Override
            public List<? extends FluentWebElement> get() {
                FluentList<FluentWebElement> list = instantiator.newFluentList();
                FluentWebElement fluentWebElement = instantiator.newFluent(element);
                list.add(fluentWebElement);
                return list;
            }
        });

        conditions.enabled();
    }

    @Test
    public void testId() {
        FluentConditions conditions = WaitConditionProxy.one(wait, "context", new Supplier<List<? extends FluentWebElement>>() {
            @Override
            public List<? extends FluentWebElement> get() {
                FluentList<FluentWebElement> list = instantiator.newFluentList();
                FluentWebElement fluentWebElement = instantiator.newFluent(element);
                list.add(fluentWebElement);
                return list;
            }
        });

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                when(element.getAttribute("id")).thenReturn("test");
            }
        }, 100L);

        conditions.id().equalToIgnoreCase("test");
        verify(wait).untilPredicate(any(Predicate.class));
    }

    @Test(expected = TimeoutException.class)
    public void testIdTimeout() {
        FluentConditions conditions = WaitConditionProxy.one(wait, "context", new Supplier<List<? extends FluentWebElement>>() {
            @Override
            public List<? extends FluentWebElement> get() {
                FluentList<FluentWebElement> list = instantiator.newFluentList();
                FluentWebElement fluentWebElement = instantiator.newFluent(element);
                list.add(fluentWebElement);
                return list;
            }
        });

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                when(element.getAttribute("id")).thenReturn("invalid");
            }
        }, 100L);

        conditions.id().equalToIgnoreCase("test");
    }

    @Test
    public void testRectangleSize() {
        when(element.getRect()).thenReturn(new Rectangle(10, 20, 30, 30));

        FluentConditions conditions = WaitConditionProxy.one(wait, "context", new Supplier<List<? extends FluentWebElement>>() {
            @Override
            public List<? extends FluentWebElement> get() {
                FluentList<FluentWebElement> list = instantiator.newFluentList();
                FluentWebElement fluentWebElement = instantiator.newFluent(element);
                list.add(fluentWebElement);
                return list;
            }
        });

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                when(element.getRect()).thenReturn(new Rectangle(10, 20, 30, 40));
            }
        }, 100L);

        conditions.rectangle().width().greaterThan(30);
    }

    @Test(expected = TimeoutException.class)
    public void testRectangleSizeTimeout() {
        when(element.getRect()).thenReturn(new Rectangle(10, 20, 30, 30));

        FluentConditions conditions = WaitConditionProxy.one(wait, "context", new Supplier<List<? extends FluentWebElement>>() {
            @Override
            public List<? extends FluentWebElement> get() {
                FluentList<FluentWebElement> list = instantiator.newFluentList();
                FluentWebElement fluentWebElement = instantiator.newFluent(element);
                list.add(fluentWebElement);
                return list;
            }
        });

        conditions.rectangle().width().greaterThan(30);
    }
}
