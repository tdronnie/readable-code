package cleancode.studycafe.tobe.model.pass;

import cleancode.studycafe.tobe.model.pass.locker.StudyCafeLockerPass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudyCafeSeatPassTest {

    @Test
    @DisplayName("사물함패스가 주어진 좌석패스와 패스타입, 기간이 같은지 확인한다.")
    void isSameDuration(){
        //given
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 4, 10000);
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED,4,250000,0.1);


        //when
        boolean result = lockerPass.isSamePassType(seatPass.getPassType())
                && lockerPass.isSameDuration(seatPass.getDuration());

        //then
        assertTrue(result);
    }



}