package com.imine.pixelmon.trigger.action;

import org.junit.Ignore;
import org.junit.Test;
import org.spongepowered.api.entity.living.player.Player;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class DelayedActionTest {

    private final TestAction testAction = new TestAction();
    private DelayedAction delayedAction = new DelayedAction(Arrays.asList(testAction, new LogAction("Action!")), null, 50);

    @Test
    @Ignore
    public void testPerform() throws Exception {
        delayedAction.perform(null);
        Thread.sleep(100);
        assertEquals(1, testAction.getInvocationCount());
    }

    private static class TestAction implements Action {

        private int invocationCount = 0;

        @Override
        public void perform(Player player) {
            invocationCount++;
        }

        public int getInvocationCount() {
            return invocationCount;
        }
    }
}