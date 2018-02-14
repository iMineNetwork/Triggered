package com.imine.pixelmon.trigger.action;

import org.junit.Test;
import org.spongepowered.api.entity.living.player.Player;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class DelayedActionTest {

    private final TestAction testAction = new TestAction();
    private DelayedAction delayedAction = new DelayedAction(Arrays.asList(testAction, new LogAction("Action!")), 50);

    @Test
    public void testPerform() throws Exception {
        delayedAction.perform(null);
        Thread.sleep(60);
        assertEquals(1, testAction.getInvokationCount());
    }

    private static class TestAction implements Action {

        private int invokationCount = 0;

        @Override
        public void perform(Player player) {
            invokationCount++;
        }

        public int getInvokationCount() {
            return invokationCount;
        }
    }
}