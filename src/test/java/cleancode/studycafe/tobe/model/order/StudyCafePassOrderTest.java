package cleancode.studycafe.tobe.model.order;

import cleancode.studycafe.tobe.model.pass.StudyCafePassType;
import cleancode.studycafe.tobe.model.pass.StudyCafeSeatPass;
import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudyCafePassOrderTest {

    @Test
    @DisplayName("좌석패스가 주어졌을 때, getDiscountPrice 메서드는 좌석패스의 할인되는 가격을 반환한다.")
    void getDiscountPrice() {
        // given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 12, 700000, 0.15);

        // when
        int result = seatPass.getDiscountPrice();

        // then
        assertEquals(105000, result);
    }

    @Test
    @DisplayName("좌석패스만 있고 사물함패스는 없는 경우, getTotalPrice 메서드는 총 가격을 반환한다.")
    void getTotalPriceWithoutLocker(){
        //given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 12, 400000, 0.15);
        StudyCafePassOrder passOrder = StudyCafePassOrder.of(seatPass, null);

        //when
        int totalPrice = passOrder.getTotalPrice();

        //then
        assertEquals(340000, totalPrice);
    }

    @Test
    @DisplayName("좌석패스와 사물함패스 모두 있는 경우, getTotalPrice 메서드는 총 가격을 반환한다.")
    void getTotalPriceWithLocker(){
        //given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 4, 250000, 0.1);
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 4, 10000);
        StudyCafePassOrder passOrder = StudyCafePassOrder.of(seatPass, lockerPass);

        //when
        int totalPrice = passOrder.getTotalPrice();

        //then
        assertEquals(235000, totalPrice);
    }

}