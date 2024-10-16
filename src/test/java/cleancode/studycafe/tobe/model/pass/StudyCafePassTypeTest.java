package cleancode.studycafe.tobe.model.pass;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class StudyCafePassTypeTest {

    private static final Set<StudyCafePassType> LOCKER_PASS = Set.of(StudyCafePassType.FIXED);

    @Test
    @DisplayName("사물함패스를 가지는 좌석패스인지 여부를 반환한다.")
    void isLockerType(){
        //given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED,4,250000,0.1);

        //when
        boolean isLockerPass = LOCKER_PASS.contains(seatPass.getPassType());

        //then
        assertTrue(isLockerPass);
    }

}