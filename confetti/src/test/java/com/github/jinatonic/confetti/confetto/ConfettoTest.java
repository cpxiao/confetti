package com.github.jinatonic.confetti.confetto;

import com.github.jinatonic.confetti.confetti.Confetti;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ConfettoTest {

    @Test
    public void test_computeMillisToReachTarget() {
        Long time = Confetti.computeMillisToReachTarget(null, 0f, 0f);
        assertNull(time);
        time = Confetti.computeMillisToReachTarget(0f, 10f, 10f);
        assertEquals(0, time.longValue());
        time = Confetti.computeMillisToReachTarget(20f, 10f, 10f);
        assertEquals(1, time.longValue());
        time = Confetti.computeMillisToReachTarget(30f, 0f, 10f);
        assertEquals(3, time.longValue());
        time = Confetti.computeMillisToReachTarget(20f, 10f, 0f);
        assertNull(time);
    }

    @Test
    public void test_computeBound_noAcceleration() {
        // Normal velocity
        long time = Confetti.computeBound(0f, 0.01f, 0f, null, null, -10000, 100);
        assertEquals(10000, time);
        time = Confetti.computeBound(0f, -0.01f, 0f, null, null, -100, 10000);
        assertEquals(10000, time);
        time = Confetti.computeBound(10f, 0.01f, 0f, null, null, -10000, 100);
        assertEquals(9000, time);
        time = Confetti.computeBound(10f, -0.01f, 0f, null, null, -100, 10000);
        assertEquals(11000, time);

        // Normal velocity with non-null unreachable target velocity
        time = Confetti.computeBound(0f, 0.01f, 0f, null, 0.02f, -10000, 100);
        assertEquals(10000, time);
        time = Confetti.computeBound(0f, -0.01f, 0f, null, 0.02f, -100, 10000);
        assertEquals(10000, time);

        // Normal velocity with non-null already-reached target velocity
        time = Confetti.computeBound(0f, 0.01f, 0f, 0L, -0.01f, -100, 10000);
        assertEquals(10000, time);

        // Normal velocity with the initial position past bound
        time = Confetti.computeBound(-100f, 0.01f, 0f, null, null, -50, 100);
        assertEquals(20000, time);
    }

    @Test
    public void test_computeBound_withAcceleration() {
        // 100 = 0.5 * 0.01 * t * t, t = sqrt(20000) or 141
        long time = Confetti.computeBound(0f, 0f, 0.01f, null, null, -10000, 100);
        assertEquals(141, time);
        time = Confetti.computeBound(0f, 0f, -0.01f, null, null, -100, 10000);
        assertEquals(141, time);

        // 100 = 10 + 0.01 * t + 0.5 * 0.01 * t * t, t 3.358
        time = Confetti.computeBound(10f, 0.01f, 0.01f, null, null, -10000, 100);
        assertEquals(133, time);
        time = Confetti.computeBound(-10f, -0.01f, -0.01f, null, null, -100, 10000);
        assertEquals(133, time);
    }

    @Test
    public void test_computeBound_withAccelerationAndTargetVelocity() {
        // 100 = 0.5 * 0.01 * 3 * 3 + 0.03 * (t - 3)
        long time = Confetti.computeBound(0f, 0f, 0.01f, 3L, 0.03f, -10000, 100);
        assertEquals(3334, time);
        time = Confetti.computeBound(0f, 0f, -0.01f, 3L, -0.03f, -100, 10000);
        assertEquals(3334, time);

        // 100 = 10 + 0.01 * 3 + 0.5 * 0.01 * 3 * 3 + 0.04 * (t - 3)
        time = Confetti.computeBound(10f, 0.01f, 0.01f, 3L, 0.04f, -10000, 100);
        assertEquals(2251, time);
        time = Confetti.computeBound(10f, -0.01f, -0.01f, 3L, -0.04f, -100, 10000);
        assertEquals(2251, time);
    }
}
