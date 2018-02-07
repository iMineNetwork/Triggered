package com.imine.pixelmon.service;

import com.flowpowered.math.vector.Vector3d;
import com.imine.pixelmon.trigger.Interval;
import com.imine.pixelmon.trigger.Trigger;
import com.imine.pixelmon.trigger.action.CommandAction;
import com.imine.pixelmon.trigger.action.LogAction;
import com.imine.pixelmon.trigger.condition.AreaCondition;
import org.junit.Before;
import org.junit.Test;
import org.spongepowered.api.util.AABB;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TriggerServiceTest {

    private TriggerService testTriggerService;

    @Before
    public void setUp() throws Exception {
        this.testTriggerService = new TriggerService(Paths.get("testStoragePath.json"));
    }

    @Test
    public void getTriggers() throws Exception {
        List<Trigger> triggers = testTriggerService.getTriggers();
        triggers.forEach(System.out::println);
    }

    @Test
    public void testSaveTriggers() throws Exception {
        testTriggerService.getTriggers();

        List<Trigger> triggerList = Arrays.asList(
                new Trigger(Collections.singletonList(new AreaCondition(new AABB(new Vector3d(0, 1, 2), new Vector3d(4,5,6)), "Pixelmon_Overworld")), Collections.singletonList(new LogAction("Logging Action for entity {}")), Interval.ONCE),
                new Trigger(Collections.singletonList(new AreaCondition(new AABB(new Vector3d(16, 123, 8), new Vector3d(163,10,100)), "Pixelmon_Interior")), Collections.singletonList(new CommandAction("/tp")), Interval.ALWAYS)
        );

        testTriggerService.saveTriggers(triggerList);

        Files.lines(Paths.get("testStoragePath.json")).forEach(System.out::println);
    }

}