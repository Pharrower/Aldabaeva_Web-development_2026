package com.example.shelter;

import com.example.shelter.entities.Supply;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SupplyLogicTest {

    @Test
    public void testPercentCalculation() {
        Supply supply = new Supply();
        supply.setCurrentAmount(50.0);
        supply.setTargetAmount(200.0);

        // Проверяем формулу: (50 / 200) * 100 = 25%
        assertEquals(25, supply.getPercent(), "Расчет процента заполненности склада неверный");
    }

    @Test
    public void testColorLogicDanger() {
        Supply supply = new Supply();
        supply.setCurrentAmount(10.0);
        supply.setTargetAmount(100.0);

        // 10% — это критически мало, должен быть красный цвет (bg-danger)
        assertEquals("bg-danger", supply.getColorClass());
    }

    @Test
    public void testColorLogicSuccess() {
        Supply supply = new Supply();
        supply.setCurrentAmount(80.0);
        supply.setTargetAmount(100.0);

        // 80% — это норма, должен быть зеленый цвет (bg-success)
        assertEquals("bg-success", supply.getColorClass());
    }
}